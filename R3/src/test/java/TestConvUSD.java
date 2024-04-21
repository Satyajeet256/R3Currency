import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class TestConvUSD {
	@Test
	public void USDRateConversion() {

		/// Test URL Status
		baseURI = "https://open.er-api.com/v6/latest";
		given().get("/USD").then().statusCode(200);

		/// test for Results section
		given().get("/USD").then().statusCode(200).body("result", equalTo("success"));

		//// Test for AED result within range
		float aed_rate = given().get("/USD").then().statusCode(200).extract().path("rates.AED");
		float expectedValue = 3.6f;
		float tolerance = 0.1f;

		assertTrue(Math.abs(aed_rate - expectedValue) <= tolerance);

		/// Test for rates are 162
		given().get("/USD").then().statusCode(200).body("rates.size()", equalTo(162));

//	      Test for schema 
		given().get("/USD").then().assertThat().body(matchesJsonSchemaInClasspath("schema.json"));

	}

}
