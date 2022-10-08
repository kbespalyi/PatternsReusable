package tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import utils.DateUtils;

public class DateConvertTest {
	
	private static DateUtils dateUtils = new DateUtils();

	@BeforeClass	
	public static void setup() {

	}

	@AfterClass
	public static void tearDown() {
		
	}

	@Test
	public void whenDateIsUTCFormat_thenConvertedSuccessfully() {
		
		String registrationDate = "2016-02-04T19:07:07.886-0500";
		String expectedRegistrationDate = "2016-02-05T00:07:07.886Z";
		
		assertEquals(
			expectedRegistrationDate,
			dateUtils.convertToUTC(registrationDate),
			"unexpetected 'registration_date' value"
		);

	}

	@Test
	public void whenDateIsWithoutSeconds_thenEquils() {
		
		String registrationDate = "2016-02-04T19:07:07.886-0500";
		String expectedRegistrationDate = "2016-02-05T00:07";
		
		assertEquals(
			expectedRegistrationDate,
			dateUtils.getDateTimeWithoutSeconds(dateUtils.convertToUTC(registrationDate)),
			"unexpetected 'registration_date' value"
		);

	}

	@Test
	public void whenJsonUTCDateIsWithoutSeconds_thenEquils() {
		
		String registrationDate = "2016-02-05T00:07:07.886Z";
		String expectedRegistrationDate = "2016-02-05T00:07";
		
		assertEquals(
			expectedRegistrationDate,
			dateUtils.getDateTimeWithoutSeconds(registrationDate),
			"unexpetected 'registration_date' value"
		);

	}
	
}
