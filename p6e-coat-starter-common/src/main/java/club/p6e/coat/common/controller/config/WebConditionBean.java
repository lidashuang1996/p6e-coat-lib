package club.p6e.coat.common.controller.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
@ConditionalOnClass(name = "org.springframework.web.servlet.package-info")
public class WebConditionBean {

    public WebConditionBean() {
    }
}
