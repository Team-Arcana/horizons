package net.teamarcana.horizons.block.blockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.teamarcana.horizons.block.BackpackBlock;
import net.teamarcana.horizons.client.screen.BackpackMenu;
import net.teamarcana.horizons.init.HorizonBlockEntityTypes;
import net.teamarcana.horizons.item.BackpackItem;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class BackpackBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    public static final int COLUMNS = 9;
    public static final int ROWS = 3;
    public static final int CONTAINER_SIZE = 27;
    private static final int[] SLOTS = IntStream.range(0, 27).toArray();
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    @Nullable
    private final DyeColor color;

    public BackpackBlockEntity(@Nullable DyeColor color, BlockPos pos, BlockState state) {
        super(HorizonBlockEntityTypes.BACKPACK.get(), pos, state);
        this.color = color;
    }
    public BackpackBlockEntity(BlockPos pos, BlockState state) {
        super(HorizonBlockEntityTypes.BACKPACK.get(), pos, state);
        this.color = BackpackBlock.getColorFromBlock(state.getBlock());
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected Component getDefaultName() {
        return BackpackBlock.BACKPACK_TRANSLATION;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.loadFromTag(tag, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items, false, registries);
        }
    }

    public void loadFromTag(CompoundTag tag, HolderLookup.Provider levelRegistry) {
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag) && tag.contains("Items", 9)) {
            ContainerHelper.loadAllItems(tag, this.items, levelRegistry);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return SLOTS;
    }


    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack item, @Nullable Direction direction) {
        return !(Block.byItem(item.getItem()) instanceof BackpackBlock) && item.getItem().canFitInsideContainerItems();
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack item, Direction direction) {
        return true;
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new BackpackMenu(id, inventory, this);
    }

    public @Nullable DyeColor getColor() { return color; }
}
