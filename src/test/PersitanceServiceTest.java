package com.tadtab.service;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.tadtab.dao.LogRepository;
import com.tadtab.utility.StringToDateConverterUtility;

@RunWith(MockitoJUnitRunner.class)
public class PersitanceServiceTest {
	
	@InjectMocks
	PersitanceService persitanceService;
	
	@InjectMocks
    private LogRepository logRepository;
    
    @InjectMocks
    private StringToDateConverterUtility stringToDateConverterUtility;
	
	@Test
	public void populateDateTest(){
		String file = "";
		try {
			file = new ClassPathResource("testFilesFolder/testLogFile.txt").getFile().getPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		persitanceService.populateDate(file);
	}

}
