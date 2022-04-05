package teamdraco.finsandstails.client.model;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import teamdraco.finsandstails.FinsAndTails;
import teamdraco.finsandstails.common.entities.BluWeeEntity;
import teamdraco.finsandstails.common.entities.PenglilEntity;

import java.util.Map;

public class PenglilModel extends AnimatedGeoModel<PenglilEntity> {
    public static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/penglil/penglil_1.png"));
        hashMap.put(1, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/penglil/penglil_2.png"));
        hashMap.put(2, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/penglil/penglil_3.png"));
        hashMap.put(3, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/penglil/penglil_4.png"));
        hashMap.put(4, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/penglil/penglil_5.png"));
        hashMap.put(5, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/penglil/penglil_6.png"));
        hashMap.put(6, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/penglil/penglil_7.png"));
        hashMap.put(7, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/penglil/penglil_8.png"));
        hashMap.put(8, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/penglil/lord_penglil.png"));
        hashMap.put(9, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/penglil/pomegranits.png"));
        hashMap.put(10, new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/penglil/sus_penglil.png"));
    });

    @Override
    public ResourceLocation getModelLocation(PenglilEntity entity) {
        return new ResourceLocation(FinsAndTails.MOD_ID, "geo/entity/penglil.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(PenglilEntity entity) {
        String s = entity.getName().getString();

        return switch (s) {
            case "Lord", "Lord Penglil", "Lord_Penglil" -> TEXTURES.get(8);
            case "Pomegranits" -> TEXTURES.get(9);
            case "Sus", "Amogus", "Impostor", "Among Us" -> TEXTURES.get(10);
            default -> TEXTURES.getOrDefault(entity.getVariant(), TEXTURES.get(0));
        };
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PenglilEntity entity) {
        return new ResourceLocation(FinsAndTails.MOD_ID, "animations/entity/penglil.animations.json");
    }
}
