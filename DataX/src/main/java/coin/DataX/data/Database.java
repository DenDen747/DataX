package coin.DataX.data;

import coin.DataX.lang.DatabaseNotFoundException;
import coin.DataX.lang.OverrideException;
import org.json.JSONObject;
import coin.DataX.data.statics.array.ArrayModification;
import coin.DataX.lang.CorruptDatabaseException;
import coin.DataX.lang.TableNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
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
        try {
            this.tables = new Table[]{};
            File directory = new File(path);
            File[] files = directory.listFiles();
            for (File file : files) {
                this.tables = ArrayModification.appendElement(this.tables, new Table(this, file.getName().substring(0, file.getName().indexOf(".json"))));
            }
        }
        catch(Exception e) {
            throw new DatabaseNotFoundException(this.path);
        }
    }
    public Table returnTable(String name) {
        try {
            return this.createTable(name);
        }
        catch(Exception e1) {
            try {
                return this.getTable(name);
            }
            catch(Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }
    public Table createTable(String name) {
        this.update();
        try {
            for(Table table : this.tables) {
                if(table.getName().equals(name)) {
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
    public void deleteTable(String name) {
        this.update();

        for (Table table : this.tables) {
            if (table.getName().equals(name)) {
                String path = this.path + "/" + table.getName() + ".json";
                File file = new File(path);
                file.delete();
                return;
            }
        }

        throw new TableNotFoundException(name);
    }
    public void deleteTable(Table table) {
        this.update();

        String path = this.path + "/" + table.getName() + ".json";
        File file = new File(path);
        file.delete();
    }
    public void selfDestruct() {
        this.update();
        File database = new File(this.path);
        database.delete();
    }
    public void selfDestructOnExit() {
        this.update();
        File database = new File(this.path);
        database.deleteOnExit();
    }
}
