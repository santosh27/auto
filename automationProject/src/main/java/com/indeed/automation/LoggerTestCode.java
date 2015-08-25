package com.indeed.automation;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerTestCode {

	public static void main(String[] args) throws Exception, Exception {
		// TODO Auto-generated method stub
		/*String path_name = "/Users/santoshkumar/Desktop/tesloga.txt";
		 Logger LOGGER = Logger.getAnonymousLogger();
		 boolean append = true;
		 FileHandler handler = new FileHandler(path_name, append);
		 handler.setFormatter(new SimpleFormatter());
		 LOGGER.addHandler(handler);
		 LOGGER.info("Welcome to the Logger test functionality");
		 LOGGER.info("Test Message 1");
		 System.out.println("Test print statement");*/
		 Libutils.selectApplyMode("/Users/santoshkumar/Desktop/jobslistinfo.xls", 0);
		 
	}

}
