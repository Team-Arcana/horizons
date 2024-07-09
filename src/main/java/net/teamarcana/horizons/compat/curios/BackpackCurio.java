package net.teamarcana.horizons.compat.curios;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.item.BackpackItem;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class BackpackCurio implements ICurio {
    protected ItemStack stack;

    public BackpackCurio(ItemStack stack){
        this.stack = stack;
    }

    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public boolean canEquip(SlotContext slotContext) {
        return true;
    }

    @Override
    public void curioTick(SlotContext slotContext) {
        // ticking logic here
        if(slotContext.entity() instanceof Player player){
            if(stack.getItem() instanceof BackpackItem backpackItem){
                if(Horizons.OPEN_BACKPACK.isDown()){
                    backpackItem.openMenu(player, player.level(), stack);
                }
            }
        }
    }

    @NotNull
    @Override
    public SoundInfo getEquipSound(SlotContext slotContext) {
        return new SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER.value(), 1.0f, 1.0f);
    }
}
