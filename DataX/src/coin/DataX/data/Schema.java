package coin.DataX.data;

import coin.DataX.data.statics.array.ArrayModification;
import coin.DataX.lang.CorruptDatabaseException;
import coin.DataX.lang.PathException;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Schema {
    String name;
    Database database;

    JSONObject file;
    JSONObject properties;
    JSONObject data;

    public Schema(Database database, String name) {
        this.database = database;
        this.name = name;
        try {
            this.properties = new JSONObject();
            this.data = new JSONObject();

            this.file = new JSONObject();
            this.file.put("PROPERTIES", this.properties);
            this.file.put("DATA", this.data);

            FileWriter fileWriter = new FileWriter(database.path + "/" + this.name + ".json");
            fileWriter.write(this.file.toString());
            fileWriter.flush();
        }
        catch(Exception e) {
            throw new CorruptDatabaseException();
        }
    }
    protected Schema(Database database, String name, JSONObject file, JSONObject properties, JSONObject data) {
        this.database = database;
        this.name = name;
        this.properties = properties;
        this.data = data;
        this.file = file;
    }

    public Database getDatabase() {
        return this.database;
    }
    public String getName() {
        return this.name;
    }
    public Schema setName(String name) {
        this.name = name;
        return this;
    }
    public String getDescription() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(this.database.path + "/" + this.name + ".datax")));
            bufferedReader.readLine();
            String raw = bufferedReader.readLine();
            System.out.println(raw);
            return null;
        }
        catch (IOException e) {
            throw new CorruptDatabaseException();
        }
    }
    public Schema setDescription(String description) {
        try {
            this.properties.put("description", description);

            FileWriter fileWriter = new FileWriter(database.path + "/" + this.name + ".json");
            fileWriter.write(this.file.toString());
            fileWriter.flush();
        }
        catch(Exception e) {
            throw new CorruptDatabaseException();
        }
        return this;
    }
}
