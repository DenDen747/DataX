package coin.DataX.data;

import coin.DataX.data.statics.array.ArrayModification;
import coin.DataX.lang.CorruptDatabaseException;
import coin.DataX.lang.PathException;
import coin.DataX.lang.SchemaNotFoundException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystemException;
import java.nio.file.InvalidPathException;

public class Database {
    String path;
    Schema[] schemas = {};

    public Database(String path) {
        this.path = path;
        this.update();
    }

    public String getPath() {
        return this.path;
    }
    public Database setPath(String path) {
        this.path = path;
        return this;
    }
    public Schema[] getSchemas() {
        this.update();
        return this.schemas;
    }
    public void update() {
        File folder = new File(this.path);
        File[] files = folder.listFiles();
        for(File file : files) {
            JSONObject internal = new JSONObject();
            JSONObject properties = internal.getJSONObject("PROPERTIES");
            JSONObject data = internal.getJSONObject("DATA");
            this.schemas = ArrayModification.appendElement(this.schemas, new Schema(this, file.getName(), internal, properties, data));
        }
    }
    public Schema getSchema(String name) {
        for(Schema schema : this.schemas) {
            if(schema.getName().equals(name)) {
                return schema;
            }
        }
        throw new SchemaNotFoundException(name);
    }
}
