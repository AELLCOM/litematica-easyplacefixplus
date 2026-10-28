package com.aell.easyplacefixplus.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.data.json.JsonUtils;
import java.io.File;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public class FixConfigs implements IConfigHandler
{
    private static final String CONFIG_FILE_NAME = "easyplacefixplus.json";

    /** Имя конфига до переименования мода: у кого стоял тот билд, настройки переезжают оттуда. */
    private static final String LEGACY_FILE_NAME = "easyplaceplus.json";

    /** Конфиг Material HUD: оттуда переезжают настройки при первом запуске. */
    private static final String DONOR_FILE_NAME = "layerlist.json";

    /** 2 (1.0.19): сообщения в чат по умолчанию выключены — у тех, кто ставил мод раньше, сбрасываются один раз. */
    private static final int SETTINGS_REVISION = 2;

    public static final ConfigBoolean EP_ENABLED             = new ConfigBoolean("easyPlaceFix", true);
    /**
     * Оформление окна настроек (22.10.2026): 0 — Aell UI (по умолчанию; рядом с Material HUD — его оформление), 1 — Vanilla,
     * 2 — Vanilla+.
     * В {@link #EP_OPTIONS} не входит: у Material HUD своя настройка с тем же именем.
     */
    public static final ConfigInteger UI_STYLE               = new ConfigInteger("uiStyle", 0, 0, 2);
    public static final ConfigBoolean EP_FIX_LIT             = new ConfigBoolean("easyPlaceFixLit", true);
    public static final ConfigBoolean EP_FIX_WATERLOGGED     = new ConfigBoolean("easyPlaceFixWaterlogged", true);
    public static final ConfigBoolean EP_FIX_FILLING         = new ConfigBoolean("easyPlaceFixFilling", false);
    public static final ConfigBoolean EP_SKIP_WRONG          = new ConfigBoolean("easyPlaceSkipWrongState", true);
    public static final ConfigBoolean EP_ONLY_SCHEMATIC      = new ConfigBoolean("easyPlaceOnlySchematic", true);
    /** С 1.0.17: после шести отказов сервера по клетке перестать в неё кликать и сказать об этом в чат. */
    public static final ConfigBoolean EP_GIVE_UP_REFUSED     = new ConfigBoolean("easyPlaceGiveUpRefused", true);
    /** С 1.0.16: железная защита — никакой блок мимо схемы, ручной тоже (по умолчанию выкл). */
    public static final ConfigBoolean EP_STRICT_SCHEMATIC    = new ConfigBoolean("easyPlaceStrictSchematic", false);
    public static final ConfigBoolean EP_STRICT_BLOCK        = new ConfigBoolean("easyPlaceStrictBlock", false);
    public static final ConfigBoolean EP_OPEN_CONTAINERS     = new ConfigBoolean("easyPlaceOpenContainers", true);
    public static final ConfigBoolean EP_STRIP_LOGS          = new ConfigBoolean("easyPlaceStripLogs", true);
    public static final ConfigBoolean EP_FINISH_TOOLS        = new ConfigBoolean("easyPlaceFinishTools", true);
    public static final ConfigBoolean EP_PLAIN_HITS          = new ConfigBoolean("easyPlaceVanillaServer", false);
    public static final ConfigBoolean EP_NO_TURN             = new ConfigBoolean("easyPlaceNoTurn", false);
    public static final ConfigBoolean EP_POSE_AHEAD          = new ConfigBoolean("easyPlacePoseAhead", false);
    public static final ConfigBoolean EP_DEBUG               = new ConfigBoolean("easyPlaceDebug", false);
    public static final ConfigBoolean EP_JITTER              = new ConfigBoolean("easyPlaceJitter", false);
    public static final ConfigBoolean EP_DIG_GUARD           = new ConfigBoolean("easyPlaceDigGuard", false);
    /** Части «Не копать лишнего» (13.10.2026): вне схемы, правильно стоящие, только в режиме Easy Place. */
    public static final ConfigBoolean EP_DIG_GUARD_OUTSIDE   = new ConfigBoolean("easyPlaceDigGuardOutside", true);
    public static final ConfigBoolean EP_DIG_GUARD_CORRECT   = new ConfigBoolean("easyPlaceDigGuardCorrect", true);
    public static final ConfigBoolean EP_DIG_GUARD_EP_ONLY   = new ConfigBoolean("easyPlaceDigGuardEasyPlaceOnly", true);
    public static final ConfigBoolean EP_AUTO_TOOL           = new ConfigBoolean("easyPlaceAutoTool", false);
    public static final ConfigBoolean EP_REPLACE_SOIL        = new ConfigBoolean("easyPlaceReplaceSoil", false);
    public static final ConfigBoolean EP_DIRT_FOR_GRASS      = new ConfigBoolean("easyPlaceDirtForGrass", false);
    public static final ConfigBoolean EP_ENTITIES            = new ConfigBoolean("easyPlaceEntities", true);
    public static final ConfigBoolean CHAT_MESSAGES          = new ConfigBoolean("chatMessages", false);
    /** С 1.0.15: не рисовать голограммы блоков, которым в мире не на чем держаться (с Material HUD — его опция). */
    public static final ConfigBoolean GHOST_HIDE_UNSUPPORTED = new ConfigBoolean("ghostHideUnsupported", false);
    /** С 1.0.15: показывать и давать ставить опору с соседнего слоя (с Material HUD — его опция). */
    public static final ConfigBoolean GHOST_SUPPORT_FROM_LAYERS = new ConfigBoolean("ghostSupportFromLayers", false);

    public static final ConfigInteger EP_PLACE_RATE          = new ConfigInteger("easyPlaceBlocksPerSecond", 10, 1, 20);
    public static final ConfigInteger EP_CONFIRM_WINDOW      = new ConfigInteger("easyPlaceConfirmWindow", 300, 0, 2000);
    public static final ConfigInteger EP_AUTOMATION_WINDOW   = new ConfigInteger("easyPlaceAutomationWindow", 1000, 0, 5000);

    private static final KeybindSettings EMPTY_OK =
            KeybindSettings.create(KeybindSettings.Context.INGAME, KeyAction.PRESS,
                    false, true, false, true);

    public static final ConfigHotkey OPEN_SETTINGS = new ConfigHotkey("openSettings", "", EMPTY_OK);
    public static final ConfigHotkey TOGGLE_FIX = new ConfigHotkey("toggleFix", "", EMPTY_OK);
    /** С 1.0.18: переключатели защит — для тех, кто то строит по схеме, то рядом с ней. */
    public static final ConfigHotkey TOGGLE_STRICT_SCHEMATIC = new ConfigHotkey("toggleStrictSchematic", "", EMPTY_OK);
    public static final ConfigHotkey TOGGLE_DIG_GUARD = new ConfigHotkey("toggleDigGuard", "", EMPTY_OK);

    public static final List<IConfigBase> OPTIONS = ImmutableList.of(
            EP_ENABLED, EP_SKIP_WRONG, EP_ONLY_SCHEMATIC, EP_GIVE_UP_REFUSED, EP_STRICT_SCHEMATIC, EP_STRICT_BLOCK,
            EP_FIX_LIT, EP_FIX_WATERLOGGED, EP_FIX_FILLING,
            EP_STRIP_LOGS, EP_FINISH_TOOLS, EP_OPEN_CONTAINERS,
            EP_PLAIN_HITS, EP_NO_TURN, EP_POSE_AHEAD,
            EP_JITTER, EP_DIG_GUARD, EP_DIG_GUARD_OUTSIDE, EP_DIG_GUARD_CORRECT, EP_DIG_GUARD_EP_ONLY, EP_AUTO_TOOL, EP_REPLACE_SOIL, EP_DIRT_FOR_GRASS, EP_ENTITIES, EP_PLACE_RATE,
            EP_CONFIRM_WINDOW, EP_AUTOMATION_WINDOW,
            EP_DEBUG, CHAT_MESSAGES, GHOST_HIDE_UNSUPPORTED, GHOST_SUPPORT_FROM_LAYERS, UI_STYLE);

    /**
     * Общие с модулем Easy Place внутри Material HUD опции: имена совпадают дословно, и через
     * {@code compat.EasyPlaceBridge} этот список — единственное, что сосед видит и правит.
     * {@link #CHAT_MESSAGES} сюда не входит: у Material HUD под тем же именем живут ещё и
     * сообщения поиска по хранилищам.
     */
    public static final List<IConfigBase> EP_OPTIONS = ImmutableList.of(
            EP_ENABLED, EP_SKIP_WRONG, EP_ONLY_SCHEMATIC, EP_GIVE_UP_REFUSED, EP_STRICT_SCHEMATIC, EP_STRICT_BLOCK,
            EP_FIX_LIT, EP_FIX_WATERLOGGED, EP_FIX_FILLING,
            EP_STRIP_LOGS, EP_FINISH_TOOLS, EP_OPEN_CONTAINERS,
            EP_PLAIN_HITS, EP_NO_TURN, EP_POSE_AHEAD,
            EP_JITTER, EP_DIG_GUARD, EP_DIG_GUARD_OUTSIDE, EP_DIG_GUARD_CORRECT, EP_DIG_GUARD_EP_ONLY, EP_AUTO_TOOL, EP_REPLACE_SOIL, EP_DIRT_FOR_GRASS, EP_ENTITIES, EP_PLACE_RATE,
            EP_CONFIRM_WINDOW, EP_AUTOMATION_WINDOW, EP_DEBUG);

    public static final List<ConfigHotkey> HOTKEYS = ImmutableList.of(OPEN_SETTINGS, TOGGLE_FIX,
            TOGGLE_STRICT_SCHEMATIC, TOGGLE_DIG_GUARD);

    /**
     * Клавиши, общие с Material HUD (его вкладка «Клавиши» правит эти же объекты через
     * {@code compat.EasyPlaceBridge}): иначе при двух модах одно нажатие переключало бы опцию дважды.
     */
    public static final List<ConfigHotkey> EP_HOTKEYS = ImmutableList.of(TOGGLE_STRICT_SCHEMATIC, TOGGLE_DIG_GUARD);

    @Override
    public void load()
    {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), CONFIG_FILE_NAME);

        if (! configFile.exists() || ! configFile.isFile() || ! configFile.canRead())
        {
            if (! adoptLegacySettings())
            {
                adoptMaterialHudSettings();
            }

            // Перенесённые настройки — старше 1.0.19: чат включён по прежнему умолчанию
            CHAT_MESSAGES.resetToDefault();
            return;
        }

        JsonElement element = JsonUtils.parseJsonFile(configFile.toPath());

        if (element != null && element.isJsonObject())
        {
            JsonObject root = element.getAsJsonObject();
            ConfigUtils.readConfigBase(root, "Options", OPTIONS);
            migrateUiStyle(root);
            ConfigUtils.readConfigBase(root, "Hotkeys", HOTKEYS);

            // Один раз: «true» в файле — прежнее умолчание, а не выбор игрока; кто включит снова, у того останется
            if (JsonUtils.getIntegerOrDefault(root, "settings_revision", 1) < 2)
            {
                CHAT_MESSAGES.resetToDefault();
                this.save();
            }
        }
    }

    /**
     * Мод переименован, вместе с ним и файл конфига. Старый читаем целиком — вместе с
     * горячими клавишами, которых у донора Material HUD нет. Старый файл не трогаем и не
     * удаляем: при первом же сохранении рядом появится новый.
     *
     * @return true, если старый конфиг нашёлся и прочитался — тогда донор уже не нужен.
     */
    private static boolean adoptLegacySettings()
    {
        File legacy = new File(FabricLoader.getInstance().getConfigDir().toFile(), LEGACY_FILE_NAME);

        if (! legacy.exists() || ! legacy.isFile() || ! legacy.canRead())
        {
            return false;
        }

        JsonElement element = JsonUtils.parseJsonFile(legacy.toPath());

        if (element == null || ! element.isJsonObject())
        {
            return false;
        }

        JsonObject root = element.getAsJsonObject();
        ConfigUtils.readConfigBase(root, "Options", OPTIONS);
        ConfigUtils.readConfigBase(root, "Hotkeys", HOTKEYS);

        return true;
    }

    /**
     * Первый запуск: до выделения в отдельный мод те же настройки жили в Material HUD,
     * под теми же именами. Молча забираем их оттуда, иначе у человека, который крутил
     * темп установки и режимы для своего сервера, всё разом откатится на умолчания.
     * Читаем, но не пишем: Material HUD продолжает жить своей жизнью.
     */
    private static void adoptMaterialHudSettings()
    {
        File donor = new File(FabricLoader.getInstance().getConfigDir().toFile(), DONOR_FILE_NAME);

        if (! donor.exists() || ! donor.isFile() || ! donor.canRead())
        {
            return;
        }

        JsonElement element = JsonUtils.parseJsonFile(donor.toPath());

        if (element != null && element.isJsonObject())
        {
            // Имена опций совпадают, лишние ключи донора malilib просто не найдёт у нас.
            ConfigUtils.readConfigBase(element.getAsJsonObject(), "Options", OPTIONS);
        }
    }

    @Override
    public void save()
    {
        File dir = FabricLoader.getInstance().getConfigDir().toFile();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs())
        {
            JsonObject root = new JsonObject();
            ConfigUtils.writeConfigBase(root, "Options", OPTIONS);
            ConfigUtils.writeConfigBase(root, "Hotkeys", HOTKEYS);

            root.addProperty("settings_revision", SETTINGS_REVISION);

            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME).toPath());
        }
    }

    /** До 22.10.2026 стиль был галочкой {@code uiVanilla}: включённая становится «Vanilla». */
    private static void migrateUiStyle(JsonObject root)
    {
        JsonObject options = root.has("Options") && root.get("Options").isJsonObject() ? root.getAsJsonObject("Options") : null;

        if (options != null && ! options.has("uiStyle") && options.has("uiVanilla")
                && options.get("uiVanilla").isJsonPrimitive() && options.get("uiVanilla").getAsBoolean())
        {
            UI_STYLE.setIntegerValue(1);
        }
    }
}
