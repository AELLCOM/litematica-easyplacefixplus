package com.aell.easyplacefixplus.mixin.easyplace;

import com.aell.easyplacefixplus.core.InventorySync;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerInventoryPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Обновления слотов инвентаря игрока — для {@link InventorySync}: после подтверждения установки они значат
 * «ответ сервера про руку дошёл». TAIL — уже на клиентском потоке: на сетевом обработчик бросает исключение
 * в {@code ensureRunningOnSameThread} раньше, чем дойдёт сюда.
 */
@Environment(EnvType.CLIENT)
@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListenerInventory
{
    @Inject(method = "handleContainerSetSlot", at = @At("TAIL"), require = 0)
    private void easyplacefixplus$slot(ClientboundContainerSetSlotPacket packet, CallbackInfo ci)
    {
        easyplacefixplus$update(packet.getContainerId());
    }

    @Inject(method = "handleContainerContent", at = @At("TAIL"), require = 0)
    private void easyplacefixplus$content(ClientboundContainerSetContentPacket packet, CallbackInfo ci)
    {
        easyplacefixplus$update(packet.containerId());
    }

    @Inject(method = "handleSetPlayerInventory", at = @At("TAIL"), require = 0)
    private void easyplacefixplus$inventory(ClientboundSetPlayerInventoryPacket packet, CallbackInfo ci)
    {
        InventorySync.onSlotUpdate();
    }

    @Unique
    private static void easyplacefixplus$update(int containerId)
    {
        var player = Minecraft.getInstance().player;

        if (player != null && containerId == player.inventoryMenu.containerId)
        {
            InventorySync.onSlotUpdate();
        }
    }
}
