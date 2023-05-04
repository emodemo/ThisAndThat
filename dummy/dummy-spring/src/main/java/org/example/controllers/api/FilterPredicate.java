package org.example.controllers.api;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterPredicate<T, F extends Filter> implements Specification<T> {

	private final F filter;

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		for (Field field : filter.getClass().getDeclaredFields()) {
			if (contains(root, field.getName())) {
				Path<Object> objectPath = root.get(field.getName());
				Object value = getValue(filter, field.getName());
				if(value == null) continue;
				predicates.add(criteriaBuilder.equal(objectPath, value));
			}
		}
		Predicate and = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		return and;

	}

	private boolean contains(Root<T> root, String field){
		return root
				.getModel()
				.getAttributes()
				.stream()
				.anyMatch(attribute -> attribute.getName().equals(field));
	}
	private Object getValue(F filter, String field){
		try {
			Field declaredField = filter.getClass().getDeclaredField(field);
			declaredField.setAccessible(true);
			return declaredField.get(filter);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			// print some message
			return null;
		}
	}
}
