package blueportal.finsandstails.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.WanderingSailorEntity;

public class WanderingSailorModel extends DefaultedEntityGeoModel<WanderingSailorEntity> {

    public WanderingSailorModel() {
        super(new ResourceLocation(FinsAndTails.MOD_ID, "wandering_sailor"));
    }

    @Override
    public ResourceLocation getTextureResource(WanderingSailorEntity entity) {
        return new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/wandering_sailor/wandering_sailor.png");
    }
}
