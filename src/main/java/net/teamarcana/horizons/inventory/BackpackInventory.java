package net.teamarcana.horizons.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.teamarcana.horizons.init.HorizonTags;
import net.teamarcana.horizons.item.BackpackItem;

public class BackpackInventory implements Container {
    protected ItemStack backpack;
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);

    public BackpackInventory(ItemStack item){
        this.backpack = item;
        ItemContainerContents contents = item.getComponents().get(DataComponents.CONTAINER);
        contents.copyInto(items);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack item = items.get(slot);
        ItemStack newItem = item.split(amount);
        if(item.isEmpty()){
            items.set(slot, ItemStack.EMPTY);
        }
        setChanged();
        return newItem;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack item = items.get(slot);
        items.set(slot, ItemStack.EMPTY);
        return item;
    }

    public ItemStack addItem(ItemStack item){
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).isEmpty()){
                items.set(i, item);
                setChanged();
                return ItemStack.EMPTY;
            } else{
                ItemStack itemStack = items.get(i);
                if(ItemStack.isSameItemSameComponents(item, itemStack)){
                    int j = 64 - itemStack.getCount();
                    if(item.getCount() <= j){
                        ItemStack item2 = itemStack.copy();
                        item2.grow(item.getCount());
                        items.set(i, item2);
                        setChanged();
                        return ItemStack.EMPTY;
                    } else{
                        itemStack.grow(j);
                        item.shrink(j);
                        setChanged();
                    }
                }
            }
        }
        return item;
    }

    @Override
    public void setItem(int slot, ItemStack item) {
        items.set(slot, item);
        setChanged();
    }

    @Override
    public void setChanged() {
        backpack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack item) {
        return !item.is(HorizonTags.Items.BACKPACK_BLACKLIST) || !(item.getItem() instanceof BackpackItem);
    }

    @Override
    public void clearContent() {
        items.clear();
        setChanged();
    }

    public NonNullList<ItemStack> getItems() { return items; }
}
