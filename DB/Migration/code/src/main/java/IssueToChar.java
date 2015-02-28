/**
 * Created by nofuture on 11/16/2014.
 */
public class IssueToChar {
    private int char_id;
    private int issue_id;

    public int getChar_id() {
        return char_id;
    }

    public void setChar_id(int char_id) {
        this.char_id = char_id;
    }

    public int getIssue_id() {
        return issue_id;
    }

    public void setIssue_id(int issue_id) {
        this.issue_id = issue_id;
    }

    String sqlInsert(){
        return String.format("insert into charrefs (char_id, issue_id) values (%d, %d)",
                char_id, issue_id);
    }
}
