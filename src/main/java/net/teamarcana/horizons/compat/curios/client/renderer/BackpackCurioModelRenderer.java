package net.teamarcana.horizons.compat.curios.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.teamarcana.horizons.client.renderer.BackpackModel;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class BackpackCurioModelRenderer implements ICurioRenderer {
    private final BackpackModel model;
    public BackpackCurioModelRenderer(ModelPart root){
        this.model = new BackpackModel(root);
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack item, SlotContext context, PoseStack pose, RenderLayerParent<T, M> parent, MultiBufferSource source, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        model.setupAnim(context.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.prepareMobModel(context.entity(), limbSwing, limbSwingAmount, partialTicks);
        parent.getModel().copyPropertiesTo(model);

        pose.pushPose();
        pose.mulPose(Axis.YP.rotationDegrees(180.0F));
        pose.scale(1.05F, -1.05F, -1.05F);
        int offset = (!context.entity().getItemBySlot(EquipmentSlot.CHEST).isEmpty()) ? 3 : 2;
        pose.translate(0, -0.99, offset * 0.0625 -0.4);

        if(parent.getModel() instanceof HumanoidModel<?> humanoidModel){
            humanoidModel.copyPropertiesTo(model);
            if(humanoidModel.crouching){
                pose.mulPose(Axis.XP.rotationDegrees(-30));
                pose.translate(0, -0.5, 0.2);
            }
        }

        Minecraft mc = Minecraft.getInstance();
        mc.getItemRenderer()
                .renderStatic(item, ItemDisplayContext.HEAD, light, OverlayTexture.NO_OVERLAY, pose,
                        source, mc.level, 0);

        pose.popPose();
    }
}
