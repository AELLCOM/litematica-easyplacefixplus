package com.aell.easyplacefixplus.mixin.ghost;

import com.aell.easyplacefixplus.core.GhostSupport;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import fi.dy.masa.litematica.render.schematic.BlockModelRendererSchematic;
import fi.dy.masa.litematica.render.schematic.ChunkCacheSchematic;
import fi.dy.masa.litematica.render.schematic.ChunkMeshDataSchematic;
import fi.dy.masa.litematica.render.schematic.ChunkRenderDataSchematic;
import fi.dy.masa.litematica.render.schematic.ChunkRenderDispatcherBuffers;
import fi.dy.masa.litematica.render.schematic.ChunkRendererSchematicVbo;
import fi.dy.masa.malilib.util.LayerRange;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.chunk.VisGraph;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = ChunkRendererSchematicVbo.class, remap = false)
public abstract class MixinChunkRendererSchematicVbo
{
    @Shadow
    protected ChunkCacheSchematic schematicWorldView;

    @Shadow
    protected ChunkCacheSchematic clientWorldView;

    @Shadow
    @Final
    protected BlockPos.MutableBlockPos position;

    // Litematica 0.26 (1.21.11): блок кладётся в буфер со сдвигом PoseStack, а не offset-аргументом, и
    // использованные слои собираются в два набора — так и зовём для опор вне слоя
    @Shadow
    protected abstract void renderBlocksAndOverlay(BlockPos pos, ChunkRenderDataSchematic data,
            ChunkMeshDataSchematic chunkMeshData, ChunkRenderDispatcherBuffers pack,
            Set<?> usedLayers, Set<?> usedRenderTypes, PoseStack poseStack, VisGraph visGraph);

    // «Скрывать блоки без опоры»: держаться не на чем — ни голограммы, ни оверлея. Рабочий поток сборки.
    @Inject(method = "renderBlocksAndOverlay", at = @At("HEAD"), cancellable = true, require = 0)
    private void easyplacefixplus$hideUnsupported(CallbackInfo ci, @Local(argsOnly = true) BlockPos pos)
    {
        if (GhostSupport.hideUnsupported() && GhostSupport.unsupported(this.schematicWorldView.getBlockState(pos),
                this.clientWorldView.getBlockState(pos), pos, this.clientWorldView, this.schematicWorldView))
        {
            GhostSupport.countCulled();
            ci.cancel();
        }
    }

    // «Опора с соседнего слоя»: чанк, который слой не задевает вовсе (слои по X/Z), Litematica не
    // собирает — а опора в нём может лежать
    @ModifyExpressionValue(method = "rebuildChunk",
            at = @At(value = "INVOKE",
                    target = "Lfi/dy/masa/malilib/util/LayerRange;intersectsBox(IIIIII)Z"),
            require = 0, allow = 1)
    private boolean easyplacefixplus$chunkHoldsSupport(boolean intersects)
    {
        return intersects || GhostSupport.cellsInChunk(this.position.getX() >> 4, this.position.getZ() >> 4).length > 0;
    }

    // Опоры вне слоя дорисовываются тем же путём, что и клетки слоя: модель, жидкость, оверлей,
    // и фильтр «без опоры» выше действует и на них. В 0.26 disableCache зовётся
    // на каждую коробку — встаём один раз, после всех коробок (второй обход списка — по слоям)
    @Inject(method = "rebuildChunk",
            at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;", ordinal = 1),
            require = 0, allow = 1)
    private void easyplacefixplus$renderSupportCells(CallbackInfo ci,
            @Local(argsOnly = true) ChunkRenderDispatcherBuffers pack,
            @Local LayerRange range,
            @Local ChunkRenderDataSchematic data,
            @Local ChunkMeshDataSchematic chunkMeshData,
            @Local(ordinal = 0) Set<?> usedLayers,
            @Local(ordinal = 1) Set<?> usedRenderTypes,
            @Local PoseStack poseStack,
            @Local VisGraph visGraph)
    {
        long[] cells = GhostSupport.cellsInChunk(this.position.getX() >> 4, this.position.getZ() >> 4);

        if (cells.length == 0)
        {
            return;
        }

        int bottomY = this.position.getY();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        BlockModelRendererSchematic.enableCache();

        try
        {
            for (long cell : cells)
            {
                pos.set(cell);

                if (GhostSupport.rawWithinRange(range, pos.getX(), pos.getY(), pos.getZ()))
                {
                    continue;
                }

                GhostSupport.countDrawn();
                poseStack.pushPose();
                poseStack.translate((float) (pos.getX() & 15), (float) (pos.getY() - bottomY), (float) (pos.getZ() & 15));
                this.renderBlocksAndOverlay(pos.immutable(), data, chunkMeshData, pack, usedLayers, usedRenderTypes,
                        poseStack, visGraph);
                poseStack.popPose();
            }
        }
        finally
        {
            BlockModelRendererSchematic.disableCache();
        }
    }
}
