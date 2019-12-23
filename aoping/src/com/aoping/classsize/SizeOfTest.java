package com.aoping.classsize;

public class SizeOfTest {

	// create inatrument.jar - see in InstrumentationAgent
	// run with -javaagent:lib/instrument.jar
	
	public static void main(String[] args) {
		
		//16bytes for object in 64bit jdk
		//references are 4 bytes for heap up to 32GB and 8 bytes for more
		
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
		System.out.println(object.getClass() + " " + InstrumentationAgent.getObjectSize(object));
	}
	
}

class EmptyClass{}