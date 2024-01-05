package club.p6e.coat.common.controller.config;

import club.p6e.coat.common.context.ResultContext;
import club.p6e.coat.common.error.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

/**
 * BaseExceptionController
 *
 * @author lidashuang
 * @version 1.0
 */
@ControllerAdvice
@Component(BaseExceptionController.BEAN_NAME)
public class BaseExceptionController {

    /**
     * 注入的 BEAN 的名称
     */
    public static final String BEAN_NAME = "club.p6e.coat.common.controller.BaseExceptionController";

    /**
     * 注入日志对象
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(BaseExceptionController.class);

    @ResponseBody
    @SuppressWarnings("ALL")
    @ExceptionHandler(value = Exception.class)
    public Object errorHandler(Exception exception) {
        LOGGER.error(exception.getMessage());
        final Throwable throwable = CustomException.transformation(exception);
        if (throwable instanceof CustomException) {
            final CustomException customException = (CustomException) exception;
            return this.isServletRequest()
                    ? ResultContext.build(customException.getCode(), customException.getSketch(), "")
                    : Mono.just(ResultContext.build(customException.getCode(), customException.getSketch(), ""));
        }
        exception.printStackTrace();
        return this.isServletRequest()
                ? ResultContext.build(500, "SERVICE_EXCEPTION", "")
                : Mono.just(ResultContext.build(500, "SERVICE_EXCEPTION", ""));
    }

    /**
     * 获取是否包含 javax.servlet.ServletRequest 对象
     *
     * @return 是否包含 javax.servlet.ServletRequest 对象
     */
    private boolean isServletRequest() {
        boolean bool = true;
        try {
            Class.forName("jakarta.servlet.ServletRequest");
        } catch (ClassNotFoundException e) {
            bool = false;
        }
        return bool;
    }
}
