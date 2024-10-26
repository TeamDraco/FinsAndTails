
package blueportal.finsandstails.client.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import blueportal.finsandstails.client.model.WeeWeeModel;
import blueportal.finsandstails.common.entities.WeeWeeEntity;

public class WeeWeeRenderer extends GeoEntityRenderer<WeeWeeEntity> {

    public WeeWeeRenderer(EntityRendererProvider.Context context) {
        super(context, new WeeWeeModel());
        this.shadowRadius = 0.1F;
    }

    @Override
    public RenderType getRenderType(WeeWeeEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}