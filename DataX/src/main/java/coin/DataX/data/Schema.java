package coin.DataX.data;

import coin.DataX.lang.CorruptDatabaseException;
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
            InputStream inputStream = new FileInputStream(database.getPath() + File.separator + this.name);

            JSONTokener tokener = new JSONTokener(inputStream);

            JSONObject file = new JSONObject(tokener);

            JSONObject properties = file.getJSONObject("PROPERTIES");

            properties.put("description", description);

            FileWriter fileWriter = new FileWriter(database.getPath() + File.separator + this.name);
            fileWriter.write(file.toString());
            fileWriter.flush();
        }
        catch(Exception e) {
            throw new CorruptDatabaseException();
        }
        return this;
    }
}
