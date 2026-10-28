package com.aell.easyplacefixplus.mixin.easyplace;

import com.aell.easyplacefixplus.config.FixConfigs;
import com.aell.easyplacefixplus.core.AutoPlaceState;
import com.aell.easyplacefixplus.core.ContainerClick;
import com.aell.easyplacefixplus.core.CountedProgress;
import com.aell.easyplacefixplus.core.EasyPlaceModule;
import com.aell.easyplacefixplus.core.EntityPlace;
import com.aell.easyplacefixplus.core.SchematicTarget;
import com.aell.easyplacefixplus.core.SoilDig;
import com.aell.easyplacefixplus.core.StateRepair;
import fi.dy.masa.litematica.util.EasyPlaceUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(value = EasyPlaceUtils.class, remap = false)
public abstract class MixinEasyPlaceUtils
{
    @Inject(method = "handleEasyPlace", at = @At("HEAD"), cancellable = true, require = 0)
    private static void easyplacefixplus$enter(CallbackInfoReturnable<InteractionResult> cir)
    {
        // Ручной клик по сундуку: PASS здесь — единственный ответ, который
        // handleEasyPlaceWithMessage не превращает в отмену клика
        if (ContainerClick.shouldPass())
        {
            cir.setReturnValue(InteractionResult.PASS);

            return;
        }

        // Модуль выключен (свой тумблер или standalone-мод рядом) — в обработчик не заходим:
        // иначе inHandler() поднимается и прицельные миксины начинают править чужую установку
        if (! EasyPlaceModule.isActive())
        {
            return;
        }

        AutoPlaceState.enter();

        boolean repaired;

        try
        {
            // Голограмма сущности под прицелом (стойка, подушка, рамка…) — раньше блоков:
            // трассировка Litematica сущностей схемы не видит вовсе
            repaired = EntityPlace.run() || StateRepair.run() || SoilDig.run();
        }
        catch (RuntimeException | LinkageError e)
        {
            return;
        }

        if (repaired)
        {
            AutoPlaceState.exit();
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Inject(method = "handleEasyPlace", at = @At("RETURN"), require = 0)
    private static void easyplacefixplus$exit(CallbackInfoReturnable<InteractionResult> cir)
    {
        AutoPlaceState.exit();
        com.aell.easyplacefixplus.core.EpDebug.say("handleEasyPlace → " + cir.getReturnValue());
    }

    @Inject(method = "easyPlaceIsPositionCached", at = @At("RETURN"), cancellable = true, require = 0)
    private static void easyplacefixplus$letCountedGrow(BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        if (EasyPlaceModule.isActive() && Boolean.TRUE.equals(cir.getReturnValue())
                && (CountedProgress.stillGrowing(pos) || StateRepair.needsFix(pos)
                        || SchematicTarget.needsClick(pos)))
        {
            cir.setReturnValue(false);
        }
    }

    /**
     * «Ограничение размещения» — глушитель, независимый от Easy Place: гасит клик при любом
     * непустом предмете в руке, если наведённая клетка пуста в схеме и лежит рядом с регионом.
     * По сундуку это срабатывает всегда, поэтому ручной клик по контейнеру снимаем и здесь.
     *
     * Цель — внутренний {@code placementRestrictionInEffect}, а не обёртка
     * {@code handlePlacementRestriction}: {@code handleEasyPlace} в двух местах зовёт
     * внутренний метод напрямую (смещения 70 и 108 в байткоде 0.28.4) — как раз на
     * ванильном попадании, то есть на прицеле в сундук. Инжект в обёртку эти вызовы
     * не накрывал.
     */
    @Inject(method = "placementRestrictionInEffect()Z", at = @At("HEAD"), cancellable = true,
            require = 0)
    private static void easyplacefixplus$letContainerThrough(CallbackInfoReturnable<Boolean> cir)
    {
        if (ContainerClick.shouldPass())
        {
            cir.setReturnValue(false);
        }
    }

    // placementRestrictionInEffect(Minecraft) в 0.26 есть только у WorldUtils (MixinWorldUtils): в EasyPlaceUtils
    // такой перегрузки нет, а неверный дескриптор Mixin считает ошибкой даже при require = 0 — и не применяет
    // весь этот миксин, отчего EasyPlaceUtils не загружается вовсе


    // 1.21.11 (Litematica 0.26): applyPlacementProtocolAll и easyPlaceBlockChecksCancel старого пути — в WorldUtils (MixinWorldUtils)

}
