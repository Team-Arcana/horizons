package net.teamarcana.horizons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.recipe.BackpackColoring;

import java.util.function.Supplier;

public class HorizonRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Horizons.MOD_ID);

    public static final Supplier<RecipeSerializer<BackpackColoring>> BACKPACK_COLORING = SERIALIZERS.register("backpack_coloring", ()-> new SimpleCraftingRecipeSerializer<>(BackpackColoring::new));
    public static void register(IEventBus eventBus){ SERIALIZERS.register(eventBus); }
}
