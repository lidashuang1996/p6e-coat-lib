package club.p6e.coat.common.verifiable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Verifiable {

    private static final Map<Class<? extends Annotation>, VerifiableAchieveInterface> ACHIEVES = new Hashtable<>();

    static {
        ACHIEVES.put(VerifiableBetween.class, new VerifiableBetweenAchieve());
    }

    public static boolean execute(Object data) {
        if (data == null) {
            return false;
        } else {
            for (final Field field : data.getClass().getDeclaredFields()) {
                if (!execute(field, data)) {
                    return false;
                }
            }
            return true;
        }
    }

    private static boolean execute(Field field, Object data) {
        if (field == null) {
            return false;
        } else {
            for (final Class<? extends Annotation> key : ACHIEVES.keySet()) {
                if (field.isAnnotationPresent(key)
                        && !ACHIEVES.get(key).execute(field.getAnnotation(key), field, data)) {
                    return false;
                }
            }
            return true;
        }
    }

}
