package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

import pojo.Posts;
import utilities.RestAssuredExtension;

import java.util.List;

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
        //  using the pojo class we created and pass the values of the post response
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

}
