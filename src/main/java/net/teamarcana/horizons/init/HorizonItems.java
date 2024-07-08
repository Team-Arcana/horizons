package net.teamarcana.horizons.init;

import net.minecraft.core.Holder;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.block.BackpackBlock;
import net.teamarcana.horizons.item.BackpackItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class HorizonItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Horizons.MOD_ID);

    public static final DeferredItem<Item> CREATIVE_ICON = ITEMS.registerSimpleItem("horizons_icon", new Item.Properties().stacksTo(1));

    // NEW ITEMS
    //public static final DeferredItem<Item> BACKPACK = ITEMS.registerSimpleItem("backpack", new Item.Properties().stacksTo(1));

    // BACKPACKS
    public static final DeferredItem<BackpackItem> BACKPACK = registerBackpackItem(HorizonBlocks.BACKPACK, null);
    public static final DeferredItem<BackpackItem> WHITE_BACKPACK = registerBackpackItem(HorizonBlocks.WHITE_BACKPACK, DyeColor.WHITE);
    public static final DeferredItem<BackpackItem> ORANGE_BACKPACK = registerBackpackItem(HorizonBlocks.ORANGE_BACKPACK, DyeColor.ORANGE);
    public static final DeferredItem<BackpackItem> MAGENTA_BACKPACK = registerBackpackItem(HorizonBlocks.MAGENTA_BACKPACK, DyeColor.MAGENTA);
    public static final DeferredItem<BackpackItem> LIGHT_BLUE_BACKPACK = registerBackpackItem(HorizonBlocks.LIGHT_BLUE_BACKPACK, DyeColor.LIGHT_BLUE);
    public static final DeferredItem<BackpackItem> YELLOW_BACKPACK = registerBackpackItem(HorizonBlocks.YELLOW_BACKPACK, DyeColor.YELLOW);
    public static final DeferredItem<BackpackItem> LIME_BACKPACK = registerBackpackItem(HorizonBlocks.LIME_BACKPACK, DyeColor.LIME);
    public static final DeferredItem<BackpackItem> PINK_BACKPACK = registerBackpackItem(HorizonBlocks.PINK_BACKPACK, DyeColor.PINK);
    public static final DeferredItem<BackpackItem> GRAY_BACKPACK = registerBackpackItem(HorizonBlocks.GRAY_BACKPACK, DyeColor.GRAY);
    public static final DeferredItem<BackpackItem> LIGHT_GRAY_BACKPACK = registerBackpackItem(HorizonBlocks.LIGHT_GRAY_BACKPACK, DyeColor.LIGHT_GRAY);
    public static final DeferredItem<BackpackItem> CYAN_BACKPACK = registerBackpackItem(HorizonBlocks.CYAN_BACKPACK, DyeColor.CYAN);
    public static final DeferredItem<BackpackItem> PURPLE_BACKPACK = registerBackpackItem(HorizonBlocks.PURPLE_BACKPACK, DyeColor.PURPLE);
    public static final DeferredItem<BackpackItem> BLUE_BACKPACK = registerBackpackItem(HorizonBlocks.BLUE_BACKPACK, DyeColor.BLUE);
    public static final DeferredItem<BackpackItem> BROWN_BACKPACK = registerBackpackItem(HorizonBlocks.BROWN_BACKPACK, DyeColor.BROWN);
    public static final DeferredItem<BackpackItem> GREEN_BACKPACK = registerBackpackItem(HorizonBlocks.GREEN_BACKPACK, DyeColor.GREEN);
    public static final DeferredItem<BackpackItem> RED_BACKPACK = registerBackpackItem(HorizonBlocks.RED_BACKPACK, DyeColor.RED);
    public static final DeferredItem<BackpackItem> BLACK_BACKPACK = registerBackpackItem(HorizonBlocks.BLACK_BACKPACK, DyeColor.BLACK);

    public static void register(IEventBus eventBus){ ITEMS.register(eventBus); }

    // stuff specifically for registering backpack items
    public static DeferredItem<BackpackItem> registerBackpackItem(DeferredBlock<BackpackBlock> backpack, @Nullable DyeColor color) {
        return registerBackpackItem(backpack, color, new Item.Properties());
    }
    public static DeferredItem<BackpackItem> registerBackpackItem(DeferredBlock<BackpackBlock> block, @Nullable DyeColor color, Item.Properties properties) {
        return registerBackpackItem(block.unwrapKey().orElseThrow().location().getPath(), color, block::value, properties);
    }
    public static DeferredItem<BackpackItem> registerBackpackItem(String name, DyeColor color, Supplier<? extends Block> block, Item.Properties properties) {
        return ITEMS.register(name, key -> new BackpackItem(block.get(), color, properties));
    }

    public static ItemStack getBackpackFromColor(DyeColor color){
        List<DeferredHolder<Item, ? extends Item>> items = ITEMS.getEntries().stream().toList();
        for(DeferredHolder<Item, ? extends Item> item : items){
            if(item.get() instanceof BackpackItem backpackItem && backpackItem.getColor() == color){
                return new ItemStack(item.get());
            }
        }
        return ItemStack.EMPTY;
    }
}
