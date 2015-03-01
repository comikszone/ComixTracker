import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URLEncoder;

/**
 * Created by nofuture on 11/28/2014.
 */
public class HTMLHTTPGetter extends HTTPGetter implements HTMLGetter  {
    @Override
    public Document get(String title) throws Exception{
        String url = basepath + "/index.php?action=render&title=" + URLEncoder.encode(title, "UTF-8");
        return Jsoup.parse(sendGet(url));
    }

    public HTMLHTTPGetter(String s){
        super(s);
    }
}
