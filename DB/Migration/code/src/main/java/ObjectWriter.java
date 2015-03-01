import java.io.*;

/**
 * Created by nofuture on 11/27/2014.
 */
public class ObjectWriter implements Writer {
    @Override
    public void write(String filepath, Object obj) throws IOException {
        OutputStream file = new FileOutputStream(filepath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(obj);

        output.close();

    }
}
