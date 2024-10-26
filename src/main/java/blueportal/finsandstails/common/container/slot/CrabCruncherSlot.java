package blueportal.finsandstails.common.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import blueportal.finsandstails.registry.FTBlocks;
import blueportal.finsandstails.registry.FTItems;

public class CrabCruncherSlot extends Slot {
    public CrabCruncherSlot(Container inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return  stack.getItem() == FTItems.AMBER_SPINDLY_GEM_CRAB.get()
                || stack.getItem() == FTItems.RUBY_SPINDLY_GEM_CRAB.get()
                || stack.getItem() == FTItems.EMERALD_SPINDLY_GEM_CRAB.get()
                || stack.getItem() == FTItems.SAPPHIRE_SPINDLY_GEM_CRAB.get()
                || stack.getItem() == FTItems.PEARL_SPINDLY_GEM_CRAB.get()
                || stack.getItem() == FTBlocks.SPINDLY_GEM_BLOCK.get().asItem()
                || stack.getItem() == FTItems.EMPTY_CHARM.get()
                || stack.getItem() == FTItems.SPINDLY_AMBER.get()
                || stack.getItem() == FTItems.SPINDLY_EMERALD.get()
                || stack.getItem() == FTItems.SPINDLY_PEARL.get()
                || stack.getItem() == FTItems.SPINDLY_SAPPHIRE.get()
                || stack.getItem() == FTItems.SPINDLY_RUBY.get()
                || stack.getItem() == Items.BOOK;
    }
}
