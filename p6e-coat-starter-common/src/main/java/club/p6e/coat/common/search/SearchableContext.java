package club.p6e.coat.common.search;

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
public class SearchableContext
        <I extends SearchableContext.Option>
        extends ArrayList<I> implements Serializable {

    @Data
    @AllArgsConstructor
    public static class Mapper implements Serializable {
        private String name;
        private String column;
    }

    @Data
    public static class Option implements Serializable {
        private String key;
        private String value;
        private String condition;
        private String relationship;
        private String type;
    }

    public static final String LEFT_TYPE = "(";
    public static final String RIGHT_TYPE = ")";
    public static final String CONDITION_TYPE = "CONDITION";
    public static final String OR_RELATIONSHIP_TYPE = "OR";
    public static final String AND_RELATIONSHIP_TYPE = "AND";
    public static final String EQUAL_OPTION_CONDITION = "=";
    public static final String GREATER_THAN_OPTION_CONDITION = ">";
    public static final String GREATER_THAN_OR_EQUAL_OPTION_CONDITION = ">=";
    public static final String LESS_THAN_OPTION_CONDITION = "<";
    public static final String LESS_THAN_OR_EQUAL_OPTION_CONDITION = "<=";
    public static final String IS_NULL_OPTION_CONDITION = "IS_NULL";
    public static final String IS_NOT_NULL_OPTION_CONDITION = "IS_NOT_NULL";

    /**
     * 验证参数
     *
     * @param clazz   模型对象
     * @param context 搜索上下文对象
     * @return 是否验证成功
     */
    public static boolean validation(Class<?> clazz, SearchableContext<?> context) {
        if (context == null) {
            return false;
        } else {
            final List<Mapper> mappers = new ArrayList<>();
            final Field[] fields = clazz.getDeclaredFields();
            for (final Field field : fields) {
                final Searchable searchable = field.getAnnotation(Searchable.class);
                if (searchable != null) {
                    mappers.add(new Mapper(
                            StringUtils.hasText(searchable.name()) ? searchable.name() : field.getName(),
                            StringUtils.hasText(searchable.column()) ? searchable.column() : field.getName())
                    );
                }
            }
            for (final SearchableContext.Option option : context) {
                boolean bool = false;
                String key = option.getKey();
                String value = option.getValue();
                String type = option.getType();
                String condition = option.getCondition();
                String relationship = option.getRelationship();
                if (StringUtils.hasText(type)) {
                    type = type.toUpperCase();
                    switch (type) {
                        case LEFT_TYPE,
                                RIGHT_TYPE -> {
                            continue;
                        }
                        case CONDITION_TYPE -> {}
                        default -> {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
                if (StringUtils.hasText(relationship)) {
                    relationship = relationship.toUpperCase();
                    relationship = relationship.equals(OR_RELATIONSHIP_TYPE) ? OR_RELATIONSHIP_TYPE : AND_RELATIONSHIP_TYPE;
                } else {
                    return false;
                }
                if (StringUtils.hasText(key)) {
                    key = key.toLowerCase();
                } else {
                    return false;
                }
                if (StringUtils.hasText(condition)) {
                    switch (condition) {
                        case EQUAL_OPTION_CONDITION,
                                GREATER_THAN_OPTION_CONDITION,
                                GREATER_THAN_OR_EQUAL_OPTION_CONDITION,
                                LESS_THAN_OPTION_CONDITION,
                                LESS_THAN_OR_EQUAL_OPTION_CONDITION -> {
                            if (!StringUtils.hasText(value)) {
                                return false;
                            }
                        }
                        case IS_NULL_OPTION_CONDITION,
                                IS_NOT_NULL_OPTION_CONDITION -> {
                        }
                        default -> {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
                for (final Mapper mapper : mappers) {
                    if (mapper.getName().toLowerCase().equals(key)) {
                        bool = true;
                        key = mapper.getColumn();
                        break;
                    }
                }
                if (bool) {
                    option.setKey(key);
                    option.setType(type);
                    option.setCondition(condition);
                    option.setRelationship(relationship);
                } else {
                    return false;
                }
            }
            context.validation = true;
            return true;
        }
    }

    /**
     * 关联关系
     */
    private final String relationship;

    /**
     * 验证情况
     */
    private boolean validation = false;

    /**
     * 构造方法
     */
    public SearchableContext() {
        super();
        this.relationship = AND_RELATIONSHIP_TYPE;
    }

    /**
     * 构造方法
     *
     * @param relationship 关联关系
     */
    public SearchableContext(String relationship) {
        super();
        this.relationship = OR_RELATIONSHIP_TYPE.equalsIgnoreCase(relationship)
                ? OR_RELATIONSHIP_TYPE : AND_RELATIONSHIP_TYPE;
    }

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
