package coin.DataX.lang;

public class DataTypeNotFoundException extends RuntimeException{
    public DataTypeNotFoundException(String dataType) {
        super("The data type " + dataType + " does not exist.");
    }
}
