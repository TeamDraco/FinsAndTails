package blueportal.finsandstails.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.WherbleEntity;
import blueportal.finsandstails.common.entities.item.TealArrowfishArrowEntity;
import blueportal.finsandstails.registry.FTEntities;
import blueportal.finsandstails.registry.FTItems;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = FinsAndTails.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {

    @SubscribeEvent
    public static void registerCommon(FMLCommonSetupEvent event) {
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(FTItems.NIGHT_LIGHT_SQUID_TENTACLE.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.NIGHT_VISION));

        DispenserBlock.registerBehavior(FTItems.WHERBLING.get(), new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                ServerLevel level = source.getLevel();
                BlockPos pos = source.getPos();
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);

                WherbleEntity wherble = new WherbleEntity(FTEntities.WHERBLE.get(), level);
                UUID id = wherble.getUUID();
                wherble.deserializeNBT(stack.getOrCreateTag().getCompound("WherbleData"));
                wherble.setUUID(id);
                wherble.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
                wherble.setProjectile(true);
                wherble.shoot(direction.getStepX(), ((float)direction.getStepY() + 0.1F), direction.getStepZ(), 3.0F, 0.0F);

                if (!wherble.isBaby()) {
                    wherble.setBaby(true);
                    wherble.setAge(-24000);
                }
                level.addFreshEntity(wherble);
                return ItemStack.EMPTY;
            }
        });

        DispenserBlock.registerBehavior(FTItems.TEAL_ARROWFISH.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position pos, ItemStack stack) {
                TealArrowfishArrowEntity arrow = new TealArrowfishArrowEntity(level, pos.x(), pos.y(), pos.z());
                arrow.pickup = AbstractArrow.Pickup.ALLOWED;
                return arrow;
            }
        });
    }
}
