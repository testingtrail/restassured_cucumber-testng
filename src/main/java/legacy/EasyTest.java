//This is a simple and separate example of the code
// this is simpler POST request


package legacy;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EasyTest {
	@Test
	public void secondhttpPost() throws JSONException,InterruptedException {

		//Rest API's URL
		RestAssured.baseURI = "http://opta-address.testmsenv-k8s.optaservice.com";
		RequestSpecification httpRequest = RestAssured.given();

		// create the JSON array
		JSONArray reqParamsArray = new JSONArray();

		// create the JSON object
		JSONObject reqParams = new JSONObject();
		reqParams.put("Line","389 CONNAUGHT AVE");
		reqParams.put("MunicipalityName","NORTH YORK");
		reqParams.put("StateProvinceCode","on");
		reqParams.put("PostalCodeValue","M2R2M1");
		reqParams.put("RequestID","1234");
		reqParams.put("RequestID","Profile");
		reqParams.put("Source","osb");

		//add the object to the array
		reqParamsArray.put(reqParams);

		//Add the header
		httpRequest.header("Content-Type","application/json");

		// add the JSON as body for the request
		httpRequest.body(reqParamsArray.toString());

		// get the response from post method
		Response response = httpRequest.post();

		// now let's print the response
		String responseBody = response.getBody().asString();
		System.out.println("Here: " + responseBody);


		//Get the desired value of a parameter
		//String result = JSONResponseBody.getString({key});

		//Check the Result
		//Assert.assertEquals(result, "{expectedValue}");

	}

}
