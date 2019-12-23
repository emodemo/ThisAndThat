package com.aoping.classsize;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;


public class SizeOfJoll {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long myLong = 32L; // primitive int is 4 bytes obecjt Integer is 16 bytes
		int myInt = 32;
		boolean myBoolean = false;
		EmptyClass emptyClass = new EmptyClass();
		Example1 example1 = new Example1();
		Example2 example2 = new Example2();
		Example3 example3 = new Example3();
		
//		printObjectSize(myLong);
//		printObjectSize(myInt);
//		printObjectSize(myBoolean);
//		printObjectSize(emptyClass);
		printObjectSize(example1);
//		printObjectSize(example2);
		printObjectSize(example3);
	
//		printObjectSize(new TestMe());
//		printObjectSize(new TestMeLong());
//		printObjectSize(new TestMeInt());
//		printObjectSize(new TestMeChar());
		
		
	}

	private static void printObjectSize(Object object) {
			System.out.println(VM.current().details());
			System.out.println(object.getClass() + " " + ClassLayout.parseInstance(object).toPrintable());
		}
}


class TestMe{}
class TestMeLong{ private long l;}
class TestMeInt {private int i;}
class TestMeChar {private char c;}