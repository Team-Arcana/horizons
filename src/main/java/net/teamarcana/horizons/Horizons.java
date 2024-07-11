package net.teamarcana.horizons;
import com.llamalad7.mixinextras.lib.apache.commons.ArrayUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.teamarcana.horizons.compat.battlements.BattleCompatItems;
import net.teamarcana.horizons.compat.curios.BackpackCurio;
import net.teamarcana.horizons.compat.curios.client.renderer.BackpackCurioModelRenderer;
import net.teamarcana.horizons.client.renderer.BackpackModel;
import net.teamarcana.horizons.client.screen.BackpackScreen;
import net.teamarcana.horizons.init.*;
import net.teamarcana.horizons.item.BackpackItem;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurio;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Horizons.MOD_ID)
public class Horizons
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "horizons";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    protected boolean isCuriosAPIHere;
    protected static boolean isBattlementsHere;

    public static KeyMapping OPEN_BACKPACK = new KeyMapping("key.horizons.open_backpack", InputConstants.KEY_B, "key.categories.horizons");

    public Horizons(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerCurioCapabilities);
        modEventBus.addListener(this::clientSetupWithCurios);

        HorizonBlocks.register(modEventBus);
        HorizonItems.register(modEventBus);
        HorizonBlockEntityTypes.register(modEventBus);
        HorizonMenuTypes.register(modEventBus);
        HorizonRecipeSerializers.register(modEventBus);

        registerKeyBindings();

        HorizonCreativeTabs.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        if(ModList.get().isLoaded("battlements")){
            BattleCompatItems.register(modEventBus);
        }

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        isCuriosAPIHere = ModList.get().isLoaded("curios");
        isBattlementsHere = ModList.get().isLoaded("battlements");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        //LOGGER.info("HELLO from server starting");
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            //LOGGER.info("HELLO FROM CLIENT SETUP");
            //LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }

        @SubscribeEvent
        public static void menuScreens(RegisterMenuScreensEvent event){
            event.register(HorizonMenuTypes.BACKPACK_MENU.get(), BackpackScreen::new);
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(HorizonModelLayers.BACKPACK, ()->  BackpackModel.createBackpackLayer(CubeDeformation.NONE));
        }

    }

    public static void registerKeyBindings(){
        if(Minecraft.getInstance() != null){
            Minecraft.getInstance().options.keyMappings = ArrayUtils.add(Minecraft.getInstance().options.keyMappings,OPEN_BACKPACK);
        }
    }

    // for curios compat
    public void registerCurioCapabilities(final RegisterCapabilitiesEvent event) {
        if(ModList.get().isLoaded("curios")){
            for(Item item : BuiltInRegistries.ITEM){
                if(item instanceof BackpackItem){
                    event.registerItem(CuriosCapability.ITEM, (stack, context) -> new BackpackCurio(stack), item);
                }
            }
        }
    }

    public void clientSetupWithCurios(FMLClientSetupEvent event){
        if(ModList.get().isLoaded("curios")){
            for(Item item : BuiltInRegistries.ITEM){
                if(item instanceof BackpackItem){
                    CuriosRendererRegistry.register(item, () -> new BackpackCurioModelRenderer(Minecraft.getInstance().getEntityModels().bakeLayer(HorizonModelLayers.BACKPACK)));
                }
            }
        }
    }

    public boolean isCuriosAPIHere(){
        return isCuriosAPIHere;
    }
    public static boolean isBattlementsHere(){
        return isBattlementsHere;
    }
}
