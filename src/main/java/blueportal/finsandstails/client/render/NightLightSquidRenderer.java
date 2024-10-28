package blueportal.finsandstails.client.render;

import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.client.model.NightLightSquidModel;
import blueportal.finsandstails.client.render.layer.FTGlowLayer;
import blueportal.finsandstails.common.entities.NightLightSquidEntity;
import blueportal.finsandstails.registry.FTModelLayers;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Map;

public class NightLightSquidRenderer extends MobRenderer<NightLightSquidEntity, NightLightSquidModel<NightLightSquidEntity>> {
    public static final Map<Integer, ResourceLocation> NIGHT_LIGHT_SQUID_LOCATIONS = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/night_light_squid/night_light_squid_1.png"));
        hashMap.put(1, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/night_light_squid/night_light_squid_2.png"));
        hashMap.put(2, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/night_light_squid/night_light_squid_3.png"));
        hashMap.put(3, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/night_light_squid/night_light_squid_4.png"));
    });
    private static final ResourceLocation NIGHT_LIGHT_SQUID_GLOW_LOCATION = new ResourceLocation(FinsAndTails.MOD_ID,"textures/entity/night_light_squid/night_light_squid_glow.png");

    public NightLightSquidRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new NightLightSquidModel<>(ctx.bakeLayer(FTModelLayers.NIGHT_LIGHT_SQUID)), 0.25f);
        addLayer(new FTGlowLayer<>(this, NIGHT_LIGHT_SQUID_GLOW_LOCATION, (p_234793_, p_234794_, p_234795_) -> {
            return Math.max(0.0F, Mth.cos(p_234795_ * 0.1F));
        }));
    }

    @Override
    public ResourceLocation getTextureLocation(NightLightSquidEntity entity) {
        return NIGHT_LIGHT_SQUID_LOCATIONS.getOrDefault(entity.getVariant(), NIGHT_LIGHT_SQUID_LOCATIONS.get(0));
    }
}
