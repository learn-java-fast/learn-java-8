package com.mcnz.lambda.example;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleStreams {

	public static void main(String[] args) {
		Integer[] arrayOfIntegers = {
			    new Integer(111), 
			    new Integer(22), 
			    new Integer(345)
		};
		
		/* To work with collections of data, convert the collections into streams. 
		 * The following code turns an array into a stream.*/
		Stream<Integer> integerStream = Stream.of(arrayOfIntegers);
		
		
		/* You can even take a random set objects and make a stream. */
		Stream<String> names = Stream.of("joe", "Jenny", "Julie");
		
		/* With a stream, you can call various lambda expressions on them. */
		
		names.forEach((String name) -> System.out.println(name));
		integerStream.forEach((Integer i) -> System.out.println(i));
		
		/* Don't forget that when both sides of the lambda just operate on the same argument type,
		 * you can just use a method reference.
		 */
		
		Stream.of("joe", "Jenny", "Julie").forEach(System.out::println);
		Stream.of(arrayOfIntegers).forEach(System.out::println);
		
		/* By the way, there's a reason I had to use Stream.of above. Do you know why? */
		
		/* Streams have methods that allow you to turn them into collection classes such as lists or sets. */
		
		List<String> listOfName = Stream.of("joe", "Jenny", "Julie").collect(Collectors.toList());
		List<Integer> listOfDigits = Stream.of(arrayOfIntegers).collect(Collectors.toList());
		
		/* With a stream, you can easily filter a list down. Here's all the integers less than 100.*/
		
		List<Integer> smallDigits = listOfDigits.stream().filter((Integer i) -> i<100).collect(Collectors.toList());
		
		/* You can even do it in parallel! */
		
		List<Integer> smallDigitsInParallel = listOfDigits.stream().parallel().filter((Integer i) -> i<100).collect(Collectors.toList());
		
		/* Or maybe you wanted a count of all the integers less than 100.*/
		
		Long count = listOfDigits.stream().filter((Integer i) -> i<100).count();
		
		/* Or maybe you just want the first one in the list that is less than 100 */
		
		Integer firstOneLessThan1000 = listOfDigits.stream().filter((Integer i) -> i<1000).findFirst().orElse(new Integer(1));
		
		/* Or maybe you want to sum them all. */
		
		int sum   = listOfDigits.stream().mapToInt( (Integer i) -> i.intValue()).sum();
		
		/* Method references always look so much better. */
		
		int sum41 = listOfDigits.stream().mapToInt(Integer::intValue).sum();
		
		/* Want to skip the second element in the stream? You can do that too! */
		
		int summer = listOfDigits.stream().skip(2).mapToInt(Integer::intValue).sum();
		

	}

}
