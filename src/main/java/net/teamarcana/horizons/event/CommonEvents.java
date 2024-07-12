package net.teamarcana.horizons.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.teamarcana.horizons.entity.Hog;
import net.teamarcana.horizons.init.HorizonEntities;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CommonEvents {
    @SubscribeEvent
    public static void RegisterAttributes(EntityAttributeCreationEvent event){
        event.put(HorizonEntities.HOG.get(), Hog.createAttributes().build());
    }
}
