package com.pocs;

import java.util.Optional;

public class OptinalTest {


	public static void main(String[] args) {
		OptinalTest test = new OptinalTest();
		A a = new A();
		A a3 = new A();
		a3.aaa = "aaaaaaa";
		Optional<String> string = test.getString(null);
		Optional<String> string2 = test.getString(a);
		Optional<String> string3 = test.getString(a3);
		string.toString();
		String s = string3.orElse("");
		System.out.println(s);
	}



	private Optional<String> getString(A a){
		return Optional.ofNullable(a)
				.map(aa -> aa.aaa);
	}
}

class A {
	String aaa;
}
