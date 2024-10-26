package blueportal.finsandstails.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import blueportal.finsandstails.FinsAndTails;

public class FTSounds {

    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FinsAndTails.MOD_ID);

    public static final RegistryObject<SoundEvent> MUDHORSE_DEATH = REGISTER.register("mudhorse.death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "mudhorse.death")));
    public static final RegistryObject<SoundEvent> MUDHORSE_HURT = REGISTER.register("mudhorse.hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "mudhorse.hurt")));
    public static final RegistryObject<SoundEvent> MUDHORSE_AMBIENT = REGISTER.register("mudhorse.ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "mudhorse.ambient")));
    public static final RegistryObject<SoundEvent> RUBBER_BELLY_GLIDER_DEATH = REGISTER.register("rubber_belly_glider.death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "rubber_belly_glider.death")));
    public static final RegistryObject<SoundEvent> RUBBER_BELLY_GLIDER_HURT = REGISTER.register("rubber_belly_glider.hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "rubber_belly_glider.hurt")));
    public static final RegistryObject<SoundEvent> RUBBER_BELLY_GLIDER_AMBIENT = REGISTER.register("rubber_belly_glider.ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "rubber_belly_glider.ambient")));
    public static final RegistryObject<SoundEvent> PENGLIL_DEATH = REGISTER.register("penglil.death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "penglil.death")));
    public static final RegistryObject<SoundEvent> PENGLIL_HURT = REGISTER.register("penglil.hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "penglil.hurt")));
    public static final RegistryObject<SoundEvent> PENGLIL_AMBIENT = REGISTER.register("penglil.ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "penglil.ambient")));
    public static final RegistryObject<SoundEvent> CRAB_DEATH = REGISTER.register("crab.death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "crab.death")));
    public static final RegistryObject<SoundEvent> CRAB_CRUNCH = REGISTER.register("crab.crunch", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "crab.crunch")));

    public static final RegistryObject<SoundEvent> HORATEE_AMBIENT = REGISTER.register("horatee.ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "horatee.ambient")));
    public static final RegistryObject<SoundEvent> HORATEE_HURT = REGISTER.register("horatee.hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "horatee.hurt")));
    public static final RegistryObject<SoundEvent> HORATEE_DEATH = REGISTER.register("horatee.death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "horatee.death")));


    public static final RegistryObject<SoundEvent> DIDGERIDOO_PLAY = REGISTER.register("didgeridoo.play", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "didgeridoo.play")));
    public static final RegistryObject<SoundEvent> CRASHING_TIDES = REGISTER.register("music_disc.crashing_tides", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "music_disc.crashing_tides")));
    public static final RegistryObject<SoundEvent> WHISTLING_WYVERNS = REGISTER.register("music_disc.whistling_wyverns", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "music_disc.whistling_wyverns")));
    public static final RegistryObject<SoundEvent> WARBLE = REGISTER.register("music_disc.warble", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "music_disc.warble")));
    public static final RegistryObject<SoundEvent> JETPACK_USE = REGISTER.register("jetpack.use", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "jetpack.use")));
    public static final RegistryObject<SoundEvent> FLATBACK_SUCKER_CLICK = REGISTER.register("click", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "click")));
    public static final RegistryObject<SoundEvent> WHERBLE_DEATH = REGISTER.register("wherble.death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "wherble.death")));
    public static final RegistryObject<SoundEvent> WHERBLE_HURT = REGISTER.register("wherble.hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "wherble.hurt")));
    public static final RegistryObject<SoundEvent> WHERBLE_AMBIENT = REGISTER.register("wherble.ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "wherble.ambient")));
    public static final RegistryObject<SoundEvent> WHERBLE_THROW = REGISTER.register("wherble.throw", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "wherble.throw")));
    public static final RegistryObject<SoundEvent> WANDERING_SAILOR_AMBIENT = REGISTER.register("wandering_sailor.ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "wandering_sailor.ambient")));
    public static final RegistryObject<SoundEvent> WANDERING_SAILOR_HURT = REGISTER.register("wandering_sailor.hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "wandering_sailor.hurt")));
    public static final RegistryObject<SoundEvent> WANDERING_SAILOR_DEATH = REGISTER.register("wandering_sailor.death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "wandering_sailor.death")));
    public static final RegistryObject<SoundEvent> WANDERING_SAILOR_TRADE = REGISTER.register("wandering_sailor.trade", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "wandering_sailor.trade")));
    public static final RegistryObject<SoundEvent> WANDERING_SAILOR_YES = REGISTER.register("wandering_sailor.yes", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "wandering_sailor.yes")));
    public static final RegistryObject<SoundEvent> WANDERING_SAILOR_NO = REGISTER.register("wandering_sailor.no", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FinsAndTails.MOD_ID, "wandering_sailor.no")));
}