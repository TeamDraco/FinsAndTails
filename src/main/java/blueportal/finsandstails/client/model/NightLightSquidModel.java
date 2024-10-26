package blueportal.finsandstails.client.model;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.NightLightSquidEntity;

import java.util.Map;

public class NightLightSquidModel extends DefaultedEntityGeoModel<NightLightSquidEntity> {
    public static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/night_light_squid/night_light_squid_1.png"));
        hashMap.put(1, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/night_light_squid/night_light_squid_2.png"));
        hashMap.put(2, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/night_light_squid/night_light_squid_3.png"));
        hashMap.put(3, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/night_light_squid/night_light_squid_4.png"));
        hashMap.put(4, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/night_light_squid/night_light_squid_e.png"));
    });

    public NightLightSquidModel() {
        super(new ResourceLocation(FinsAndTails.MOD_ID, "night_light_squid"));
    }

    @Override
    public ResourceLocation getTextureResource(NightLightSquidEntity squid) {
        return TEXTURES.getOrDefault(squid.getVariant(), TEXTURES.get(0));
    }
}
