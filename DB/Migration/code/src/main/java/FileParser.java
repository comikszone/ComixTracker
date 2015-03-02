import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Created by nofuture on 12/3/2014.
 */
public class FileParser  {

    FileGetter getter;

    public FileParser(FileGetter getter) {
        this.getter = getter;
    }

    public static String getVolumeName(Issue a) {

        String[] arr = a.getName().split(" ");
        StringJoiner joiner = new StringJoiner(" ");

        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].equals("Vol")) {
                joiner.add(arr[i]);

            } else {
                joiner.add(arr[i]);
                joiner.add(arr[i + 1]);
                break;
            }
        }

        return joiner.toString();
    }

    public static String getComicbookName(Volume a) {

        String[] arr = a.getName().split(" ");
        StringJoiner joiner = new StringJoiner(" ");

        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].equals("Vol")) {
                joiner.add(arr[i]);

            } else break;
        }

        return joiner.toString();
    }


    List<Volume> getVolumeList() throws Exception {
        List<Issue> issueList = (LinkedList)getter.getFromFile("D:\\project\\issues_v2");
        List<Volume> volumes = new LinkedList<Volume>();

        Volume volume = null;
        ListIterator<Issue> iter = issueList.listIterator();


        while(iter.hasNext()){
            Issue issue = iter.next();
            if (issue.getName().equals("User")) {
                issueList.remove(issue);
                break;
            }
        }

        iter = issueList.listIterator();

        while (iter.hasNext()) {
            Issue issue = iter.next();

            if (volume != null && volume.getName().equals(getVolumeName(issue))) {
                issue.setVolumeId(volume.getVolume_id());
                volume.setIssues(issue);

            } else {
                if (volume != null) {
                    volume.getPic();
                    volumes.add(volume);
                }
                volume = new Volume();
                issue.setVolumeId(volume.getVolume_id());
                volume.setName(getVolumeName(issue));
                volume.setIssues(issue);
            }
        }

        String [] imprints = {"Marvel_2099_Comic_Books", "Malibu_Comics", "Marvel_Knights", "Epic_Comics",
                "Marvel_Age_Comic_Books", "CrossGen", "Amalgam_Comics", "Icon_Comics", "Marvel_Comics", "MAX",
                "Curtis_Magazines", "Marvel_Absurd", "Razorline", "Paramount_Comics", "New_Universe", "MC2",
                "Marvel_Next", "Marvel_Music", "Soleil", "Star_Comics", "Marvel_Edge"};

        List<Imprint> imprintList = new LinkedList<Imprint>();

        HTTPJsonGetter getter1 = new HTTPJsonGetter("marvel");

        for(String s: imprints){
            Imprint imprint = new Imprint();
            imprint.setName(s);
            imprintList.add(imprint);

            JSONObject response = getter1.get(String.format("/api/v1/Articles/List?limit=10000&category=%s", s));
            JSONArray arr = response.getJSONArray("items");

            for(int i = 0; i < arr.length(); i++){

                ListIterator<Volume> volumeIter = volumes.listIterator();

                for(int j = 0; j < volumes.size() && volumeIter.hasNext(); j++){
                    Issue tmp = new Issue();
                    Volume vol = volumeIter.next();
                    tmp.setName(arr.getJSONObject(i).getString("title"));
                    if(getVolumeName(tmp).equals(vol.getName())) {
                        vol.setImprintId(imprint.getId());
                        break;
                    }
                }
            }

            System.out.println(s);
        }
        new ObjectWriter().write("D:\\project\\imprints_v1", imprintList);
        new ObjectWriter().write("D:\\project\\volumes_v1", volumes);
        new ObjectWriter().write("D:\\project\\issues_v3", issueList);
        return volumes;
    }

    List<Comics> getComicsList(List<Volume> volumeList) throws IOException {
        List<Comics> comicsList = new LinkedList<Comics>();
        Comics comics = null;
        ListIterator<Volume> volumeListIterator = volumeList.listIterator();

        for (int i = 0; i < volumeList.size() && volumeListIterator.hasNext(); i++) {
            Volume volume = volumeListIterator.next();
            if (comics != null && comics.getName().equals(getComicbookName(volume))) {
                comics.setVolumes(volume);
                volume.setComics_id(comics.getComics_id());

            } else {
                if (comics != null) {
                    comics.getPic();
                    comicsList.add(comics);
                }
                comics = new Comics();
                comics.setName(getComicbookName(volume));
                volume.setComics_id(comics.getComics_id());
                comics.setVolumes(volume);
                comics.setPublisherId(1);
                comics.setImprintId(volume.getImprintId());
            }
        }
        new ObjectWriter().write("D:\\project\\comics_v1", comicsList);
        new ObjectWriter().write("D:\\project\\volumes_v2", volumeList);
        return comicsList;
    }

}
