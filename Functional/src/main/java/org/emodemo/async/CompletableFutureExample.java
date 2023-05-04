package org.emodemo.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class CompletableFutureExample {
	public static void main(String[] args) {
		CompletableFutureExample app = new CompletableFutureExample();
		app.runMe4();
		app.runMe3();
		app.runMe2();
		app.runMe();
		app.runMe5();
	}

	public void runMe() {
		// each async call wil be run in a separate thread.
		CompletableFuture<Integer> future1 = supplyAsync(() -> callable(1, 1));
		CompletableFuture<Integer> future5 = supplyAsync(() -> callable(5, 1));
		// then process the result of future1's computation, and get another value
		CompletableFuture<Integer> future2 = future1.thenApplyAsync(i -> callable(2, i));
		// or process the result of future1's computation, and do not return a value
		CompletableFuture<Void> future3 = future1.thenAcceptAsync(i -> runnable(3, i));
		// or do not need the return value, or the result
		CompletableFuture<Void> future4 = future1.thenRunAsync(() -> runnable(4, 1));
		CompletableFuture<Void> future6 = future5.thenRunAsync(() -> runnable(6, 1));
	}

	public void runMe2() {
		// combine asks for another future to combine with, and a function to map both results
		CompletableFuture<Integer> future9 = supplyAsync(() -> callable(7, 1))
				.thenCombineAsync(supplyAsync(() -> callable(8, 1)), this::callable);

		CompletableFuture<Void> future12 = supplyAsync(() -> callable(10, 1))
				.thenAcceptBothAsync(supplyAsync(() -> callable(11, 1)), this::callable);
	}

	public void runMe3() {
		CompletableFuture<Integer> future14 = supplyAsync(() -> callable(13, 1))
				.thenComposeAsync(this::toFuture);
	}

	public void runMe4() {
		// wait for all of them to finish, but have no result
		CompletableFuture<Void> all = CompletableFuture.allOf(
				supplyAsync(() -> "Hello"),
				supplyAsync(() -> " Java"),
				supplyAsync(() -> " World!"));
		try {
			all.get(); // wait for al of them to finish
			// assert that all 3 futures are isDone(), but I need to set them to local vars
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		// same idea, but get the result too
		// CompletableFuture::join will return the action to the main (or calling) thread
		String collect = Stream.of(supplyAsync(() -> "Hello"), supplyAsync(() -> "Java"), supplyAsync(() -> "World!"))
				.map(CompletableFuture::join)
				.collect(Collectors.joining(" "));
		System.out.println(collect);
	}

	public void runMe5(){
		String name = null; // "there!";
		CompletableFuture<String> future = supplyAsync(() -> {
			if (name == null) throw new RuntimeException("Computation Error");
			return "Hello, " + name;
		}).handle((string, throwable) -> string != null ? string : "Hello, Stranger!");
		try {
			System.out.println(future.get()); // wait for al of them to finish
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	private int callable(int a, int b) {
		System.out.println(Thread.currentThread().getName() + "  - callable - " + a + " - " + b);
		return a + b;
	}

	private void runnable(int a, int b) {
		System.out.println(Thread.currentThread().getName() + "  - runnable - " + a + " - " + b);
	}

	private CompletableFuture<Integer> toFuture(int i) {
		return supplyAsync(() -> callable(i, 1));
	}
}

