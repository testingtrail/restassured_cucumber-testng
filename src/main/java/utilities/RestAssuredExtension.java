package utilities;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;


import java.util.HashMap;
import java.util.Map;

public class RestAssuredExtension {

    public static RequestSpecification Request;

    public RestAssuredExtension(){
        //Arrange options
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("http://localhost:3000");
        builder.setContentType(ContentType.JSON);
        //starting Java 10 you can use var
        //var requestSpec = builder.build();
        RequestSpecification requestSpec = builder.build();
        Request = RestAssured.given().spec(requestSpec);
    }

    public static ResponseOptions<Response> GetOps(String url){
        //Act
        try {
            return Request.get(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseOptions<Response> POSTOpsWithBodyAndPathParams(String url, Map<String,String> pathParams, Map<String,String> body){
        Request.pathParams(pathParams);
        Request.body(body);
        try {
            return Request.post(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseOptions<Response> PostOpsWithBody(String url, Map<String,String> body){
        //Act
        Request.body(body);
        return Request.post(url);
    }

    public static ResponseOptions<Response> DeleteOpsPathParams(String url, Map<String,String> pathParams){
        //Act
        Request.pathParams(pathParams);
        return Request.delete(url);
    }

    public static ResponseOptions<Response> GetWithPathParams(String url, Map<String,String> pathParams){
        //Act
        Request.pathParams(pathParams);
        return Request.get(url);
    }

    public static ResponseOptions<Response> GetWithQueryParams(String url, Map<String,String> queryParams){
        //Act
        Request.queryParams(queryParams);
        return Request.get(url);
    }


    public static ResponseOptions<Response> PutOpsWithBodyAndPathParams(String url, HashMap<String, String> body, HashMap<String, String> pathParams) {
        Request.pathParams(pathParams);
        Request.body(body);
        return Request.put(url);
    }

}
