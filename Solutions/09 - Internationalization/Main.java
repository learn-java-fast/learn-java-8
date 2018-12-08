package com.example.i18n;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;


public class Main {

	private static final String DATE_MESSAGE = "DATE_MESSAGE";
	private static final String TIME_MESSAGE = "TIME_MESSAGE";
	private static final String BALANCE_MESSAGE = "BALANCE_MESSAGE";
	
	public static void main(String[] args) {

		Locale currentLocale = Locale.getDefault();

		Locale en_US = Locale.US;
		Locale en_UK = Locale.UK;
		Locale de_DE = Locale.GERMANY;
		Locale es_ES = new Locale("es");
		
		currentLocale = es_ES;
				
		ResourceBundle bundle = ResourceBundle.getBundle("messages", currentLocale);
		
		DateTimeFormatter dateformatter = DateTimeFormatter.ISO_LOCAL_DATE.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(currentLocale);
		DateTimeFormatter timeformatter = DateTimeFormatter.ISO_LOCAL_TIME.ofLocalizedTime(FormatStyle.MEDIUM).withLocale(currentLocale);
		
		System.out.println(bundle.getString(DATE_MESSAGE) + dateformatter.format(LocalDate.now()));
		System.out.println(bundle.getString(TIME_MESSAGE) + timeformatter.format(LocalTime.now()));
		System.out.println(bundle.getString(BALANCE_MESSAGE) + NumberFormat.getCurrencyInstance(currentLocale).format(12345678.99));
		
	}

}
