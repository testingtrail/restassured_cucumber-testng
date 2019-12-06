package utilities;

import io.restassured.builder.RequestSpecBuilder;

import java.util.Map;

public class RestAssuredExtensionv2 {

    RequestSpecBuilder builder = new RequestSpecBuilder();
    private String method;
    private String url;

    public RestAssuredExtensionv2(String uri, String method, String token) {
        //Formulate the API url
        this.url = "http://localhost:3000" + uri;
        this.method = method;

        if(token != null)
            builder.addHeader("Authorization", "Bearer " + token);
    }

    public String Authenticate(Map<String, String> body){
        builder.setBody(body);
        return "hola";
    }


}
