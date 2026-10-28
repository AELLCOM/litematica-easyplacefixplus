package com.aell.easyplacefixplus.compat;

import com.aell.easyplacefixplus.config.GuiFixConfigs;
import com.aell.easyplacefixplus.shaded.ui.Nav;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuImpl implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return screen ->
        {
            Nav.clear();
            return GuiFixConfigs.create(screen, "easyplacefixplus.gui.parent.modmenu");
        };
    }
}
