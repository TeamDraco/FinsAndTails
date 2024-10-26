
package blueportal.finsandstails.client.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import blueportal.finsandstails.client.model.PapaWeeModel;
import blueportal.finsandstails.common.entities.PapaWeeEntity;

public class PapaWeeRenderer extends GeoEntityRenderer<PapaWeeEntity> {

    public PapaWeeRenderer(EntityRendererProvider.Context context) {
        super(context, new PapaWeeModel());
        this.shadowRadius = 0.25F;
    }

    @Override
    public RenderType getRenderType(PapaWeeEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutout(texture);
    }
}