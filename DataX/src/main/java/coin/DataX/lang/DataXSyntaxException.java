package coin.DataX.lang;

public class DataXSyntaxException extends RuntimeException{
    public DataXSyntaxException(String syntax, String error) {
        super("Syntax error in \"" + syntax + "\" at \"" + error + "\".");
    }
    public DataXSyntaxException(String syntax, String error, String comment) {
        super("Syntax error in \"" + syntax + "\" at \"" + error + "\"." + " " + comment);
    }
    public DataXSyntaxException(String errorMessage) {
        super(errorMessage);
    }
}
