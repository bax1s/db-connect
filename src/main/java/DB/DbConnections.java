package DB;

import Utils.TestContext;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnections  {
    TestContext testContext=TestContext.getInstance();

    public Connection getConnectionJDBC() throws SQLException {
        return DriverManager.getConnection(testContext.getDBUrl(), testContext.getDBUsername(), testContext.getDBPassword());
    }

    protected MongoCollection<Document> getConnectionMongoDb(String dbName, String collectinName) {
        String url=testContext.getDBUrl();
        String user=testContext.getDBUsername();
        String password=testContext.getDBPassword();
        String connection = "mongodb://"+user+":"+password+"@"+url+"/"+dbName;
        MongoClient client = MongoClients.create(connection);
        return client.getDatabase(dbName).getCollection(collectinName);
    }
}
