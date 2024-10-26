package blueportal.finsandstails.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.FlatbackSuckerEntity;

public class FlatbackSuckerModel extends DefaultedEntityGeoModel<FlatbackSuckerEntity> {

    public FlatbackSuckerModel() {
        super(new ResourceLocation(FinsAndTails.MOD_ID, "flatback_sucker"));
    }
}
