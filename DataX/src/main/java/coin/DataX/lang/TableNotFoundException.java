package coin.DataX.lang;

public class TableNotFoundException extends RuntimeException{
    public TableNotFoundException(String name) {
        super("The schema " + name + " is not found within the database.");
    }
}
