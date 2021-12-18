package com.upgrade.tests;

import com.upgrade.pojos.lead.LeadSecretRequest;
import com.upgrade.pojos.lead.LeadSecretResponse;
import lombok.extern.log4j.Log4j;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;


@Log4j
public class LeadSecretApiTest extends AbstractTest {

	private UUID loanAppUuid = UUID.fromString("b8096ec7-2150-405f-84f5-ae99864b3e96");
	private String url = "https://credapi.credify.tech/api/brfunnelorch/";
	private LeadSecretResponse response;
	private LeadSecretRequest request;

	/*
        Please refer README.md for more details on APT Test
	 */
	@Test
	public void leadSecretTest() {

		//Building the request body

		request = apiRequest().request(loanAppUuid, true);

		//Response
		response= apiRequest().response(request, 200, url);

		//Verify the https response code, personal loan attribute and first name value
		Assert.assertTrue(apiRequest().getStatusCode()==200,"Http response code is not 200");
		Assert.assertTrue(response.getLoanAppResumptionInfo().getProductType().equals("PERSONAL_LOAN"),"Response is not having PERSONAL_LOAN attribute");
		Assert.assertTrue(response.getLoanAppResumptionInfo().getBorrowerResumptionInfo().getFirstName().equals("Benjamin"),"First name validation failed");

	}

	@Test
	public void invalidLoanAppId() {
		
	    //Building the request body
		request = apiRequest().request(UUID.randomUUID(), true);
		
		//Response
		response= apiRequest().response(request, 404, url);
		
		//Verify the https response code
		Assert.assertTrue(apiRequest().getStatusCode()==404,"Http response code is not 404");
		

	}


}
