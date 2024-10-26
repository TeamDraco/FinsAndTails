package blueportal.finsandstails.client.model;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.RiverPebbleSnailEntity;

import java.util.Map;

public class RiverPebbleSnailModel extends DefaultedEntityGeoModel<RiverPebbleSnailEntity> {
    public static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/river_pebble_snail/river_pebble_snail_1.png"));
        hashMap.put(1, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/river_pebble_snail/river_pebble_snail_2.png"));
        hashMap.put(2, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/river_pebble_snail/river_pebble_snail_3.png"));
        hashMap.put(3, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/river_pebble_snail/river_pebble_snail_4.png"));
        hashMap.put(4, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/river_pebble_snail/river_pebble_snail_5.png"));
        hashMap.put(5, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/river_pebble_snail/river_pebble_snail_6.png"));
    });

    public RiverPebbleSnailModel() {
        super(new ResourceLocation(FinsAndTails.MOD_ID, "river_pebble_snail"));
    }

    @Override
    public ResourceLocation getTextureResource(RiverPebbleSnailEntity wee) {
        return TEXTURES.getOrDefault(wee.getVariant(), TEXTURES.get(0));
    }

    @Override
    public void setCustomAnimations(RiverPebbleSnailEntity entity, long instanceId, AnimationState<RiverPebbleSnailEntity> customPredicate) {
        super.setCustomAnimations(entity, instanceId, customPredicate);
        CoreGeoBone sparkle = this.getAnimationProcessor().getBone("sparkle");

        sparkle.setHidden(entity.getVariant() != 5 || !entity.getShimmer());
    }
}
