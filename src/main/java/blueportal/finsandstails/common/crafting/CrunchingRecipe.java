package blueportal.finsandstails.common.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import blueportal.finsandstails.registry.FTBlocks;
import blueportal.finsandstails.registry.FTRecipes;

public class CrunchingRecipe implements Recipe<Container> {
    public static final Serializer SERIALIZER = new Serializer();
    private final Ingredient base;
    private final Ingredient addition;
    private final ItemStack result;
    private final ResourceLocation recipeId;

    public CrunchingRecipe(ResourceLocation recipeId, Ingredient base, Ingredient addition, ItemStack result) {
        this.recipeId = recipeId;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    @Override
    public boolean matches(Container inv, Level level) {
        return this.base.test(inv.getItem(0)) && this.addition.test(inv.getItem(1));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(FTBlocks.CRAB_CRUNCHER.get());
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FTRecipes.CRUNCHING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return FTRecipes.CRUNCHING_TYPE.get();
    }

    public boolean isAdditionIngredient(ItemStack p_44536_) {
        return this.addition.test(p_44536_);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> i = NonNullList.create();

        i.add(base);
        i.add(addition);
        i.add(Ingredient.of(new ItemStack(FTBlocks.CRAB_CRUNCHER.get().asItem())));

        return i;
    }

    public static class Serializer implements RecipeSerializer<CrunchingRecipe> {

        public Serializer() {
        }

        @Override
        public CrunchingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition"));
            Item item = ShapedRecipe.itemFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new CrunchingRecipe(recipeId, ingredient, ingredient1, new ItemStack(item));
        }

        @Override
        public CrunchingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            Ingredient ingredient1 = Ingredient.fromNetwork(buffer);
            ItemStack itemstack = buffer.readItem();
            return new CrunchingRecipe(recipeId, ingredient, ingredient1, itemstack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CrunchingRecipe recipe) {
            recipe.base.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
