import java.io.Serializable;

/**
 * Created by nofuture on 12/1/2014.
 */
public class Universe implements Serializable{
    private static int count = 1;
    private String name;
    private int universeId;

    Universe(){
        universeId = count++;
    }

    Universe(String name){
        universeId = count++;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUniverseId() {
        return universeId;
    }

    public void setUniverseId(int universeId) {
        this.universeId = universeId;
    }
}
