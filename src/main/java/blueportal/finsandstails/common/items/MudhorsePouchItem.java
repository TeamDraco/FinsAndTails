package blueportal.finsandstails.common.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import blueportal.finsandstails.common.container.MudhorsePouchContainer;

public class MudhorsePouchItem extends Item {
    public MudhorsePouchItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide && hand == InteractionHand.MAIN_HAND) {
            player.openMenu(new SimpleMenuProvider((windowId, inventory, owner) -> new MudhorsePouchContainer(windowId, inventory), stack.getHoverName()));
        }
        return InteractionResultHolder.success(stack);
    }

    public boolean canFitInsideContainerItems() {
        return false;
    }
}
