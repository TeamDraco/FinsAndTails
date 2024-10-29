package blueportal.finsandstails.registry;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.common.crafting.CrunchingRecipe;

@Mod.EventBusSubscriber(modid = FinsAndTails.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FTRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, FinsAndTails.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FinsAndTails.MOD_ID);

    public static RegistryObject<RecipeType<CrunchingRecipe>> CRUNCHING_TYPE = RECIPE_TYPE.register("crunching", () -> register("crunching"));
    public static final RegistryObject<CrunchingRecipe.Serializer> CRUNCHING_SERIALIZER = SERIALIZERS.register("crunching", CrunchingRecipe.Serializer::new);

    static <T extends Recipe<?>> RecipeType<T> register(final String name) {
        return new RecipeType<>() {
            public String toString() {
                return FinsAndTails.MOD_ID + ":" + name;
            }
        };
    }
}
