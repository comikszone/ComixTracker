import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nofuture on 11/27/2014.
 */
public class JsonParser {
    String basepath;
    String publisher;

    public String getBasepath() {
        return basepath;
    }

    public void setBasepath(String basepath) {
        this.basepath = basepath;
    }

    JsonParser(JsonGetter a, String s) throws Exception{
        basepath = "http://" + s + ".wikia.com";
        getter = a;
    }

    JsonGetter getter;

    List<Issue> getIssues() throws Exception{
        JSONArray array = getter.get("/api/v1/Articles/List?category=Comics&limit=100000").getJSONArray("items");
        List<Issue> issues = new LinkedList<Issue>();

        int c = 0;
        int issueCount = 41964;


        for(int i = 0; i < array.length(); i++) {
            JSONObject tmp = array.getJSONObject(i);
            if (tmp.getString("title").matches("Comics .*"))
                continue;

            Issue issue = new Issue();
            issue.setName(tmp.getString("title"));
            issue.setFirstId(tmp.getInt("id"));
            issue.setUrl(basepath + tmp.getString("url"));
            issue.setIssueId(issueCount++);

            try {
                System.out.printf("%s/api/v1/Articles/Details?ids=%d%n", basepath, issue.getFirstId());
                JSONObject info = getter.get(String.format("/api/v1/Articles/Details?ids=%d", issue.getFirstId()))
                        .getJSONObject("items").getJSONObject(String.valueOf(issue.getFirstId()));
                if (info.get("thumbnail") != JSONObject.NULL)
                    issue.setImage(info.getString("thumbnail"));
            } catch (Exception e) {
                e.printStackTrace();
                c++;
              }

            issues.add(issue);

            System.out.println(i);
            System.out.println(issue.getName());
            System.out.println(issue.getImage());
            System.out.println(issue.getUrl());
            System.out.println(issue.getIssueId());
            System.out.println("========================================");
        }

        System.out.println("Errors: " + c);

        Writer writer = new ObjectWriter();
        writer.write("D:\\project\\DCissues_v1", issues);

        return issues;

    }


    Object getCharacters() throws Exception{
        JSONArray array = getter.get("/api/v1/Articles/List?category=Characters&limit=100000").getJSONArray("items");
        List<Character> characters = new LinkedList<Character>();
        Map<String, Realm> realms = new HashMap<String, Realm>();
        realms.put(null, new Realm("Default Realm"));

        int realmCount = 1662;
        int characterCount = 40227;

        Pattern pattern = Pattern.compile("(.*)\\((.*Earth-? ?.*)\\)");

        int c = 0;

        for(int i = 0; i < array.length(); i++){
            JSONObject tmp = array.getJSONObject(i);

            if (tmp.getString("url").matches(".*\\/User:.*")) continue;
            if (tmp.getString("title").matches("Character(s)? .*")) continue;

            Character character = new Character();
            character.setCharId(characterCount++);

            Matcher matcher = pattern.matcher(tmp.getString("title"));
            Realm realm;

            if (matcher.matches()){
                if(realms.containsKey(matcher.group(2))) {
                    realm = realms.get(matcher.group(2));

                } else {
                    realm = new Realm();
                    realm.setRealmId(realmCount++);
                    realm.setName(matcher.group(2));
                    realms.put(realm.getName(), realm);
                }

                character.setRealmId(realm.getRealmId());
                character.setName(matcher.group(1));
            } else {
                character.setName(tmp.getString("title"));
            }

            character.setFirstId(tmp.getInt("id"));
            character.setUrl(basepath + tmp.getString("url"));

            try {
                System.out.printf("%s/api/v1/Articles/Details?ids=%d%n", basepath, character.getFirstId());
                JSONObject info = getter.get(String.format("/api/v1/Articles/Details?ids=%d", character.getFirstId()))
                        .getJSONObject("items").getJSONObject(String.valueOf(character.getFirstId()));
                if (info.get("thumbnail") != JSONObject.NULL)
                    character.setImage(info.getString("thumbnail"));
                character.setCharAbstract(info.getString("abstract"));
            } catch (Exception e) {
                e.printStackTrace();
                c++;
            }

            characters.add(character);

            System.out.println(i);
            System.out.println(character.getName());
            System.out.println(character.getRealmId());
            System.out.println(character.getImage());
            System.out.println(character.getUrl());
            System.out.println(character.getCharId());
            System.out.println("========================================");
            character.setUniverseId(2); //заменить при смене издательства
        }

        System.out.println("Errors: " + c);

        Writer writer = new ObjectWriter();
        writer.write("D:\\project\\DCcharacters_v1", characters);
        writer.write("D:\\project\\DCrealms_v1", realms);

        return characters;
    }
}
