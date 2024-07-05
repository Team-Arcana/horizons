package net.teamarcana.horizons.item;

import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.client.screen.BackpackMenu;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

import static net.minecraft.world.level.block.state.BlockBehaviour.propertiesCodec;

public class BackpackItem extends ArmorItem implements MenuProvider {
    private static final Map<DyeColor, DyeItem> ITEM_BY_COLOR = Maps.newEnumMap(DyeColor.class);
    protected DyeColor dyeColor;
    protected boolean isDyed = false;

    public static final Component BACKPACK_TRANSLATION = Component.translatable("container.horizons.backpack");
    protected static final String EQUIP_TEXTURE = Horizons.MOD_ID + ":textures/item/backpack_equipped.png";


    public BackpackItem(DyeColor dyeColor, Properties pProperties) {
        super(ArmorMaterials.LEATHER, Type.CHESTPLATE, pProperties.durability(-1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
        this.dyeColor = dyeColor;
    }

    @Override
    public Component getDisplayName() {
        return BACKPACK_TRANSLATION;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new BackpackMenu(containerId, inventory);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> result = super.use(level, player, hand);
        if (player instanceof ServerPlayer serverPlayer) {

        }
        return result;
    }
}
