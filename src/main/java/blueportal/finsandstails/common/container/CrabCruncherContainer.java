package blueportal.finsandstails.common.container;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import blueportal.finsandstails.common.crafting.CrunchingRecipe;
import blueportal.finsandstails.registry.FTBlocks;
import blueportal.finsandstails.registry.FTContainers;
import blueportal.finsandstails.registry.FTRecipes;

import java.util.List;

public class CrabCruncherContainer extends ItemCombinerMenu {
    private final List<CrunchingRecipe> recipes;
    private final ContainerLevelAccess access;
    private final Player player;
    private CrunchingRecipe selectedRecipe;
    private final Level level;

    public CrabCruncherContainer(int windowId, Inventory playerInventory) {
        this(windowId, playerInventory, ContainerLevelAccess.NULL);
    }

    public CrabCruncherContainer(int windowId, Inventory playerInventory, ContainerLevelAccess access) {
        super(FTContainers.CRAB_CRUNCHER.get(), windowId, playerInventory, access);
        this.player = playerInventory.player;
        this.access = access;
        this.level = playerInventory.player.level();
        this.recipes = player.level().getRecipeManager().getAllRecipesFor(FTRecipes.CRUNCHING_TYPE.get());
    }

    @Override
    protected boolean isValidBlock(BlockState p_40266_) {
        return p_40266_.is(FTBlocks.CRAB_CRUNCHER.get());
    }

    @Override
    protected boolean mayPickup(Player p_40268_, boolean p_40269_) {
        return this.selectedRecipe != null && this.selectedRecipe.matches(this.inputSlots, player.level());
    }

    @Override
    protected void onTake(Player player, ItemStack stack) {
        stack.onCraftedBy(player.level(), player, stack.getCount());
        this.resultSlots.awardUsedRecipes(player, this.getRelevantItems());
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        this.access.execute((p_40263_, p_40264_) -> {
            p_40263_.levelEvent(1044, p_40264_, 0);
        });
    }

    @Override
    public void createResult() {
        List<CrunchingRecipe> list = player.level().getRecipeManager().getRecipesFor(FTRecipes.CRUNCHING_TYPE.get(), this.inputSlots, player.level());
        if (list.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            this.selectedRecipe = list.get(0);
            ItemStack itemstack = this.selectedRecipe.assemble(this.inputSlots, this.level.registryAccess());
            this.resultSlots.setRecipeUsed(this.selectedRecipe);
            this.resultSlots.setItem(0, itemstack);
        }

    }

    @Override
    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 27, 47, stack -> true)
                .withSlot(1, 76, 47, stack -> true)
                .withResultSlot(2, 134, 47)
                .build();
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    private List<ItemStack> getRelevantItems() {
        return List.of(this.inputSlots.getItem(0), this.inputSlots.getItem(1));
    }

    private void shrinkStackInSlot(int p_40271_) {
        ItemStack itemstack = this.inputSlots.getItem(p_40271_);
        itemstack.shrink(1);
        this.inputSlots.setItem(p_40271_, itemstack);
    }
}

