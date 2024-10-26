package blueportal.finsandstails.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import blueportal.finsandstails.common.entities.GopjetEntity;

public class GopjetModel<T extends GopjetEntity> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart thruster;
    private final ModelPart tail;
    private final ModelPart tailFin;
    private final ModelPart leftFin;
    private final ModelPart rightFin;

    public GopjetModel(ModelPart modelPart) {
        this.root = modelPart;

        this.body = this.root.getChild("body");

        this.thruster = this.body.getChild("thruster");
        this.tail = this.body.getChild("tail");
        this.leftFin = this.body.getChild("leftFin");
        this.rightFin = this.body.getChild("rightFin");

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
        this.leftFin.zRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.5F * 0.25F + 0.35F;
        this.rightFin.zRot = Mth.cos(ageInTicks * 0.125F + 30 + Mth.PI) * 0.5F * 0.25F - 0.35F;

        //move
        this.body.xRot += Mth.cos(limbSwing) * limbSwingAmount * 0.25F;
        this.body.y += Mth.sin(limbSwing) * limbSwingAmount * 2F;
        this.leftFin.zRot += Mth.cos(limbSwing + 30) * 1.5F * limbSwingAmount;
        this.rightFin.zRot += Mth.cos(limbSwing + 30 + Mth.PI) * 1.5F * limbSwingAmount;
        this.tail.yRot = Mth.cos(limbSwing + 30) * 1.5F * limbSwingAmount;
        this.tail.zRot = Mth.sin(limbSwing + 30) * 0.25F * limbSwingAmount;

        if (entityIn.isBoosting()) {
            this.thruster.xScale = Mth.cos(ageInTicks + 30) * 1.5F * 0.25F + 1;
            this.thruster.yScale = Mth.cos(ageInTicks) * 1.5F * 0.25F + 1;
            this.thruster.zScale = Mth.sin(ageInTicks) * 1.5F * 0.25F + 1;
            this.body.xScale = Mth.cos(ageInTicks + 30) * 0.5F * 0.25F + 1;
            this.body.yScale = Mth.cos(ageInTicks) * 0.5F * 0.25F + 1;
            this.body.zScale = Mth.sin(ageInTicks) * 0.5F * 0.25F + 1;
        } else {
            this.thruster.xScale = 1;
            this.thruster.yScale = 1;
            this.thruster.zScale = 1;
            this.body.xScale = 1;
            this.body.yScale = 1;
            this.body.zScale = 1;
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.5F, -5.5F, 8.0F, 5.0F, 11.0F), PartPose.offset(0.0F, 21.5F, -1.5F));
        PartDefinition thruster = body.addOrReplaceChild("thruster", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, -2.0F, -3.5F, 4.0F, 2.0F, 7.0F), PartPose.offset(0.0F, -2.5F, 1.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(15, 16).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 11.0F), PartPose.offset(0.0F, 1.0F, 5.5F));
        PartDefinition tailFin = tail.addOrReplaceChild("tailFin", CubeListBuilder.create().texOffs(4, 5).addBox(0.0F, -2.0F, -8.5F, 0.0F, 3.0F, 11.0F), PartPose.offset(0.0F, -1.5F, 10.5F));
        PartDefinition leftFin = body.addOrReplaceChild("leftFin", CubeListBuilder.create().texOffs(10, 21).addBox(0.0F, 0.0F, -1.0F, 5.0F, 0.0F, 3.0F), PartPose.offsetAndRotation(4.0F, 2.5F, -0.5F, 0.0F, 0.0F, 0.2618F));
        PartDefinition rightFin = body.addOrReplaceChild("rightFin", CubeListBuilder.create().texOffs(10, 21).mirror().addBox(-5.0F, 0.0F, -1.0F, 5.0F, 0.0F, 3.0F).mirror(false), PartPose.offsetAndRotation(-4.0F, 2.5F, -0.5F, 0.0F, 0.0F, -0.2618F));

        return LayerDefinition.create(meshdefinition, 48, 48);
    }
    @Override
    public ModelPart root() {
        return this.root;
    }
}
