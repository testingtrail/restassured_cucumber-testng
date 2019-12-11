package utilities;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RestAssuredExtensionv2 {

    RequestSpecBuilder builder = new RequestSpecBuilder();
    private String method;
    private String url;

    /**
     * RestAssuredExtensionv2 constructor to pass the initial settings for the following method
     * @param uri
     * @param method
     * @param token
     */
    public RestAssuredExtensionv2(String uri, String method, String token) {
        //Formulate the API url
        this.url = "http://localhost:3000" + uri;
        this.method = method;

        if(token != null)
            builder.addHeader("Authorization", "Bearer " + token);
    }

    /**
     * ExecuteAPI to execute the API for Get/Post/Delete
     * @return ResponseOptions<Response>
     */
    private ResponseOptions<Response> ExecuteAPI(){
        RequestSpecification requestSpecification = builder.build();
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.spec(requestSpecification);

        if(this.method.equalsIgnoreCase(APIConstants.ApiMethods.POST))
            return request.post(this.url);
        else if(this.method.equalsIgnoreCase(APIConstants.ApiMethods.DELETE))
            return request.delete(this.url);
        else if(this.method.equalsIgnoreCase(APIConstants.ApiMethods.GET))
            return request.get(this.url);

        return null;
    }


    /**
     * Authenticate to get the token variable
     * @param body
     * @return String token
     */
    public String Authenticate(Map<String, String> body){
        builder.setBody(body);
        return ExecuteAPI().getBody().jsonPath().get("access_token");
    }

    /**
     * Executing API with query params being passed as the input of it
     * @param queryPath
     * @return Reponse
     */
    public ResponseOptions<Response> ExecuteWithQueryParams(Map<String, String> queryPath) {
        builder.addPathParams(queryPath);
        return ExecuteAPI();
    }

    /**
     * ExecuteWithPathParams
     * @param pathParams
     * @return Response
     */
    public ResponseOptions<Response> ExecuteWithPathParams(Map<String, String> pathParams) {
        builder.addQueryParams(pathParams);
        return ExecuteAPI();
    }

    /**
     * ExecuteWithPathParamsAndBody
     * @param pathParams
     * @param body
     * @ Response
     */
    public ResponseOptions<Response> ExecuteWithPathParamsAndBody(Map<String, String> pathParams, Map<String, String> body) {
        builder.setBody(body);
        builder.addQueryParams(pathParams);
        return ExecuteAPI();
    }



}
