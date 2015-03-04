import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by nofuture on 11/16/2014.
 */
public class Issue implements Serializable {
    private int firstId;

    public int getFirstId() {
        return firstId;
    }

    public void setFirstId(int firstId) {
        this.firstId = firstId;
    }

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    Issue(){
        issueId = count++;
    }

    private static int count = 1;
    private String name;
    private String image = "";
    private int issueId;
    private int volumeId;
    private int publisherIdd;
    private int releaseDate;
    private int imprintId;
    private float rating;
    private String description;
    private List<Character> characters = new LinkedList<Character>();

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(Character character) {
        if (characters == null)
            characters = new LinkedList<Character>();
        this.characters.add(character);
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

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public int getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(int volumeId) {
        this.volumeId = volumeId;
    }

    public int getPublisherIdd() {
        return publisherIdd;
    }

    public void setPublisherIdd(int publisherIdd) {
        this.publisherIdd = publisherIdd;
    }

    public int getImprintId() {
        return imprintId;
    }

    public void setImprintId(int imprintId) {
        this.imprintId = imprintId;
    }

    String sqlInsert() {
        return null;
    }
}
