package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

class TestCreateUser {

	// this is not set up as of now
	private final String CONTEXT_PATH = "/mobile-app-ws";

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
	}

	@Test
	void testCreateUser() {
		List<Map<String, Object>> userAddresses = new ArrayList<>();

		Map<String, Object> shippingAddress = new HashMap<>();
		shippingAddress.put("city", "Vancouver");
		shippingAddress.put("country", "Canada");
		shippingAddress.put("streetName", "123 Street name");
		shippingAddress.put("postalCode", "123456");
		shippingAddress.put("type", "shipping");

		Map<String, Object> billingAddress = new HashMap<>();
		billingAddress.put("city", "Vancouver");
		billingAddress.put("country", "Canada");
		billingAddress.put("streetName", "123 Street name");
		billingAddress.put("postalCode", "123456");
		billingAddress.put("type", "billing");

		userAddresses.add(shippingAddress);
		userAddresses.add(billingAddress);

		Map<String, Object> userDetails = new HashMap<>();
		userDetails.put("firstName", "Viktar");
		userDetails.put("lastName", "Kava");
		userDetails.put("email", "test@test.com");
		userDetails.put("password", "123");
		userDetails.put("addresses", userAddresses);

		Response response = given().contentType("application/json").accept("application/json").body(userDetails).when()
				.post("/users").then().statusCode(200).contentType("application/json").extract().response();

		String userId = response.jsonPath().getString("userId");
		assertNotNull(userId);
	}

}
