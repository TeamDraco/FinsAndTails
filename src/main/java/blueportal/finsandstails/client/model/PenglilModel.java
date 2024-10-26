package blueportal.finsandstails.client.model;

import blueportal.finsandstails.client.animation.PenglilAnimation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import blueportal.finsandstails.client.animation.MudhorseAnimation;
import blueportal.finsandstails.common.entities.PenglilEntity;

@SuppressWarnings("FieldCanBeLocal, unused")
@OnlyIn(Dist.CLIENT)
public class PenglilModel<T extends PenglilEntity> extends HierarchicalModel<T> {

	private final ModelPart all;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart leftFoot;
	private final ModelPart rightFoot;

	public PenglilModel(ModelPart root) {
		this.all = root.getChild("all");
		this.body = this.all.getChild("body");
		this.tail = this.body.getChild("tail");
		this.rightWing = this.body.getChild("rightWing");
		this.leftWing = this.body.getChild("leftWing");
		this.leftFoot = this.all.getChild("leftFoot");
		this.rightFoot = this.all.getChild("rightFoot");
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.isInWater()) {
			this.animateWalk(PenglilAnimation.SWIM, limbSwing, limbSwingAmount, 3.0F, 100.0F);
			all.xRot = headPitch * ((float) Math.PI / 180F) + 0.7F;
			all.yRot = netHeadYaw * ((float) Math.PI / 180F);
		}
		else this.animateWalk(PenglilAnimation.WALK, limbSwing * 1.5F, limbSwingAmount, 3.0F, 100.0F);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -5.5F, -2.5F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(19, 8).addBox(-3.0F, -9.5F, -1.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 11).addBox(-2.0F, -5.5F, -4.5F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(9, 13).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.5F));

		PartDefinition rightWing = body.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(22, 2).addBox(-1.0F, 0.0F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -1.5F, 1.0F));

		PartDefinition leftWing = body.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(22, 2).mirror().addBox(0.0F, 0.0F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, -1.5F, 1.0F));

		PartDefinition leftFoot = all.addOrReplaceChild("leftFoot", CubeListBuilder.create().texOffs(17, 2).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -1.0F, 0.5F));

		PartDefinition rightFoot = all.addOrReplaceChild("rightFoot", CubeListBuilder.create().texOffs(17, 2).mirror().addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, -1.0F, 0.5F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public ModelPart root() {
		return this.all;
	}
}