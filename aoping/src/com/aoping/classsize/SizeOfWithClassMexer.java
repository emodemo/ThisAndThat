package com.aoping.classsize;

import com.javamex.classmexer.MemoryUtil;

public class SizeOfWithClassMexer {

	// run with -javaagent:lib/classmexer.jar
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char myChar = 'c'; // primitive int is 4 bytes obecjt Integer is 16 bytes
		int myInt = 32;
		boolean myBoolean = false;
		EmptyClass emptyClass = new EmptyClass();
		Example1 example1 = new Example1();
		Example2 example2 = new Example2();
		Example3 example3 = new Example3();
		
		printObjectSize(myChar);
		printObjectSize(myInt);
		printObjectSize(myBoolean);
		printObjectSize(emptyClass);
		printObjectSize(example1);
		printObjectSize(example2);
		printObjectSize(example3);

		
		
	}

	private static void printObjectSize(Object object) {
			System.out.println(object.getClass() + " " + MemoryUtil.deepMemoryUsageOf(object));
		}

}
