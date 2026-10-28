package com.aell.easyplacefixplus.config;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigResettable;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class FixDefaults
{
    private FixDefaults() {}

    public static void resetEverything()
    {
        for (IConfigBase config : FixConfigs.OPTIONS)
        {
            if (config instanceof IConfigResettable resettable)
            {
                resettable.resetToDefault();
            }
        }

        for (ConfigHotkey hotkey : FixConfigs.HOTKEYS)
        {
            hotkey.resetToDefault();
        }

        new FixConfigs().save();
    }
}
