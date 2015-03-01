import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * Created by nofuture on 11/27/2014.
 */
public class HTMLParser {
    HTMLGetter getter;

    HTMLParser(HTMLGetter a) {
        getter = a;
    }

    void issues(List<Issue> issues) throws Exception {
        ListIterator<Issue> issueListIterator = issues.listIterator();
        Issue issue = new Issue();

        Map<Integer, String> realms = new HashMap<Integer, String>();
        Map<String, Realm> rels = (Map<String, Realm>)new FileGetter().getFromFile("D:\\project\\realms_v1");
        List<Character> chars = (List<Character>)new FileGetter().getFromFile("D:\\project\\characters_v1");

        for(String s: rels.keySet()){
            realms.put(rels.get(s).getRealmId(), s);
        }

        int successfulAdd = 0;
        int c = 0;
        int q = 0;

        Map<String, Character> characters = new HashMap<String, Character>();
        ListIterator<Character> characterListIterator = chars.listIterator();

        while (characterListIterator.hasNext()){
            Character charact = characterListIterator.next();
            characters.put(String.format("%s(%s)", charact.getName(), realms.get(charact.getRealmId())), charact);
        }

        while(issueListIterator.hasNext()){
            try {
                issue = issueListIterator.next();
                if (issue.getUrl().matches(".*\\/User:.*")){
                    issue.setName("User");
                    continue;
                }
                Document doc = getter.get(issue.getName());
                q++;
                if(q % 500 == 0)
                    System.out.printf("went thru %d issues%n", q);

                Element firstH1 = doc.select("h2").first();
                Elements siblings = firstH1.siblingElements();
                for (int i = 1; i < siblings.size(); i++) {
                    Element sibling = siblings.get(i);

                    if ("h2".equals(sibling.tagName()))
                        break;

                    if("<p><b>Featured Characters:</b></p>".equals(sibling.toString()) ||
                            "<p><b>Villains:</b></p>".equals(sibling.toString())){
                        Elements content = siblings.get(i + 1).getElementsByTag("a");

                        for(Element e: content){
                            String name = e.attr("title");
                            if(!name.matches("Previous Appearance.*") && !name.matches("Next Appearance.*") && name != "" &&
                                    characters.containsKey(name)) {
                                issue.setCharacters(characters.get(name));
                                successfulAdd++;
                            }
                        }

                        if ("<p><b>Villains:</b></p>".equals(sibling.toString())) break;
                    }
                }
            } catch (Exception e){
                c++;
                e.printStackTrace();
                System.out.println("Error number: " + c);
                System.out.println("Name: " + issue.getName());
            }

        }
        System.out.println("HTML Errors:" + c);
        System.out.println("Added" + successfulAdd + "characters(not unique)");
        Writer writer = new ObjectWriter();
        writer.write("D:\\project\\issues_v2", issues);
    }
}
