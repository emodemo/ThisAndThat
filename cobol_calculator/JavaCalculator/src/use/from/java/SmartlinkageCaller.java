package use.from.java;

import java.util.Scanner;

import my.procedure.Calculator;
import my.procedure.LnkSum;

public class SmartlinkageCaller {

	public static void main(String[] args) {
		
		// get the input
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type two integers.");
		int arg1 = scanner.nextInt();
		int arg2 = scanner.nextInt();
		scanner.close();
		
		// init the program
		Calculator calc = new Calculator();
		
		// call the program
		LnkSum sum = new LnkSum();
		Byte operation = new Byte("1"); // call addition as defined in the functions.cpy
		calc.Calculator(arg1, arg2, operation, sum);
		System.out.println(arg1 + " + " + arg2 + " = " + sum.getLnkSum());
		
		// call the program again
		sum = new LnkSum();
		operation = new Byte("3"); // call multiplication as defined in the functions.cpy
		calc.Calculator(arg1, arg2, operation, sum);
		System.out.println(arg1 + " * " + arg2 + " = " + sum.getLnkSum());
	}

}
