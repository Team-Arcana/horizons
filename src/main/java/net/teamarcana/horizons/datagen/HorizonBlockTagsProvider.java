package net.teamarcana.horizons.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.init.HorizonBlocks;
import net.teamarcana.horizons.init.HorizonTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class HorizonBlockTagsProvider extends BlockTagsProvider {
    public HorizonBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Horizons.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(HorizonTags.Blocks.BACKPACK).add(
                HorizonBlocks.BACKPACK.get(),
                HorizonBlocks.WHITE_BACKPACK.get(),
                HorizonBlocks.LIGHT_GRAY_BACKPACK.get(),
                HorizonBlocks.GRAY_BACKPACK.get(),
                HorizonBlocks.BLACK_BACKPACK.get(),
                HorizonBlocks.BROWN_BACKPACK.get(),
                HorizonBlocks.RED_BACKPACK.get(),
                HorizonBlocks.ORANGE_BACKPACK.get(),
                HorizonBlocks.YELLOW_BACKPACK.get(),
                HorizonBlocks.LIME_BACKPACK.get(),
                HorizonBlocks.GREEN_BACKPACK.get(),
                HorizonBlocks.CYAN_BACKPACK.get(),
                HorizonBlocks.LIGHT_BLUE_BACKPACK.get(),
                HorizonBlocks.BLUE_BACKPACK.get(),
                HorizonBlocks.PURPLE_BACKPACK.get(),
                HorizonBlocks.MAGENTA_BACKPACK.get(),
                HorizonBlocks.PINK_BACKPACK.get()
        );
        tag(HorizonTags.Blocks.BACKPACK_BLACKLIST).addTag(HorizonTags.Blocks.BACKPACK).add(
                Blocks.SHULKER_BOX,
                Blocks.WHITE_SHULKER_BOX,
                Blocks.LIGHT_GRAY_SHULKER_BOX,
                Blocks.GRAY_SHULKER_BOX,
                Blocks.BLACK_SHULKER_BOX,
                Blocks.BROWN_SHULKER_BOX,
                Blocks.RED_SHULKER_BOX,
                Blocks.ORANGE_SHULKER_BOX,
                Blocks.YELLOW_SHULKER_BOX,
                Blocks.LIME_SHULKER_BOX,
                Blocks.GREEN_SHULKER_BOX,
                Blocks.CYAN_SHULKER_BOX,
                Blocks.LIGHT_BLUE_SHULKER_BOX,
                Blocks.BLUE_SHULKER_BOX,
                Blocks.PURPLE_SHULKER_BOX,
                Blocks.MAGENTA_SHULKER_BOX,
                Blocks.PINK_SHULKER_BOX
        );
    }
}
