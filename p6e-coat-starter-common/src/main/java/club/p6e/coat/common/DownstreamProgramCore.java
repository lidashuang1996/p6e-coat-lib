package club.p6e.coat.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class DownstreamProgramCore {

    /**
     * 初始化程序方法对象
     */
    public static Function<Object, Map<String, Object>> init = (obj) ->
            DownstreamProgramCore.DEBUG_DATA == null ? new HashMap<>() : DownstreamProgramCore.DEBUG_DATA;

    /**
     * 是否 DEBUG 模式
     */
    private static boolean IS_DEBUG = false;

    /**
     * DEBUG 模式下的数据
     */
    private static Map<String, Object> DEBUG_DATA = null;

    /**
     * 设置是否开启 DEBUG 模式
     *
     * @param status DEBUG 模式状态
     */
    public static void setDebug(boolean status) {
        IS_DEBUG = status;
    }

    /**
     * 设置 DEBUG 模式下的数据
     *
     * @param data DEBUG 模式下的数据
     */
    public static void setDebugData(Map<String, Object> data) {
        DEBUG_DATA = data;
    }

    /**
     * 获取数据
     *
     * @return 数据对象
     */
    public static Map<String, Object> getData() {
        return getData(null);
    }

    /**
     * 获取数据
     *
     * @param data 传入的初始化对象数据
     * @return 数据对象
     */
    public static Map<String, Object> getData(Object data) {
        if (IS_DEBUG) {
            return DEBUG_DATA;
        } else {
            return init.apply(data);
        }
    }

}
