package net.teamarcana.horizons.init;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.block.BackpackBlock;

import javax.annotation.Nullable;

public class HorizonBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Horizons.MOD_ID);
    private static final BlockBehaviour.StatePredicate NOT_CLOSED_BACKPACK = (state, getter, pos) -> getter.getBlockEntity(pos) instanceof ShulkerBoxBlockEntity shulkerboxblockentity
            ? shulkerboxblockentity.isClosed()
            : true;

    // Backpacks
    public static final DeferredBlock<BackpackBlock> BACKPACK = BLOCKS.register("backpack", ()-> backpack(null, MapColor.TERRACOTTA_WHITE));
    public static final DeferredBlock<BackpackBlock> WHITE_BACKPACK = BLOCKS.register("white_backpack", ()-> backpack(DyeColor.WHITE, MapColor.SNOW));
    public static final DeferredBlock<BackpackBlock> ORANGE_BACKPACK = BLOCKS.register("orange_backpack", ()-> backpack(DyeColor.ORANGE, MapColor.COLOR_ORANGE));
    public static final DeferredBlock<BackpackBlock> MAGENTA_BACKPACK = BLOCKS.register("magenta_backpack", ()-> backpack(DyeColor.MAGENTA, MapColor.COLOR_MAGENTA));
    public static final DeferredBlock<BackpackBlock> LIGHT_BLUE_BACKPACK = BLOCKS.register("light_blue_backpack", ()-> backpack(DyeColor.LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE));
    public static final DeferredBlock<BackpackBlock> YELLOW_BACKPACK = BLOCKS.register("yellow_backpack", ()-> backpack(DyeColor.YELLOW, MapColor.COLOR_YELLOW));
    public static final DeferredBlock<BackpackBlock> LIME_BACKPACK = BLOCKS.register("lime_backpack", ()-> backpack(DyeColor.LIME, MapColor.COLOR_LIGHT_GREEN));
    public static final DeferredBlock<BackpackBlock> PINK_BACKPACK = BLOCKS.register("pink_backpack", ()-> backpack(DyeColor.PINK, MapColor.COLOR_PINK));
    public static final DeferredBlock<BackpackBlock> GRAY_BACKPACK = BLOCKS.register("gray_backpack", ()-> backpack(DyeColor.GRAY, MapColor.COLOR_GRAY));
    public static final DeferredBlock<BackpackBlock> LIGHT_GRAY_BACKPACK = BLOCKS.register("light_gray_backpack", ()-> backpack(DyeColor.LIGHT_GRAY, MapColor.COLOR_LIGHT_GRAY));
    public static final DeferredBlock<BackpackBlock> CYAN_BACKPACK = BLOCKS.register("cyan_backpack", ()-> backpack(DyeColor.CYAN, MapColor.COLOR_CYAN));
    public static final DeferredBlock<BackpackBlock> PURPLE_BACKPACK = BLOCKS.register("purple_backpack", ()-> backpack(DyeColor.PURPLE, MapColor.TERRACOTTA_PURPLE));
    public static final DeferredBlock<BackpackBlock> BLUE_BACKPACK = BLOCKS.register("blue_backpack", ()-> backpack(DyeColor.BLUE, MapColor.COLOR_BLUE));
    public static final DeferredBlock<BackpackBlock> BROWN_BACKPACK = BLOCKS.register("brown_backpack", ()-> backpack(DyeColor.BROWN, MapColor.COLOR_BROWN));
    public static final DeferredBlock<BackpackBlock> GREEN_BACKPACK = BLOCKS.register("green_backpack", ()-> backpack(DyeColor.GREEN, MapColor.COLOR_GREEN));
    public static final DeferredBlock<BackpackBlock> RED_BACKPACK = BLOCKS.register("red_backpack", ()-> backpack(DyeColor.RED, MapColor.COLOR_RED));
    public static final DeferredBlock<BackpackBlock> BLACK_BACKPACK = BLOCKS.register("black_backpack", ()-> backpack(DyeColor.BLACK, MapColor.COLOR_BLACK));


    public static void register(IEventBus eventBus){ BLOCKS.register(eventBus); }
    private static BackpackBlock backpack(@Nullable DyeColor color, MapColor mapColor) {
        return new BackpackBlock(
                color,
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor)
                        .sound(SoundType.WOOL)
                        .forceSolidOn()
                        .strength(1.0F)
                        .dynamicShape()
                        .noOcclusion()
                        .isSuffocating(NOT_CLOSED_BACKPACK)
                        .isViewBlocking(NOT_CLOSED_BACKPACK)
                        .pushReaction(PushReaction.DESTROY)
        );
    }
}
