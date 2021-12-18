package com.upgrade.tests;

import com.upgrade.api.ApiRequest;
import com.upgrade.pages.BasePage;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.log4j.PropertyConfigurator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class AbstractTest {
	private WebDriver driver;
	private ApiRequest apiRequest;
	protected BasePage page;



	@BeforeMethod
	public void beforeTest() {
		PropertyConfigurator.configure(AbstractTest.class.getClassLoader().getResourceAsStream("log4j.properties"));
		page = new BasePage(getDriver()); 
		BasePage.getDp().clear();
	}

	@AfterMethod(alwaysRun = true)
	public void afterTest() {
		if (driver != null) {
			driver.quit();
		}
	}

	public WebDriver getDriver() {

		boolean hasQuit = driver == null || driver.toString().contains("(null)");
		if (hasQuit == true) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		return driver;
	}

	public ApiRequest apiRequest() {
		if (apiRequest == null) {
			apiRequest = new ApiRequest();
		}
		return apiRequest;
	}

}