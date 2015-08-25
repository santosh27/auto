package com.indeed.automation;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.firefox.FirefoxDriver;


public class NavigationUtils extends ExcelUtilities {
	public static void openFirefoxDriver(){
			Libutils.driver = new FirefoxDriver();
			Libutils.driver.manage().deleteAllCookies();
	}
	public static void navigateTillApplyPage() throws Exception{

		Libutils.driver = new FirefoxDriver();
			Libutils.driver.get("https://admin:AxkN!BjZx@cecentertainment-test.mobolt.com");
			Libutils.verifyAndClick(Libutils.driver, Repository.SEARCH_JOBS_CSS_SELECTOR);
			Thread.sleep(3000);
			Libutils.verifyAndClick(Libutils.driver, Repository.SEARCH_CSS_SELECTOR);
			Thread.sleep(3000);
			ExcelUtilities.getInputDataFromExcel(Libutils.path_name, 1);
	}
	public static void navigateToEachpage() throws Exception{
		for (int i = 0; i < 50; i++) {
			Libutils.LOGGER.info("User is on the page with the URL as  :"+Libutils.driver.getCurrentUrl());
			if (!Libutils.driver.getCurrentUrl().contains("completed")) {
				Libutils.knock_answer = 0;
				Libutils.required_answer = 0;
				Libutils.LOGGER.info("Attempts to read the JSON and answring it");
				Libutils.getJSONFrmJS(); // 1.read , 2.write answers from json based
				Libutils.driver.findElement(By.cssSelector("#application-continue-button")).click();
				Libutils.LOGGER.info("Clicked on the continue button");
				Thread.sleep(1000);
				Libutils.answerErrorMessages(); 
				Thread.sleep(1000);
				if(Libutils.driver.getCurrentUrl().contains(Repository.URL_CONTAINS_SUBMIT)){
					Libutils.handler.close();
					break;
				}
			} else {
				System.out
						.println("*********  At SUMMARY Page ***********");
				Libutils.LOGGER.info("******USER IS ON THE SUMMARY PAGE******");
				// click on 'Submit' in the Summary page ..
				File scrFile = ((TakesScreenshot)Libutils.driver).getScreenshotAs(OutputType.FILE);
				//Libutils.applicationIDToExcel(Libutils.row_val, Libutils.client_url_admin);
				Libutils.getApplicationID();//****NEWLY ADDED CODE
				Libutils.appidndURLToArray();
				DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd"+"_"+"HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				System.out.println(dateFormat.format(cal.getTime()));
				String time = dateFormat.format(cal.getTime()).toString();
				String screenshot_name = Libutils.log_path_name + Libutils.app_id+ time;//***NEWLY ADDED CODE
				FileUtils.copyFile(scrFile, new File(screenshot_name+".png"));
				Libutils.driver.findElement(By.cssSelector("#application-continue-button")).click();
				break;
			}
		}
		try {
			// click on 'Submit' in the Summary page ..
			//Libutils.driver.findElement(By.cssSelector("#application-continue-button")).click();
			if (Libutils.driver.findElement(By.xpath("//input[@id='feedback_Very_Easy' and @value = 'Very Easy']")).isDisplayed())
				System.out.println("End of Application Flow");
				Libutils.LOGGER.info("**END OF APPLICATION FLOW**");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
