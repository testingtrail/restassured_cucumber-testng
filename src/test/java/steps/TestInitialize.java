package steps;

import cucumber.api.java.Before;
import legacy.RestAssuredExtension;

public class TestInitialize {

    /**
     * This method initialize restAssuredExtension, the old way to request.
     */
    @Before
    public void TestSetup(){
        RestAssuredExtension restAssuredExtension = new RestAssuredExtension();
    }

}
