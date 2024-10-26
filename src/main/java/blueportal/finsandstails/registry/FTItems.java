package blueportal.finsandstails.registry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.items.*;

@Mod.EventBusSubscriber(modid = FinsAndTails.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FTItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FinsAndTails.MOD_ID);

//region FISH

    //Wee
    public static final RegistryObject<Item> WEE_SPAWN_EGG = ITEMS.register("wee_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.WEE, 0x379cc1, 0x2f6194, new Item.Properties()));
    public static final RegistryObject<Item> WEE_BUCKET = ITEMS.register("wee_bucket", () -> new FinsBucketItem(FTEntities.WEE, () -> Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WEE = ITEMS.register("wee", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).fast().build())));

    //Wee Wee
    public static final RegistryObject<Item> WEE_WEE_SPAWN_EGG = ITEMS.register("wee_wee_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.WEE_WEE, 0x8b9da5, 0x516279, new Item.Properties()));
    public static final RegistryObject<Item> WEE_WEE_BUCKET = ITEMS.register("wee_wee_bucket", () -> new MobBucketItem(FTEntities.WEE_WEE, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WEE_WEE = ITEMS.register("wee_wee", () -> new WeeWeeItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.1F).fast().build())));

    //Vibra Wee
    public static final RegistryObject<Item> VIBRA_WEE_SPAWN_EGG = ITEMS.register("vibra_wee_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.VIBRA_WEE, 0x0b1a50, 0xf09c35, new Item.Properties()));
    public static final RegistryObject<Item> VIBRA_WEE_BUCKET = ITEMS.register("vibra_wee_bucket", () -> new FinsBucketItem(FTEntities.VIBRA_WEE, () -> Fluids.WATER, new Item.Properties().stacksTo(1)));

    //Papa Wee
    public static final RegistryObject<Item> PAPA_WEE_SPAWN_EGG = ITEMS.register("papa_wee_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.PAPA_WEE, 0x8b5a3d, 0xebc454, new Item.Properties()));
    public static final RegistryObject<Item> PAPA_WEE_BUCKET = ITEMS.register("papa_wee_bucket", () -> new FinsBucketItem(FTEntities.PAPA_WEE, () -> Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PAPA_WEE = ITEMS.register("papa_wee", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(0.2F).build())));
    public static final RegistryObject<Item> COOKED_PAPA_WEE = ITEMS.register("cooked_papa_wee", () -> new PapaWeeItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationMod(0.6f).build())));

    //All Wees
    public static final RegistryObject<Item> WEE_DELIGHT = ITEMS.register("wee_delight", () -> new BowlFoodItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.4f).fast().build()).stacksTo(1)));

    //Teal Arrowfish
    public static final RegistryObject<Item> TEAL_ARROWFISH_SPAWN_EGG = ITEMS.register("teal_arrowfish_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.TEAL_ARROWFISH, 0x014242, 0x427c8a, new Item.Properties()));
    public static final RegistryObject<Item> TEAL_ARROWFISH_BUCKET = ITEMS.register("teal_arrowfish_bucket", () -> new MobBucketItem(FTEntities.TEAL_ARROWFISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> TEAL_ARROWFISH = ITEMS.register("teal_arrowfish", () -> new TealArrowfishItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));

    //High-Finned Blue
    public static final RegistryObject<Item> HIGH_FINNED_BLUE_SPAWN_EGG = ITEMS.register("high_finned_blue_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.HIGH_FINNED_BLUE, 0x4b6e81, 0x425e85, new Item.Properties()));
    public static final RegistryObject<Item> HIGH_FINNED_BLUE_BUCKET = ITEMS.register("high_finned_blue_bucket", () -> new MobBucketItem(FTEntities.HIGH_FINNED_BLUE, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> HIGH_FINNED_BLUE = ITEMS.register("high_finned_blue", () -> new AbstractDescriptionItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));

    //Golden River Ray
    public static final RegistryObject<Item> GOLDEN_RIVER_RAY_SPAWN_EGG = ITEMS.register("golden_river_ray_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.GOLDEN_RIVER_RAY, 0xb1a55f, 0x6c4f3c, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_RIVER_RAY_BUCKET = ITEMS.register("golden_river_ray_bucket", () -> new FinsBucketItem(FTEntities.GOLDEN_RIVER_RAY, () -> Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RAW_GOLDEN_RIVER_RAY_WING = ITEMS.register("raw_golden_river_ray_wing", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).build())));
    public static final RegistryObject<Item> GOLDEN_RIVER_RAY_WING_FILLET = ITEMS.register("golden_river_ray_wing_fillet", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build())));

    //Swamp Mucker
    public static final RegistryObject<Item> SWAMP_MUCKER_SPAWN_EGG = ITEMS.register("swamp_mucker_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.SWAMP_MUCKER, 0x5e663b, 0x997d45, new Item.Properties()));
    public static final RegistryObject<Item> SWAMP_MUCKER_BUCKET = ITEMS.register("swamp_mucker_bucket", () -> new MobBucketItem(FTEntities.SWAMP_MUCKER, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SWAMP_MUCKER = ITEMS.register("swamp_mucker", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));
    public static final RegistryObject<Item> FWIN = ITEMS.register("fwin", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FWINGED_BOOTS = ITEMS.register("fwinged_boots", FwingedBootsItem::new);

    //Flatback Sucker
    public static final RegistryObject<Item> FLATBACK_SUCKER_SPAWN_EGG = ITEMS.register("flatback_sucker_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.FLATBACK_SUCKER, 0x898364, 0x4b7486, new Item.Properties()));
    public static final RegistryObject<Item> FLATBACK_SUCKER_BUCKET = ITEMS.register("flatback_sucker_bucket", () -> new MobBucketItem(FTEntities.FLATBACK_SUCKER, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FLATBACK_SUCKER = ITEMS.register("flatback_sucker", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));

    //Mudhorse
    public static final RegistryObject<Item> MUDHORSE_SPAWN_EGG = ITEMS.register("mudhorse_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.MUDHORSE, 0x5c3d2b, 0xc9973c, new Item.Properties()));
    public static final RegistryObject<Item> MUDHORSE_SCALE = ITEMS.register("mudhorse_scale", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MUDHORSE_POUCH = ITEMS.register("mudhorse_pouch", MudhorsePouchItem::new);
    public static final RegistryObject<Item> SWAMP_DIDGERIDOO = ITEMS.register("swamp_didgeridoo", () -> new SwampDidgeridooItem(new Item.Properties().stacksTo(1).durability(64)));

    //Horatee
    public static final RegistryObject<Item> CROWNED_HORATEE_SPAWN_EGG = ITEMS.register("crowned_horatee_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.CROWNED_HORATEE, 0xF7A53D, 0xB34B1B, new Item.Properties()));
    public static final RegistryObject<Item> BABY_HORATEE_BUCKET = ITEMS.register("baby_horatee_bucket", () -> new MobBucketItem(FTEntities.CROWNED_HORATEE, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> HORATEE_HIDE = ITEMS.register("horatee_hide", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARMORED_GOPJET_JETPACK = ITEMS.register("armored_gopjet_jetpack", ArmoredGopjetJetpackItem::new);

    //Ornate Bugfish
    public static final RegistryObject<Item> ORNATE_BUGFISH_SPAWN_EGG = ITEMS.register("ornate_bugfish_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.ORNATE_BUGFISH, 0xf8eeee, 0xff861b, new Item.Properties()));
    public static final RegistryObject<Item> ORNATE_BUGFISH_BUCKET = ITEMS.register("ornate_bugfish_bucket", () -> new MobBucketItem(FTEntities.ORNATE_BUGFISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BUGFISH_MANDIBLES = ITEMS.register("bugfish_mandibles", () -> new ShearsItem(new Item.Properties().durability(232)));
    public static final RegistryObject<Item> BUGMEAT = ITEMS.register("bugmeat", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(0.15F).build())));
    public static final RegistryObject<Item> COOKED_BUGMEAT = ITEMS.register("cooked_bugmeat", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.6F).build())));
    public static final RegistryObject<Item> BANNER_PATTERN_MANDIBLES = ITEMS.register("banner_pattern_mandibles", () -> new BannerPatternItem(FTTags.PATTERN_ITEM_MANDIBLES, new Item.Properties().stacksTo(1)));

    //Gopjet
    public static final RegistryObject<Item> GOPJET_SPAWN_EGG = ITEMS.register("gopjet_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.GOPJET, 0xcfe0ff, 0x403c70, new Item.Properties()));
    public static final RegistryObject<Item> GOPJET_BUCKET = ITEMS.register("gopjet_bucket", () -> new FinsBucketItem(FTEntities.GOPJET, () -> Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GOPJET_JET = ITEMS.register("gopjet_jet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOPJET_JETPACK = ITEMS.register("gopjet_jetpack", GopjetpackItem::new);

//endregion

//region CRUSTACEANS

    //Banded Redback Shrimp
    public static final RegistryObject<Item> BANDED_REDBACK_SHRIMP_SPAWN_EGG = ITEMS.register("banded_redback_shrimp_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.BANDED_REDBACK_SHRIMP, 0x115456, 0xc71913, new Item.Properties()));
    public static final RegistryObject<Item> BANDED_REDBACK_SHRIMP_BUCKET = ITEMS.register("banded_redback_shrimp_bucket", () -> new MobBucketItem(FTEntities.BANDED_REDBACK_SHRIMP, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BANDED_REDBACK_SRHIMP = ITEMS.register("banded_redback_shrimp", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));
    public static final RegistryObject<Item> COOKED_BANDED_REDBACK_SHRIMP = ITEMS.register("cooked_banded_redback_shrimp", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.65F).build())));

    //Bull Crabs
    //Red
    public static final RegistryObject<Item> RED_BULL_CRAB_SPAWN_EGG = ITEMS.register("red_bull_crab_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.RED_BULL_CRAB, 0x6e4c3d, 0xa62d2b, new Item.Properties()));
    public static final RegistryObject<Item> RED_BULL_CRAB_BUCKET = ITEMS.register("red_bull_crab_bucket", () -> new FinsBucketItem(FTEntities.RED_BULL_CRAB, () -> Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RED_BULL_CRAB_CLAW = ITEMS.register("red_bull_crab_claw", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RED_CLAW_GAUNTLET = ITEMS.register("red_claw_gauntlet", () -> new CrabGauntletItem(Tiers.STONE, 3, -1.4F, new Item.Properties().durability(130)));
    //White
    public static final RegistryObject<Item> WHITE_BULL_CRAB_SPAWN_EGG = ITEMS.register("white_bull_crab_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.WHITE_BULL_CRAB, 0x6e4c3d, 0xdddddd, new Item.Properties()));
    public static final RegistryObject<Item> WHITE_BULL_CRAB_BUCKET = ITEMS.register("white_bull_crab_bucket", () -> new FinsBucketItem(FTEntities.WHITE_BULL_CRAB, () -> Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WHITE_BULL_CRAB_CLAW = ITEMS.register("white_bull_crab_claw", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHITE_CLAW_GAUNTLET = ITEMS.register("white_claw_gauntlet", () -> new CrabGauntletItem(Tiers.STONE, 2, -0.6F, new Item.Properties().durability(200)));
    //Both
    public static final RegistryObject<Item> COOKED_BULL_CRAB_CLAW = ITEMS.register("cooked_bull_crab_claw", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build())));
    public static final RegistryObject<Item> SEAFOOD_SOUP = ITEMS.register("seafood_soup", () -> new BowlFoodItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(18).saturationMod(0.8f).build()).stacksTo(1)));

    //Spindly Gem Crabs
    public static final RegistryObject<Item> SPINDLY_GEM_CRAB_SPAWN_EGG = ITEMS.register("spindly_gem_crab_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.SPINDLY_GEM_CRAB, 0xf39111, 0x1a4591, new Item.Properties()));
    public static final RegistryObject<Item> SPINDLY_GEM_CRAB_BUCKET = ITEMS.register("spindly_gem_crab_bucket", () -> new FinsBucketItem(FTEntities.SPINDLY_GEM_CRAB, () -> Fluids.WATER, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> AMBER_SPINDLY_GEM_CRAB = ITEMS.register("amber_spindly_gem_crab", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EMERALD_SPINDLY_GEM_CRAB = ITEMS.register("emerald_spindly_gem_crab", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PEARL_SPINDLY_GEM_CRAB = ITEMS.register("pearl_spindly_gem_crab", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUBY_SPINDLY_GEM_CRAB = ITEMS.register("ruby_spindly_gem_crab", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_SPINDLY_GEM_CRAB = ITEMS.register("sapphire_spindly_gem_crab", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPINDLY_AMBER = ITEMS.register("spindly_amber", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPINDLY_EMERALD = ITEMS.register("spindly_emerald", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPINDLY_PEARL = ITEMS.register("spindly_pearl", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPINDLY_RUBY = ITEMS.register("spindly_ruby", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPINDLY_SAPPHIRE = ITEMS.register("spindly_sapphire", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPINDLY_GEM = ITEMS.register("spindly_gem", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> EMPTY_CHARM = ITEMS.register("empty_charm", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPINDLY_AMBER_CHARM = ITEMS.register("spindly_amber_charm", () -> new SpindlyCharmItem("amber", MobEffects.MOVEMENT_SPEED));
    public static final RegistryObject<Item> SPINDLY_EMERALD_CHARM = ITEMS.register("spindly_emerald_charm", () -> new SpindlyCharmItem("emerald", MobEffects.DAMAGE_BOOST));
    public static final RegistryObject<Item> SPINDLY_PEARL_CHARM = ITEMS.register("spindly_pearl_charm", () -> new SpindlyCharmItem("pearl", MobEffects.REGENERATION));
    public static final RegistryObject<Item> SPINDLY_RUBY_CHARM = ITEMS.register("spindly_ruby_charm", () -> new SpindlyCharmItem("ruby", MobEffects.FIRE_RESISTANCE));
    public static final RegistryObject<Item> SPINDLY_SAPPHIRE_CHARM = ITEMS.register("spindly_sapphire_charm", () -> new SpindlyCharmItem("sapphire", MobEffects.WATER_BREATHING));
    public static final RegistryObject<Item> GEM_CRAB_AMULET = ITEMS.register("gem_crab_amulet", SpindlyGemCharmItem::new);

    public static final RegistryObject<Item> SPINDLY_AMBER_BLOCK = ITEMS.register("spindly_amber_block", () -> new BlockItem(FTBlocks.SPINDLY_AMBER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SPINDLY_EMERALD_BLOCK = ITEMS.register("spindly_emerald_block", () -> new BlockItem(FTBlocks.SPINDLY_EMERALD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SPINDLY_PEARL_BLOCK = ITEMS.register("spindly_pearl_block", () -> new BlockItem(FTBlocks.SPINDLY_PEARL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SPINDLY_RUBY_BLOCK = ITEMS.register("spindly_ruby_block", () -> new BlockItem(FTBlocks.SPINDLY_RUBY_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SPINDLY_SAPPHIRE_BLOCK = ITEMS.register("spindly_sapphire_block", () -> new BlockItem(FTBlocks.SPINDLY_SAPPHIRE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SPINDLY_GEM_BLOCK = ITEMS.register("spindly_gem_block", () -> new BlockItem(FTBlocks.SPINDLY_GEM_BLOCK.get(), new Item.Properties()));

    //One or more Crustaceans
    public static final RegistryObject<Item> CRAB_SANDWICH = ITEMS.register("crab_sandwich", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(17).saturationMod(0.55f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 160), 1.0F).build())));
    public static final RegistryObject<Item> REEF_COCKTAIL = ITEMS.register("reef_cocktail", () -> new BowlFoodItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.5f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100), 1.0F).effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 200), 0.75F).build()).stacksTo(1)));
    public static final RegistryObject<Item> CRAB_CRUNCHER = ITEMS.register("crab_cruncher", () -> new BlockItem(FTBlocks.CRAB_CRUNCHER.get(), new Item.Properties()));

//endregion

//region OTHER

    //Penglil
    public static final RegistryObject<Item> PENGLIL_SPAWN_EGG = ITEMS.register("penglil_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.PENGLIL,0x234040, 0xa7d7c1, new Item.Properties()));
    public static final RegistryObject<Item> PENGLIL_BUCKET = ITEMS.register("penglil_bucket", () -> new FinsBucketItem(FTEntities.PENGLIL, () -> Fluids.EMPTY, new Item.Properties().stacksTo(1)));

    //Rubber Belly Glider
    public static final RegistryObject<Item> RUBBER_BELLY_GLIDER_SPAWN_EGG = ITEMS.register("rubber_belly_glider_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.RUBBER_BELLY_GLIDER, 0x8dc87f, 0xffc52c, new Item.Properties()));

    //Wherble
    public static final RegistryObject<Item> WHERBLE_SPAWN_EGG = ITEMS.register("wherble_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.WHERBLE, 0x2f3945, 0x8eb8cc, new Item.Properties()));
    public static final RegistryObject<Item> WHERBLING = ITEMS.register("wherbling", () -> new WherblingItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WHERBLE_FIN = ITEMS.register("wherble_fin", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.2F).meat().fast().build())));
    public static final RegistryObject<Item> COOKED_WHERBLE_FIN = ITEMS.register("cooked_wherble_fin", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.3F).meat().fast().build())));
    public static final RegistryObject<Item> STUFFED_WHERBLE_FIN = ITEMS.register("stuffed_wherble_fin", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).fast().build())));
    public static final RegistryObject<Item> MUSIC_DISC_WARBLE = ITEMS.register("music_disc_warble", () -> new RecordItem(13, FTSounds.WARBLE, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 3480));
    public static final RegistryObject<Item> MUSIC_DISC_WARBLE_FRAGMENT = ITEMS.register("disc_fragment_warble", () -> new DiscFragmentItem(new Item.Properties()));

//endregion

//region MOLLUSCS

    //Phantom Nudibranch
    public static final RegistryObject<Item> PHANTOM_NUDIBRANCH_SPAWN_EGG = ITEMS.register("phantom_nudibranch_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.PHANTOM_NUDIBRANCH, 0xebebeb, 0xa886ff, new Item.Properties()));
    public static final RegistryObject<Item> PHANTOM_NUDIBRANCH_BUCKET = ITEMS.register("phantom_nudibranch_bucket", () -> new MobBucketItem(FTEntities.PHANTOM_NUDIBRANCH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));

    //Night Light Squid
    public static final RegistryObject<Item> NIGHT_LIGHT_SQUID_SPAWN_EGG = ITEMS.register("night_light_squid_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.NIGHT_LIGHT_SQUID, 0x280827, 0xd6f7eb, new Item.Properties()));
    public static final RegistryObject<Item> NIGHT_LIGHT_SQUID_BUCKET = ITEMS.register("night_light_squid_bucket", () -> new FinsBucketItem(FTEntities.NIGHT_LIGHT_SQUID, () -> Fluids.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> NIGHT_LIGHT_SQUID_TENTACLE = ITEMS.register("night_light_squid_tentacle", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHAINED_TENTACLE = ITEMS.register("chained_tentacle", () -> new BlockItem(FTBlocks.CHAINED_TENTACLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LUMINOUS_CALAMARI = ITEMS.register("luminous_calamari", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.3f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 250), 0.5f).build())));
    public static final RegistryObject<Item> COOKED_LUMINOUS_CALAMARI = ITEMS.register("cooked_luminous_calamari", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.5f).effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 500), 1.0f).build())));

    //Flatback Leaf Snail
    public static final RegistryObject<Item> FLATBACK_LEAF_SNAIL_SPAWN_EGG = ITEMS.register("flatback_leaf_snail_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.FLATBACK_LEAF_SNAIL, 0x4a2c1a, 0xd1a06b, new Item.Properties()));
    public static final RegistryObject<Item> FLATBACK_LEAF_SNAIL_POT = ITEMS.register("flatback_leaf_snail_pot", () -> new FinsPotItem(FTEntities.FLATBACK_LEAF_SNAIL, () -> Fluids.EMPTY, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FLATBACK_SHELL = ITEMS.register("flatback_shell", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLATBACK_SHELL_BRICKS = ITEMS.register("flatback_shell_bricks", () -> new BlockItem(FTBlocks.FLATBACK_SHELL_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLATBACK_SHELL_BRICK_STAIRS = ITEMS.register("flatback_shell_brick_stairs", () -> new BlockItem(FTBlocks.FLATBACK_SHELL_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLATBACK_SHELL_BRICK_SLAB = ITEMS.register("flatback_shell_brick_slab", () -> new BlockItem(FTBlocks.FLATBACK_SHELL_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_FLATBACK_SHELL_BRICKS = ITEMS.register("chiseled_flatback_shell_bricks", () -> new BlockItem(FTBlocks.CHISELED_FLATBACK_SHELL_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLATBACK_SHELL_BLOCK = ITEMS.register("flatback_shell_block", () -> new BlockItem(FTBlocks.FLATBACK_SHELL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLATBACK_SHELL_STAIRS = ITEMS.register("flatback_shell_stairs", () -> new BlockItem(FTBlocks.FLATBACK_SHELL_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLATBACK_SHELL_SLAB = ITEMS.register("flatback_shell_slab", () -> new BlockItem(FTBlocks.FLATBACK_SHELL_SLAB.get(), new Item.Properties()));

    //River Pebble Snail
    public static final RegistryObject<Item> RIVER_PEBBLE_SNAIL_SPAWN_EGG = ITEMS.register("river_pebble_snail_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.RIVER_PEBBLE_SNAIL, 0xb8b2ab, 0x665852, new Item.Properties()));
    public static final RegistryObject<Item> RIVER_PEBBLE_SNAIL_POT = ITEMS.register("river_pebble_snail_pot", () -> new FinsPotItem(FTEntities.RIVER_PEBBLE_SNAIL, () -> Fluids.EMPTY, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PEBBLE_SHELL = ITEMS.register("pebble_shell", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PEBBLE_SHELL_BRICKS = ITEMS.register("pebble_shell_bricks", () -> new BlockItem(FTBlocks.PEBBLE_SHELL_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PEBBLE_SHELL_BRICK_STAIRS = ITEMS.register("pebble_shell_brick_stairs", () -> new BlockItem(FTBlocks.PEBBLE_SHELL_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PEBBLE_SHELL_BRICK_SLAB = ITEMS.register("pebble_shell_brick_slab", () -> new BlockItem(FTBlocks.PEBBLE_SHELL_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_PEBBLE_SHELL_BRICKS = ITEMS.register("chiseled_pebble_shell_bricks", () -> new BlockItem(FTBlocks.CHISELED_PEBBLE_SHELL_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PEBBLE_SHELL_PILLAR = ITEMS.register("pebble_shell_pillar", () -> new BlockItem(FTBlocks.PEBBLE_SHELL_PILLAR.get(), new Item.Properties()));

    //Siderol Whiskered Snail
    public static final RegistryObject<Item> SIDEROL_WHISKERED_SNAIL_SPAWN_EGG = ITEMS.register("siderol_whiskered_snail_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.SIDEROL_WHISKERED_SNAIL, 0x9e8372, 0x92402b, new Item.Properties()));
    public static final RegistryObject<Item> SIDEROL_WHISKERED_SNAIL_POT = ITEMS.register("siderol_whiskered_snail_pot", () -> new FinsPotItem(FTEntities.SIDEROL_WHISKERED_SNAIL, () -> Fluids.EMPTY, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SIDEROL_SHELL = ITEMS.register("siderol_shell", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SIDEROL_SHELL_BRICKS = ITEMS.register("siderol_shell_bricks", () -> new BlockItem(FTBlocks.SIDEROL_SHELL_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SIDEROL_SHELL_BRICK_STAIRS = ITEMS.register("siderol_shell_brick_stairs", () -> new BlockItem(FTBlocks.SIDEROL_SHELL_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> SIDEROL_SHELL_BRICK_SLAB = ITEMS.register("siderol_shell_brick_slab", () -> new BlockItem(FTBlocks.SIDEROL_SHELL_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_SIDEROL_SHELL_BRICKS = ITEMS.register("chiseled_siderol_shell_bricks", () -> new BlockItem(FTBlocks.CHISELED_SIDEROL_SHELL_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MIXED_FLATBACK_SHELL_BRICKS = ITEMS.register("mixed_flatback_shell_bricks", () -> new BlockItem(FTBlocks.MIXED_FLATBACK_SHELL_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MIXED_FLATBACK_SHELL_BRICK_STAIRS = ITEMS.register("mixed_flatback_shell_brick_stairs", () -> new BlockItem(FTBlocks.MIXED_FLATBACK_SHELL_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MIXED_FLATBACK_SHELL_BRICK_SLAB = ITEMS.register("mixed_flatback_shell_brick_slab", () -> new BlockItem(FTBlocks.MIXED_FLATBACK_SHELL_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> MIXED_PEBBLE_SHELL_BRICKS = ITEMS.register("mixed_pebble_shell_bricks", () -> new BlockItem(FTBlocks.MIXED_PEBBLE_SHELL_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MIXED_PEBBLE_SHELL_BRICK_STAIRS = ITEMS.register("mixed_pebble_shell_brick_stairs", () -> new BlockItem(FTBlocks.MIXED_PEBBLE_SHELL_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MIXED_PEBBLE_SHELL_BRICK_SLAB = ITEMS.register("mixed_pebble_shell_brick_slab", () -> new BlockItem(FTBlocks.MIXED_PEBBLE_SHELL_BRICK_SLAB.get(), new Item.Properties()));

    //All Snails
    public static final RegistryObject<Item> BANNER_PATTERN_SHELL = ITEMS.register("banner_pattern_shell", () -> new BannerPatternItem(FTTags.PATTERN_ITEM_SHELL, new Item.Properties().stacksTo(1)));

//endregion

//region MISC
    //public static final RegistryObject<Item> WANDERING_SAILOR_SPAWN_EGG = ITEMS.register("wandering_sailor_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.WANDERING_SAILOR, 0x31a7cf, 0xd3904c, new Item.Properties()));
    //public static final RegistryObject<Item> GOLIATH_GARDEN_CRAB_SPAWN_EGG = ITEMS.register("goliath_garden_crab_spawn_egg", () -> new ForgeSpawnEggItem(FTEntities.GOLIATH_GARDEN_CRAB, 0xbd673a, 0x59ab30, new Item.Properties()));
    public static final RegistryObject<Item> MUSIC_DISC_CRASHING_TIDES = ITEMS.register("music_disc_crashing_tides", () -> new RecordItem(1, FTSounds.CRASHING_TIDES, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 4000));
    //public static final RegistryObject<Item> MUSIC_DISC_WHISTLING_WYVERNS = ITEMS.register("music_disc_whistling_wyverns", () -> new RecordItem(1, FTSounds.WHISTLING_WYVERNS, new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
//endregion

}