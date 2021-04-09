package coin.DataX.data;

import coin.DataX.lang.DataXSyntaxException;

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
        if(this.query == null) {
            throw new NullPointerException("Cannot execute query as query is null.");
        }
        String[] args = this.query.split(" ");
        if(args[0].equalsIgnoreCase("INSERT")) {

        }
        else if(args[0].equalsIgnoreCase("DELETE")) {

        }
        else if(args[0].equalsIgnoreCase("SELECT")) {

        }
        else if(args[0].equalsIgnoreCase("UPDATE")) {

        }
        else {
            throw new DataXSyntaxException(this.query, args[0]);
        }
        return new ResultSet();
    }
}
