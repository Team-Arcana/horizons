package net.teamarcana.horizons.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.teamarcana.horizons.datagen.loot.HorizonBlockLootTables;
import net.teamarcana.horizons.datagen.loot.HorizonEntityLootTables;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HorizonLootTableProvider {
    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider){
        return new LootTableProvider(output, Collections.EMPTY_SET, List.of(
                HorizonBlockLootTables.getEntry(),
                HorizonEntityLootTables.getEntry()
        ), lookupProvider);
    }
}
