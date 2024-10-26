
package blueportal.finsandstails.client.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import blueportal.finsandstails.client.model.NightLightSquidModel;
import blueportal.finsandstails.client.render.layer.NightLightSquidGlowLayer;
import blueportal.finsandstails.common.entities.NightLightSquidEntity;

public class NightlightSquidRenderer extends GeoEntityRenderer<NightLightSquidEntity> {

    public NightlightSquidRenderer(EntityRendererProvider.Context context) {
        super(context, new NightLightSquidModel());
        this.addRenderLayer(new NightLightSquidGlowLayer(this));
        this.shadowRadius = 0.3F;
    }

    @Override
    public RenderType getRenderType(NightLightSquidEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutout(texture);
    }
}