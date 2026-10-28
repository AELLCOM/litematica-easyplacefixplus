package com.aell.easyplacefixplus;

import com.aell.easyplacefixplus.config.FixConfigs;
import com.aell.easyplacefixplus.config.GuiFixConfigs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;

/**
 * Снимки окна настроек в двух стилях для проверки без рук ({@code EPF_SHOTS=1}): Aell UI, затем ванильный
 * (22.10.2026). Без переменной окружения ничего не делает. Окно открывается прямо с титульного экрана — мир не нужен;
 * после снимков стиль возвращается как был, клиент закрывается.
 */
@Environment(EnvType.CLIENT)
public final class DevScreenshots
{
    private static final int START_TICKS = 80;
    private static final int STEP_TICKS = 20;

    private static int ticks;
    private static int step;
    private static int before;

    private DevScreenshots() {}

    public static void register()
    {
        if ("1".equals(System.getenv("EPF_SHOTS")))
        {
            ClientTickEvents.END_CLIENT_TICK.register(DevScreenshots::tick);
        }
    }

    private static void tick(Minecraft mc)
    {
        if (++ticks < START_TICKS || (ticks - START_TICKS) % STEP_TICKS != 0)
        {
            return;
        }

        switch (step++)
        {
            case 0 ->
            {
                before = FixConfigs.UI_STYLE.getIntegerValue();
                FixConfigs.UI_STYLE.setIntegerValue(0);
                mc.gui.setScreen(GuiFixConfigs.create(null, null));
            }
            case 1 -> shot(mc, "settings-aell");
            case 2 -> FixConfigs.UI_STYLE.setIntegerValue(1);
            case 3 -> shot(mc, "settings-vanilla");
            case 4 -> FixConfigs.UI_STYLE.setIntegerValue(2);
            case 5 -> shot(mc, "settings-vanillaplus");
            case 6 ->
            {
                FixConfigs.UI_STYLE.setIntegerValue(before);
                mc.stop();
            }
            default -> {}
        }
    }

    private static void shot(Minecraft mc, String name)
    {
        Screenshot.grab(mc, false);
        System.out.println("[easyplacefixplus] screenshot: " + name);
    }
}
