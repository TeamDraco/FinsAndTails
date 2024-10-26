package blueportal.finsandstails.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import blueportal.finsandstails.common.entities.OrnateBugfishEntity;

@SuppressWarnings("FieldCanBeLocal, unused")
@OnlyIn(Dist.CLIENT)
public class OrnateBugfishModel<T extends OrnateBugfishEntity> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart leftFin;
    private final ModelPart rightFin;
    private final ModelPart tail;
    private final ModelPart tailFin;
    private final ModelPart mandibles;
    private final ModelPart leftMandible;
    private final ModelPart rightMandible;

    public OrnateBugfishModel(ModelPart modelPart) {
        this.root = modelPart;

        this.body = this.root.getChild("body");

        this.tail = this.body.getChild("tail");
        this.leftFin = this.body.getChild("leftFin");
        this.rightFin = this.body.getChild("rightFin");
        this.mandibles = this.body.getChild("mandibles");

        this.tailFin = this.tail.getChild("tailFin");

        this.leftMandible = this.mandibles.getChild("leftMandible");
        this.rightMandible = this.mandibles.getChild("rightMandible");
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        limbSwingAmount = Mth.clamp(limbSwingAmount, -0.45F, 0.45F);

        this.body.xRot = headPitch * (((float)Math.PI / 180F) / 2);
        this.body.yRot = netHeadYaw * (((float)Math.PI / 180F) / 2);

        //idle
        this.body.xRot += Mth.cos(ageInTicks * 0.125F) * 0.25F * 0.25F;
        this.body.y = Mth.sin(ageInTicks * 0.125F) * 1.5F * 0.25F + 20F;
        this.leftFin.xRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.25F * 0.25F;
        this.rightFin.xRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.25F * 0.25F;
        this.leftFin.yRot = Mth.cos(ageInTicks * 0.125F + 35) * 0.5F * 0.25F + 0.7854F;
        this.rightFin.yRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.5F * 0.25F - 0.7854F;
        this.tail.xRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.25F * 0.25F;
        this.tailFin.xRot = Mth.cos(ageInTicks * 0.125F + 60) * 0.25F * 0.25F;
        this.leftMandible.yRot = Mth.cos(ageInTicks * 0.125F + Mth.PI) * 0.25F - 0.25F;
        this.rightMandible.yRot = Mth.cos(ageInTicks * 0.125F) * 0.25F + 0.25F;

        //move
        this.body.y += Mth.sin(limbSwing * 0.5F) * limbSwingAmount * 1.75F;
        this.tail.yRot = Mth.cos(limbSwing + 30) * 1.5F * limbSwingAmount;
        this.tailFin.yRot = Mth.cos(limbSwing + 60) * 1.25F * limbSwingAmount;
        this.tail.zRot = Mth.sin(limbSwing + 30) * 0.25F * limbSwingAmount;
        this.leftMandible.yRot += Mth.cos(limbSwing * 1.5F + Mth.PI) * limbSwingAmount * 1F;
        this.rightMandible.yRot += Mth.cos(limbSwing * 1.5F) * limbSwingAmount * 1F;
        this.leftFin.yRot += Mth.cos(limbSwing * 1.5F + 30) * 1.5F * limbSwingAmount;
        this.rightFin.yRot += Mth.cos(limbSwing * 1.5F + 35 + Mth.PI) * 1.5F * limbSwingAmount;

        if (!entityIn.isInWater()) {
            this.body.zRot = Mth.PI * 0.5F;
        } else this.body.zRot = 0;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -4.0F, -13.5F, 7.0F, 8.0F, 15.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 20.0F, 6.0F));
        PartDefinition leftFin = body.addOrReplaceChild("leftFin", CubeListBuilder.create().texOffs(0, -5).addBox(0.0F, -3.0F, 0.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 3.0F, -7.5F, 0.0F, 0.7854F, 0.0F));
        PartDefinition rightFin = body.addOrReplaceChild("rightFin", CubeListBuilder.create().texOffs(0, -5).mirror().addBox(0.0F, -3.0F, 0.0F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 3.0F, -7.5F, 0.0F, -0.7854F, 0.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 23).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(0, 3).addBox(0.0F, -4.0F, 0.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 1.5F));
        PartDefinition tailFin = tail.addOrReplaceChild("tailFin", CubeListBuilder.create().texOffs(29, -5).addBox(0.0F, -6.5F, 0.0F, 0.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 10.0F));
        PartDefinition mandibles = body.addOrReplaceChild("mandibles", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -13.5F));
        PartDefinition rightMandible = mandibles.addOrReplaceChild("rightMandible", CubeListBuilder.create().texOffs(21, 23).addBox(0.0F, -2.5F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(21, 30).addBox(1.5F, 1.5F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.02F)), PartPose.offset(-2.5F, 1.5F, 0.0F));
        PartDefinition leftMandible = mandibles.addOrReplaceChild("leftMandible", CubeListBuilder.create().texOffs(33, 23).addBox(-3.0F, -2.5F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(25, 30).addBox(-2.5F, 1.5F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 1.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 48, 48);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

}