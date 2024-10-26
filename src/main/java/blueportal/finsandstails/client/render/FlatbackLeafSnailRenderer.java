
package blueportal.finsandstails.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import blueportal.finsandstails.client.model.FlatbackLeafSnailModel;
import blueportal.finsandstails.common.entities.FlatbackLeafSnailEntity;

public class FlatbackLeafSnailRenderer extends GeoEntityRenderer<FlatbackLeafSnailEntity> {

    public FlatbackLeafSnailRenderer(EntityRendererProvider.Context context) {
        super(context, new FlatbackLeafSnailModel());
        this.shadowRadius = 0.3F;
    }

    @Override
    public RenderType getRenderType(FlatbackLeafSnailEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public void render(FlatbackLeafSnailEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}