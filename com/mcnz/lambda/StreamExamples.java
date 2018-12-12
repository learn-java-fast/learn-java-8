package com.mcnz.lambda.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExamples {
	
	public static void main(String args[]) {

		
		ArrayList<Gift> gifts = new ArrayList<Gift>();
		gifts.add(new Gift(12, "Console", 299.99));
		gifts.add(new Gift(19, "Fortnite Pack", 99.89));
		gifts.add(new Gift(21, "Doll House", 99.99));
		gifts.add(new Gift(13, "Owl Magazine", 99.99));
		gifts.add(new Gift(1, "Chocolate Orange", 2.99));
		gifts.add(new Gift(99, "Million Dollar Man", 22.99));
		
		/* This code increases all prices by 10% */
		gifts.forEach( (Gift gift) -> gift.cost=gift.cost*1.1);
		gifts.forEach( (Gift gift) -> System.out.println(gift));
		/* The above line of code could be written with a method reference.
		 * Which one to you like better?*/
		gifts.forEach(System.out::println);
		
		/* The forEach is actually implemented with a stream. 
		 * You can call it through the stream() method:
		 */
		gifts.stream().forEach( (Gift gift) -> gift.id = gift.id + 10);
		gifts.forEach( (Gift gift) -> System.out.println(gift));
		/* T A S K :: write the line of code above using a method reference */
		
		/* You can create new lists by filtering an existing list as a stream.
		 * Here's how to get all of the gifts that are $99.99.
		 */
		
		List<Gift> gretzkyGifts = gifts.stream().filter((Gift g) -> (g.getCost()==99.99)).collect(Collectors.toList());
		
		/* T A S K 1 :: Write your own filter that gets a list of gifts that cost less than $99 */
		
		List<Gift> cheaperGifts = null;
		
		/* Task 2 :: You can add a findFirst() method after a filter and instead of the collect. 
		 * Do that to find the first element that costs 99.99
		 */
		
		Gift firstGreatGift = null;
		
		/* Task 3 :: Add the following orElse after the findFirst call:
		 * orElse(new Gift(199, "Computer Chess", 99.88));
		 */
		
		Gift orElseGift = null;
		
		/* T A S K :: Instead of calling findFirst, just call .count() after the filter to see how many records are returned */
		
		Long numberOfCheapGifts = null; //.count();

		
		/* You can use a stream to average the cost all the gifts */
		Double averageCost = gifts.stream().mapToDouble( (Gift g) -> g.getCost() ).average().getAsDouble();
		
		/* T A S K :: Think you're clever? Re-write the lambda in the above code using a method reference. 
		 * Hint - Gift::xxxCxxx
		 */
		
		/* T A S K :: You can see the stream above getting the average cost. Your job is to now calculate
		 * what the TOTAL cost of buying ALL gifts would be.
		 */
		Double totalCost = 0d;

		/* T A S K :: streams have skip(int) and limit(int) methods.
		 * What would the cost of buying the 2nd, 3rd and 4th item be?
		 * Calculate the sum of the stream using skip and limit
		 */
		Double costOfThreeItems = 0d;
		
		
		/* Which of the two sorting algorithms do you like best? */
		List sortedGiftsA = gifts.stream().sorted((g1, g2) -> (g1.getCost().compareTo(g2.getCost()))).collect(Collectors.toList());
		List sortedGiftsB = gifts.stream().sorted((g1, g2) -> (Double.compare(g1.getCost(), g2.getCost()))).collect(Collectors.toList());
		
		
		
		Stream<Integer> randomDigits = Stream.of(232, 434, 252, 656, 112, 343);
		randomDigits.sorted((Integer a, Integer b) -> Integer.compare(a, b));
		/* T A S K :: Think you're clever? Rewrite the above line of code using a method reference.
		 * The code is a whole lot simpler than you probably think it is.
		 */
		
		
		Stream<Integer> indexes = Stream.of(1,3,5);
		
		List<Integer> listOfIndexes = indexes.collect(Collectors.toList());
		listOfIndexes.forEach(System.out::println);
		
		/* T A S K :: With the line of code below compile if uncommented? Will it run? */
		//List<Integer> secondListOfIndexes = indexes.collect(Collectors.toList());
		
		System.out.println("Odd Gifts");
		List<Gift> oddGifts = listOfIndexes.stream().map( (Integer i) -> gifts.get(i)).collect(Collectors.toList());
		oddGifts.forEach(Gift::printGift);
		
		/* T A S K :: Replace the lambda expressions in the two lines of code above with method references. */


		/* T A S K :: There are methods called  allMatch, anyMatch and noneMatch? What is the difference between them.
		 * Use anyMatch in a code snippet.
		 */

	}

}


class Gift{
	
	public static void printGift(Gift g) {
		System.out.println(g.toString());
	
	}
	
	public Gift(int i, String n, double c) {
		id = i;
		name = n;
		cost = c;
	}
	
	@Override
	public String toString() {
		return "Gift [id=" + id + ", name=" + name + ", cost=" + cost + "]";
	}

	int id;
	String name;
	Double cost;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Double getCost() {
		return cost;
	}
	
}
