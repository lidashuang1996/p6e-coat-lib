package club.p6e.coat.common.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class PaginationContext {

    private String all;
    private Integer page;
    private Integer size;

}
