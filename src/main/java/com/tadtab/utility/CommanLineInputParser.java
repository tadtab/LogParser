package com.tadtab.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tadtab.service.PersitanceService;

@Service
public class CommanLineInputParser {
	
	final static Logger LOGGER = Logger.getLogger(CommanLineInputParser.class);
	/*
	 * This method parses command line inputs and returns a Map of the key value in the following format 
	 * {duration=hourly, startDate=2017-01-01 13:00:00, accesslog=/path/to/file}
	 */
	
	public Map<String, String> getArguments(String[] args){
		
		Map<String, String> argsMap = null;
		
		if(args.length > 0) {
			argsMap = new HashMap<>();
			for(int i = 0; i < args.length; i++) {
				
				Pattern patternKey = Pattern.compile("^--(.*)=");
				Pattern patternValue = Pattern.compile("=(.*)$");
				
				Matcher keyMatcher = patternKey.matcher(args[i]);
				Matcher ValueMatcher = patternValue.matcher(args[i]);
				
				String key ="";
				String value ="";
				
				if (keyMatcher.find())
				{
				    key = keyMatcher.group(1);
				}
				
				if (ValueMatcher.find())
				{
				   value = ValueMatcher.group(1);
				}
					
				if( ("accesslog").equalsIgnoreCase(key)
						) {
				
					argsMap.put(key, value);
				}
				if( ("threshold").equalsIgnoreCase(key)
						) {
				
					argsMap.put(key, value);
				}
				
				if( ("duration").equalsIgnoreCase(key)
						) {
					
					argsMap.put(key, value);
				}
				
				if( ("startDate").equalsIgnoreCase(key)
						) {
					String startDate = value;
					startDate = startDate.replaceFirst("\\.", " ");
					argsMap.put(key, startDate);
					
				}
				
			}
			
		}
		return argsMap;
	}
}
