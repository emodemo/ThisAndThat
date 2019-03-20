package use.from.java;

import java.io.File;
import java.util.Scanner;

import com.microfocus.cobol.RuntimeSystem;
import com.microfocus.cobol.lang.ReturnCode;

public class NativeCaller {

	public static void main(String[] args) {

		// get the input
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type two integers.");
		int arg1 = scanner.nextInt();
		int arg2 = scanner.nextInt();
		scanner.close();
		
		// load the program
		String sep = File.separator;
		String dll = ".." + sep + "NativeCalculator"+ sep + "New_Configuration.bin" + sep + "Calculator.dll";
		int cobload = RuntimeSystem.cobload(dll);
		if(cobload != 0) {
			System.err.println("Calculator not loaded!");
			return;
		}
		
		// prepare native call arguments
		ReturnCode code = new ReturnCode();
		Integer sum = new Integer(0);
		char operation = '1'; // call addition as defined in the functions.cpy
		Object[] params = new Object[] {arg1, arg2, operation, sum};
		int usageUpdate[] = {RuntimeSystem.BY_VALUE, RuntimeSystem.BY_VALUE, RuntimeSystem.BY_VALUE, RuntimeSystem.BY_REFERENCE};
		
		// call the program
		try {
			RuntimeSystem.cobcall(code, "Calculator", params, usageUpdate);
			System.out.println(arg1 + " + " + arg2 + " = " + sum);
		} catch (Exception e) {
			System.err.println("Error calling Calulator: " + code.intValue());
		}
		
		// unload the program
		try {
			RuntimeSystem.cobcancel(dll);
		} catch (Exception ex) {
			System.err.println("Error closing Calculator");
			return;
		}
	}
		
}
