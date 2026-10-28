package com.aell.easyplacefixplus.config;

import com.aell.easyplacefixplus.shaded.ui.Model;
import com.aell.easyplacefixplus.shaded.ui.Num;
import com.aell.easyplacefixplus.shaded.ui.SettingsModel;
import com.aell.easyplacefixplus.shaded.ui.SharedOptions;
import com.aell.easyplacefixplus.shaded.ui.Strings;
import com.aell.easyplacefixplus.shaded.ui.Ui;
import com.aell.easyplacefixplus.shaded.ui.Values;
import fi.dy.masa.litematica.config.Configs;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public final class FixSettings implements SettingsModel
{
    /**
     * Модуль Easy Place внутри Litematica Material HUD: те же опции под теми же именами. Пока
     * стоит этот мод, движок ведём мы, но настраивать человек может из обоих экранов, поэтому
     * значения общие, а его файл на диске держим в согласии со своим.
     */
    private static final SharedOptions HUD_SHARED =
            SharedOptions.of("com.aell.layerlist.compat.EasyPlaceBridge");

    public static final String MOD_ID = "easyplacefixplus";
    public static final String MOD_NAME = "Litematica EasyPlaceFixPlus";
    public static final String MALI_MOD_NAME = "Litematica EasyPlaceFixPlus";

    public static final Identifier ICON =
            Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/mod_icon.png");

    private static final FixSettings INSTANCE = new FixSettings();

    private @Nullable List<Model.Tab> tabs;

    private FixSettings() {}

    public static FixSettings getInstance()
    {
        return INSTANCE;
    }

    @Override
    public String modName()
    {
        return MOD_NAME;
    }

    @Override
    public String modId()
    {
        return MOD_ID;
    }

    @Override
    public String maliModName()
    {
        return MALI_MOD_NAME;
    }

    @Override
    public Identifier icon()
    {
        return ICON;
    }

    @Override
    public List<Model.Tab> tabs()
    {
        if (this.tabs == null)
        {
            this.tabs = buildTabs();
        }

        return this.tabs;
    }

    @Override
    public void save()
    {
        new FixConfigs().save();

        // Значения ведём мы, но у Material HUD те же опции: пусть его файл не отстаёт, иначе
        // после снятия этого мода там всплывут настройки, которых человек уже не помнит
        HUD_SHARED.push(FixConfigs.EP_OPTIONS);
        HUD_SHARED.saveSibling();
    }

    @Override
    public void resetAll()
    {
        FixDefaults.resetEverything();
    }

    /**
     * Без Easy Place самой Litematica ставить нечего: мод правит её клики, а не заменяет их.
     * Об этом стоит сказать прямо в шапке, иначе выключенный там режим читается как «мод сломан».
     */
    @Override
    public @Nullable Model.Status status()
    {
        if (! Configs.Generic.EASY_PLACE_MODE.getBooleanValue())
        {
            return new Model.Status(text("status.litematica_off"), Ui.C_WARN, null, null);
        }

        return null;
    }

    /** Акцент раздела стиля — тёплый золотой, как в Material HUD (22.10.2026). */
    private static final int UI_STYLE_ACCENT = 0xFFE0A030;

    private static List<Model.Tab> buildTabs()
    {
        List<Model.Tab> tabs = new ArrayList<>();

        tabs.add(new Model.Tab("easyplacefixplus.gui.tab_easy_place").add(
                // Стиль окна: отдельный золотой акцент, как в Material HUD
                new Model.Section("easyplacefixplus.section.ui_style")
                        .note("easyplacefixplus.section.ui_style.note")
                        .accent(UI_STYLE_ACCENT)
                        .add(Model.Option.choice("uiStyle", Values.choice(FixConfigs.UI_STYLE,
                                com.aell.easyplacefixplus.shaded.ui.Style.LEVEL_KEYS))),
                new Model.Section("easyplacefixplus.section.easy_place_fixes")
                        .note("easyplacefixplus.section.easy_place_fixes"
                                + (HUD_SHARED.present() ? ".note_shared" : ".note"))
                        .add(
                        Model.Option.toggle("easyPlaceFix", Values.of(FixConfigs.EP_ENABLED)),
                        Model.Option.toggle("easyPlaceSkipWrongState",
                                        Values.of(FixConfigs.EP_SKIP_WRONG))
                                .child().gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceOnlySchematic",
                                        Values.of(FixConfigs.EP_ONLY_SCHEMATIC))
                                .child().gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceGiveUpRefused",
                                        Values.of(FixConfigs.EP_GIVE_UP_REFUSED))
                                .child().gate(FixSettings::easyPlaceGate),
                        // Железные защиты — свои, от тумблера исправлений не зависят
                        Model.Option.toggle("easyPlaceStrictSchematic", Values.of(FixConfigs.EP_STRICT_SCHEMATIC))
                                .swatch(() -> 0xFFE0902E),
                        Model.Option.toggle("easyPlaceStrictBlock", Values.of(FixConfigs.EP_STRICT_BLOCK))
                                .swatch(() -> 0xFFE0902E)),
                new Model.Section("easyplacefixplus.section.easy_place_tools")
                        .note("easyplacefixplus.section.easy_place_tools.note").add(
                        Model.Option.toggle("easyPlaceFixLit", Values.of(FixConfigs.EP_FIX_LIT))
                                .gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceFixWaterlogged",
                                        Values.of(FixConfigs.EP_FIX_WATERLOGGED))
                                .gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceStripLogs", Values.of(FixConfigs.EP_STRIP_LOGS))
                                .gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceFinishTools", Values.of(FixConfigs.EP_FINISH_TOOLS))
                                .gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceFixFilling", Values.of(FixConfigs.EP_FIX_FILLING))
                                .gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceReplaceSoil", Values.of(FixConfigs.EP_REPLACE_SOIL))
                                .gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceDirtForGrass", Values.of(FixConfigs.EP_DIRT_FOR_GRASS))
                                .gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceEntities", Values.of(FixConfigs.EP_ENTITIES))
                                .gate(FixSettings::easyPlaceGate)),
                new Model.Section("easyplacefixplus.section.easy_place_digging")
                        .note("easyplacefixplus.section.easy_place_digging.note").add(
                        Model.Option.toggle("easyPlaceDigGuard", Values.of(FixConfigs.EP_DIG_GUARD))
                                .gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceDigGuardOutside", Values.of(FixConfigs.EP_DIG_GUARD_OUTSIDE))
                                .child().gate(FixSettings::digGuardGate),
                        Model.Option.toggle("easyPlaceDigGuardCorrect", Values.of(FixConfigs.EP_DIG_GUARD_CORRECT))
                                .child().gate(FixSettings::digGuardGate),
                        Model.Option.toggle("easyPlaceDigGuardEasyPlaceOnly", Values.of(FixConfigs.EP_DIG_GUARD_EP_ONLY))
                                .child().gate(FixSettings::digGuardGate),
                        Model.Option.toggle("easyPlaceAutoTool", Values.of(FixConfigs.EP_AUTO_TOOL))
                                .gate(FixSettings::easyPlaceGate)),
                new Model.Section("easyplacefixplus.section.ghost_support")
                        .note("easyplacefixplus.section.ghost_support.note").add(
                        Model.Option.toggle("ghostHideUnsupported", Values.of(FixConfigs.GHOST_HIDE_UNSUPPORTED))
                                .gate(FixSettings::hudGate),
                        Model.Option.toggle("ghostSupportFromLayers", Values.of(FixConfigs.GHOST_SUPPORT_FROM_LAYERS))
                                .gate(FixSettings::hudGate)),
                new Model.Section("easyplacefixplus.section.placement").add(
                        Model.Option.toggle("easyPlaceOpenContainers",
                                        Values.of(FixConfigs.EP_OPEN_CONTAINERS))
                                .gate(FixSettings::easyPlaceGate)),
                new Model.Section("easyplacefixplus.section.easy_place_server")
                        .note("easyplacefixplus.section.easy_place_server.note").add(
                        Model.Option.integer("easyPlaceBlocksPerSecond",
                                        Values.of(FixConfigs.EP_PLACE_RATE), Num.Scale.LINEAR)
                                .gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceJitter", Values.of(FixConfigs.EP_JITTER))
                                .gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceNoTurn", Values.of(FixConfigs.EP_NO_TURN))
                                .gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceVanillaServer",
                                        Values.of(FixConfigs.EP_PLAIN_HITS))
                                .gate(FixSettings::easyPlaceGate)),
                new Model.Section("easyplacefixplus.section.easy_place_timings").add(
                        Model.Option.integer("easyPlaceConfirmWindow",
                                        Values.of(FixConfigs.EP_CONFIRM_WINDOW), Num.Scale.LINEAR)
                                .gate(FixSettings::easyPlaceGate),
                        Model.Option.integer("easyPlaceAutomationWindow",
                                        Values.of(FixConfigs.EP_AUTOMATION_WINDOW), Num.Scale.LINEAR)
                                .gate(FixSettings::easyPlaceGate),
                        Model.Option.toggle("easyPlaceDebug", Values.of(FixConfigs.EP_DEBUG))
                                .gate(FixSettings::easyPlaceGate))));

        tabs.add(new Model.Tab("easyplacefixplus.gui.tab_hotkeys").add(
                new Model.Section("easyplacefixplus.section.keys")
                        .note("easyplacefixplus.section.keys.note").add(
                        key("toggleFix", FixConfigs.TOGGLE_FIX),
                        key("toggleStrictSchematic", FixConfigs.TOGGLE_STRICT_SCHEMATIC)
                                .swatch(() -> 0xFFE0902E),
                        key("toggleDigGuard", FixConfigs.TOGGLE_DIG_GUARD),
                        key("openSettings", FixConfigs.OPEN_SETTINGS)),
                new Model.Section("easyplacefixplus.section.messages").add(
                        Model.Option.toggle("chatMessages", Values.of(FixConfigs.CHAT_MESSAGES)))));

        return tabs;
    }

    private static Model.Option key(String name, ConfigHotkey config)
    {
        return Model.Option.keybind(name, Values.of(config), Values.keybind(config));
    }

    private static @Nullable Model.Reason easyPlaceGate()
    {
        return FixConfigs.EP_ENABLED.getBooleanValue() ? null
                : new Model.Reason(text("gate.easy_place_off"), false);
    }

    private static @Nullable Model.Reason digGuardGate()
    {
        Model.Reason off = easyPlaceGate();

        return off != null || FixConfigs.EP_DIG_GUARD.getBooleanValue() ? off
                : new Model.Reason(text("gate.dig_guard_off"), false);
    }

    /** С Material HUD голограммами и опорой ведает он — тумблеры на его вкладке «Подсветка». */
    private static @Nullable Model.Reason hudGate()
    {
        return com.aell.easyplacefixplus.core.GhostSupport.HERE ? null
                : new Model.Reason(text("gate.material_hud"), false);
    }

    private static String text(String suffix)
    {
        return Strings.plain(MOD_ID + ".ui." + suffix);
    }
}
