package Base;

import org.testng.Assert;

public class BaseMethods {
    public void VerifyValue(String actualValue, String expectedValue){
        Assert.assertEquals(actualValue, expectedValue, "The actual value does not match the expected value. Actual value: " + actualValue);
    }
}

