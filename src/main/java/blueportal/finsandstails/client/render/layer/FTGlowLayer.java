package blueportal.finsandstails.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FTGlowLayer<T extends LivingEntity, M extends HierarchicalModel<T>> extends RenderLayer<T, M> {
    private final FTGlowLayer.AlphaFunction<T> alphaFunction;
    private final ResourceLocation glowLayer;

    public FTGlowLayer(RenderLayerParent<T, M> layer, ResourceLocation glowOverlayResourceLocation, FTGlowLayer.AlphaFunction<T> p_234887_) {
        super(layer);
        this.glowLayer = glowOverlayResourceLocation;
        this.alphaFunction = p_234887_;
    }

    public void render(PoseStack p_234902_, MultiBufferSource p_234903_, int p_234904_, T p_234905_, float p_234906_, float p_234907_, float p_234908_, float p_234909_, float p_234910_, float p_234911_) {
        if (!p_234905_.isInvisible()) {
            VertexConsumer vertexconsumer = p_234903_.getBuffer(RenderType.entityTranslucentEmissive(this.glowLayer));
            this.getParentModel().renderToBuffer(p_234902_, vertexconsumer, p_234904_, LivingEntityRenderer.getOverlayCoords(p_234905_, 0.0F), 1.0F, 1.0F, 1.0F, this.alphaFunction.apply(p_234905_, p_234908_, p_234909_));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface AlphaFunction<T extends LivingEntity> {
        float apply(T p_234920_, float p_234921_, float p_234922_);
    }
}