package coin.DataX.data;

import coin.DataX.data.statics.array.ArrayModification;
import coin.DataX.lang.CorruptDatabaseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Schema {
    String name;
    Database database;

    public Schema(Database database, String name) {
        this.database = database;
        this.name = name;
        database.addSchema(this);
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
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.database.path + "/" + this.name + ".datax"));
        }
        catch(Exception e) {
            throw new CorruptDatabaseException();
        }
        return this;
    }
}
