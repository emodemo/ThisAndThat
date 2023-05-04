package com.delegate;

import io.github.resilience4j.bulkhead.BulkheadRegistry;

public class MainClass {


	public static void main(String[] args) {
		BulkheadRegistry bulkheadRegistry = BulkheadRegistry.custom().build();
		WrapperApi api = new WrapperApi((IApi) new Api(), bulkheadRegistry.bulkhead("testme"));
		api.doSomething("dSomething");
	}
}
