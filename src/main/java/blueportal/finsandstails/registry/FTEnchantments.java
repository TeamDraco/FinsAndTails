package blueportal.finsandstails.registry;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.enchantments.CrabsFavorEnchantment;
import blueportal.finsandstails.common.enchantments.FlukedEdgeEnchantment;
import blueportal.finsandstails.common.enchantments.UppercuttingEnchantment;

public class FTEnchantments {
    public static final DeferredRegister<Enchantment> REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, FinsAndTails.MOD_ID);

    public static final RegistryObject<Enchantment> FLUKED_EDGE = REGISTER.register("fluked_edge", () -> new FlukedEdgeEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET}));
    public static final RegistryObject<Enchantment> UPPERCUTTING = REGISTER.register("uppercutting", () -> new UppercuttingEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}));
    public static final RegistryObject<Enchantment> CRABS_FAVOR = REGISTER.register("crabs_favor", () -> new CrabsFavorEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}));
}
