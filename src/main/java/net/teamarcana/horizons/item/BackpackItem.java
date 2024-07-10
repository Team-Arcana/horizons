package net.teamarcana.horizons.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.client.renderer.BackpackModel;
import net.teamarcana.horizons.client.screen.BackpackMenu;
import net.teamarcana.horizons.init.HorizonItems;
import net.teamarcana.horizons.init.HorizonModelLayers;
import net.teamarcana.horizons.inventory.BackpackInventory;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class BackpackItem extends ArmorItem{
    protected DyeColor color;
    protected final Block block;
    private BackpackModel BACKPACK;

    public BackpackItem(Block block, @Nullable DyeColor color, Properties properties) {
        super(ArmorMaterials.LEATHER, Type.CHESTPLATE, properties.durability(-1).stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).attributes(new ItemAttributeModifiers(List.of(), false)));
        this.color = color;
        this.block = block;
    }

    public DyeColor getColor() { return color; }
    public Block getBlock() { return block; }
    public BackpackInventory getInventory(ItemStack item){ return new BackpackInventory(item); }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            protected boolean init = false;

            protected void init(){
                init = true;
                BACKPACK = new BackpackModel(Minecraft.getInstance().getEntityModels().bakeLayer(HorizonModelLayers.BACKPACK));
            }

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack item, EquipmentSlot slot, HumanoidModel<?> original) {
                if(!init){ init(); }
                return BACKPACK != null ? BACKPACK : original;
            }
        });
    }
    public BackpackModel getBackpack(){ return BACKPACK; }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack item, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return color != null ? ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "textures/item/backpack_equipped_"+color.getName()+".png") : ResourceLocation.fromNamespaceAndPath(Horizons.MOD_ID, "textures/item/backpack_equipped.png");
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getClickedFace() != Direction.UP || !context.getLevel().getBlockState(context.getClickedPos()).isCollisionShapeFullBlock(context.getLevel(), context.getClickedPos())) {
            return super.useOn(context);
        }
        InteractionResult resultPlace = this.place(new BlockPlaceContext(context), false);
        if (!resultPlace.consumesAction()) {
            InteractionResult resultUse = this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
            return resultUse == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : resultUse;
        } else {
            return resultPlace;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        boolean isShiftPressed = player.isShiftKeyDown();
        if(!isShiftPressed){
            openMenu(player, level, item);
        }
        return InteractionResultHolder.sidedSuccess(item, level.isClientSide);
    }

    protected boolean placeBlock(BlockPlaceContext context, BlockState state, Boolean fromSlot) {
        return context.getLevel().setBlock(context.getClickedPos(), state, fromSlot ? 3 : 11);
    }

    public InteractionResult place(BlockPlaceContext pContext, boolean fromSlot) {
        if (!this.getBlock().isEnabled(pContext.getLevel().enabledFeatures())) {
            return InteractionResult.FAIL;
        } else if (!pContext.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext blockplacecontext = this.updatePlacementContext(pContext);
            if (blockplacecontext == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState blockstate = this.getPlacementState(blockplacecontext);
                if (blockstate == null) {
                    return InteractionResult.FAIL;
                } else if (!this.placeBlock(blockplacecontext, blockstate, fromSlot)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos blockpos = blockplacecontext.getClickedPos();
                    Level level = blockplacecontext.getLevel();
                    Player player = blockplacecontext.getPlayer();
                    ItemStack itemstack = blockplacecontext.getItemInHand();
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    if (blockstate1.is(blockstate.getBlock())) {
                        blockstate1 = this.updateBlockStateFromTag(blockpos, level, itemstack, blockstate1);
                        this.updateCustomBlockEntityTag(blockpos, level, player, itemstack, blockstate1);
                        updateBlockEntityComponents(level, blockpos, itemstack);
                        blockstate1.getBlock().setPlacedBy(level, blockpos, blockstate1, player, itemstack);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                        }
                    }

                    SoundType soundtype = blockstate1.getSoundType(level, blockpos, pContext.getPlayer());
                    level.playSound(
                            player,
                            blockpos,
                            this.getPlaceSound(blockstate1, level, blockpos, pContext.getPlayer()),
                            SoundSource.BLOCKS,
                            (soundtype.getVolume() + 1.0F) / 2.0F,
                            soundtype.getPitch() * 0.8F
                    );
                    level.gameEvent(GameEvent.BLOCK_PLACE, blockpos, GameEvent.Context.of(player, blockstate1));
                    itemstack.consume(1, player);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
    }

    @Nullable
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext pContext) {
        return pContext;
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockstate = this.getBlock().getStateForPlacement(context);
        return blockstate != null && this.canPlace(context, blockstate) ? blockstate : null;
    }
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        Player player = pContext.getPlayer();
        CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        return (!this.mustSurvive() || pState.canSurvive(pContext.getLevel(), pContext.getClickedPos()))
                && pContext.getLevel().isUnobstructed(pState, pContext.getClickedPos(), collisioncontext);
    }
    protected boolean mustSurvive() {
        return true;
    }

    private BlockState updateBlockStateFromTag(BlockPos pPos, Level pLevel, ItemStack item, BlockState pState) {
        BlockItemStateProperties blockitemstateproperties = item.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
        if (blockitemstateproperties.isEmpty()) {
            return pState;
        } else {
            BlockState blockstate = blockitemstateproperties.apply(pState);
            if (blockstate != pState) {
                pLevel.setBlock(pPos, blockstate, 2);
            }

            return blockstate;
        }
    }

    protected boolean updateCustomBlockEntityTag(BlockPos pPos, Level pLevel, @javax.annotation.Nullable Player pPlayer, ItemStack pStack, BlockState pState) {
        return updateCustomBlockEntityTag(pLevel, pPlayer, pPos, pStack);
    }
    public static boolean updateCustomBlockEntityTag(Level pLevel, @javax.annotation.Nullable Player pPlayer, BlockPos pPos, ItemStack pStack) {
        MinecraftServer minecraftserver = pLevel.getServer();
        if (minecraftserver == null) {
            return false;
        } else {
            CustomData customdata = pStack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
            if (!customdata.isEmpty()) {
                BlockEntity blockentity = pLevel.getBlockEntity(pPos);
                if (blockentity != null) {
                    if (pLevel.isClientSide || !blockentity.onlyOpCanSetNbt() || pPlayer != null && pPlayer.canUseGameMasterBlocks()) {
                        return customdata.loadInto(blockentity, pLevel.registryAccess());
                    }

                    return false;
                }
            }

            return false;
        }
    }
    private static void updateBlockEntityComponents(Level pLevel, BlockPos pPoa, ItemStack pStack) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPoa);
        if (blockentity != null) {
            blockentity.applyComponentsFromItemStack(pStack);
            blockentity.setChanged();
        }
    }
    protected SoundEvent getPlaceSound(BlockState pState, Level world, BlockPos pos, Player entity) {
        return pState.getSoundType(world, pos, entity).getPlaceSound();
    }


    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player, ItemStack item) {
        return new BackpackMenu(containerId, inventory, getInventory(item));
    }

    public SimpleContainer getContainerInventory(@Nullable ItemStack item){
        NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
        ItemStackHandler itemHandler = new ItemStackHandler(items);
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        if(item != null){
            ItemContainerContents container = item.getComponents().get(DataComponents.CONTAINER);
            container.copyInto(items);
            for(int i = 0; i < itemHandler.getSlots(); i++){
                inventory.setItem(i, itemHandler.getStackInSlot(i));
            }
        }
        return inventory;
    }

    public static Item getItemByColor(@Nullable DyeColor color) {
        if (color == null) {
            return HorizonItems.BACKPACK.get();
        } else {
            return switch (color) {
                case WHITE -> HorizonItems.WHITE_BACKPACK.get();
                case ORANGE -> HorizonItems.ORANGE_BACKPACK.get();
                case MAGENTA -> HorizonItems.MAGENTA_BACKPACK.get();
                case LIGHT_BLUE -> HorizonItems.LIGHT_BLUE_BACKPACK.get();
                case YELLOW -> HorizonItems.YELLOW_BACKPACK.get();
                case LIME -> HorizonItems.LIME_BACKPACK.get();
                case PINK -> HorizonItems.PINK_BACKPACK.get();
                case GRAY -> HorizonItems.GRAY_BACKPACK.get();
                case LIGHT_GRAY -> HorizonItems.LIGHT_GRAY_BACKPACK.get();
                case CYAN -> HorizonItems.CYAN_BACKPACK.get();
                case BLUE -> HorizonItems.BLUE_BACKPACK.get();
                case BROWN -> HorizonItems.BROWN_BACKPACK.get();
                case GREEN -> HorizonItems.GREEN_BACKPACK.get();
                case RED -> HorizonItems.RED_BACKPACK.get();
                case BLACK -> HorizonItems.BLACK_BACKPACK.get();
                case PURPLE -> HorizonItems.PURPLE_BACKPACK.get();
            };
        }
    }

    @Override
    public boolean canFitInsideContainerItems() {
        NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
        ItemContainerContents container = this.components().get(DataComponents.CONTAINER);
        container.copyInto(items);
        return !(container.stream().toList().isEmpty()) && super.canFitInsideContainerItems();
    }

    @Override
    public void inventoryTick(ItemStack item, Level level, Entity entity, int slot, boolean isSelected) {
        if(entity instanceof Player player && player.getItemBySlot(EquipmentSlot.CHEST) == item){
            if(Horizons.OPEN_BACKPACK.isDown()){ openMenu(player, level, item); }
        }
        super.inventoryTick(item, level, entity, slot, isSelected);
    }

    public void openMenu(Player player, Level level, ItemStack item){
        if(!level.isClientSide()){
            player.level().playSound(null, player.getOnPos(), SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.BLOCKS);
            player.openMenu(new SimpleMenuProvider((id, inventory, p)-> createMenu(id, inventory, p, item), item.getHoverName()));
        }
    }

}
