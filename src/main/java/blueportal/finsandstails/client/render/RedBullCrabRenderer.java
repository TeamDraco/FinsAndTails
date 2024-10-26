
package blueportal.finsandstails.client.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import blueportal.finsandstails.client.model.RedBullCrabModel;
import blueportal.finsandstails.common.entities.RedBullCrabEntity;

public class RedBullCrabRenderer extends GeoEntityRenderer<RedBullCrabEntity> {

    public RedBullCrabRenderer(EntityRendererProvider.Context context) {
        super(context, new RedBullCrabModel());
        this.shadowRadius = 0.2F;
    }

    @Override
    public RenderType getRenderType(RedBullCrabEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutout(texture);
    }
}