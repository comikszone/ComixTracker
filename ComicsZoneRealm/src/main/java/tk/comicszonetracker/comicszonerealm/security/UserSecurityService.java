package tk.comicszonetracker.comicszonerealm.security;

import com.sun.appserv.jdbc.DataSource;
import com.sun.enterprise.connectors.ConnectorRuntime;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import tk.comicszonetracker.comicszonerealm.security.password.SHA256SimpleSaltedEncryptor;

public class UserSecurityService implements IUserSecurityService {

    private final String JNDIName = "JNDI_ComicsZoneDB";

    @Override
    public void validatePassword(String nickname, String password) throws Exception {
        SHA256SimpleSaltedEncryptor encryptor = new SHA256SimpleSaltedEncryptor();
        password = encryptor.getEncodedPassword(password, nickname);
        
        final String query = String.format(
                "SELECT * FROM users WHERE nickname LIKE '%s' AND pass LIKE '%s';", nickname, password);
        if (!existRow(query))
            throw new Exception("User or password not valid!");
    }

    @Override
    public List<String> getGroups(String nickname){
        try {
            final String query = String.format(
                    "SELECT gname FROM user_group WHERE nickname LIKE '%s';", nickname);
            
            DataSource ds = (DataSource) ConnectorRuntime.getRuntime().lookupNonTxResource(JNDIName, false);
            Connection con = ds.getConnection();
            con.createStatement();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            List<String> result = new LinkedList<>();
            while (rs.next()){
                result.add(rs.getString(1));
            }
            
            rs.close();
            st.close();
            con.close();
            
            return result;
        } catch (NamingException ex) {
            Logger.getLogger(UserSecurityService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserSecurityService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new LinkedList<>();
    }
    
    private boolean existRow(String query) throws NamingException,SQLException{
        DataSource ds = (DataSource) ConnectorRuntime.getRuntime().lookupNonTxResource(JNDIName, false);
        Connection con = ds.getConnection();
        con.createStatement();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        boolean result = rs.next();
        rs.close();
        st.close();
        con.close();
        return result;
    }

}
