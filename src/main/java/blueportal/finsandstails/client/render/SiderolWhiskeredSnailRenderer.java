
package blueportal.finsandstails.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import blueportal.finsandstails.client.model.SiderolWhiskeredSnailModel;
import blueportal.finsandstails.common.entities.SiderolWhiskeredSnailEntity;

public class SiderolWhiskeredSnailRenderer extends GeoEntityRenderer<SiderolWhiskeredSnailEntity> {

    public SiderolWhiskeredSnailRenderer(EntityRendererProvider.Context context) {
        super(context, new SiderolWhiskeredSnailModel());
        this.shadowRadius = 0.2F;
    }

    @Override
    public void render(SiderolWhiskeredSnailEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}