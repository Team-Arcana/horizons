package net.teamarcana.horizons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.teamarcana.horizons.Horizons;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class HorizonCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Horizons.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> HORIZONS_TAB = CREATIVE_MODE_TABS.register("horizons", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.horizons")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.INGREDIENTS)
            .icon(() -> HorizonItems.CREATIVE_ICON.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(HorizonItems.BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.WHITE_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.LIGHT_GRAY_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.GRAY_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.BLACK_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.BROWN_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.RED_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.ORANGE_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.YELLOW_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.LIME_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.GREEN_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.CYAN_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.LIGHT_BLUE_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.BLUE_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.PURPLE_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.MAGENTA_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                output.accept(HorizonItems.PINK_BACKPACK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }).build());


    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    public static void register(IEventBus eventBus){ CREATIVE_MODE_TABS.register(eventBus); }
}
