package net.teamarcana.horizons.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.init.HorizonBlocks;

public class HorizonBlockStateProvider extends BlockStateProvider {
    public HorizonBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Horizons.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    }

    private void blockWithItem(Block block){
        simpleBlockWithItem(block, cubeAll(block));
    }
}
