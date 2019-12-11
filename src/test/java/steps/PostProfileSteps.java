package steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import legacy.RestAssuredExtension;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class PostProfileSteps {

    public static ResponseOptions<Response> response;

    @Given("^I perform POST operation for \"([^\"]*)\"$")
    public void iPerformPOSTOperationFor(String arg0) throws Throwable {
        BDDStyleMethod.PerformPOSTWithBodyParameter();
    }

    /**
     * This one perform a POST request. This one is using old RestAssuredExtension.
     * @param url
     * @param table
     * @throws Throwable
     */
    @Given("^I Perform POST operation for \"([^\"]*)\" with body$")
    public void iPerformPOSTOperationForWithBody(String url, DataTable table) throws Throwable {
        List<List<String>> data = table.raw();

        //set body
        HashMap<String, String> body = new HashMap();
        body.put("name", data.get(1).get(0));

        //Path Parameter
        HashMap<String, String> pathParams = new HashMap();
        pathParams.put("profileNo", data.get(1).get(1));

        //Perform post operation
        response = RestAssuredExtension.POSTOpsWithBodyAndPathParams(url, pathParams, body);
    }

    /**
     * This one checks a response to check a JSON response and look for the name.
     * @param name
     * @throws Throwable
     */
    @Then("^I should see the body name as \"([^\"]*)\"$")
    public void iShouldSeeTheBodyNameAs(String name) throws Throwable {
        //had to create the following line to get the elements, I could not put it directly on assertThat
        Object element = response.getBody().jsonPath().get("name");
        assertThat(element.toString(),equalTo(name));
    }

    /**
     * This created a POST request with body. This one is using RestAssuredExtension.
     * @param url
     * @param table
     * @throws Throwable
     */
    @Given("^I ensure to Perform POST operation for \"([^\"]*)\" with body as$")
    public void iEnsureToPerformPOSTOperationForWithBodyAs(String url, DataTable table) throws Throwable {
        List<List<String>> data = table.raw();

        //set body
        HashMap<String, String> body = new HashMap();
        body.put("id", data.get(1).get(0));     //row 1 as 0 are the headers
        body.put("title", data.get(1).get(1));
        body.put("author", data.get(1).get(2));

        //perform post
        RestAssuredExtension.PostOpsWithBody(url, body);
    }

    /**
     * This created a DELETE request with body. This one is using RestAssuredExtension.
     * @param url
     * @param table
     * @throws Throwable
     */
    @And("^I Perform DELETE operation for \"([^\"]*)\"$")
    public void iPerformDELETEOperationFor(String url, DataTable table) throws Throwable {
        List<List<String>> data = table.raw();

        //set body
        HashMap<String, String> pathParams = new HashMap();
        pathParams.put("postid", data.get(1).get(0));     //row 1 as 0 are the headers

        //perform post
        RestAssuredExtension.DeleteOpsPathParams(url, pathParams);
    }

    /**
     * Check if a title exist or not in a response body. This one is using RestAssuredExtension.
     * @param condition
     * @param title
     * @throws Throwable
     */
    @Then("^I \"([^\"]*)\" see the body with title \"([^\"]*)\"$")
    public void iShoultNotSeeTheBodyWithTitle(String condition, String title) throws Throwable {
        //had to create the following line to get the elements, I could not put it directly on assertThat
        Object element = response.getBody().jsonPath().get("title");

        //let´s check if the element is null
        if(condition.equalsIgnoreCase("should not"))
            assertThat(element, is(nullValue()));
        else
            assertThat(element, is(notNullValue()));
    }

    /**
     * This perform a GET request. This one is using RestAssuredExtension.
     * @param url
     * @param table
     * @throws Throwable
     */
    @And("^I perform GET operation with path parameter for \"([^\"]*)\"$")
    public void iPerformGETOperationWithPathParameterFor(String url, DataTable table) throws Throwable {
        List<List<String>> data = table.raw();

        HashMap<String, String> pathParams = new HashMap();
        pathParams.put("postid", data.get(1).get(0));

        response = RestAssuredExtension.GetWithPathParams(url, pathParams);
    }


    /**
     * This one executes a PUT request. This one is using old RestAssuredExtension.
     * @param url
     * @param table
     * @throws Throwable
     */
    @And("^I Perform PUT operation for \"([^\"]*)\"$")
    public void iPerformPUTOperationFor(String url, DataTable table) throws Throwable {
        List<List<String>> data = table.raw();

        //set body
        HashMap<String, String> body = new HashMap();
        body.put("id", data.get(1).get(0));     //row 1 as 0 are the headers
        body.put("title", data.get(1).get(1));
        body.put("author", data.get(1).get(2));

        HashMap<String, String> pathParams = new HashMap();
        pathParams.put("postid", data.get(1).get(0));

        //perform post
        RestAssuredExtension.PutOpsWithBodyAndPathParams(url, body, pathParams);
    }
}
