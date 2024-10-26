package blueportal.finsandstails.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.CrownedHorateeEntity;

public class CrownedHorateeModel extends DefaultedEntityGeoModel<CrownedHorateeEntity> {

	public CrownedHorateeModel() {
		super(new ResourceLocation(FinsAndTails.MOD_ID, "crowned_horatee"));
	}

	@Override
	public void setCustomAnimations(CrownedHorateeEntity entity, long uniqueID, AnimationState<CrownedHorateeEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);
		CoreGeoBone head = this.getAnimationProcessor().getBone("head");
		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);

		head.setRotX(extraData.headPitch() * (float) Math.PI / 180F);
		head.setRotY(extraData.netHeadYaw() * (float) Math.PI / 180F);
	}
}
