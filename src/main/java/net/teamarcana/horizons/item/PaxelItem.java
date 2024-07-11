package net.teamarcana.horizons.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.teamarcana.horizons.init.HorizonTags;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public class PaxelItem extends AxeItem {
    // Paxels are pickaxes, axes, and shovels combined
    public static final Set<ItemAbility> PAXEL_ACTIONS = Collections.newSetFromMap(new IdentityHashMap<>());
    protected static final Map<Block, BlockState> FLATTENABLES;

    static {
        PAXEL_ACTIONS.addAll(ItemAbilities.DEFAULT_PICKAXE_ACTIONS);
        PAXEL_ACTIONS.addAll(ItemAbilities.DEFAULT_AXE_ACTIONS);
        PAXEL_ACTIONS.addAll(ItemAbilities.DEFAULT_SHOVEL_ACTIONS);
        FLATTENABLES = Maps.newHashMap((new ImmutableMap.Builder()).put(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.DIRT, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.PODZOL, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.COARSE_DIRT, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.MYCELIUM, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.ROOTED_DIRT, Blocks.DIRT_PATH.defaultBlockState()).build());
    }

    public PaxelItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, properties.durability(tier.getUses() * 3).attributes(AxeItem.createAttributes(tier, attackDamage, attackSpeed)));
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
        return PAXEL_ACTIONS.contains(ability);
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        return pState.is(HorizonTags.Blocks.PAXEL_MINING) ? getTier().getSpeed() : 1;
    }


    @Override
    public boolean isCorrectToolForDrops(ItemStack item, BlockState state) {
        Tool tool = item.get(DataComponents.TOOL);
        return tool != null && tool.isCorrectForDrops(state);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (super.useOn(pContext) != InteractionResult.PASS) {
            return super.useOn(pContext);
        }

        // Basically, if it doesn't strip wood, it flattens

        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (pContext.getClickedFace() == Direction.DOWN) {
            return InteractionResult.PASS;
        } else {
            Player player = pContext.getPlayer();
            BlockState blockstate1 = blockstate.getToolModifiedState(pContext, ItemAbilities.SHOVEL_FLATTEN, false);
            BlockState blockstate2 = null;
            if (blockstate1 != null && level.isEmptyBlock(blockpos.above())) {
                level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                blockstate2 = blockstate1;
            } else if (blockstate.getBlock() instanceof CampfireBlock && (Boolean) blockstate.getValue(CampfireBlock.LIT)) {
                if (!level.isClientSide()) {
                    level.levelEvent((Player) null, 1009, blockpos, 0);
                }

                CampfireBlock.dowse(pContext.getPlayer(), level, blockpos, blockstate);
                blockstate2 = (BlockState) blockstate.setValue(CampfireBlock.LIT, false);
            }

            if (blockstate2 != null) {
                if (!level.isClientSide) {
                    level.setBlock(blockpos, blockstate2, 11);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, blockstate2));
                    if (player != null) {
                        pContext.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(pContext.getHand()));
                    }
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }
}
