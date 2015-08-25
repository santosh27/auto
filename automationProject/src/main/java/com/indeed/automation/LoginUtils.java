package com.indeed.automation;

import java.io.FileInputStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.browserlaunchers.locators.GoogleChromeLocator;

import jxl.Sheet;
import jxl.Workbook;

public class LoginUtils {
	public static String credential1;
	public static String credential2;
	static String cv_upload;
	
	public static void loginIntoSNS(String path_name, String sheet, String sns_name) throws Exception{
		Libutils.LOGGER.info("The Login details are extracted from the Excel Sheet: "+path_name+" and the sheet is: "+sheet);
		FileInputStream fi = new FileInputStream(path_name);
		Workbook w = Workbook.getWorkbook(fi);
		Sheet s1 = w.getSheet(sheet);
		Sheet s2 = w.getSheet("Jobs&Resume");
		if(sns_name.equals("facebook")){
			credential1 = s1.getCell(0, 1).getContents();
			credential2 = s1.getCell(0, 2).getContents();
			try{
			Libutils.LOGGER.info("The login credentials are : "+ "Email ID: "+ credential1 + "Password: " +credential2);
//			cv_upload = s1.getCell(3, 4).getContents();
			System.out.println("The login credentials are: "+ "Email ID:" + credential1 + "Password:" + credential2);
			Libutils.verifyAndEnter(Repository.FACEBOOK_EMAIL_CSS_SELECTOR, credential1);
			Libutils.LOGGER.info("Entered the Email: " +credential1 + " into the Email address field");
			Libutils.verifyAndEnter(Repository.FACEBOOK_PASSWORD_CSS_SELECTOR, credential2);
			Libutils.LOGGER.info("Entered the Password: " +credential2 + " into the password field");
			Libutils.verifyAndClickButton(Repository.FACEBOOK_SIGNIN_CSS_SELECTOR);
			Libutils.LOGGER.info("Clicked on the Sign in button");
			Thread.sleep(1000);
			WaitStatements.waitforElementVisibility(Repository.CONTINUE_CSS_SELECTOR);
			Libutils.LOGGER.info("Waited until the user has been landed on to the next page");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else if(sns_name.equals("linkedin")){
			credential1 = s1.getCell(1, 1).getContents();
			credential2 = s1.getCell(1, 2).getContents();
			try{
			Libutils.LOGGER.info("The login credentials are : "+ "Email ID: "+ credential1 + "Password: " +credential2);
			System.out.println("The login credentials are: "+ "Email ID:" + credential1 + "Password:" + credential2);
			Libutils.verifyAndEnter(Repository.LINKEDIN_EMAIL_CSS_SELECTOR, credential1);
			Libutils.LOGGER.info("Entered the Email: " +credential1 + " into the Email address field");
			Libutils.verifyAndEnter(Repository.LINKEDIN_PASSWORD_CSS_SELECTOR, credential2);
			Libutils.LOGGER.info("Entered the Password: " +credential2 + " into the password field");
			Libutils.verifyAndClickButton(Repository.LINKEDIN_SIGNIN_CSS_SELECTOR);
			Libutils.LOGGER.info("Clicked on the Sign in button");
			Thread.sleep(1000);
			WaitStatements.waitforElementVisibility(Repository.CONTINUE_CSS_SELECTOR);
			Libutils.LOGGER.info("Waited until the user has been landed on to the next page");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else if(sns_name.equals("indeed")){
			credential1 = s1.getCell(2, 1).getContents();
			credential2 = s1.getCell(2, 2).getContents();
			try{
			Libutils.LOGGER.info("The login credentials are : "+ "Email ID: "+ credential1 + "Password: " +credential2);
			System.out.println("The login credentials are: "+ "Email ID:" + credential1 + "Password:" + credential2);
			Libutils.verifyAndEnter(Repository.INDEED_EMAIL_CSS_SELECTOR, credential1);
			Libutils.LOGGER.info("Entered the Email: " +credential1 + " into the Email address field");
			Libutils.verifyAndEnter(Repository.INDEED_PASSWORD_CSS_SELECTOR, credential2);
			Libutils.LOGGER.info("Entered the Password: " +credential2 + " into the password field");
			Libutils.verifyAndClickButton(Repository.INDEED_SIGNIN_CSS_SELECTOR);
			Libutils.LOGGER.info("Clicked on the Sign in button");
			Thread.sleep(3500);
			WaitStatements.waitforElementVisibility(Repository.INDEED_ALLOW_CSS_SELECTOR);
			Libutils.LOGGER.info("Waited until the user has navigated to the Allow Access page");
			Libutils.verifyAndClickButton(Repository.INDEED_ALLOW_CSS_SELECTOR);
			Libutils.LOGGER.info("Clicked on the Allow button");
			WaitStatements.waitforElementVisibility(Repository.CONTINUE_CSS_SELECTOR);
			Libutils.LOGGER.info("Waited until the user has been landed on to the next page");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void loginIntoResume(String attachment_type, String path_name) throws Exception{
		Libutils.LOGGER.info("The Login details are extracted from the Excel Sheet: "+path_name+" and the sheet is: 2");
		try{
		FileInputStream fi = new FileInputStream(path_name);
		  Workbook w = Workbook.getWorkbook(fi);
		  Sheet s1 = w.getSheet(2);
		 // Libutils.verifyAndClick(Libutils.driver, cv_upload);
		 // WaitStatements.waitforElementVisibility(wait_ele);
		  Thread.sleep(3000);
	//	  System.out.println("The user is going to login into: " +cv_upload);
		if (attachment_type.equals("google")){
			credential1 = s1.getCell(3, 1).getContents();
			credential2 = s1.getCell(3, 2).getContents();
			String resume_name = s1.getCell(3, 3).getContents();
			try{
			System.out.println("The credentials are:"+ credential1 +"and"+ credential2 +"and resume name is: "+resume_name);
			Libutils.LOGGER.info("The login credentials are : "+ "Email ID: "+ credential1 + "Password: " +credential2);
			Libutils.LOGGER.info("The attachment to be uploaded is: "+resume_name);
			Libutils.verifyAndEnter(Repository.GOOGLEDRIVE_EMAIL_CSS_SELECTOR, credential1);
			Libutils.LOGGER.info("Entered the Email: " +credential1 + " into the Email address field");
			Libutils.verifyAndClickButton(Repository.GOOGLEDRIVE_NEXT_CSS_SELECTOR);
			Libutils.LOGGER.info("Clicked on the Next button");
			WaitStatements.waitforElementClick(Repository.GOOGLEDRIVE_SIGNIN_CSS_SELECTOR);
			Libutils.verifyAndEnter(Repository.GOOGLEDRIVE_PASSWORD_CSS_SELECTOR, credential2);
			Libutils.LOGGER.info("Entered the Password: " +credential2 + " into the password field");
			Libutils.verifyAndClickButton(Repository.GOOGLEDRIVE_SIGNIN_CSS_SELECTOR);
			Libutils.LOGGER.info("Clicked on the Sign in button");
			Thread.sleep(5500);
			WaitStatements.waitforElementClick(Repository.GOOGLEDRIVE_ALLOW_CSS_SELECTOR);
			Libutils.LOGGER.info("Waited until user is landed on the Allow access page of Google Drive");
			Libutils.verifyAndClickButton(Repository.GOOGLEDRIVE_ALLOW_CSS_SELECTOR);
			Libutils.LOGGER.info("Clicked on the Allow button");
			Libutils.selectResume(Libutils.driver, Libutils.resume_name);
			Libutils.LOGGER.info("Selected the resume from the available list");
			Thread.sleep(3000);
			Libutils.LOGGER.info("Waited until resume is successfull uploaded");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else if (attachment_type.equals("dropbox")) {
			credential1 = s1.getCell(4, 1).getContents();
			credential2 = s1.getCell(4, 2).getContents();
			String resume_name = s1.getCell(4, 3).getContents();
			try{
			System.out.println("The credentials are:"+ credential1 + "and" + credential2 );
			Libutils.LOGGER.info("The login credentials are : "+ "Email ID: "+ credential1 + "Password: " +credential2);
			Libutils.LOGGER.info("The attachment to be uploaded is: "+resume_name);
			Libutils.verifyAndEnter(Repository.DROPBOX_EMAIL_CSS_SELECTOR, credential1);
			Libutils.LOGGER.info("Entered the Email: " +credential1 + " into the Email address field");
			Libutils.verifyAndEnter(Repository.DROPBOX_PASSWORD_CSS_SELECTOR, credential2);
			Libutils.LOGGER.info("Entered the Password: " +credential2 + " into the password field");
			WaitStatements.waitforElementClick(Repository.DROPBOX_SIGNIN_CSS_SELECTOR);
			Libutils.verifyAndClickButton(Repository.DROPBOX_SIGNIN_CSS_SELECTOR);
			Libutils.LOGGER.info("Clicked on the Sign in button");
			Thread.sleep(5500);
			WaitStatements.waitforElementClick(Repository.DROPBOX_ALLOW_CSS_SELECTOR);
			Libutils.LOGGER.info("Waited until user is landed on the Allow access page of Google Drive");
			Libutils.verifyAndClickButton(Repository.DROPBOX_ALLOW_CSS_SELECTOR);
			Libutils.LOGGER.info("Clicked on the Allow button");
			Libutils.selectResume(Libutils.driver, Libutils.resume_name);
			Libutils.LOGGER.info("Selected the resume from the available list");
			Thread.sleep(3000);
			Libutils.LOGGER.info("Waited until resume is successfull uploaded");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else if (attachment_type.equals("autocreateCV")) {
			//Libutils.verifyAndClick(Libutils.driver, cv_upload);
			System.out.println("The resume will be uploaded automatically using your profile as you have selected Auto Created CV");
			Libutils.LOGGER.info("The resume will be uploaded automatically using your profile as you have selected Auto Created CV");
		}
		else if (attachment_type.equals("emailitlater")) {
			//Libutils.verifyAndClick(Libutils.driver, cv_upload);
			System.out.println("After this application is submitted please login into your email and send the email attachment in order to complete your application");
			Libutils.LOGGER.info("After this application is submitted please login into your email and send the email attachment in order to complete your application");
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static void signIn(WebDriver driver, String email, String password, 
			String signIn, String access, String credential1, String credential2, String resume_name){
		try{ 
		Libutils.verifyAndEnter(email, credential1);
		Libutils.verifyAndEnter(password, credential2);
		Libutils.verifyAndClick(Libutils.driver, signIn);
		Thread.sleep(9000);
		Libutils.verifyAndClick(Libutils.driver, access);
		System.out.println(resume_name);
		Thread.sleep(3000);
		driver.findElement(By.linkText(resume_name)).click();
		WaitStatements.waitforElementVisibility(Repository.CONTINUE_CSS_SELECTOR);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
