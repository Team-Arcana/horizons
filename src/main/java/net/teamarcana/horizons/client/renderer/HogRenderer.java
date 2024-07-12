package net.teamarcana.horizons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.client.entity.HogModel;
import net.teamarcana.horizons.entity.Hog;
import net.teamarcana.horizons.init.HorizonModelLayers;

public class HogRenderer extends MobRenderer<Hog, HogModel<Hog>> {
    private static final ResourceLocation HOG_TEXTURE = ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "textures/entity/hog/hog.png");
    private static final ResourceLocation HOG_SADDLE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "textures/entity/hog/hog_saddle.png");

    public HogRenderer(EntityRendererProvider.Context context) {
        super(context, new HogModel<>(context.bakeLayer(HorizonModelLayers.HOG)), 0.8f);
    }

    @Override
    public ResourceLocation getTextureLocation(Hog hog) {
        return HOG_TEXTURE;
    }

    @Override
    public void render(Hog hog, float entityYaw, float partialTick, PoseStack pose, MultiBufferSource source, int pakedLight) {
        if(hog.isBaby()){
            pose.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(hog, entityYaw, partialTick, pose, source, pakedLight);
    }
}
