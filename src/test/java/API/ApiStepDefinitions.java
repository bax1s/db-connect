package API;

import Utils.TestContext;
import io.cucumber.java.en.And;

public class ApiStepDefinitions {
    TestContext testContext= TestContext.getInstance();
    ApiMethods apiMethods= new ApiMethods();
    ApiRequestFormalizer apiRequestFormalizer= new ApiRequestFormalizer();


    @And("Add Url-{string}")
    public void addUrl(String url) {
        testContext.setUrl(url);
    }

    @And("Send GET request expected statusCode-{string}")
    public void getRequest(String statusCode) {
        apiRequestFormalizer.getRequestFormalizer(Integer.parseInt(statusCode));
    }

    @And("API save value from path-{string} to testContext-{string}")
    public void saveValueFromPath(String path, String contextName){
        apiMethods.saveValueFromPath(path,contextName);
        System.out.println(testContext.getTestContext(contextName)+" <------------------------"+contextName+" is here\uD83E\uDEF6\uD83C\uDFFB");
    }

    //3 different usage header:  static - dynamic from test context  - dynamic from environment
    @And("Add header-{string} value from testContext-{string}")
    public void addHeaderFromTestContext(String key, String value) {
           value= testContext.getTestContext(value).toString();
            testContext.getHeader().put(key,value);
    }

    @And("Add header-{string} value from environment-{string}")
    public void addHeaderFromEnvironment(String key, String value) {
        value= System.getenv(value);
        testContext.getHeader().put(key,value);
    }

    @And("Add header-{string} value-{string}")
    public void addHeader(String key, String value) {
        testContext.getHeader().put(key,value);
    }

    @And("Add Body")
    public void addBody() {
        String body= testContext.getBody();
    }



}

