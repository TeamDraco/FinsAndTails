package blueportal.finsandstails.common.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class PapaWeeItem extends Item {

    public PapaWeeItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        ItemStack itemstack = super.finishUsingItem(stack, world, entity);

        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (!player.isCreative()) {
                if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                    player.setItemInHand(InteractionHand.MAIN_HAND, stack);
                }
                if (!player.getInventory().add(new ItemStack(Items.BONE))) {
                    player.drop(new ItemStack(Items.BONE), false);
                }
            }
        }
        return itemstack;
    }
}
