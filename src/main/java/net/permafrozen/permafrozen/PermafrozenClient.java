package net.permafrozen.permafrozen;

import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.permafrozen.permafrozen.client.entity.render.AuroraFaeRenderer;
import net.permafrozen.permafrozen.client.entity.render.FatfishEntityRenderer;
import net.permafrozen.permafrozen.client.entity.render.LunarKoiEntityRenderer;
import net.permafrozen.permafrozen.client.entity.render.NudifaeEntityRenderer;
import net.permafrozen.permafrozen.registry.PermafrozenBlocks;
import net.permafrozen.permafrozen.registry.PermafrozenEntities;

public class PermafrozenClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.FIR_BOAT, BoatEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.NUDIFAE, NudifaeEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.LUNAR_KOI, LunarKoiEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.FATFISH, FatfishEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.AURORA_FAE, AuroraFaeRenderer::new);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), PermafrozenBlocks.FIR_SAPLING, PermafrozenBlocks.POTTED_FIR_SAPLING, PermafrozenBlocks.FIR_TRAPDOOR, PermafrozenBlocks.SPECTRAL_CAP, PermafrozenBlocks.PRISMARINE_CLUSTER, PermafrozenBlocks.SMALL_PRISMARINE_BUD, PermafrozenBlocks.MEDIUM_PRISMARINE_BUD, PermafrozenBlocks.LARGE_PRISMARINE_BUD);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, PermafrozenBlocks.FIR_SIGN.getTexture()));
		initColors();
	}
	
	private void initColors() {
		ColorProviderRegistry.BLOCK.register((block, pos, world, layer) -> FoliageColors.getSpruceColor(), PermafrozenBlocks.FIR_LEAVES);
		ColorProviderRegistry.ITEM.register((item, layer) -> GrassColors.getColor(0.5D, 1.0D), PermafrozenBlocks.FIR_LEAVES);
	}
}
