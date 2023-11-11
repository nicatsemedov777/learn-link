package az.iktlab.learnlink.specification;

import az.iktlab.learnlink.entity.Course;
import az.iktlab.learnlink.entity.metadata.Course_;
import az.iktlab.learnlink.model.request.course.CourseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class CourseSpecification implements Specification<Course> {

    private CourseFilter filter;

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isFalse(root.get(Course_.IS_DELETED)));
//        predicates.add(cb.equal(root.get(Course_.TEACHER).get("id"), filter.getUserId()));

        if (filter == null) {
            cb.and(predicates.toArray(Predicate[]::new));
        }

        if (filter.getMinDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Course_.CREATE_DATE), new Date(filter.getMinDate())));
        }

        if (filter.getMaxDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Course_.CREATE_DATE), new Date(filter.getMaxDate())));
        }

        if (filter.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Course_.PRICE), filter.getMinPrice()));
        }

        if (filter.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Course_.PRICE), filter.getMaxPrice()));
        }

        if (StringUtils.isNotEmpty(filter.getSearchText())) {
            predicates.add(cb.like(cb.lower(root.get(Course_.NAME)), prepareSearchText(filter.getSearchText())));
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }

    private String prepareSearchText(String searchText) {
        return "%" + searchText.toLowerCase() + "%";
    }
}
