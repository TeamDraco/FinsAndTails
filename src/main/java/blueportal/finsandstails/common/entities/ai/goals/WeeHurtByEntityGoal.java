package blueportal.finsandstails.common.entities.ai.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.AbstractFish;
import blueportal.finsandstails.common.entities.PapaWeeEntity;

import java.util.List;

public class WeeHurtByEntityGoal extends Goal {
    private final AbstractFish wee;

    public WeeHurtByEntityGoal(AbstractFish owner) {
        this.wee = owner;
    }

    @Override
    public boolean canUse() {
        return wee.getLastHurtByMob() != null;
    }

    @Override
    public void start() {
        List<PapaWeeEntity> list = wee.level().getEntitiesOfClass(PapaWeeEntity.class, wee.getBoundingBox().inflate(7.5D));
        LivingEntity attacker = wee.getLastHurtByMob();

        if (!list.isEmpty()) {
            for (PapaWeeEntity papaWee : list) {
                papaWee.setTarget(attacker);
            }
        }

        super.start();
    }
}
