package net.teamarcana.horizons.compat.curios.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.client.renderer.BackpackModel;
import net.teamarcana.horizons.item.BackpackItem;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class BackpackCurioModelRenderer implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack item, SlotContext context, PoseStack pose, RenderLayerParent<T, M> parent, MultiBufferSource source, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(!item.isEmpty()){
            ResourceLocation textureLocation =  ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "textures/item/backpack_equipped.png");
            if(item.getItem() instanceof BackpackItem backpackItem){
                DyeColor color = backpackItem.getColor();
                textureLocation = color != null ? ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "textures/item/backpack_equipped_"+color.getName()+".png") : ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "textures/item/backpack_equipped.png");

                BackpackModel model = backpackItem.getBackpack();
                Entity entity = context.entity();
                
                pose.pushPose();
                VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(source, RenderType.armorCutoutNoCull(textureLocation), item.hasFoil());
                if(model != null && entity instanceof LivingEntity living){
                    model.setupAnim(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    model.prepareMobModel(living, limbSwing, limbSwingAmount, partialTicks);
                    model.renderToBuffer(pose, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1);
                }
                pose.popPose();
            }
        }
    }


}
