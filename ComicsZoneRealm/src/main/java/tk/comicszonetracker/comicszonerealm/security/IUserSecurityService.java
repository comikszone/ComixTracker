package tk.comicszonetracker.comicszonerealm.security;

import java.util.List;

public interface IUserSecurityService {
 
    public void validatePassword(String nickname, String password) throws Exception;
 
    public List<String> getGroups(String nickname);
}