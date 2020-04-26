package com.task;

import java.util.HashMap;
import java.util.Map;

public class ExampleQuestion {

	public static void main(String[] args) {
		
		Pair ticket2 = new Pair("BOS", "SFO");
		Pair ticket3 = new Pair("SFO", "JFK");
		Pair ticket1 = new Pair("SOF", "BOS");
		Pair[] tickets = {ticket2, ticket3, ticket1};
		withDirection(tickets);
	}

	public static void wihtoutDirection(Pair[] pairs) {
		
		//Map<String, List<Pair>> map = new HashMap<>();
		
		//sfo:  sfo->jfk, sfo->bos
		//jfk:  jfk->sfo
		//bos:  bos->sfo, bos->sof
		//sof:  sof->bos
		
		// the ones with 1 element in the queue are possible starts
	}
	
	public static void withDirection2(Pair[] pairs) {
		
		//Map<String, List<Pair>> map = new HashMap<>();
		
		// bos : bos->sfo, sof->bos
		// sfo : bos->sfo; sfo->jfk
		// jfk : sfo->jfk
		// sof : sof->bos
		
		// ot tezi, koito imat po 1 element v list, vzemi tozi, kudeto kliu4at e starting node,
		// vurti ostanalite i vzemi tozi element, kudto kliu4at e ending node
		
	}
	
	// time O(n), space O(n)
	// WRONG SOLUTION
	public static void withDirection(Pair[] pairs) {
		
		Map<String, Pair> starts = new HashMap<>();
		Map<String, Pair> ends = new HashMap<>();
		
		for(Pair p : pairs) {
			String s = p.start;
			String e = p.end;
			if(!ends.containsKey(s)) {
				starts.put(s, p);
				ends.put(e, p);
			}
			else {
				ends.put(e, p);
			}
			if(starts.containsKey(e)) {
				starts.remove(e);
			}
		}
		System.out.println("s");
	}
	
	
	
	private static class Pair {
		private String start;
		private String end;

		Pair(String start, String end) {
			super();
			this.start = start;
			this.end = end;
		}
		
		@Override
		public String toString() {
			return "[" + start + " -> " + end + "]";
		}
		
	}
	
}
