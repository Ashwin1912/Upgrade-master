package com.upgrade.pages;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import lombok.extern.log4j.Log4j;

@Log4j
public class PortalPage extends BasePage {

	@FindBy(css = "table[class='sc-ehALMs iMblqV sc-ckXLN pwxfb table table--row-bordered']>tbody tr:nth-child(5) td")
	private WebElement adverseActionNoticeLnk;

	HashMap<String, Object> PortalPageDetails = new HashMap<String, Object>();


	public PortalPage(WebDriver driver) {
		super(driver);
	}

	public  PortalPage adverseActionNoticeLnkPresent() {

		Boolean adverseActionNoticeLnkPresent = waitForElementToBeDisplayed(adverseActionNoticeLnk);
		setDp("adverseActionNoticeLnkPresent", adverseActionNoticeLnkPresent);
		return this;
	}

	public PortalPage getLoanId(String url) {
		setDp("loanId",url.substring(40,url.lastIndexOf("documents")-1));			 
		return this;

	}

}
