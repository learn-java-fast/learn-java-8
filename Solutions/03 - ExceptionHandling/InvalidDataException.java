package com.example.mypackage;

public class InvalidDataException extends InputDataException {

	private static final String INVALID_INPUT_DATA = "Invalid Input Data";

	public InvalidDataException( String input) {
		super(INVALID_INPUT_DATA, input);
		// TODO Auto-generated constructor stub
	}
}
