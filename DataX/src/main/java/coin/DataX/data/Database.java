package coin.DataX.data;

import coin.DataX.lang.OverrideException;
import org.json.JSONObject;
import coin.DataX.data.statics.array.ArrayModification;
import coin.DataX.lang.CorruptDatabaseException;
import coin.DataX.lang.TableNotFoundException;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Database {
    private String path;
    private Table[] tables;

    public Database(String path) {
        this.path = path;
        this.update();
    }

    public String getPath() {
        return this.path;
    }
    public String getAbsolutePath() {
        Path path = Paths.get("");
        return path.toAbsolutePath().toString();
    }

    protected void update() {
        this.tables = new Table[]{};
        File directory = new File(path);
        File[] files = directory.listFiles();
        for(File file : files) {
            this.tables = ArrayModification.appendElement(this.tables, new Table(this, file.getName().substring(0, file.getName().indexOf(".json"))));
        }
    }
    public Table createTable(String name) {
        this.update();
        try {
            for(Table table : this.tables) {
                if(table.getName().equals(name + ".json")) {
                    throw new OverrideException("The table " + name + " already exists.");
                }
            }
            JSONObject file = new JSONObject();
            JSONObject properties = new JSONObject();
            JSONObject data = new JSONObject();

            properties.put("description", "");

            file.put("PROPERTIES", properties);
            file.put("DATA", data);

            FileWriter fileWriter = new FileWriter(this.path + "/" + name + ".json");
            fileWriter.write(file.toString());
            fileWriter.flush();

            this.update();

            return new Table(this, name);
        }
        catch(OverrideException e) {
            throw new OverrideException("The table " + name + " already exists.");
        }
        catch(Exception e) {
            throw new CorruptDatabaseException();
        }
    }
    public Table getTable(String name) {
        this.update();

        for(Table table : this.tables) {
            if(table.getName().equals(name)) {
                return table;
            }
        }
        throw new TableNotFoundException(name);
    }
}
