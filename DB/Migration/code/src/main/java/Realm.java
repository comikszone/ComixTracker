import java.io.Serializable;

/**
 * Created by nofuture on 12/1/2014.
 */
public class Realm implements Serializable{
    private String name;
    private int realmId;
    private static int count = 0;

    Realm(String name){
        realmId = count++;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
    }

    Realm(){
        realmId = count++;

    }

}
