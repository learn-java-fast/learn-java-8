package com.example.mypackage;

public class InputDataException extends Exception {
	String inputData;

	public String getInputData() {
		return inputData;
	}

	public void setInputData(String inputData) {
		this.inputData = inputData;
	}

	public InputDataException() {
		super();
		inputData="Input Data Not Captured";
	}

	public InputDataException(String message, String input) {
		super(message);
		inputData = input;
	}

}
