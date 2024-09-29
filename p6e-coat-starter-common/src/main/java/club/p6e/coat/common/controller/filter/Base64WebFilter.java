package club.p6e.coat.common.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 凭证认证过滤器
 *
 * @author lidashuang
 * @version 1.0
 */
public class Base64WebFilter implements Filter {

    /**
     * 注入日志系统
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Base64WebFilter.class);

    /**
     * 初始化过滤器时候的方法回调
     *
     * @param config 过滤的配置对象
     */
    @Override
    public void init(FilterConfig config) {
        LOGGER.info("filter [ " + this.getClass() + " ] init complete ...");
    }

    /**
     * 过滤器内容的处理方法
     *
     * @param servletRequest  服务请求头对象
     * @param servletResponse 服务返回头对象
     * @param chain           内部代理对象
     * @throws IOException      请求头和返回头处理时候可能出现的 IO 异常
     * @throws ServletException 服务处理时候可能出现的异常
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final Enumeration<String> enumeration = request.getHeaderNames();
        final CustomHttpServletRequest customHttpServletRequest = new CustomHttpServletRequest(request);
        while (enumeration.hasMoreElements()) {
            final String name = enumeration.nextElement();
            if (name != null && name.toLowerCase().startsWith("p6e-")) {
                customHttpServletRequest.addHeader(name,
                        new String(Base64.getDecoder().decode(
                                request.getHeader(name)), StandardCharsets.UTF_8));
            }
        }
        chain.doFilter(customHttpServletRequest, servletResponse);
    }

    /**
     * 摧毁过滤器的时候的回调方法
     */
    @Override
    public void destroy() {
        LOGGER.info("filter [ {} ] destroy complete !!", this.getClass());
    }


    private static class CustomHttpServletRequest extends HttpServletRequestWrapper {
        private final Map<String, String> headers = new HashMap<>();

        public CustomHttpServletRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getHeader(String name) {
            final String value = headers.get(name);
            if (value != null) {
                return value;
            }
            return super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            Enumeration<String> originalHeaders = super.getHeaderNames();
            List<String> combinedHeaders = Collections.list(originalHeaders);
            combinedHeaders.addAll(headers.keySet());
            return Collections.enumeration(combinedHeaders);
        }

        public void addHeader(String name, String value) {
            this.headers.put(name, value);
        }
    }

}
