package net.teamarcana.horizons.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.teamarcana.horizons.block.BackpackBlock;
import net.teamarcana.horizons.init.HorizonBlocks;
import net.teamarcana.horizons.init.HorizonItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class HorizonBlockLootTables extends BlockLootSubProvider {
    public HorizonBlockLootTables(HolderLookup.Provider lookupProvider) {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
    }

    @Override
    protected void generate() {
        add(HorizonBlocks.BACKPACK.get(), createBackpackLoot(HorizonBlocks.BACKPACK.get()));
        add(HorizonBlocks.WHITE_BACKPACK.get(), createBackpackLoot(HorizonBlocks.WHITE_BACKPACK.get()));
        add(HorizonBlocks.ORANGE_BACKPACK.get(), createBackpackLoot(HorizonBlocks.ORANGE_BACKPACK.get()));
        add(HorizonBlocks.MAGENTA_BACKPACK.get(), createBackpackLoot(HorizonBlocks.MAGENTA_BACKPACK.get()));
        add(HorizonBlocks.LIGHT_BLUE_BACKPACK.get(), createBackpackLoot(HorizonBlocks.LIGHT_BLUE_BACKPACK.get()));
        add(HorizonBlocks.YELLOW_BACKPACK.get(), createBackpackLoot(HorizonBlocks.YELLOW_BACKPACK.get()));
        add(HorizonBlocks.LIME_BACKPACK.get(), createBackpackLoot(HorizonBlocks.LIME_BACKPACK.get()));
        add(HorizonBlocks.PINK_BACKPACK.get(), createBackpackLoot(HorizonBlocks.PINK_BACKPACK.get()));
        add(HorizonBlocks.GRAY_BACKPACK.get(), createBackpackLoot(HorizonBlocks.GRAY_BACKPACK.get()));
        add(HorizonBlocks.LIGHT_GRAY_BACKPACK.get(), createBackpackLoot(HorizonBlocks.LIGHT_GRAY_BACKPACK.get()));
        add(HorizonBlocks.CYAN_BACKPACK.get(), createBackpackLoot(HorizonBlocks.CYAN_BACKPACK.get()));
        add(HorizonBlocks.PURPLE_BACKPACK.get(), createBackpackLoot(HorizonBlocks.PURPLE_BACKPACK.get()));
        add(HorizonBlocks.BLUE_BACKPACK.get(), createBackpackLoot(HorizonBlocks.BLUE_BACKPACK.get()));
        add(HorizonBlocks.BROWN_BACKPACK.get(), createBackpackLoot(HorizonBlocks.BROWN_BACKPACK.get()));
        add(HorizonBlocks.GREEN_BACKPACK.get(), createBackpackLoot(HorizonBlocks.GREEN_BACKPACK.get()));
        add(HorizonBlocks.RED_BACKPACK.get(), createBackpackLoot(HorizonBlocks.RED_BACKPACK.get()));
        add(HorizonBlocks.BLACK_BACKPACK.get(), createBackpackLoot(HorizonBlocks.BLACK_BACKPACK.get()));
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock,
                (LootPoolEntryContainer.Builder<?>)this.applyExplosionDecay(
                        pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    protected LootTable.Builder createLapisLikeOreDrops(Block pBlock, Item item) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock,
                (LootPoolEntryContainer.Builder<?>)this.applyExplosionDecay(
                        pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    protected LootTable.Builder createRedstoneLikeOreDrops(Block pBlock, Item item) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock,
                (LootPoolEntryContainer.Builder<?>)this.applyExplosionDecay(
                        pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 5.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> knownBlocks = new ArrayList<>();
        knownBlocks.addAll(HorizonBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).toList());
        return knownBlocks;
    }

    public static LootTableProvider.SubProviderEntry getEntry(){
       return  new LootTableProvider.SubProviderEntry(
                HorizonBlockLootTables::new,
                LootContextParamSets.BLOCK
        );
    }

    protected LootTable.Builder createBackpackLoot(Block block) {
        if(block instanceof BackpackBlock backpackBlock){
            ItemStack backpack = backpackBlock.getBackpack(backpackBlock.getColor());
            return LootTable.lootTable()
                    .withPool(
                            this.applyExplosionCondition(
                                    block,
                                    LootPool.lootPool()
                                            .setRolls(ConstantValue.exactly(1.0F))
                                            .add(
                                                    LootItem.lootTableItem(backpack.getItem())
                                                            .apply(
                                                                    CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                                                            .include(DataComponents.CUSTOM_NAME)
                                                                            .include(DataComponents.CONTAINER)
                                                                            .include(DataComponents.LOCK)
                                                                            .include(DataComponents.CONTAINER_LOOT)
                                                            )
                                            )
                            )
                    );
        } else{
            return LootTable.lootTable()
                    .withPool(
                            this.applyExplosionCondition(
                                    block,
                                    LootPool.lootPool()
                                            .setRolls(ConstantValue.exactly(1.0F))
                                            .add(
                                                    LootItem.lootTableItem(block)
                                                            .apply(
                                                                    CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                                                            .include(DataComponents.CUSTOM_NAME)
                                                                            .include(DataComponents.CONTAINER)
                                                                            .include(DataComponents.LOCK)
                                                                            .include(DataComponents.CONTAINER_LOOT)
                                                            )
                                            )
                            )
                    );
        }
    }
    protected void add(Block block, Function<Block, LootTable.Builder> factory) {
        this.add(block, factory.apply(block));
    }
}
