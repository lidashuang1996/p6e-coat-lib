package club.p6e.coat.common.controller.filter;

import club.p6e.coat.common.Properties;
import club.p6e.coat.common.context.ResultContext;
import club.p6e.coat.common.utils.JsonUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * 凭证认证过滤器
 *
 * @author lidashuang
 * @version 1.0
 */
public class VoucherWebFilter implements Filter {

    /**
     * 凭证的头部
     */
    private static final String VOUCHER_HEADER = "P6e-Voucher";

    /**
     * 注入日志系统
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherWebFilter.class);

    /**
     * 错误结果对象
     */
    private static final ResultContext ERROR_RESULT = ResultContext.build(401, "Unauthorized", "Unauthorized");

    /**
     * 错误结果文本内容
     */
    private static final String ERROR_RESULT_CONTENT = JsonUtil.toJson(ERROR_RESULT);

    /**
     * 配置对象
     */
    private final Properties properties;

    /**
     * 构造方法初始化
     *
     * @param properties 配置对象
     */
    public VoucherWebFilter(Properties properties) {
        this.properties = properties;
    }

    /**
     * 初始化过滤器时候的方法回调
     *
     * @param config 过滤的配置对象
     */
    @Override
    public void init(FilterConfig config) {
        LOGGER.info("Filter [ VoucherFilter ] init complete ... ");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        final Properties.Security security = properties.getSecurity();
        if (security != null && security.isEnable()) {
            String voucher = null;
            final HttpServletRequest request = (HttpServletRequest) servletRequest;
            final Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                final String headerName = headerNames.nextElement();
                if (VOUCHER_HEADER.equalsIgnoreCase(headerName)) {
                    voucher = request.getHeader(headerName);
                    break;
                }
            }
            if (voucher != null) {
                for (final String item : security.getVouchers()) {
                    if (item.equalsIgnoreCase(voucher)) {
                        chain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                }
            }
            final HttpServletResponse response = (HttpServletResponse) servletResponse;
            final String result = JsonUtil.toJson(ERROR_RESULT_CONTENT);
            if (result != null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                try (final OutputStream outputStream = response.getOutputStream()) {
                    outputStream.write(result.getBytes(StandardCharsets.UTF_8));
                }
            }
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * 摧毁过滤器的时候的回调方法
     */
    @Override
    public void destroy() {
        LOGGER.info("Filter [ VoucherFilter ] destroy complete ... ");
    }

}
