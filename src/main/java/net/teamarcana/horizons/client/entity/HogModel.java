package net.teamarcana.horizons.client.entity;// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.definitions.CamelAnimation;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.teamarcana.horizons.entity.Hog;
import net.teamarcana.horizons.init.HorizonAnimationDefinitions;

public class HogModel<T extends Entity> extends AgeableHierarchicalModel<T> {
	private final ModelPart hog;

	public HogModel(ModelPart hog) {
		super(0.5F, 24.0F);
		this.hog = hog.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot().addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));;

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -12.0F, -8.0F, 12.0F, 12.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

		PartDefinition face = body.addOrReplaceChild("face", CubeListBuilder.create().texOffs(0, 4).addBox(-8.0F, -3.0F, -3.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(-2.0F, -3.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 8).addBox(-10.0F, -3.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 0.0F, -7.0F));

		PartDefinition snout = face.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -2.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -3.0F, -1.0F));

		PartDefinition ear_Right = body.addOrReplaceChild("ear_Right", CubeListBuilder.create().texOffs(32, 28).addBox(0.0F, 0.0F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -10.0F, -4.0F));

		PartDefinition ear_Left = body.addOrReplaceChild("ear_Left", CubeListBuilder.create().texOffs(32, 33).addBox(-5.0F, 0.0F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -10.0F, -4.0F));

		PartDefinition legs = root.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_Leg_1 = legs.addOrReplaceChild("right_Leg_1", CubeListBuilder.create().texOffs(0, 28).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -6.0F, -4.0F));

		PartDefinition right_Leg_2 = legs.addOrReplaceChild("right_Leg_2", CubeListBuilder.create().texOffs(0, 38).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -6.0F, 7.0F));

		PartDefinition left_Leg_1 = legs.addOrReplaceChild("left_Leg_1", CubeListBuilder.create().texOffs(16, 28).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -6.0F, -4.0F));

		PartDefinition left_Leg_2 = legs.addOrReplaceChild("left_Leg_2", CubeListBuilder.create().texOffs(16, 38).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -6.0F, 7.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(HorizonAnimationDefinitions.HOG_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
		this.animate(((Hog) entity).sitAnimationState, HorizonAnimationDefinitions.HOG_LAY, ageInTicks, 1.0F);
		this.animate(((Hog) entity).idleAnimationState, HorizonAnimationDefinitions.HOG_IDLE, ageInTicks, 1.0F);
		this.animate(((Hog) entity).runAnimationState, HorizonAnimationDefinitions.HOG_DASH, ageInTicks, 1.0F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		hog.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public ModelPart root() {
		return this.hog;
	}
}