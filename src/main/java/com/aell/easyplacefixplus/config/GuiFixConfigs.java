package com.aell.easyplacefixplus.config;

import com.aell.easyplacefixplus.shaded.ui.Nav;
import com.aell.easyplacefixplus.shaded.ui.SettingsScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class GuiFixConfigs extends SettingsScreen
{
    private GuiFixConfigs()
    {
        super(FixSettings.getInstance());
    }

    public static GuiFixConfigs create(@Nullable Screen parent, @Nullable String backLabelKey)
    {
        GuiFixConfigs screen = new GuiFixConfigs();
        screen.setParent(parent);
        screen.backLabel(backLabelKey);

        return screen;
    }

    public static void open(@Nullable Screen parent, @Nullable String backLabelKey)
    {
        Nav.open(create(parent, backLabelKey), parent);
    }
}
