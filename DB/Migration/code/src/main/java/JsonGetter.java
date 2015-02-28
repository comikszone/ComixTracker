import org.json.JSONObject;

/**
 * Created by nofuture on 12/9/2014.
 */
public interface JsonGetter {
    public JSONObject get(String path) throws Exception;
}
