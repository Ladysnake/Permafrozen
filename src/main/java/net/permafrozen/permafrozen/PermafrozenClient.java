package net.permafrozen.permafrozen;

import com.mojang.serialization.Codec;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import ladysnake.satin.api.event.PostWorldRenderCallback;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import ladysnake.satin.api.managed.uniform.Uniform2f;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.Camera;
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

public class PermafrozenClient implements ClientModInitializer, ClientTickEvents.EndTick, PostWorldRenderCallback {
	public static ParticleType<AuroraParticleEffect> AURORA_SMOL;
	public static final ManagedCoreShader AURORA = ShaderEffectManager.getInstance().manageCoreShader(new Identifier(Permafrozen.MOD_ID, "aurora"));
	private final Uniform1f uniformGameTime = AURORA.findUniform1f("GameTime");
	private final Uniform1f uniformPlayerRotPitch = AURORA.findUniform1f("PlayerRotPitch");
	private final Uniform1f uniformPlayerRotYaw = AURORA.findUniform1f("PlayerRotYaw");
	private int ticks;
	private boolean renderingEffect;


	@Override
	public void onInitializeClient() {
		TerraformBoatClientHelper.registerModelLayer(new Identifier(Permafrozen.MOD_ID, "fir"));
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.NUDIFAE, NudifaeEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.LUNAR_KOI, LunarKoiEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.FATFISH, FatfishEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.AURORA_FAE, AuroraFaeRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.PUFFBOO, PuffbooRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.LESSER_FIDDLESNOUT, LesserFiddlesnoutEntityRenderer::new);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), PermafrozenBlocks.FIR_SAPLING, PermafrozenBlocks.POTTED_FIR_SAPLING, PermafrozenBlocks.PRISMATIC_CORAL, PermafrozenBlocks.DEAD_PRISMATIC_CORAL, PermafrozenBlocks.FIR_TRAPDOOR, PermafrozenBlocks.SPECTRAL_CAP, PermafrozenBlocks.PRISMARINE_CLUSTER, PermafrozenBlocks.SMALL_PRISMARINE_BUD, PermafrozenBlocks.MEDIUM_PRISMARINE_BUD, PermafrozenBlocks.LARGE_PRISMARINE_BUD, PermafrozenBlocks.FIR_LEAVES, PermafrozenBlocks.DEADWOOD_TRAPDOOR, PermafrozenBlocks.DEADWOOD_DOOR);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, PermafrozenBlocks.FIR_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, PermafrozenBlocks.DEADWOOD_SIGN.getTexture()));


		PostWorldRenderCallback.EVENT.register(this);
		ClientTickEvents.END_CLIENT_TICK.register(this);

		initColors();
		initParticles();

		FabricModelPredicateProviderRegistry.register(PermafrozenItems.NUDIFAE_BUCKET, new Identifier(Permafrozen.MOD_ID + ":type"), (itemStack, world, livingEntity, seed) -> itemStack.getOrCreateNbt().getInt("type"));
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

	@Override
	public void onEndTick(MinecraftClient client) {
		if (client.player != null) {
			if (!this.renderingEffect) {
				this.ticks = 0;
				this.renderingEffect = true;
			}
			this.ticks++;
		} else {
			this.renderingEffect = false;
		}
	}

	@Override
	public void onWorldRendered(Camera camera, float tickDelta, long nanoTime) {
		uniformGameTime.set((ticks + tickDelta) / 20f);
		uniformPlayerRotPitch.set(camera.getPitch());
		uniformPlayerRotYaw.set(camera.getYaw());
	}
}
