package test;

import renewed.DataX.data.Database;
import renewed.DataX.data.Schema;

public class Test {
    public static void main(String[] args) {
        Database database = new Database("src/test/database");
        Schema schema = database.getSchema("data");
        schema.setDescription("yasasa");
    }
}
