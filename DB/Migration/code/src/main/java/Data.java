//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.*;
//
//
//public class Data {
//
//    private static String USER_AGENT = "Mozilla/5.0";
//    private static String basepath;
//    private static int limit = 50000;
//    private static String publisher;
//
//    Data(String s){
//        basepath ="http://" + s + ".wikia.com";
//        publisher = s;
//    }
//
//    public Document getHTML(String name) throws Exception {
//
//        String tmp = URLEncoder.encode(name);
//        String url = basepath + "/index.php?action=render&title=" + tmp;
//
//        return Jsoup.parse(getResponce(url));
//
//    }
//
//    List<Character> getCharacterList() throws Exception {
//
//        LinkedList<Character> characters = new LinkedList<Character>();
//        String url = basepath + "/api/v1/Articles/List?category=Characters&limit=" + limit;
//        JSONArray jsarr = new JSONObject(getResponce(url)).getJSONArray("items");
//
//        for (int i = 0; i < jsarr.length(); i++) {
//            if (jsarr.getJSONObject(i).getString("url").matches(".*\\/User:.*")) continue;
//            if (jsarr.getJSONObject(i).getString("title").matches("Character(s)? .*")) break;
//            characters.add(characters.size(), new Character(jsarr.getJSONObject(i).getString("title")));
//
//        }
//
//        int c = 0;
//        ListIterator<Character> iter = characters.listIterator();
//
//        for (int i = 0; i < characters.size() && iter.hasNext(); i++) {
//            try {
//                Character character = iter.next();
//                String tmp = character.getName();
//
//                Elements links = getHTML(tmp).getElementsByTag("a");
//
//                for (Element link : links) {
//                    if (link.attr("href").matches(".*img.*\\.wikia\\.nocookie.*"))
//                        character.setImage(link.attr("href"));
//                }
//
//            } catch (Exception e) {
//                c++;
//            }
//
//            System.out.println(i);
//        }
//
//        System.out.println(c);
//        write(characters, "D:\\Downloads\\comics\\New folder\\characters.txt");
//
//        return characters;
//    }
//
//    public String getResponce(String url) throws Exception {
//
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        con.setRequestMethod("GET");
//
//        con.setRequestProperty("User-Agent", USER_AGENT);
//
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        return response.toString();
//    }
//
//    public static String getVolumeName(Issue a) {
//
//        String[] arr = a.getName().split(" ");
//        StringJoiner joiner = new StringJoiner(" ");
//
//        for (int i = 0; i < arr.length; i++) {
//            if (!arr[i].equals("Vol")) {
//                joiner.add(arr[i]);
//
//            } else {
//                joiner.add(arr[i]);
//                joiner.add(arr[i + 1]);
//                break;
//            }
//        }
//
//        return joiner.toString();
//    }
//
//    public static String getComicbookName(Volume a) {
//
//        String[] arr = a.getName().split(" ");
//        StringJoiner joiner = new StringJoiner(" ");
//
//        for (int i = 0; i < arr.length; i++) {
//            if (!arr[i].equals("Vol")) {
//                joiner.add(arr[i]);
//
//            } else break;
//        }
//
//        return joiner.toString();
//    }
//
//    public void write(Object obj, String st) throws Exception {
//
//        OutputStream file = new FileOutputStream(st);
//        OutputStream buffer = new BufferedOutputStream(file);
//        ObjectOutput output = new ObjectOutputStream(buffer);
//
//        output.writeObject(obj);
//
//        output.close();
//    }
//
//    Object read(String st) throws Exception {
//
//        InputStream file = new FileInputStream(st);
//        InputStream buffer = new BufferedInputStream(file);
//        ObjectInput input = new ObjectInputStream(buffer);
//
//        Object obj = input.readObject();
//        input.close();
//
//        return obj;
//    }
//
//    List<Issue> getIssueList() throws Exception {
//
//        BufferedReader in = new BufferedReader(new FileReader("D:\\Downloads\\comics\\comics_json.txt"));
//        in.read();
//
//        String inputline = "";
//        List<Issue> issues = new LinkedList<Issue>();
//
//        while ((inputline = in.readLine()) != null) {
//            Issue issue = new Issue();
//            issue.setName((new JSONObject(inputline)).getJSONArray("sections").getJSONObject(0).getString("title"));
//            issues.add(issue);
//        }
//
//        int c = 0;
//        ListIterator<Issue> iter = issues.listIterator();
//
//        for(int j = 0; j < issues.size() && iter.hasNext(); j++){
//            System.out.println(j);
//
//            try {
//                Issue issue = iter.next();
//                Element firstH1 = getHTML(issue.getName()).select("h2").first();
//                Elements siblings = firstH1.siblingElements();
//                for (int i = 1; i < siblings.size(); i++) {
//                    Element sibling = siblings.get(i);
//
//                    if ("h2".equals(sibling.tagName()))
//                        break;
//
//                    if("<p><b>Featured Characters:</b></p>".equals(sibling.toString()) ||
//                            "<p><b>Supporting Characters:</b></p>".equals(sibling.toString()) ||
//                            "<p><b>Villains:</b></p>".equals(sibling.toString())){
//                        Elements content = siblings.get(i + 1).getElementsByTag("a");
//
//                        for(Element e: content){
//                            String name = e.attr("title");
//                            if(!name.matches("Previous Appearance.*") && !name.matches("Next Appearance.*") && name != "")
//                                issue.appendCharacter(new Character(name));
//                        }
//
//                        if ("<p><b>Villains:</b></p>".equals(sibling.toString())) break;
//                    }
//                }
//            } catch (Exception e){
//                c++;
//            }
//        }
//
//        System.out.print(c);
//        write(issues, "D:\\Downloads\\comics\\issues.txt");
//        return issues;
//    }
//
//    List<IssueFinal> expandCharacterList() throws Exception{
//
//        List<Issue> issuesWithCharacters = (LinkedList<Issue>)read("D:\\Downloads\\comics\\issues.txt");
//        List<Issue> issuesWithPics = (LinkedList<Issue>)read("D:\\Downloads\\comics\\issues_img_publ.txt");
//        List<Volume> volumesWithImprints = (LinkedList<Volume>)read("D:\\Downloads\\comics\\volumes.txt");
//        List<IssueFinal> finalIssues = new LinkedList<IssueFinal>();
//
//        ListIterator<Volume> volumeIterator = volumesWithImprints.listIterator();
//
//        while(volumeIterator.hasNext()){
//            Volume volume = volumeIterator.next();
//            ListIterator<Issue> issueIterator = volume.issues.listIterator();
//
//            while(issueIterator.hasNext()){
//                Issue issue = issueIterator.next();
//                //issue.setImprint(volume.imprint);
//                issue.setPublisher(publisher);
//                finalIssues.add(new IssueFinal(issue));
//            }
//        }
//
//        ListIterator<IssueFinal> finalIssueIterator = finalIssues.listIterator();
//
//        while (finalIssueIterator.hasNext()){
//            IssueFinal finalIssue = finalIssueIterator.next();
//            ListIterator<Issue> issueIterator = issuesWithCharacters.listIterator();
//
//            while (issueIterator.hasNext()){
//                Issue issue = issueIterator.next();
//                if (issue.getName().equals(finalIssue.getName())){
//                    finalIssue.setCharacters(issue.getCharacters());
//                    issuesWithCharacters.remove(issue);
//                    break;
//                }
//            }
//        }
//
//        finalIssueIterator = finalIssues.listIterator();
//
//        while (finalIssueIterator.hasNext()){
//            IssueFinal issueFinal = finalIssueIterator.next();
//            ListIterator<Issue> issueIterator = issuesWithPics.listIterator();
//
//            while (issueIterator.hasNext()){
//                Issue issue = issueIterator.next();
//                if (issueFinal.getName().equals(issue.getName())){
//                    issueFinal.setImage(issue.getPublisher());
//                    issuesWithPics.remove(issue);
//                    break;
//                }
//            }
//        }
//
//        write(finalIssues, "D:\\Downloads\\comics\\final_issues1.txt");
//
//        return finalIssues;
//    }
//
//    List<Volume> getVolumeList(List<IssueFinal> issueList) {
//
//        List<Volume> volumes = new LinkedList<Volume>();
//        Volume volume = null;
//        ListIterator<IssueFinal> iter = issueList.listIterator();
//
//        for (int i = 0; i < issueList.size() && iter.hasNext(); i++) {
//            IssueFinal issue = iter.next();
//
//            if (volume != null && volume.getName().equals(getVolumeName(issue))) {
//                volume.addIssue(issue);
//
//            } else {
//                if (volume != null) volumes.add(volume);
//                volume = new Volume(getVolumeName(issue));
//                volume.addIssue(issue);
//            }
//        }
//
//        return volumes;
//    }
//
//    List<Comicbook> getComicbookList(List<Volume> volumeList) {
//        List<Comicbook> comicbooks = new LinkedList<Comicbook>();
//        Comicbook comicbook = null;
//        ListIterator<Volume> iter = volumeList.listIterator();
//
//        for (int i = 0; i < volumeList.size() && iter.hasNext(); i++) {
//            Volume volume = iter.next();
//            if (comicbook != null && comicbook.getName().equals(getComicbookName(volume))) {
//                comicbook.addVolume(volume);
//
//            } else {
//                if (comicbook != null) comicbooks.add(comicbook);
//                comicbook = new Comicbook(getComicbookName(volume));
//                comicbook.addVolume(volume);
//            }
//        }
//
//        return comicbooks;
//    }
//
//    void transform() throws Exception{
//        List<IssueFinal> issues =  (LinkedList<IssueFinal>)read("D:\\Downloads\\comics\\final_issues1.txt");
//        LinkedList<IssueFinal> finalIssues = new LinkedList<IssueFinal>();
//
//        ListIterator<IssueFinal> issuesIter = issues.listIterator();
//        int c = 0;
//        while(issuesIter.hasNext()){
//            c++;
//            System.out.println(c);
//            IssueFinal issue = issuesIter.next();
//            finalIssues.add(issue);
//            LinkedList<Character> chars = (LinkedList<Character>) issue.getCharacters().clone();
//
//            issue.getCharacters().clear();
//
//
//            for(int i = 0; i < chars.size(); i++){
//                int id = chars.get(i).getCharacter_id();
//                finalIssues.getLast().appendCharacter(new CharacterFinal(chars.get(i), "Marvel", id));
//            }
//        }
//
//        write(finalIssues, "D:\\Downloads\\comics\\transformed_issues.txt");
//    }
//
//    void createCharacterList() throws Exception{
//
//        List<String> names = new LinkedList<String>();
//        List<IssueFinal> issues = (LinkedList<IssueFinal>)read("D:\\Downloads\\comics\\transformed_issues.txt");
//        List<CharacterFinal> characters = new LinkedList<CharacterFinal>();
//
//        ListIterator<IssueFinal> issueIter = issues.listIterator();
//        int c = 0;
//        while(issueIter.hasNext()){
//            c++;
//            //System.out.println(c);
//            ListIterator<Character> charIter = issueIter.next().getCharacters().listIterator();
//
//            while (charIter.hasNext()){
//                CharacterFinal character = (CharacterFinal)charIter.next();
//                if(names.contains(character.getName())){
//
//                    continue;
//                } else {
//                    names.add(character.getName());
//
//                    characters.add((CharacterFinal) character);
//                }
//            }
//        }
//        System.out.println(characters.size());
//        write(characters, "D:\\Downloads\\comics\\final_characters.txt");
//    }
//
//    void pics() throws Exception{
//        List<CharacterFinal> characters = (LinkedList<CharacterFinal>)read("D:\\Downloads\\comics\\final_characters.txt");
//
//
//
//
//
//    }
//
//    public static void main(String[] args) throws Exception {
//
//        LinkedList<CharacterFinal> chars = (LinkedList)new Data("").read("D:\\Downloads\\comics\\final_characters.txt");
//
//        PrintWriter writer = new PrintWriter("D:\\Downloads\\comics\\SQLcharacters.txt");
//
//        ListIterator<CharacterFinal> iter = chars.listIterator();
//
//        HashMap<String, Integer> charMap = new HashMap<String, Integer>();
//
//        JSONArray arr = new JSONObject(new Data("marvel").getResponce("http://marvel.wikia.com/api/v1/Articles/List?category=Characters&limit=50000")).getJSONArray("items");
//        for (int i = 0; i < arr.length(); i++){
//            charMap.put(arr.getJSONObject(i).getString("title"), arr.getJSONObject(i).getInt("id"));
//        }
//
//        //System.out.print(charMap.size());
//
//        while (iter.hasNext()){
//            int err = 0;
//            CharacterFinal character = iter.next();
//            if (!charMap.containsKey(character.getName()))  continue;
//            String img = "";
//            try {
//                String s = new Data("marvel").getResponce("http://marvel.wikia.com/api/v1/Articles/Details?ids="
//                        + charMap.get(character.getName()) +
//                        "&abstract=100&width=200&height=200");
//                Object obj = new JSONObject(s).getJSONObject("items").getJSONObject(charMap.get(character.getName()).toString()).get("thumbnail");
//                img = obj == JSONObject.NULL ? "" : (String) obj;
//            } catch (Exception e){
//                err++;
//                System.out.println(err);
//            }
//
//            Character tmp = new Character();
//            tmp.setChar_id(charMap.get(character.getName()));
//            character.realm();
//            tmp.setImage(img);
//            tmp.setName(character.getName());
//            tmp.setUniverse(character.getUniverse());
//            tmp.setRealm(character.getRealm());
//            writer.println(tmp.sqlInsert());
//        }
//
//        writer.close();
//
//        /*List<Comicbook> comics = (LinkedList<Comicbook>)new Data("").read("D:\\Downloads\\comics\\result_comicbooks.txt");
//
//        ListIterator<Comicbook> iterator = comics.listIterator();
//
//        PrintWriter comicWriter = new PrintWriter("D:\\Downloads\\comics\\SQLcomics.txt");
//        PrintWriter volumeWriter = new PrintWriter("D:\\Downloads\\comics\\SQLvolumes.txt");
//        PrintWriter issueWriter = new PrintWriter("D:\\Downloads\\comics\\SQLissues.txt");
//        PrintWriter charrefWriter = new PrintWriter("D:\\Downloads\\comics\\SQLcharrefs.txt");
//
//        int c = 0;
//
//        while (iterator.hasNext()){
//            Comicbook comicbook = iterator.next();
//            ComicbookEntity tmp = new ComicbookEntity();
//            tmp.setName(comicbook.getName());
//            tmp.setImage(comicbook.getVolumes().get(0).getImg());
//            tmp.setComics_id(comicbook.getComicbook_id());
//
//            comicWriter.println(tmp.sqlInsert());
//
//            ListIterator<Volume> volumeIter = comicbook.getVolumes().listIterator();
//
//            while(volumeIter.hasNext()){
//                Volume volume = volumeIter.next();
//                VolumeEntity tmpv = new VolumeEntity();
//                tmpv.setComics_id(volume.getComicbook_id());
//                tmpv.setImage(volume.getImg());
//                tmpv.setName(volume.getName());
//                tmpv.setVolume_id(volume.getVolume_id());
//                volumeWriter.println(tmpv.sqlInsert());
//
//                ListIterator<IssueFinal> issueIter = (ListIterator)volume.getIssues().listIterator();
//
//                while(issueIter.hasNext()){
//
//                    IssueFinal issue = issueIter.next();
//                    IssueEntity tmpi = new IssueEntity();
//                    tmpi.setName(issue.getName());
//                    tmpi.setVolume_id(issue.getVolume_id());
//                    tmpi.setImage(issue.getImage());
//                    tmpi.setImprint(issue.getImprint());
//                    tmpi.setPublisher(issue.getPublisher());
//                    tmpi.setIssue_id(issue.getIssue_id());
//                    issueWriter.println(tmpi.sqlInsert());
//
//                    ListIterator<CharacterFinal> charIter = (ListIterator)issue.getCharacters().listIterator();
//
//                    while (charIter.hasNext()){
//
//                        CharacterFinal character = charIter.next();
//                        if (!charMap.containsKey(character.getName()))
//                            continue;
//
//                        IssueToCharEntity entity = new IssueToCharEntity();
//                        entity.setIssue_id(issue.getIssue_id());
//                        entity.setChar_id(charMap.get(character.getName()));
//
//                        charrefWriter.println(entity.sqlInsert());
//                    }
//                }
//            }
//        }
//
//        charrefWriter.close();
//        issueWriter.close();
//        volumeWriter.close();
//        comicWriter.close();
//
//*/
//    }
//}
//
//
//
//
