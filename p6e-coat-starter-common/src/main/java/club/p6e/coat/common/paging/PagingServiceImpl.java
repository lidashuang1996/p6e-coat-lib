package club.p6e.coat.common.paging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * @author lidashuang
 * @version 1.0
 */
//@Component
//@ConditionalOnMissingBean(
//        value = PagingService.class,
//        ignored = PagingServiceImpl.class
//)
public class PagingServiceImpl implements PagingService {

    /**
     * 最大的分页长度
     */
    private int maxSize = 200;

    /**
     * 默认的分页页码
     */
    private int defaultPage = 1;

    /**
     * 默认的分页长度
     */
    private int defaultSize = 16;

    @Override
    public int formatPage(Integer page) {
        return (page == null || page < 1) ? defaultPage : page;
    }

    @Override
    public int formatSize(Integer size) {
        return (size == null || size < 1) ? defaultSize : Math.min(size, maxSize);
    }

    @Override
    public void setMaxSize(int maxSize) {
        if (maxSize > 0) {
            this.maxSize = maxSize;
        }
    }

    @Override
    public void setDefaultPage(int defaultPage) {
        if (defaultPage > 0) {
            this.defaultPage = defaultPage;
        }
    }

    @Override
    public void setDefaultSize(int defaultSize) {
        if (defaultSize > 0) {
            this.defaultSize = defaultSize;
        }
    }

}
