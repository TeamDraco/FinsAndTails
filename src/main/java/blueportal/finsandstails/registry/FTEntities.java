package blueportal.finsandstails.registry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.entities.*;
import blueportal.finsandstails.common.entities.item.TealArrowfishArrowEntity;

public class FTEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FinsAndTails.MOD_ID);

    public static final RegistryObject<EntityType<WeeEntity>> WEE = create("wee", EntityType.Builder.of(WeeEntity::new, MobCategory.WATER_AMBIENT).sized(0.3f, 0.2f));
    public static final RegistryObject<EntityType<BandedRedbackShrimpEntity>> BANDED_REDBACK_SHRIMP = create("banded_redback_shrimp", EntityType.Builder.of(BandedRedbackShrimpEntity::new, MobCategory.WATER_AMBIENT).sized( 0.5f, 0.3f));
    public static final RegistryObject<EntityType<TealArrowfishEntity>> TEAL_ARROWFISH = create("teal_arrowfish", EntityType.Builder.of(TealArrowfishEntity::new, MobCategory.WATER_AMBIENT).sized(0.4f, 0.2f));
    public static final RegistryObject<EntityType<SwampMuckerEntity>> SWAMP_MUCKER = create("swamp_mucker", EntityType.Builder.of(SwampMuckerEntity::new, MobCategory.WATER_AMBIENT).sized(0.4f, 0.2f));
    public static final RegistryObject<EntityType<FlatbackSuckerEntity>> FLATBACK_SUCKER = create("flatback_sucker", EntityType.Builder.of(FlatbackSuckerEntity::new, MobCategory.WATER_AMBIENT).sized(0.4f, 0.2f));
    public static final RegistryObject<EntityType<HighFinnedBlueEntity>> HIGH_FINNED_BLUE = create("high_finned_blue", EntityType.Builder.of(HighFinnedBlueEntity::new, MobCategory.WATER_AMBIENT).sized(0.2f, 0.5f));
    public static final RegistryObject<EntityType<MudhorseEntity>> MUDHORSE = create("mudhorse", EntityType.Builder.of(MudhorseEntity::new, MobCategory.CREATURE).sized(0.9f, 1.6f));
    public static final RegistryObject<EntityType<OrnateBugfishEntity>> ORNATE_BUGFISH = create("ornate_bugfish", EntityType.Builder.of(OrnateBugfishEntity::new, MobCategory.WATER_AMBIENT).sized(0.7f, 1f));
    public static final RegistryObject<EntityType<PhantomNudibranchEntity>> PHANTOM_NUDIBRANCH = create("phantom_nudibranch", EntityType.Builder.of(PhantomNudibranchEntity::new, MobCategory.WATER_AMBIENT).sized(0.3f, 0.3f));
    public static final RegistryObject<EntityType<PenglilEntity>> PENGLIL = create("penglil", EntityType.Builder.of(PenglilEntity::new, MobCategory.WATER_CREATURE).sized( 0.5f, 0.5f));
    public static final RegistryObject<EntityType<SpindlyGemCrabEntity>> SPINDLY_GEM_CRAB = create("spindly_gem_crab", EntityType.Builder.of(SpindlyGemCrabEntity::new, MobCategory.WATER_AMBIENT).sized(0.4f, 0.3f));
    public static final RegistryObject<EntityType<FlatbackLeafSnailEntity>> FLATBACK_LEAF_SNAIL = create("flatback_leaf_snail", EntityType.Builder.of(FlatbackLeafSnailEntity::new, MobCategory.CREATURE).sized(0.6f, 0.3f));
    public static final RegistryObject<EntityType<RedBullCrabEntity>> RED_BULL_CRAB = create("red_bull_crab", EntityType.Builder.of(RedBullCrabEntity::new, MobCategory.WATER_CREATURE).sized(0.5f, 0.3f));
    public static final RegistryObject<EntityType<WhiteBullCrabEntity>> WHITE_BULL_CRAB = create("white_bull_crab", EntityType.Builder.of(WhiteBullCrabEntity::new, MobCategory.WATER_CREATURE).sized(0.5f, 0.3f));
    public static final RegistryObject<EntityType<WeeWeeEntity>> WEE_WEE = create("wee_wee", EntityType.Builder.of(WeeWeeEntity::new, MobCategory.WATER_AMBIENT).sized(0.3f, 0.3f));
    public static final RegistryObject<EntityType<VibraWeeEntity>> VIBRA_WEE = create("vibra_wee", EntityType.Builder.of(VibraWeeEntity::new, MobCategory.WATER_AMBIENT).sized(0.3f, 0.4f));
    public static final RegistryObject<EntityType<RiverPebbleSnailEntity>> RIVER_PEBBLE_SNAIL = create("river_pebble_snail", EntityType.Builder.of(RiverPebbleSnailEntity::new, MobCategory.WATER_AMBIENT).sized(0.3f, 0.3f));
    public static final RegistryObject<EntityType<SiderolWhiskeredSnailEntity>> SIDEROL_WHISKERED_SNAIL = create("siderol_whiskered_snail", EntityType.Builder.of(SiderolWhiskeredSnailEntity::new, MobCategory.CREATURE).sized(0.3f, 0.4f));
    public static final RegistryObject<EntityType<GoldenRiverRayEntity>> GOLDEN_RIVER_RAY = create("golden_river_ray", EntityType.Builder.of(GoldenRiverRayEntity::new, MobCategory.WATER_CREATURE).sized(0.7f, 0.3f));
    public static final RegistryObject<EntityType<NightLightSquidEntity>> NIGHT_LIGHT_SQUID = create("night_light_squid", EntityType.Builder.of(NightLightSquidEntity::new, MobCategory.WATER_CREATURE).sized(0.4f, 0.3f));
    public static final RegistryObject<EntityType<RubberBellyGliderEntity>> RUBBER_BELLY_GLIDER = create("rubber_belly_glider", EntityType.Builder.of(RubberBellyGliderEntity::new, MobCategory.WATER_CREATURE).sized(0.7f, 0.285f));
    public static final RegistryObject<EntityType<TealArrowfishArrowEntity>> TEAL_ARROWFISH_ARROW = create("teal_arrowfish_arrow", EntityType.Builder.<TealArrowfishArrowEntity>of(TealArrowfishArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F));
    public static final RegistryObject<EntityType<GopjetEntity>> GOPJET = create("gopjet", EntityType.Builder.of(GopjetEntity::new, MobCategory.WATER_CREATURE).sized(0.7f, 0.5f));
    public static final RegistryObject<EntityType<PapaWeeEntity>> PAPA_WEE = create("papa_wee", EntityType.Builder.of(PapaWeeEntity::new, MobCategory.WATER_CREATURE).sized(0.5f, 0.5f));
    public static final RegistryObject<EntityType<WherbleEntity>> WHERBLE = create("wherble", EntityType.Builder.of(WherbleEntity::new, MobCategory.CREATURE).sized(0.8f, 0.8f));
    public static final RegistryObject<EntityType<WanderingSailorEntity>> WANDERING_SAILOR = create("wandering_sailor", EntityType.Builder.of(WanderingSailorEntity::new, MobCategory.CREATURE).sized(0.6f, 1.65f));
    //public static final RegistryObject<EntityType<GoliathGardenCrabEntity>> GOLIATH_GARDEN_CRAB = create("goliath_garden_crab", EntityType.Builder.of(GoliathGardenCrabEntity::new, MobCategory.WATER_AMBIENT).sized(3.5f, 2.5f));
    public static final RegistryObject<EntityType<CrownedHorateeEntity>> CROWNED_HORATEE = create("crowned_horatee", EntityType.Builder.of(CrownedHorateeEntity::new, MobCategory.CREATURE).sized(0.8f, 0.6f));


    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return REGISTER.register(name, () -> builder.build(FinsAndTails.MOD_ID + "." + name));
    }
}
