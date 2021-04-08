import coin.DataX.data.Database;
import coin.DataX.data.Schema;

public class Test {
    public static void main(String[] args) {
        Database database = new Database("src/test/java/database");
        Schema schema = database.getSchema("data");
        schema.setDescription("yasasa");
    }
}
