package com.indeed.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


import com.indeed.automation.Libutils;
//import com.indeed.automation.Utils;

public class Samplecode extends Libutils {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		ExcelUtilities.getInputDataFromExcel(
				"/Users/santoshkumar/Desktop/jobslistinfo.xls", 1);
		Libutils.driver = new FirefoxDriver();
		Libutils.driver.manage().deleteAllCookies();
		try {
			Libutils.driver
					.get("https://admin:rC9WcJCW@dish-staging.mobolt.com");
			// Libutils.driver
			// .get("https://admin:lnQ1vmvX@primetherapeuticscareers-test.mobolt.com/job/all");
			verifyAndClick(driver, Repository.SEARCH_JOBS_CSS_SELECTOR);
			Thread.sleep(3000);
			verifyAndClick(driver, Repository.SEARCH_CSS_SELECTOR);
			Thread.sleep(3000);
			Libutils.driver.findElement(
					By.partialLinkText("Business Analyst")).click();
			Thread.sleep(3000);
			verifyAndClick(driver, Repository.APPLY_BUTTON_CSS_SELECTOR);
			Thread.sleep(3000);
			verifyAndClick(driver, Repository.BUILD_CV_CSSSELECTOR);

			// Get the json from each page & then answer each question based on
			// json format if it faileshen approach for trial & error approach
			// ..
			for (int i = 0; i < 20; i++) {
				if (!driver.getCurrentUrl().contains("completed")) {
					getJSONFrmJS(); // 1.read , 2.write answers from json based
					driver.findElement(
							By.cssSelector("#application-continue-button"))
							.click();
					Thread.sleep(1000);
					Libutils.answerErrorMessages(); // 3. write answers if json
													// based is failed for some
													// reason
				} else {
					System.out
							.println("*********  At SUMMARY Page ***********");
					break;
				}
			}

			try {
				// click on 'Submit' in the Summary page ..
				driver.findElement(
						By.cssSelector("#application-continue-button")).click();

				if (Libutils.driver
						.findElement(
								By.xpath("//input[@id='feedback_Very_Easy' and @value = 'Very Easy']"))
						.isDisplayed())
					System.out.println("End of Application Flow");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
