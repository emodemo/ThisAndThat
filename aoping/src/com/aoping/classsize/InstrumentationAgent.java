package com.aoping.classsize;

import java.lang.instrument.Instrumentation;

public class InstrumentationAgent {

	private static volatile Instrumentation globalInstrumentation;
	
	public static void premain(final String agentArgs, final Instrumentation instrumentation) {
		globalInstrumentation = instrumentation;
	}
	
	public static long getObjectSize(final Object object) {
		return globalInstrumentation.getObjectSize(object);
	}
	
	
	// create jar with this class and manifest premain-class:fqname entry
	// run the SizeOfTest with : -javaagent:"fullpath to jar"
	
}
