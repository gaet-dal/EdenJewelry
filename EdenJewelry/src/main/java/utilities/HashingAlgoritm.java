package main.java.utilities;

import java.nio.charset.StandardCharsets;

public class HashingAlgoritm {

    //metodo per effettuare l'hash delle password;
    private String toHash(String password) {
        String hashString=null;

        try {
            java.security.MessageDigest digest=java.security.MessageDigest.getInstance("SHA-512");
            byte [] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            hashString=""; for(int i=0; i<hash.length; i++) {
                hashString+=Integer.toHexString(hash[i] & 0xFF | 0x100).substring(1,3);
            }
        }
        catch(java.security.NoSuchAlgorithmException e) {
        }
        return hashString;
    }
}
