package blueportal.finsandstails.registry;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.container.CrabCruncherContainer;
import blueportal.finsandstails.common.container.MudhorsePouchContainer;

public class FTContainers {
    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, FinsAndTails.MOD_ID);

    public static final RegistryObject<MenuType<MudhorsePouchContainer>> MUDHORSE_POUCH = REGISTER.register("mudhorse_pouch", () -> IForgeMenuType.create((windowId, playerInventory, data) -> new MudhorsePouchContainer(windowId, playerInventory)));
    public static final RegistryObject<MenuType<CrabCruncherContainer>> CRAB_CRUNCHER = REGISTER.register("crab_cruncher", () -> IForgeMenuType.create((windowId, playerInventory, data) -> new CrabCruncherContainer(windowId, playerInventory)));
}
