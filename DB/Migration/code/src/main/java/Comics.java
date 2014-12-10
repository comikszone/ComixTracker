import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by nofuture on 11/16/2014.
 */
public class Comics implements Serializable{
    private int comics_id;
    private static int count = 1;

    public Comics() {
        this.comics_id = count++;
    }

    private String name;
    private String description;
    private String image = "";
    private float rating;
    private int releaseDate;
    private int publisherId;
    private int imprintId;

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public void getPic(){
        ListIterator<Volume> volumeListIterator = volumes.listIterator();
        while (volumeListIterator.hasNext()){
            Volume volume = volumeListIterator.next();
            if (volume.getImage() != null){
                image = volume.getImage();
                break;
            }
        }
    }

    public void getImprint(){

    }

    public int getImprintId() {
        return imprintId;
    }

    public void setImprintId(int imprintId) {
        this.imprintId = imprintId;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(Volume volume) {
        this.volumes.add(volume);
    }

    private List<Volume> volumes = new LinkedList<Volume>();

    public int getComics_id() {
        return comics_id;
    }

    public void setComics_id(int comics_id) {
        this.comics_id = comics_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    String sqlInsert(){
        return String.format("insert into comics (comics_id, name, image, name_lowercase) values (%d, '%s', '%s', '%s')",
                comics_id,
                name.replaceAll("'", "''"),
                image == null ? "" : image.replaceAll("'", "''"),
                name.replaceAll("'", "''").toLowerCase());
    }
}
