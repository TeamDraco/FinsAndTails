package blueportal.finsandstails.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import blueportal.finsandstails.client.model.armor.GopjetpackModel;
import blueportal.finsandstails.common.items.GopjetpackItem;
import blueportal.finsandstails.registry.FTModelLayers;

public class FTArmorRenderProperties implements IClientItemExtensions {
    private static boolean init;
    public static GopjetpackModel<LivingEntity> GOPJETPACK_MODEL;

    public static void initializeModels() {
        init = true;
        GOPJETPACK_MODEL = new GopjetpackModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(FTModelLayers.GOPJETPACK));
    }

    @Override
    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
        if (!init) {
            initializeModels();
        }
        if (itemStack.getItem() instanceof GopjetpackItem) {
            return entityLiving == null ? GOPJETPACK_MODEL : GOPJETPACK_MODEL.withAnimations(entityLiving);
        }
        return _default;
    }

    public static void renderCustomArmor(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, ItemStack itemStack, ArmorItem armorItem, Model armorModel, boolean legs, ResourceLocation texture) {
        //if(armorItem.getMaterial() == ACItemRegistry.DARKNESS_ARMOR_MATERIAL){
            VertexConsumer vertexconsumer1 = itemStack.hasFoil() ? VertexMultiConsumer.create(multiBufferSource.getBuffer(RenderType.entityGlintDirect()), multiBufferSource.getBuffer(RenderType.entityTranslucent(texture))) : multiBufferSource.getBuffer(RenderType.entityTranslucent(texture));
            armorModel.renderToBuffer(poseStack, vertexconsumer1, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        //}
    }
}