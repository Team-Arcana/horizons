package net.teamarcana.horizons.client.renderer;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class BackpackModel extends HumanoidModel<LivingEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart body;

	//public static BackpackModel INSTANCE = new BackpackModel<>(BackpackModel.createArmorLayer().bakeRoot());


	public BackpackModel(ModelPart root) {
        super(root);
		this.body = root.getChild("body");
	}

	public static LayerDefinition createArmorLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition root = meshdefinition.getRoot();

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 0).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.2F))
				.texOffs(0, 31).addBox(-1.0F, -5.3F, 2.5F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition backpack_top = body.addOrReplaceChild("backpack_top", CubeListBuilder.create().texOffs(0, 10).addBox(-4.0F, -3.5F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.4F))
				.texOffs(0, 0).addBox(-4.0F, 0.5F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, -0.5F, 4.0F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 48, 32);
	}
	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}
	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int p_350308_) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		super.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, p_350308_);
	}
}