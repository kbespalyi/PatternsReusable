package utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils {
	
	public String convertToUTC(String inputDateTime) {
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern( "YYYY-MM-dd'T'HH:mm:ss.SSSZ" );
		// Adding "withOffsetParsed()" means "set new DateTime's time zone offset to match input string".
		DateTime dateTime = formatter.withOffsetParsed().parseDateTime( inputDateTime );
		// Convert to UTC/GMT (no time zone offset).
		DateTime dateTimeUTC = dateTime.toDateTime( DateTimeZone.UTC );
		return dateTimeUTC.toString();
	}
	
	public String convertToUTCWithOffset(String inputDateTime) {
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern( "YYYY-MM-dd'T'HH:mm:ss.SSSZ" );
		// Adding "withOffsetParsed()" means "set new DateTime's time zone offset to match input string".
		DateTime dateTime = formatter.withOffsetParsed().parseDateTime(inputDateTime );	
		// Convert to UTC/GMT (no time zone offset).
		DateTime dateTimeUTC = dateTime.toDateTime( DateTimeZone.UTC );	
		DateTimeFormatter expectedFormatter = DateTimeFormat.forPattern( "YYYY-MM-dd'T'HH:mm:ss.SSSZ" );	
		return (dateTimeUTC.toString(expectedFormatter));
	}
	
	public String getCurrentDateTimeWithoutSecondsPattern() {
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern( "YYYY-MM-dd'T'HH:mmZ" );
		DateTime localDateTime = LocalDateTime.now().toDateTime().withZone(DateTimeZone.UTC);
		String currentDateTime =localDateTime.toString(formatter) ;
		return currentDateTime ;
	}

	public String getCurrentDateTime() {
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern( "YYYY-MM-dd'T'HH:mm:ss.SSSZ" );
		DateTime localDateTime = LocalDateTime.now().toDateTime();
		return localDateTime.toString(formatter);
	}
	
	public String getCurrentLocalDateTimeWithTZOffset(){
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern( "YYYY-MM-dd'T'HH:mm:ss.SSSZZ" );
		DateTime localDateTime = LocalDateTime.now().toDateTime();
		return localDateTime.toString(formatter);
	}
	
	public String getDateTimeNSecondsPriorToCurrentLocalDateTime(DateTime currentLocalDateTime, int seconds){	
		DateTimeFormatter formatter = DateTimeFormat.forPattern( "YYYY-MM-dd'T'HH:mm:ss.SSSZZ" );
		DateTime dateTime = currentLocalDateTime.minusSeconds(seconds);
		return dateTime.toString(formatter);
	}
	
	public String getDateTimeWithoutSeconds(String inputDateTime){
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern( "YYYY-MM-dd'T'HH:mm:ss.SSSZ" );
		// Adding "withOffsetParsed()" means "set new DateTime's time zone offset to match input string".
		DateTime dateTime = formatter.withOffsetParsed().parseDateTime( inputDateTime );			
		// Convert to UTC/GMT (no time zone offset).
		DateTime dateTimeUTC = dateTime.toDateTime( DateTimeZone.UTC );	
		DateTimeFormatter expectedFormatter = DateTimeFormat.forPattern( "YYYY-MM-dd'T'HH:mm" );	
		return (dateTimeUTC.toString(expectedFormatter));

	}
	
	public String getDateTimeInUTCFromEpoch (long epoch){
		
		DateTime dateTime = new DateTime(epoch);
		DateTime dateTimeUTC = dateTime.toDateTime(DateTimeZone.UTC);
		DateTimeFormatter expectedFormatter = DateTimeFormat.forPattern( "YYYY-MM-dd'T'HH:mm:ss.SSSZZ" );	
		return (dateTimeUTC.toString(expectedFormatter));
	}
	
	public String getLocalDateTimeFromEpoch (long epoch){
		
		DateTime dateTime = new DateTime(epoch);
		DateTimeFormatter expectedFormatter = DateTimeFormat.forPattern( "YYYY-MM-dd'T'HH:mm:ss.SSSZZ" );	
		return (dateTime.toString(expectedFormatter));

	}
	
	public void setTimer(int seconds){
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
