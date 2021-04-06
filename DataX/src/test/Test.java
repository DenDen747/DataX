package test;

import coin.DataX.data.Database;
import coin.DataX.data.Schema;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Database database = new Database("src/test/database");
        Schema schema = new Schema(database, "data");
        schema.getDescription();
    }
}
