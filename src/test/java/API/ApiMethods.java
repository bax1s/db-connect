package API;

import Utils.TestContext;
import io.restassured.response.Response;
import java.util.function.Consumer;

public class ApiMethods {
    TestContext testContext= TestContext.getInstance();

    public void saveValueFromPath(String path,String contextName){
        Response response=testContext.getResponse();
        String responseVariable=response.jsonPath().getString(path);
        testContext.addTestContext(contextName,responseVariable);
    }
}
