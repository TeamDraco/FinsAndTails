package blueportal.finsandstails.common.entities.ai.control;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathFinder;

public class GroundAndSwimmerNavigator extends WaterBoundPathNavigation {

    public GroundAndSwimmerNavigator(PathfinderMob entity, Level world) {
        super(entity, world);
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }

    @Override
    protected PathFinder createPathFinder(int p_179679_1_) {
        this.nodeEvaluator = new AmphibiousNodeEvaluator(true);
        return new PathFinder(this.nodeEvaluator, p_179679_1_);
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        BlockPos blockPos = pos.below();
        BlockState state = this.level.getBlockState(blockPos);
        return this.level.getBlockState(pos).is(Blocks.WATER) || !state.isAir();
    }
}
