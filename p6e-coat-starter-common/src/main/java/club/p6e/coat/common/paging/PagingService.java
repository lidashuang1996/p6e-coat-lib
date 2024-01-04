package club.p6e.coat.common.paging;

/**
 * 分页的配置接口
 *
 * @author lidashuang
 * @version 1.0
 */
public interface PagingService {

    /**
     * 格式化分页的页码
     *
     * @param page 分页页码
     * @return 格式化之后的分页页码
     */
    int formatPage(Integer page);

    /**
     * 格式化分页的长度
     *
     * @param size 分页长度
     * @return 格式化之后的分页长度
     */
    int formatSize(Integer size);

    /**
     * 设置最大的分页长度
     *
     * @param maxSize 最大的分页长度
     */
    void setMaxSize(int maxSize);

    /**
     * 设置默认的分页页码
     *
     * @param defaultPage 默认的分页页码
     */
    void setDefaultPage(int defaultPage);

    /**
     * 设置默认的分页长度
     *
     * @param defaultSize 默认的分页长度
     */
    void setDefaultSize(int defaultSize);

}
