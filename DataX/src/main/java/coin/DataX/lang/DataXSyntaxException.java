package coin.DataX.lang;

public class DataXSyntaxException extends RuntimeException{
    public DataXSyntaxException(String syntax, String error) {
        super("Syntax error in \"" + syntax + "\" at \"" + error + "\".");
    }
}
