package coin.DataX.data;

import coin.DataX.lang.CorruptDatabaseException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

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

    public Schema setDescription(String description) {
        try {
            JSONParser parser = new JSONParser();

            Object o = parser.parse(new FileReader(new File(this.database.getPath() + File.separator + this.name).getAbsolutePath()));

            JSONObject file = (JSONObject) o;

            JSONObject properties = file.getJSONObject("DESCRIPTION");

            properties.put("DESCRIPTION", description);

            /*FileWriter fileWriter = new FileWriter(database.path + "/" + this.name + ".json");
            fileWriter.write(this.file.toString());
            fileWriter.flush();*/
        }
        catch(Exception e) {
            e.printStackTrace();
            //throw new CorruptDatabaseException();
        }
        return this;
    }
}
