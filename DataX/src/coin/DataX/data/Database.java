package coin.DataX.data;

import coin.DataX.data.statics.array.ArrayModification;
import coin.DataX.lang.CorruptDatabaseException;
import coin.DataX.lang.PathException;

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
    }

    public String getPath() {
        return this.path;
    }
    public Database setPath(String path) {
        this.path = path;
        return this;
    }
    public Schema[] getSchemas() {
        return this.schemas;
    }

    protected void addSchema(Schema schema) {
        try {
            File file = new File(this.path + "/" + schema.getName() + ".datax");
            if(!file.createNewFile()) {
                throw new PathException();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.path + "/" + schema.getName() + ".datax"));
            bufferedWriter.append("PROPERTIES {\n\n}\nDATA {\n\n}\n");
            bufferedWriter.close();
            this.schemas = ArrayModification.appendElement(this.schemas, schema);
        }
        catch(Exception e) {
            throw new CorruptDatabaseException();
        }
    }
}
