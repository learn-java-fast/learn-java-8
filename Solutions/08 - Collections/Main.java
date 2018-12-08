package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Logger;

public class Main {
	static Logger logger = Logger.getLogger(Main.class.getName());	
	private static final int YEAR_COLUMN = 0;
	private static final int QUARTER_COLUMN=1;
	private static final int MONTH_COLUMN=2;
	private static final int DAY_OF_MONTH_COLUMN=3;
	private static final int DAY_OF_WEEK_COLUMN=4;
	private static final int FLIGHT_DATE_COLUMN = 5;
	private static final int CARRIER_COLUMN=6;
	private static final int FLIGHT_NUM_COLUMN = 8;
	private static final int ORIGIN_AIPORT_SEQUENCE_COLUMN = 10;
	private static final int DESTINATION_AIPORT_SEQUENCE_COLUMN = 17;
	private static final int ORIGIN_COLUMN = 12;
	private static final int DESTINATION_COLUMN = 19;
	private static final int DEPARTURE_TIME_COLUMN=23;
	private static final int DELAY_COLUMN=24;
	private static final int CANCELLED_COLUMN=29;

	public static void main(String[] args) {
		readToArrayList();
		readToHashMap();
	}
	private static void readToArrayList() {
		// TODO Auto-generated method stub
		LocalDateTime start = LocalDateTime.now();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("./resources/885977206_T_ONTIME.csv"))))) {
			String firstline = br.readLine();
// Step 2 goes here
			List<Record> records = new ArrayList<Record>();
//
			int recordsRead = 0;
			while(true) {
				String line = br.readLine();
				if (line != null) {
					recordsRead++;
					String[] columns = line.replace("\"", "").split(",");
// Step 3 goes here
					Record record = Record.createRecord(columns[YEAR_COLUMN], columns[QUARTER_COLUMN], 
							columns[MONTH_COLUMN],columns[DAY_OF_MONTH_COLUMN], columns[DAY_OF_WEEK_COLUMN], 
							columns[FLIGHT_DATE_COLUMN], columns[CARRIER_COLUMN], columns[ORIGIN_COLUMN], 
							columns[DESTINATION_COLUMN], columns[DEPARTURE_TIME_COLUMN], columns[DELAY_COLUMN], 
							columns[CANCELLED_COLUMN]);
					records.add(record);
//
				} else {
					break;
				}
			}
// Step 4 goes here
//			LocalDateTime startSort = LocalDateTime.now();
//			Collections.sort(records, (Record r1, Record r2)-> r1.getFlightDate().compareTo(r2.getFlightDate()));
//			logger.info("Records sort: "+ records.size()+ " duration: "+Duration.between(startSort, LocalDateTime.now()));
//

// Step 5 goes here			
			LocalDateTime startFind = LocalDateTime.now();
			ArrayList<Record> foundRecords = new ArrayList<Record>();
			Record foundRecord = findRecord(records,"5640","SBA", "SFO","2015-01-29","1229","0.00");
			if (foundRecord != null) {
				foundRecords.add(foundRecord);
				logger.info("Found record ");
			}
			foundRecord = findRecord(records, "742","SEA", "SMF","2015-01-09","0705","0.00");
			if (foundRecord != null) {
				foundRecords.add(foundRecord);
				logger.info("Found record ");
			}
			foundRecord = findRecord(records,"5640","SBA", "SFO","2015-01-29","1229","0.00");
			if (foundRecord != null) {
				foundRecords.add(foundRecord);
				logger.info("Found record ");
			}
//
			
// Step 6 goes here			
			LocalDateTime startDelete = LocalDateTime.now();
			records.removeAll(foundRecords);
//
			
			LocalDateTime now = LocalDateTime.now();
			logger.info("Records read: "+ recordsRead + " results count: "+ records.size() 	// <-- for step 2
			+ " duration: "+Duration.between(start, now)									// <-- for step 2
			+ " Find took "+ Duration.between(startFind, now)								// <-- for step 5
			+ " Delete took "+ Duration.between(startDelete, now)							// <-- for step 6
			);																				// <-- for step 2
			
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Record findRecord(List<Record> records, String flightNumber, String origin, String destination, String flightDate, String departureTime, String cancelled) {
		Record foundRecord = null;
		return foundRecord;
	}

	private static void readToHashMap() {
		// TODO Auto-generated method stub
		LocalDateTime start = LocalDateTime.now();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("./resources/885977206_T_ONTIME.csv"))))) {
			String firstline = br.readLine();
// Step 9 goes here
			SortedMap<String, Record> records = new ConcurrentSkipListMap<String,Record>();
//			
			int recordsRead = 0;
			while(true) {
				String line = br.readLine();
				if (line != null) {
					recordsRead++;
					String[] columns = line.replace("\"", "").split(",");
// Step 10 goes here
					Record record = Record.createRecord(columns[YEAR_COLUMN], columns[QUARTER_COLUMN], 
							columns[MONTH_COLUMN],columns[DAY_OF_MONTH_COLUMN], columns[DAY_OF_WEEK_COLUMN], 
							columns[FLIGHT_DATE_COLUMN], columns[CARRIER_COLUMN], columns[ORIGIN_COLUMN], 
							columns[DESTINATION_COLUMN], columns[DEPARTURE_TIME_COLUMN], columns[DELAY_COLUMN], 
							columns[CANCELLED_COLUMN]);
					String key = buildKey(columns);
					records.put(buildKey(columns), record);				
//
				} else {
					break;
				}
			}
			
// Step 11 goes here
			LocalDateTime startFind = LocalDateTime.now();
			ArrayList<String> foundKeys = new ArrayList<String>();
			String key = "";
			Record foundRecord = records.get(key=buildKey("5640","SBA", "SFO","2015-01-29","1229","0.00"));
			if (foundRecord != null) {
				foundKeys.add(key);
			}
			foundRecord = records.get(key=buildKey("742","SEA", "SMF","2015-01-09","0705","0.00"));
			if (foundRecord != null) {
				foundKeys.add(key);
			}
			foundRecord = records.get(key=buildKey("5640","SBA", "SFO","2015-01-29","1229","0.00"));
			if (foundRecord != null) {
				foundKeys.add(key);
			}
//
// Step 12 goes here
			LocalDateTime startDelete = LocalDateTime.now();
			for (Iterator iterator = foundKeys.iterator(); iterator.hasNext();) {
				String keytoDelete = (String) iterator.next();
				records.remove(keytoDelete);				
			}
//			
			LocalDateTime now = LocalDateTime.now();
			logger.info("Records read: "+ recordsRead + " results count: "+ records.size() 	// <-- for step  9
					+ " duration: "+Duration.between(start, now)							// <-- for step  9
					+ " Find took "+ Duration.between(startFind, now) 						// <-- for step 11
					+ " Delete took "+ Duration.between(startDelete, now)					// <-- for step 12
					);																		// <-- for step  9

		} catch ( IOException e) {
			e.printStackTrace();
		}
	}
	
	private static final String buildKey(String flightNumber, String origin, String destination, String flightDate, String departureTime, String cancelled) {
		String key = flightNumber+origin+destination+flightDate+departureTime+cancelled;
		return key;
	}
	
	private static final String buildKey(String[] columns) {
		String key = columns[FLIGHT_NUM_COLUMN]+columns[ORIGIN_COLUMN]+columns[DESTINATION_COLUMN]+columns[FLIGHT_DATE_COLUMN]+columns[DEPARTURE_TIME_COLUMN]+columns[CANCELLED_COLUMN];
		return key;
	}
}
