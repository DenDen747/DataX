package coin.DataX.data;

import coin.DataX.data.statics.array.ArrayModification;
import coin.DataX.data.statics.dataType.DataType;
import coin.DataX.data.statics.json.Get;
import coin.DataX.lang.CorruptDatabaseException;
import coin.DataX.lang.DataXQueryRuntimeException;
import coin.DataX.lang.DataXSyntaxException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Iterator;

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
        if(this.query == null) {
            throw new NullPointerException("Cannot execute query as query is null.");
        }
        String[] args = this.query.split(" ");

        switch(args[0].toUpperCase()) {
            case "INSERT":
                return insert(args);
            case "SELECT":
                break;
            case "UPDATE":
                break;
            case "DELETE":
                break;
            default:
                throw new DataXSyntaxException(this.query, args[0], "Invalid command");
        }

        return new ResultSet(null);
    }

    private ResultSet insert(String[] args) {
        // TODO: 5/11/2021 Return ID in ResultSet
        String[] columns = {};
        String[] values = {};
        //If false, reading columns. Else, reading values
        boolean reading = false;
        
        for(int i = 1; i < args.length; i++) {
            if(args[i].contains("(") && args[i].contains(",")) {
                if(reading) {
                    values = ArrayModification.appendElement(values, args[i].substring(1, args[i].length() - 1));
                }
                else {
                    columns = ArrayModification.appendElement(columns, args[i].substring(1, args[i].length() - 1));
                }
            }
            else if(args[i].contains(",")) {
                if(reading) {
                    values = ArrayModification.appendElement(values, args[i].substring(0, args[i].length() - 1));
                }
                else {
                    columns = ArrayModification.appendElement(columns, args[i].substring(0, args[i].length() - 1));
                }
            }
            else if(args[i].contains(")") && args[i].contains("(")) {
                if(reading) {
                    values = ArrayModification.appendElement(values, args[i].substring(1, args[i].length() - 1));
                }
                else {
                    columns = ArrayModification.appendElement(columns, args[i].substring(1, args[i].length() - 1));
                    if(!args[i + 1].equalsIgnoreCase("VALUES")) {
                        throw new DataXSyntaxException(this.query, args[i + 1]);
                    }
                    //Starting to read values after this
                    reading = true;
                }
            }
            else if(args[i].contains(")")) {
                if(reading) {
                    values = ArrayModification.appendElement(values, args[i].substring(0, args[i].length() - 1));
                }
                else {
                    columns = ArrayModification.appendElement(columns, args[i].substring(0, args[i].length() - 1));
                    if(!args[i + 1].equalsIgnoreCase("VALUES")) {
                        throw new DataXSyntaxException(this.query, args[i + 1]);
                    }
                    //Starting to read values after this
                    reading = true;
                }
            }
            else if(!args[i].equalsIgnoreCase("VALUES")) {
                throw new DataXSyntaxException(this.query, args[i]);
            }
        }

        if(args.length == 1) {
            throw new DataXSyntaxException("Not enough arguments");
        }
        if(values.length != columns.length) {
            throw new DataXQueryRuntimeException("Number of values given does not match number of columns given.");
        }

        try {
            JSONObject file = Get.getJSONObjectFromPath(database.getPath() + File.separator + this.table.getName() + ".json");
            JSONObject properties = file.getJSONObject("PROPERTIES");
            JSONObject data = file.getJSONObject("DATA");
            JSONObject rowData = new JSONObject();
            int id = 0;
            for (int i = 0; i < columns.length; i++) {
                DataType dataType = null;
                if(properties.getJSONObject(columns[i]).get("dataType").equals("STRING")) {
                    dataType = DataType.STRING;
                }
                else if(properties.getJSONObject(columns[i]).get("dataType").equals("INT")) {
                    dataType = DataType.INT;
                }
                else if(properties.getJSONObject(columns[i]).get("dataType").equals("DOUBLE")) {
                    dataType = DataType.DOUBLE;
                }
                else if(properties.getJSONObject(columns[i]).get("dataType").equals("BOOLEAN")) {
                    dataType = DataType.BOOLEAN;
                }

                for (Iterator key = data.keys(); key.hasNext(); ) {
                    //data.remove(key.next().toString());
                    String name = key.next().toString();
                    JSONObject o = data.getJSONObject(name);
                    int currentId = Integer.parseInt(name);
                    if (currentId == id) {
                        id++;
                    } else {
                        break;
                    }
                }

                if(dataType.equals(DataType.STRING)) {
                    rowData.put(columns[i], values[i]);
                }
                else if(dataType.equals(DataType.INT)) {
                    rowData.put(columns[i], Integer.parseInt(values[i]));
                }
                else if(dataType.equals(DataType.DOUBLE)) {
                    rowData.put(columns[i], Double.parseDouble(values[i]));
                }
                else if(dataType.equals(DataType.BOOLEAN)) {
                    rowData.put(columns[i], Boolean.parseBoolean(values[i]));
                }
            }
            data.put(String.valueOf(id), rowData);
            FileWriter fileWriter = new FileWriter(database.getPath() + File.separator + this.table.getName() + ".json");
            fileWriter.write(file.toString());
            fileWriter.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return new ResultSet(null);
    }


















    /*public ResultSet executeQuery() {
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
                        JSONObject file = Get.getJSONObjectFromPath(this.database.getPath() + FileConfig.separator + this.table.getName() + ".json");

                        JSONObject data = file.getJSONObject("DATA");

                        data.clear();

                        FileWriter fileWriter = new FileWriter(database.getPath() + FileConfig.separator + this.table.getName() + ".json");
                        fileWriter.write(file.toString());
                        fileWriter.flush();

                        /*for(Iterator key = data.keys(); key.hasNext();) {
                            //data.remove(key.next().toString());
                            String name = key.next().toString();
                            JSONObject o = data.getJSONObject(name);
                        }
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
    }*/
}
