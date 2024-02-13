import com.mongodb.client.*;
public class MongoDBConnector {
    private MongoClient client;
    private MongoDatabase database;

    public MongoDBConnector() {
        client = MongoClients.create("mongodb://localhost:27017");
        database = client.getDatabase("reservesDB");
    }
}

