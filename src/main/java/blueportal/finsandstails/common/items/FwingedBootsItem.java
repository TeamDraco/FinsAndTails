package blueportal.finsandstails.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.client.model.armor.FwingedBootsModel;
import blueportal.finsandstails.client.render.ArmorItemRenderer;
import blueportal.finsandstails.registry.FTEnchantments;

import java.util.function.Consumer;

public class FwingedBootsItem extends ArmorItem implements GeoItem {
    public static final ArmorMaterial MATERIAL = new FinsArmorMaterial(FinsAndTails.MOD_ID + ":fwinged", 3, new int[]{1, 2, 3, 1}, 3, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, () -> Ingredient.of(Items.LEATHER));
    public static final Lazy<Multimap<Attribute, AttributeModifier>> MODIFIERS = Lazy.of(() ->
            ImmutableMultimap.of(
                    ForgeMod.SWIM_SPEED.get(), new AttributeModifier("Swim modifier", 1.25D, AttributeModifier.Operation.ADDITION),
                    Attributes.MOVEMENT_SPEED, new AttributeModifier("Movement modifier", -0.15D, AttributeModifier.Operation.MULTIPLY_BASE)
            ));
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public FwingedBootsItem() {
        super(MATERIAL, Type.BOOTS, new Properties());
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private ArmorItemRenderer<FwingedBootsItem> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    this.renderer = new ArmorItemRenderer<>(new FwingedBootsModel());
                }
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public void onArmorTick(ItemStack stack, Level worldIn, Player player) {
        int j = EnchantmentHelper.getItemEnchantmentLevel(FTEnchantments.FLUKED_EDGE.get(), stack);

        if (EnchantmentHelper.getEnchantments(stack).containsKey(FTEnchantments.FLUKED_EDGE.get())) {
            if (j <= 0 || worldIn.getBlockState(player.blockPosition().below()).is(Blocks.WATER) && worldIn.getBlockState(player.blockPosition()).isAir() && player.getDeltaMovement().y > 0.25) {
                if (j > 0) {
                    float f7 = player.getYRot();
                    float f = player.getXRot();
                    float f1 = -Mth.sin(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
                    float f2 = -Mth.sin(f * ((float)Math.PI / 180F));
                    float f3 = Mth.cos(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
                    float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                    float f5 = 3.0F * ((1.0F + (float)j) / 4.0F);
                    f1 = f1 * (f5 / f4);
                    f2 = f2 * (f5 / f4);
                    f3 = f3 * (f5 / f4);
                    if (j > 1) player.push((double) f1 / 3.5, (double) f2 / 2, (double) f3 / 3.5);
                    else player.push((double) f1 / 1.5, (double) f2 / 2, (double) f3 / 1.5);
                    player.startAutoSpinAttack(1);

                    if (player.onGround()) {
                        player.move(MoverType.SELF, new Vec3(0.0D, 1.1999999F, 0.0D));
                    }
                }
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.FEET ? MODIFIERS.get() : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment != Enchantments.DEPTH_STRIDER && super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

}
