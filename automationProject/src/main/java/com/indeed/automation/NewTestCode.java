package com.indeed.automation;

import org.openqa.selenium.WebDriver;

public class NewTestCode {
	
	public static String local_machine_path;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
				//local_machine_path = args[0];
				local_machine_path = "/Users/santoshkumar/Desktop/";
				Libutils.createExternalPaths();
				Libutils.selectApplyMode(Libutils.path_name, 0);
				Libutils.checkApplicationStatus();
				Libutils.handler.close();
				System.out.println("**End of the Execution**");
	}

}
