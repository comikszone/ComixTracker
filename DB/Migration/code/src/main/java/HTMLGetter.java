import org.jsoup.nodes.Document;

/**
 * Created by nofuture on 12/9/2014.
 */
public interface HTMLGetter  {
    public Document get(String url) throws Exception;
}
