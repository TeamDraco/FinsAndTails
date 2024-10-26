
package blueportal.finsandstails.client.render;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import blueportal.finsandstails.client.model.WanderingSailorModel;
import blueportal.finsandstails.common.entities.WanderingSailorEntity;

public class WanderingSailorRenderer extends GeoEntityRenderer<WanderingSailorEntity> {

    public WanderingSailorRenderer(EntityRendererProvider.Context context) {
        super(context, new WanderingSailorModel());
        this.shadowRadius = 0.45F;
    }
}