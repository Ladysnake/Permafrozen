package net.permafrozen.permafrozen;

import com.mojang.serialization.Codec;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.permafrozen.permafrozen.client.entity.render.*;
import net.permafrozen.permafrozen.client.particle.aurora.AuroraParticle;
import net.permafrozen.permafrozen.client.particle.aurora.AuroraParticleEffect;
import net.permafrozen.permafrozen.registry.PermafrozenBlocks;
import net.permafrozen.permafrozen.registry.PermafrozenEntities;
import net.permafrozen.permafrozen.registry.PermafrozenItems;

public class PermafrozenClient implements ClientModInitializer {
	public static ParticleType<AuroraParticleEffect> AURORA_SMOL;
	@Override
	public void onInitializeClient() {
		TerraformBoatClientHelper.registerModelLayer(new Identifier(Permafrozen.MOD_ID, "fir"));
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.NUDIFAE, NudifaeEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.LUNAR_KOI, LunarKoiEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.FATFISH, FatfishEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.AURORA_FAE, AuroraFaeRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.PUFFBOO, PuffbooRenderer::new);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), PermafrozenBlocks.FIR_SAPLING, PermafrozenBlocks.POTTED_FIR_SAPLING, PermafrozenBlocks.PRISMATIC_CORAL, PermafrozenBlocks.DEAD_PRISMATIC_CORAL, PermafrozenBlocks.FIR_TRAPDOOR, PermafrozenBlocks.SPECTRAL_CAP, PermafrozenBlocks.PRISMARINE_CLUSTER, PermafrozenBlocks.SMALL_PRISMARINE_BUD, PermafrozenBlocks.MEDIUM_PRISMARINE_BUD, PermafrozenBlocks.LARGE_PRISMARINE_BUD, PermafrozenBlocks.FIR_LEAVES);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, PermafrozenBlocks.FIR_SIGN.getTexture()));
		initColors();
		initParticles();

		FabricModelPredicateProviderRegistry.register(new Identifier(Permafrozen.MOD_ID + ":type"), (itemStack, world, livingEntity, seed) -> itemStack.getOrCreateSubNbt(Permafrozen.MOD_ID).getInt("type"));
	}

	private void initParticles() {
		AURORA_SMOL = Registry.register(Registry.PARTICLE_TYPE, "permafrozen:aurora", new ParticleType<AuroraParticleEffect>(true, AuroraParticleEffect.PARAMETERS_FACTORY) {
			@Override
			public Codec<AuroraParticleEffect> getCodec() {
				return AuroraParticleEffect.CODEC;
			}
		});
		ParticleFactoryRegistry.getInstance().register(PermafrozenClient.AURORA_SMOL, AuroraParticle.Factory::new);
	}
	
	private void initColors() {
		ColorProviderRegistry.BLOCK.register((block, pos, world, layer) -> FoliageColors.getSpruceColor(), PermafrozenBlocks.FIR_LEAVES);
		ColorProviderRegistry.ITEM.register((item, layer) -> GrassColors.getColor(0.5D, 1.0D), PermafrozenBlocks.FIR_LEAVES);
	}
}
