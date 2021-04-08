package renewed.DataX.data;

import org.json.JSONObject;
import renewed.DataX.data.statics.array.ArrayModification;
import renewed.DataX.lang.CorruptDatabaseException;
import renewed.DataX.lang.SchemaNotFoundException;

import java.io.File;
import java.io.FileWriter;

public class Database {
    private String path;
    private Schema[] schemas;

    public Database(String path) {
        this.path = path;
        this.update();
    }

    public String getPath() {
        return this.path;
    }

    protected void update() {
        this.schemas = new Schema[]{};
        File directory = new File(path);
        File[] files = directory.listFiles();
        for(File file : files) {
            this.schemas = ArrayModification.appendElement(this.schemas, new Schema(this, file.getName()));
        }
    }
    public Schema createSchema(String name) {
        try {
            JSONObject file = new JSONObject();
            JSONObject properties = new JSONObject();
            JSONObject data = new JSONObject();

            file.put("PROPERTIES", properties);
            file.put("DATA", data);

            FileWriter fileWriter = new FileWriter(this.path + "/" + name + ".json");
            fileWriter.write(file.toString());
            fileWriter.flush();

            this.update();

            return new Schema(this, name);
        }
        catch(Exception e) {
            throw new CorruptDatabaseException();
        }
    }
    public Schema getSchema(String name) {
        this.update();

        for(Schema schema : this.schemas) {
            if(schema.getName().equals(name + ".json")) {
                return schema;
            }
        }
        throw new SchemaNotFoundException(name);
    }
}
