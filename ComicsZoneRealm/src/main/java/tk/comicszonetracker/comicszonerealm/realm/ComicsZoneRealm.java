package tk.comicszonetracker.comicszonerealm.realm;

import com.sun.appserv.security.AppservRealm;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComicsZoneRealm extends AppservRealm{
    
    public static final String PARAM_JAAS_CONTEXT = "jaas-context";
 
    /** Authentication type description */
 
    public static final String AUTH_TYPE = "ComicsZoneRealm aunthefication";
 
    @Override
    public void init(Properties properties) throws BadRealmException, NoSuchRealmException
    {
        String propJaasContext = properties.getProperty(PARAM_JAAS_CONTEXT);
        if (propJaasContext != null) setProperty(PARAM_JAAS_CONTEXT, propJaasContext);
 
        Logger logger = Logger.getLogger(this.getClass().getName());
 
        String realmName = this.getClass().getSimpleName();
 
        logger.log(Level.INFO, realmName + " started. ");
        logger.log(Level.INFO, realmName + ": " + getAuthType());
        logger.log(Level.INFO, realmName + " authentication uses jar file com-booreg-security.jar located at $domain/lib folder ");
 
        for (Map.Entry<Object, Object> property:properties.entrySet()) 
            logger.log(Level.INFO, property.getKey() + ": " + property.getValue());
    }
    
    @Override
    public String getAuthType() {
        return AUTH_TYPE;
    }

    @Override
    public Enumeration getGroupNames(String string) throws InvalidOperationException, NoSuchUserException {
        Vector groups = new Vector();
        groups.add("user");
        groups.add("admin");
        return groups.elements();
    }
}
