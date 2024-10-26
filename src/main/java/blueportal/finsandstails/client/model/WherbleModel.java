package blueportal.finsandstails.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import blueportal.finsandstails.client.animation.WherbleAnimation;
import blueportal.finsandstails.common.entities.WherbleEntity;

public class WherbleModel<T extends WherbleEntity> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart torso;
    private final ModelPart tail;
    private final ModelPart head;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftFoot;
    private final ModelPart rightFoot;

    public WherbleModel(ModelPart modelPart) {
        this.root = modelPart;

        this.body = this.root.getChild("body");
        this.leftLeg = this.body.getChild("leftLeg");
        this.leftFoot = this.leftLeg.getChild("leftFoot");
        this.rightLeg = this.body.getChild("rightLeg");
        this.rightFoot = this.rightLeg.getChild("rightFoot");
        this.torso = this.body.getChild("torso");

        this.tail = this.torso.getChild("tail");
        this.head = this.torso.getChild("head");
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        limbSwingAmount = Mth.clamp(limbSwingAmount, -0.45F, 0.45F);

        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.head.xRot = headPitch * 0.017453292F;
        this.head.yRot = netHeadYaw * 0.017453292F;

        if (this.young && entityIn.isProjectile()) this.animateWalk(WherbleAnimation.WHERBLING_SPIN, ageInTicks, 0.5F, 2.0F, 100.0F);
        if (this.young) this.animateWalk(WherbleAnimation.WHERBLING_WALK, limbSwing, limbSwingAmount, 2.0F, 100.0F);
        else this.animateWalk(WherbleAnimation.WALK, limbSwing, limbSwingAmount, 3.0F, 100.0F);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 39).mirror().addBox(-1.0F, -1.0F, -1.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-3.5F, -5.0F, 0.0F));
        PartDefinition rightFoot = rightLeg.addOrReplaceChild("rightFoot", CubeListBuilder.create().texOffs(-2, 48).mirror().addBox(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(0.0F, 5.0F, -1.5F));
        PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 39).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(3.5F, -5.0F, 0.0F));
        PartDefinition leftFoot = leftLeg.addOrReplaceChild("leftFoot", CubeListBuilder.create().texOffs(-2, 48).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 5.0F, -1.5F));
        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -5.0F, -5.0F, 9.0F, 7.0F, 7.0F), PartPose.offset(0.0F, -6.0F, 0.5F));
        PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(13, 31).addBox(-0.5F, -2.75F, -2.5F, 1.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(-3.0F, -5.0F, 0.5F, -0.2618F, 0.0F, -0.2618F));
        PartDefinition cube_r2 = torso.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -2.75F, -2.5F, 1.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(3.0F, -5.0F, 0.5F, -0.2618F, 0.0F, 0.2618F));
        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(27, 9).addBox(-3.5F, -2.0F, -5.0F, 7.0F, 4.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -5.0F));
        PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 16).addBox(-3.5F, -1.0F, 1.0F, 7.0F, 3.0F, 10.0F)
                .texOffs(24, 20).addBox(-2.5F, -1.0F, 0.0F, 5.0F, 4.0F, 10.0F), PartPose.offset(0.0F, -3.0F, 2.0F));
        PartDefinition cube_r3 = tail.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 31).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 3.0F, 5.0F), PartPose.offsetAndRotation(0.0F, -2.0F, 3.5F, -0.1309F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public static LayerDefinition createWherblingBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 5).addBox(-1.5F, -2.0F, -2.0F, 3.0F, 3.0F, 3.0F), PartPose.offset(0.0F, -3.0F, -0.25F));
        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 0.0F, -2.0F));
        PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F), PartPose.offset(0.0F, -1.0F, 1.0F));
        PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(12, 5).addBox(0.0F, 0.0F, -2.5F, 0.0F, 2.0F, 3.0F), PartPose.offset(-1.5F, -2.0F, 0.25F));
        PartDefinition rightFoot = rightLeg.addOrReplaceChild("rightFoot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.5F));
        PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(12, 5).mirror().addBox(0.0F, 0.0F, -2.5F, 0.0F, 2.0F, 3.0F).mirror(false), PartPose.offset(1.5F, -2.0F, 0.25F));
        PartDefinition leftFoot = leftLeg.addOrReplaceChild("leftFoot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.5F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }
    
    @Override
    public ModelPart root() {
        return this.root;
    }
}
