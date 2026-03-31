package Utils;

import io.restassured.response.Response;
import lombok.Data;
import org.testng.Assert;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class TestContext {
    private static TestContext instance;
    public static TestContext getInstance() {
        if (instance == null) {
            instance = new TestContext();
        }
        return instance;
    }

    public void setValue(String contextName, String contextValue) {
        try {
            Field field = getInstance().getClass().getDeclaredField(contextName);
            field.set(this, contextValue);
        }catch (NoSuchFieldException e){
            Assert.fail("This variable name not found in TestContext class ----->"+ e.getMessage());
        }catch (IllegalAccessException e){
            Assert.fail(e.getMessage()+"NO ACCESS to this field");
        }
    }

    public String getValue(String contextName)  {
        try {
            Field field = getInstance().getClass().getDeclaredField(contextName);
            return field.get(this).toString();
        }catch (Exception e){
            Assert.fail("NOT FOUND THIS VARIABLE"+e.getMessage());
        }
        return  null;
    }

    private Map<String, Object> contexts = new HashMap<>();

    public void addTestContext(String contextName, Object value) {
        if(value.equals("")){
            this.contexts.put(contextName, null);
        }
        this.contexts.put(contextName, value);
    }

     public Object getTestContext(String contextName) {
        if (contexts.containsKey(contextName)) {
            return this.contexts.get(contextName);
        }else  {
            Assert.fail("NOT FOUND THIS VARIABLE: "+contextName);
            return  null;
        }
     }

    //default variables for DB
    private ResultSet ResultSet;
    private Object[] splitParams;
    private List<String> mongoResult;
    ArrayList<Object> multipleValues = new ArrayList<>();
    private String DBUrl;
    private String DBUsername;
    private String DBPassword;

    //default variables for Api
    private String url;
    private String body;
    private Response response;
    Map<String, String> header = new HashMap<>();

}
