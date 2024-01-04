package club.p6e.coat.common.search;

import club.p6e.coat.common.sortable.SortableAbstract;
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
        execute(root, query, builder, context);
    }

    private static void execute(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchableContext context) {
        if (context != null && !context.isEmpty()) {
            SearchableContext.extractOptions(context);
        }
    }


    private static void execute(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder, List<Object> list, Predicate predicate, CriteriaBuilder builder) {
        if (list != null) {
            Predicate newPredicate = builder.and();
            for (final Object item : list) {
                if (item instanceof List<?>) {

                }
                if (item instanceof SearchableAbstract.Option) {
                    query.where();
                }
            }
        }
    }

    private static Predicate execxute(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder, SearchableContext context) {
        int hierarchy = 0;
        boolean bool = false;
        SearchableAbstract<SearchableAbstract.Option> searchable = null;
        final List<Predicate> predicates = new ArrayList<>();
        for (final SearchableAbstract.Option option : context) {
            if (SearchableAbstract.LEFT_TYPE.equals(option.getType())) {
                hierarchy++;
                if (searchable == null) {
                    searchable = new SearchableAbstract<>(option.getRelationship());
                    if (SearchableAbstract.AND_RELATIONSHIP_TYPE
                            .equalsIgnoreCase(searchable.getRelationship())) {
                        bool = true;
                    }
                } else {
                    searchable.add(option);
                }
            }
            if (SearchableAbstract.RIGHT_TYPE.equals(option.getType())) {
                if (searchable == null) {
                    return null;
                } else {
                    hierarchy--;
                    if (hierarchy == 0) {
                        if (!searchable.isEmpty()) {
                            if (SearchableAbstract.OR_RELATIONSHIP_TYPE
                                    .equalsIgnoreCase(searchable.getRelationship())) {
                                predicates.add(execute(searchable, rt, cb));
                            }
                            if (SearchableAbstract.AND_RELATIONSHIP_TYPE
                                    .equalsIgnoreCase(searchable.getRelationship())) {
                                predicates.add(execute(searchable, rt, cb));
                            }
                        }
                        searchable = null;
                    } else {
                        searchable.add(option);
                    }
                }
            }
            if (SearchableAbstract.CONDITION_TYPE.equals(option.getType())) {
                if (searchable == null) {
                    if (SearchableAbstract.OR_RELATIONSHIP_TYPE
                            .equalsIgnoreCase(option.getRelationship())) {
                        final Predicate predicate = execute(option, rt, cb);
                        if (predicate != null) {
                            predicates.add(predicate);
                        }
                    }
                    if (SearchableAbstract.AND_RELATIONSHIP_TYPE
                            .equalsIgnoreCase(option.getRelationship())) {
                        final Predicate predicate = execute(option, rt, cb);
                        if (predicate != null) {
                            bool = true;
                            predicates.add(predicate);
                        }
                    }
                } else {
                    searchable.add(option);
                }
            }
        }
        return bool ? cb.and(predicates.toArray(new Predicate[0])) : cb.or(predicates.toArray(new Predicate[0]));
    }

    private static Predicate execute(SearchableAbstract.Option option, Root<?> rt, CriteriaBuilder cb) {
        if (option.getKey() == null || option.getCondition() == null) {
            return null;
        }
        if (SearchableAbstract
                .EQUAL_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())
                && option.getValue() != null) {
            return cb.equal(rt.get(option.getKey()), option.getValue());
        }
        if (SearchableAbstract
                .GREATER_THAN_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())
                && option.getValue() != null) {
            return cb.greaterThan(rt.get(option.getKey()), option.getValue());
        }
        if (SearchableAbstract
                .GREATER_THAN_OR_EQUAL_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())
                && option.getValue() != null) {
            return cb.greaterThanOrEqualTo(rt.get(option.getKey()), option.getValue());
        }
        if (SearchableAbstract
                .LESS_THAN_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())
                && option.getValue() != null) {
            return cb.lessThan(rt.get(option.getKey()), option.getValue());
        }
        if (SearchableAbstract
                .LESS_THAN_OR_EQUAL_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())
                && option.getValue() != null) {
            return cb.lessThanOrEqualTo(rt.get(option.getKey()), option.getValue());
        }
        if (SearchableAbstract
                .IS_NULL_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())) {
            return cb.isNull(rt.get(option.getKey()));
        }
        if (SearchableAbstract
                .IS_NOT_NULL_OPTION_CONDITION
                .equalsIgnoreCase(option.getCondition())
                && option.getValue() != null) {
            return cb.isNotNull(rt.get(option.getKey()));
        }
        return null;
    }

}
