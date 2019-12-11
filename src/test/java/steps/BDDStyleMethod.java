package steps;

import io.restassured.http.ContentType;
import org.hamcrest.core.Is;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsIterableContaining.hasItem;

public class BDDStyleMethod {

    /**
     * Checks that in the first post the author is Karthik
     * This is using BDD style
     * @param postNumber
     */
    public static void SimpleGETPost(String postNumber){
        given()
            .contentType(ContentType.JSON).
        when()
            .get(String.format("http://localhost:3000/posts/%s",postNumber)).
        then()
            .body("author",is("Karthik KK"));
    }

    /**
     * Check that in the collection the author Karthik exist in any order
     * This is using BDD style
     * @param
     */
    public static void PerformContainsCollection(){
        given().
            contentType(ContentType.JSON)
        .when()
            .get("http://localhost:3000/posts/")
        .then()
            .body("author", containsInAnyOrder("Karthik KK","Karthik KK",null))
                .statusCode(200);
    }

    /**
     * Check that in the response when requesting a post with path params the response has a specific value.
     */
    public static void PerformPathParameters(){
        given().
            contentType(ContentType.JSON).
        with().
           pathParams("post",1).
        when().
            get("http://localhost:3000/posts/{post}"). //the value {post} is the same as the name in pathParams
        then().
            body("author",containsString("Karthik KK"));
    }


    /**
     * Check that in the response when requesting a post with query params the response has a specific value.
     */
    public static void PerformQueryParameters(){
        given().
            contentType(ContentType.JSON).
            queryParam("id",1).
        when().
            //here does not need the param as this is going in the queryParam
            get("http://localhost:3000/posts/").
        then().
            body("author",hasItem("Karthik KK"));
    }

    /**
     * This one creates posts using Body Parameters instead of query params or path params
     */
    public static  void PerformPOSTWithBodyParameter(){
        HashMap<String, String> postContent = new HashMap();
        postContent.put("id","5");
        postContent.put("title","Lo que el viento se llevo");
        postContent.put("author","Juanilama");

        given()
                .contentType(ContentType.JSON)
        .with()
                .body(postContent)
        .when()
                .post("http://localhost:3000/posts")
        .then()
                .body("author", Is.is("Juanilama"));
    }


}
