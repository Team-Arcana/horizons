package net.teamarcana.horizons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.client.screen.BackpackMenu;

import java.util.function.Supplier;

public class HorizonMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Horizons.MOD_ID);

    public static final Supplier<MenuType<BackpackMenu>> BACKPACK_MENU = MENUS.register("backpack", () -> new MenuType<>(BackpackMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
