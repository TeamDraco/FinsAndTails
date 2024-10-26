package blueportal.finsandstails.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import blueportal.finsandstails.registry.FTItems;

public class FlukedEdgeEnchantment extends Enchantment {
    public FlukedEdgeEnchantment(Rarity rarity, EnchantmentCategory type, EquipmentSlot[] slots) {
        super(rarity, type, slots);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() == FTItems.FWINGED_BOOTS.get();
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
    protected boolean checkCompatibility(Enchantment ench) {
        return !ench.equals(Enchantments.DEPTH_STRIDER);
    }
}
