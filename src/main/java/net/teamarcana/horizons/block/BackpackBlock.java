package net.teamarcana.horizons.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.teamarcana.horizons.item.BackpackItem;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BackpackBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(4, 0, 4, 11, 10, 7);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final Component BACKPACK_TRANSLATION = BackpackItem.BACKPACK_TRANSLATION;
    protected DyeColor color;

    public static final MapCodec<BackpackBlock> CODEC = RecordCodecBuilder.mapCodec(
            blockInstance -> blockInstance.group(DyeColor.CODEC.optionalFieldOf("color").forGetter(backpackBlock -> Optional.ofNullable(backpackBlock.color)), propertiesCodec())
                    .apply(blockInstance, (dyeColor, properties) -> new BackpackBlock(dyeColor.orElse(null), properties))
    );

    protected BackpackBlock(@Nullable DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) { return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()); }

    public Direction getDirectionFacing(BlockState state){
        return state.getValue(FACING);
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) { return super.rotate(state, level, pos, direction); }
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) { return super.mirror(pState, pMirror); }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) { pBuilder.add(new Property[]{FACING}); }
}
