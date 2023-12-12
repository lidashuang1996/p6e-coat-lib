package club.p6e.coat.common.utils.reactor;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class HttpUtil {

    private static final WebClient WEB_CLIENT = WebClient.create();

    public static Mono<String> doPost(String url) {
        return doPost(url, null);
    }

    public static Mono<String> doPost(String url, Map<String, String> headers) {
        return doPost(url, headers, "");
    }

    public static Mono<String> doPost(String url, Map<String, String> headers, String params) {
        return doPost(WEB_CLIENT, url, headers, params);
    }

    public static Mono<String> doPost(String url, Map<String, String> headers, Map<String, String> params) {
        return doPost(WEB_CLIENT, url, headers, params);
    }

    public static Mono<String> doPost(WebClient webClient, String url, Map<String, String> headers, String params) {
        if (headers == null) {
            headers = new HashMap<>();
            headers.put(HttpHeaders.CONTENT_TYPE, "application/json");
        } else {
            headers.putIfAbsent(HttpHeaders.CONTENT_TYPE, "application/json");
        }
        final Map<String, String> fh = headers;
        return webClient
                .post()
                .uri(url)
                .headers(httpHeaders -> {
                    for (final String key : fh.keySet()) {
                        httpHeaders.set(key, fh.get(key));
                    }
                })
                .bodyValue(params)
                .retrieve()
                .bodyToMono(String.class);
    }

    public static Mono<String> doPost(WebClient webClient, String url, Map<String, String> headers, Map<String, String> params) {
        if (headers == null) {
            headers = new HashMap<>();
            headers.put(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        } else {
            headers.putIfAbsent(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        }
        if (params == null) {
            params = new HashMap<>();
        }
        final Map<String, String> fh = headers;
        final StringBuilder content = new StringBuilder();
        for (final String key : params.keySet()) {
            content.append("&")
                    .append(key)
                    .append("=")
                    .append(URLEncoder.encode(params.get(key), StandardCharsets.UTF_8));
        }
        if (!content.isEmpty()) {
            content.deleteCharAt(0);
        }
        return webClient
                .post()
                .uri(url)
                .headers(httpHeaders -> {
                    for (final String key : fh.keySet()) {
                        httpHeaders.set(key, fh.get(key));
                    }
                })
                .bodyValue(content.toString())
                .retrieve()
                .bodyToMono(String.class);
    }

}
