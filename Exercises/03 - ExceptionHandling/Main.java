package com.example.mypackage;

import java.io.File;

public class Main {
	public static void main (String [] args) {
		if (args.length > 0) {
			new ShowNumbers().showNumbers(args);
			new ShowNumbers().writeList();
			ShowNumbers.echoFile(new File("resources/numberslist.txt"));
		}
	}

}
