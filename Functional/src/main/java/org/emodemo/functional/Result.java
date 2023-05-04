package org.emodemo.functional;

import lombok.ToString;

import java.util.function.Function;

@ToString
public class Result<U, E> {

	private final U result;
	private final E error;

	private Result(U result, E error){
		this.result = result;
		this.error = error;
	}

	public static <U, E> Result<U, E> success(U result){
		return new Result<U, E>(result, null);
	}
	public static <U, E> Result<U, E> failure(E error){
		return new Result<U, E>(null, error);
	}

	// === Monad ===

	// flatMap
	// compose two functions that cannot be composed directly
	// e.g extract the if() and the application of the 2nd method here
	public static <U, E> Result<U, E> bind(Result<U, E> value, Function<U, Result<U, E>> function){
		if(value.isFailure()) return value;
		return function.apply(value.getResult());
	}
	// flatMap better
	// handle two different Results (from Result<U> to Result<V>)
	public static <U, V, E> Result<V, E> bind2(Result<U, E> value, Function<U, Result<V, E>> function){
		if(value.isFailure()) return Result.failure(value.getError());
		return function.apply(value.getResult());
	}
	// flatMap OOP
	// (from Result<U> to Result<V>)
	public <V> Result<V, E> bindOOP(Function<U, Result<V, E>> function){
		if(isFailure()) return Result.failure(error);
		return function.apply(result);
	}

	public <V> Result<V, E> mapOOP(Function<U, V> function){
		if(result == null) return Result.failure(error);
		else return Result.success(function.apply(result));
	}

	// === Functor ===
	// Maps between categories, where the input function is the mapping one of
	// the categories and the output is the result in the second category.
	// The output can be also a mapping function in the second category.
	// It is better that the input value to be mapped is in the same category as the function,
	// but it could be also in the resulting category.
	public static <U, V, E> Result<V, E> map(U value, E error, Function<U, V> function){
		if(value == null) return Result.failure(error);
		else return Result.success(function.apply(value));
	}



	// === Applicative ===
	// pure & apply
	// Result<A> pure(A a) = Result.success(a);
	// Result<B> apply(Result<A> a, function(Result<A>, Result<B> f)  // wrong ???
	// Result<B> apply(Result<A> a, function(Result<A>, Result<B> f)  // wrong


	public boolean isSuccess() {
		return result != null;
	}

	public boolean isFailure() {
		return result == null;
	}

	public U getResult(){
		return result;
	}

	public E getError(){
		return error;
	}
}
