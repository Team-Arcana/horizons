package net.teamarcana.horizons.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.teamarcana.battlements.init.BattleItems;
import net.teamarcana.horizons.compat.battlements.BattleCompatItems;
import net.teamarcana.horizons.init.HorizonItems;
import net.teamarcana.horizons.recipe.BackpackColoring;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HorizonRecipeProvider extends RecipeProvider {

    public HorizonRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    List<Item> dyes = List.of(
            Items.BLACK_DYE,
            Items.BLUE_DYE,
            Items.BROWN_DYE,
            Items.CYAN_DYE,
            Items.GRAY_DYE,
            Items.GREEN_DYE,
            Items.LIGHT_BLUE_DYE,
            Items.LIGHT_GRAY_DYE,
            Items.LIME_DYE,
            Items.MAGENTA_DYE,
            Items.ORANGE_DYE,
            Items.PINK_DYE,
            Items.PURPLE_DYE,
            Items.RED_DYE,
            Items.YELLOW_DYE,
            Items.WHITE_DYE
    );

    List<Item> backpacks = List.of(
            HorizonItems.BLACK_BACKPACK.get(),
            HorizonItems.BLUE_BACKPACK.get(),
            HorizonItems.BROWN_BACKPACK.get(),
            HorizonItems.CYAN_BACKPACK.get(),
            HorizonItems.GRAY_BACKPACK.get(),
            HorizonItems.GREEN_BACKPACK.get(),
            HorizonItems.LIGHT_BLUE_BACKPACK.get(),
            HorizonItems.LIGHT_GRAY_BACKPACK.get(),
            HorizonItems.LIME_BACKPACK.get(),
            HorizonItems.MAGENTA_BACKPACK.get(),
            HorizonItems.ORANGE_BACKPACK.get(),
            HorizonItems.PINK_BACKPACK.get(),
            HorizonItems.PURPLE_BACKPACK.get(),
            HorizonItems.RED_BACKPACK.get(),
            HorizonItems.YELLOW_BACKPACK.get(),
            HorizonItems.WHITE_BACKPACK.get()
    );

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, HorizonItems.BACKPACK)
                .pattern("| |")
                .pattern("LiL")
                .pattern("LLL")
                .define('L', Items.LEATHER)
                .define('|', Items.STRING)
                .define('i', Items.IRON_NUGGET)
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(output);
        SpecialRecipeBuilder.special(BackpackColoring::new).save(output, "backpack_coloring");

        // paxels
        paxelRecipe(HorizonItems.WOODEN_PAXEL.get(), Ingredient.of(Items.WOODEN_PICKAXE), Ingredient.of(Items.WOODEN_AXE), Ingredient.of(Items.WOODEN_SHOVEL), Items.STICK, output);
        paxelRecipe(HorizonItems.STONE_PAXEL.get(), Ingredient.of(Items.STONE_PICKAXE), Ingredient.of(Items.STONE_AXE), Ingredient.of(Items.STONE_SHOVEL), Items.STONE_PICKAXE, output);
        paxelRecipe(HorizonItems.GOLDEN_PAXEL.get(), Ingredient.of(Items.GOLDEN_PICKAXE), Ingredient.of(Items.GOLDEN_AXE), Ingredient.of(Items.GOLDEN_SHOVEL), Items.GOLD_INGOT, output);
        paxelRecipe(HorizonItems.IRON_PAXEL.get(), Ingredient.of(Items.IRON_PICKAXE), Ingredient.of(Items.IRON_AXE), Ingredient.of(Items.IRON_SHOVEL), Items.IRON_INGOT, output);
        paxelRecipe(BattleCompatItems.STEEL_PAXEL.get(), Ingredient.of(BattleItems.STEEL_PICKAXE), Ingredient.of(BattleItems.STEEL_AXE), Ingredient.of(BattleItems.STEEL_SHOVEL), Items.STICK, output);
        paxelRecipe(HorizonItems.DIAMOND_PAXEL.get(), Ingredient.of(Items.DIAMOND_PICKAXE), Ingredient.of(Items.DIAMOND_AXE), Ingredient.of(Items.DIAMOND_SHOVEL), Items.DIAMOND, output);
        paxelRecipe(HorizonItems.NETHERITE_PAXEL.get(), Ingredient.of(Items.NETHERITE_PICKAXE), Ingredient.of(Items.NETHERITE_AXE), Ingredient.of(Items.NETHERITE_SHOVEL), Items.NETHERITE_INGOT, output);
        paxelRecipe(BattleCompatItems.ENDERIUM_PAXEL.get(), Ingredient.of(BattleItems.ENDERIUM_PICKAXE), Ingredient.of(BattleItems.ENDERIUM_AXE), Ingredient.of(BattleItems.ENDERIUM_SHOVEL), BattleItems.ENDERIUM_INGOT, output);

        netheriteSmithing(output, HorizonItems.DIAMOND_PAXEL.get(), RecipeCategory.TOOLS, HorizonItems.NETHERITE_PAXEL.get());
        netheriteSmithing(output, HorizonItems.DIAMOND_PAXEL.get(), RecipeCategory.TOOLS, BattleCompatItems.ENDERIUM_PAXEL.get());
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

    public static void colorBlockWithDye(RecipeOutput output, List<Item> pDyes, List<Item> items, String group) {
        for (int i = 0; i < pDyes.size(); i++) {
            Item dye = pDyes.get(i);
            Item item = items.get(i);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, item)
                    .requires(dye)
                    .requires(Ingredient.of(items.stream().filter(item2 -> !item2.equals(item)).map(ItemStack::new)))
                    .group(group)
                    .unlockedBy("has_needed_dye", has(dye))
                    .save(output, "dye_" + getItemName(item));
        }
    }
    public static void paxelRecipe(ItemLike result, Ingredient pickaxe, Ingredient axe, Ingredient shovel, ItemLike unlockItem, RecipeOutput output){
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result)
                .pattern("ASP")
                .pattern(" | ")
                .pattern(" | ")
                .define('S', shovel)
                .define('P', pickaxe)
                .define('A', axe)
                .define('|', Items.STICK)
                .unlockedBy(getHasName(unlockItem), has(unlockItem))
                .save(output);
    }
}
