package coin.DataX.data;

public class Query{
    private String query;
    private final Table table;

    protected Query(String query, Table table) {
        this.query = query;
        this.table = table;
    }

    public String getQuery() {
        return this.query;
    }
    public Query setQuery(String query) {
        this.query = query;
        return this;
    }

    public ResultSet executeQuery() {
        // TODO: 4/8/2021 Manage execution in here
        return new ResultSet();
    }
}
