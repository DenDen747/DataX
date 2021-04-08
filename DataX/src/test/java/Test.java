import coin.DataX.data.Database;
import coin.DataX.data.Schema;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Database database = new Database("src/test/java/database");
        Schema schema = database.getSchema("data");
        System.out.println(schema.getDescription());
    }
}
