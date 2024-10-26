package blueportal.finsandstails.client.model.armor;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.items.SpindlyCharmItem;

public class SpindlyCharmModel extends GeoModel<SpindlyCharmItem> {

    @Override
    public ResourceLocation getModelResource(SpindlyCharmItem object) {
        return new ResourceLocation(FinsAndTails.MOD_ID, "geo/armor/spindly_charm.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SpindlyCharmItem object) {
        return new ResourceLocation(FinsAndTails.MOD_ID, "textures/armor/spindly_" + object.getTypeName() + "_charm.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SpindlyCharmItem object) {
        return null;
    }
}
