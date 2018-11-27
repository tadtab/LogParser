package com.tadtab.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class StringToDateConverterUtility {
	
	final static Logger LOGGER = Logger.getLogger(StringToDateConverterUtility.class);
	
	public String addHour(String oldDate, Long hourToAdd) {
		
		Date oldDateAfterConversion = dateConverter(oldDate.toString());
		final long hoursInMillis = 60L * 60L * 1000L;
    	Date newDate = new Date(oldDateAfterConversion.getTime() + hourToAdd * hoursInMillis);
    	
    	DateTime dateTime = new DateTime(newDate);
    	
    	String dateTimeString = dateTime.toString().replaceFirst("T", " ");
    	dateTimeString = dateTimeString.replaceFirst("-\\d{2}:\\d{2}$", "");
    	
    	
    	return dateTimeString;
	}
	
	public Date dateConverter(String dateString) {
		
		SimpleDateFormat formatter  =  null;
		
		if(dateString.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}")) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
		}else {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
    	
    	Date date = null;
        try {
            date = formatter.parse(dateString);
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
