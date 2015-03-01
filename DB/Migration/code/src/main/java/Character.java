import java.io.Serializable;

/**
 * Created by nofuture on 11/16/2014.
 */
public class Character implements Serializable {
    public int getFirstId() {
        return firstId;
    }

    public void setFirstId(int firstId) {
        this.firstId = firstId;
    }

    private int firstId;
    private String name;

    public String getCharAbstract() {
        return charAbstract;
    }

    public void setCharAbstract(String charAbstract) {
        this.charAbstract = charAbstract;
    }

    private String charAbstract;
    private String image;
    private int charId;
    private int universeId;
    private int realmId;
    private String description;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    private String realName;

    String sqlInsert(){
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCharId() {
        return charId;
    }

    public void setCharId(int charId) {
        this.charId = charId;
    }

    public int getUniverseId() {
        return universeId;
    }

    public void setUniverseId(int universeId) {
        this.universeId = universeId;
    }

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
