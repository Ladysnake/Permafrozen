package permafrozen.registry;

import java.lang.reflect.Field;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import permafrozen.Permafrozen;
import permafrozen.entity.Nudifae;
import permafrozen.entity.fish.LunarKoi;
import permafrozen.entity.fish.lunarkoi.LunarKoiRenderer;
import permafrozen.entity.nudifae.NudifaeRenderer;

@Mod.EventBusSubscriber(modid = Permafrozen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistry {

    public static final EntityType<Nudifae> NUDIFAE = registerEntity(EntityType.Builder.create(Nudifae::new, EntityClassification.WATER_CREATURE), "nudifae");
    public static final EntityType<LunarKoi> LUNAR_KOI = registerEntity(EntityType.Builder.create(LunarKoi::new, EntityClassification.WATER_CREATURE), "lunar_koi");


    private static final EntityType registerEntity(EntityType.Builder builder, String entityName) {
        ResourceLocation nameLoc = new ResourceLocation(Permafrozen.MOD_ID, entityName);
        return (EntityType) builder.build(entityName).setRegistryName(nameLoc);
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {

        event.put(NUDIFAE, Nudifae.getAttributes().create());
        event.put(LUNAR_KOI, LunarKoi.getAttributes().create());

    }

    public static void registerRenderers() {

        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.NUDIFAE, NudifaeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.LUNAR_KOI, LunarKoiRenderer::new);

    }


    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        try {
            for (Field f : EntityRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof EntityType) {
                    event.getRegistry().register((EntityType) obj);
                } else if (obj instanceof EntityType[]) {
                    for (EntityType type : (EntityType[]) obj) {
                        event.getRegistry().register(type);

                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}