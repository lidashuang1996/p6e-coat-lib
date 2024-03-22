package club.p6e.coat.common.error;

/**
 * CustomException / AuthStateException
 *
 * @author lidashuang
 * @version 1.0
 */
public class ResourceException extends CustomException {

    /**
     * 默认的代码
     */
    public static final int DEFAULT_CODE = 11900;

    /**
     * 默认的简述
     */
    private static final String DEFAULT_SKETCH = "AUTH_STATE_EXCEPTION";

    /**
     * AuthStateException
     *
     * @param sc      源 class
     * @param error   异常对象
     * @param content 描述内容
     */
    public ResourceException(Class<?> sc, String error, String content) {
        super(sc, ResourceException.class, error, DEFAULT_CODE, DEFAULT_SKETCH, content);
    }

    /**
     * AuthStateException
     *
     * @param sc        源 class
     * @param throwable 异常对象
     * @param content   描述内容
     */
    public ResourceException(Class<?> sc, Throwable throwable, String content) {
        super(sc, ResourceException.class, throwable, DEFAULT_CODE, DEFAULT_SKETCH, content);
    }

    /**
     * AuthStateException
     *
     * @param sc      源 class
     * @param error   异常内容
     * @param code    代码
     * @param sketch  简述
     * @param content 描述内容
     */
    public ResourceException(Class<?> sc, String error, int code, String sketch, String content) {
        super(sc, ResourceException.class, error, code, sketch, content);
    }

    /**
     * AuthStateException
     *
     * @param sc        源 class
     * @param throwable 异常对象
     * @param code      代码
     * @param sketch    简述
     * @param content   描述内容
     */
    public ResourceException(Class<?> sc, Throwable throwable, int code, String sketch, String content) {
        super(sc, ResourceException.class, throwable, code, sketch, content);
    }
}
