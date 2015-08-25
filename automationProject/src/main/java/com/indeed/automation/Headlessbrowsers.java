package com.indeed.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Headlessbrowsers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WebDriver driver = new HtmlUnitDriver();
		driver.get("https://www.google.com");
		System.out.println("Opened Google search engine");
		driver.findElement(By.name("q")).sendKeys("selenium");
		System.out.println("Entered sample text into it");
		driver.quit();
	}

}
