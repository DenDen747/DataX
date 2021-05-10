package coin.DataX.data.statics.dataType;

public class DataType {
    public static DataType STRING = new DataType("STRING");
    public static DataType INT = new DataType("INT");
    public static DataType DOUBLE = new DataType("DOUBLE");
    public static DataType BOOLEAN = new DataType("BOOLEAN");

    private final String value;

    private DataType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
