package club.p6e.coat.common.controller;

import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * BaseWebFluxController
 *
 * @author lidashuang
 * @version 1.0
 */
public class BaseWebFluxController extends BaseController {

    /**
     * 获取 ACCESS TOKEN 内容
     *
     * @param exchange ServerWebExchange 对象
     * @return ACCESS TOKEN 内容
     */
    public static String getAccessToken(ServerWebExchange exchange) {
        return getAccessToken(exchange.getRequest());
    }

    /**
     * 获取 ACCESS TOKEN 内容
     *
     * @param request ServerHttpRequest 对象
     * @return ACCESS TOKEN 内容
     */
    public static String getAccessToken(ServerHttpRequest request) {
        String accessToken = getParamToken(request, ACCESS_TOKEN_PARAM1, ACCESS_TOKEN_PARAM2);
        if (accessToken == null) {
            return getHeaderToken(request);
        } else {
            return accessToken;
        }
    }

    /**
     * 获取 REFRESH TOKEN 内容
     *
     * @param exchange ServerWebExchange 对象
     * @return REFRESH TOKEN 内容
     */
    public static String getRefreshToken(ServerWebExchange exchange) {
        return getRefreshToken(exchange.getRequest());
    }

    /**
     * 获取 REFRESH TOKEN 内容
     *
     * @param request ServerHttpRequest 对象
     * @return REFRESH TOKEN 内容
     */
    public static String getRefreshToken(ServerHttpRequest request) {
        return getParamToken(request, REFRESH_TOKEN_PARAM1, REFRESH_TOKEN_PARAM2);
    }

    /**
     * 通过多个参数名称去获取 url 路径上面的参数值
     *
     * @param params 参数名称
     * @return 读取的参数名称对应的值
     */
    public static String getParamToken(ServerHttpRequest request, String... params) {
        String value;
        for (String param : params) {
            value = request.getQueryParams().getFirst(param);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    /**
     * 获取请求头部存在的 TOKEN 信息
     *
     * @return 头部的 TOKEN 信息
     */
    public static String getHeaderToken(ServerHttpRequest request) {
        final String requestHeaderContent = request.getHeaders().getFirst(AUTH_HEADER);
        if (requestHeaderContent != null
                && requestHeaderContent.startsWith(AUTH_HEADER_TOKEN_PREFIX)) {
            return requestHeaderContent.substring(AUTH_HEADER_TOKEN_PREFIX.length());
        }
        return null;
    }

    /**
     * 获取 cookie 的 TOKEN 信息
     *
     * @return cookie 的 ACCESS TOKEN 信息
     */
    public static List<HttpCookie> getCookie(ServerHttpRequest request, String name) {
        return request.getCookies().get(name);
    }

    /**
     * 获取 cookie 的 TOKEN 信息
     *
     * @return cookie 的 ACCESS TOKEN 信息
     */
    public static String getCookieAccessToken(ServerHttpRequest request) {
        final List<HttpCookie> cookies = getCookie(request, COOKIE_ACCESS_TOKEN_PARAM);
        if (cookies != null && !cookies.isEmpty()) {
            return cookies.get(0).getValue();
        }
        return null;
    }

    /**
     * 获取 cookie 的 TOKEN 信息
     *
     * @return cookie 的 REFRESH TOKEN 信息
     */
    public static String getCookieRefreshToken(ServerHttpRequest request) {
        final List<HttpCookie> cookies = getCookie(request, COOKIE_REFRESH_TOKEN_PARAM);
        if (cookies != null && !cookies.isEmpty()) {
            return cookies.get(0).getValue();
        }
        return null;
    }
}
