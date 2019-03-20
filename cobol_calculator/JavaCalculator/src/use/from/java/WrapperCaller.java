package use.from.java;

import java.util.Scanner;

import my.pack.CalclulatorFactory;
import my.pack.ICalculator;

public class WrapperCaller {

	public static void main(String[] args) {

		// get the input
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type two integers.");
		int arg1 = scanner.nextInt();
		int arg2 = scanner.nextInt();
		scanner.close();
		
		// program calls
		ICalculator calc = CalclulatorFactory.createCalculator();
		int add = calc.add(arg1, arg2);
		int multiply = calc.multiply(arg1, arg2);
		System.out.println(arg1 + " " + arg2 + " add " + add + " multiply " + multiply);
		
		// program call random
		int random = calc.randomNumber();
		System.out.println(random);
		
	}
	
}
