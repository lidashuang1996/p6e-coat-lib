package club.p6e.coat.common.global;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class GlobalUserInfo implements Serializable {

    public static GlobalUserInfo DEBUG = new GlobalUserInfo();

    public static void setDebug(GlobalUserInfo userInfo) {
        DEBUG = userInfo;
    }

    private Integer id;
    private Integer status;
    private String account;
    private String name;
    private String nickname;
    private String avatar;
    private String description;
    private String phone;
    private String mailbox;
    private List<String> marks = new ArrayList<>();
    private List<String> groups = new ArrayList<>();
}
