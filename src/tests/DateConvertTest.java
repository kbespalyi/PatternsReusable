package tests;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
		
		assertEquals("unexpetected 'registration_date' value",
			expectedRegistrationDate,
			dateUtils.convertToUTC(registrationDate)
		);

	}

	@Test
	public void whenDateIsWithoutSeconds_thenEquils() {
		
		String registrationDate = "2016-02-04T19:07:07.886-0500";
		String expectedRegistrationDate = "2016-02-05T00:07";
		
		assertEquals("unexpetected 'registration_date' value",
			expectedRegistrationDate,
			dateUtils.getDateTimeWithoutSeconds(dateUtils.convertToUTC(registrationDate))
		);

	}

	@Test
	public void whenJsonUTCDateIsWithoutSeconds_thenEquils() {
		
		String registrationDate = "2016-02-05T00:07:07.886Z";
		String expectedRegistrationDate = "2016-02-05T00:07";
		
		assertEquals("unexpetected 'registration_date' value",
			expectedRegistrationDate,
			dateUtils.getDateTimeWithoutSeconds(registrationDate)
		);

	}
	
}
