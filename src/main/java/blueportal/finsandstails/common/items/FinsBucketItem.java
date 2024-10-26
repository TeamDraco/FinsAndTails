package blueportal.finsandstails.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class FinsBucketItem extends MobBucketItem {
    private final Supplier<? extends EntityType<?>> entityTypeSupplier;
    private final boolean hasTooltip;

    public FinsBucketItem(Supplier<? extends EntityType<?>> entityType, Supplier<? extends Fluid> fluid, Properties builder) {
        this(entityType, fluid, builder, false);
    }

    public FinsBucketItem(Supplier<? extends EntityType<?>> entityType, Supplier<? extends Fluid> fluid, Properties builder, boolean hasTooltip) {
        super(entityType, fluid, () -> SoundEvents.BUCKET_EMPTY_FISH,builder);
        this.hasTooltip = hasTooltip;
        this.entityTypeSupplier = entityType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (this.getFluid() == Fluids.EMPTY) {
            //for penglil
            ItemStack itemstack = playerIn.getItemInHand(handIn);
            BlockHitResult result = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.NONE);
            InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, result);
            if (ret != null) return ret;
            if (result.getType() == BlockHitResult.Type.MISS) {
                return InteractionResultHolder.pass(itemstack);
            } else if (result.getType() != BlockHitResult.Type.BLOCK) {
                return InteractionResultHolder.pass(itemstack);
            }
            BlockPos blockpos = result.getBlockPos();
            Direction direction = result.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            this.checkExtraContent(playerIn, worldIn, itemstack, blockpos1);
            if (playerIn instanceof ServerPlayer) {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)playerIn, blockpos1, itemstack);
            }
            playerIn.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(itemstack, playerIn), worldIn.isClientSide());
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        if (hasTooltip && stack.hasTag()) {
            tooltip.add(Component.translatable(this.entityTypeSupplier.get().getDescriptionId() + "." + stack.getTag().getInt("Variant")).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        }
    }

}
