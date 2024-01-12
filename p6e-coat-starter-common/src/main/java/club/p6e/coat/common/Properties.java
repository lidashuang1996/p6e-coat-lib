package club.p6e.coat.common;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "p6e.coat.common")
@Component(value = "club.p6e.coat.common.Properties")
public class Properties implements Serializable {

    /**
     * 版本号
     */
    private volatile String version = "unknown";

    /**
     * 安全
     */
    private volatile Security security = new Security();

    /**
     * 安全
     */
    private volatile CrossDomain crossDomain = new CrossDomain();

    /**
     * 雪花
     */
    private volatile Map<String, Snowflake> snowflake = new HashMap<>();

    @Data
    public static class Security implements Serializable {

        /**
         * 是否启用
         */
        private boolean enable = false;

        /**
         * 凭证
         */
        private List<String> vouchers = new ArrayList<>();

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
        private volatile boolean enable = false;

        /**
         * 白名单
         */
        private volatile List<String> whiteList = new ArrayList<>();
    }

    @Data
    public static class Snowflake implements Serializable {
        private volatile Integer workerId;
        private volatile Integer dataCenterId;
    }
}
