package net.teamarcana.horizons.compat.curios.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.teamarcana.horizons.client.renderer.BackpackModel;
import net.teamarcana.horizons.init.HorizonModelLayers;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class BackpackCurioModelRenderer implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack item, SlotContext context, PoseStack pose, RenderLayerParent<T, M> parent, MultiBufferSource source, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(!item.isEmpty()){
            pose.pushPose();
            if (parent.getModel() instanceof HumanoidModel<?> parentModel) {
                BackpackModel model = new BackpackModel(Minecraft.getInstance().getEntityModels().bakeLayer(HorizonModelLayers.BACKPACK));
                model.createBackpackLayer(CubeDeformation.NONE);
                pose.popPose();
            }
        }
    }
}
