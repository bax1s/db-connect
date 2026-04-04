package DB;

import Utils.TestContext;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DbStepDefinitions {
    DbMethods dbMethods = new DbMethods();
    TestContext testContext = TestContext.getInstance();

    @And("Add DB url-{string} username-{string} password-{string}")
    public void addDBUrl(String dbUrl,String dbUsername,String dbPassword){
        testContext.setDBUrl(System.getenv(dbUrl));
        testContext.setDBUsername(System.getenv(dbUsername));
        testContext.setDBPassword(System.getenv(dbPassword));
    }

    @And("JDBC--DELETE Query-{string} Variables-{string}")
    public void JDBCDeleteMethodQuery(String query, String parameters){
        testContext.setSplitParams(null);
        dbMethods.splitAndFindInTestContext(parameters);
        dbMethods.editQueryJDBC(query,testContext.getSplitParams());
    }

    @And("JDBC--UPDATE Query-{string} Variables-{string}")
    public void JDBCUpdateMethodQuery(String query, String parameters){
        testContext.setSplitParams(null);
        dbMethods.splitAndFindInTestContext(parameters);
        dbMethods.editQueryJDBC(query,testContext.getSplitParams());
    }

    @And("JDBC--INSERT Query-{string} Variables-{string}")
    public void JDBCInsertMethodQuery(String query, String parameters){
        testContext.setSplitParams(null);
        dbMethods.splitAndFindInTestContext(parameters);
        dbMethods.editQueryJDBC(query,testContext.getSplitParams());
    }

    @And("JDBC--SELECT Query-{string} Variables-{string}")
    public void JDBCSelectMethodQuery(String query,String parameters){
        testContext.setSplitParams(null);
        dbMethods.splitAndFindInTestContext(parameters);
        dbMethods.selectQueryJDBC(query,testContext.getSplitParams());
    }

    @And("JDBC--Save value from response Column Name-{string},Variable Name-{string}")
    public void saveValue(String columnName, String contextName)  {
        dbMethods.saveValueFromJDBC(columnName,contextName);
        //logging
        System.out.println(testContext.getTestContext(contextName)+" <------------------------"+contextName+" is here\uD83E\uDEF6\uD83C\uDFFB");
    }

    @And("JDBC--Save multiple value from response Column Name-{string}")
    public void saveMultipleValue(String columnName)  {
        dbMethods.saveMultipleValueFromJDBC(columnName);
    }

    @And("MongoDb--SELECT DB Name-{string} Collection name-{string} sort-{string} find-{string} find variables-{string}")
    public void MongoDbSelectMethodQuery(String dbName, String collectionName, String sort,String find, String findVariables) {
        testContext.setSplitParams(null);
        dbMethods.splitAndFindInTestContext(findVariables);
        dbMethods.selectQueryMongoDb(dbName,collectionName,sort,find,testContext.getSplitParams());
    }

    @And("MongoDb--DELETE DB Name-{string} Collection name-{string} filter-{string} filter variables-{string}")
    public void MongoDbDeleteMethodQuery(String dbName, String collectionName, String deleteFilter,String findVariables){
        testContext.setSplitParams(null);
        dbMethods.splitAndFindInTestContext(findVariables);
        dbMethods.deleteQueryMongoDb(dbName,collectionName,deleteFilter,testContext.getSplitParams());
        System.out.println("SUCCESSFULLY DELETED");
    }

    @And("MongoDb--UPDATE DB Name-{string} Collection name-{string} filter-{string} updateOperation-{string}")
    public void MongoDbUpdateMethodQuery(String dbName, String collectionName, String filter,String updateOperation){
        dbMethods.updateQueryMongoDb(dbName,collectionName,filter,updateOperation);
        System.out.println("SUCCESSFULLY UPDATED");
    }

    @And("MongoDb--INSERT DB Name-{string} Collection name-{string} insertData-{string} insert variables-{string}")
    public void MongoDbInsertMethodQuery(String dbName, String collectionName, String insertData,String insertVariables){
        testContext.setSplitParams(null);
        dbMethods.splitAndFindInTestContext(insertVariables);
        dbMethods.insertQueryMongoDb(dbName,collectionName,insertData,testContext.getSplitParams());
        System.out.println("SUCCESSFULLY INSERTED");
    }

    @And("MongoDB--Save value from response Json Path-{string},Variable Name-{string}")
    public void MongoDbSaveValueFromResponse(String jsonPath, String contextName){
        dbMethods.saveValueFromMongoDb(jsonPath,contextName);
        System.out.println(testContext.getTestContext(contextName)+" <------------------------"+contextName+" is here\uD83E\uDEF6\uD83C\uDFFB");
    }
}
