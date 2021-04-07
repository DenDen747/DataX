package test;

import coin.DataX.data.Database;
import coin.DataX.data.Schema;

public class Test {
    public static void main(String[] args) {
        Database database = new Database("src/test/database");
        Schema schema = new Schema(database, "data");
    }
}
