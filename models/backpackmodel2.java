// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class backpackmodel2<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "backpackmodel2"), "main");
	private final ModelPart backpack;
	private final ModelPart top;

	public backpackmodel2(ModelPart root) {
		this.backpack = root.getChild("backpack");
		this.top = root.getChild("top");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition backpack = partdefinition.addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(24, 32).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition pack_r1 = backpack.addOrReplaceChild("pack_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -2.5F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 2.5F, 4.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition top = backpack.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 63).addBox(-1.0F, -5.3F, 2.5F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition top_r1 = top.addOrReplaceChild("top_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-4.0F, -3.5F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, -0.5F, 4.0F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		backpack.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}