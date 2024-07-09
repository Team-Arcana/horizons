package net.teamarcana.horizons.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.block.BackpackBlock;
import net.teamarcana.horizons.init.HorizonBlocks;

public class HorizonBlockStateProvider extends BlockStateProvider {
    public HorizonBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Horizons.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        /*
        horizontalBlock(HorizonBlocks.BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/backpack")));
        horizontalBlock(HorizonBlocks.WHITE_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/white_backpack")));
        horizontalBlock(HorizonBlocks.ORANGE_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/orange_backpack")));
        horizontalBlock(HorizonBlocks.MAGENTA_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/magenta_backpack")));
        horizontalBlock(HorizonBlocks.LIGHT_BLUE_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/light_blue_backpack")));
        horizontalBlock(HorizonBlocks.YELLOW_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/yellow_backpack")));
        horizontalBlock(HorizonBlocks.LIME_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/lime_backpack")));
        horizontalBlock(HorizonBlocks.PINK_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/pink_backpack")));
        horizontalBlock(HorizonBlocks.GRAY_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/gray_backpack")));
        horizontalBlock(HorizonBlocks.LIGHT_GRAY_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/light_gray_backpack")));
        horizontalBlock(HorizonBlocks.CYAN_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/cyan_backpack")));
        horizontalBlock(HorizonBlocks.PURPLE_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/purple_backpack")));
        horizontalBlock(HorizonBlocks.BLUE_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/blue_backpack")));
        horizontalBlock(HorizonBlocks.BROWN_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/brown_backpack")));
        horizontalBlock(HorizonBlocks.GREEN_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/green_backpack")));
        horizontalBlock(HorizonBlocks.RED_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/red_backpack")));
        horizontalBlock(HorizonBlocks.BLACK_BACKPACK.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/black_backpack")));
         */
        backpackBlock(HorizonBlocks.BACKPACK.get());
        backpackBlock(HorizonBlocks.WHITE_BACKPACK.get());
        backpackBlock(HorizonBlocks.ORANGE_BACKPACK.get());
        backpackBlock(HorizonBlocks.MAGENTA_BACKPACK.get());
        backpackBlock(HorizonBlocks.LIGHT_BLUE_BACKPACK.get());
        backpackBlock(HorizonBlocks.YELLOW_BACKPACK.get());
        backpackBlock(HorizonBlocks.LIME_BACKPACK.get());
        backpackBlock(HorizonBlocks.PINK_BACKPACK.get());
        backpackBlock(HorizonBlocks.GRAY_BACKPACK.get());
        backpackBlock(HorizonBlocks.LIGHT_GRAY_BACKPACK.get());
        backpackBlock(HorizonBlocks.CYAN_BACKPACK.get());
        backpackBlock(HorizonBlocks.PURPLE_BACKPACK.get());
        backpackBlock(HorizonBlocks.BLUE_BACKPACK.get());
        backpackBlock(HorizonBlocks.BROWN_BACKPACK.get());
        backpackBlock(HorizonBlocks.GREEN_BACKPACK.get());
        backpackBlock(HorizonBlocks.RED_BACKPACK.get());
        backpackBlock(HorizonBlocks.BLACK_BACKPACK.get());
    }

    private void blockWithItem(Block block){
        simpleBlockWithItem(block, cubeAll(block));
    }
    public void backpackBlock(Block block){
        horizontalBlock(block, backpackBlockModel(block));
    }
    public ModelFile backpackBlockModel(Block block) {
        if(block instanceof BackpackBlock backpackBlock){
            ResourceLocation textureLocation = backpackBlock.getColor() != null ?
                    ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID,"item/backpack_equipped_"+backpackBlock.getColor().getName()) :
                    ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID,"item/backpack_equipped");

            return models().withExistingParent(name(backpackBlock), ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/backpack_block"))
                    .texture("particle", textureLocation)
                    .texture("layer0", textureLocation);
        } else{
            return new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "block/backpack_block"));
        }
    }

    private String name(Block block) {
        return key(block).getPath();
    }
    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}
