package renewed.DataX.lang;

public class CorruptDatabaseException extends RuntimeException{
    public CorruptDatabaseException() {
        super("The given database is corrupt.");
    }
}
