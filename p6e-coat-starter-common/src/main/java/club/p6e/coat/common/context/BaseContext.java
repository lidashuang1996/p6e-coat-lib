package club.p6e.coat.common.context;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 基础的上下文对象
 *
 * @author lidashuang
 * @version 1.0
 */
public class BaseContext implements Serializable {

    @Data
    @Accessors(chain = true)
    public static class PagingParam implements Serializable {
        private Integer size;
        private Integer page;
    }

    @Data
    @Accessors(chain = true)
    public static class ListResult implements Serializable {
        private Integer size;
        private Integer page;
        private Long total;
    }

}
