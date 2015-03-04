import java.io.*;

/**
 * Created by nofuture on 12/9/2014.
 */
public class FileGetter {

    public Object getFromFile(String path) throws Exception{
        InputStream file = new FileInputStream(path);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);

        Object obj = input.readObject();
        input.close();

        return obj;
    }
}
