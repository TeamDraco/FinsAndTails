package blueportal.finsandstails.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import blueportal.finsandstails.common.entities.PhantomNudibranchEntity;

public class PhantomNudibranchModel<T extends PhantomNudibranchEntity> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart leftMantle;
    private final ModelPart rightMantle;
    private final ModelPart leftAntenna;
    private final ModelPart rightAntenna;
    
    public PhantomNudibranchModel(ModelPart modelPart) {
        this.root = modelPart;

        this.body = this.root.getChild("body");
        this.leftMantle = this.body.getChild("leftMantle");
        this.rightMantle = this.body.getChild("rightMantle");
        this.leftAntenna = this.body.getChild("leftAntenna");
        this.rightAntenna = this.body.getChild("rightAntenna");
    }
    
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        limbSwingAmount = Mth.clamp(limbSwingAmount, -0.45F, 0.45F);

        this.body.xRot = headPitch * (((float)Math.PI / 180F) / 2);
        this.body.yRot = netHeadYaw * (((float)Math.PI / 180F) / 2);

        //idle
        this.body.xRot += Mth.cos(ageInTicks * 0.125F) * 0.25F * 0.25F;
        this.body.y = Mth.sin(ageInTicks * 0.125F) * 1.5F * 0.25F + 21.5F;
        this.leftMantle.zRot = Mth.cos(ageInTicks * 0.125F + 30) * 0.25F * 0.25F;
        this.rightMantle.zRot = Mth.cos(ageInTicks * 0.125F + 30 + Mth.PI) * 0.25F * 0.25F;
        this.rightAntenna.xRot = Mth.cos(ageInTicks * 0.075F + Mth.PI) * 0.5F * 0.25F - 0.25F;
        this.rightAntenna.zRot = Mth.cos(ageInTicks * 0.075F + 60 + Mth.PI) * 0.25F * 0.25F - 0.25F;
        this.rightAntenna.yRot = Mth.sin(ageInTicks * 0.075F + Mth.PI) * 0.25F * 0.25F;
        this.leftAntenna.xRot = Mth.cos(ageInTicks * 0.075F) * 0.5F * 0.25F - 0.25F;
        this.leftAntenna.zRot = Mth.cos(ageInTicks * 0.075F + 60) * 0.25F * 0.25F + 0.25F;
        this.leftAntenna.yRot = Mth.sin(ageInTicks * 0.075F) * 0.25F * 0.25F;

        //move
        this.body.xRot += Mth.cos(limbSwing) * limbSwingAmount * 0.5F;
        this.body.y += Mth.sin(limbSwing) * limbSwingAmount * 1.75F;
        this.leftMantle.zRot += Mth.cos(limbSwing + 30) * 1.5F * limbSwingAmount;
        this.rightMantle.zRot += Mth.cos(limbSwing + 30 + Mth.PI) * 1.5F * limbSwingAmount;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 3.0F, 6.0F), PartPose.offset(0.0F, 22.5F, 0.0F));
        PartDefinition leftAntenna = body.addOrReplaceChild("leftAntenna", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 0.0F), PartPose.offsetAndRotation(1.0F, -1.0F, -2.5F, -0.3927F, 0.0F, 0.3927F));
        PartDefinition rightAntenna = body.addOrReplaceChild("rightAntenna", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 0.0F).mirror(false), PartPose.offsetAndRotation(-1.0F, -1.0F, -2.5F, -0.3927F, 0.0F, -0.3927F));
        PartDefinition rightMantle = body.addOrReplaceChild("rightMantle", CubeListBuilder.create().texOffs(15, 0).mirror().addBox(-3.0F, -0.2F, -1.0F, 5.0F, 0.0F, 7.0F).mirror(false), PartPose.offset(-2.0F, 0.5F, -1.0F));
        PartDefinition leftMantle = body.addOrReplaceChild("leftMantle", CubeListBuilder.create().texOffs(15, 0).addBox(-2.0F, -0.2F, -1.0F, 5.0F, 0.0F, 7.0F), PartPose.offset(2.0F, 0.5F, -1.0F));

        return LayerDefinition.create(meshdefinition, 32, 16);
    }
    @Override
    public ModelPart root() {
        return this.root;
    }
}
