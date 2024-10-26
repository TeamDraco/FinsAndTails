package blueportal.finsandstails.common.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import blueportal.finsandstails.FinsAndTails;
import blueportal.finsandstails.registry.FTRecipes;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class FinsJEIPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_ID = new ResourceLocation(FinsAndTails.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();

        findRecipesByType(FTRecipes.CRUNCHING_TYPE.get(), manager);
    }

    private static <C extends Container, T extends Recipe<C>> List<T> findRecipesByType(RecipeType<T> type, RecipeManager recipeManager) {
        return recipeManager.getAllRecipesFor(type);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new CrunchingRecipeCategory(helper));
    }

    private static Collection<?> getRecipes(RecipeManager manager, RecipeType<?> type) {
        return manager.getRecipes().parallelStream().filter(recipe -> recipe.getType() == type).collect(Collectors.toList());
    }
}
