package club.p6e.coat.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class ApplicationProperties {

    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();

    public static Object get(String key) {
        return CACHE.get(key);
    }

    public static void unregister(String key) {
        CACHE.remove(key);
    }

    public static void register(String key, Object value) {
        CACHE.put(key, value);
    }

}
