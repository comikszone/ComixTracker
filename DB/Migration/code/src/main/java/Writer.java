import java.io.IOException;

/**
 * Created by nofuture on 11/27/2014.
 */
public interface Writer {
    public abstract void write(String filepath, Object obj) throws IOException;
}
