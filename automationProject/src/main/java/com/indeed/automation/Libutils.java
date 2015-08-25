package com.indeed.automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.indeed.automation.Repository;
import com.indeed.automation.ExcelUtilities;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Libutils {
	public static WebDriver driver;
	static List<WebElement> textboxes;
	static String xpath, xpath_day, xpath_month, xpath_year;
	static String tagname;
	static String id, name, visible, label, category, options, validations,
			knockout, indices_val, req_indices_val, index, compulsory, backend_name, source_name, resume;
	static String length, length_json, email_string, textarea_string, source_val, resume_val, cv_string,
			questions_arr, maximum, type, key, fileData;
	static WebElement element, cvupload;
	static Map<String, Object> validatonMap;
	static Map<String, Object> questionMap;
	static Map<String, Object> knockout_ans;
	static Map<String, Object> required_ans;
	static ObjectMapper mapper;
	static int index_ctr, row_val;
	static int knock_answer, required_answer;
	static String attachment_type, resume_name, auth_name;
	static String client_url, job_name, url_split, app_id, client_url_admin;
	static int columns, index_last_val;
	static String apply_mode, facebook_app_id, indeed_app_id, linkedin_app_id, buildcv_app_id, facebook_admin_url, indeed_admin_url,linkedin_admin_url, buildcv_admin_url;
	static String email_address, confirm_email_address, password, confirm_password, username;
	//static String path_name = "/Users/santoshkumar/Desktop/jobslistinfo.xls"; //Excel Sheet Path Name 1
	static String path_name;
	//static String applicationid_path_name = "/Users/santoshkumar/Desktop/applicationid.xls";//Excel Sheet Path Name 2
	static String applicationid_path_name;
	//static String log_path_name = "/Users/santoshkumar/Desktop/default.txt";
	//static String log_path_name = "/Users/santoshkumar/Desktop/";//Excel Sheet Path Name 3
	static String log_path_name;
	static Logger LOGGER = Logger.getAnonymousLogger();
	static boolean append = true;
	static List<String> client_url_list, app_id_list; 
	static FileHandler handler;
	public static String EMAIL_ID = "admin_email";
	public static String PASSWORD_ID = "admin_password";
	public static String SUBMIT_BUTTON_XPATH = "//input[@name='commit']";
	public static String BROWSE_APPLICATIONS_XPATH = "//a[@href='/admin/browse/applications']";
	public static String STATUS_TEXT_XPATH = "html/body/div[1]/div[2]/table/tbody/tr[7]/td[2]";
	public static String SUBSTATUS_TEXT_XPATH = "html/body/div[1]/div[2]/table/tbody/tr[8]/td[2]";
	static String status_text, sub_status_text;

	@SuppressWarnings("unchecked")
	public static void extractJavaScript(WebDriver driver, String id) {
		By by = By.xpath(id);                                                      
		WebElement script = driver.findElement(by);
		fileData = script.getAttribute(Repository.ATTRIBUTE_JAVASCRIPT);
		LOGGER.info("The java script for this page is :" + fileData);
	}
	public static void createExternalPaths(){
		path_name = NewTestCode.local_machine_path + "jobslistinfo.xls";
		log_path_name = NewTestCode.local_machine_path;
		applicationid_path_name = NewTestCode.local_machine_path + "applicationid.xls";
		System.out.println(path_name);
		System.out.println(log_path_name);
		System.out.println(applicationid_path_name);
	}
	public static void LoggerClass() throws Exception, IOException{
		 DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd"+"_"+"HH:mm:ss");
		 Calendar cal = Calendar.getInstance();
		 System.out.println(dateFormat.format(cal.getTime()));
		 String time = dateFormat.format(cal.getTime()).toString();
		 String log_file_name = log_path_name + time;
		 System.out.println("The name or path of the log file will be: "+log_file_name);
		 handler = new FileHandler(log_file_name, append);
		 handler.setFormatter(new SimpleFormatter());
		 LOGGER.addHandler(handler);
		 LOGGER.info("**** LOG FILE ***");
	}
	public static void getJSONFrmJS() throws Exception {
		String JSON2 = "", input;
		String JSON = "";
		char[] json2_char;
		char[] json_final_char;
		extractJavaScript(driver, Repository.JAVA_SCRIPT_XPATH);
		input = fileData;
		int matches_val = StringUtils.countMatches(input, "\"questions\":");
		for (int i = 0; i < matches_val; i++) {
			// System.out.println(fileData);
			JSON2 = input.split("\"questions\":")[1].split("\"answers\":")[0];
			System.out.println(JSON2);
			System.out.println(input);
			input = input.replaceFirst("\"questions\":", "");
			System.out.println(input);
			// removing '[' from the starting index of the string
			json2_char = JSON2.trim().toCharArray();
			System.out.println(json2_char[0]);
			if (json2_char[0] == '[')
				json2_char[0] = ' ';
			JSON2 = String.valueOf(json2_char);
			// removing ']' from the ending index of the string
			json2_char = JSON2.toCharArray();
			int count = JSON2.length() - 2;
			System.out.println(json2_char[count]);
			if (json2_char[count] == ']')
				json2_char[count] = ' ';
			JSON2 = String.valueOf(json2_char).trim();
			System.out.println("REMOVED FIRST INDEX VALUE :" + JSON2);
			// Adding all the individual questions (json2 here) to JSON string
			JSON = JSON + JSON2;
		}
		
		LOGGER.info("Partialy filtered JSON Output: \n" + JSON);
		json_final_char = JSON.toCharArray();
		int count = JSON.length() - 1;
		System.out.println(json_final_char[count]);
		if (json_final_char[count] == ',') {
			json_final_char[count] = ' ';
		}
		JSON = String.valueOf(json_final_char).trim();
		String final_json = "{" + "\"questions\":" + "[" + JSON + "]" + "}";
		LOGGER.info("The Final JSON Question Set extracted from the raw JAVA Script is:" + final_json);
		generateQuestions(final_json);
	}
	@SuppressWarnings("unused")
	// Prepare Questions from the JSON File
	public static void generateQuestions(String questions_arr)
			throws Exception {
		Map<String, Object> elementsMap = new HashMap<String, Object>();
		mapper = new ObjectMapper();
		elementsMap = mapper.readValue(questions_arr,
				new TypeReference<HashMap<String, Object>>() {
				});
		@SuppressWarnings("unchecked")
		List<Object> questions = (List<Object>) elementsMap.get("questions");
		// Get all the list of questions
		System.out.println(questions);
		LOGGER.info("No. of questions in the page are : "
				+ questions.size());
		loop1 : for (Object question : questions) {
			// Navigate into each and every question and performing actions
			System.out.println("Attempting to answer the question: "+question);
			@SuppressWarnings("unchecked")
			Map<String, Object> questionMap = (Map<String, Object>) question;
			LOGGER.info("Attempting to answer the question with label name :"
					+ questionMap.get("label"));
			id = questionMap.get("id").toString();
			// Extract id from JSON
			visible = questionMap.get("visible").toString();
			// Extract visible
			label = questionMap.get("label").toString();
			// Extract label from JSON
			category = questionMap.get("category").toString();
			// Extract Category/type of webelement from JSON
			validations = (String) questionMap.get("validations");
			// Extracting options of a question
			options = questionMap.get("options").toString();
			name = questionMap.get("name").toString();
			backend_name = questionMap.get("backend_name").toString();
			source_name = questionMap.get("source").toString();
			resume = questionMap.get("resume").toString();
			System.out.println("The validations of this question are: "+validations);
			System.out.println("The options of this question are: "+options);
			LOGGER.info("Extracted the below basic objects for this question: " );
			LOGGER.info("*****Below are the basic objects for the label: "+label);
			LOGGER.info("ID of the Question is: "+id);
			LOGGER.info("Visibility of the Question is: "+visible);
			LOGGER.info("Category of the Question is: "+category);
			LOGGER.info("Validations of the Question are: "+validations);
			LOGGER.info("Options of the Question are: "+options);
			LOGGER.info("The name of the question is: "+ name);
			LOGGER.info("Back End name of this question is: "+backend_name);
			System.out.println("\n The id of the question: " + label + " is "
					+ id + "and category is:" + category);
			if (visible.contains("0") || visible.contains("-1")) {
				continue;
			}
			try{
			source_val = source_name.toString().replaceAll("\\{", "").replaceAll("\\}", "").toString();
			resume_val = resume_name.toString().replaceAll("\\[", "").replaceAll("\\]", "").toString();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			getOptionsFrmQuestions();
			getVaidationsFrmQuestions();
			getLengthFrmValidations();
			//**Removed getOptionsFrmQuestions(); and added it above getVaidationsFrmQuestions();
			selectWebelement();
		}
	}
	@SuppressWarnings("unchecked")
	public static void getVaidationsFrmQuestions() throws JsonParseException,
			JsonMappingException, IOException {
		validatonMap = new HashMap<String, Object>();
		validatonMap = mapper.readValue(validations,
				new TypeReference<HashMap<String, Object>>() {
				});
		// Extract Knockout from the validations
		// Extract compulsory from validations
		compulsory = validatonMap.get("compulsory").toString();
		System.out.println("The compulsory for this question is: "+compulsory);
		// Extracting knockout into an object
		Object knockout_val = validatonMap.get("knockout");
		Object required_val = validatonMap.get("required");
		@SuppressWarnings("unchecked")
		Map<String, Object> knockout_ans = (Map<String, Object>) knockout_val;
		Map<String, Object> required_ans = (Map<String, Object>) required_val;
		// Extracting indices value into a string
		if ((knockout_ans != null && !knockout_ans.isEmpty())
				|| (required_ans != null && !required_ans.isEmpty())) {
			try {
				// Indices value extracted
				indices_val = knockout_ans.get("indices").toString()
						.replaceAll("\\[", "").replaceAll("\\]", "").toString()
						.trim();
				System.out.println("The index value which may be selected is:"+indices_val);
				LOGGER.info("The label will be knocked out if we select the index value as: "+indices_val);
				knock_answer = Integer.parseInt(indices_val);
				System.out.println("The last index value is: "+index_last_val);
				System.out.println("The index value to be selected is: "+knock_answer);
				if(index_last_val!=1){ //***NEWLY ADDED CODE (removing knock_answer and adding 1 in place of it)
				LOGGER.info("This is a knock out question and user enters the knock out loop");
				System.out.println("Entered the knock out loop");
				knock_answer--;
				System.out.println("The index value to be selected after decrement is: "+knock_answer);
				LOGGER.info("Decrements the knock out value to 1 and the value that will be selected is: "+knock_answer);
				if (knock_answer <= 0) {
					// If knock_answer is '1' then it will be incremented twice
					LOGGER.info("The knock out value is less than or equal to 0 hence attempting to increment it by 2");
					knock_answer = knock_answer + 2;
					System.out.println("The index value to be selected after incremented by 2 is: "+knock_answer);
					LOGGER.info("Increments the knock out value to 2 and the value to be selected is: "+knock_answer);
				}
				}else{
					System.out.println("There is only one index value and it will only be selected");
					LOGGER.info("There is only one index value and it will only be selected");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				req_indices_val = required_ans.get("indices").toString()
						.replaceAll("\\[", "").replaceAll("\\]", "").toString();
				required_answer = Integer.parseInt(req_indices_val);
				System.out.println("The value of required is:"
						+ required_answer);
				LOGGER.info("This question requires a value to be selected and the value is: "+required_answer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void xpathForInputtag(String id, String category,
			int knockout_ans, int required_ans) {
		try {
			if (category.contains("text") || category.contains("tel")
					|| category.equalsIgnoreCase("num")
					|| category.equalsIgnoreCase("number")
					|| category.contains("Email")
					|| category.contains("password")
					|| category.contains("radio") || category.contains("check")) {
				String tagname = "input";
				System.out.println("The tagname has been generated as: "
						+ tagname);
				LOGGER.info("The tagname has been generated as: "+ tagname);
				if ((category.equalsIgnoreCase("radio")
						|| category.equalsIgnoreCase("check")) && !name.equals("authentication_mode")) {
					if (knockout_ans != 0) {
						System.out.println("Knock out exist and the index value that will be selected for this element is: "+knock_answer);
						LOGGER.info("Knock out exist and the index value that will be selected for this element is: "+knock_answer);
						xpath = "//" + tagname + "[contains(@id,'" + id
								+ "') and (@value='" + knock_answer + "')]";
						System.out.println(xpath);
						LOGGER.info("The generated xpath for the knock out case is: "+xpath);
						knock_answer = 0;
					} else if (required_ans != 0) {
						System.out.println("Required answer exist and the index value that will be selected for this element is: "+required_answer);
						LOGGER.info("Required answer exist and the index value that will be selected for this element is: "+required_answer);
						xpath = "//" + tagname + "[contains(@id,'" + id
								+ "') and (@value='" + required_answer + "')]";
						System.out.println(xpath);
						LOGGER.info("The generated for the required answer case is: "+xpath);
						required_answer = 0;
					}
					// }
					else if (required_ans == 0 || knockout_ans == 0) {
						System.out.println("The index value that will be selected for this element is: "+index_last_val);
						LOGGER.info("The index value that will be selected for this element is: "+index_last_val);
						xpath = "//" + tagname + "[contains(@id,'" + id
								+ "') and (@value='" + index_last_val + "')]"; //index to index_last_val
						System.out.println(xpath);
						LOGGER.info("The generated xpath for the normal question is: "+xpath);
					}
				} else if (category.contains("text")
						|| category.contains("tel") || category.contains("num")
						|| category.contains("number")
						|| category.contains("Email")) {
					xpath = "//" + tagname + "[contains(@id,'" + id + "')]";
					System.out.println(xpath);
					LOGGER.info("The generated xpath for the text box is: "+xpath);
				}
			}
		}//
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void xpathForOthertags(String id, String category,
			int knockout_ans, int required_ans) {
		try {
			if (category.equals("select") || category.contains("textarea")) {
				tagname = category;
				System.out.println("The tagname has been generated as: "
						+ tagname);
				LOGGER.info("The tagname for the webelement"+category+ " is : "+ tagname);
				xpath = "//" + tagname + "[contains(@id,'" + id + "')]";
				System.out.println(xpath);
				LOGGER.info("The xpath genertated for the dropdown or text area is: " +xpath);
			} else if (category.equals("label")) {
				xpath = null;
				LOGGER.info("The xpath genertated for the label is: " +xpath);
			} else if (category.equals("multiselect")) {
				tagname = "a";
				System.out.println("The tagname has been generated as: "
						+ tagname);
				LOGGER.info("The tagname for the webelement"+category+ " is : "+ tagname);
				String button = "-button";
				xpath = "//" + tagname + "[contains(@id,'" + id + button+ "')]";
				System.out.println(xpath);
				LOGGER.info("The xpath genertated for the multiselect is: " +xpath);
			} else if (category.equals("datetime")){
				tagname = "select";
				System.out.println("The tagname has been generated as: "
						+ tagname);
				LOGGER.info("The tagname for the webelement"+category+ " is : "+ tagname);
				String day = "_day";
				String month = "_month";
				String year = "_year";
				xpath = "//" + tagname + "[contains(@id,'" + id + "')]"; //NEWLY ADDED CODE
				xpath_day = "//" + tagname + "[contains(@id,'" + id + day +"')]";
				xpath_month = "//" + tagname + "[contains(@id,'" + id + month +"')]";
				xpath_year = "//" + tagname + "[contains(@id,'" + id + year +"')]";
				System.out.println("The day xpath is :"+xpath_day);
				LOGGER.info("Xpath for the drop down Day is: "+xpath_day);
				System.out.println("The month xpath is :"+xpath_month);
				LOGGER.info("Xpath for the drop down Month is: "+xpath_month);
				System.out.println("The year xpath is :"+xpath_year);
				LOGGER.info("Xpath for the drop down Year is: "+xpath_year);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("null")
	public static void getLengthFrmValidations() throws JsonParseException,
			JsonMappingException, IOException {
		if (category.contains("text") || category.equalsIgnoreCase("tel")
				|| category.equalsIgnoreCase("num")
				|| category.equalsIgnoreCase("number")
				|| category.equalsIgnoreCase("Email")
				|| category.equalsIgnoreCase("Password")) {
			// Extracting length from validations
			System.out.println("The length is: "+validatonMap.get("length"));
			String maxlength_for_quest = validatonMap.get("length").toString()
					.replaceAll("\\[", "").replaceAll("\\]", "").trim();
			try{
			if (!maxlength_for_quest.isEmpty() && maxlength_for_quest != null) {
				System.out.println("The max length is: "+maxlength_for_quest);
				LOGGER.info("The maximum length for this question is: "+maxlength_for_quest);
				String max[] = maxlength_for_quest.split("max=");
				String max_val[] = max[1].split("}");// }]
				if (max_val[0] != null && !max_val[0].isEmpty() && !max_val[0].contains(",")) { //**NEWLY ADDED CODE !max_val[0].contains(",")
					maximum = max_val[0];
				} else if (label.contains("\"Number\"")
						|| label.contains("\"Primary Phone\"")
						|| label.contains("\"Mobile\"")
						|| label.equalsIgnoreCase("\"Cell\"")) {
					maximum = "10";
				} else if (label.equalsIgnoreCase("\"Zip\"")
						|| label.equalsIgnoreCase("\"Postal Code\"")
						|| label.equalsIgnoreCase("\"Area Code\"")
						|| label.contains("\"Code\"")) {
					maximum = "9";
				} else if (label.equalsIgnoreCase("\"SSN\"")
						|| label.equalsIgnoreCase("\"Social Security Number\"")) {
					maximum = "10";
				}
				else{ //**NEWLY ADDED CODE
					String max_val_rem[] = max_val[0].split(",");
					maximum = max_val_rem[0];
				}
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void getOptionsFrmQuestions() throws JsonParseException,
			JsonMappingException, IOException {
		String options_json = "{" + "\"options\":" + options + "}";
		System.out.println(options_json);
		// Mapping options into a Map
		Map<String, Object> inner_opt_map = new HashMap<String, Object>();
		ObjectMapper map = new ObjectMapper();
		inner_opt_map = map.readValue(options_json,
				new TypeReference<HashMap<String, Object>>() {
				});
		// Getting all the options into a List
		@SuppressWarnings("unchecked")
		List<Object> option_s = (List<Object>) inner_opt_map.get("options");
		if (option_s != null)
			loop2 : for (Object option : option_s) {
				@SuppressWarnings("unchecked")
				Map<String, Object> optionMap = (Map<String, Object>) option;
					if(category.equals("radio") || category.equals("select") || category.equals("check") || category.equals("multiselect")){
					System.out.println("The index val is: "+optionMap.get("index"));
					index = optionMap.get("index").toString();
					index_last_val = Integer.parseInt(index);
					System.out.println("The final index value is: "+ index_last_val);
					}
					else if (category.equals("text")) {
					System.out.println("Get the type of text box: "+optionMap.get("type"));
					type = optionMap.get("type").toString();
				}
					else if(category.equals("file")){
					System.out.println("Get the key of CV upload"+optionMap.get("key"));
					key = optionMap.get("key").toString();
				}
			}
	}
	public static void selectWebelement() throws Exception {
		System.out.println("The category is: "+category);
		LOGGER.info("The category is: "+category);
		xpathForInputtag(id, category, knock_answer, required_answer);
		xpathForOthertags(id, category, knock_answer, required_answer);
		if (category.equals("file") && !name.equalsIgnoreCase("additional")) { //**NEWLY ADDED CODE name.equalsIgnoreCase("resume")
			if(Libutils.driver.findElements(By.xpath("//div[contains(@id,'indeed_text')]")).size()>0){
				System.out.println("The user has selected to apply from indeed and the indeed resume has been pre-populated");
				LOGGER.info("The user has selected to apply from indeed and the indeed resume has been pre-populated");
				//if (attachment_type !=""){
				if(!attachment_type.equalsIgnoreCase("none")){
				System.out.println("As the given attachment type is: "+attachment_type + " it will click on Use link");
				LOGGER.info("As the given attachment type is: "+attachment_type + "it will click on Use link");
				Libutils.driver.findElement(By.xpath("//a[contains(@id,'chooser')]")).click();
				LOGGER.info("Clicked on the Use link and the list of resume options are populated");
				Thread.sleep(2000);
				LOGGER.info("Waited for 2 second(s)");
				}
				else{
					System.out.println("User hasn't provided any attachment type");
					LOGGER.info("User hasn't provided any attachment type");
				}
			}
			LOGGER.info("Attempting to upload CV using: "+attachment_type);
			uploadAttachments(attachment_type, path_name);
		}
		if (name.equals("authentication_mode")){
			LOGGER.info("The user is on the Authentication page");
			if(auth_name.equals("autocreate")){
			LOGGER.info("User has selected to AUTO CREATE THE ACCOUNT");
			chooseSignUp("autocreate");
			LOGGER.info("Completed Auto Creating an account");
			} else if(auth_name.equals("createaccount")){
				LOGGER.info("User has selected to CREATE THE ACCOUNT");
				chooseSignUp("createaccount");
				LOGGER.info("Completed Creating an Account");
			}
			else if(auth_name.equals("signin")){
				LOGGER.info("User has selected to SIGN IN as a returning user");
				signInAsReturningUser();
				LOGGER.info("Returning user Sign In completed");
			}
		}
		if(xpath!=null){
		if(driver.findElements(By.xpath(xpath)).size()>0 && driver.findElement(By.xpath(xpath)).isEnabled()){
		selectAWebElement(driver, category, xpath, "google");
		System.out
				.println("Completed writing the answer for category question & its xapth :"
						+ category + xpath);
		LOGGER.info("Completed writing the answer for category question & its xapth :"+ category + xpath);
		}
		else{
			try{
			System.out.println("The element: "+xpath+" might be hidden or collapsed in the page");
			LOGGER.info("The element: "+xpath+" might be hidden or collapsed in the page");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		}
		else{
			System.out.println("Found XPATH as null - so can't do much");
			LOGGER.info("Found XPATH as null - so can't do much");
		}
	}
	public static void selectAWebElement(WebDriver driver, String category,
			String xpath, String attachment_type) throws Exception {
		if (xpath != null) {
			try {
				By by = By.xpath(xpath);
				element = driver.findElement(by);
			} catch (Exception e) {
				e.printStackTrace();
			}
			switch (category) {
				case "text" :
					if (type.equals("[text]") && !backend_name.equals("username") && !backend_name.equals("email") && !backend_name.contains("mail")) {//**NEWLY ADDED CODE !backend_name.equals("email")
						System.out.println("Entered the text box loop");
						String val = element.getAttribute("value");
						if (val.equals("")){
							LOGGER.info("Attempting to answer the question "+label);
							generateRandomString();
						}
						//Thread.sleep(1000);
					} else if (type.equals("[num]") || type.equals("[tel]")) {
						String val = element.getAttribute("value");
						if (val.equalsIgnoreCase("")){
							LOGGER.info("Attempting to answer the question "+label);
							generateRandomNumbers(maximum);
						}
						//Thread.sleep(1000);
					} else if (type.equals("[email]") || backend_name.equals("email") || backend_name.contains("mail")) {
						if((auth_name.equals("signin") || auth_name.equalsIgnoreCase("createaccount")) && name.equals("authentication_mode")){
							try{
							driver.findElement(By.xpath(xpath)).clear();
							driver.findElement(By.xpath(xpath)).sendKeys(email_address);
							}
							catch(Exception e){
								e.printStackTrace();
							}
						}
						else{
						try{
					    	LOGGER.info("Attempting to clear the question "+label);
					    	element.clear();
							verifyAndTypeEmailFrmExcel();
						Thread.sleep(1000);
						}
						catch (Exception e){
							e.printStackTrace();
						}
						}
					} else if (type.equals("[password]")){ //***NEWLY ADDED CODE
						if(backend_name.equals("password")){
						try{
						LOGGER.info("Attempting to clear the question "+label);
						driver.findElement(By.xpath(xpath)).clear();
						driver.findElement(By.xpath(xpath)).sendKeys(password);//***NEWLY ADDED CODE
					//	driver.findElement(By.xpath(xpath)).sendKeys(confirm_password);//***NEWLY ADDED CODE
						LOGGER.info("Attempting to answer the question "+label+" with value as: "+password);
						}
						catch (Exception e){
							e.printStackTrace();
						}
						}
						else if(backend_name.equals("password_confirmation")){
							try{
								LOGGER.info("Attempting to clear the question "+label);
								driver.findElement(By.xpath(xpath)).clear();
								driver.findElement(By.xpath(xpath)).sendKeys(confirm_password);//***NEWLY ADDED CODE
								LOGGER.info("Attempting to answer the question "+label+" with value as: "+password);
								}
								catch (Exception e){
									e.printStackTrace();
								}
						}
					} else if ((type.equals("[text]") || type.equals("[username]")) && backend_name.equals("username")){//***NEWLY ADDED CODE
						try{
						LOGGER.info("Attempting to clear the question "+label);
						driver.findElement(By.xpath(xpath)).sendKeys(username);//***NEWLY ADDED CODE
						LOGGER.info("Attempting to answer the question "+label+" with value as: "+username);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
					break;
				case "tel" :
				case "num" :
					generateRandomNumbers(maximum);
					Thread.sleep(1000);
					break;
				case "email" :
					verifyAndTypeEmailFrmExcel();
					Thread.sleep(1000);
					break;
				case "radio" :
					LOGGER.info("Attempting to answer the question "+label);
					verifyAndClick();
					LOGGER.info("The question "+label+" has been answered");
					Thread.sleep(1500);
					break;
				case "check" :
					String checked = element.getAttribute("checked");
					System.out.println("The status of the check box is: "+ checked);
					LOGGER.info("The status of the check box is: "+ checked);
					if(checked == null){
					LOGGER.info("Attempting to answer the question "+label);
					verifyAndClick();
					LOGGER.info("The question "+label+" has been answered");
					}
					Thread.sleep(1500);
					break;
				case "textarea" :
					LOGGER.info("Attempting to answer the question "+label);
					verifyAndTypeTextArea();
					break;
				case "select" :
					LOGGER.info("Attempting to answer the question "+label);
					verifyAndSelect();
					Thread.sleep(1500);
					break;
				case "multiselect" :
					element.click();
					verifyAndSelectMultiDropdown(Repository.MULTI_SELECT_DROPDOWN_SECOND_VALUE_XPATH);
					verifyAndSelectMultiDropdown(Repository.MULTI_SELECT_DROPDOWN_THIRD_VALUE_XPATH);
					verifyAndSelectMultiDropdown(Repository.MULTI_SELECT_DROPDOWN_DONE_XPATH);
					break;
				case "datetime" :
					if (name.equals("start_date") || source_name.contains("start_date") || resume.contains("start_date") || name.contains("start_date")) {
						try{
						LOGGER.info("Attempting to answer Start Date");
						verifyAndSelectDate(driver, xpath_day, "27");
						LOGGER.info("Selected the Day as: 27");
						verifyAndSelectDate(driver, xpath_month, "5");
						LOGGER.info("Selected the Month as: 5");
						verifyAndSelectDate(driver, xpath_year, "2000");
						LOGGER.info("Selected the Year as: 2009");
						}
						catch (Exception e){
							e.printStackTrace();
						}
					}
					else if (name.equals("end_date") || source_name.contains("end_date") || resume.contains("end_date") || name.contains("end_date")) {
						try{
						verifyAndSelectDate(driver, xpath_day, "28");
						LOGGER.info("Selected the Day as: 28");
						verifyAndSelectDate(driver, xpath_month, "6");
						LOGGER.info("Selected the Month as: 6");
						verifyAndSelectDate(driver, xpath_year, "2015");
						LOGGER.info("Selected the Year as: 2015");
						}
						catch (Exception e){
							e.printStackTrace();
						}
					}
					else if(name.equalsIgnoreCase("dob")||backend_name.equalsIgnoreCase("dob")){
						try{
							verifyAndSelectDate(driver, xpath_day, "27");
							LOGGER.info("Selected the Day as: 27");
							verifyAndSelectDate(driver, xpath_month, "7");
							LOGGER.info("Selected the Month as: 7");
							verifyAndSelectDate(driver, xpath_year, "1990");
							LOGGER.info("Selected the Year as: 1990");
							}
							catch (Exception e){
								e.printStackTrace();
							}
					}
					else{
						try{
							verifyAndSelectDate(driver, xpath_day, "28");
							LOGGER.info("Selected the Day as: 28");
							verifyAndSelectDate(driver, xpath_month, "8");
							LOGGER.info("Selected the Month as: 8");
							verifyAndSelectDate(driver, xpath_year, "2015");
							LOGGER.info("Selected the Year as: 2015");
							}
							catch (Exception e){
								e.printStackTrace();
							}
					}
					break;
				case "label" :
					break;
			}
		}
	}
	public static void generateRandomString() throws Exception {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		char c = chars[random.nextInt(chars.length)];
		sb.append(c);
		int i = 1;
		String sample = ExcelUtilities.sample.get(i);
		String output = sample + sb.toString();
		System.out.println(output);
		LOGGER.info("The value that has been auto generated is: "+output);
		try{
		if(element.isEnabled())
		element.clear();
		element.sendKeys(output);
		LOGGER.info("Answered the question " +label+" with value: "+output);
		Thread.sleep(1000);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void generateRandomNumbers(String count) {
		int count_value = Integer.parseInt(count);
		String randomNumbers = RandomStringUtils.randomNumeric(count_value);
		String number = randomNumbers; // 9 + randomNumbers {Changed here}
		System.out.println(number);
		LOGGER.info("The random numbers to be generated are: "+number);
		try{
		if (element.isEnabled())
			element.sendKeys(number);
			LOGGER.info("Answered the question " +label+" with value: "+number);
		Thread.sleep(1000);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void verifyAndTypeEmailFrmExcel() {
		email_string = ExcelUtilities.email_address.get(0).toString();
		LOGGER.info("Email Address that will be entered from the Excel Sheet is: "+email_string);
		if(element.isEnabled())
		element.sendKeys(email_string);
		LOGGER.info("Answered the question "+label+" with value as: " +email_string);
	}
	public static void verifyAndClick() {
		if(element.isEnabled())
		try {
			element.click();
			LOGGER.info("Clicked on the radio element");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void verifyAndSelectMultiDropdown(String id){
		try{
			By by = By.xpath(id);
			WebElement element = driver.findElement(by);
			element.click();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void verifyAndClickButton(String id){
		try {
			By by = By.cssSelector(id);
			WebElement element = driver.findElement(by);
			element.click();
			Thread.sleep(3000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	@SuppressWarnings("null")
	public static void verifyAndSelect() {
		try{
		if (knock_answer != 0) { // &&!backend_name.contains("security_question")
			// String value = ""+knock_answer;
			LOGGER.info("The question "+label+" is a knock out question");
			String value = Integer.toString(knock_answer);
			System.out.println("The value to be selected for this knock out is: "+value);
			new Select(element).selectByIndex(knock_answer);
			LOGGER.info("Selected the question "+label+" with index as: "+knock_answer);
		} else if (required_answer != 0) {
			// String value = ""+required_answer;
			LOGGER.info("The question "+label+" has a specific answered to be selected");
			String value1 = Integer.toString(required_answer);
			System.out.println(value1);
			new Select(element).selectByIndex(required_answer);
			LOGGER.info("Selected the question "+label+" with index as: "+required_answer);
		} else{
			LOGGER.info("The question "+label+" is a normal question");
			new Select(element).selectByIndex(Integer.parseInt(index));
			LOGGER.info("Selected the question "+label+" with index as: "+index);
		}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void verifyAndTypeTextArea() {
		try{
		textarea_string = ExcelUtilities.sample.get(0).toString();
		LOGGER.info("Attempting to enter the value: "+textarea_string);
		element.sendKeys(textarea_string);
		LOGGER.info("Entered the value into the question");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void verifyAndTypeCVTextArea(WebDriver driver, String id){
		try{
			cv_string = ExcelUtilities.sample.get(0).toString();
			By by = By.xpath(id);
			WebElement element = driver.findElement(by);
			element.sendKeys(cv_string);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
	public static void uploadAttachments(String attachment_type, String path_name) throws Exception {
	//	if ((name.equalsIgnoreCase("resume") || name.equals("")) && (!attachment_type.equalsIgnoreCase("none"))) { //NEWLY ADDED CODE removed  || name.contains("additional")
		if(!attachment_type.equalsIgnoreCase("none")){
			try{
			xpath = "//input[@type = 'radio' and @value = '"+attachment_type+"']";
			System.out.println(xpath);
			LOGGER.info("The xpath for the CV Upload option is: "+xpath);
			By by = By.xpath(xpath);
			cvupload = driver.findElement(by);
			Thread.sleep(1500);
			LOGGER.info("The attachment type element "+attachment_type+" has been recognized");
			cvupload.click();
			LOGGER.info("Clicked on the CV Upload option: "+attachment_type);
			switch (attachment_type) {
				case "google":
					LoginUtils.loginIntoResume(attachment_type, path_name);
					break;
				case "dropbox":
					LoginUtils.loginIntoResume(attachment_type, path_name);
					break;
				case "auto":
					break;
				case "email":
					break;
				case "paste":
					verifyAndTypeCVTextArea(driver, Repository.CV_PASTE_TEXTAREA_XPATH);
					break;
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void verifyAndSelectCheckBox(WebDriver driver, String id){
		try{
		By by = By.xpath(id);
		List<WebElement> element = driver.findElements(by);
		for (int i= 0; i<element.size(); i++){	
			try{
			String element_checked = element.get(i).getAttribute("checked");
			if(element_checked==null){
			element.get(i).click();
			}
			}
			catch(Exception ex){
				System.out.println("Hidden elements");
				ex.printStackTrace();
			}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static void verifyAndSelectDate(WebDriver driver, String id, String value){
		try{
			By by = By.xpath(id);
			WebElement element = driver.findElement(by);
			new Select(element).selectByValue(value);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
	public static void verifyAndClick(WebDriver driver, String id) {
		try {
			By by = By.cssSelector(id);
			WebElement element = driver.findElement(by);
			element.click();
			Thread.sleep(3000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void selectResume(WebDriver driver, String id){
		try{
			By by = By.linkText(id);
			WebElement element = driver.findElement(by);
			element.click();
			Thread.sleep(3000);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
	public static void generateAdminURL(String client_url){
		String split_url[] = client_url.split(".com");
			client_url_admin =  split_url[0]+".com/"+"admin";
			System.out.println(client_url_admin);
			if(apply_mode.equalsIgnoreCase("facebook")){
				facebook_admin_url = client_url_admin;
			}
			else if (apply_mode.equalsIgnoreCase("indeed")) {
				indeed_admin_url = client_url_admin;
			}
			else if (apply_mode.equalsIgnoreCase("linkedin")) {
				linkedin_admin_url = client_url_admin;
			}
			else if (apply_mode.equalsIgnoreCase("buildcv")) {
				buildcv_admin_url = client_url_admin;
			}
	}
	public static void getApplicationID(){
		String currentURL = driver.getCurrentUrl();
		System.out.println(currentURL);
		String C_URL[] = currentURL.split("application_id=");//Split URL into 2 parts with "applicatio_id=" as split
		System.out.println(C_URL.length);
		System.out.println(C_URL[1]);
		url_split = C_URL[1]; //Get the second part into a String
		String C_URL2[] = url_split.split("&status=");
		System.out.println(C_URL2.length);
		System.out.println("The Application ID is:"+C_URL2[0]);
		app_id = C_URL2[0]; //Get the Application ID into a variable
		if(apply_mode.equalsIgnoreCase("facebook")){
			facebook_app_id = app_id;
		}
		else if (apply_mode.equalsIgnoreCase("indeed")) {
			indeed_app_id = app_id;
		}
		else if (apply_mode.equalsIgnoreCase("linkedin")) {
			linkedin_app_id = app_id;
		}
		else if (apply_mode.equalsIgnoreCase("buildcv")) {
			buildcv_app_id = app_id;
		}
	}
	public static void appidndURLToArray(){
		app_id_list = new ArrayList<String>();
		app_id_list.add(facebook_app_id);
		app_id_list.add(indeed_app_id);
		app_id_list.add(linkedin_app_id);
		app_id_list.add(buildcv_app_id);
		client_url_list = new ArrayList<String>();
		client_url_list.add(facebook_admin_url);
		client_url_list.add(indeed_admin_url);
		client_url_list.add(linkedin_admin_url);
		client_url_list.add(buildcv_admin_url);
		System.out.println(app_id_list.size());
		System.out.println(client_url_list.size());
		try{
		for(int i = 0; i<app_id_list.size(); i++){
			System.out.println(app_id_list.get(i));
		}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		try{
		for(int j = 0; j<client_url_list.size(); j++){
			System.out.println(client_url_list.get(j));
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void applicationIDToExcel(int row_val, String client_url_admin) throws Exception{
		FileOutputStream fo = new FileOutputStream(applicationid_path_name);
		WritableWorkbook wb = Workbook.createWorkbook(fo);
		WritableSheet ws = wb.createSheet("ApplicationID", 3);
		try{
			String currentURL = driver.getCurrentUrl();
			System.out.println(currentURL);
			String C_URL[] = currentURL.split("application_id=");//Split URL into 2 parts with "applicatio_id=" as split
			System.out.println(C_URL.length);
			System.out.println(C_URL[1]);
			url_split = C_URL[1]; //Get the second part into a String
			String C_URL2[] = url_split.split("&status=");
			System.out.println(C_URL2.length);
			System.out.println("The Application ID is:"+C_URL2[0]);
			app_id = C_URL2[0]; //Get the Application ID into a variable
			Label ti = new Label(0, 0, "ListofID's");
			ws.addCell(ti);
			Label lab_url = new Label(0, row_val, client_url_admin);	
			Label lab_id = new Label(1, row_val, app_id);
			ws.addCell(lab_url);	
			ws.addCell(lab_id);
			wb.write();
			wb.close();
			}
			catch (Exception e){
				System.out.println("No need of writing app id");
			}
	}
	public static void writeDataToExcel() throws Exception{
		FileOutputStream fo = new FileOutputStream(applicationid_path_name);
		WritableWorkbook wb = Workbook.createWorkbook(fo);
		WritableSheet ws = wb.createSheet("ApplicationID", 3);
		try{
		for(int i = 0, j=0; i<app_id_list.size(); i++){
			if(app_id_list.get(i) != null){
			Label lab_url = new Label(0, j, client_url_list.get(i));	
			Label lab_id = new Label(1, j, app_id_list.get(i));
			ws.addCell(lab_url);
			ws.addCell(lab_id);
			j++;
			}else{
				System.out.println("There is no application id");
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		wb.write();
		wb.close();
	}
	public static void chooseSignUp(String auth_name){ //signup_type to auth_name
		try{
			if(auth_name.equals("autocreate")){
				autoGenerateMyAccount();
			}
			else if (auth_name.equals("createaccount")) {
				createMyAccount();
			}
			else if (auth_name.equals("signin")){
				signInAsReturningUser();
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static void autoGenerateMyAccount(){
		int auto_create_option = driver.findElements(By.xpath(Repository.AUTO_CREATE_ACCOUNT_XPATH)).size();
		System.out.println("Auto create option present in the page :" +auto_create_option);
		LOGGER.info("Auto Create account option is available in the page");
		if (auto_create_option>0) {
			LOGGER.info("Attempting to click on the Auto Create option");
			driver.findElement(By.xpath(Repository.AUTO_CREATE_ACCOUNT_XPATH)).click();
			LOGGER.info("Clicked on the Auto Create option");
		}
	}
	public static void createMyAccount() throws Exception{
		int create_account_option = driver.findElements(By.xpath(Repository.CREATE_MY_OWN_ACCOUNT_XPATH)).size();
		System.out.println("Create an account option is available in the page: "+create_account_option);
		LOGGER.info("Create an account option is available in the page");
		if(create_account_option>0){
			driver.findElement(By.xpath(Repository.CREATE_MY_OWN_ACCOUNT_XPATH)).click();
			FileInputStream fi = new FileInputStream(path_name);
			Workbook w = Workbook.getWorkbook(fi);
			Sheet s1 = w.getSheet(2);
			email_address = s1.getCell(6, 1).getContents();
			confirm_email_address = s1.getCell(6, 1).getContents();
			password = s1.getCell(6, 2).getContents();
			confirm_password = s1.getCell(6, 2).getContents();
			username = s1.getCell(6, 3).getContents();
			System.out.println(email_address + " " + password + " " + username);
			LOGGER.info("The Email Address for Creating an account is: "+email_address );
			LOGGER.info("The Username for Creating an account is: "+username );
			LOGGER.info("The Password for Creating an account is: "+password );
		}
	}
	public static void signInAsReturningUser() throws Exception{
		int signin_option = driver.findElements(By.xpath(Repository.SIGN_IN_XPATH)).size();
		System.out.println("Sign In option is available in the page: "+signin_option);
		LOGGER.info("Sign In option is available in the page");
		if(signin_option>0){
		driver.findElement(By.xpath(Repository.SIGN_IN_XPATH)).click();
		FileInputStream fi = new FileInputStream(path_name);
		Workbook w = Workbook.getWorkbook(fi);
		Sheet s1 = w.getSheet(2);
		email_address = s1.getCell(5, 1).getContents();
		username = s1.getCell(5, 1).getContents();
		password = s1.getCell(5, 2).getContents();
		System.out.println(email_address + " " + password);
		LOGGER.info("The Email Address/username to Sign In is: "+email_address );
		LOGGER.info("The Password to Sign In is: "+password );
		}
	}
	public static void selectApplyMode(String path_name, int sheet) throws Exception{
		LoggerClass();
		LOGGER.info("The path name of the Excel Sheet is : "+path_name);
		LOGGER.info("The sheet to be selected from the Excel Sheet is: "+sheet);
		FileInputStream fi = new FileInputStream(path_name);
		WorkbookSettings workbookSettings = new WorkbookSettings();
	    workbookSettings.setEncoding( "Cp1252" );
		Workbook w = Workbook.getWorkbook(fi, workbookSettings);
		Sheet s1 = w.getSheet(sheet);
		System.out.println("The flag value for Apply using Facebook is: "+s1.getCell(2, 4).getContents());
		LOGGER.info("The flag value for Apply using Facebook is : "+s1.getCell(2, 4).getContents());
		System.out.println("The flag value for Apply using Indeed is :"+s1.getCell(2, 5).getContents());
		LOGGER.info("The flag value for Apply using Indeed is   :"+s1.getCell(2, 5).getContents());
		System.out.println("The flag value for Apply using Linkedin is :"+s1.getCell(2, 6).getContents());
		LOGGER.info("The flag value for Apply using Linkedin is :"+s1.getCell(2, 6).getContents());
		System.out.println("The flag value for Apply using Build-CV is :"+s1.getCell(2, 7).getContents());
		LOGGER.info("The flag value for Apply using Build-CV is :"+s1.getCell(2, 7).getContents());
		for (int i = 4; i <= 7; i++) {
			System.out.println("The apply mode at row "+ i + " is: "+s1.getCell(1, i).getContents());
			LOGGER.info("The apply mode at row "+ i + " is: "+s1.getCell(1, i).getContents());
			if(s1.getCell(2, i).getContents().equalsIgnoreCase("yes") && s1.getCell(1, i).getContents().contains("Facebook")){
				System.out.println("Apply using Facebook will be selected and executed");
				LOGGER.info("*******************************************************************************************************************************");
				LOGGER.info("The flag value for Apply using Facebook is Yes hence it will be executed for the client: "+s1.getCell(0, i).getContents());
				LOGGER.info("*******************************************************************************************************************************");
				System.out.println("The job to be applied is: "+s1.getCell(3, i).getContents());
				LOGGER.info("The job to be applied is: "+s1.getCell(3, i).getContents());
				client_url = s1.getCell(0, i).getContents();
				job_name = s1.getCell(3, i).getContents();
				attachment_type = s1.getCell(4, i).getContents();
				resume_name = s1.getCell(5, i).getContents();
				auth_name = s1.getCell(6, i).getContents();
				columns = 0;
				row_val = 1;
				apply_mode = "facebook";
				generateAdminURL(client_url);
				System.out.println("The client URL is "+ client_url+ " ; the attachment type is: " + attachment_type + " and the resume to be uploaded is: "+resume_name);
				LOGGER.info("The resume upload option for this case is: "+attachment_type + "and the cv to be uploaded is :"+resume_name);
				LOGGER.info("Attempts to extract the Job Name from the Excel Sheet");
				LOGGER.info("The Job to be applied is: "+job_name);
				Libutils.driver = new FirefoxDriver();//****NEWLY ADDED LINE 
				LOGGER.info("Firefox browser has been opened");
				Libutils.driver.manage().window().maximize();
				LOGGER.info("Firefox browser has been maximized");
				Libutils.driver.get(client_url);//****NEWLY ADDED LINE
				LOGGER.info("The "+client_url+" has been opened");
				LOGGER.info("User is on the home page now");
				Libutils.verifyAndClick(Libutils.driver, Repository.SEARCH_JOBS_CSS_SELECTOR);//****NEWLY ADDED LINE
				LOGGER.info("Clicked on the Search Jobs button on the home page");
				LOGGER.info("User has landed on to the Search Criteria Page");
				LOGGER.info("The page will wait for 3 seconds");
				Thread.sleep(3000);//****NEWLY ADDED LINE
				Libutils.verifyAndClick(Libutils.driver, Repository.SEARCH_CSS_SELECTOR);//****NEWLY ADDED LINE
				LOGGER.info("Clicked on the Search button in the Search Criteria page");
				LOGGER.info("User has landed on the Job List page");
				LOGGER.info("The page will wait for 3 seconds");
				Thread.sleep(3000); //****NEWLY ADDED LINE
				LOGGER.info("Attempts to extract all the input data from the Excel Sheet from row '1'");
				ExcelUtilities.getInputDataFromExcel(Libutils.path_name, 1);//****NEWLY ADDED LINE
				selectAJobfromExcelSheet(job_name);
				verifyAndClickButton(Repository.APPLY_BUTTON_CSS_SELECTOR);
				LOGGER.info("Clicks on the APPLY button");
				LOGGER.info("User has landed on the Apply Scenarios page");
				verifyAndSelectCheckBox(driver, Repository.CHECKBOX_XPATH);//Selecting all the check boxes on the Apply page 
				verifyAndClickButton(Repository.APPLY_FACEBOOK_CSS_SELECTOR);
				LOGGER.info("Clicks on the Apply Facebook button");
				LOGGER.info("User has landed on the Facebook page to provide the Login Credentials");
				LoginUtils.loginIntoSNS(path_name, "Logins", "facebook");
				NavigationUtils.navigateToEachpage();//****NEWLY ADDED LINE
			}
			else if(s1.getCell(2, i).getContents().equalsIgnoreCase("yes") && s1.getCell(1, i).getContents().contains("Indeed")){
				System.out.println("Apply using Indeed will be selected and executed");
				LOGGER.info("*******************************************************************************************************************************");
				LOGGER.info("The flag value for Apply using Indeed is Yes hence it will be executed for the client: "+s1.getCell(0, i).getContents());
				LOGGER.info("*******************************************************************************************************************************");
				System.out.println("The job to be applied is: "+s1.getCell(3, i).getContents());
				LOGGER.info("The job to be applied is: "+s1.getCell(3, i).getContents());
				client_url = s1.getCell(0, i).getContents();
				job_name = s1.getCell(3, i).getContents();
				attachment_type = s1.getCell(4, i).getContents();
				resume_name = s1.getCell(5, i).getContents();
				auth_name = s1.getCell(6, i).getContents();
				columns = 1;
				row_val = 2;
				apply_mode = "indeed";
				generateAdminURL(client_url);
				System.out.println("The client URL is "+ client_url+ " ; the attachment type is: " + attachment_type + " and the resume to be uploaded is: "+resume_name);
				LOGGER.info("The resume upload option for this case is: "+attachment_type + "and the cv to be uploaded is :"+resume_name);
				LOGGER.info("Attempts to extract the Job Name from the Excel Sheet");
				LOGGER.info("The Job to be applied is: "+job_name);
				Libutils.driver = new FirefoxDriver();//****NEWLY ADDED LINE
				LOGGER.info("Firefox browser has been opened");
				Libutils.driver.manage().window().maximize();
				LOGGER.info("Firefox browser has been maximized");
				Libutils.driver.get(client_url);//****NEWLY ADDED LINE
				LOGGER.info("The "+client_url+" has been opened");
				LOGGER.info("User is on the home page now");
				Libutils.verifyAndClick(Libutils.driver, Repository.SEARCH_JOBS_CSS_SELECTOR);//****NEWLY ADDED LINE
				LOGGER.info("Clicked on the Search Jobs button on the home page");
				LOGGER.info("User has landed on to the Search Criteria Page");
				LOGGER.info("The page will wait for 3 seconds");
				Thread.sleep(3000);//****NEWLY ADDED LINE
				Libutils.verifyAndClick(Libutils.driver, Repository.SEARCH_CSS_SELECTOR);//****NEWLY ADDED LINE
				LOGGER.info("Clicked on the Search button in the Search Criteria page");
				LOGGER.info("User has landed on the Job List page");
				LOGGER.info("The page will wait for 3 seconds");
				Thread.sleep(3000); //****NEWLY ADDED LINE
				LOGGER.info("Attempts to extract all the input data from the Excel Sheet from row '2'");
				ExcelUtilities.getInputDataFromExcel(Libutils.path_name, 1);
				selectAJobfromExcelSheet(job_name);
				verifyAndSelectCheckBox(driver, Repository.CHECKBOX_XPATH);//Selecting all the check boxes on the Apply page 
				verifyAndClickButton(Repository.APPLY_BUTTON_CSS_SELECTOR);
				LOGGER.info("Clicks on the APPLY button");
				LOGGER.info("User has landed on the Apply Scenarios page");
				verifyAndSelectCheckBox(driver, Repository.CHECKBOX_XPATH);//Selecting all the check boxes on the Apply page 
				verifyAndClickButton(Repository.APPLY_INDEED_CSS_SELECTOR);
				LOGGER.info("Clicks on the Apply Indeed button");
				LOGGER.info("User has landed on the Indeed page to provide the Login Credentials");
				LoginUtils.loginIntoSNS(path_name, "Logins", "indeed");
				NavigationUtils.navigateToEachpage();//****NEWLY ADDED LINE
			}
			else if(s1.getCell(2, i).getContents().equalsIgnoreCase("yes") && s1.getCell(1, i).getContents().contains("Linkedin")){
				System.out.println("Apply using Linkedin will be selected and executed");
				LOGGER.info("*******************************************************************************************************************************");
				LOGGER.info("The flag value for Apply using Linkedin is Yes hence it will be executed for the client: "+s1.getCell(0, i).getContents());
				LOGGER.info("*******************************************************************************************************************************");
				System.out.println("The job to be applied is: "+s1.getCell(3, i).getContents());
				client_url = s1.getCell(0, i).getContents();
				job_name = s1.getCell(3, i).getContents();
				attachment_type = s1.getCell(4, i).getContents();
				resume_name = s1.getCell(5, i).getContents();
				auth_name = s1.getCell(6, i).getContents();
				columns = 2;
				row_val = 3;
				apply_mode = "linkedin";
				generateAdminURL(client_url);
				System.out.println("The client URL is "+ client_url+ " ; the attachment type is: " + attachment_type + " and the resume to be uploaded is: "+resume_name);
				Libutils.driver = new FirefoxDriver();//****NEWLY ADDED LINE
				LOGGER.info("Firefox browser has been opened");
				Libutils.driver.manage().window().maximize();
				LOGGER.info("Firefox browser has been maximized");
				Libutils.driver.get(client_url);//****NEWLY ADDED LINE
				LOGGER.info("The "+client_url+" has been opened");
				LOGGER.info("User is on the home page now");
				Libutils.verifyAndClick(Libutils.driver, Repository.SEARCH_JOBS_CSS_SELECTOR);//****NEWLY ADDED LINE
				LOGGER.info("Clicked on the Search Jobs button on the home page");
				LOGGER.info("User has landed on to the Search Criteria Page");
				LOGGER.info("The page will wait for 3 seconds");
				Thread.sleep(3000);//****NEWLY ADDED LINE
				Libutils.verifyAndClick(Libutils.driver, Repository.SEARCH_CSS_SELECTOR);//****NEWLY ADDED LINE
				LOGGER.info("Clicked on the Search button in the Search Criteria page");
				LOGGER.info("User has landed on the Job List page");
				LOGGER.info("The page will wait for 3 seconds");
				Thread.sleep(3000); //****NEWLY ADDED LINE
				LOGGER.info("Attempts to extract all the input data from the Excel Sheet from row '3'");
				ExcelUtilities.getInputDataFromExcel(Libutils.path_name, 1);
				selectAJobfromExcelSheet(job_name);
				verifyAndSelectCheckBox(driver, Repository.CHECKBOX_XPATH);//Selecting all the check boxes on the Apply page 
				verifyAndClickButton(Repository.APPLY_BUTTON_CSS_SELECTOR);
				LOGGER.info("Clicks on the APPLY button");
				LOGGER.info("User has landed on the Apply Scenarios page");
				verifyAndSelectCheckBox(driver, Repository.CHECKBOX_XPATH);//Selecting all the check boxes on the Apply page 
				verifyAndClickButton(Repository.APPLY_LINKEDIN_CSS_SELECTOR);
				LOGGER.info("Clicks on the Apply Linkedin button");
				LOGGER.info("User has landed on the Linkedin page to provide the Login Credentials");
				LoginUtils.loginIntoSNS(path_name, "Logins", "linkedin");
				NavigationUtils.navigateToEachpage();//****NEWLY ADDED LINE
			}
			else if (s1.getCell(2, i).getContents().equalsIgnoreCase("yes") && s1.getCell(1, i).getContents().contains("Build-CV")) {
				System.out.println("Apply using Build-CV will be selected and executed");
				LOGGER.info("*******************************************************************************************************************************");
				LOGGER.info("The flag value for Apply using BuildCV is Yes hence it will be executed for the client: "+s1.getCell(0, i).getContents());
				LOGGER.info("*******************************************************************************************************************************");
				System.out.println("The job to be applied is: "+s1.getCell(3, i).getContents());
				client_url = s1.getCell(0, i).getContents();
				job_name = s1.getCell(3, i).getContents();
				attachment_type = s1.getCell(4, i).getContents();
				resume_name = s1.getCell(5, i).getContents();
				auth_name = s1.getCell(6, i).getContents();
				columns = 3;
				row_val = 4;
				apply_mode = "buildcv";
				generateAdminURL(client_url);
				System.out.println("The client URL is "+ client_url+ " ; the attachment type is: " + attachment_type + " and the resume to be uploaded is: "+resume_name);
				Libutils.driver = new FirefoxDriver();//****NEWLY ADDED LINE
				LOGGER.info("Firefox browser has been opened");
				Libutils.driver.manage().window().maximize();
				LOGGER.info("Firefox browser has been maximized");
				driver.manage().deleteAllCookies();
				Libutils.driver.get(client_url);//****NEWLY ADDED LINE
				LOGGER.info("The "+client_url+" has been opened");
				LOGGER.info("User is on the home page now");
				Libutils.verifyAndClick(Libutils.driver, Repository.SEARCH_JOBS_CSS_SELECTOR);//****NEWLY ADDED LINE
				LOGGER.info("Clicked on the Search Jobs button on the home page");
				LOGGER.info("User has landed on to the Search Criteria Page");
				LOGGER.info("The page will wait for 3 seconds");
				Thread.sleep(3000);//****NEWLY ADDED LINE
				Libutils.verifyAndClick(Libutils.driver, Repository.SEARCH_CSS_SELECTOR);//****NEWLY ADDED LINE
				LOGGER.info("Clicked on the Search button in the Search Criteria page");
				LOGGER.info("User has landed on the Job List page");
				LOGGER.info("The page will wait for 3 seconds");
				Thread.sleep(3000); //****NEWLY ADDED LINE
				LOGGER.info("Attempts to extract all the input data from the Excel Sheet from row '4'");
				ExcelUtilities.getInputDataFromExcel(Libutils.path_name, 1);
				selectAJobfromExcelSheet(job_name);
				verifyAndClickButton(Repository.APPLY_BUTTON_CSS_SELECTOR);
				LOGGER.info("Clicks on the APPLY button");
				LOGGER.info("User has landed on the Apply Scenarios page");
				verifyAndSelectCheckBox(driver, Repository.CHECKBOX_XPATH);//Selecting all the check boxes on the Apply page 
				verifyAndClickButton(Repository.BUILD_CV_CSSSELECTOR);
				WaitStatements.waitforElementVisibility(Repository.CONTINUE_CSS_SELECTOR);
				NavigationUtils.navigateToEachpage();//****NEWLY ADDED LINE
			}
		}
		System.out.println("End of the Application scenarios");
		LOGGER.info("End of the Application scenarios");
		writeDataToExcel();
	}
	public static void selectAJobfromExcelSheet(String job_name) throws Exception{
		for (int scroll = 0; scroll < 50; scroll++) {
			try{
    		if (driver.findElements(By.partialLinkText(job_name)).size()>0) {
    		LOGGER.info("Attempting to find the Job Name "+job_name+" in the Job List page");
    		driver.findElement(By.partialLinkText(job_name)).click();	
    		LOGGER.info("The Job name : "+job_name+ " has been found and selected");
    		WaitStatements.waitforElementVisibility(Repository.APPLY_BUTTON_CSS_SELECTOR);
    		LOGGER.info("Waited for some to make sure that user has been landed on to the next page");
    		break;
			}else{
				LOGGER.info("Couldn't find the Job : "+job_name);
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy(0,500)", "");
				LOGGER.info("Scrolls down and searches for the job");
			      Thread.sleep(3000);
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
    	}
	}
	public static void verifyAndEnter(String id, String value){
		try{
		By by = By.cssSelector(id);
		WebElement element = driver.findElement(by);
		element.sendKeys(value);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void answerErrorMessages() throws InterruptedException, Exception{
		while ((driver.getCurrentUrl().contains(Repository.URL_CONTAINS_SUBMIT))){
		int ele_txtbox_size = driver.findElements(By.xpath(Repository.CHILD_ELE_TXT_XPATH)).size();
		int ele_dpdwn_size = driver.findElements(By.xpath(Repository.CHILD_ELE_DPDWN_XPATH)).size();
		int ele_txtarea_size = driver.findElements(By.xpath(Repository.CHILD_ELE_TXTAREA_XPATH)).size();
		int ele_radio_size = driver.findElements(By.xpath(Repository.CHILD_ELE_RADIO_XPATH)).size();
//		int securityques_size =  driver.findElements(By.xpath(Repository.SECURITY_QUES_XPATH)).size();
		System.out.println("The no. textboxes present to handle:"+ele_txtbox_size);
		LOGGER.info("The no. textboxes present to handle:"+ele_txtbox_size);
		System.out.println("The no. dropdowns present to handle:"+ele_dpdwn_size);
		LOGGER.info("The no. textboxes present to handle:"+ele_dpdwn_size);
		System.out.println("The no. textarea present to handle"+ele_txtarea_size);
		LOGGER.info("The no. textboxes present to handle:"+ele_txtarea_size);
		System.out.println("The no. radio buttons present to handle"+ele_radio_size);
		LOGGER.info("The no. textboxes present to handle:"+ele_radio_size);
//		System.out.println("There is an error message in the auth page to select unique security questions :" +securityques_size);
		if(ele_txtbox_size == 0 && ele_dpdwn_size == 0 && ele_txtarea_size == 0 && ele_radio_size == 0 ){
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd"+"_"+"HH:mm:ss");
			 Calendar cal = Calendar.getInstance();
			 System.out.println(dateFormat.format(cal.getTime()));
			 String time = dateFormat.format(cal.getTime()).toString();
			 String screenshot_name = log_path_name + time;
			FileUtils.copyFile(scrFile, new File(screenshot_name+".png"));
			LOGGER.info("A screenshot has been taken with the name: "+screenshot_name + " as this validation cannot be handled on this page");
			break;
		}
		int ele_size = driver.findElements(By.xpath(Repository.ERROR_VALIDATION_XPATH)).size();
		System.out.println(ele_size);
		if(ele_txtbox_size!=0){
		//for(int ele = 0; ele_txtbox_size>0; ele++){ //ele_size to ele_txtbox_size
			LOGGER.info("The number of validation errors to be handled for "+ele_txtbox_size+" textboxes");
			for(int ele=0; ele<ele_txtbox_size; ele++){
			int current_ele_size = driver.findElements(By.xpath(Repository.ERROR_VALIDATION_XPATH)).size();
			int current_ele_txtbx_size = driver.findElements(By.xpath(Repository.CHILD_ELE_TXT_XPATH)).size();
			System.out.println(current_ele_size);
			System.out.println(current_ele_txtbx_size);
			if(current_ele_txtbx_size!=0){
		List<WebElement> Element =	driver.findElements(By.xpath(Repository.CHILD_ELE_TXT_XPATH));
		String tb_id = Element.get(0).getAttribute("id");
		System.out.println(tb_id);
		LOGGER.info("Text box with the id "+tb_id+ " has been selected and this will be answered now");
		String text_box_xpath = "//input[@id='" + tb_id + "']";
		System.out.println(text_box_xpath);
		LOGGER.info("The xpath generated for the textbox id: "+tb_id+ " is: "+text_box_xpath);
		for(int j=0; j<ExcelUtilities.data_trial_error.size(); j++){
			driver.findElement(By.xpath(text_box_xpath)).clear();
			LOGGER.info("Clears the text box");
			System.out.println("The value to be entered into the text box  is: "+ ExcelUtilities.data_trial_error.get(j));
			LOGGER.info("The value that will be entered into the text box is: "+ ExcelUtilities.data_trial_error.get(j));
			driver.findElement(By.xpath(text_box_xpath)).sendKeys(ExcelUtilities.data_trial_error.get(j));
			LOGGER.info("The text box is answered");
			driver.findElement(By.cssSelector("#application-continue-button")).click();
			LOGGER.info("Clicks on the continue button");
			Thread.sleep(1000);
			if(driver.findElements(By.xpath(Repository.CHILD_ELE_TXT_XPATH)).size() < current_ele_txtbx_size){
				break;
			}
		}
			}
		}
		}
		else if(ele_dpdwn_size!=0){
			LOGGER.info("The number of validation errors to be handled for "+ele_dpdwn_size+" dropdowns");
			for(int ele = 0; ele<ele_dpdwn_size; ele++){
				int current_ele_dpdwn_size = driver.findElements(By.xpath(Repository.CHILD_ELE_DPDWN_XPATH)).size();
				System.out.println("The current drop down size is:"+current_ele_dpdwn_size);
				if(current_ele_dpdwn_size!=0){
					List<WebElement> Element =	driver.findElements(By.xpath(Repository.CHILD_ELE_DPDWN_XPATH));
					String dpdwn_id = Element.get(0).getAttribute("id");
					System.out.println(dpdwn_id);
					LOGGER.info("Dropdown with the id "+dpdwn_id+ " has been selected and this will be answered now");
					String dpdwn_xpath = "//select[@id='" + dpdwn_id + "']";
					System.out.println(dpdwn_xpath);
					LOGGER.info("The xpath generated for the dropdown id: "+dpdwn_id+ " is: "+dpdwn_xpath);
					int drpdwn_size = driver.findElements(By.xpath(dpdwn_xpath)).size();//**NEWLY ADDED CODE
					for(int j=0; j<drpdwn_size; j++){//**NEWLY ADDED CODE
						new Select(driver.findElement(By.xpath(dpdwn_xpath))).selectByIndex(1);
						LOGGER.info("Selects the dropdown with index as '1'");
						driver.findElement(By.cssSelector("#application-continue-button")).click();
						LOGGER.info("Clicks on the continue button");
						Thread.sleep(1000);
						if(driver.findElements(By.xpath(Repository.CHILD_ELE_DPDWN_XPATH)).size()< current_ele_dpdwn_size){
							break;
						}
					}
				}
			}
		}
		else if(ele_txtarea_size!=0){
			LOGGER.info("The number of validation errors to be handled for "+ele_txtarea_size+" textarea");
			for(int ele = 0; ele<ele_txtarea_size; ele++){
				int current_ele_txtarea_size = driver.findElements(By.xpath(Repository.CHILD_ELE_TXTAREA_XPATH)).size();
				System.out.println("The current text are size is:"+current_ele_txtarea_size);
				System.out.println(current_ele_txtarea_size);
				if(current_ele_txtarea_size!=0){
					List<WebElement> Element =	driver.findElements(By.xpath(Repository.CHILD_ELE_TXTAREA_XPATH));
					String txtarea_id = Element.get(0).getAttribute("id");
					System.out.println(txtarea_id);
					LOGGER.info("Text area with the id "+txtarea_id+ " has been selected and this will be answered now");
					String txtarea_xpath = "//textarea[@id='" + txtarea_id + "']";
					System.out.println(txtarea_xpath);
					LOGGER.info("The xpath generated for the dropdown id: "+txtarea_id+ " is: "+txtarea_xpath);
					for(int j=0; j<ExcelUtilities.data_trial_error.size(); j++){
						driver.findElement(By.xpath(txtarea_xpath)).clear();
						LOGGER.info("Clears the text box");
						System.out.println("The value to be entered into the text area  is: "+ ExcelUtilities.data_trial_error.get(j));
						LOGGER.info("The value that will be enetered into the text area is: "+ ExcelUtilities.data_trial_error.get(j));
						driver.findElement(By.xpath(txtarea_xpath)).sendKeys(ExcelUtilities.data_trial_error.get(j));
						LOGGER.info("The text area has been answered");
						driver.findElement(By.cssSelector("#application-continue-button")).click();
						LOGGER.info("Clicks on the continue button");
						if(driver.findElements(By.xpath(Repository.CHILD_ELE_DPDWN_XPATH)).size()< current_ele_txtarea_size){
							break;
						}
					}
				}
			}
		}
		else if(ele_radio_size!=0){
			LOGGER.info("The number of validation errors to be handled for "+ele_radio_size+" radiobuttons");
			for(int ele = 0; ele<ele_radio_size; ele++){
				int current_ele_radio_size = driver.findElements(By.xpath(Repository.CHILD_ELE_RADIO_XPATH)).size();
				System.out.println(current_ele_radio_size);
				if(current_ele_radio_size!=0){
					List<WebElement> Element =	driver.findElements(By.xpath(Repository.CHILD_ELE_RADIO_XPATH));
					String radio_id = Element.get(0).getAttribute("id");
					System.out.println(radio_id);
					LOGGER.info("Radio button(s) with the id "+radio_id+ " has been selected and this will be answered now");
					String id_split [] =radio_id.split("_");
					String final_radio_ele_id = id_split[3];
					System.out.println(final_radio_ele_id);
					String radio_xpath = "//input[contains(@id,'" + final_radio_ele_id + "')]";
					System.out.println(radio_xpath);
					LOGGER.info("The xpath generated for the radio button id: "+final_radio_ele_id+ " is: "+radio_xpath);
					int radio_size = driver.findElements(By.xpath(radio_xpath)).size();
					for(int j=1; j<=radio_size; j++){
						int radio_value = j;
						System.out.println(radio_value);
						String new_radio_xpath = "//input[contains(@id,'" + final_radio_ele_id + "') and (@value='"+radio_value+"')]";
						System.out.println(new_radio_xpath);
						LOGGER.info("The xpath generated for the radio button and it's answer is: "+new_radio_xpath);
						driver.findElement(By.xpath(new_radio_xpath)).click();
						LOGGER.info("The radio button has been selected");
						driver.findElement(By.cssSelector("#application-continue-button")).click();
						LOGGER.info("Clicks on the continue button");
						if(driver.findElements(By.xpath(Repository.CHILD_ELE_RADIO_XPATH)).size()< current_ele_radio_size){
							break;
						}
					}
				}
			}
		}
		}//while
	}
	public static void checkApplicationStatus(){
		LOGGER.info("Attempting to check the status of the applications using their application id's");
		try{
			FileInputStream fi = new FileInputStream(applicationid_path_name);
			Workbook w = Workbook.getWorkbook(fi);
			Sheet s1 = w.getSheet(0);
			LOGGER.info("Gets the first excel sheet from the file name: "+applicationid_path_name);
			for(int rows = 0; rows < 4; rows++){
				String client_name = s1.getCell(0, rows).getContents();
				System.out.println("The client name at: "+rows+" is: "+client_name);
				if(client_name.length()>0 && !client_name.contains("test")){
					String app_id_val = s1.getCell(1, rows).getContents();
					System.out.println("The application id for the client name: "+client_name+" is: "+app_id_val);
					LOGGER.info("The application id for the client name: "+client_name+" is: "+app_id_val);
					//HtmlUnitDriver driver = new HtmlUnitDriver();
					WebDriver driver = new FirefoxDriver();
					driver.get(client_name);
					LOGGER.info("Attempts to open the admin part using the URL: "+client_name);
					  driver.findElement(By.id(EMAIL_ID)).sendKeys("santosh+automationtesting@indeed.com");
					  driver.findElement(By.id(PASSWORD_ID)).sendKeys("Pass@w0rd!");
					  LOGGER.info("Attempts to login by providing the default login credentials");
					  driver.findElement(By.xpath(SUBMIT_BUTTON_XPATH)).click();
					  LOGGER.info("Clicks on the submit button");
					  Thread.sleep(3000);
					  driver.findElement(By.xpath(BROWSE_APPLICATIONS_XPATH)).click();
					  LOGGER.info("Clicks on the Browse Applications button");
					  Thread.sleep(3000);
					  try{
						 	driver.findElement(By.linkText(app_id_val)).click();
						 	LOGGER.info("Clicks on the application id");
						 	for(int i = 0; i<5; i++){
						 	status_text = driver.findElement(By.xpath(STATUS_TEXT_XPATH)).getText();
						 	sub_status_text = driver.findElement(By.xpath(SUBSTATUS_TEXT_XPATH)).getText();
						 	LOGGER.info("The status of the application is: "+ status_text+ " and sub status of the application is: "+sub_status_text);
								if(!status_text.equalsIgnoreCase("Processing") && !sub_status_text.equalsIgnoreCase("wta")){
						 		if (status_text.equalsIgnoreCase("Succeeded") || sub_status_text.equalsIgnoreCase("success")) {
									System.out.println("The job application has been successful");
									LOGGER.info("The job application has been successful");
									driver.close();
									break;
								}
							 	else if (status_text.equalsIgnoreCase("Queued") && sub_status_text.equalsIgnoreCase("waiting for attachment")) {
									System.out.println("Please send the resume as an email attachment and check for application success");
									LOGGER.info("Please send the resume as an email attachment and check for application success");
									driver.close();
									break;
								}
							 	else if(status_text.equalsIgnoreCase("Succeeded") && sub_status_text.equalsIgnoreCase("Previous")){
							 		System.out.println("The user has already applied to this job");
							 		LOGGER.info("The user has already applied to this job");
							 		driver.close();
							 		break;
							 	}
							 		else{
									System.out.println("The job application is a failure");
									LOGGER.info("The job application is a failure");
								}
								}else{
									System.out.println("The application is under procesing state it will retry after some time");
									LOGGER.info("The application is under procesing state it will retry after some time");
									Thread.sleep(240000);
									LOGGER.info("The application has waited for 4 minutes....");
									driver.navigate().refresh();
									LOGGER.info("The page has been refreshed to check for the latest status");
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
