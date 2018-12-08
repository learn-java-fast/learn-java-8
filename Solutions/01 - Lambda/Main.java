package com.example.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Main {
	class Customer {
		String account;
		String state;
		Customer(String account, String state) {
			this.account = account;
			this.state = state;
		}
	}
	interface ShowCustomer {
		void show(Customer t);
	}
	// inner class
	List<Customer> customers = Arrays.asList(new Customer[] {new Customer("123","TX"), new Customer("234","AZ"), new Customer("345","WA")});


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime = System.nanoTime();
		//System.out.println("In main");
		Main main = new Main();
		main.process();
		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime-startTime));
	} 
	public  Main() {
		//System.out.println("Constructor");
	}
	
	public void checkCustomerList(List<Customer> customers, Predicate<Customer> predicate) {
		for(Customer customer:customers){
			if (predicate.test(customer)) {
				System.out.println("Account "+ customer.account+ " state "+customer.state);
			}
		}
	}

	
	public void process() {
		// create instance of lambda expression
		checkCustomerList(customers, (n) -> "TX".equals(n.state));
		checkCustomerList(customers, (n) -> "WA".equals(n.state));
	}
}
