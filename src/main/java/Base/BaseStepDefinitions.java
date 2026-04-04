package Base;

import Utils.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;

    @Slf4j
    public class BaseStepDefinitions {
        BaseMethods baseMethods=new BaseMethods();
        TestContext testContext = TestContext.getInstance();

        @And("Add testContext-{string} and value-{string}")
        public void addTestContext(String contextName, String contextValue){
            testContext.addTestContext(contextName, contextValue);
        }

        @Then("Verify testContext value-{string} equals-{string}")
        public void assertEquals(String actualValue, String expectedValue){
            baseMethods.VerifyValue(testContext.getTestContext(actualValue).toString(), expectedValue);
        }

    }


