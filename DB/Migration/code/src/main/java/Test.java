import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by nofuture on 11/14/2014.
 */
public class Test {

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

    LinkedList<Issue> getIssueList() throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("D:\\Downloads\\comics\\comics_json.txt"));
        in.read();

        String inputline = "";
        LinkedList<Issue> issues = new LinkedList<Issue>();


        while ((inputline = in.readLine()) != null) {
            Issue issue = new Issue();
            issue.setName((new JSONObject(inputline)).getJSONArray("sections").getJSONObject(0).getString("title"));
            issues.add(issue);
        }

        return issues;
    }

    Object read(String st) throws Exception {
        InputStream file = new FileInputStream(st);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);
        return input.readObject();
    }

    public String getResponce(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public void write(Object obj, String st) throws Exception {
        OutputStream file = new FileOutputStream(st);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        output.writeObject(obj);
        output.close();
    }

    LinkedList<Volume> getVolumeList(LinkedList<Issue> issueList) {

        LinkedList<Volume> volumes = new LinkedList<Volume>();
        Volume volume = null;

        for (int i = 0; i < issueList.size(); i++) {

            if (volume != null && volume.getName().equals(getVolumeName(issueList.get(i)))) {
                //volume.addIssue(issueList.get(i));

            } else {
                if (volume != null) volumes.add(volume);
                //volume = new Volume(getVolumeName(issueList.get(i)));
                //volume.addIssue(issueList.get(i));
            }
        }

        return volumes;
    }

    void getPicsForIssues() throws Exception{
        LinkedList<Issue> issues = (LinkedList<Issue>)read("D:\\Downloads\\comics\\issues.txt");

        int c = 0;
        for (int i = 0; i < issues.size(); i++){
            System.out.println(i);
            String img = "";
            try{
                 img = getResponce(
                        "http://marvel.wikia.com/api/v1/Articles/Details?ids=50&titles=" +
                                URLEncoder.encode(issues.get(i).getName().replace(" ", "_"), "UTF-8")+
                                "&abstract=100&width=320&height=320");
                Iterator<String> iter = new JSONObject(img).getJSONObject("items").keys();
                String key = "";
                while(true){
                    key = iter.next();
                    if (key.matches("\\d+"))
                        break;
                }
                String image = "";
                Object obj = new JSONObject(img).getJSONObject("items").getJSONObject(key).get("thumbnail");
                if(obj != JSONObject.NULL) {
                    image = (String) obj;
                }
                //issues.get(i).setPublisher(image);

            } catch (Exception e){
                c++;
            }

        }

        write(issues, "");
    }

    void getImprints() throws Exception{
        String [] imprints = {"Marvel_2099_Comic_Books", "Malibu_Comics", "Marvel_Knights", "Epic_Comics",
                "Marvel_Age_Comic_Books", "CrossGen", "Amalgam_Comics", "Icon_Comics", "Marvel_Comics", "MAX",
                "Curtis_Magazines", "Marvel_Absurd", "Razorline", "Paramount_Comics", "New_Universe", "MC2",
                "Marvel_Next", "Marvel_Music", "Soleil", "Star_Comics", "Marvel_Edge"};

        LinkedList<Volume> volumes = getVolumeList(getIssueList());

        for(String s: imprints){
            JSONObject response = new JSONObject(getResponce("http://marvel.wikia.com/api/v1/Articles/List?limit=10000&category=" + s));
            JSONArray arr = response.getJSONArray("items");

            for(int i = 0; i < arr.length(); i++){

                ListIterator<Volume> volumeIter = volumes.listIterator();

                for(int j = 0; j < volumes.size() && volumeIter.hasNext(); j++){
                    Issue tmp = new Issue();
                    Volume vol = volumeIter.next();
                    tmp.setName(arr.getJSONObject(i).getString("title"));
                    if(getVolumeName(tmp).equals(vol.getName())) {
                        //vol.setImprint(s);
                        break;
                    }
                }
            }

            System.out.println(s);
        }

        write(volumes, "D:\\Downloads\\comics\\volumes.txt");
    }

    public static void main(String[] args) throws Exception {
        if (false) {
            FileParser parser = new FileParser(new FileGetter());
            parser.getComicsList(parser.getVolumeList());
        } else {
            ScriptCreator creator = new ScriptCreator(new FileGetter());
            creator.createCharactersScript();
            creator.createCharrefsScript();
            creator.createComicsScript();
            creator.createImprintsScript();
            creator.createIssuesScript();
            creator.createPublishersScript();
            creator.createVolumesScript();
            creator.createRealmsScript();
        }

    }
}
