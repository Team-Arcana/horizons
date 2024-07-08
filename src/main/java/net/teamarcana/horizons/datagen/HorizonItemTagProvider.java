package net.teamarcana.horizons.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.init.HorizonItems;
import net.teamarcana.horizons.init.HorizonTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class HorizonItemTagProvider extends ItemTagsProvider {
    public HorizonItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Horizons.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(HorizonTags.Items.BACKPACK).add(
                HorizonItems.BACKPACK.get(),
                HorizonItems.WHITE_BACKPACK.get(),
                HorizonItems.LIGHT_GRAY_BACKPACK.get(),
                HorizonItems.GRAY_BACKPACK.get(),
                HorizonItems.BLACK_BACKPACK.get(),
                HorizonItems.BROWN_BACKPACK.get(),
                HorizonItems.RED_BACKPACK.get(),
                HorizonItems.ORANGE_BACKPACK.get(),
                HorizonItems.YELLOW_BACKPACK.get(),
                HorizonItems.LIME_BACKPACK.get(),
                HorizonItems.GREEN_BACKPACK.get(),
                HorizonItems.CYAN_BACKPACK.get(),
                HorizonItems.LIGHT_BLUE_BACKPACK.get(),
                HorizonItems.BLUE_BACKPACK.get(),
                HorizonItems.PURPLE_BACKPACK.get(),
                HorizonItems.MAGENTA_BACKPACK.get(),
                HorizonItems.PINK_BACKPACK.get()
                );
        tag(HorizonTags.Items.BACKPACK_BLACKLIST).addTag(HorizonTags.Items.BACKPACK).add(
                Items.SHULKER_BOX,
                Items.WHITE_SHULKER_BOX,
                Items.LIGHT_GRAY_SHULKER_BOX,
                Items.GRAY_SHULKER_BOX,
                Items.BLACK_SHULKER_BOX,
                Items.BROWN_SHULKER_BOX,
                Items.RED_SHULKER_BOX,
                Items.ORANGE_SHULKER_BOX,
                Items.YELLOW_SHULKER_BOX,
                Items.LIME_SHULKER_BOX,
                Items.GREEN_SHULKER_BOX,
                Items.CYAN_SHULKER_BOX,
                Items.LIGHT_BLUE_SHULKER_BOX,
                Items.BLUE_SHULKER_BOX,
                Items.PURPLE_SHULKER_BOX,
                Items.MAGENTA_SHULKER_BOX,
                Items.PINK_SHULKER_BOX
        );
    }
}
