package coin.DataX.lang;

public class TableNotFoundException extends RuntimeException{
    public TableNotFoundException(String name) {
        super("The table " + name + " is not found within the database.");
    }
}
