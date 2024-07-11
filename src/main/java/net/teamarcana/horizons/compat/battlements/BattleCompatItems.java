package net.teamarcana.horizons.compat.battlements;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.teamarcana.battlements.init.BattleTiers;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.item.PaxelItem;

public class BattleCompatItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Horizons.MOD_ID);

    public static final DeferredItem<PaxelItem> STEEL_PAXEL = ITEMS.register("steel_paxel", ()-> new PaxelItem(BattleTiers.STEEL, 5.0F, -3.0F, new Item.Properties()));
    public static final DeferredItem<PaxelItem> ENDERIUM_PAXEL = ITEMS.register("enderium_paxel", ()-> new PaxelItem(BattleTiers.ENDERIUM, 5.0F, -3.0F, new Item.Properties()));

    public static void register(IEventBus eventBus){ ITEMS.register(eventBus); }
}
