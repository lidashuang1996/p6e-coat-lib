package club.p6e.coat.common.verifiable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class VerifiableCustomAchieve implements VerifiableAchieveInterface {

    @Override
    public boolean execute(Annotation annotation, Field field, Object data) {
        if (annotation instanceof VerifiableCustom custom) {
            try {
                return execute(field.get(data), custom.value());
            } catch (Exception ignored) {
                // ignored
            }
        }
        return false;
    }

    public boolean execute(Object data, String content) {
        return false;
    }

}
