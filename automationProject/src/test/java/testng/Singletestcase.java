package testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Singletestcase {
	WebDriver driver;
  @Test
  public void testcase1() {
	  System.out.println("Started executing the test case sceaniro");
	  driver.get("https://www.flipkart.com");
	  String title = "Notifications";
	  String gettitle = driver.findElement(By.id("notifications-link")).getAttribute("title");
	  Assert.assertEquals(gettitle, title, "Attempting to verify the title of the notifications");
  }
  @Test
  public void testcase2(){
	  System.out.println("User is on the second test case now..");
	  driver.get("https://www.myntra.com");
  }
  @BeforeClass
  public void beforeclass(){
	  System.out.println("The test case has started executing");
  }
  @AfterClass
  public void afterclass(){
	  System.out.println("All the test cases have been executed");
  }
  @BeforeTest
  public void beforeTest() {
	  driver = new FirefoxDriver();
  }

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
