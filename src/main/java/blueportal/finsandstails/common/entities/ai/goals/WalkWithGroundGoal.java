package blueportal.finsandstails.common.entities.ai.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class WalkWithGroundGoal extends RandomStrollGoal {
	private final PathfinderMob fish;

	public WalkWithGroundGoal(PathfinderMob p_27505_) {
		super(p_27505_, 1.0D, 120);
		this.fish = p_27505_;
	}

	public boolean canUse() {
		return (this.fish.onGround() && this.fish.isInWater() || !this.fish.isInWater()) && super.canUse();
	}
}
