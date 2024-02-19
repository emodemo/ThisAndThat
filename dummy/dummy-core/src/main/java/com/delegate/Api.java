package com.delegate;

public class Api implements IApi {
	@Override
	public String doSomething(String text) {
		throw new RuntimeException("aaaa");
	}
}
