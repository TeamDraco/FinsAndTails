package blueportal.finsandstails.client.model;

import blueportal.finsandstails.client.animation.NightlightSquidAnimation;
import blueportal.finsandstails.common.entities.NightLightSquidEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class NightLightSquidModel<T extends NightLightSquidEntity> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart tentacles;
	private final ModelPart tentacleNorth;
	private final ModelPart tentacleSouth;
	private final ModelPart tentacleEast;
	private final ModelPart tendrilRight;
	private final ModelPart tentacleWest;
	private final ModelPart tendrilLeft;
	private final ModelPart finRight;
	private final ModelPart finLeft;

	public NightLightSquidModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.tentacles = this.body.getChild("tentacles");
		this.tentacleNorth = this.tentacles.getChild("tentacleNorth");
		this.tentacleSouth = this.tentacles.getChild("tentacleSouth");
		this.tentacleEast = this.tentacles.getChild("tentacleEast");
		this.tendrilRight = this.tentacleEast.getChild("tendrilRight");
		this.tentacleWest = this.tentacles.getChild("tentacleWest");
		this.tendrilLeft = this.tentacleWest.getChild("tendrilLeft");
		this.finRight = this.body.getChild("finRight");
		this.finLeft = this.body.getChild("finLeft");
	}

	@Override
	public void setupAnim(NightLightSquidEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.body.xRot = headPitch * (((float)Math.PI / 180F) / 2);
		this.body.yRot = netHeadYaw * (((float)Math.PI / 180F) / 2);

		animateWalk(NightlightSquidAnimation.SWIM, ageInTicks * 0.4F, 0.5F, 3.0F, 100.0F);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.5F, 24.0F, -0.5F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -1.0F, -1.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition tentacles = body.addOrReplaceChild("tentacles", CubeListBuilder.create().texOffs(24, 8).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -2.5F));

		PartDefinition tentacleNorth = tentacles.addOrReplaceChild("tentacleNorth", CubeListBuilder.create().texOffs(-2, 32).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -1.5F));

		PartDefinition tentacleSouth = tentacles.addOrReplaceChild("tentacleSouth", CubeListBuilder.create().texOffs(-2, 26).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -1.5F));

		PartDefinition tentacleEast = tentacles.addOrReplaceChild("tentacleEast", CubeListBuilder.create().texOffs(1, 26).addBox(0.0F, -1.5F, -2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -1.0F, -1.5F));

		PartDefinition tendrilRight = tentacleEast.addOrReplaceChild("tendrilRight", CubeListBuilder.create().texOffs(24, 0).addBox(-1.5F, 0.0F, -8.0F, 3.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition tentacleWest = tentacles.addOrReplaceChild("tentacleWest", CubeListBuilder.create().texOffs(1, 26).mirror().addBox(0.0F, -1.5F, -2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, -1.0F, -1.5F));

		PartDefinition tendrilLeft = tentacleWest.addOrReplaceChild("tendrilLeft", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(-1.4782F, -0.0005F, -8.0F, 3.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 0.0F, 1.6144F));

		PartDefinition finRight = body.addOrReplaceChild("finRight", CubeListBuilder.create().texOffs(-1, 0).mirror().addBox(-2.0F, 0.0F, -3.5F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, -0.5F, 1.5F));

		PartDefinition finLeft = body.addOrReplaceChild("finLeft", CubeListBuilder.create().texOffs(-1, 0).addBox(-3.0F, 0.0F, -3.5F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -0.5F, 1.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}