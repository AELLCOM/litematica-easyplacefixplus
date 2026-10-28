package com.aell.easyplacefixplus;

import com.aell.easyplacefixplus.config.FixConfigs;
import com.aell.easyplacefixplus.config.FixSettings;
import com.aell.easyplacefixplus.config.GuiFixConfigs;
import com.aell.easyplacefixplus.core.AutoPlaceState;
import com.aell.easyplacefixplus.core.ContainerClick;
import com.aell.easyplacefixplus.core.DeferredPlace;
import com.aell.easyplacefixplus.core.FollowUp;
import com.aell.easyplacefixplus.core.ForeignSuppressor;
import com.aell.easyplacefixplus.core.PoseAhead;
import com.aell.easyplacefixplus.shaded.ui.ModSwitcher;
import com.aell.easyplacefixplus.shaded.ui.Nav;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.util.InfoUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

@Environment(EnvType.CLIENT)
public class EasyPlaceClient implements ClientModInitializer, IKeybindProvider, IHotkeyCallback
{
    @Override
    public void onInitializeClient()
    {
        FixConfigs config = new FixConfigs();
        config.load();
        ConfigManager.getInstance().registerConfigHandler(FixSettings.MOD_ID, config);
        // Ванильный стиль: своя настройка или тема Material HUD, если он рядом
        com.aell.easyplacefixplus.shaded.ui.Style.levelWhen(FixConfigs.UI_STYLE::getIntegerValue, true);
        DevScreenshots.register();

        InputEventHandler.getKeybindManager().registerKeybindProvider(this);

        for (ConfigHotkey hotkey : FixConfigs.HOTKEYS)
        {
            hotkey.getKeybind().setCallback(this);
        }

        ModSwitcher.register(FixSettings.MOD_ID, FixSettings.MOD_NAME,
                () -> GuiFixConfigs.create(null, null));

        ClientTickEvents.END_CLIENT_TICK.register(client ->
        {
            ForeignSuppressor.onClientTick(client);
            AutoPlaceState.onClientTick(client);
            PoseAhead.onClientTick(client);
            ContainerClick.onClientTick(client);
            DeferredPlace.onClientTick(client);
            FollowUp.onClientTick(client);
            com.aell.easyplacefixplus.core.SoilDig.onClientTick(client);

            if (com.aell.easyplacefixplus.core.GhostSupport.HERE)
            {
                com.aell.easyplacefixplus.core.GhostSupport.onClientTick(client);
            }
        });

        FixConfigs.GHOST_HIDE_UNSUPPORTED.setValueChangeCallback(changed -> {
            try
            {
                fi.dy.masa.litematica.util.SchematicWorldRefresher.INSTANCE.updateAll();
            }
            catch (RuntimeException | LinkageError ignored)
            {
            }
        });
        FixConfigs.GHOST_SUPPORT_FROM_LAYERS.setValueChangeCallback(
                changed -> com.aell.easyplacefixplus.core.GhostSupport.markDirty());
    }

    @Override
    public void addKeysToMap(IKeybindManager manager)
    {
        for (ConfigHotkey hotkey : FixConfigs.HOTKEYS)
        {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager)
    {
        manager.addHotkeysForCategory(FixSettings.MOD_ID, FixSettings.MOD_NAME, FixConfigs.HOTKEYS);
    }

    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key)
    {
        if (key == FixConfigs.OPEN_SETTINGS.getKeybind())
        {
            Nav.clear();
            GuiBase.openGui(GuiFixConfigs.create(null, null));

            return true;
        }

        if (key == FixConfigs.TOGGLE_FIX.getKeybind())
        {
            boolean enabled = ! FixConfigs.EP_ENABLED.getBooleanValue();
            FixConfigs.EP_ENABLED.setBooleanValue(enabled);
            new FixConfigs().save();

            InfoUtils.printActionbarMessage(enabled
                    ? "easyplacefixplus.message.fix_on"
                    : "easyplacefixplus.message.fix_off");

            return true;
        }

        if (key == FixConfigs.TOGGLE_STRICT_SCHEMATIC.getKeybind())
        {
            return toggle(FixConfigs.EP_STRICT_SCHEMATIC, "easyplacefixplus.message.strict_schematic");
        }

        if (key == FixConfigs.TOGGLE_DIG_GUARD.getKeybind())
        {
            return toggle(FixConfigs.EP_DIG_GUARD, "easyplacefixplus.message.dig_guard");
        }

        return false;
    }

    private static boolean toggle(fi.dy.masa.malilib.config.options.ConfigBoolean option, String message)
    {
        boolean on = ! option.getBooleanValue();
        option.setBooleanValue(on);
        new FixConfigs().save();
        InfoUtils.printActionbarMessage(message + (on ? "_on" : "_off"));

        return true;
    }
}
