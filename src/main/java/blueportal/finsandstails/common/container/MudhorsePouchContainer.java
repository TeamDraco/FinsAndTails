package blueportal.finsandstails.common.container;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import blueportal.finsandstails.registry.FTContainers;
import blueportal.finsandstails.registry.FTItems;
import blueportal.finsandstails.registry.FTTags;

public class MudhorsePouchContainer extends AbstractContainerMenu {


    private ItemStackHandler handler = new ItemStackHandler(9){
        @Override
        protected void onContentsChanged(int slot) {
            MudhorsePouchContainer.this.writeItemsToStack();
        }
    };

    private ItemStack itemStack;


    public MudhorsePouchContainer(int id, Inventory playerInventory) {
        super(FTContainers.MUDHORSE_POUCH.get(), id);
        this.itemStack = playerInventory.player.getItemInHand(InteractionHand.MAIN_HAND);
        writeItemsToHandler();
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new SlotItemHandler(handler, j + i * 3, 62 + j * 18, 17 + i * 18) {

                    @Override
                    public void setChanged() {
                        super.setChanged();
                        MudhorsePouchContainer.this.writeItemsToStack();
                    }

                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return super.mayPlace(stack) && !stack.is(FTTags.MUDHORSE_POUCH_BLACKLIST);
                    }
                });
            }
        }

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
        }

    }



    public void writeItemsToStack() {
        itemStack.setTag(this.handler.serializeNBT());
    }

    public void writeItemsToHandler() {
        if(this.itemStack.getTag() != null && this.itemStack.getTag().contains("Items") &&  this.itemStack.getTag().contains("Size")) {
            handler.deserializeNBT(this.itemStack.getTag());
        }
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return playerIn.getItemInHand(playerIn.getUsedItemHand()).is(FTItems.MUDHORSE_POUCH.get());
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack resultStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            resultStack = slotStack.copy();
            if (index < 9) {
                if (!this.moveItemStackTo(slotStack, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == resultStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotStack);
        }

        return resultStack;
    }
}
