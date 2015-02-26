package tk.comicszonetracker.comicszonerealm.realm;

import com.sun.appserv.security.AppservPasswordLoginModule;
import java.util.List;
import javax.security.auth.login.LoginException;
import tk.comicszonetracker.comicszonerealm.security.*;

public class ComicsZoneRealmModule extends AppservPasswordLoginModule{
    
    @Override
    protected void authenticateUser() throws LoginException {
        IUserSecurityService uss = new UserSecurityService();

        try {
            uss.validatePassword(_username, new String(_passwd));
        } catch (Exception ex) {
            throw new LoginException(ex.toString());
        }
        
        List<String> userGroups = uss.getGroups(_username);
        String[] groups = userGroups.toArray(new String[0]);
        commitUserAuthentication(groups);
    }
}
