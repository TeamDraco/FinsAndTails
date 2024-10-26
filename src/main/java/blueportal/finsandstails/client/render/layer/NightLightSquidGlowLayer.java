package blueportal.finsandstails.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.NightLightSquidEntity;

public class NightLightSquidGlowLayer extends GeoRenderLayer<NightLightSquidEntity> {
    private static final ResourceLocation OVERLAY = new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/night_light_squid/night_light_squid_e.png");
    private static final ResourceLocation MODEL = new ResourceLocation(FinsAndTails.MOD_ID, "geo/entity/night_light_squid.geo.json");

    public NightLightSquidGlowLayer(GeoRenderer<NightLightSquidEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, NightLightSquidEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType cameo = RenderType.eyes(OVERLAY);
        this.getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), poseStack, bufferSource, animatable, cameo, bufferSource.getBuffer(cameo), partialTick, 15000, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

}
