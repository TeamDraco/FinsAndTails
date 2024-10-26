
package blueportal.finsandstails.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import blueportal.finsandstails.client.model.RiverPebbleSnailModel;
import blueportal.finsandstails.common.entities.RiverPebbleSnailEntity;

public class RiverPebbleSnailRenderer extends GeoEntityRenderer<RiverPebbleSnailEntity> {

    public RiverPebbleSnailRenderer(EntityRendererProvider.Context context) {
        super(context, new RiverPebbleSnailModel());
        this.shadowRadius = 0.2F;
    }

    @Override
    public RenderType getRenderType(RiverPebbleSnailEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public void render(RiverPebbleSnailEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}