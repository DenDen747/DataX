package coin.DataX.data;

import org.jetbrains.annotations.Nullable;

public class ResultSet {
    private final Object[][] rs;
    int id = -1;

    protected ResultSet(Object[][] rs) {
        this.rs = rs;
    }
    protected ResultSet(Object[][] rs, int id) {
        this.rs = rs;
        this.id = id;
    }

    public Object[][] get() {
        return this.rs;
    }
    public int getId() {
        return this.id;
    }
}
