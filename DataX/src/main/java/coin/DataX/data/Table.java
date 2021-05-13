package coin.DataX.data;

import coin.DataX.data.statics.dataType.DataType;
import coin.DataX.data.statics.json.Get;
import coin.DataX.lang.CorruptDatabaseException;
import coin.DataX.lang.DataTypeNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

public class Table {
    private final Database database;
    private final String name;

    protected Table(Database database, String name) {
        this.database = database;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public String getPath() {
        return this.database.getPath() + File.separator + this.name + ".json";
    }
    public Database getDatabase() {
        return this.database;
    }

    public String getDescription() {
        try {
            InputStream inputStream = new FileInputStream(database.getPath() + File.separator + this.name);

            JSONTokener tokener = new JSONTokener(inputStream);

            JSONObject file = new JSONObject(tokener);

            JSONObject properties = file.getJSONObject("PROPERTIES");

            return properties.get("description").toString();
        }
        catch(Exception e) {
            throw new CorruptDatabaseException();
        }
    }
    public Table setDescription(String description) {
        try {
            JSONObject file = Get.getJSONObjectFromPath(database.getPath() + File.separator + this.name + ".json");

            JSONObject properties = file.getJSONObject("PROPERTIES");

            properties.put("description", description);

            FileWriter fileWriter = new FileWriter(database.getPath() + File.separator + this.name + ".json");
            fileWriter.write(file.toString());
            fileWriter.flush();
        }
        catch(Exception e) {
            throw new CorruptDatabaseException();
        }
        return this;
    }
    public Table addColumn(String name, DataType dataType) {
        try {
            JSONObject file = Get.getJSONObjectFromPath(database.getPath() + File.separator + this.name + ".json");

            JSONObject properties = file.getJSONObject("PROPERTIES");

            JSONObject column = new JSONObject();
            if(dataType.equals(DataType.STRING) || dataType.equals(DataType.INT) || dataType.equals(DataType.DOUBLE) || dataType.equals(DataType.BOOLEAN)) {
                column.put("dataType", dataType.getValue());
            }
            else {
                throw new DataTypeNotFoundException(dataType.getValue());
            }
            //column.put("notNull", notNull);
            //column.put("autoIncremental", autoIncremental);

            properties.put(name, column);

            FileWriter fileWriter = new FileWriter(database.getPath() + File.separator + this.name + ".json");
            fileWriter.write(file.toString());
            fileWriter.flush();

            return this;
        }
        catch(Exception e) {
            throw new CorruptDatabaseException();
        }
    }
    public Table removeColumn(String name) {
        try {
            JSONObject file = Get.getJSONObjectFromPath(database.getPath() + File.separator + this.name + ".json");

            JSONObject properties = file.getJSONObject("PROPERTIES");

            properties.remove(name);

            FileWriter fileWriter = new FileWriter(database.getPath() + File.separator + this.name + ".json");
            fileWriter.write(file.toString());
            fileWriter.flush();

            return this;
        }
        catch(Exception e) {
            throw new CorruptDatabaseException();
        }
    }
    public Table execute(@NotNull Execution execution) {
        execution.Execution(new Query(null, this, this.database));
        return this;
    }
    public Table rename(String newName) throws IOException {
        File file = new File(this.database.getPath() + File.separator + this.name + ".json");
        File file2 = new File(this.database.getPath() + File.separator + newName + ".json");
        if (file2.exists())
            throw new java.io.IOException("file exists");
        boolean success = file.renameTo(file2);
        if (!success) {
            return new Table(this.database, newName);
        }
        else {
            return null;
        }
    }
}
