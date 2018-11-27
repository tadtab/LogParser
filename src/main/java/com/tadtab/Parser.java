package com.tadtab;


import com.tadtab.dao.LogRepository;
import com.tadtab.model.LogModel;
import com.tadtab.service.PersitanceService;
import com.tadtab.utility.CommanLineInputParser;
import com.tadtab.utility.StringToDateConverterUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.System.exit;


@SpringBootApplication
@ComponentScan({"com.tadtab"})
@EntityScan("com.tadtab")
public class Parser implements CommandLineRunner {
	
	final static Logger LOGGER = Logger.getLogger(Parser.class);

    @Autowired
    DataSource dataSource;
    
    @Autowired
    private LogRepository logRepository;
    
    @Autowired
    private  StringToDateConverterUtility stringToDateConverterUtility;
    
    @Autowired
    private CommanLineInputParser commanLineInputParser;
    
    @Autowired
    private PersitanceService persitanceService;
    

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Parser.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
    	

		if(args.length > 0) {
	
			Map<String, String> commandLineArguments = commanLineInputParser.getArguments(args);
			
			String StartDate = commandLineArguments.get("startDate");
			String threshold = commandLineArguments.get("threshold");
			String duration = commandLineArguments.get("duration");
			String accesslog = commandLineArguments.get("accesslog");
			
			persitanceService.populateDate(accesslog);
			
			List<LogModel> logModelList = logRepository.findLogs(StartDate, duration);
	
			Map<String, Long> mapOfIp = logModelList
					.stream().map(LogModel::getIp).
					collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			
			Map<Object, Object> filteredIps = mapOfIp.entrySet().stream()
					.filter(map -> map.getValue().intValue() > Integer.parseInt(threshold)) //filter by value with the threshold
			         .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
			
			LOGGER.info("FilteredList of Ips: " + filteredIps);
			
			filteredIps.forEach((key, value) -> {
				int count = ((Long)value).intValue();
				LOGGER.info("IP address: " + key + " has made: " + count + " requests and is being blocked");
				logRepository.addToBlockedList((String)key, count, "for making requests more than " + threshold );
			});;
				
				
			
		} else {
			LOGGER.info("Arguments are missing. please provide logFileLocation, startDate, duration and threshold");
		}
    
        exit(0);
    }
    
    
}