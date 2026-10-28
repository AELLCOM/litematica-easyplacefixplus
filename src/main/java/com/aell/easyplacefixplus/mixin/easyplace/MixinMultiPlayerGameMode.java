package com.aell.easyplacefixplus.mixin.easyplace;

import com.aell.easyplacefixplus.core.AutoPlaceState;
import com.aell.easyplacefixplus.core.DigGuard;
import com.aell.easyplacefixplus.core.LookSpoof;
import com.aell.easyplacefixplus.core.PlacementGuard;
import com.aell.easyplacefixplus.core.StateFixer;
import com.aell.easyplacefixplus.core.ToolPick;
import com.aell.easyplacefixplus.core.WaterPlace;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGameMode
{
    @Unique
    private static boolean easyplacefixplus$reentrant;

    /**
     * Ванильная установка на воду тем же правым кликом. {@code Minecraft.startUseItem}
     * зовёт {@code useItem} следом за кликом по блоку, а при промахе — сразу, и кувшинка
     * ложится туда, куда смотрит игрок, а не туда, куда просит схема.
     */
    @Inject(method = "useItem", at = @At("HEAD"), cancellable = true)
    private void easyplacefixplus$guardWaterUse(Player player, InteractionHand hand,
            CallbackInfoReturnable<InteractionResult> cir)
    {
        boolean stray;

        try
        {
            stray = WaterPlace.strayUse(player, hand);
        }
        catch (RuntimeException | LinkageError e)
        {
            return;
        }

        if (stray)
        {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }

    /**
     * Номер ушедшего пакета известен только после возврата из клика — вот здесь он и
     * дописывается в запись, заведённую перед кликом. Инжект отдельный, потому что часть
     * решений конвейера ванильный клик не переписывает, а пропускает как есть.
     *
     * {@code useItem} — путь кувшинки, икры лягушки и ведра: у него в пакете свой номер
     * последовательности, и запись заводится так же.
     */
    @Inject(method = "useItemOn", at = @At("RETURN"))
    private void easyplacefixplus$stampOnBlock(LocalPlayer player, InteractionHand hand, BlockHitResult hit,
            CallbackInfoReturnable<InteractionResult> cir)
    {
        easyplacefixplus$stamp();

        // Клик Easy Place ушёл: ответ сервера про руку ещё в пути — обмен в руку подождёт его
        try
        {
            if (cir.getReturnValue() != null && cir.getReturnValue().consumesAction()
                    && AutoPlaceState.isAutomated(com.aell.easyplacefixplus.config.FixConfigs.EP_AUTOMATION_WINDOW.getIntegerValue()))
            {
                com.aell.easyplacefixplus.core.InventorySync.onPlaceClick();
            }
        }
        catch (RuntimeException | LinkageError ignored)
        {
        }
    }

    /**
     * Клик по инвентарю игрока (обмен Litematica, автопополнение Inventory Profiles Next…): пока сервер его не
     * подтвердил, установки Easy Place ждут ({@code InventorySync}).
     */
    @Inject(method = "handleInventoryMouseClick", at = @At("HEAD"))
    private void easyplacefixplus$inventoryClick(int containerId, int slot, int button,
            net.minecraft.world.inventory.ClickType input, Player player, org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci)
    {
        if (player != null && player.inventoryMenu != null && containerId == player.inventoryMenu.containerId)
        {
            com.aell.easyplacefixplus.core.InventorySync.onInventoryClick();
        }
    }

    @Inject(method = "useItem", at = @At("RETURN"))
    private void easyplacefixplus$stampOnUse(Player player, InteractionHand hand,
            CallbackInfoReturnable<InteractionResult> cir)
    {
        easyplacefixplus$stamp();
    }

    @Unique
    private static void easyplacefixplus$stamp()
    {
        try
        {
            AutoPlaceState.stampSequence();
        }
        catch (RuntimeException | LinkageError e)
        {
            // Номер — только уточнение поверх окна: не прочитался, значит работаем по окну
        }
    }

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void easyplacefixplus$guard(LocalPlayer player, InteractionHand hand, BlockHitResult hit,
            CallbackInfoReturnable<InteractionResult> cir)
    {
        if (easyplacefixplus$reentrant)
        {
            return;
        }

        // «Защита от установки мимо схемы»: любой блок, ручной тоже, — только туда, где схема его ждёт
        try
        {
            if (com.aell.easyplacefixplus.core.StrictGuard.blocks(player, hand, hit))
            {
                cir.setReturnValue(InteractionResult.FAIL);
                return;
            }
        }
        catch (RuntimeException | LinkageError ignored)
        {
            // Сбой проверки не должен ронять клик
        }

        PlacementGuard.Decision decision;

        try
        {
            decision = PlacementGuard.decide(player, hand, hit);
        }
        catch (RuntimeException | LinkageError e)
        {
            return;
        }

        switch (decision)
        {
            case PlacementGuard.Decision.Pass ignored ->
            {
            }
            case PlacementGuard.Decision.Cancel ignored -> cir.setReturnValue(InteractionResult.FAIL);
            case PlacementGuard.Decision.Fix fix ->
            {
                easyplacefixplus$reentrant = true;

                try
                {
                    boolean done = StateFixer.run(Minecraft.getInstance(), player, fix.pos(),
                            fix.schematic(), fix.world(), fix.face());
                    cir.setReturnValue(done ? InteractionResult.SUCCESS : InteractionResult.FAIL);
                }
                finally
                {
                    easyplacefixplus$reentrant = false;
                }
            }
            case PlacementGuard.Decision.Water water ->
            {
                easyplacefixplus$reentrant = true;

                try
                {
                    boolean done = WaterPlace.place(Minecraft.getInstance(), player, hand,
                            water.pos(), water.pose(), water.world());
                    cir.setReturnValue(done ? InteractionResult.SUCCESS : InteractionResult.FAIL);
                }
                finally
                {
                    easyplacefixplus$reentrant = false;
                }
            }
            case PlacementGuard.Decision.Click click ->
            {
                BlockHitResult target = click.hit() == null ? hit : click.hit();

                // Наблюдатель, поршень, раздатчик, бочка: сервер считает их лицо по голове
                // игрока, а голова догоняет присланный угол только в серверном тике. Такой
                // клик ждёт, пока угол проживёт на сервере тик, — иначе блок встаёт по
                // настоящему взгляду игрока.
                boolean head = click.pose() != null
                        && com.aell.easyplacefixplus.core.PoseAhead.headMatters(player, hand, target, click.pose());

                // «Поза на тик вперёд»: нужного угла у сервера ещё нет, и он уедет ближайшим
                // ванильным пакетом движения. В этом тике не кликаем и клетку не метим —
                // клика не было; следующим тиком клик уйдёт без своего пакета поворота
                if (click.pose() != null
                        && com.aell.easyplacefixplus.core.PoseAhead.deferring(player, click.pose(), head))
                {
                    com.aell.easyplacefixplus.core.EpDebug.say(head
                            ? "жду, пока угол проживёт на сервере тик: блок встаёт по голове"
                            : "жду свой пакет движения с углом");
                    cir.setReturnValue(InteractionResult.FAIL);

                    return;
                }

                if (click.markPos() != null && click.markState() != null)
                {
                    AutoPlaceState.markClicked(click.markPos(), click.markState());
                }

                if (! click.rewrites())
                {
                    return;
                }

                MultiPlayerGameMode self = (MultiPlayerGameMode) (Object) this;
                LookSpoof.Pose pose = click.pose() == null
                        ? LookSpoof.Pose.of(player)
                        : click.pose();

                easyplacefixplus$reentrant = true;

                try
                {
                    InteractionResult result = com.aell.easyplacefixplus.core.PoseAhead.applied(
                            player, pose, () -> self.useItemOn(player, hand, target));

                    com.aell.easyplacefixplus.core.EpDebug.say("переписанный клик → " + result);
                    cir.setReturnValue(result);
                }
                finally
                {
                    easyplacefixplus$reentrant = false;
                }
            }
        }
    }

    /**
     * Левый клик рядом с Easy Place: «Не копать лишнего» отменяет удар по блоку вне схемы
     * и по блоку, уже стоящему как в схеме; «Брать инструмент для копания» перед началом
     * копания берёт в руку лучший инструмент. Оба помощника сами проверяют свои тумблеры.
     */
    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void easyplacefixplus$guardDigStart(BlockPos pos, Direction face, CallbackInfoReturnable<Boolean> cir)
    {
        try
        {
            if (DigGuard.blocks(pos))
            {
                cir.setReturnValue(false);

                return;
            }

            ToolPick.before(pos);
        }
        catch (RuntimeException | LinkageError e)
        {
            // Помощники копания — надстройка: сломались — копаем как обычно
        }
    }

    @Inject(method = "continueDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void easyplacefixplus$guardDigContinue(BlockPos pos, Direction face, CallbackInfoReturnable<Boolean> cir)
    {
        easyplacefixplus$guardDig(pos, cir);
    }

    /** Мгновенная ломка в креативе идёт сюда, минуя {@code startDestroyBlock}. */
    @Inject(method = "destroyBlock", at = @At("HEAD"), cancellable = true)
    private void easyplacefixplus$guardDigInstant(BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        easyplacefixplus$guardDig(pos, cir);
    }

    @Unique
    private static void easyplacefixplus$guardDig(BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        try
        {
            if (DigGuard.blocks(pos))
            {
                cir.setReturnValue(false);
            }
        }
        catch (RuntimeException | LinkageError e)
        {
            // см. выше
        }
    }
}
