package club.p6e.coat.common.sortable;

import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.data.domain.Sort;

/**
 * @author lidashuang
 * @version 1.0
 */
public class JpaSortableContextConverter {

    public static void to(CriteriaQuery<?> query, SortableContext context) {
        to(query, context, null);
    }

    public static void to(CriteriaQuery<?> query, SortableContext context, Sort sort) {
        execute(query, context, sort);
    }

    private static void execute(CriteriaQuery<?> query, SortableContext context, Sort sort) {
        if (sort == null) {
            query.orderBy(to(context));
        } else {
            query.orderBy(to(context).and(sort));
        }

        return Sort.by(context.stream().map(i ->
                SortableAbstract.DESC.equals(i.getCondition())
                        ? Sort.Order.desc(i.getContent()) : Sort.Order.asc(i.getContent())
        ).toList());
    }

}
