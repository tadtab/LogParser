package com.tadtab.utility;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommanLineInputparserTest {
	
	@InjectMocks
	CommanLineInputParser commanLineInputParser;
	
	@Test
	public void getArgumentsTest() {
		String[] arguments = {"--duration=daily"};
		Map<String, String> argumentsMap = commanLineInputParser.getArguments(arguments);
		String value = argumentsMap.get("duration");
		assertEquals("daily", value);
	}
	
}
