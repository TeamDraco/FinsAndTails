package blueportal.finsandstails.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.SiderolWhiskeredSnailEntity;

public class SiderolWhiskeredSnailModel extends DefaultedEntityGeoModel<SiderolWhiskeredSnailEntity> {

    public SiderolWhiskeredSnailModel() {
        super(new ResourceLocation(FinsAndTails.MOD_ID, "siderol_whiskered_snail"));
    }
}
