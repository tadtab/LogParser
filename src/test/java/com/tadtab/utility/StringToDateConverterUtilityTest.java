package com.tadtab.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StringToDateConverterUtilityTest {
	
	@InjectMocks
	StringToDateConverterUtility stringToDateConverterUtility;
	
	@Test
	public void addHourTest(){
		String afterOneHour = stringToDateConverterUtility.addHour("2017-01-01 00:00:42.000", 1l);
		assertEquals("2017-01-01 01:00:42.000", afterOneHour);
	}
	
	@Test
	public void dateConverterTest(){
		Date timeToBeConverted = stringToDateConverterUtility.dateConverter("2017-01-01 00:00:42.000");
		Date date = new Date("Sun Jan 01 00:00:42 EST 2017");
		assertEquals(date, timeToBeConverted);
	}

}
