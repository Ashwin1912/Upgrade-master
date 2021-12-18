package com.upgrade.tests;

import com.github.javafaker.Faker;

import com.upgrade.pages.AdversePage;
import com.upgrade.pages.BasePage;
import com.upgrade.pages.LandingPage;
import com.upgrade.pages.PortalPage;
import com.upgrade.pages.SelectOfferPage;
import com.upgrade.pages.SignInPage;
import com.upgrade.pojos.Borrower;

import lombok.extern.log4j.Log4j;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.text.SimpleDateFormat;

import java.util.Locale;

@Log4j
public class LoanOffersUITest extends AbstractTest {
	private static final String url = "https://www.credify.tech";
	Borrower borrower = getRandomTestBorrower();		


	/*
        Please refer README.md for more details on
        Case # 1 : Validate offers after re-login
	 */

	@Test
	public void validateOffersTest() throws Exception {

		borrower.setYearlyIncome(generateRandomNumberFromRange(150000,200000));
		borrower.setAdditionalIncome(generateRandomNumberFromRange(5000,10000));
		borrower.setEmail(String.format("coding.%s@upgrade-challenge.com", generateRandomNumberFromRange(15000000, 20000000)));

		Object offerLoanAmount;
		Object monthlyPayment;
		Object loanTerm;
		Object loanInterestRate;

		//Capture offer details in the Offers page
		page.getInstance(LandingPage.class)
		.gotoLandingPage(url)
		.enterLoanDetails(borrower)
		.enterContactDetails(borrower)
		.enterIncomeDetails(borrower)
		.enterLoginDetails(borrower,SelectOfferPage.class)
		.getOfferPageDetails()
		.clickSignOut();

		//Capturing the details from offer page in hash map
		offerLoanAmount = page.getDp("userLoanAmount");
		monthlyPayment = page.getDp("monthlyPayment");
		loanTerm = page.getDp("loanTerm");
		loanInterestRate = page.getDp("loanInterestRate");


		//Validate the offer details after login//used generic for page instantiation		
		page.getInstance(SignInPage.class)
		.gotoSignInPage(url)
		.signIn(borrower)
		.getOfferPageDetails()
		.clickSignOut();


       //Validate the LoanAmount, Monthly payment, Loan term and Loan interest rate 
		Assert.assertEquals(offerLoanAmount.toString(), page.dpValues("userLoanAmount"), "LoanAmount is incorrect in offer page");
		Assert.assertEquals(monthlyPayment.toString(), page.dpValues("monthlyPayment"), "Monthly payment is incorrect in offer page");
		Assert.assertEquals(loanTerm.toString(), page.dpValues("loanTerm"), "Loan term is incorrect in offer page");
		Assert.assertEquals(loanInterestRate.toString(), page.dpValues("loanInterestRate"), "Loan interest rate is incorrect in offer page");
	}

	/*
        Please refer README.md for more details on
        Case # 2  : Loan rejected for low annual income
	 */

	@Test
	public void validateDeclineLoanTest(){

		borrower.setYearlyIncome(generateRandomNumberFromRange(100,1000));
		borrower.setAdditionalIncome(generateRandomNumberFromRange(100,500));
		borrower.setEmail(String.format("coding.%s@upgrade-challenge.com", generateRandomNumberFromRange(15000000, 20000000)));



		//Capture offer details in the Offers page
		page.getInstance(LandingPage.class)
		.gotoLandingPage(url)
		.enterLoanDetails(borrower)
		.enterContactDetails(borrower)
		.enterIncomeDetails(borrower)
		.enterLoginDetails(borrower,AdversePage.class)
		.getLoanDenialText()		
		.clickHereLink()
		.currentUrl(PortalPage.class)
		.getLoanId(page.dpValues("PortalPageUrl"))
		.adverseActionNoticeLnkPresent();

	    //Validate the Loan denial text, Portal page url and Adverse notice link
		Assert.assertEquals(page.dpValues("loanDenialText"), "We can't find you a loan offer yet, but you still have great options"
				,"Incorrect loan not approved message");		
		Assert.assertTrue(page.dpValues("PortalPageUrl").contains("/portal/product/"+ page.getDp("loanId").toString()+"/documents"),
				"Incorrect portal Url");
		Assert.assertTrue(page.dpValues("adverseActionNoticeLnkPresent").contains("true"),"Adverse notice link");		

	}

	private Borrower getRandomTestBorrower() {
		Borrower borrower = new Borrower();
		Faker faker = new Faker(new Locale("en-US"));

		borrower.setFirstName(faker.name().firstName());
		borrower.setLastName(faker.name().lastName());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		borrower.setDob(simpleDateFormat.format(faker.date().birthday(22,92)));//make sure DOB falls in between 1930 and 200
		borrower.setCity(faker.address().city());
		borrower.setPassword("System@987");
		borrower.setZipCode(faker.address().zipCode());
		borrower.setStreet(faker.address().streetAddress());
		borrower.setState("CA");
		borrower.setLoanPurpose("Home Improvement");
		borrower.setDesiredLoanAmount(generateRandomNumberFromRange(5000, 10000));
		return borrower;
	}

	private BigDecimal generateRandomNumberFromRange(int min, int max) {
		return BigDecimal.valueOf(Math.random() * (max - min + 1) + min).setScale(0, RoundingMode.DOWN);
	}

}
