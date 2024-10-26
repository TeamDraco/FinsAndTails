package blueportal.finsandstails.common.entities.ai.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import blueportal.finsandstails.common.entities.WeeEntity;
import blueportal.finsandstails.common.entities.VibraWeeEntity;
import blueportal.finsandstails.common.entities.WeeWeeEntity;

public class PapaWeeAttractionGoal extends Goal {
    private final Mob entity;

    public PapaWeeAttractionGoal(Mob entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return entity.tickCount % 60 == 0 && entity.getPassengers().isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        return entity.tickCount % 80 != 0;
    }

    @Override
    public void start() {
        super.start();
        for (Mob mob : entity.level().getEntitiesOfClass(Mob.class, entity.getBoundingBox().inflate(12.0D), e -> e != entity && e.getVehicle() == null)) {
            if (mob instanceof WeeWeeEntity || mob instanceof VibraWeeEntity || mob instanceof WeeEntity) {
                mob.getNavigation().moveTo(entity, mob.getSpeed() * 1.25D);
            }
        }
    }
}