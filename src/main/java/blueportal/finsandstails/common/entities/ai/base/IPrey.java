package blueportal.finsandstails.common.entities.ai.base;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public interface IPrey {

    TagKey<EntityType<?>> getPredatorsTag();
}
