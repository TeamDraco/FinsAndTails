package blueportal.finsandstails;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = FinsAndTails.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FTConfig {
    public static boolean finsFishingLoot;

    @SubscribeEvent
    public static void configLoad(ModConfigEvent.Reloading event) {
        try {
            IConfigSpec spec = event.getConfig().getSpec();
            if (spec == Common.SPEC) Common.reload();
        }
        catch (Throwable e) {
            FinsAndTails.LOGGER.error("Something went wrong updating the Fins and Tails config, using previous or default values! {}", e.toString());
        }
    }

    public static class Common {
        public static final Common INSTANCE;
        public static final ForgeConfigSpec SPEC;

        static {
            Pair<Common, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Common::new);
            INSTANCE = pair.getLeft();
            SPEC = pair.getRight();
        }

        public final ForgeConfigSpec.BooleanValue finsFishingLoot;

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            finsFishingLoot = builder.comment("Should Fins and Tails fish should appear in the fishing loot table?").define("fins_fishing_loot", true);
            builder.pop();
        }

        public static void reload() {
            FTConfig.finsFishingLoot = INSTANCE.finsFishingLoot.get();
        }
    }
}