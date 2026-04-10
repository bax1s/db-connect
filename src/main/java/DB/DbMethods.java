package DB;

import Utils.TestContext;
import com.jayway.jsonpath.JsonPath;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import lombok.SneakyThrows;
import org.bson.Document;
import org.testng.Assert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DbMethods {
    TestContext testContext = TestContext.getInstance();
    DbConnections dbConnection = new DbConnections();
    PreparedStatement stmt;
    //update, delete and etc JDBC
    @SneakyThrows
    public void editQueryJDBC(String query, Object... params) {
        stmt = dbConnection.getConnectionJDBC().prepareStatement(query);
        if (params!=null) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
        }
        try {
            stmt.executeUpdate();
            stmt.close();
        }catch (SQLException e){
            Assert.fail("Check your SQL query ---->"+e.getMessage());
            stmt.close();
        }
    }

    // only select  JDBC
    @SneakyThrows
    public void selectQueryJDBC(String query, Object... params) {
        stmt = dbConnection.getConnectionJDBC().prepareStatement(query);
        if (params!=null) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
        }
        try {
            testContext.setResultSet(stmt.executeQuery());
        }catch (SQLException e){
            Assert.fail("Check your SQL query ---->"+e.getMessage());
            stmt.close();
        }
    }


    //save from response
    @SneakyThrows
    public void saveValueFromJDBC(String columnName, String contextName)  {
        ResultSet rs = testContext.getResultSet();
        if (rs.next()) {
//            testContext.setValue(contextName, rs.getString(columnName));
            //added
            testContext.addTestContext(contextName,rs.getString(columnName));
        }else{
            System.out.println("MY ERROR---- No results were found");
        }
        rs.getStatement().getConnection().close();

    }

    @SneakyThrows
    public void saveMultipleValueFromJDBC(String columnName)  {
        ResultSet rs = testContext.getResultSet();
        ArrayList<Object> columnValues = new ArrayList<>();
        while(rs.next()) {
            columnValues.add(rs.getObject(columnName));
            testContext.setMultipleValues(columnValues);
        }
        System.out.println(testContext.getMultipleValues()+"array is here");
    }

    //split parameters and find in testContext
    public void splitAndFindInTestContext(String parameters) {
        if(!parameters.trim().isEmpty()) {
            Object[] splitParams = parameters.split(",");
            for (int i = 0; i < splitParams.length; i++) {
//                splitParams[i] = testContext.getValue(splitParams[i].toString());

                //this added
                splitParams[i]=testContext.getTestContext(splitParams[i].toString());
            }
            testContext.setSplitParams(splitParams);
        }
    }

    public void selectQueryMongoDb(String dbName, String collectionName, String sort, String find, Object... parameters) {
        MongoCollection<Document> collection= dbConnection.getConnectionMongoDb(dbName,collectionName);
        if(parameters !=null) {
            for (int i = 0; i < parameters.length; i++) {
                find = find.replaceFirst("\\?", parameters[i].toString());
            }
        }
        Document parsedFind = Document.parse(find);
        FindIterable<Document> result = collection.find(parsedFind);
        if (!sort.isBlank()){
           result= result.sort(Document.parse(sort)).limit(100);
        }
        MongoCursor<Document> cursor = result.iterator();
        List<String> jsonResults = new ArrayList<>();
        while (cursor.hasNext()) {
            jsonResults.add(cursor.next().toJson());
        }
        testContext.setMongoResult(jsonResults);
        System.out.println(jsonResults);
    }

    public void deleteQueryMongoDb(String dbName, String collectionName, String deleteFilter, Object... parameters) {
        MongoCollection<Document> collection= dbConnection.getConnectionMongoDb(dbName,collectionName);
        if(parameters !=null) {
            for (int i = 0; i < parameters.length; i++) {
                deleteFilter = deleteFilter.replaceFirst("\\?", parameters[i].toString());
            }
        }
        Document parsedDelete = Document.parse(deleteFilter);
        collection.deleteOne(parsedDelete);
    }

    public void updateQueryMongoDb(String dbName, String collectionName, String filterJson,String updateJson) {
        MongoCollection<Document> collection= dbConnection.getConnectionMongoDb(dbName,collectionName);
        Document filter = Document.parse(filterJson);
        Document updateOperation = Document.parse(updateJson);
        collection.updateMany((filter),updateOperation);
    }
    public void insertQueryMongoDb(String dbName, String collectionName, String insertJson,Object... parameters) {
        MongoCollection<Document> collection= dbConnection.getConnectionMongoDb(dbName,collectionName);
        if(parameters !=null) {
            for (int i = 0; i < parameters.length; i++) {
                insertJson = insertJson.replaceFirst("\\?", parameters[i].toString());
            }
        }
        Document insertData = Document.parse(insertJson);
        collection.insertOne((insertData));
    }

    public void saveValueFromMongoDb(String path, String contextName) {
        String document = testContext.getMongoResult().toString();
//        testContext.setValue(contextName,JsonPath.read(document, path));
        //added here
        testContext.addTestContext(contextName,JsonPath.read(document, path));
    }


}
