package blueportal.finsandstails.common.entities.ai.base;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

import java.util.List;

public interface IPanickableSchooling {

    AvoidEntityGoal<?> avoidGoal();

    List<Class<? extends Entity>> toAvoid();
}
