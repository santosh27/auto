package automationmoboltproduct;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class BuildCVScenarios extends ScenarioUtilities{
  @Test
  public void BuildCV() throws Exception {
	    ExcelUtilities.getInputDataFromExcel("/Users/santoshkumar/Desktop/jobslistinfo.xls", 1); // "/Users/santoshkumar/Documents/jobslistinfo.xls"
		navigateTillApplyPage("/Users/santoshkumar/Desktop/jobslistinfo.xls", 0, 0);
		extractandFillQuestionsinEveryPage(Repository.EMAIL_IT_LATER_CSS_SELECTOR, "0", "/Users/santoshkumar/Desktop/jobslistinfo.xls", "auto");
  }
  @BeforeTest
  public void beforeTest() {
	  FirefoxProfile Profile = new FirefoxProfile();
	  Profile.setPreference("intl.accept_languages", "de");
	  Repository.driver = new FirefoxDriver();
		Utilities.openUrlandWaitForPageToLoad(Repository.driver, "https://admin:AxkN!BjZx@cecentertainment-test.mobolt.com");
		//openUrlandWaitForPageToLoad(driver, url_client);
		Utilities.maximizeWindow(Repository.driver);
  }

  @AfterTest
  public void afterTest() {
  }

}
