package tk.comicszonetracker.comicszonerealm.realm;

import com.sun.appserv.security.AppservPasswordLoginModule;
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
        
        String[] groups = {"user"};
        commitUserAuthentication(groups);
    }
}
