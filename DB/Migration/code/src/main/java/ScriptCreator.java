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
        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Issues.sql");
        List<Issue> issueList = (List<Issue>) getter.getFromFile("D:\\project\\issues_v3");
        writer.printf("begin;");
        for (Issue i: issueList){
            writer.printf("insert into issue (name, img, volume_id) values ( '%s', '%s', %d);%n",
                     i.getName().replaceAll("'", "''"), i.getImage(), i.getVolumeId());
        }
        writer.printf("commit");
        writer.close();

    }

    void createVolumesScript() throws Exception {
        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Volumes.sql");
        List<Volume> volumeList  = (List<Volume>) getter.getFromFile("D:\\project\\volumes_v2");
        writer.printf("begin;");
        for (Volume v: volumeList){
            writer.printf("insert into volume ( name, img, comics_id) values ( '%s', '%s', %d);%n",
                    v.getName().replaceAll("'", "''"), v.getImage(), v.getComics_id());
        }
        writer.printf("commit");
        writer.close();

    }

    void createComicsScript() throws Exception {
        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Comics.sql");
        List<Comics> comicsList  = (List<Comics>) getter.getFromFile("D:\\project\\comics_v1");
        writer.printf("begin;");
        for (Comics c: comicsList){
            writer.printf("insert into comics (name, image, publisher_id, imprint_id) values ( '%s', '%s', %d, %d);%n",
                     c.getName().replaceAll("'", "''"), c.getImage(), c.getPublisherId(), c.getImprintId());
        }
        writer.printf("commit");
        writer.close();

    }

    void createCharactersScript() throws Exception {

        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Characters.sql");
        List<Character> characterList = (List<Character>) getter.getFromFile("D:\\project\\characters_v1");
        ListIterator<Character> charIter = characterList.listIterator();
        writer.printf("begin;");
        int count = 1;
        Map<Integer, Integer> identifiers = new HashMap<Integer, Integer>();
        while (charIter.hasNext()){
            Character character = charIter.next();
            identifiers.put(character.getFirstId(), count++);
        }

        for (Character ch: characterList){
            writer.printf("insert into character ( name, image, realm_id, universe_id) values ( '%s', '%s', %d, %d);%n",
                     ch.getName().replaceAll("'", "''"), ch.getImage(), ch.getRealmId(), ch.getUniverseId());
        }
        writer.printf("commit");
        writer.close();
    }

    void createCharrefsScript() throws Exception {
        int c = 0;
        Set<Pair> set = new HashSet<Pair>();
        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Charrefs.sql");
        List<Issue> issueList = (List<Issue>) getter.getFromFile("D:\\project\\issues_v2");
        List<Character> characterList = (List<Character>) getter.getFromFile("D:\\project\\characters_v1");
        writer.printf("begin;");
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
        writer.printf("commit");
        writer.close();
    }

    void createRealmsScript() throws Exception {
        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Realms.sql");
        Map<String, Realm> realms = (Map<String, Realm>) getter.getFromFile("D:\\project\\realms_v1");
        List<Realm> tmp = new ArrayList<Realm>();
        writer.printf("begin;");
        tmp.addAll(realms.values());
        class mycomp implements Comparator<Realm>{

            @Override
            public int compare(Realm o1, Realm o2) {
                if (o1.getRealmId() > o2.getRealmId())
                    return 1;
                else if(o1.getRealmId() == o2.getRealmId())
                    return 0;
                else return -1;
            }
        }
        Collections.sort(tmp, new mycomp());
        for (Realm r: realms.values()){
            writer.printf("insert into realm ( name) values ( '%s');%n",
                    r.getName().replaceAll("'", "''"));
        }
        writer.printf("commit");
        writer.close();

    }

    void createImprintsScript() throws Exception {
        PrintWriter writer = new PrintWriter("D:\\project\\sql\\Imprints.sql");
        List<Imprint> imprintList = (List<Imprint>) getter.getFromFile("D:\\project\\imprints_v1");
        writer.printf("begin;");
        for (Imprint i: imprintList){
            writer.printf("insert into imprint ( name) values ( '%s');%n",
                     i.getName().replaceAll("'", "''"));
        }
        writer.printf("commit");
        writer.close();

    }

    void createPublishersScript() throws Exception {
        File file = new File("D:\\project\\sql\\Publishers.sql");
        PrintWriter writer = new PrintWriter(file);
        writer.printf("begin;");
        writer.printf("insert into publisher (name) values ('%s');%n", "Marvel");
        writer.printf("insert into publisher ( name) values ('%s');%n", "DC");
        writer.printf("commit");
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
