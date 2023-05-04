package org.example.controllers.api;
import org.springframework.data.jpa.domain.Specification;

public interface Filter<T, F extends Filter> {

	default Specification<T> toPredicate(){
		return new FilterPredicate<>(this);
	}


}

enum FilterOperation{

	EQ("EQ"),
	LT("LT"),
	LTE("LTE"),
	GT("GT"),
	GTE("GTE"),
	NOT("NOT"),
	LIKE("LIKE"),
	IN("IN");

	private String value;

	FilterOperation(String value) {
		this.value = value;
	}
}
