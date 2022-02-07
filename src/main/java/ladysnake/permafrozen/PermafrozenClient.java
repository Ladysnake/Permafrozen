package ladysnake.permafrozen;

import com.mojang.serialization.Codec;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import ladysnake.permafrozen.client.PermafrozenFx;
import ladysnake.permafrozen.client.entity.render.*;
import ladysnake.permafrozen.client.melonslisestuff.init.IzzyShaders;
import ladysnake.permafrozen.client.melonslisestuff.init.IzzyTextures;
import ladysnake.permafrozen.client.particle.aurora.AuroraParticle;
import ladysnake.permafrozen.client.particle.aurora.AuroraParticleEffect;
import ladysnake.permafrozen.client.particle.snowflake.SnowflakeParticle;
import ladysnake.permafrozen.registry.PermafrozenBlocks;
import ladysnake.permafrozen.registry.PermafrozenEntities;
import ladysnake.permafrozen.registry.PermafrozenItems;
import ladysnake.permafrozen.util.ShaderHandler;
import ladysnake.permafrozen.util.Wind;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PermafrozenClient implements ClientModInitializer {
	public final PermafrozenFx permafrozenFx;
	public static ParticleType<AuroraParticleEffect> AURORA_SMOL;
	public static final DefaultParticleType SNOWFLAKE = register("snowflake");
	private static DefaultParticleType register(String name) {
		return Registry.register(Registry.PARTICLE_TYPE, Permafrozen.MOD_ID + ":" + name, FabricParticleTypes.simple());
	}
	public static final ManagedCoreShader AURORA = ShaderEffectManager.getInstance().manageCoreShader(new Identifier(Permafrozen.MOD_ID, "aurora"), VertexFormats.POSITION_TEXTURE);

	public PermafrozenClient() {
		this.permafrozenFx = new PermafrozenFx();
	}
	public PermafrozenFx fxRenderer() {
		return permafrozenFx;
	}

	@Override
	public void onInitializeClient() {
		TerraformBoatClientHelper.registerModelLayer(new Identifier(Permafrozen.MOD_ID, "deadwood"));
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.NUDIFAE, NudifaeEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.BURROW_GRUB, BurrowGrubRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.LUNAR_KOI, LunarKoiEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.FATFISH, FatfishEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.AURORA_FAE, AuroraFaeRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.PUFFBOO, PuffbooRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.LESSER_FIDDLESNOUT, LesserFiddlesnoutEntityRenderer::new);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), PermafrozenBlocks.PRISMATIC_CORAL, PermafrozenBlocks.DEAD_PRISMATIC_CORAL, PermafrozenBlocks.MOSSY_COBBLED_SHIVERSLATE, PermafrozenBlocks.COBBLED_SHIVERSLATE, PermafrozenBlocks.SHIVERSLATE, PermafrozenBlocks.SPECTRAL_CAP, PermafrozenBlocks.PRISMARINE_CLUSTER, PermafrozenBlocks.SMALL_PRISMARINE_BUD, PermafrozenBlocks.MEDIUM_PRISMARINE_BUD, PermafrozenBlocks.LARGE_PRISMARINE_BUD, PermafrozenBlocks.DEADWOOD_TRAPDOOR, PermafrozenBlocks.DEADWOOD_DOOR, PermafrozenBlocks.GLAUCA_GRASS, PermafrozenBlocks.DEADWOOD_THORN);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, PermafrozenBlocks.DEADWOOD_SIGN.getTexture()));
		BlockEntityRendererRegistry.INSTANCE.register(PermafrozenEntities.SPECTRAL_CAP_TYPE, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new SpectralCapRenderer());
		BlockEntityRendererRegistry.INSTANCE.register(PermafrozenEntities.AURORA_ALTAR_TYPE, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new AuroraAltarRenderer());
		BlockRenderLayerMap.INSTANCE.putBlock(PermafrozenBlocks.FEN_ICE, RenderLayer.getTranslucent());
		ShaderHandler.init();
		ClientTickEvents.START_WORLD_TICK.register(world -> Wind.get().tick(world));
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).addReloadListener(IzzyShaders.INSTANCE);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).addReloadListener(IzzyTextures.INSTANCE);
		initParticles();
		this.permafrozenFx.registerCallbacks();

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
		ParticleFactoryRegistry.getInstance().register(PermafrozenClient.SNOWFLAKE, SnowflakeParticle.Factory::new);

	}




}
