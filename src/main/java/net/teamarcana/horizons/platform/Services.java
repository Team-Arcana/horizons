package net.teamarcana.horizons.platform;

import net.teamarcana.horizons.platform.services.BackpackHelper;
import org.jline.utils.Log;

import java.util.ServiceLoader;

public class Services {
    public static final BackpackHelper BACKPACK = load(BackpackHelper.class);

    public static <T> T load(Class<T> c){
        final T loaded = ServiceLoader.load(c).findFirst().orElseThrow(()-> new NullPointerException("Failed to load service for " + c.getName()));
        Log.debug(String.format("loaded %s for %s", loaded, c));
        return  loaded;
    }
}
