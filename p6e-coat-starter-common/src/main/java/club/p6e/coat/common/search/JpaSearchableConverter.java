package club.p6e.coat.common.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class JpaSearchableConverter {

    public static void injectSearch(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchableContext context) {
        execute(root, query, builder, context, null);
    }

    public static void injectSearch(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchableContext context, Predicate predicate) {
        execute(root, query, builder, context, predicate);
    }

    private static void execute(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchableContext context, Predicate predicate) {
        final List<Predicate> predicates = new ArrayList<>();
        if (predicate != null) {
            predicates.add(predicate);
        }
        if (context != null && !context.isEmpty()) {
            predicates.addAll(execute(root, builder, SearchableContext.extractOptions(context)));
        }
        query.where(predicates.toArray(new Predicate[0]));
    }

    private static Predicate execute(Root<?> root, CriteriaBuilder builder, SearchableAbstract.Option option) {
        if (SearchableAbstract
                .EQUAL_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())
                && option.getValue() != null) {
            return builder.equal(root.get(option.getKey()).as(String.class), option.getValue());
        }
        if (SearchableAbstract
                .NOT_EQUAL_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())
                && option.getValue() != null) {
            return builder.notEqual(root.get(option.getKey()).as(String.class), option.getValue());
        }
        if (SearchableAbstract
                .GREATER_THAN_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())
                && option.getValue() != null) {
            return builder.greaterThan(root.get(option.getKey()).as(String.class), option.getValue());
        }
        if (SearchableAbstract
                .GREATER_THAN_OR_EQUAL_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())
                && option.getValue() != null) {
            return builder.greaterThanOrEqualTo(root.get(option.getKey()).as(String.class), option.getValue());
        }
        if (SearchableAbstract
                .LESS_THAN_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())
                && option.getValue() != null) {
            return builder.lessThan(root.get(option.getKey()).as(String.class), option.getValue());
        }
        if (SearchableAbstract
                .LESS_THAN_OR_EQUAL_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())
                && option.getValue() != null) {
            return builder.lessThanOrEqualTo(root.get(option.getKey()).as(String.class), option.getValue());
        }
        if (SearchableAbstract
                .IS_NULL_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())) {
            return builder.isNull(root.get(option.getKey()));
        }
        if (SearchableAbstract
                .IS_NOT_NULL_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())) {
            return builder.isNotNull(root.get(option.getKey()));
        }
        if (SearchableAbstract
                .LIKE_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())
                && option.getValue() != null) {
            return builder.like(root.get(option.getKey()).as(String.class), option.getValue());
        }
        return builder.and();
    }

    private static List<Predicate> execute(Root<?> root, CriteriaBuilder builder, List<SearchableAbstract.Mixin> mixins) {
        final List<Predicate> predicates = new ArrayList<>();
        if (mixins != null) {
            for (final SearchableAbstract.Mixin mixin : mixins) {
                final SearchableAbstract.Option option = mixin.getData();
                final List<SearchableAbstract.Mixin> list = mixin.getList();
                if (option != null && list == null) {
                    if (SearchableAbstract.OR_RELATIONSHIP_TYPE.equals(option.getRelationship())) {
                        predicates.add(builder.or(execute(root, builder, option)));
                    }
                    if (SearchableAbstract.AND_RELATIONSHIP_TYPE.equals(option.getRelationship())) {
                        predicates.add(builder.and(execute(root, builder, option)));
                    }
                }
                if (option != null && list != null) {
                    if (SearchableAbstract.OR_RELATIONSHIP_TYPE.equals(option.getRelationship())) {
                        final Predicate a3 = builder.or(execute(root, builder, list).toArray(new Predicate[0]));
                        predicates.add(a3);
                    }
                    if (SearchableAbstract.AND_RELATIONSHIP_TYPE.equals(option.getRelationship())) {
                        final Predicate a4 = builder.or(execute(root, builder, list).toArray(new Predicate[0]));
                        predicates.add(a4);
                    }
                }
            }
        }
        return predicates;
    }
}
