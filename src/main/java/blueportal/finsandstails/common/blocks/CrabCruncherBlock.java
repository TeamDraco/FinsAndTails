package blueportal.finsandstails.common.blocks;

import blueportal.finsandstails.registry.FTBlocks;
import blueportal.finsandstails.registry.FTItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Map;

public class CrabCruncherBlock extends Block {

    public CrabCruncherBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(handIn);

        Map<Item, Item> crabToGem = ImmutableMap.of(
                FTItems.AMBER_SPINDLY_GEM_CRAB.get(), FTItems.SPINDLY_AMBER.get(),
                FTItems.PEARL_SPINDLY_GEM_CRAB.get(), FTItems.SPINDLY_PEARL.get(),
                FTItems.SAPPHIRE_SPINDLY_GEM_CRAB.get(), FTItems.SPINDLY_SAPPHIRE.get(),
                FTItems.RUBY_SPINDLY_GEM_CRAB.get(), FTItems.SPINDLY_RUBY.get(),
                FTItems.EMERALD_SPINDLY_GEM_CRAB.get(), FTItems.SPINDLY_EMERALD.get()
        );

        if (crabToGem.containsKey(stack.getItem())) {
            ItemStack outputStack = new ItemStack(crabToGem.get(stack.getItem()), level.random.nextIntBetweenInclusive(3, 5));

            popResourceFromFace(level, pos, hit.getDirection(), outputStack);

            ExperienceOrb exp = new ExperienceOrb(level, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, level.random.nextIntBetweenInclusive(2, 4));

            level.addFreshEntity(exp);

            level.playLocalSound(pos, SoundEvents.SNIFFER_EGG_CRACK, SoundSource.BLOCKS, 1.0F, 1.0F, true);

            if (level instanceof ServerLevel server) {
                double d0 = 0.5D;
                double d1 = FTBlocks.CRAB_CRUNCHER.get().defaultBlockState().getShape(level, pos).max(Direction.Axis.Y);

                double d2 = 0.0D;
                double d3 = 0.0D;
                double d4 = 0.0D;
                double d6 = (double) pos.getX() + level.random.nextDouble() * d0 * 2.0D;
                double d7 = (double) pos.getY() + level.random.nextDouble() * d1;
                double d8 = (double) pos.getZ() + level.random.nextDouble() * d0 * 2.0D;
                server.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), d6, d7, d8, 100, d2, d3, d4, 0.1D);
            }

            stack.shrink(1);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

/*
    private static final Component CONTAINER_NAME = Component.translatable("container." + FinsAndTails.MOD_ID + ".crab_cruncher");

    @Nullable
    public MenuProvider getMenuProvider(BlockState p_48821_, Level p_48822_, BlockPos p_48823_) {
        return new SimpleMenuProvider((p_48785_, p_48786_, p_48787_) -> new CrabCruncherContainer(p_48785_, p_48786_, ContainerLevelAccess.create(p_48822_, p_48823_)), CONTAINER_NAME);
    }
*/

/*    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof Container) {
                Containers.dropContents(worldIn, pos, (Container)tileentity);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }*/
}
