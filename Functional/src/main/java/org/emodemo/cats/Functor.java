package org.emodemo.cats;

import java.util.function.Function;

interface Functor<T, F extends Functor<?, ?>> {
	<R> F map(Function<T, R> mapper);
	// TODO: compose
}

interface Empty<F extends Empty<?>> {
	F empty(); // could be a static method instead. See java optionals
}

/** Applicative without pure() */
interface Apply<F extends Function<?, ?>, T extends Apply<?, ?>> {
	/** must accept an Effect<Function<T,R>> only, buj Java is not quite happy with Higher Kinded Types */
	T ap(Apply<F, T> effectMapper);
}

interface Applicative<T, F extends Applicative<?, ?>> extends Functor<T, F> //, Apply<WTF>
{
	/** pure (a.k.a. unit(), of(), from() */
	F pure(T value);
}

/** Monad without Pure */
interface FlatMap<T, F extends FlatMap<?, ?>> {
	//<R extends FlatMap<?, ?>> F flatmap(Function<T, FlatMap<>> mapper);
	// map, then flatten
}

interface Monad<T extends Monad<?>> {  // extends Applicative, FlatMap
	/**
	 * flatten (a.k.a. join())
	 * takes a value in a nested context F<F<A>> and "joins" the contexts together so that we have a single context F<A>
	 */
	T flatten(T ... monads); // not quite like this
}

class Maybe<T> implements
		  Empty<Maybe<T>>
		, Applicative<T, Maybe<?>>
{

	private final T value;

	Maybe(T value){
		this.value = value;
	}

	@Override
	public Maybe<T> empty() {
		return new Maybe<>(null);
	}

	@Override
	public Maybe<T> pure(T value) {
		return new Maybe<>(value);
	}

	@Override
	public <R> Maybe<R> map(Function<T, R> mapper) {
		if(value == null) return new Maybe<>(null); //empty();
		return new Maybe<>(mapper.apply(value));
	}

	// cannot make Apply generic enough :), to work and to allow the IDE to autogenerate
	public <R> Maybe<R> ap(Maybe<Function<T, R>> effectOfMappers){
		return effectOfMappers
				.map(mapper -> mapper.apply(value));
		// or else
	}

//	@Override
//	public Maybe<T> flatten(Maybe<T> ... monads) {
//		return null; // impl specific this is
//	}
}

//class Maybe2<T> extends Apply<T, Maybe2<?>> {
//
//	@Override
//	public <R> Maybe2<R> ap(Maybe2<Function<T, R>> effectMapper) {
//		return null;
//	}
//}

record Try<T>(T value) implements Empty<Try<?>>, Functor<T, Try<?>> {

	@Override
	public Try<T> empty() {
		return new Try<>(null);
	}

	@Override
	public <R> Try<R> map(Function<T, R> mapper) {
		try{
			return new Try<>(mapper.apply(value));
		} catch (Exception ex){
			return new Try<>(null); //empty();
		}
	}
}
