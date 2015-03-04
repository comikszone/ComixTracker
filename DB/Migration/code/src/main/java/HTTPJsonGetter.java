import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by nofuture on 11/28/2014.
 */
public class HTTPJsonGetter extends HTTPGetter implements JsonGetter{
    @Override
    public JSONObject get(String url) throws Exception{

        return new JSONObject(sendGet(basepath + url));
    }

    public HTTPJsonGetter(String s){
        super(s);
    }

}
