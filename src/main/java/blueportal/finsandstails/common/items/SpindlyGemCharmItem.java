package blueportal.finsandstails.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.client.model.armor.SpindlyGemModel;
import blueportal.finsandstails.client.render.ArmorItemRenderer;

import java.util.List;
import java.util.function.Consumer;

public class SpindlyGemCharmItem extends ArmorItem implements GeoItem {
    public static final ArmorMaterial MATERIAL = new FinsArmorMaterial(FinsAndTails.MOD_ID + ":gem_crab_amulet", 1, new int[]{1, 2, 3, 1}, 3, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, () -> Ingredient.EMPTY);
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public SpindlyGemCharmItem() {
        super(MATERIAL, Type.CHESTPLATE, new Properties().durability(2).rarity(Rarity.RARE));
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable(stack.getItem().getDescriptionId() + ".desc").withStyle(ChatFormatting.DARK_AQUA));
            components.add(Component.translatable(stack.getItem().getDescriptionId() + ".desc.2").withStyle(ChatFormatting.DARK_AQUA));
            components.add(Component.translatable(stack.getItem().getDescriptionId() + ".desc.3").withStyle(ChatFormatting.DARK_AQUA));
        } else {
            components.add(Component.translatable("finsandtails.info").withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private ArmorItemRenderer<SpindlyGemCharmItem> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    this.renderer = new ArmorItemRenderer<>(new SpindlyGemModel());
                }
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (player.isAlive() && isUsable(stack) && player.getHealth() <= 8.0F) {
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 1200, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 0, false, false, true));
            stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(EquipmentSlot.CHEST));
        }
    }

    public static boolean isUsable(ItemStack stack) {
        return !stack.isDamaged();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

}