package com.springboot.main.util;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogRecorder {

	public static void record(Logger logger){
		
		try {
			
			
			FileHandler fh = new FileHandler("D:/myLogfile.txt");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
		}catch(Exception ex) {ex.printStackTrace();}
		
	}
	
}
