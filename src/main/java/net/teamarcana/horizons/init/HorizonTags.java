package net.teamarcana.horizons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.teamarcana.horizons.Horizons;

public class HorizonTags {
    public static class Blocks{
        public static TagKey<Block> BACKPACK_BLACKLIST = tag("block/backpack_blacklist");
        public static TagKey<Block> BACKPACK = tag("block/backpack");

        private static TagKey<Block> tag(String name){
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, name));
        }
    }

    public static class Items {
        public static TagKey<Item> BACKPACK_BLACKLIST = tag("item/backpack_blacklist");
        public static TagKey<Item> BACKPACK = tag("item/backpack");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, name));
        }
    }
}
