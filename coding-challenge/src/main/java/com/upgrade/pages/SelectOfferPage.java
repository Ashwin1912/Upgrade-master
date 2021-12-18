package com.upgrade.pages;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.HashMap;

@Log4j
public class SelectOfferPage extends FunnelBasePage {

	@FindBy(css = "[data-auto='getDefaultLoan']")
	private WebElement continueBtn;
   
	@FindBy(css = "[data-auto='userLoanAmount']")
	private WebElement userLoanAmount;
    
	@FindBy(css = "li>div[data-auto='defaultMonthlyPayment']")
	private WebElement monthlyPayment;

	@FindBy(css = "li[data-auto='defaultLoanTerm']>div")
	private WebElement loanTerm;

	@FindBy(css = "li[data-auto='defaultLoanInterestRate']>div")
	private WebElement loanInterestRate;
	
    HashMap<String, Object> offerPageDetails = new HashMap<String, Object>();
	

	public SelectOfferPage(WebDriver driver) {
		super(driver);
		waitForWebElements(Arrays.asList(continueBtn));
	}


	public String getUserLoanAmount() {
		return userLoanAmount.getText();

	}

	public SelectOfferPage  getOfferPageDetails() {
		setDp("userLoanAmount", userLoanAmount.getText());
		setDp("monthlyPayment", monthlyPayment.getText());
		setDp("loanTerm", loanTerm.getText());
		setDp("loanInterestRate", loanInterestRate.getText());
		return this;

	}
}
