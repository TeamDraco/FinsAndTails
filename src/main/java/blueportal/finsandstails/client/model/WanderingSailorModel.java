package blueportal.finsandstails.client.model;

import blueportal.finsandstails.common.entities.WanderingSailorEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class WanderingSailorModel<T extends WanderingSailorEntity> extends HierarchicalModel<T> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart tentacles;
	private final ModelPart hat;
	private final ModelPart armRight;
	private final ModelPart arms;
	private final ModelPart armLeft;
	private final ModelPart overlay;
	private final ModelPart legLeft;
	private final ModelPart legRight;

	public WanderingSailorModel(ModelPart root) {
		this.body = root.getChild("body");
		this.head = this.body.getChild("head");
		this.tentacles = this.head.getChild("tentacles");
		this.hat = this.head.getChild("hat");
		this.armRight = this.body.getChild("armRight");
		this.arms = this.armRight.getChild("arms");
		this.armLeft = this.body.getChild("armLeft");
		this.overlay = this.body.getChild("overlay");
		this.legLeft = this.body.getChild("legLeft");
		this.legRight = this.body.getChild("legRight");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(14, 48).addBox(-4.0F, -5.0F, -3.0F, 8.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

		PartDefinition tentacles = head.addOrReplaceChild("tentacles", CubeListBuilder.create().texOffs(24, 2).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -4.0F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -5.0F, 0.0F));

		PartDefinition armRight = body.addOrReplaceChild("armRight", CubeListBuilder.create().texOffs(0, 35).addBox(0.0F, -2.0F, -2.5F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -2.0F, 0.0F, -0.9599F, 0.0F, 0.0F));

		PartDefinition arms = armRight.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(40, 23).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 4.0F, -0.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition armLeft = body.addOrReplaceChild("armLeft", CubeListBuilder.create().texOffs(0, 35).mirror().addBox(-4.0F, -2.0F, -2.5F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -2.0F, 0.0F, -0.9599F, 0.0F, 0.0F));

		PartDefinition overlay = body.addOrReplaceChild("overlay", CubeListBuilder.create().texOffs(18, 26).mirror().addBox(-4.0F, -5.0F, -3.0F, 8.0F, 15.0F, 6.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition legLeft = body.addOrReplaceChild("legLeft", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 5.0F, 0.0F));

		PartDefinition legRight = body.addOrReplaceChild("legRight", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 5.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public ModelPart root() {
		return body;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}
}