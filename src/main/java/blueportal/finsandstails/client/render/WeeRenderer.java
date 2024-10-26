
package blueportal.finsandstails.client.render;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.client.model.WeeModel;
import blueportal.finsandstails.common.entities.WeeEntity;
import blueportal.finsandstails.registry.FTModelLayers;

public class WeeRenderer extends MobRenderer<WeeEntity, WeeModel<WeeEntity>> {
    private static final ResourceLocation BLU_WEE_LOCATION = new ResourceLocation(FinsAndTails.MOD_ID,"textures/entity/wee/blu_wee.png");
    private static final ResourceLocation PEA_WEE_LOCATION = new ResourceLocation(FinsAndTails.MOD_ID,"textures/entity/wee/pea_wee.png");
    private static final ResourceLocation MUCK_WEE_LOCATION = new ResourceLocation(FinsAndTails.MOD_ID,"textures/entity/wee/muck_wee.png");

    public WeeRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new WeeModel<>(ctx.bakeLayer(FTModelLayers.WEE)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(WeeEntity entity) {

        return switch (entity.getVariant()) {
            case 1 -> PEA_WEE_LOCATION;
            case 2 -> MUCK_WEE_LOCATION;
            default -> BLU_WEE_LOCATION;
        };
    }
}
