package com.indeed.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitStatements {
	public static void waitforElementVisibility(String ELE_CSS_SELECTOR){
		try{
		WebDriverWait wait = new WebDriverWait(Libutils.driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELE_CSS_SELECTOR)));
		System.out.println("Element has been located: " + " " + ELE_CSS_SELECTOR);
		//LOGGER.info("Wait until the element is found:" +ELE_CSS_SELECTOR);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static void waitforElementClick(String ELE_CSS_SELECTOR){
		try{
		WebDriverWait wait = new WebDriverWait(Libutils.driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ELE_CSS_SELECTOR)));
		System.out.println("Element has been located: " + " " + ELE_CSS_SELECTOR);
		//LOGGER.info("Wait until the element is found:" +ELE_CSS_SELECTOR);
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	}

}
