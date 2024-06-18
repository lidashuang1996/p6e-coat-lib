package club.p6e.coat.common.global;

import club.p6e.coat.common.controller.BaseController;
import club.p6e.coat.common.controller.BaseWebController;
import club.p6e.coat.common.controller.BaseWebFluxController;
import club.p6e.coat.common.utils.JsonUtil;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class Globals {

    /**
     * 是否为调试模式
     */
    private static boolean DEBUG = false;

    /**
     * 用户的信息头
     */
    @SuppressWarnings("ALL")
    private static final String USER_INFO_HEADER = "P6e-User-Info";

    /**
     * 用户的权限头
     */
    @SuppressWarnings("ALL")
    private static final String USER_INFO_PERMISSION = "P6e-User-Permission";

    public static void setDebug() {
        DEBUG = true;
    }

    public static GlobalUserInfo getUserInfo() {
        if (BaseController.isServletRequest()) {
            try {
                if (DEBUG) {
                    return GlobalUserInfo.DEBUG;
                } else {
                    return JsonUtil.fromJson(BaseWebController.getHeader(USER_INFO_HEADER), GlobalUserInfo.class);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static String getUserInfoContent() {
        if (BaseController.isServletRequest()) {
            try {
                if (DEBUG) {
                    return JsonUtil.toJson(GlobalUserInfo.DEBUG);
                } else {
                    return BaseWebController.getHeader(USER_INFO_HEADER);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static String getUserProject() {
        return "20000";
    }

    public static String getUserOrganization() {
        return "20000";
    }

    public static String getLanguage() {
        return "zh-cn";
    }

    public static GlobalUserInfo getUserInfo(ServerHttpRequest request) {
        if (!BaseController.isServletRequest()) {
            try {
                if (DEBUG) {
                    return GlobalUserInfo.DEBUG;
                } else {
                    return JsonUtil.fromJson(BaseWebFluxController.getHeader(request, USER_INFO_HEADER), GlobalUserInfo.class);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static GlobalUserPermission getUserPermission() {
        if (BaseController.isServletRequest()) {
            try {
                if (DEBUG) {
                    return GlobalUserPermission.DEBUG;
                } else {
                    return JsonUtil.fromJson(BaseWebController.getHeader(USER_INFO_PERMISSION), GlobalUserPermission.class);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static GlobalUserPermission getUserPermission(ServerHttpRequest request) {
        if (!BaseController.isServletRequest()) {
            try {
                if (DEBUG) {
                    return GlobalUserPermission.DEBUG;
                } else {
                    return JsonUtil.fromJson(BaseWebFluxController.getHeader(request, USER_INFO_PERMISSION), GlobalUserPermission.class);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

}
