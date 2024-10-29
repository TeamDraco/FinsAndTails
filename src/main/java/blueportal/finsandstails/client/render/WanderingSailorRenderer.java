
package blueportal.finsandstails.client.render;

import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.client.FTModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import blueportal.finsandstails.client.model.WanderingSailorModel;
import blueportal.finsandstails.common.entities.WanderingSailorEntity;

public class WanderingSailorRenderer extends MobRenderer<WanderingSailorEntity, WanderingSailorModel<WanderingSailorEntity>> {
    private static final ResourceLocation WANDERING_SAILOR_LOCATION = new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/wandering_sailor/wandering_sailor.png");

    public WanderingSailorRenderer(EntityRendererProvider.Context context) {
        super(context, new WanderingSailorModel<>(context.bakeLayer(FTModelLayers.WANDERING_SAILOR)), 0.45F);
    }

    @Override
    public ResourceLocation getTextureLocation(WanderingSailorEntity entity) {
        return WANDERING_SAILOR_LOCATION;
    }
}