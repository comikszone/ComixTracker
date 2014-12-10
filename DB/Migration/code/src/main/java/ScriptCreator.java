import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by nofuture on 12/4/2014.
 */
public class ScriptCreator {

    FileGetter getter;

    public ScriptCreator(FileGetter getter) {

        this.getter = getter;
    }

    void createIssuesScript() throws Exception {
        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Issues.txt");
        List<Issue> issueList = (List<Issue>) getter.getFromFile("D:\\project\\issues_v3");
        for (Issue i: issueList){
            writer.printf("insert into issue (issue_id, name, img, volume_id) values (%d, '%s', '%s', %d);%n",
                    i.getIssueId(), i.getName().replaceAll("'", "''"), i.getImage(), i.getVolumeId());
        }
        writer.close();

    }

    void createVolumesScript() throws Exception {
        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Volumes.txt");
        List<Volume> volumeList  = (List<Volume>) getter.getFromFile("D:\\project\\volumes_v2");
        for (Volume v: volumeList){
            writer.printf("insert into volume (volume_id, name, img, comics_id) values (%d, '%s', '%s', %d);%n",
                    v.getVolume_id(), v.getName().replaceAll("'", "''"), v.getImage(), v.getComics_id());
        }
        writer.close();

    }

    void createComicsScript() throws Exception {
        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Comics.txt");
        List<Comics> comicsList  = (List<Comics>) getter.getFromFile("D:\\project\\comics_v1");
        for (Comics c: comicsList){
            writer.printf("insert into comics (comics_id, name, image, publisher_id, imprint_id) values (%d, '%s', '%s', %d, %d);%n",
                    c.getComics_id(), c.getName().replaceAll("'", "''"), c.getImage(), c.getPublisherId(), c.getImprintId());
        }
        writer.close();

    }

    void createCharactersScript() throws Exception {

        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Characters.txt");
        List<Character> characterList = (List<Character>) getter.getFromFile("D:\\project\\characters_v1");
        ListIterator<Character> charIter = characterList.listIterator();

        int count = 1;
        Map<Integer, Integer> identifiers = new HashMap<Integer, Integer>();
        while (charIter.hasNext()){
            Character character = charIter.next();
            identifiers.put(character.getFirstId(), count++);
        }

        for (Character ch: characterList){
            writer.printf("insert into character (char_id, name, image, realm_id, universe_id) values (%d, '%s', '%s', %d, %d);%n",
                    identifiers.get(ch.getFirstId()), ch.getName().replaceAll("'", "''"), ch.getImage(), ch.getRealmId(), ch.getUniverseId());
        }
        writer.close();
    }

    void createCharrefsScript() throws Exception {
        int c = 0;
        Set<Pair> set = new HashSet<Pair>();
        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Charrefs.txt");
        List<Issue> issueList = (List<Issue>) getter.getFromFile("D:\\project\\issues_v2");
        List<Character> characterList = (List<Character>) getter.getFromFile("D:\\project\\characters_v1");

        int count = 1;
        Map<Integer, Integer> identifiers = new HashMap<Integer, Integer>();

        for (Character ch: characterList){
            identifiers.put(ch.getFirstId(), count++);
        }

        for (Issue i: issueList) {
            if (i.getCharacters() != null)
                for (Character ch : i.getCharacters()) {
                    Pair tmp = new Pair(i.getIssueId(), identifiers.get(ch.getFirstId()));
                    if (tmp.a == 854){
                        System.out.println(set.contains(new Pair(854, 23373)));
                        System.out.println(tmp);
                    }
                    set.add(tmp);

                }
        }
        Pair ss = new Pair(854, 23373);
        for(Pair p: set){
            c++;
            //System.out.println(c);
            if (p.a < 41948)
                writer.printf("insert into charrefs (issue_id, char_id) values (%d, %d);%n",
                    p.a, p.b);
            if (p.equals(ss)){
                System.out.println("HALLELUJAH");
            }
        }
        writer.close();
    }

    void createRealmsScript() throws Exception {
        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Realms.txt");
        Map<String, Realm> realms = (Map<String, Realm>) getter.getFromFile("D:\\project\\realms_v1");
        for (Realm r: realms.values()){
            writer.printf("insert into realm (realm_id, name) values (%d, '%s');%n",
                    r.getRealmId(), r.getName().replaceAll("'", "''"));
        }
        writer.close();

    }

    void createImprintsScript() throws Exception {
        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Imprints.txt");
        List<Imprint> imprintList = (List<Imprint>) getter.getFromFile("D:\\project\\imprints_v1");
        for (Imprint i: imprintList){
            writer.printf("insert into imprint (imprint_id, name) values (%d, '%s');%n",
                    i.getId(), i.getName().replaceAll("'", "''"));
        }
        writer.close();

    }

    void createPublishersScript() throws Exception {
        File file = new File("D:\\project\\sql\\Publishers.txt");
        PrintWriter writer = new PrintWriter(file);
        writer.printf("insert into publisher (publisher_id, name) values (%d, '%s');%n", 1, "Marvel");
        writer.printf("insert into publisher (publisher_id, name) values (%d, '%s');%n", 2, "DC");
        writer.close();
    }

}

class Pair{
    int a;
    int b;

    Pair(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;

        Pair pair = (Pair) o;

        return a == pair.a && b == pair.b;
    }

    public String toString(){
        return a + " " + b;
    }

    @Override
    public int hashCode() {
        int result;
        result = 31*a + 100*b;
        return result;
    }
}
