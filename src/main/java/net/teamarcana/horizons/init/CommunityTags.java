package net.teamarcana.horizons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CommunityTags {

    public static TagKey<Item> TOOLS_PAXEL = itemTag("tools/paxel");
    public static TagKey<Item> TOOLS_PICKAXE = itemTag("tools/pickaxe");
    public static TagKey<Item> TOOLS_AXE = itemTag("tools/axe");
    public static TagKey<Item> TOOLS_SHOVEL = itemTag("tools/shovel");

    private static TagKey<Item> itemTag(String path){
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
    }
    private static TagKey<Block> blockTag(String path){
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", path));
    }
}
