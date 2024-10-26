
package blueportal.finsandstails.client.render;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import blueportal.finsandstails.client.model.FlatbackSuckerModel;
import blueportal.finsandstails.common.entities.FlatbackSuckerEntity;

public class FlatbackSuckerRenderer extends GeoEntityRenderer<FlatbackSuckerEntity> {

    public FlatbackSuckerRenderer(EntityRendererProvider.Context context) {
        super(context, new FlatbackSuckerModel());
        this.shadowRadius = 0.3F;
    }
}