package net.teamarcana.horizons.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HorizonRecipeProvider extends RecipeProvider {

    public HorizonRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
    }

    public static void smokingRecipe(RecipeOutput output,  List<ItemLike> pIngredients,  RecipeCategory pCategory,  ItemLike pResult,  float pExperience,  int cookingTime,  String pGroup) {
        oreCooking(output, RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, pIngredients, pCategory, pResult, pExperience, cookingTime, pGroup, "_from_smoking");
    }

    public static void campfireRecipe(RecipeOutput output, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(output, RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_camp_fire");
    }

    public static void swordRecipe(ItemLike result, Ingredient ingot, ItemLike unlockItem, RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern(" S ")
                .pattern(" S ")
                .pattern(" | ")
                .define('S', ingot)
                .define('|', Items.STICK)
                .unlockedBy(getHasName(unlockItem), has(unlockItem))
                .save(output);
    }
    public static void pickaxeRecipe(ItemLike result, Ingredient ingot, ItemLike unlockItem, RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("SSS")
                .pattern(" | ")
                .pattern(" | ")
                .define('S', ingot)
                .define('|', Items.STICK)
                .unlockedBy(getHasName(unlockItem), has(unlockItem))
                .save(output);
    }
    public static void axeRecipe(ItemLike result, Ingredient ingot, ItemLike unlockItem, RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("SS ")
                .pattern("S| ")
                .pattern(" | ")
                .define('S', ingot)
                .define('|', Items.STICK)
                .unlockedBy(getHasName(unlockItem), has(unlockItem))
                .save(output);
    }
    public static void shovelRecipe(ItemLike result, Ingredient ingot, ItemLike unlockItem, RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern(" S ")
                .pattern(" | ")
                .pattern(" | ")
                .define('S', ingot)
                .define('|', Items.STICK)
                .unlockedBy(getHasName(unlockItem), has(unlockItem))
                .save(output);
    }
    public static void hoeRecipe(ItemLike result, Ingredient ingot, ItemLike unlockItem, RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("SS ")
                .pattern(" | ")
                .pattern(" | ")
                .define('S', ingot)
                .define('|', Items.STICK)
                .unlockedBy(getHasName(unlockItem), has(unlockItem))
                .save(output);
    }
    public static void helmetRecipe(ItemLike result, Ingredient ingot, ItemLike unlockItem, RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("SSS")
                .pattern("S S")
                .define('S', ingot)
                .unlockedBy(getHasName(unlockItem), has(unlockItem))
                .save(output);
    }
    public static void chestplateRecipe(ItemLike result, Ingredient ingot, ItemLike unlockItem, RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("S S")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ingot)
                .unlockedBy(getHasName(unlockItem), has(unlockItem))
                .save(output);
    }
    public static void leggingsRecipe(ItemLike result, Ingredient ingot, ItemLike unlockItem, RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("SSS")
                .pattern("S S")
                .pattern("S S")
                .define('S', ingot)
                .unlockedBy(getHasName(unlockItem), has(unlockItem))
                .save(output);
    }
    public static void bootsRecipe(ItemLike result, Ingredient ingot, ItemLike unlockItem, RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result)
                .pattern("S S")
                .pattern("S S")
                .define('S', ingot)
                .unlockedBy(getHasName(unlockItem), has(unlockItem))
                .save(output);
    }
}
