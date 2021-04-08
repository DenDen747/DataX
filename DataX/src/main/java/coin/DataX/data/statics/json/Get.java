package coin.DataX.data.statics.json;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Get {
    public static JSONObject getJSONObjectFromPath(String path) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(path);

        JSONTokener tokener = new JSONTokener(inputStream);

        return new JSONObject(tokener);
    }
}
