package blueportal.finsandstails.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import blueportal.finsandstails.common.entities.GoldenRiverRayEntity;

@SuppressWarnings("FieldCanBeLocal, unused")
@OnlyIn(Dist.CLIENT)
public class GoldenRiverRayModel<T extends GoldenRiverRayEntity> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart tailFin;
    private final ModelPart leftFin;
    private final ModelPart rightFin;
    private final ModelPart dorsalFin;
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public GoldenRiverRayModel(ModelPart modelPart) {
        this.root = modelPart;

        this.body = this.root.getChild("body");

        this.dorsalFin = this.body.getChild("dorsalFin");
        this.tail = this.body.getChild("tail");
        this.leftFin = this.body.getChild("leftFin");
        this.rightFin = this.body.getChild("rightFin");
        this.leftWing = this.body.getChild("leftWing");
        this.rightWing = this.body.getChild("rightWing");

        this.tailFin = this.tail.getChild("tailFin");
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        limbSwingAmount = Mth.clamp(limbSwingAmount, -0.45F, 0.45F);

        this.body.xRot = headPitch * (((float)Math.PI / 180F) / 2);
        this.body.yRot = netHeadYaw * (((float)Math.PI / 180F) / 2);

        //idle
        this.body.xRot += Mth.cos(ageInTicks * 0.125F) * 0.25F * 0.25F;
        this.body.y = Mth.sin(ageInTicks * 0.125F) * 1.5F * 0.25F + 21.5F;
        this.tail.xRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.25F * 0.25F;
        this.leftFin.zRot = Mth.cos(ageInTicks * 0.125F + 35) * 0.5F * 0.25F;
        this.rightFin.zRot = Mth.cos(ageInTicks * 0.125F + 35 + Mth.PI) * 0.5F * 0.25F;
        this.leftWing.zRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.25F * 0.25F;
        this.rightWing.zRot = Mth.cos(ageInTicks * 0.125F + 30 + Mth.PI) * 0.25F * 0.25F;

        //move
        this.body.xRot += Mth.cos(limbSwing) * limbSwingAmount * 0.5F;
        this.body.y += Mth.sin(limbSwing) * limbSwingAmount * 1.75F;
        this.tail.yRot = Mth.cos(limbSwing + 30) * 1.5F * limbSwingAmount;
        this.tailFin.yRot = Mth.cos(limbSwing + 60) * 1.25F * limbSwingAmount;
        this.tail.zRot = Mth.sin(limbSwing + 30) * 0.25F * limbSwingAmount;
        this.leftWing.zRot += Mth.cos(limbSwing + 30) * 1.5F * limbSwingAmount;
        this.rightWing.zRot += Mth.cos(limbSwing + 30 + Mth.PI) * 1.5F * limbSwingAmount;

        this.leftFin.zRot += Mth.cos(limbSwing + 35) * 1.5F * limbSwingAmount;
        this.rightFin.zRot += Mth.cos(limbSwing + 35 + Mth.PI) * 1.5F * limbSwingAmount;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -4.0F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 10).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 4.0F));
        PartDefinition tailfin = tail.addOrReplaceChild("tailFin", CubeListBuilder.create().texOffs(10, 4).addBox(0.0F, -0.5F, -2.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.5F));
        PartDefinition leftFin = body.addOrReplaceChild("leftFin", CubeListBuilder.create().texOffs(-2, 10).mirror().addBox(-1.0F, 0.0F, 0.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 0.0F, 3.0F));
        PartDefinition rightFin = body.addOrReplaceChild("rightFin", CubeListBuilder.create().texOffs(-2, 10).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 0.0F, 3.0F));
        PartDefinition dorsalFin = body.addOrReplaceChild("dorsalFin", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.0F, -0.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 3.0F));
        PartDefinition leftWing = body.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(-6, 19).mirror().addBox(0.0F, 0.0F, -1.5F, 10.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, -1.0F, -1.5F));
        PartDefinition rightWing = body.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(-6, 19).addBox(-10.0F, 0.0F, -1.5F, 10.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -1.0F, -1.5F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

}