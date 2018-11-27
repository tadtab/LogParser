package com.tadtab.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.tadtab.Parser;
import com.tadtab.model.LogModel;
import com.tadtab.utility.StringToDateConverterUtility;



	@Repository
	public class LogRepository {
		
		final static Logger LOGGER = Logger.getLogger(LogRepository.class);

	    @Autowired
	    private JdbcTemplate jdbcTemplate;
	    
	    @Autowired
	    private StringToDateConverterUtility stringToDateConverterUtility;

	    public List<LogModel> findLogs(String startTime, String dailyOrHourlyIndicator) {
	    	
	    	String endTimeLimit = "";
	    	List<LogModel> logList = new ArrayList<>();
	    	String select = "SELECT date, ip, request, status, userAgent FROM log where (date >= ? AND date <= ?)";
	    	
	    	
	    	if(dailyOrHourlyIndicator.equalsIgnoreCase("hourly")) {
	    		endTimeLimit = stringToDateConverterUtility.addHour(startTime, 1l);
    		   
	    	}else if(dailyOrHourlyIndicator.equalsIgnoreCase("daily")){
	    		endTimeLimit = stringToDateConverterUtility.addHour(startTime, 24l);
    		    
	    	}
	    	logList = jdbcTemplate.query(select, (rs, rowNum) -> new LogModel(rs.getDate("date"), rs.getString("ip"), 
               		rs.getString("request"), rs.getInt("status"), rs.getString("userAgent") ), new Object[]{startTime, endTimeLimit});
	    	
	    	LOGGER.info("Nubmer of User Agents to be Blocked: " + logList.size());
	    	 
	    	 return logList;
	    
	    }
	    
	    public void addLog(Date date, String ip,  String request,int status, String userAgent) {

	        jdbcTemplate.update("INSERT INTO log(date, ip, request, status, userAgent) VALUES (?,?,?,?,?)",
	                date, ip, request, status, userAgent);
	    }

	    public void addToBlockedList(String ip, int count, String reasonForBlocking) {

	        jdbcTemplate.update("INSERT INTO blockedips(ip, count, reasonForBlocking) VALUES (?,?,?)",
	        		ip, count, reasonForBlocking);
	    }

	}
