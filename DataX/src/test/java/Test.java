import coin.DataX.data.*;

public class Test {
    public static void main(String[] args) {
        Database database = new Database("src/test/java/database");
        Table table = database.returnTable("data");
    }
}
