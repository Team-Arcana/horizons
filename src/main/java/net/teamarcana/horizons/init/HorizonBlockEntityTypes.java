package net.teamarcana.horizons.init;

import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.block.blockEntity.BackpackBlockEntity;

import java.util.Objects;
import java.util.function.Supplier;

public class HorizonBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Horizons.MOD_ID);

    public static final Supplier<BlockEntityType<BackpackBlockEntity>> BACKPACK = BLOCK_ENTITY_TYPES.register("backpack", ()->
            BlockEntityType.Builder.of(
                    BackpackBlockEntity::new,
                    HorizonBlocks.BACKPACK.get(),
                    HorizonBlocks.BLACK_BACKPACK.get(),
                    HorizonBlocks.BLUE_BACKPACK.get(),
                    HorizonBlocks.BROWN_BACKPACK.get(),
                    HorizonBlocks.CYAN_BACKPACK.get(),
                    HorizonBlocks.GRAY_BACKPACK.get(),
                    HorizonBlocks.GREEN_BACKPACK.get(),
                    HorizonBlocks.LIGHT_BLUE_BACKPACK.get(),
                    HorizonBlocks.LIGHT_GRAY_BACKPACK.get(),
                    HorizonBlocks.LIME_BACKPACK.get(),
                    HorizonBlocks.MAGENTA_BACKPACK.get(),
                    HorizonBlocks.ORANGE_BACKPACK.get(),
                    HorizonBlocks.PINK_BACKPACK.get(),
                    HorizonBlocks.PURPLE_BACKPACK.get(),
                    HorizonBlocks.RED_BACKPACK.get(),
                    HorizonBlocks.WHITE_BACKPACK.get(),
                    HorizonBlocks.YELLOW_BACKPACK.get()

            ).build(null)
    );

    public static void register(IEventBus eventBus){ BLOCK_ENTITY_TYPES.register(eventBus); }
}
