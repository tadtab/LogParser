package com.tadtab.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tadtab.model.LogModel;
import com.tadtab.utility.StringToDateConverterUtility;



	@Repository
	public class LogRepository {

	    @Autowired
	    private JdbcTemplate jdbcTemplate;
	    
	    @Autowired
	    private StringToDateConverterUtility stringToDateConverterUtility;

	    public List<LogModel> findAll(String startTime, String dailyOrHourlyIndicator) {
	    	
	    	Date StartDate = stringToDateConverterUtility.dateConverter(startTime);
	    	
	    	String queryWithCreteria ="SELECT date, ip, request, status, userAgent FROM log";
	    	
	    	if(dailyOrHourlyIndicator.equalsIgnoreCase("hourly")) {
	    		queryWithCreteria = 
	    				"SELECT date, ip, request, status, userAgent FROM log where date >= 'StartDate' and date <= '2017-01-01 00:06:02'";
	    		
	    	}else if(dailyOrHourlyIndicator.equalsIgnoreCase("daily")){
	    		queryWithCreteria = 
	    				"SELECT date, ip, request, status, userAgent FROM log where date >= 'StartDate' and date <= '2017-01-01 00:06:02'";
	    	}

	        return jdbcTemplate.query(
	        		queryWithCreteria,
	                (rs, rowNum) -> new LogModel(rs.getDate("date"), rs.getString("ip"), 
	                		rs.getString("request"), rs.getInt("status"), rs.getString("userAgent") ));
	    	

	    }
	    
	  


	    public void addLog(Date date, String ip,  String request,int status, String userAgent) {

	        jdbcTemplate.update("INSERT INTO log(date, ip, request, status, userAgent) VALUES (?,?,?,?,?)",
	                date, ip, request, status, userAgent);

	    }


	}
