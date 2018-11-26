package com.tadtab;


import com.tadtab.dao.LogRepository;
import com.tadtab.model.LogModel;
import com.tadtab.utility.StringToDateConverterUtility;

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
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.System.exit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@SpringBootApplication
@ComponentScan({"com.tadtab"})
@EntityScan("com.tadtab")
public class SpringBootConsoleApplication implements CommandLineRunner {

    @Autowired
    DataSource dataSource;
    
    @Autowired
    private LogRepository logRepository;
    
    @Autowired
    private StringToDateConverterUtility stringToDateConverterUtility;
    
    private static final String FILENAME = "/Users/tadtab/eclipse-workspace/ParserTest/outSideFile.txt";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootConsoleApplication.class, args);
    }
    
    

    @Override
    public void run(String... args) throws Exception {

    	BufferedReader br = null;
		FileReader fr = null;
		
		if(args.length <= 0) {
	
			try {
	
				fr = new FileReader(FILENAME);
				br = new BufferedReader(fr);
	
				String sCurrentLine;
				String finalLine = "";
	
				while ((sCurrentLine = br.readLine()) != null) {
					System.out.println(sCurrentLine);
					finalLine = finalLine + sCurrentLine;
					
				}
				
				String[] stringArray = finalLine.split("(?=(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}))");
				
				for(int i=0; i < stringArray.length; i++) {
						System.out.println(i + " is " + stringArray[i]);
						
						String insideString = stringArray[i];
						String[] logFields = insideString.split("\\|");
						Date date = null;
						String ip = null;
						String request = null;
						int status = 0;
						String userAgent = null; 
						for(int j = 0; j < logFields.length; j++) {
							
							if(logFields[j].matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}")) {
								System.out.println("time of request: " + logFields[j]);
								date = stringToDateConverterUtility.dateConverter(logFields[j]);
							}else if(logFields[j].matches("\\d*\\.\\d*\\.\\d*\\.\\d*")) {
								System.out.println("IP Adderes of the requesting machins is: " + logFields[j]);
								ip = logFields[j];
							}else if(logFields[j].matches("(?=(\\d{3}))")) {
								System.out.println("Request " + logFields[j]);
								request = logFields[j];
							}else if(logFields[j].matches("\\d{3}")) {
								System.out.println("Status of the Request is " + logFields[j]);
								status = Integer.parseInt(logFields[j]);
							}else if(logFields[j].matches("^[a-zA-Z]*\\|\\d")) {
								System.out.println("User Agent is: " + logFields[j]);
								userAgent = logFields[j];
							}
//							request = logFields[2];
//							userAgent = logFields[4];
							
						}
						
						logRepository.addLog(date, ip, request, status, userAgent);
				}
				
			} catch (IOException e) {
	
				e.printStackTrace();
	
			} finally {
	
				try {
	
					if (br != null)
						br.close();
	
					if (fr != null)
						fr.close();
	
				} catch (IOException ex) {
	
					ex.printStackTrace();
	
				}
	
			}
		}else if(args.length >= 1) {
			
			// java -cp "parser.jar" com.ef.Parser --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 
		
			System.out.println("Displaying IPs that made enormous number of requests...");
			List<LogModel> logModelList = logRepository.findAll( args[1], args[2]);
	
			Map<String, Long> mapOfIp = logModelList
					.stream().map(LogModel::getIp).
					collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			
			System.out.println(mapOfIp.entrySet().stream()
					.filter(map -> map.getValue().intValue() > Integer.parseInt(args[3])) //filter by value
			         .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue())));
		}
        exit(0);
    }
}