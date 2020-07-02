package htcc.web.service.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    public static String hashSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest    = MessageDigest.getInstance("SHA-256");
        byte[]        shaByteArr = mDigest.digest(input.getBytes(Charset.forName("UTF-8")));
        StringBuilder hexString  = new StringBuilder();

        for (byte b : shaByteArr) {
            String hex = Integer.toHexString(255 & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
