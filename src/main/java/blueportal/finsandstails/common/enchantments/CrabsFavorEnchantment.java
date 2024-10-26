package blueportal.finsandstails.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class CrabsFavorEnchantment extends Enchantment {
    public CrabsFavorEnchantment(Rarity rarity, EnchantmentCategory type, EquipmentSlot[] slots) {
        super(rarity, type, slots);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    protected boolean checkCompatibility(Enchantment ench) {
        return ench != Enchantments.MOB_LOOTING;
    }
}
