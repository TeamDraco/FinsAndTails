package blueportal.finsandstails.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.WeeWeeEntity;

public class WeeWeeModel extends DefaultedEntityGeoModel<WeeWeeEntity> {

    public WeeWeeModel() {
        super(new ResourceLocation(FinsAndTails.MOD_ID, "wee_wee"));
    }

    @Override
    public void setCustomAnimations(WeeWeeEntity entity, long uniqueID, AnimationState<WeeWeeEntity> customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        CoreGeoBone body = this.getAnimationProcessor().getBone("root");

        if (!entity.isInWater()) {
            body.setRotZ(1.5708f);
        }
    }
}
