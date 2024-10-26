package blueportal.finsandstails.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.RubberBellyGliderEntity;

public class RubberBellyGliderModel extends DefaultedEntityGeoModel<RubberBellyGliderEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/rubber_belly_glider.png");

    public RubberBellyGliderModel() {
        super(new ResourceLocation(FinsAndTails.MOD_ID, "rubber_belly_glider"));
    }

    @Override
    public ResourceLocation getTextureResource(RubberBellyGliderEntity wee) {
        return TEXTURE;
    }

    @Override
    public void setCustomAnimations(RubberBellyGliderEntity entity, long uniqueID, AnimationState<RubberBellyGliderEntity> customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        CoreGeoBone body = this.getAnimationProcessor().getBone("body");

        EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);

        if (entity.isInWater()) {
            body.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
            body.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
        }
        else {
            body.setRotX(0.0F);
        }
    }
}
