package com.comicszone.dao.util.encryption;

public class SHA256SimpleSaltedEncryptor {

    public String getEncodedPassword(String password, String nickname) {
        if (nickname.length() < 1){
            throw new IllegalArgumentException("Nickname length must be greater then 2!");
        }
        
        IPasswordEncryptor pe = new SHA256Encryptor();
        
        return pe.getEncodedPassword(getSaltPassword(password, nickname));
    }
    
    private String getSaltPassword(String password, String nickname){
        StringBuilder builder = new StringBuilder();
        builder.append(nickname.charAt(0)).
                append("_%1512").
                append(nickname.charAt(nickname.length() - 1)).
                append(password).
                append(nickname.charAt(nickname.length() - 1)).
                append("&&&_**25123").
                append(nickname.charAt(0));
        return builder.toString();
    }
    
}
