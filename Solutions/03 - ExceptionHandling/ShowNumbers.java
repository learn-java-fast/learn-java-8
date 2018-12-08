package com.example.mypackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.example.mypackage.DataFormatException;
import com.example.mypackage.InputDataException;
import com.example.mypackage.InvalidDataException;

public class ShowNumbers {
	private static Logger logger = Logger.getLogger(ShowNumbers.class.getName());
	private List<Integer> list;
    private static final int SIZE = 10;
 	public ShowNumbers() {
 		list = new ArrayList<Integer>(SIZE);
        for (int i = 0; i < SIZE; i++)
        	list.add(new Integer(i));
    }
 	
 	public void showNumbers(String[] strings) {
//		int[] A = new int[0];
//		A[0] = 0;
		for (int i = 0; i < strings.length; i++) {
			try {
				displayStringAsInteger(strings[i]);
			} catch (InputDataException e) {
				logger.warning(e.getMessage()+" found for "+e.getInputData()+ " on parameter "+i);
			}
		}
	}
 
 	public void writeList() {
         try (PrintWriter out = new PrintWriter(new FileWriter("OutFile.txt"))){
             System.out.println("Entering try statement");
       
             for (int i = 0; i < SIZE; i++)
                 out.println("Value at: " + i + " = " + list.get(i));
         } catch (IndexOutOfBoundsException e) {
             System.err.println("Caught IndexOutOfBoundsException: " +
                                  e.getMessage());
         } catch (IOException e) {
             System.err.println("Caught IOException: " + e.getMessage());
         }
 	 }
 	 
 	public static void echoFile(File file) {
 		String line = null;
		int lineCount = 1;
	    try (RandomAccessFile input = new RandomAccessFile(file, "r")) {
	    	while ((line = input.readLine()) != null) {
	    		try {
	        		displayStringAsInteger(line);
	    		} catch (InputDataException e) {
	    			logger.warning(e.getMessage() + " found for " + e.getInputData() + " on line " + lineCount + " in file " + file.getName());
	    		} 
	    	    lineCount++;
	        }
	        return;
	    } catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
 	}
 	 
 	private static void displayStringAsInteger(String string) throws InputDataException{
 		try {
 			Integer inInt = Integer.parseInt(string);
 			logger.info(""+inInt.intValue());;
 		} catch (NumberFormatException e) {
 			logger.fine("Unable to parse input: "+string);
 			if(string.matches("")) {
 				throw new DataFormatException(string);
 			} else {
 				throw new InvalidDataException(string);
 			}
 		}
 	}
}