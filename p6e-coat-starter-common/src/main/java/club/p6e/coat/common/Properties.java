package club.p6e.coat.common;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@Component(value = Properties.BEAN_NAME)
@ConfigurationProperties(prefix = "p6e.coat.common")
public class Properties implements Serializable {

    /**
     * 注入的 BEAN 的名称
     */
    public static final String BEAN_NAME = "club.p6e.coat.common.Properties";

    /**
     * 版本号
     */
    private String version = "unknown";

    /**
     * 安全
     */
    private Security security = new Security();

    /**
     * 安全
     */
    private CrossDomain crossDomain = new CrossDomain();

    /**
     * 雪花
     */
    private Map<String, Snowflake> snowflake = new HashMap<>();

    @Data
    public static class Security {

        /**
         * 是否启用
         */
        private boolean enable = false;

        /**
         * 凭证
         */
        private String[] vouchers = new String[]{};

    }

    /**
     * Cross Domain
     */
    @Data
    @Accessors(chain = true)
    public static class CrossDomain implements Serializable {
        /**
         * 是否启动
         */
        private boolean enable = false;

        /**
         * 白名单
         */
        private List<String> whiteList = List.of("*");
    }

    @Data
    public static class Snowflake {
        private Integer workerId;
        private Integer datacenterId;
    }
}
