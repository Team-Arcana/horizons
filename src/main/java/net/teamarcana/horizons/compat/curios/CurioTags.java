package net.teamarcana.horizons.compat.curios;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CurioTags {
    public static final TagKey<Item> CURIO_BACK = itemTag("back");
    private static TagKey<Item> itemTag(String path){
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", path));
    }
}
