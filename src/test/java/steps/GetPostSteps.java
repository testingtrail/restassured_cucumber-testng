package steps;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

import pojo.Address;
import pojo.Location;
import pojo.Posts;
import utilities.RestAssuredExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class GetPostSteps{

    public static ResponseOptions<Response> response;

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

    @Given("^I Perform GET operation for path parameter for address \"([^\"]*)\"$")
    public void iPerformGETOperationForPathParameterForAddress(String url, DataTable table) throws Throwable {
       var data = table.raw();
       Map<String, String> queryParams = new HashMap<>();
       queryParams.put("id", data.get(1).get(0));

       response = RestAssuredExtension.GetWithQueryParams(url, queryParams);

    }

    @Then("^I should see the street name as \"([^\"]*)\" for the \"([^\"]*)\" address$")
    public void iShouldSeeTheStreetNameAsForTheAddress(String streetName, String type) throws Throwable {
        //Deserialization for complex POJO classes

        //Since Location is an array with many values we have to call it as an array with []
        var location = response.getBody().as(Location[].class);
        //we need to filter the results as we have many addresses in just one location
        //  so we use stream to get the type to be equals as the one we are passing as parameter from the
        //  feature file
        Address address  = location[0].getAddress().stream().filter(x -> x.getType().equalsIgnoreCase(type))
                                                            .findFirst().orElse(null);

        assertThat(address.getStreet(), equalTo(streetName));
    }
}
