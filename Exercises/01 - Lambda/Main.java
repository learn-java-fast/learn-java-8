package com.example.lambda;

import java.util.Arrays;
import java.util.List;

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
	class CustomerProcessor implements ShowCustomer {

		@Override
		public void show(Customer t) {
			// TODO Auto-generated method stub
			System.out.println("Account "+ t.account+ " state "+t.state);
		}
		
	}
	List<Customer> customers = Arrays.asList(new Customer[] {new Customer("123","TX")});

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		//System.out.println("In main");
		Main main = new Main();
		main.process();
		long endTime = System.currentTimeMillis();
		System.out.println("Took "+(endTime-startTime));
	} 
	public  Main() {
		//System.out.println("Constructor");
	}
	public void process() {
		// create instance of inner class
		ShowCustomer show = new CustomerProcessor();
		String state = "TX";
		for (Customer customer : customers) {
			if (state.equals(customer.state) ) {
			show.show(customer);
			}
		}
	}

}
