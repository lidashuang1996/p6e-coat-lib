package club.p6e.coat.common;

import club.p6e.coat.common.context.WebResultErrorContent;
import club.p6e.coat.common.utils.SnowflakeIdUtil;
import club.p6e.coat.common.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

/**
 * Spring Boot 初始化之后钩子函数
 *
 * @author lidashuang
 * @version 1.0
 */
@Component(ApplicationListener.BEAN_NAME)
public class ApplicationListener implements
        org.springframework.context.ApplicationListener<ApplicationReadyEvent> {

    /**
     * 注入的 BEAN 的名称
     */
    public static final String BEAN_NAME = "club.p6e.coat.common.ApplicationListener";

    /**
     * 注入日志对象
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        SpringUtil.init(event.getApplicationContext());
        WebResultErrorContent.init();
        final Properties properties = SpringUtil.getBean(Properties.class);
        for (final String name : properties.getSnowflake().keySet()) {
            final Properties.Snowflake snowflake = properties.getSnowflake().get(name);
            SnowflakeIdUtil.register(name, snowflake.getWorkerId(), snowflake.getDatacenterId());
            LOGGER.info("p6e coat application initialization snowflake [ worker_id: "
                    + snowflake.getWorkerId() + ", datacenter_id: " + snowflake.getDatacenterId() + "] ==> " + name);
        }
    }
}
