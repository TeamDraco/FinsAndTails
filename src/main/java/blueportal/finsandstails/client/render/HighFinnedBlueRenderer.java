
package blueportal.finsandstails.client.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import blueportal.finsandstails.client.model.HighFinnedBlueModel;
import blueportal.finsandstails.common.entities.HighFinnedBlueEntity;

public class HighFinnedBlueRenderer extends GeoEntityRenderer<HighFinnedBlueEntity> {

    public HighFinnedBlueRenderer(EntityRendererProvider.Context context) {
        super(context, new HighFinnedBlueModel());
        this.shadowRadius = 0.3F;
    }

    @Override
    public RenderType getRenderType(HighFinnedBlueEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutout(texture);
    }
}