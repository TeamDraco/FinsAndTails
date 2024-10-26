package blueportal.finsandstails.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.client.model.TealArrowfishModel;
import blueportal.finsandstails.common.entities.item.TealArrowfishArrowEntity;
import blueportal.finsandstails.registry.FTModelLayers;

public class TealArrowfishArrowRenderer extends EntityRenderer<TealArrowfishArrowEntity> {
    private static final ResourceLocation TEAL_ARROWFISH_LOCATION = new ResourceLocation(FinsAndTails.MOD_ID,"textures/entity/teal_arrowfish/teal_arrowfish.png");
    private TealArrowfishModel model;


    public TealArrowfishArrowRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new TealArrowfishModel(ctx.bakeLayer(FTModelLayers.TEAL_ARROWFISH_ARROW));
    }

    @Override
    public ResourceLocation getTextureLocation(TealArrowfishArrowEntity entity) {
        return TEAL_ARROWFISH_LOCATION;
    }

    @Override
    public void render(TealArrowfishArrowEntity entity, float p_113840_, float p_113841_, PoseStack poseStack, MultiBufferSource buffer, int p_113844_) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_113841_, entity.yRotO, entity.getYRot()) - 180.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(p_113841_, entity.xRotO, entity.getXRot())));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-180.0F));
        poseStack.translate(0.0, -1.45f, 0.3);
        float f9 = (float) entity.shakeTime - p_113841_;
        if (f9 > 0.0F) {
            float f10 = -Mth.sin(f9 * 3.0F) * f9;
            poseStack.mulPose(Axis.XP.rotationDegrees(f10));
        }

        VertexConsumer ivertexbuilder = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
        this.model.renderToBuffer(poseStack, ivertexbuilder, p_113844_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.scale(0.05625F, 0.05625F, 0.05625F);

        poseStack.popPose();
        super.render(entity, p_113840_, p_113841_, poseStack, buffer, p_113844_);
    }

}
