import coin.DataX.data.*;

public class Test {
    public static void main(String[] args) {
        Database database = new Database("src/test/java/database");
        Table table = database.getTable("data");
        table.execute(new Execution() {
            @Override
            public void Execution(Query query) {
                query.setQuery("DELETE ALL WHERE \"username\" = \"user1\"");
                query.executeQuery();
            }
        });
    }
}
