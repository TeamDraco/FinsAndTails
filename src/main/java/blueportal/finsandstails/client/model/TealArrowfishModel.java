package blueportal.finsandstails.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import blueportal.finsandstails.common.entities.TealArrowfishEntity;

public class TealArrowfishModel<T extends TealArrowfishEntity> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart bottomFin;
    private final ModelPart leftFin;
    private final ModelPart rightFin;
    private final ModelPart topFin;


    public TealArrowfishModel(ModelPart modelPart) {
        this.root = modelPart;

        this.body = this.root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.bottomFin = this.body.getChild("bottomFin");
        this.leftFin = this.body.getChild("leftFin");
        this.rightFin = this.body.getChild("rightFin");
        this.topFin = this.body.getChild("topFin");
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        limbSwingAmount = Mth.clamp(limbSwingAmount, -0.45F, 0.45F);

        //idle
        this.body.xRot = Mth.cos(ageInTicks * 0.125F) * 0.25F * 0.25F;
        this.body.y = Mth.sin(ageInTicks * 0.125F) * 1.5F * 0.25F + 21.5F;
        this.tail.xRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.25F * 0.25F;
        this.leftFin.xRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.25F * 0.25F;
        this.rightFin.xRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.25F * 0.25F;
        this.leftFin.yRot = Mth.cos(ageInTicks * 0.125F + 35) * 0.5F * 0.25F - 0.25F;
        this.rightFin.yRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.5F * 0.25F + 0.25F;

        //move
        this.body.xRot += Mth.cos(limbSwing) * limbSwingAmount * 0.5F;
        this.body.y += Mth.sin(limbSwing) * limbSwingAmount * 1.75F;
        this.tail.yRot = Mth.cos(limbSwing + 30) * 1.5F * limbSwingAmount;
        this.tail.zRot = Mth.sin(limbSwing + 30) * 0.25F * limbSwingAmount;
        this.leftFin.yRot += Mth.cos(limbSwing * 1.5F + 30) * 1.5F * limbSwingAmount;
        this.rightFin.yRot += Mth.cos(limbSwing * 1.5F + 35 + Mth.PI) * 1.5F * limbSwingAmount;

        if (!entityIn.isInWater()) {
            this.body.zRot = Mth.PI * 0.5F;
        } else this.body.zRot = 0;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 2.0F, 8.0F)
                .texOffs(6, 10).addBox(-0.5F, -1.0F, -10.0F, 1.0F, 1.0F, 4.0F), PartPose.offset(0.0F, 23.0F, 2.0F));
        PartDefinition bottomFin = body.addOrReplaceChild("bottomFin", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 1.0F, 1.0F));
        PartDefinition leftFin = body.addOrReplaceChild("leftFin", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F), PartPose.offset(1.0F, 0.5F, -3.5F));
        PartDefinition rightFin = body.addOrReplaceChild("rightFin", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F).mirror(false), PartPose.offset(-1.0F, 0.5F, -3.5F));
        PartDefinition topFin = body.addOrReplaceChild("topFin", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F), PartPose.offset(0.0F, -1.0F, 1.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 5).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 5.0F), PartPose.offset(0.0F, -0.5F, 2.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }
    @Override
    public ModelPart root() {
        return this.root;
    }
}
