package permafrozen.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import permafrozen.Permafrozen;
import permafrozen.entity.Nudifae;
import permafrozen.entity.fish.LunarKoi;
import permafrozen.entity.fish.lunarkoi.LunarKoiRenderer;
import permafrozen.entity.nudifae.NudifaeRenderer;

@Mod.EventBusSubscriber(modid = Permafrozen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> entityRegister = DeferredRegister.create(ForgeRegistries.ENTITIES, Permafrozen.MOD_ID);


    public static final RegistryObject<EntityType<Nudifae>> NUDIFAE = createLivingEntity("nudifae", Nudifae::new, EntityClassification.WATER_CREATURE);
    public static final RegistryObject<EntityType<LunarKoi>> LUNAR_KOI = createLivingEntity("lunar_koi", LunarKoi::new, EntityClassification.WATER_CREATURE);


    public static <E extends LivingEntity> RegistryObject createLivingEntity(String name,  EntityType.IFactory<E> factory, EntityClassification classification) {
        return entityRegister.register(name, () -> EntityType.Builder.create(factory, classification).build(new ResourceLocation(Permafrozen.MOD_ID + name).toString()));
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {

        event.put(NUDIFAE.get(), Nudifae.getAttributes().create());
        event.put(LUNAR_KOI.get(), LunarKoi.getAttributes().create());

    }

    public static void registerRenderers() {

        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.NUDIFAE.get(), NudifaeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.LUNAR_KOI.get(), LunarKoiRenderer::new);

    }


    public static void register(IEventBus eventBus) {

        entityRegister.register(eventBus);

    }

}