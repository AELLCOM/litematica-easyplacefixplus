package com.aell.easyplacefixplus.mixin;

import com.aell.easyplacefixplus.core.AutoPlaceState;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevel
{
    /**
     * Подтверждение сервера по номеру пакета. Приходит независимо от того, удалась
     * установка или нет — {@code ackBlockChangesUpTo} стоит в серверном обработчике до
     * всех проверок, кроме {@code hasClientLoaded}. Отсюда и точный признак «сервер
     * отказал»: ack пришёл, а клетка не изменилась.
     */
    @Inject(method = "handleBlockChangedAck", at = @At("HEAD"), require = 0)
    private void easyplacefixplus$onBlockChangedAck(int sequence, CallbackInfo ci)
    {
        AutoPlaceState.onAck(sequence);
        com.aell.easyplacefixplus.core.InventorySync.onAck(sequence);
    }

    // Блок в мире сменился — опоры пересчитать; при «скрывать без опоры» пересобрать и соседний чанк
    @Inject(method = "sendBlockUpdated", at = @At("HEAD"), require = 0)
    private void easyplacefixplus$onBlockUpdated(net.minecraft.core.BlockPos pos,
            net.minecraft.world.level.block.state.BlockState previous, net.minecraft.world.level.block.state.BlockState current,
            int flags, CallbackInfo ci)
    {
        if (previous != current && com.aell.easyplacefixplus.core.GhostSupport.HERE)
        {
            com.aell.easyplacefixplus.core.GhostSupport.onBlockChanged(pos);
        }
    }
}
