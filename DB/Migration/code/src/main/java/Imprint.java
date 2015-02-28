import java.io.Serializable;

/**
 * Created by nofuture on 12/3/2014.
 */
public class Imprint implements Serializable {
    private int id;
    private static int count = 1;
    private String name;

    public String getName() {
        return name;
    }

    Imprint(){
        id = count++;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
