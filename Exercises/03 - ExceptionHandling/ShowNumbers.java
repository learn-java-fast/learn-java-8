package com.example.mypackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
		int[] A = new int[0];
		A[0] = 0;
		for (int i = 0; i < strings.length; i++) {
			Integer inInt = Integer.parseInt(strings[i]);
			logger.info(""+inInt.intValue());;
		}
	}
 	 public void writeList() {
         PrintWriter out = null;
  
         try {
             System.out.println("Entering try statement");
             out = new PrintWriter(new FileWriter("OutFile.txt"));
          
             for (int i = 0; i < SIZE; i++)
                 out.println("Value at: " + i + " = " + list.get(i));
         } catch (IndexOutOfBoundsException e) {
             System.err.println("Caught IndexOutOfBoundsException: " +
                                  e.getMessage());
         } catch (IOException e) {
             System.err.println("Caught IOException: " + e.getMessage());
         } finally {
             if (out != null) {
                 System.out.println("Closing PrintWriter");
                 out.close();
             } else {
                 System.out.println("PrintWriter not open");
             }
         }
     }
	public static void echoFile(File file) {
	    RandomAccessFile input = null;
	    String line = null;

	    try  {
	        input = new RandomAccessFile(file, "r");
	        while ((line = input.readLine()) != null) {
	        	Integer value = new Integer(line);
				logger.info(""+value.intValue());;
	        }
	        return;
	    } finally {
	        if (input != null) {
	            input.close();
	        }
	    }
	}
}
