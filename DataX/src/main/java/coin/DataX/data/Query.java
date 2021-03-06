package coin.DataX.data;

import coin.DataX.data.statics.json.Get;
import coin.DataX.lang.CorruptDatabaseException;
import coin.DataX.lang.DataXSyntaxException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;

public class Query{
    private String query;
    private final Table table;
    private final Database database;

    protected Query(String query, Table table, Database database) {
        this.query = query;
        this.table = table;
        this.database = database;
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
            if(args.length == 1) {
               throw new DataXSyntaxException("Cannot execute INSERT command as not enough arguments were given.");
            }
        }
        else if(args[0].equalsIgnoreCase("DELETE")) {
            if(args.length == 1) {
                throw new DataXSyntaxException("Cannot execute DELETE command as not enough arguments were given.");
            }
            else if(args[1].equalsIgnoreCase("ALL")) {
                if(args.length == 2) {
                    try {
                        JSONObject file = Get.getJSONObjectFromPath(this.database.getPath() + File.separator + this.table.getName() + ".json");

                        JSONObject data = file.getJSONObject("DATA");

                        data.clear();

                        FileWriter fileWriter = new FileWriter(database.getPath() + File.separator + this.table.getName() + ".json");
                        fileWriter.write(file.toString());
                        fileWriter.flush();

                        /*for(Iterator key = data.keys(); key.hasNext();) {
                            //data.remove(key.next().toString());
                            String name = key.next().toString();
                            JSONObject o = data.getJSONObject(name);
                        }*/
                    }
                    catch(Exception e) {
                        throw new CorruptDatabaseException();
                    }
                }
                else {
                    throw new DataXSyntaxException(this.query, args[2]);
                }
            }
            else {
                throw new DataXSyntaxException(this.query, args[1]);
            }
        }
        else if(args[0].equalsIgnoreCase("SELECT")) {
            if(args.length == 1) {
                throw new DataXSyntaxException("Cannot execute SELECT command as not enough arguments were given.");
            }
        }
        else if(args[0].equalsIgnoreCase("UPDATE")) {
            if(args.length == 1) {
                throw new DataXSyntaxException("Cannot execute UPDATE command as not enough arguments were given.");
            }
        }
        else {
            throw new DataXSyntaxException(this.query, args[0]);
        }
        return new ResultSet();
    }
}
