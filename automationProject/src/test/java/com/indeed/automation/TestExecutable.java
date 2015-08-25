package com.indeed.automation;

import org.testng.annotations.Test;

public class TestExecutable {
	public static String local_machine_path;
  @Test
  public void f() throws Exception {
	  local_machine_path = "/Users/santoshkumar/Desktop/";
		Libutils.createExternalPaths();
		Libutils.selectApplyMode(Libutils.path_name, 0);
		Libutils.checkApplicationStatus();
		Libutils.handler.close();
		System.out.println("**End of the Execution**");
  }
}
