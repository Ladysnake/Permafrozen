package net.permafrozen.permafrozen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
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
    }
}
