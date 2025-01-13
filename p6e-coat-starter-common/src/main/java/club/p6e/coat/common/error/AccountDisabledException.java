package club.p6e.coat.common.error;

/**
 * 自定义异常
 * 账号禁用异常
 *
 * @author lidashuang
 * @version 1.0
 */
public class AccountDisabledException extends CustomException {

    /**
     * 默认的代码
     */
    public static final int DEFAULT_CODE = 102000;

    /**
     * 默认的简述
     */
    private static final String DEFAULT_SKETCH = "ACCOUNT_DISABLED_EXCEPTION";

    /**
     * 类型不匹配异常
     *
     * @param sc      源 class
     * @param error   异常对象
     * @param content 描述内容
     */
    public AccountDisabledException(Class<?> sc, String error, String content) {
        super(sc, AccountDisabledException.class, error, DEFAULT_CODE, DEFAULT_SKETCH, content);
    }

    /**
     * 类型不匹配异常
     *
     * @param sc        源 class
     * @param throwable 异常对象
     * @param content   描述内容
     */
    public AccountDisabledException(Class<?> sc, Throwable throwable, String content) {
        super(sc, AccountDisabledException.class, throwable, DEFAULT_CODE, DEFAULT_SKETCH, content);
    }

    /**
     * 类型不匹配异常
     *
     * @param sc      源 class
     * @param error   异常内容
     * @param code    代码
     * @param sketch  简述
     * @param content 描述内容
     */
    public AccountDisabledException(Class<?> sc, String error, int code, String sketch, String content) {
        super(sc, AccountDisabledException.class, error, code, sketch, content);
    }

    /**
     * 类型不匹配异常
     *
     * @param sc        源 class
     * @param throwable 异常对象
     * @param code      代码
     * @param sketch    简述
     * @param content   描述内容
     */
    public AccountDisabledException(Class<?> sc, Throwable throwable, int code, String sketch, String content) {
        super(sc, AccountDisabledException.class, throwable, code, sketch, content);
    }
}
