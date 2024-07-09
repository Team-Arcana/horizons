package net.teamarcana.horizons.client.renderer;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.teamarcana.horizons.init.HorizonModelLayers;

public class BackpackModel extends HumanoidModel{
	private final ModelPart backpack;
	private final ModelPart top;

	//Minecraft.getInstance().getEntityModels().bakeLayer(HorizonModelLayers.BACKPACK)

	public static final BackpackModel INSTANCE =  new BackpackModel(createBackpackLayer(CubeDeformation.NONE).bakeRoot());

	public BackpackModel(ModelPart root) {
        super(root);

		this.backpack = root.getChild("body").getChild("backpack");
		this.top = root.getChild("body").getChild("backpack").getChild("backpack_top");
	}

	public static LayerDefinition createBackpackLayer(CubeDeformation deformation) {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0.0F);
		PartDefinition root = meshdefinition.getRoot();

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, CubeDeformation.NONE),
				PartPose.offset(0.0F, 8, 0.0F));

		PartDefinition backpack = body.addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(24, 32).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition pack_bottom = backpack.addOrReplaceChild("backpack_bottom", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -2.5F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 2.5F, 4.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition pack_top = backpack.addOrReplaceChild("backpack_top", CubeListBuilder.create().texOffs(0, 63).addBox(-1.0F, -5.3F, 2.5F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition pack_top_hanger = pack_top.addOrReplaceChild("backpack_top_hanger", CubeListBuilder.create().texOffs(0, 42).addBox(-4.0F, -3.5F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, -0.5F, 4.0F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.backpack);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int i) {
		backpack.render(poseStack, vertexConsumer, packedLight, packedOverlay, i);
	}
}