package teamdraco.finsandstails.common.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.event.ForgeEventFactory;
import teamdraco.finsandstails.common.entities.MudhorseEntity;

import java.util.EnumSet;

public class MudhorseForageGoal extends Goal {
    // private static final ResourceLocation FORAGING_LOOT = new ResourceLocation(FinsAndTails.MOD_ID, "gameplay/mudhorse_foraging");
    private final MudhorseEntity mudhorse;
    private final Level level;
    private int timer;
    private final int cooldown = 200;
    private int cooldownTimer;

    public MudhorseForageGoal(MudhorseEntity p_i45314_1_) {
        this.mudhorse = p_i45314_1_;
        this.level = p_i45314_1_.level;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        BlockPos pos = this.mudhorse.blockPosition();
        return this.level.getFluidState(pos).is(Fluids.WATER) && this.level.getBlockState(pos.below()).is(Blocks.SAND) ;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        this.timer = 0;
        this.cooldownTimer = 0;
        this.mudhorse.setForaging(false);
    }

    @Override
    public void tick() {
        System.out.println("Timer: " + timer);
        System.out.println("Cooldown Timer: " + cooldownTimer);
        if (canUse()) {
            if (this.cooldownTimer < cooldown) {
                this.cooldownTimer++;
            } else {
                if (this.timer <= 40) {
                    this.timer++;
                    this.mudhorse.setForaging(true);
                    this.mudhorse.getNavigation().stop();
                    if (this.timer == 40) {
                        BlockPos pos = mudhorse.blockPosition();
                        if (this.level.getBlockState(pos.below()).is(Blocks.SAND) && this.level.getBlockState(pos).is(Blocks.WATER)) {
                            if (ForgeEventFactory.getMobGriefingEvent(this.level, this.mudhorse)) {
                                this.level.levelEvent(2001, pos, Block.getId(Blocks.SAND.defaultBlockState()));
                            }
                            System.out.println("big ones");
                        }
                    }
                } else {
                    this.mudhorse.setForaging(false);
                    this.timer = 0;
                    this.cooldownTimer = 0;
                }
            }
        } else {
            this.stop();
        }
    }
}
