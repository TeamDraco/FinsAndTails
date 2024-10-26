
package blueportal.finsandstails.client.render;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.client.model.MudhorseModel;
import blueportal.finsandstails.common.entities.MudhorseEntity;
import blueportal.finsandstails.registry.FTModelLayers;

@OnlyIn(Dist.CLIENT)
public class MudhorseRenderer extends MobRenderer<MudhorseEntity, MudhorseModel<MudhorseEntity>> {
    private static final ResourceLocation MUDHORSE_LOCATION = new ResourceLocation(FinsAndTails.MOD_ID,"textures/entity/mudhorse/mudhorse.png");
    private static final ResourceLocation MUDHORSE_POUCH_LOCATION = new ResourceLocation(FinsAndTails.MOD_ID,"textures/entity/mudhorse/mudhorse_pouch.png");

    public MudhorseRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new MudhorseModel<>(ctx.bakeLayer(FTModelLayers.MUDHORSE)), 0.8F);
    }

    @Override
    public ResourceLocation getTextureLocation(MudhorseEntity entity) {
        return MUDHORSE_LOCATION;
    }
}
