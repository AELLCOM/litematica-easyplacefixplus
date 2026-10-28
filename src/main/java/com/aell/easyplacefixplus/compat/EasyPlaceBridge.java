package com.aell.easyplacefixplus.compat;

import com.aell.easyplacefixplus.config.FixConfigs;
import fi.dy.masa.malilib.config.IConfigBase;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.Nullable;

/**
 * Мост для Litematica Material HUD: внутри него живёт тот же модуль Easy Place с теми же
 * опциями под теми же именами. Пока стоит этот мод, движок ведём мы, а экран Material HUD
 * правит наши же объекты опций — значения у обоих модов одни.
 *
 * <p>Имя класса и подписи методов — контракт между модами: Material HUD находит их рефлексией
 * (см. {@code com.aell.ui.SharedOptions}), переименование разведёт настройки на два набора.
 *
 * <p>Отдаём только {@link FixConfigs#EP_OPTIONS} и клавиши-переключатели {@link FixConfigs#EP_HOTKEYS}:
 * «сообщения в чат» у обоих модов называются
 * одинаково, но отвечают за разное, и переезжать между модами не должны.
 */
@Environment(EnvType.CLIENT)
public final class EasyPlaceBridge
{
    private EasyPlaceBridge() {}

    public static @Nullable IConfigBase option(String name)
    {
        for (IConfigBase config : FixConfigs.EP_OPTIONS)
        {
            if (config.getName().equals(name))
            {
                return config;
            }
        }

        for (IConfigBase config : FixConfigs.EP_HOTKEYS)
        {
            if (config.getName().equals(name))
            {
                return config;
            }
        }

        return null;
    }

    /** Только запись своего файла: ничего у соседа не зовём, чтобы сохранение не зацикливалось. */
    public static void saveOwn()
    {
        new FixConfigs().save();
    }
}
