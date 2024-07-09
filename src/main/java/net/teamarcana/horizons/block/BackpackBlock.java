package net.teamarcana.horizons.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.teamarcana.horizons.block.blockEntity.BackpackBlockEntity;
import net.teamarcana.horizons.init.HorizonBlocks;
import net.teamarcana.horizons.init.HorizonItems;
import net.teamarcana.horizons.item.BackpackItem;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BackpackBlock extends BaseEntityBlock {
    public static final VoxelShape shapeNorth = Block.box(4, 0, 4, 12, 10, 8);
    public static final VoxelShape shapeEast = Block.box(8, 0, 4, 12, 10, 12);
    public static final VoxelShape shapeSouth = Block.box(4, 0, 8, 12, 10, 12);
    public static final VoxelShape shapeWest = Block.box(4, 0, 4, 8, 10, 12);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final Component BACKPACK_TRANSLATION = Component.translatable("container.horizons.backpack");
    private static final Component UNKNOWN_CONTENTS = Component.translatable("container.horizons.backpack.unknownContents");
    protected DyeColor color;
    protected BackpackItem item;

    public static final MapCodec<BackpackBlock> CODEC = RecordCodecBuilder.mapCodec(
            blockInstance -> blockInstance.group(DyeColor.CODEC.optionalFieldOf("color").forGetter(backpackBlock -> Optional.ofNullable(backpackBlock.color)), propertiesCodec())
                    .apply(blockInstance, (dyeColor, properties) -> new BackpackBlock(dyeColor.orElse(null), properties))
    );

    public BackpackBlock(@Nullable DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BackpackBlockEntity(color, pos, state);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if(getDirectionFacing(state) == Direction.NORTH ){
            return shapeNorth;
        }
        if(getDirectionFacing(state) == Direction.EAST){
            return shapeEast;
        }
        if(getDirectionFacing(state) == Direction.SOUTH ){
            return shapeSouth;
        }
        if(getDirectionFacing(state) == Direction.WEST){
            return shapeWest;
        }
        return shapeNorth;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) { return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()); }

    public Direction getDirectionFacing(BlockState state){
        return state.getValue(FACING);
    }

    @Nullable
    public static DyeColor getColorFromItem(Item item) {
        return getColorFromBlock(Block.byItem(item));
    }

    @Nullable
    public static DyeColor getColorFromBlock(Block block) {
        return block instanceof BackpackBlock ? ((BackpackBlock)block).getColor() : null;
    }
    public DyeColor getColor() { return color; }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return InteractionResult.CONSUME;
        } else if (level.getBlockEntity(pos) instanceof BackpackBlockEntity backpackBlockEntity) {
            player.openMenu(backpackBlockEntity);
            level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.BLOCKS);
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) { return super.rotate(state, level, pos, direction); }
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) { return super.mirror(pState, pMirror); }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) { pBuilder.add(new Property[]{FACING}); }

    /*
    @Override
    public void appendHoverText(ItemStack item, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(item, context, components, tooltipFlag);
        if (item.has(DataComponents.CONTAINER_LOOT)) {
            components.add(UNKNOWN_CONTENTS);
        }

        int i = 0;
        int j = 0;

        for (ItemStack itemstack : item.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).nonEmptyItems()) {
            j++;
            if (i <= 4) {
                i++;
                components.add(Component.translatable("container.shulkerBox.itemCount", itemstack.getHoverName(), itemstack.getCount()));
            }
        }

        if (j - i > 0) {
            components.add(Component.translatable("container.shulkerBox.more", j - i).withStyle(ChatFormatting.ITALIC));
        }
    }
     */

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        BackpackBlockEntity blockEntity = (BackpackBlockEntity) level.getBlockEntity(pos);
        ItemStack backpack = getBackpack(blockEntity.getColor());
        backpack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(blockEntity.getItems()));
        backpack.set(DataComponents.CUSTOM_NAME, blockEntity.getCustomName());
        return backpack;
    }
    public ItemStack getBackpack(DyeColor color){
        ItemStack item = HorizonItems.getBackpackFromColor(color);
        return item;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof BackpackBlockEntity backpackBlockEntity){
            if (!level.isClientSide && player.isCreative() && !backpackBlockEntity.isEmpty()) {
                ItemStack backpack = getBackpack(backpackBlockEntity.getColor());
                backpack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(backpackBlockEntity.getItems()));
                backpack.set(DataComponents.CUSTOM_NAME, backpackBlockEntity.getCustomName());
                ItemEntity itementity = new ItemEntity(
                        level, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, backpack
                );
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            } else {
                backpackBlockEntity.unpackLootTable(player);
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    public static Block getBlockByColor(@Nullable DyeColor color) {
        if (color == null) {
            return HorizonBlocks.BACKPACK.get();
        } else {
            return switch (color) {
                case WHITE -> HorizonBlocks.WHITE_BACKPACK.get();
                case ORANGE -> HorizonBlocks.ORANGE_BACKPACK.get();
                case MAGENTA -> HorizonBlocks.MAGENTA_BACKPACK.get();
                case LIGHT_BLUE -> HorizonBlocks.LIGHT_BLUE_BACKPACK.get();
                case YELLOW -> HorizonBlocks.YELLOW_BACKPACK.get();
                case LIME -> HorizonBlocks.LIME_BACKPACK.get();
                case PINK -> HorizonBlocks.PINK_BACKPACK.get();
                case GRAY -> HorizonBlocks.GRAY_BACKPACK.get();
                case LIGHT_GRAY -> HorizonBlocks.LIGHT_GRAY_BACKPACK.get();
                case CYAN -> HorizonBlocks.CYAN_BACKPACK.get();
                case BLUE -> HorizonBlocks.BLUE_BACKPACK.get();
                case BROWN -> HorizonBlocks.BROWN_BACKPACK.get();
                case GREEN -> HorizonBlocks.GREEN_BACKPACK.get();
                case RED -> HorizonBlocks.RED_BACKPACK.get();
                case BLACK -> HorizonBlocks.BLACK_BACKPACK.get();
                case PURPLE -> HorizonBlocks.PURPLE_BACKPACK.get();
            };
        }
    }
}
