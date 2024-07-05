package net.teamarcana.horizons.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.teamarcana.horizons.Horizons;

public class HorizonBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Horizons.MOD_ID);


    public static void register(IEventBus eventBus){ BLOCKS.register(eventBus); }
}
