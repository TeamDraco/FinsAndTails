package blueportal.finsandstails.common.entities.ai.goals;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import blueportal.finsandstails.common.entities.CrownedHorateeEntity;

import java.util.EnumSet;

public class ShareTheBubbleGoal extends Goal {
	private final CrownedHorateeEntity tamable;
	private LivingEntity owner;
	private final Level level;
	private final double speedModifier;
	private int timeToRecalcPath;
	private float oldWaterCost;
	private final PathNavigation navigation;
	private int randomInterval;

	public ShareTheBubbleGoal(CrownedHorateeEntity crownedHoratee, double speed, int randomInterval) {
		this.tamable = crownedHoratee;
		this.level = crownedHoratee.level();
		this.speedModifier = speed;
		this.navigation = crownedHoratee.getNavigation();
		this.randomInterval = randomInterval;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	public boolean canUse() {
		if (this.randomInterval > 0 && this.tamable.getRandom().nextInt(this.randomInterval) != 0) {
			return false;
		} else {
			for (Entity entity : this.level.getEntities(this.tamable, this.tamable.getBoundingBox().inflate(32D), Entity::isAlive)) {
				if (entity instanceof LivingEntity) {
					LivingEntity livingentity = (LivingEntity) entity;
					if (livingentity.getAirSupply() <= 60 && this.tamable.getTarget() == null && this.tamable.hasLineOfSight(livingentity)) {
						this.owner = livingentity;
						break;
					}
				}
			}

		}
		return this.owner != null;
	}

	public boolean canContinueToUse() {
		return this.owner != null && this.owner.getAirSupply() <= (this.tamable.trusts(this.owner.getUUID()) ? this.owner.getMaxAirSupply() : 100);
	}

	public void start() {
		this.timeToRecalcPath = 0;
		this.oldWaterCost = this.tamable.getPathfindingMalus(BlockPathTypes.WATER);
		this.tamable.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.tamable.setBubbleTarget(this.owner.getId());
	}

	public void stop() {
		this.owner = null;
		this.navigation.stop();
		this.tamable.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
		this.tamable.setBubbleCharge(false);
		this.tamable.setBubbleTarget(0);
	}

	public void tick() {
		this.tamable.getLookControl().setLookAt(this.owner, 10.0F, (float) this.tamable.getMaxHeadXRot());
		if (--this.timeToRecalcPath <= 0) {
			this.timeToRecalcPath = this.adjustedTickDelay(10);
			if (!this.tamable.isLeashed() && !this.tamable.isPassenger()) {
				this.navigation.moveTo(this.owner, this.speedModifier);
			}

			if (this.tamable.distanceToSqr(this.owner) <= 16.0D) {
				this.owner.setAirSupply(Mth.clamp(-20, this.owner.getAirSupply() + (this.tamable.trusts(this.owner.getUUID()) ? 20 : 8) + 2 * EnchantmentHelper.getRespiration(this.owner), this.owner.getMaxAirSupply()));
				this.tamable.setBubbleCharge(true);
			} else {
				this.tamable.setBubbleCharge(false);
			}
		}
	}
}