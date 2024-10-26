package blueportal.finsandstails.client.model.armor;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.items.SpindlyGemCharmItem;

public class SpindlyGemModel extends GeoModel<SpindlyGemCharmItem> {

    @Override
    public ResourceLocation getModelResource(SpindlyGemCharmItem object) {
        return new ResourceLocation(FinsAndTails.MOD_ID, "geo/armor/spindly_charm.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SpindlyGemCharmItem object) {
        return new ResourceLocation(FinsAndTails.MOD_ID, "textures/armor/gem_crab_amulet.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SpindlyGemCharmItem object) {
        return null;
    }
}
