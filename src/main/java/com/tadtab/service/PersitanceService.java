package com.tadtab.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tadtab.dao.LogRepository;
import com.tadtab.utility.StringToDateConverterUtility;

@Service
public class PersitanceService {
	
	final static Logger LOGGER = Logger.getLogger(PersitanceService.class);
	  
    @Autowired
    private LogRepository logRepository;
    
    @Autowired
    private StringToDateConverterUtility stringToDateConverterUtility;
	
	public void populateDate(String logfileLocation) {
    	
    	BufferedReader bufferReader = null;
		FileReader fileReader = null;
    	
    	try {
    		
    		fileReader = new FileReader(logfileLocation);
			bufferReader = new BufferedReader(fileReader);

			String sCurrentLine;
			String finalLine = "";

			while ((sCurrentLine = bufferReader.readLine()) != null) {
				LOGGER.info(sCurrentLine);
				finalLine = finalLine + sCurrentLine;
				
			}
			
			String[] stringArray = finalLine.split("(?=(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}))");
			
			for(int i=0; i < stringArray.length; i++) {
					
					String insideString = stringArray[i];
					String[] logFields = insideString.split("\\|");
					Date date = null;
					String ip = null;
					String request = null;
					int status = 0;
					String userAgent = null; 
					for(int j = 0; j < logFields.length; j++) {
						if(logFields[j].matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}.\\d{3}")) {
							date = stringToDateConverterUtility.dateConverter(logFields[j]);
						}else if(logFields[j].matches("\\d*\\.\\d*\\.\\d*\\.\\d*")) {
							ip = logFields[j];
						}else if(logFields[j].matches("\\d{3}")) {
							status = Integer.parseInt(logFields[j]);
						}
						if( j == 2 && logFields[2] != null) {
							request = logFields[2].replaceAll("\"", "");
						}
						if( j == 4 && logFields[4] != null) {
							userAgent = logFields[4];
						}
						
					}
					
					logRepository.addLog(date, ip, request, status, userAgent);
			}
			
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bufferReader != null)
					bufferReader.close();

				if (fileReader != null)
					fileReader.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
    }

}
