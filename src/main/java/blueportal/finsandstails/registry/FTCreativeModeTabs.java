package blueportal.finsandstails.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import blueportal.finsandstails.FinsAndTails;

@Mod.EventBusSubscriber(modid = FinsAndTails.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FTCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FinsAndTails.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FINS_AND_TAILS = CREATIVE_MODE_TABS.register("fins_and_tails", () -> CreativeModeTab.builder()
            .icon(FTItems.WEE.get()::getDefaultInstance)
            .title(Component.translatable("itemGroup.finsandtails"))
            .displayItems((itemDisplayParameters, output) -> {
                FTItems.ITEMS.getEntries().forEach(itemRegistryObject -> {
                    output.accept(itemRegistryObject.get());
                });
            })
            .build());

}
