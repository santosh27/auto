package com.indeed.automation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import jxl.Sheet;
import jxl.Workbook;

public class Admin {
	public static String EMAIL_ID = "admin_email";
	public static String PASSWORD_ID = "admin_password";
	public static String SUBMIT_BUTTON_XPATH = "//input[@name='commit']";
	public static String BROWSE_APPLICATIONS_XPATH = "//a[@href='/admin/browse/applications']";
	public static String STATUS_TEXT_XPATH = "html/body/div[1]/div[2]/table/tbody/tr[7]/td[2]";
	public static String SUBSTATUS_TEXT_XPATH = "html/body/div[1]/div[2]/table/tbody/tr[8]/td[2]";
	static String status_text, sub_status_text;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	try{
		FileInputStream fi = new FileInputStream(Libutils.applicationid_path_name);
		Workbook w = Workbook.getWorkbook(fi);
		Sheet s1 = w.getSheet(0);
		for(int rows = 0; rows < 4; rows++){
			String client_name = s1.getCell(0, rows).getContents();
			System.out.println("The client name at: "+rows+" is: "+client_name);
			if(client_name.length()>0){
				String app_id_val = s1.getCell(1, rows).getContents();
				System.out.println("The application id for the client name: "+client_name+" is: "+app_id_val);
				//HtmlUnitDriver driver = new HtmlUnitDriver();
				WebDriver driver = new FirefoxDriver();
				driver.get(client_name);
				  driver.findElement(By.id(EMAIL_ID)).sendKeys("santosh+automationtesting@indeed.com");
				  driver.findElement(By.id(PASSWORD_ID)).sendKeys("Pass@w0rd!");
				  driver.findElement(By.xpath(SUBMIT_BUTTON_XPATH)).click();
				  Thread.sleep(3000);
				  driver.findElement(By.xpath(BROWSE_APPLICATIONS_XPATH)).click();
				  Thread.sleep(3000);
				  try{
					 	driver.findElement(By.linkText(app_id_val)).click();
					 	for(int i = 0; i<5; i++){
					 	status_text = driver.findElement(By.xpath(STATUS_TEXT_XPATH)).getText();
					 	sub_status_text = driver.findElement(By.xpath(SUBSTATUS_TEXT_XPATH)).getText();
							if(!status_text.equalsIgnoreCase("Processing") && !sub_status_text.equalsIgnoreCase("wta")){
					 		if (status_text.equalsIgnoreCase("Succeeded") || sub_status_text.equalsIgnoreCase("success")) {
								System.out.println("The job application has been successful");
								driver.close();
								break;
							}
						 	else if (status_text.equalsIgnoreCase("Queued") && sub_status_text.equalsIgnoreCase("waiting for attachment")) {
								System.out.println("Please send the resume as an email attachment and check for application success");
								driver.close();
								break;
							}
						 	else if(status_text.equalsIgnoreCase("Succeeded") && sub_status_text.equalsIgnoreCase("Previous")){
						 		System.out.println("The user has already applied to this job");
						 		driver.close();
						 		break;
						 	}
						/* 	else if (status_text.equalsIgnoreCase("Processing") && sub_status_text.equalsIgnoreCase("wta")) {
								System.out.println("The application is under procesing state it will retry after some time");
								driver.close();
							}*/
						 		else{
								System.out.println("The job application is a failure");
							}
							}else{
								System.out.println("The application is under procesing state it will retry after some time");
								Thread.sleep(120000);
								driver.navigate().refresh();
							}
					 	}
				  }
					 catch(Exception e){
						 System.out.println("No element found");
					 }
				  Thread.sleep(60000);
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
