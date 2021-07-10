package net.permafrozen.permafrozen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.permafrozen.permafrozen.block.PermafrozenBlocks;
import net.permafrozen.permafrozen.client.entity.render.FatfishRenderer;
import net.permafrozen.permafrozen.client.entity.render.LunarKoiRenderer;
import net.permafrozen.permafrozen.client.entity.render.NudifaeRenderer;
import net.permafrozen.permafrozen.entity.PermafrozenEntities;

public class PermafrozenClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.NUDIFAE, NudifaeRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.LUNAR_KOI, LunarKoiRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.FAT_FUCK, FatfishRenderer::new);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), PermafrozenBlocks.FIR_SAPLING, PermafrozenBlocks.POTTED_FIR_SAPLING, PermafrozenBlocks.FIR_TRAPDOOR);
		colours();
	}
	
	private void colours() {
		ColorProviderRegistry.BLOCK.register((block, pos, world, layer) -> FoliageColors.getSpruceColor(), PermafrozenBlocks.FIR_LEAVES);
		ColorProviderRegistry.ITEM.register((item, layer) -> GrassColors.getColor(0.5D, 1.0D), PermafrozenBlocks.FIR_LEAVES);
	}
}
