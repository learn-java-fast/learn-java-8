package com.example.mypackage;

public class DataFormatException extends InputDataException {

	private static final String DATA_MUST_BE_AN_INTEGER = "Data must be an integer";

	public DataFormatException(String input) {
		super(DATA_MUST_BE_AN_INTEGER, input);
	}
}
