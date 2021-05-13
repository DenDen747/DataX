package coin.DataX.lang;

public class DatabaseNotFoundException extends RuntimeException{
    public DatabaseNotFoundException(String path) {
        super("The database " + path + " is not found.");
    }
}
