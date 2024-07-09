package net.teamarcana.horizons.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.teamarcana.horizons.client.renderer.BackpackModel;
import net.teamarcana.horizons.init.HorizonModelLayers;

public class CustomArmorRenderProperties implements IClientItemExtensions {
    protected static boolean init = false;
    public static BackpackModel BACKPACK;

    public static void init(){
        init = true;
        BACKPACK =  new BackpackModel(Minecraft.getInstance().getEntityModels().bakeLayer(HorizonModelLayers.BACKPACK));
    }

    @Override
    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack item, EquipmentSlot slot, HumanoidModel<?> original) {
        if(!init){
            init();
        }
        if(item.getItem() instanceof BackpackItem){
            return BACKPACK;
        }
        return original;
    }

}
