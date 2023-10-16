package club.p6e.coat.common.sortable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@Getter
public class SortableContext
        <I extends SortableContext.Option>
        extends ArrayList<I> implements Serializable {

    @Data
    @AllArgsConstructor
    public static class Mapper implements Serializable {

        /**
         * 名称
         */
        private String name;

        /**
         * 字段
         */
        private String column;
    }

    @Data
    public static class Option implements Serializable {

        /**
         * 请求的内容
         */
        private String content;

        /**
         * 请求的条件
         */
        private String condition;
    }

    /**
     * AES 升序排列
     */
    public static final String AES = "AES";

    /**
     * DESC 降序排列
     */
    public static final String DESC = "DESC";

    /**
     * 验证参数是否合法
     *
     * @param clazz   模型类型
     * @param context 排序上下文对象
     * @return 参数是否合法
     */
    public static boolean validation(Class<?> clazz, SortableContext<?> context) {
        if (clazz == null || context == null) {
            return false;
        } else {
            final Field[] fields = clazz.getDeclaredFields();
            final List<Mapper> mappers = new ArrayList<>();
            for (final Field field : fields) {
                final Sortable sortable = field.getAnnotation(Sortable.class);
                if (sortable != null) {
                    mappers.add(new Mapper(
                            StringUtils.hasText(sortable.name()) ? sortable.name() : field.getName(),
                            StringUtils.hasText(sortable.column()) ? sortable.column() : field.getName())
                    );
                }
            }
            for (final Option option : context) {
                boolean bool = false;
                String content = option.getContent();
                String condition = option.getCondition();
                if (StringUtils.hasText(content)) {
                    content = content.toLowerCase();
                } else {
                    return false;
                }
                if (StringUtils.hasText(condition)) {
                    condition = condition.toUpperCase();
                    condition = condition.equals(DESC) ? DESC : AES;
                } else {
                    return false;
                }
                for (final Mapper mapper : mappers) {
                    if (mapper.getName().toLowerCase().equals(content)) {
                        bool = true;
                        content = mapper.getColumn();
                        break;
                    }
                }
                if (bool) {
                    option.setContent(content);
                    option.setCondition(condition);
                } else {
                    return false;
                }
            }
            context.validation = true;
            return true;
        }
    }

    /**
     * 验证情况
     */
    private boolean validation = false;

    /**
     * 验证自身
     *
     * @param clazz 模型类型
     * @return 参数是否合法
     */
    public boolean validation(Class<?> clazz) {
        return validation(clazz, this);
    }

}
