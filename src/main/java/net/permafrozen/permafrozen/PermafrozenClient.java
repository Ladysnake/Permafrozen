package net.permafrozen.permafrozen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.permafrozen.permafrozen.block.PermafrozenBlocks;
import net.permafrozen.permafrozen.client.entity.render.FatfishEntityRenderer;
import net.permafrozen.permafrozen.client.entity.render.LunarKoiEntityRenderer;
import net.permafrozen.permafrozen.client.entity.render.NudifaeEntityRenderer;
import net.permafrozen.permafrozen.entity.PermafrozenEntities;

public class PermafrozenClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.NUDIFAE, NudifaeEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.LUNAR_KOI, LunarKoiEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.FAT_FUCK, FatfishEntityRenderer::new);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), PermafrozenBlocks.FIR_SAPLING, PermafrozenBlocks.POTTED_FIR_SAPLING, PermafrozenBlocks.FIR_TRAPDOOR);
		colours();
	}
	
	private void colours() {
		ColorProviderRegistry.BLOCK.register((block, pos, world, layer) -> FoliageColors.getSpruceColor(), PermafrozenBlocks.FIR_LEAVES);
		ColorProviderRegistry.ITEM.register((item, layer) -> GrassColors.getColor(0.5D, 1.0D), PermafrozenBlocks.FIR_LEAVES);
	}
}
