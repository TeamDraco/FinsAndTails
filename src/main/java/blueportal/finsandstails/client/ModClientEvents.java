package blueportal.finsandstails.client;

import com.google.common.reflect.Reflection;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.client.model.*;
import blueportal.finsandstails.client.model.armor.GopjetpackModel;
import blueportal.finsandstails.client.render.*;
import blueportal.finsandstails.client.screen.CrabCruncherScreen;
import blueportal.finsandstails.client.screen.MudhorsePouchScreen;
import blueportal.finsandstails.registry.*;

@Mod.EventBusSubscriber(modid = FinsAndTails.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FTModelLayers.BANDED_REDBACK_SHRIMP, BandedRedbackShrimpModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.GOLDEN_RIVER_RAY, GoldenRiverRayModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.MUDHORSE, MudhorseModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.ORNATE_BUGFISH, OrnateBugfishModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.PHANTOM_NUDIBRANCH, PhantomNudibranchModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.SWAMP_MUCKER, SwampMuckerModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.TEAL_ARROWFISH, TealArrowfishModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.TEAL_ARROWFISH_ARROW, TealArrowfishArrowModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.WHERBLE, WherbleModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.WHERBLING, WherbleModel::createWherblingBodyLayer);
        event.registerLayerDefinition(FTModelLayers.GOPJET, GopjetModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.GOPJETPACK, () -> GopjetpackModel.createArmorLayer(new CubeDeformation(0.0F)));
        event.registerLayerDefinition(FTModelLayers.WEE, WeeModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.PENGLIL, PenglilModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.NIGHT_LIGHT_SQUID, NightLightSquidModel::createBodyLayer);
        event.registerLayerDefinition(FTModelLayers.WANDERING_SAILOR, WanderingSailorModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        Reflection.initialize(FTModelLayers.class);

        event.registerEntityRenderer(FTEntities.BANDED_REDBACK_SHRIMP.get(), BandedRedbackShrimpRenderer::new);
        event.registerEntityRenderer(FTEntities.GOLDEN_RIVER_RAY.get(), GoldenRiverRayRenderer::new);
        event.registerEntityRenderer(FTEntities.MUDHORSE.get(), MudhorseRenderer::new);
        event.registerEntityRenderer(FTEntities.ORNATE_BUGFISH.get(), OrnateBugfishRenderer::new);
        event.registerEntityRenderer(FTEntities.PHANTOM_NUDIBRANCH.get(), PhantomNudibranchRenderer::new);
        event.registerEntityRenderer(FTEntities.SWAMP_MUCKER.get(), SwampMuckerRenderer::new);
        event.registerEntityRenderer(FTEntities.TEAL_ARROWFISH_ARROW.get(), TealArrowfishArrowRenderer::new);
        event.registerEntityRenderer(FTEntities.TEAL_ARROWFISH.get(), TealArrowfishRenderer::new);
        event.registerEntityRenderer(FTEntities.WHERBLE.get(), WherbleRenderer::new);
        event.registerEntityRenderer(FTEntities.GOPJET.get(), GopjetRenderer::new);
        event.registerEntityRenderer(FTEntities.WEE.get(), WeeRenderer::new);
        event.registerEntityRenderer(FTEntities.PENGLIL.get(), PenglilRenderer::new);
        event.registerEntityRenderer(FTEntities.NIGHT_LIGHT_SQUID.get(), NightLightSquidRenderer::new);
        event.registerEntityRenderer(FTEntities.WANDERING_SAILOR.get(), WanderingSailorRenderer::new);

        event.registerEntityRenderer(FTEntities.RED_BULL_CRAB.get(), RedBullCrabRenderer::new);
        event.registerEntityRenderer(FTEntities.WHITE_BULL_CRAB.get(), WhiteBullCrabRenderer::new);
        event.registerEntityRenderer(FTEntities.FLATBACK_LEAF_SNAIL.get(), FlatbackLeafSnailRenderer::new);
        event.registerEntityRenderer(FTEntities.FLATBACK_SUCKER.get(), FlatbackSuckerRenderer::new);
        event.registerEntityRenderer(FTEntities.HIGH_FINNED_BLUE.get(), HighFinnedBlueRenderer::new);
        event.registerEntityRenderer(FTEntities.WEE_WEE.get(), WeeWeeRenderer::new);
        event.registerEntityRenderer(FTEntities.PAPA_WEE.get(), PapaWeeRenderer::new);
        event.registerEntityRenderer(FTEntities.RIVER_PEBBLE_SNAIL.get(), RiverPebbleSnailRenderer::new);
        event.registerEntityRenderer(FTEntities.RUBBER_BELLY_GLIDER.get(), RubberBellyGliderRenderer::new);
        event.registerEntityRenderer(FTEntities.SIDEROL_WHISKERED_SNAIL.get(), SiderolWhiskeredSnailRenderer::new);
        event.registerEntityRenderer(FTEntities.VIBRA_WEE.get(), VibraWeeRenderer::new);
        event.registerEntityRenderer(FTEntities.SPINDLY_GEM_CRAB.get(), SpindlyGemCrabRenderer::new);
        event.registerEntityRenderer(FTEntities.CROWNED_HORATEE.get(), CrownedHorateeRenderer::new);
    }

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        MenuScreens.register(FTContainers.MUDHORSE_POUCH.get(), MudhorsePouchScreen::new);
        MenuScreens.register(FTContainers.CRAB_CRUNCHER.get(), CrabCruncherScreen::new);
        event.enqueueWork(FTItemProperties::setupItemProperties);
    }

}
