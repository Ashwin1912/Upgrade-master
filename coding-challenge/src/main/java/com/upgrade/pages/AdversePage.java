package com.upgrade.pages;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import lombok.extern.log4j.Log4j;

@Log4j
public class AdversePage extends FunnelBasePage {

	HashMap<String,Object> adversePagedetails = new HashMap<String,Object>();

	@FindBy(css = "div[class='col-xs-12 col-md-6']>h2")
	private WebElement loanDenialTxt;

	@FindBy(linkText = "click here")
	private WebElement ClickHereLnk;

	public AdversePage(WebDriver driver) {
		super(driver);
	}

	public AdversePage getLoanDenialText() {
		setDp("loanDenialText", loanDenialTxt.getText());
		return this;

	}
	
	public PortalPage clickHereLink() {
		click(ClickHereLnk);
		return new PortalPage(driver);
		
	}


}
