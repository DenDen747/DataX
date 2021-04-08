package coin.DataX.lang;

public class SchemaNotFoundException extends RuntimeException{
    public SchemaNotFoundException(String name) {
        super("The schema " + name + " is not found within the database.");
    }
}
