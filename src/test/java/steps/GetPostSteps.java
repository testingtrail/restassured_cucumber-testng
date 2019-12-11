package steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

import pojo.Address;
import pojo.Location;
import pojo.Posts;
import utilities.APIConstants;
import legacy.RestAssuredExtension;
import utilities.RestAssuredExtensionv2;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class GetPostSteps{

    public static ResponseOptions<Response> response;
    public static String token;

    /**
     * This performs a GET request for a post. This one is using new RestAssuredExtension.
     * @param url
     * @throws Throwable
     */
    @Given("^I perform GET operation for \"([^\"]*)\"$")
    public void iPerformGETOperationFor(String url) throws Throwable {
        response = RestAssuredExtension.GetOps(url);
    }

    /**
     * This uses deserialization with POJO class to get the author.
     * This one is using new RestAssuredExtension.
     * @param authorName
     * @throws Throwable
     */
    @Then("^I should see the author name as \"([^\"]*)\"$")
    public void iShouldSeeTheAuthorNameAs(String authorName) throws Throwable {
        //had to create the following line to get the elements, I could not put it directly on assertThat
//        List<Object> elements = response.getBody().jsonPath().get("author");
//        assertThat(elements, hasItem("Karthik KK"));

        //Instead of doing the above, we are going to deserialize
        //  using the POJO class named 'Posts' we created and pass the values of the post response
        var posts = response.getBody().as(Posts.class);
        assertThat(posts.getAuthor(), equalTo(authorName));

    }

    /**
     * This one use the BDD Style to perform a search in a body response
     * @throws Throwable
     */
    @Then("^I should see the author names$")
    public void iShouldSeeTheAuthorNames() throws Throwable {
        BDDStyleMethod.PerformContainsCollection();
    }

    /**
     * This one use the BDD Style to check a response using path parameters and query parameters
     * @throws Throwable
     */
    @Then("^I should verify GET Parameter$")
    public void iShouldVerifyGETParameter() throws Throwable {
        BDDStyleMethod.PerformPathParameters();
        BDDStyleMethod.PerformQueryParameters();
    }

    /**
     * This perform a get operation to retrieve a response from location endpoint.
     * This one is using new RestAssuredExtensionv2. Returns response.
     * @param uri
     * @param table
     * @throws Throwable
     */
    @Given("^I Perform GET operation for path parameter for address \"([^\"]*)\"$")
    public void iPerformGETOperationForPathParameterForAddress(String uri, DataTable table) throws Throwable {
       var data = table.raw();
       Map<String, String> queryParams = new HashMap<>();
       queryParams.put("id", data.get(1).get(0));

       // Before RestAssuredExtensionv2
       //response = RestAssuredExtension.GetWithQueryParams(url, queryParams);

        // With RestAssuredExtensionv2
        RestAssuredExtensionv2 restAssuredExtensionv2 = new RestAssuredExtensionv2(uri, "GET", token);
        response = restAssuredExtensionv2.ExecuteWithQueryParams(queryParams);

    }

    /**
     * This take the locations and get the address of a response.
     * Deserialization for complex POJO classes.
     * @param streetName
     * @param type
     * @throws Throwable
     */
    @Then("^I should see the street name as \"([^\"]*)\" for the \"([^\"]*)\" address$")
    public void iShouldSeeTheStreetNameAsForTheAddress(String streetName, String type) throws Throwable {

        //Since Location is an array with many values we have to call it as an array with []
        var location = response.getBody().as(Location[].class);

        //we need to filter the results as we have many addresses in just one location
        //  so we use stream to get the type to be equals as the one we are passing as parameter from the
        //  feature file
        Address address = location[0].getAddress().stream().filter(x -> x.getType().equalsIgnoreCase(type))
                .findFirst().orElse(null);

        assertThat(address.getStreet(), equalTo(streetName));
    }

    /**
     * This one checks a response body but also comparing against an schema named posts.json
     * That file is located on targetfolder as this is in execution time folder where it looks for that file
     * @param arg0
     * @throws Throwable
     */
    @Then("^I should see the author name as \"([^\"]*)\" with JSON validation$")
    public void iShouldSeeTheAuthorNameAsWithJSONValidation(String arg0) throws Throwable {
        // returns the body as string
       var responseBody = response.body().asString();
       assertThat(responseBody, matchesJsonSchemaInClasspath("posts.json"));
    }

    /**
     * Authenticate the user. This one is using new RestAssuredExtensionv2. It gets the token.
     * @param uri
     * @param table
     * @throws Throwable
     */
    @Given("^I perform authentication operation for \"([^\"]*)\" with body$")
    public void iPerformAuthenticationOperationForWithBody(String uri, DataTable table) throws Throwable {
        var data = table.raw();

        HashMap<String, String> body =  new HashMap<>();
        body.put("email", data.get(1).get(0));
        body.put("password", data.get(1).get(1));

        RestAssuredExtensionv2 restAssuredExtensionv2 = new RestAssuredExtensionv2(uri, APIConstants.ApiMethods.POST, null);
        token = restAssuredExtensionv2.Authenticate(body);

    }
}
