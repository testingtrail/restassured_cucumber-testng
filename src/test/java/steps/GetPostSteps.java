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
import utilities.RestAssuredExtension;
import utilities.RestAssuredExtensionv2;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class GetPostSteps{

    public static ResponseOptions<Response> response;
    public static String token;

    @Given("^I perform GET operation for \"([^\"]*)\"$")
    public void iPerformGETOperationFor(String url) throws Throwable {
        response = RestAssuredExtension.GetOps(url);
    }

    @Then("^I should see the author name as \"([^\"]*)\"$")
    public void iShouldSeeTheAuthorNameAs(String authorName) throws Throwable {
        //had to create the following line to get the elements, I could not put it directly on assertThat
//        List<Object> elements = response.getBody().jsonPath().get("author");
//        assertThat(elements, hasItem("Karthik KK"));

        //Instead of dong the above, we are going to deserialize
        //  using the pojo class named 'Posts' we created and pass the values of the post response
        var posts = response.getBody().as(Posts.class);
        assertThat(posts.getAuthor(), equalTo(authorName));

    }

    @Then("^I should see the author names$")
    public void iShouldSeeTheAuthorNames() throws Throwable {
        BDDStyleMethod.PerformContainsCollection();
    }

    @Then("^I should verify GET Parameter$")
    public void iShouldVerifyGETParameter() throws Throwable {
        BDDStyleMethod.PerformPathParameters();
        BDDStyleMethod.PerformQueryParameters();
    }

    //This one is using new RestAssuredExtensionv2
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

    @Then("^I should see the street name as \"([^\"]*)\" for the \"([^\"]*)\" address$")
    public void iShouldSeeTheStreetNameAsForTheAddress(String streetName, String type) throws Throwable {
        //Deserialization for complex POJO classes

        //Since Location is an array with many values we have to call it as an array with []
        var location = response.getBody().as(Location[].class);
        //we need to filter the results as we have many addresses in just one location
        //  so we use stream to get the type to be equals as the one we are passing as parameter from the
        //  feature file
        Address address = location[0].getAddress().stream().filter(x -> x.getType().equalsIgnoreCase(type))
                .findFirst().orElse(null);

        assertThat(address.getStreet(), equalTo(streetName));
    }

    @Then("^I should see the author name as \"([^\"]*)\" with JSON validation$")
    public void iShouldSeeTheAuthorNameAsWithJSONValidation(String arg0) throws Throwable {
        // returns the body as string
       var responseBody = response.body().asString();
       assertThat(responseBody, matchesJsonSchemaInClasspath("posts.json"));
    }

    //This one is using new RestAssuredExtensionv2
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
