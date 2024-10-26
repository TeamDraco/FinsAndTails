package blueportal.finsandstails.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import blueportal.finsandstails.common.entities.BandedRedbackShrimpEntity;

@SuppressWarnings("FieldCanBeLocal, unused")
@OnlyIn(Dist.CLIENT)
public class BandedRedbackShrimpModel<T extends BandedRedbackShrimpEntity> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart legs1;
    private final ModelPart legs2;
    private final ModelPart legs3;
    private final ModelPart legs4;
    private final ModelPart rostrum;
    private final ModelPart leftAntenna;
    private final ModelPart rightAntenna;
    private final ModelPart leftFin;
    private final ModelPart rightFin;
    private final ModelPart tail;
    private final ModelPart tailFan;

    public BandedRedbackShrimpModel(ModelPart modelPart) {
        this.root = modelPart;

        this.body = this.root.getChild("body");

        this.legs1 = this.body.getChild("legs1");
        this.legs2 = this.body.getChild("legs2");
        this.legs3 = this.body.getChild("legs3");
        this.legs4 = this.body.getChild("legs4");
        this.rostrum = this.body.getChild("rostrum");
        this.leftAntenna = this.body.getChild("leftAntenna");
        this.rightAntenna = this.body.getChild("rightAntenna");
        this.leftFin = this.body.getChild("leftFin");
        this.rightFin = this.body.getChild("rightFin");
        this.tail = this.body.getChild("tail");

        this.tailFan = this.tail.getChild("tailFan");
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        limbSwingAmount = Mth.clamp(limbSwingAmount, -0.45F, 0.45F);

        this.body.xRot = headPitch * (((float)Math.PI / 180F) / 2);
        this.body.yRot = netHeadYaw * (((float)Math.PI / 180F) / 2);

        //idle
        this.body.xRot += Mth.cos(ageInTicks * 0.125F) * 0.25F * 0.25F;
        this.body.y = Mth.sin(ageInTicks * 0.125F) * 1.5F * 0.25F + 21.5F;
        this.leftFin.xRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.25F * 0.25F;
        this.rightFin.xRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.25F * 0.25F;
        this.tail.xRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.75F * 0.25F;
        this.tailFan.xRot = Mth.cos(ageInTicks * 0.125F + 60) * 1.5F * 0.25F;
        this.legs1.xRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.75F * 0.25F + 0.25F;
        this.legs2.xRot = Mth.cos(ageInTicks * 0.125F + 31) * 0.75F * 0.25F + 0.25F;
        this.legs3.xRot = Mth.cos(ageInTicks * 0.125F + 32) * 0.75F * 0.25F + 0.25F;
        this.legs4.xRot = Mth.cos(ageInTicks * 0.125F + 33) * 0.75F * 0.25F + 0.25F;
        this.rightAntenna.xRot = Mth.cos(ageInTicks * 0.075F + Mth.PI) * 0.5F * 0.25F;
        this.rightAntenna.zRot = Mth.cos(ageInTicks * 0.075F + 60 + Mth.PI) * 0.25F * 0.25F;
        this.rightAntenna.yRot = Mth.sin(ageInTicks * 0.075F + Mth.PI) * 0.25F * 0.25F + 0.25F;
        this.leftAntenna.xRot = Mth.cos(ageInTicks * 0.075F) * 0.5F * 0.25F;
        this.leftAntenna.zRot = Mth.cos(ageInTicks * 0.075F + 60) * 0.25F * 0.25F;
        this.leftAntenna.yRot = Mth.sin(ageInTicks * 0.075F) * 0.25F * 0.25F - 0.25F;

        //move
        this.body.xRot += Mth.cos(limbSwing) * limbSwingAmount * 0.5F;
        this.body.y += Mth.sin(limbSwing) * limbSwingAmount * 1.75F;
        this.tail.xRot += Mth.cos(limbSwing + 30) * 1.25F * limbSwingAmount;
        this.tailFan.xRot += Mth.cos(limbSwing + 60) * 1.75F * limbSwingAmount;
        this.legs1.xRot += Mth.cos(limbSwing + 30) * 1.25F * limbSwingAmount;
        this.legs2.xRot += Mth.cos(limbSwing + 31) * 1.25F * limbSwingAmount;
        this.legs3.xRot += Mth.cos(limbSwing + 32) * 1.25F * limbSwingAmount;
        this.legs4.xRot += Mth.cos(limbSwing + 33) * 1.25F * limbSwingAmount;

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(1, 0).addBox(-1.5F, -1.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.5F, 0.0F));
        PartDefinition rostrum = body.addOrReplaceChild("rostrum", CubeListBuilder.create().texOffs(0, 9).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -2.5F));
        PartDefinition rightAntenna = body.addOrReplaceChild("rightAntenna", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(0.0F, -3.5F, -6.0F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, -1.0F, -2.5F, -0.1F, 0.1745F, 0.0F));
        PartDefinition leftAntenna = body.addOrReplaceChild("leftAntenna", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -3.5F, -6.0F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.0F, -2.5F, -0.1F, -0.1745F, 0.0F));
        PartDefinition rightFin = body.addOrReplaceChild("rightFin", CubeListBuilder.create().texOffs(7, 9).addBox(-3.0F, -0.5F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -0.5F, 0.0F));
        PartDefinition leftFin = body.addOrReplaceChild("leftFin", CubeListBuilder.create().texOffs(7, 9).mirror().addBox(0.0F, -0.5F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, -0.5F, 0.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(17, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 2.5F, 0, 0.0F, 0.0F));
        PartDefinition tailFan = tail.addOrReplaceChild("tailFan", CubeListBuilder.create().texOffs(9, 0).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));
        PartDefinition legs4 = body.addOrReplaceChild("legs4", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 1.5F, 2.0F));
        PartDefinition legs3 = body.addOrReplaceChild("legs3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 1.5F, 1.0F));
        PartDefinition legs2 = body.addOrReplaceChild("legs2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 1.5F, 0.0F));
        PartDefinition legs1 = body.addOrReplaceChild("legs1", CubeListBuilder.create().texOffs(26, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, -1.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
