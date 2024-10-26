
package blueportal.finsandstails.client.render;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.client.model.SwampMuckerModel;
import blueportal.finsandstails.common.entities.SwampMuckerEntity;
import blueportal.finsandstails.registry.FTModelLayers;

public class SwampMuckerRenderer extends MobRenderer<SwampMuckerEntity, SwampMuckerModel<SwampMuckerEntity>> {
    private static final ResourceLocation SWAMP_MUCKER_LOCATION = new ResourceLocation(FinsAndTails.MOD_ID,"textures/entity/swamp_mucker/swamp_mucker.png");

    public SwampMuckerRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SwampMuckerModel<>(ctx.bakeLayer(FTModelLayers.SWAMP_MUCKER)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(SwampMuckerEntity entity) {
        return SWAMP_MUCKER_LOCATION;
    }
}
