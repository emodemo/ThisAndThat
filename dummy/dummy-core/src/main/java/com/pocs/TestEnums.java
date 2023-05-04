package com.pocs;


public class TestEnums {

	public static void main(String[] args) {

		String myString = "my-json-aaa.txt";
		String myString2 = "my-json-aaa.json";
		String myString3 = "my.json";

		System.out.println(myString.replaceAll("\\.json", ""));
		System.out.println(myString2.replaceAll("\\.json", ""));
		System.out.println(myString3.replaceAll("\\.json", ""));

		String validStr = "valid";
		System.out.println(validStr.substring(0, 0));
		System.out.println(validStr.substring(0, 1));
		System.out.println("===============");
		for(int i = 0; i < validStr.length(); i++){
			System.out.println(validStr.substring(0, i) + " " + validStr.substring(1+i));
		}
		
		
		String invalidStr = "invalid";
		State valid = State.VALID;
		State valid2 = State.VALID;
		State invalid = State.INVALID;

		System.out.println(invalid == valid);
		System.out.println(valid2 == valid);
		//System.out.println(valid == validStr);
		System.out.println(valid.equals(validStr));
		System.out.println(valid2.equals(validStr));
		System.out.println(invalid.equals(validStr));
		System.out.println(invalid.equals(invalidStr));

	}


	enum State {

		VALID("valid"),
		INVALID("invalid");

		private final String value;

		State(String value) {
			this.value = value;
		}
	}
}
