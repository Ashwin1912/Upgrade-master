package com.upgrade.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.upgrade.pojos.lead.LeadSecretRequest;
import com.upgrade.pojos.lead.LeadSecretResponse;

@Log4j
public class ApiRequest {

	private String requestUrl;
	private Response response;
	private ContentType contentType;
	private Map<String, String> headers = new HashMap<>();
	private Headers header;
	private RequestSpecification requestSpecification = RestAssured.given();


	public ApiRequest post(Object body, Integer responseCode) {
		log.info("Calling POST: " + requestUrl);


		response = this.requestSpecification
				.contentType(contentType)
				.headers(header)
				.body(body)
				.log()
				.body(true)
				.log()
				.headers()
				.when()
				.post(requestUrl);

		try {
			response
			.then()
			.statusCode(responseCode);
		} catch (AssertionError e) {
			throw new RuntimeException("Expected response code did not match for a Post request to: " + requestUrl +
					"\n with status : \n" + response.statusLine(), e);
		}

		response
		.then()
		.log()
		.body(true);

		return this;
	}

	public ApiRequest setRequestUrl(String url) {
		this.requestUrl = url;
		return this;
	}

	public ApiRequest setContentType(ContentType contentType) {
		this.contentType = contentType;
		return this;
	}

	public Response getResponse() {
		return response;
	}

	//To return the satus code
	public int getStatusCode(){
		return response.getStatusCode();
	}

	public ApiRequest addHeader(String key, String value) {
		this.headers.put(key, value);
		return this;
	}

	public ApiRequest leadSecretHeaders() {
		Header header1 = new Header ("x-cf-corr-id", UUID.randomUUID().toString());
		Header header2 = new Header ("x-cf-source-id", "coding-challenge");
		this.header = new Headers (header1, header2);
		return this;		
	}

	public LeadSecretResponse response (Object leadSecretRequest, Integer statusCode, String url) {

		return leadSecretHeaders()
				.setContentType(ContentType.JSON)
				.setRequestUrl(String.format("%s%s", url, "v2/resume/byLeadSecret"))
				.post(leadSecretRequest, statusCode)
				.getResponse()
				.as(LeadSecretResponse.class);

	}

	public LeadSecretRequest request (UUID loanAppUuid, Boolean sideEffect) {

		return LeadSecretRequest.builder()
				.loanAppUuid(loanAppUuid)
				.skipSideEffects(sideEffect)
				.build();
	}

}
