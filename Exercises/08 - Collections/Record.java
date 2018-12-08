package com.example;


import java.time.LocalDate;

public class Record implements Comparable {
	private int year;
	private int quarter;
	private int month;
	private int dayOfMonth;
	private int dayOfWeek;
	private LocalDate flightDate;
	private String carrier;
	private String origin;
	private String destination;
	private String departureTime;
	private String delay;
	private String cancelled;
	private Record() {
		
	}
	public static Record createRecord(String year, String quarter, String month, String dayOfMonth, String dayOfWeek, String flightDate, String carrier, String origin, String destination, String departureTime, String delay, String cancelled) {
		Record record = new Record();
		record.year = Integer.parseInt(year);
		record.quarter = Integer.parseInt(quarter);
		record.month = Integer.parseInt(month);
		record.dayOfMonth = Integer.parseInt(dayOfMonth);
		record.dayOfWeek = Integer.parseInt(dayOfWeek);
		record.flightDate = LocalDate.parse(flightDate);
		record.carrier = carrier;
		record.origin = origin;
		record.cancelled = cancelled;
		return record;
	}
	public int getYear() {
		return year;
	}
	public int getQuarter() {
		return quarter;
	}
	public int getMonth() {
		return month;
	}
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public LocalDate getFlightDate() {
		return flightDate;
	}
	public String getCarrier() {
		return carrier;
	}
	public String getOrigin() {
		return origin;
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return flightDate.compareTo(((Record)o).flightDate);
	}
	public String getDestination() {
		return destination;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public String getDelay() {
		return delay;
	}
	public String getCancelled() {
		return cancelled;
	}
	
}
