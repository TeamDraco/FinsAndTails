package blueportal.finsandstails.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.WhiteBullCrabEntity;

public class WhiteBullCrabModel extends DefaultedEntityGeoModel<WhiteBullCrabEntity> {

    public WhiteBullCrabModel() {
        super(new ResourceLocation(FinsAndTails.MOD_ID, "bull_crab"));
    }

    @Override
    public ResourceLocation getTextureResource(WhiteBullCrabEntity entity) {
        return new ResourceLocation(FinsAndTails.MOD_ID, "textures/entity/white_bull_crab.png");
    }
}