package com.aell.easyplacefixplus.mixin;

import com.aell.easyplacefixplus.config.FixSettings;
import com.aell.easyplacefixplus.config.GuiFixConfigs;
import com.aell.easyplacefixplus.shaded.ui.LitematicaSlots;
import com.aell.easyplacefixplus.shaded.ui.Strings;
import fi.dy.masa.litematica.gui.GuiRenderLayer;
import fi.dy.masa.malilib.gui.GuiBase;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GuiRenderLayer.class)
public abstract class MixinLitematicaRenderLayerScreen
{
    private static final String[] SLOT_ORDER = { "layerlist", "zonefocus", "ghostalpha", "easyplacefixplus" };

    @Inject(method = "initGui", at = @At("TAIL"), require = 0)
    private void easyplacefixplus$addModButton(CallbackInfo ci)
    {
        GuiBase screen = (GuiBase) (Object) this;

        screen.addButton(LitematicaSlots.addonTabButton(screen,
                Strings.plain("easyplacefixplus.gui.litematica_button"),
                FixSettings.ICON,
                LitematicaSlots.hasAllTab(screen), SLOT_ORDER, "easyplacefixplus"),
                (b, mouseButton) -> GuiFixConfigs.open(screen,
                        "easyplacefixplus.gui.parent.litematica"));
    }
}
