package blueportal.finsandstails.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import blueportal.finsandstails.common.entities.SwampMuckerEntity;

public class SwampMuckerModel<T extends SwampMuckerEntity> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart leftPelvicFin;
    private final ModelPart rightPelvicFin;
    private final ModelPart leftFwin;
    private final ModelPart rightFwin;

    public SwampMuckerModel(ModelPart modelPart) {
        this.root = modelPart;

        this.body = this.root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.leftPelvicFin = this.body.getChild("leftPelvicFin");
        this.rightPelvicFin = this.body.getChild("rightPelvicFin");
        this.leftFwin = this.body.getChild("leftFwin");
        this.rightFwin = this.body.getChild("rightFwin");
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        limbSwingAmount = Mth.clamp(limbSwingAmount, -0.45F, 0.45F);

        //idle
        this.body.xRot = Mth.cos(ageInTicks * 0.125F) * 0.25F * 0.25F;
        this.body.y = Mth.sin(ageInTicks * 0.125F) * 1.5F * 0.25F + 21.5F;
        this.tail.xRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.25F * 0.25F;
        this.leftFwin.zRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.25F * 0.25F;
        this.rightFwin.zRot = Mth.cos(ageInTicks * 0.125F + 30 + Mth.PI) * 0.25F * 0.25F;
        this.leftPelvicFin.zRot = Mth.cos(ageInTicks * 0.125F + 35) * 0.5F * 0.25F - 0.35F;
        this.rightPelvicFin.zRot = Mth.cos(ageInTicks * 0.125F + 35 + Mth.PI) * 0.5F * 0.25F + 0.35F;

        //move
        this.body.xRot += Mth.cos(limbSwing) * limbSwingAmount * 0.5F;
        this.body.y += Mth.sin(limbSwing) * limbSwingAmount * 1.75F;
        this.leftFwin.zRot += Mth.cos(limbSwing + 30) * 1.5F * limbSwingAmount;
        this.rightFwin.zRot += Mth.cos(limbSwing + 30 + Mth.PI) * 1.5F * limbSwingAmount;
        this.tail.yRot = Mth.cos(limbSwing + 30) * 1.5F * limbSwingAmount;
        this.tail.zRot = Mth.sin(limbSwing + 30) * 0.25F * limbSwingAmount;
        this.leftPelvicFin.zRot += Mth.cos(limbSwing + 35) * 0.5F * limbSwingAmount;
        this.rightPelvicFin.zRot += Mth.cos(limbSwing + 35 + Mth.PI) * 0.5F * limbSwingAmount;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 8.0F).texOffs(14, -5).addBox(0.0F, -3.5F, -4.0F, 0.0F, 2.0F, 5.0F), PartPose.offset(0.0F, 22.5F, 3.0F));
        PartDefinition leftPelvicFin = body.addOrReplaceChild("leftPelvicFin", CubeListBuilder.create().texOffs(0, -2).addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F), PartPose.offsetAndRotation(0.5F, 1.5F, -3.0F, 0.0F, 0.0F, -0.3927F));
        PartDefinition rightPelvicFin = body.addOrReplaceChild("rightPelvicFin", CubeListBuilder.create().texOffs(0, -2).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F).mirror(false), PartPose.offsetAndRotation(-0.5F, 1.5F, -3.0F, 0.0F, 0.0F, 0.3927F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 1.0F));
        PartDefinition leftFwin = body.addOrReplaceChild("leftFwin", CubeListBuilder.create().texOffs(-4, 11).addBox(0.0F, 0.0F, -0.5F, 9.0F, 0.0F, 4.0F), PartPose.offset(1.5F, -1.5F, -4.5F));
        PartDefinition rightFwin = body.addOrReplaceChild("rightFwin", CubeListBuilder.create().texOffs(-4, 11).mirror().addBox(-9.0F, 0.0F, -0.5F, 9.0F, 0.0F, 4.0F).mirror(false), PartPose.offset(-1.5F, -1.5F, -4.5F));

        return LayerDefinition.create(meshdefinition, 32, 16);
    }
    @Override
    public ModelPart root() {
        return this.root;
    }
}
