package net.teamarcana.horizons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.teamarcana.horizons.Horizons;
import net.teamarcana.horizons.entity.Hog;

import java.util.function.Supplier;

public class HorizonEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Horizons.MOD_ID);

    public static final Supplier<EntityType<Hog>> HOG = ENTITY_TYPES.register("hog", () -> registerEntity(EntityType.Builder.of(Hog::new, MobCategory.CREATURE).sized(1, 1), "hog"));

    private static EntityType registerEntity(EntityType.Builder builder, String entityName) {
        return builder.build(entityName);
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
