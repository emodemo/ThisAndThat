package com.delegate;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class WrapperApi implements IApi{

	private final IApi delegate;
	private final Bulkhead bulkhead;

	@Override
	public String doSomething(String text) {
		return trySomething(() -> delegate.doSomething(text));
	}

	private <T> T trySomething(Supplier<T> supplier){
		Supplier<T> decoratedSupplier = Bulkhead.decorateSupplier(bulkhead, supplier);
		try {
			return decoratedSupplier.get();
		} catch (BulkheadFullException e){
			System.out.println("something happened {}".formatted(e.getMessage()));
			throw e;
		}
	}
}
