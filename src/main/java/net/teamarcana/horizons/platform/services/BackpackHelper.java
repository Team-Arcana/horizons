package net.teamarcana.horizons.platform.services;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.teamarcana.horizons.item.BackpackItem;

public interface BackpackHelper {

    ItemStack getBackpack(Player player);
    boolean setBackpack(Player player, ItemStack stack);
    boolean isVisible(Player player);
    void openBackpack(ServerPlayer openingPlayer, Container inventory, int cols, int rows, boolean owner, Component title);
    BackpackItem createBackpackItem(Item.Properties properties);

}
