package net.teamarcana.horizons.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.teamarcana.horizons.Horizons;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Horizons.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new HorizonRecipeProvider(packOutput, lookupProvider));

        generator.addProvider(event.includeServer(), HorizonLootTableProvider.create(packOutput, lookupProvider));

        generator.addProvider(event.includeClient(), new HorizonBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new HorizonItemModelProvider(packOutput, existingFileHelper));

        //generator.addProvider(event.includeServer(), new HorizonWorldGenProvider(packOutput, lookupProvider));

        HorizonBlockTagsProvider blockTagGenerator = generator.addProvider(event.includeServer(),
                new HorizonBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new HorizonItemTagProvider(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));
    }
}