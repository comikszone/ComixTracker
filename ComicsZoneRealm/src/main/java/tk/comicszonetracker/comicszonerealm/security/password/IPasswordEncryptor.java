package tk.comicszonetracker.comicszonerealm.security.password;

public interface IPasswordEncryptor {
    String getEncodedPassword(String password);
}
