package com.aell.easyplacefixplus.core;

import com.aell.easyplacefixplus.config.FixConfigs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class EasyPlaceModule
{
    private EasyPlaceModule() {}

    public static boolean isActive()
    {
        return FixConfigs.EP_ENABLED.getBooleanValue();
    }
}
