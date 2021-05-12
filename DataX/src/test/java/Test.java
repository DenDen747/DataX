import coin.DataX.data.*;

public class Test {
    public static void main(String[] args) {
        Database database = new Database("src/test/java/database");
        Table table = database.returnTable("data");
        table.execute(new Execution() {
            @Override
            public void Execution(Query query) {
                query.setQuery("INSERT (username, password) VALUES (user3, pass3)");
                query.executeQuery();
            }
        });
    }
}
