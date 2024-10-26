package blueportal.finsandstails.client.model.armor;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.items.ArmoredGopjetJetpackItem;

public class HorateeJetpackModel extends GeoModel<ArmoredGopjetJetpackItem> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(FinsAndTails.MOD_ID, "textures/armor/horatee_jetpack.png");
    public static final ResourceLocation MODEL = new ResourceLocation(FinsAndTails.MOD_ID, "geo/armor/horatee_jetpack.geo.json");

    public static final ResourceLocation ANIMATION = new ResourceLocation(FinsAndTails.MOD_ID, "animation/armor/gopjet_jetpack.animation.json");

    @Override
    public ResourceLocation getModelResource(ArmoredGopjetJetpackItem object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(ArmoredGopjetJetpackItem object) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(ArmoredGopjetJetpackItem animatable) {
        return ANIMATION;
    }
}
