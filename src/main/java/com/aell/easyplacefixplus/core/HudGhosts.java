package com.aell.easyplacefixplus.core;

import java.lang.reflect.Method;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

/**
 * Голограммы, которые Material HUD сейчас не рисует («только блоки из инвентаря», «скрывать блоки без
 * опоры»). Их не видно, и доводка прицела проходит такую клетку насквозь, к видимой за ней. Без Material
 * HUD — никогда. Контракт — {@code com.aell.layerlist.compat.EasyPlaceBridge.ghostHidden(BlockPos)}.
 */
@Environment(EnvType.CLIENT)
public final class HudGhosts
{
    private static final String MOD_ID = "layerlist";
    private static final String BRIDGE = "com.aell.layerlist.compat.EasyPlaceBridge";
    private static final @Nullable Method HIDDEN = resolve();

    private HudGhosts() {}

    private static @Nullable Method resolve()
    {
        if (! FabricLoader.getInstance().isModLoaded(MOD_ID))
        {
            return null;
        }

        try
        {
            return Class.forName(BRIDGE).getMethod("ghostHidden", BlockPos.class);
        }
        catch (ReflectiveOperationException | LinkageError e)
        {
            return null;
        }
    }

    public static boolean hidden(BlockPos pos)
    {
        if (HIDDEN == null)
        {
            return false;
        }

        try
        {
            return Boolean.TRUE.equals(HIDDEN.invoke(null, pos));
        }
        catch (ReflectiveOperationException | RuntimeException | LinkageError e)
        {
            return false;
        }
    }
}
