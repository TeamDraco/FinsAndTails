package blueportal.finsandstails.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.HighFinnedBlueEntity;

public class HighFinnedBlueModel extends DefaultedEntityGeoModel<HighFinnedBlueEntity> {

    public HighFinnedBlueModel() {
        super(new ResourceLocation(FinsAndTails.MOD_ID, "high_finned_blue"));
    }

    @Override
    public void setCustomAnimations(HighFinnedBlueEntity entity, long uniqueID, AnimationState<HighFinnedBlueEntity> customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        CoreGeoBone body = this.getAnimationProcessor().getBone("body");

        if (!entity.isInWater()) {
            body.setRotZ(1.5708f);
        }
    }
}
