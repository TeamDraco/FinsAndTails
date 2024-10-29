
package blueportal.finsandstails.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.client.model.WherbleModel;
import blueportal.finsandstails.common.entities.WherbleEntity;
import blueportal.finsandstails.client.FTModelLayers;

public class WherbleRenderer extends MobRenderer<WherbleEntity, WherbleModel<WherbleEntity>> {
    private final WherbleModel<WherbleEntity> adultModel = this.getModel();
    private final WherbleModel<WherbleEntity> babyModel;

    public WherbleRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new WherbleModel<>(ctx.bakeLayer(FTModelLayers.WHERBLE)), 0.3F);
        this.babyModel = new WherbleModel<>(ctx.bakeLayer(FTModelLayers.WHERBLING));
    }

    @Override
    public void render(WherbleEntity entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        this.model = entity.isBaby() ? this.babyModel : this.adultModel;
        super.render(entity, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    public ResourceLocation getTextureLocation(WherbleEntity entity) {
        if (entity.isBaby()) return new ResourceLocation(FinsAndTails.MOD_ID,"textures/entity/wherble/wherbling.png");
        return new ResourceLocation(FinsAndTails.MOD_ID,"textures/entity/wherble/wherble_"+ (entity.getVariant() + 1) +".png");
    }
}
