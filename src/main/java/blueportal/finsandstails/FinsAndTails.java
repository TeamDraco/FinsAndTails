package blueportal.finsandstails;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import blueportal.finsandstails.client.ClientUtils;
import blueportal.finsandstails.common.entities.*;
import blueportal.finsandstails.data.PlayerHitComboData;
import blueportal.finsandstails.data.PlayerHitComboProvider;
import blueportal.finsandstails.network.FTMessages;
import blueportal.finsandstails.network.INetworkPacket;
import blueportal.finsandstails.network.TriggerFlyingPacket;
import blueportal.finsandstails.registry.*;

import java.util.Optional;
import java.util.function.Function;

@Mod(FinsAndTails.MOD_ID)
public class FinsAndTails {
    public static final String MOD_ID = "finsandtails";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final SimpleChannel NETWORK = INetworkPacket.makeChannel("network", "1");
    private static int currentNetworkId;

    public FinsAndTails() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        bus.addListener(this::registerEntityAttributes);
        bus.addListener(this::registerSpawnPlacements);
        bus.addListener(this::registerCapabilities);
        forgeBus.addGenericListener(Entity.class, this::attachCapabilities);

        FTBannerPatterns.BANNER_PATTERNS.register(bus);
        FTRecipes.RECIPE_TYPE.register(bus);
        FTRecipes.SERIALIZERS.register(bus);
        FTEnchantments.REGISTER.register(bus);
        FTItems.ITEMS.register(bus);
        FTBlocks.BLOCKS.register(bus);
        FTContainers.REGISTER.register(bus);
        FTEntities.REGISTER.register(bus);
        FTSounds.REGISTER.register(bus);
        FTCreativeModeTabs.CREATIVE_MODE_TABS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FTConfig.Common.SPEC);
        registerMessage(TriggerFlyingPacket.class, TriggerFlyingPacket::new, LogicalSide.SERVER);

        FTMessages.register();
    }

    private void attachCapabilities(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof Player) {
            if (!e.getObject().getCapability(PlayerHitComboProvider.HIT_COMBO).isPresent()) {
                e.addCapability(new ResourceLocation(FinsAndTails.MOD_ID, "hit_combo"), new PlayerHitComboProvider());
            }
        }
    }

    private void registerCapabilities(RegisterCapabilitiesEvent e) {
        e.register(PlayerHitComboData.class);
    }

    private void registerSpawnPlacements(SpawnPlacementRegisterEvent e) {
        e.register(FTEntities.WEE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.BANDED_REDBACK_SHRIMP.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.SWAMP_MUCKER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.TEAL_ARROWFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.FLATBACK_SUCKER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.HIGH_FINNED_BLUE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.MUDHORSE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.PHANTOM_NUDIBRANCH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.ORNATE_BUGFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.PENGLIL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PenglilEntity::canPenglilSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.SPINDLY_GEM_CRAB.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.FLATBACK_LEAF_SNAIL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.RED_BULL_CRAB.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RedBullCrabEntity::canCrabSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.WHITE_BULL_CRAB.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RedBullCrabEntity::canCrabSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.WEE_WEE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.VIBRA_WEE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.RIVER_PEBBLE_SNAIL.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.SIDEROL_WHISKERED_SNAIL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.GOLDEN_RIVER_RAY.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.NIGHT_LIGHT_SQUID.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.GOPJET.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.PAPA_WEE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::checkSurfaceWaterAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.WHERBLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WherbleEntity::checkWherbleSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        e.register(FTEntities.CROWNED_HORATEE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrownedHorateeEntity::checkCrownedSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(FTEntities.BANDED_REDBACK_SHRIMP.get(), BandedRedbackShrimpEntity.createAttributes().build());
        event.put(FTEntities.WEE.get(), WeeEntity.createAttributes().build());
        event.put(FTEntities.FLATBACK_SUCKER.get(), FlatbackSuckerEntity.createAttributes().build());
        event.put(FTEntities.HIGH_FINNED_BLUE.get(), HighFinnedBlueEntity.createAttributes().build());
        event.put(FTEntities.MUDHORSE.get(), MudhorseEntity.createAttributes().build());
        event.put(FTEntities.ORNATE_BUGFISH.get(), OrnateBugfishEntity.createAttributes().build());
        event.put(FTEntities.PENGLIL.get(), PenglilEntity.createAttributes().build());
        event.put(FTEntities.PHANTOM_NUDIBRANCH.get(), PhantomNudibranchEntity.createAttributes().build());
        event.put(FTEntities.SPINDLY_GEM_CRAB.get(), SpindlyGemCrabEntity.createAttributes().build());
        event.put(FTEntities.SWAMP_MUCKER.get(), SwampMuckerEntity.createAttributes().build());
        event.put(FTEntities.TEAL_ARROWFISH.get(), TealArrowfishEntity.createAttributes().build());
        event.put(FTEntities.FLATBACK_LEAF_SNAIL.get(), FlatbackLeafSnailEntity.createAttributes().build());
        event.put(FTEntities.RUBBER_BELLY_GLIDER.get(), RubberBellyGliderEntity.registerRBGAttributes().build());
        event.put(FTEntities.RED_BULL_CRAB.get(), RedBullCrabEntity.createAttributes().build());
        event.put(FTEntities.WHITE_BULL_CRAB.get(), WhiteBullCrabEntity.createAttributes().build());
        event.put(FTEntities.WEE_WEE.get(), WeeWeeEntity.createAttributes().build());
        event.put(FTEntities.VIBRA_WEE.get(), AbstractFish.createAttributes().build());
        event.put(FTEntities.GOPJET.get(), GopjetEntity.createAttributes().build());
        event.put(FTEntities.RIVER_PEBBLE_SNAIL.get(), RiverPebbleSnailEntity.createAttributes().build());
        event.put(FTEntities.SIDEROL_WHISKERED_SNAIL.get(), SiderolWhiskeredSnailEntity.createAttributes().build());
        event.put(FTEntities.GOLDEN_RIVER_RAY.get(), GoldenRiverRayEntity.createAttributes().build());
        event.put(FTEntities.NIGHT_LIGHT_SQUID.get(), NightLightSquidEntity.createAttributes().build());
        event.put(FTEntities.PAPA_WEE.get(), PapaWeeEntity.createAttributes().build());
        event.put(FTEntities.WHERBLE.get(), WherbleEntity.createAttributes().build());
        event.put(FTEntities.WANDERING_SAILOR.get(), WanderingSailorEntity.createAttributes().build());
        event.put(FTEntities.CROWNED_HORATEE.get(), CrownedHorateeEntity.createAttributes().build());
        //event.put(FTEntities.GOLIATH_GARDEN_CRAB.get(), GoliathGardenCrabEntity.createAttributes().build());
    }

    private <T extends INetworkPacket> void registerMessage(Class<T> message, Function<FriendlyByteBuf, T> reader, LogicalSide side) {
        NETWORK.registerMessage(currentNetworkId++, message, INetworkPacket::write, reader, (msg, contextSupplier) -> {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> msg.handle(context.getDirection().getOriginationSide().isServer() ? ClientUtils.getClientPlayer() : context.getSender()));
            context.setPacketHandled(true);
        }, Optional.of(side.isClient() ? NetworkDirection.PLAY_TO_CLIENT : NetworkDirection.PLAY_TO_SERVER));
    }

}
