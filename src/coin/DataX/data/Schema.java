package renewed.DataX.data;

import coin.DataX.lang.CorruptDatabaseException;
import org.json.JSONObject;

import java.io.File;
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


            File jsonFile = new File(this.database.getPath() + File.separator + this.name + ".json");

            JSONObject file = new JSONObject(jsonFile.toString());

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
