package net.teamarcana.horizons.init;

import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.item.BackpackItem;

public class HorizonItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Horizons.MOD_ID);

    public static final DeferredItem<Item> CREATIVE_ICON = ITEMS.registerSimpleItem("horizons_icon", new Item.Properties().stacksTo(1));

    // NEW ITEMS
    public static final DeferredItem<Item> BACKPACK = ITEMS.registerSimpleItem("backpack", new Item.Properties().stacksTo(1));

    public static void register(IEventBus eventBus){ ITEMS.register(eventBus); }
}
