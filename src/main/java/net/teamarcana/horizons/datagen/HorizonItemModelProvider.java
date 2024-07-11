package net.teamarcana.horizons.datagen;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.compat.battlements.BattleCompatItems;
import net.teamarcana.horizons.init.HorizonItems;
import net.teamarcana.horizons.init.HorizonModelOverrides;
import net.teamarcana.horizons.item.BackpackItem;

import java.util.LinkedHashMap;
import java.util.Objects;

public class HorizonItemModelProvider extends ItemModelProvider {
    private static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static{
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public HorizonItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Horizons.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(HorizonItems.CREATIVE_ICON);

        // BACKPACKS
        backpackItem(HorizonItems.BACKPACK.get());
        backpackItem(HorizonItems.WHITE_BACKPACK.get());
        backpackItem(HorizonItems.LIGHT_GRAY_BACKPACK.get());
        backpackItem(HorizonItems.GRAY_BACKPACK.get());
        backpackItem(HorizonItems.BLACK_BACKPACK.get());
        backpackItem(HorizonItems.BROWN_BACKPACK.get());
        backpackItem(HorizonItems.RED_BACKPACK.get());
        backpackItem(HorizonItems.ORANGE_BACKPACK.get());
        backpackItem(HorizonItems.YELLOW_BACKPACK.get());
        backpackItem(HorizonItems.LIME_BACKPACK.get());
        backpackItem(HorizonItems.GREEN_BACKPACK.get());
        backpackItem(HorizonItems.CYAN_BACKPACK.get());
        backpackItem(HorizonItems.LIGHT_BLUE_BACKPACK.get());
        backpackItem(HorizonItems.BLUE_BACKPACK.get());
        backpackItem(HorizonItems.PURPLE_BACKPACK.get());
        backpackItem(HorizonItems.MAGENTA_BACKPACK.get());
        backpackItem(HorizonItems.PINK_BACKPACK.get());

        // paxels
        handheldItem(HorizonItems.WOODEN_PAXEL.get());
        handheldItem(HorizonItems.STONE_PAXEL.get());
        handheldItem(HorizonItems.GOLDEN_PAXEL.get());
        handheldItem(HorizonItems.IRON_PAXEL.get());
        handheldItem(HorizonItems.DIAMOND_PAXEL.get());
        handheldItem(HorizonItems.NETHERITE_PAXEL.get());
        handheldItem(BattleCompatItems.STEEL_PAXEL.get());
        handheldItem(BattleCompatItems.ENDERIUM_PAXEL.get());
    }

    // METHODS
    // Shoutout to El_Redstoniano for making this
    // [Obtained from a tutorial by kaupenjoe]
    private void trimmedArmorItem(DeferredItem itemRegistryObject) {
        final String MOD_ID = Horizons.MOD_ID; // Change this to your mod id

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.fromNamespaceAndPath(MOD_ID, armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.fromNamespaceAndPath(MOD_ID, currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }

    private ItemModelBuilder simpleItem(DeferredItem item){
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "item/"+item.getId().getPath()));
    }
    private ItemModelBuilder simpleBlockItem(DeferredBlock item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID,"item/" + item.getId().getPath()));
    }

    public void evenSimplerBlockItem(Block block) {
        this.withExistingParent(Horizons.MOD_ID + ":" + Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)),
                modLoc("block/" + Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block))));
    }

    public ItemModelBuilder handheldItem(Item item) {
        return handheldItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)));
    }
    public ItemModelBuilder handheldItem(ResourceLocation item) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()));
    }

    public ItemModelBuilder handheldItemWithExistingModel(Item item, String existingModelPath){
        return handheldItemWithExistingModel(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)), existingModelPath);
    }
    public ItemModelBuilder handheldItemWithExistingModel(ResourceLocation item, String existingModelPath){
        ItemModelBuilder heldModel = heldItemWithExistingModel(item, existingModelPath);
        ItemModelBuilder inventoryModel = inventoryItem(item);

        return getBuilder(String.valueOf(item)).customLoader(SeparateTransformsModelBuilder::begin)
                .base(heldModel)
                .perspective(ItemDisplayContext.GUI, inventoryModel)
                .perspective(ItemDisplayContext.FIXED, inventoryModel).end().guiLight(BlockModel.GuiLight.FRONT);
    }
    public ItemModelBuilder inventoryItem(ResourceLocation item) {
        return withExistingParent(item + "_inventory",
                "item/handheld")
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()));
    }
    public ItemModelBuilder heldItemWithExistingModel(ResourceLocation item, String existingModelPath) {
        return withExistingParent(item + "_held",
                existingModelPath)
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath() + "_held"));
    }
    public ItemModelBuilder backpackItem(BackpackItem item){
        return item.getColor() != null ? backpackItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)), "_" + item.getColor().getName()) : backpackItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)), "");
    }
    public ItemModelBuilder backpackItem(ResourceLocation item, String dyeColor){
        ItemModelBuilder heldModel = withExistingParent(item + "_held", "horizons:item/backpack_model").texture("layer0",
                ResourceLocation.fromNamespaceAndPath(item.getNamespace(), String.format("item/backpack_equipped%s", dyeColor)));
        ItemModelBuilder inventoryModel = withExistingParent(item + "_inventory", "item/handheld").texture("layer0",
                ResourceLocation.fromNamespaceAndPath(item.getNamespace(), String.format("item/backpack%s", dyeColor)));

        return getBuilder(String.valueOf(item)).customLoader(SeparateTransformsModelBuilder::begin)
                .base(heldModel)
                .perspective(ItemDisplayContext.GUI, inventoryModel)
                .perspective(ItemDisplayContext.FIXED, inventoryModel).end().guiLight(BlockModel.GuiLight.FRONT);
    }
}
