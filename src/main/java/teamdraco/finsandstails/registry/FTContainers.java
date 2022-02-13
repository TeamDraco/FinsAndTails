package teamdraco.finsandstails.registry;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import teamdraco.finsandstails.FinsAndTails;
import teamdraco.finsandstails.common.container.CrabCruncherContainer;

public class FTContainers {
    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.CONTAINERS, FinsAndTails.MOD_ID);

    //public static final RegistryObject<MenuType<MudhorsePouchContainer>> MUDHORSE_POUCH = REGISTER.register("mudhorse_pouch", () -> IForgeMenuType.<MudhorsePouchContainer>create(MudhorsePouchContainer::new));
    public static final RegistryObject<MenuType<CrabCruncherContainer>> CRAB_CRUNCHER = REGISTER.register("crab_cruncher", () -> IForgeMenuType.create((windowId, playerInventory, data) -> new CrabCruncherContainer(windowId, playerInventory)));
}