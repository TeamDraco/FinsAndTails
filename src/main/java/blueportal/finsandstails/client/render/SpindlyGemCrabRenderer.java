package blueportal.finsandstails.client.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import blueportal.finsandstails.client.model.SpindlyGemCrabModel;
import blueportal.finsandstails.common.entities.SpindlyGemCrabEntity;

public class SpindlyGemCrabRenderer extends GeoEntityRenderer<SpindlyGemCrabEntity> {

    public SpindlyGemCrabRenderer(EntityRendererProvider.Context context) {
        super(context, new SpindlyGemCrabModel());
        this.shadowRadius = 0.3F;
    }

    @Override
    public RenderType getRenderType(SpindlyGemCrabEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}