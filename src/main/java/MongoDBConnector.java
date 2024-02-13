import com.mongodb.client.*;
public class MongoDBConnector {
    private MongoClient client;
    private MongoDatabase database;

    public MongoDBConnector() {
        client = MongoClients.create("mongodb://localhost:27017");
        database = client.getDatabase("reservesDB");
    }

    public MongoClient getClient() {
        return client;
    }

    public void setClient(MongoClient client) {
        this.client = client;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }
}

