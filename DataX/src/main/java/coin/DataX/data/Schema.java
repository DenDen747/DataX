package coin.DataX.data;

import coin.DataX.data.statics.json.Get;
import coin.DataX.lang.CorruptDatabaseException;
import coin.DataX.lang.DataTypeNotFoundException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

public class Schema {
    private final Database database;
    private final String name;

    protected Schema(Database database, String name) {
        this.database = database;
        this.name = name;
    }

    public String getName() {
        return this.name;
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
    public Schema setDescription(String description) {
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
    public Schema addColumn(String name, String dataType, boolean notNull, boolean autoIncremental) {
        try {
            JSONObject file = Get.getJSONObjectFromPath(database.getPath() + File.separator + this.name + ".json");

            JSONObject properties = file.getJSONObject("PROPERTIES");

            JSONObject column = new JSONObject();
            if(dataType.equals("STRING") || dataType.equals("INT") || dataType.equals("DOUBLE") || dataType.equals("BOOLEAN")) {
                column.put("dataType", dataType);
            }
            else {
                throw new DataTypeNotFoundException(dataType);
            }
            column.put("notNull", notNull);
            column.put("autoIncremental", autoIncremental);

            properties.put(name, column);

            FileWriter fileWriter = new FileWriter(database.getPath() + File.separator + this.name + ".json");
            fileWriter.write(file.toString());
            fileWriter.flush();

            return this;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new CorruptDatabaseException();
        }
    }
}
