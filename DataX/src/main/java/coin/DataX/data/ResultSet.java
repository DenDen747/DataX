package coin.DataX.data;

public class ResultSet {
    private final Object[][] rs;

    protected ResultSet(Object[][] rs) {
        this.rs = rs;
    }

    public Object[][] get() {
        return this.rs;
    }
}
