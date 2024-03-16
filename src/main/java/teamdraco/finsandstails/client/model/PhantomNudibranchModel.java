package teamdraco.finsandstails.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import teamdraco.finsandstails.FinsAndTails;
import teamdraco.finsandstails.common.entities.PhantomNudibranchEntity;

public class PhantomNudibranchModel extends GeoModel<PhantomNudibranchEntity> {

    @Override
    public ResourceLocation getModelResource(PhantomNudibranchEntity wee) {
        return new ResourceLocation(FinsAndTails.MOD_ID, "geo/entity/phantom_nudibranch.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PhantomNudibranchEntity wee) {
        return new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/phantom_nudibranch.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PhantomNudibranchEntity wee) {
        return new ResourceLocation(FinsAndTails.MOD_ID, "animations/entity/phantom_nudibranch.animations.json");
    }
}
