package blueportal.finsandstails.client.render;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.client.model.PhantomNudibranchModel;
import blueportal.finsandstails.common.entities.PhantomNudibranchEntity;
import blueportal.finsandstails.client.FTModelLayers;

public class PhantomNudibranchRenderer extends MobRenderer<PhantomNudibranchEntity, PhantomNudibranchModel<PhantomNudibranchEntity>> {
    private static final ResourceLocation PHANTOM_NUDIBRANCH_LOCATION = new ResourceLocation(FinsAndTails.MOD_ID,"textures/entity/phantom_nudibranch/phantom_nudibranch.png");

    public PhantomNudibranchRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new PhantomNudibranchModel<>(ctx.bakeLayer(FTModelLayers.PHANTOM_NUDIBRANCH)), 0.2f);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(PhantomNudibranchEntity p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
        return RenderType.dragonExplosionAlpha(PHANTOM_NUDIBRANCH_LOCATION);
    }

    @Override
    public ResourceLocation getTextureLocation(PhantomNudibranchEntity entity) {
        return PHANTOM_NUDIBRANCH_LOCATION;
    }
}
