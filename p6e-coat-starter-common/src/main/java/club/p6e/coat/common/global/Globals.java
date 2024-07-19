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
     * 语言的信息头
     */
    @SuppressWarnings("ALL")
    private static final String USER_LANGUAGE_HEADER = "P6e-Language";

    /**
     * 项目的信息头
     */
    @SuppressWarnings("ALL")
    private static final String USER_PROJECT_HEADER = "P6e-User-Project";

    /**
     * 语言的信息头
     */
    @SuppressWarnings("ALL")
    private static final String USER_ORGANIZATION_HEADER = "P6e-User-Organization";

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
        if (BaseController.isServletRequest()) {
            try {
                if (DEBUG) {
                    return GlobalUserProject.DEBUG.getProject();
                } else {
                    return BaseWebController.getHeader(USER_PROJECT_HEADER);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static String getUserProject(ServerHttpRequest request) {
        return "20000";
    }

    public static String getUserOrganization() {
        if (BaseController.isServletRequest()) {
            try {
                if (DEBUG) {
                    return GlobalUserOrganization.DEBUG.getOrganization();
                } else {
                    return BaseWebController.getHeader(USER_ORGANIZATION_HEADER);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static String getUserOrganization(ServerHttpRequest request) {
        return "20000";
    }

    public static String getLanguage() {
        if (BaseController.isServletRequest()) {
            try {
                if (DEBUG) {
                    return GlobalUserLanguage.DEBUG.getLanguage();
                } else {
                    return BaseWebController.getHeader(USER_LANGUAGE_HEADER);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static String getLanguage(ServerHttpRequest request) {
        return "zh-cn";
    }

}
