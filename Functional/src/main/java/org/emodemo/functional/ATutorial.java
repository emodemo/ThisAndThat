package org.emodemo.functional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

//import static org.emodemo.functional.Result.apply;
import java.util.function.Function;

import static org.emodemo.functional.Result.bind;
import static org.emodemo.functional.Result.bind2;
//import static org.emodemo.functional.Result.pure;

public class ATutorial {

	public static void main(String[] args) {


		// 1
		System.out.println(enthuse("hello world"));
		// 2
		System.out.println(enthuse2("hello world"));
		// 3
		System.out.println(enthuse3("hello world").getResult());
		// 4
		System.out.println(enthuse4_2 ( "  hello world  " ));
		System.out.println(enthuse4_2 ( "   " ));
		System.out.println(enthuse4_2 ( "hello 123" ));
		System.out.println(enthuse4_2 ( "Krungthepmahanakhon is the capital of Thailand" ));
		System.out.println(enthuse5("one"));
		System.out.println(enthuse5("three"));
		System.out.println(enthuse6("one"));
		System.out.println(enthuse6("three"));
		System.out.println(enthuse6_2("hello world"));
		System.out.println(enthuse6_2 ( "hello 123" ));
	}

	// ON MONADS, where Result is the Monad
	// 1: a simple example
	static String enthuse(String sentence){
		return sentence.trim().toUpperCase().concat("!");
	}

	// 2: use 3 pure functions
	static String trim(String string){ return string.trim(); }
	static String toUpperCase(String string) {return string.toUpperCase(); }
	static String appendExclamation(String string) {return string.concat("!"); }
	static String enthuse2(String sentence){
		return appendExclamation(toUpperCase(trim(sentence))); // return f(g(h(x)))
	}

	// 3: handle errors
	static Result<String, SimpleError> trim3(String string){
		String result = string.trim();
		if(result.isEmpty()) return Result.failure(new SimpleError("String must contain non-space characters"));
		return Result.success(result);
	}
	static Result<String, SimpleError> toUpperCase3(String string) {
		if(string == null || ! string.matches ( "[a-zA-Z ]+" )) return Result.failure(new SimpleError("String must contain only letters and spaces."));
		return Result.success(string.toUpperCase());
	}
	static Result<String, SimpleError> 	appendExclamation3(String string) {
		if(string == null || string.length() > 20) return Result.failure(new SimpleError("String must not exceed 20 characters."));
		return Result.success(string.concat("!"));
	}
	static Result<String, SimpleError> enthuse3(String sentence){
		Result<String, SimpleError> trim3 = trim3(sentence);
		if(trim3.isFailure()) return trim3;

		Result<String, SimpleError> toUpperCase3 = toUpperCase3(trim3.getResult());
		if(toUpperCase3.isFailure()) return toUpperCase3;

		return appendExclamation3(toUpperCase3.getResult());
	}

	// 4: bind functions together
	// if trim3() returns a string, then toUpperCase3() can be called, but if it returns an error, toUpperCase3() cannot be called
	// => make trim3() returning a Result (done in previous example), add bind() function to accept this Result and toUpperCase3() as function
	// (i.e.) if(result.isFailure()) and doSomething(result.getResult()) are hidden in the bind() function;
	static Result<String, SimpleError> enthuse4(String sentence){
		Result<String, SimpleError> trim3 = trim3(sentence);
		Result<String, SimpleError> upperCase3 = bind(trim3, ATutorial::toUpperCase3);
		// equivalent to: bind(trim3, string -> toUpperCase3(string));
		Result<String, SimpleError> result = bind(upperCase3, ATutorial::appendExclamation3);
		return result;
	}

	static Result<String, SimpleError> enthuse4_2(String sentence){
		return bind(bind(
				trim3(sentence),
				trimmed -> toUpperCase3(trimmed)),
				upper -> appendExclamation3(upper));
	}

	// 5: but what if binding Result<Integer, Error> with Result<String, Error>
	// bind cannot handle it because the output type are different.

	static Result<String, SimpleError> stringify(Integer integer){
		switch (integer) {
			case 1: return Result.success("one");
			case 2: return Result.success("two");
			default: return Result.failure(new SimpleError("must be 1 or 2"));
		}
	}
	static Result<Integer, SimpleError> numberise(String string){
		switch (string) {
			case "one": return Result.success(1);
			case "two": return Result.success(2);
			default: return Result.failure(new SimpleError("must be one or two"));
		}
	}

	static Result<String, SimpleError> enthuse5(String sentence){
		return bind2(
				numberise(sentence), number -> stringify(number));
	}

	// 6: to OOP
	static Result<String, SimpleError> enthuse6(String sentence){
		return numberise(sentence).bindOOP(number -> stringify(number));
	}

	static Result<String, SimpleError> enthuse6_2(String sentence){
		return trim3(sentence)
				.bindOOP(trimmed -> toUpperCase3(trimmed)
				.bindOOP(upper -> appendExclamation3(upper)));
	}

}

@RequiredArgsConstructor
@Getter
@ToString
class SimpleError {
	private final String info;
}