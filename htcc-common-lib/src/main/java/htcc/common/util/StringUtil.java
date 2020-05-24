package htcc.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.InetAddresses;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import htcc.common.constant.Constant;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Log4j2
public class StringUtil {

    public static final String EMPTY = "";

    public static final Type LIST_STRING_TYPE = new TypeToken<List<String>>() {}.getType();

    private static final Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Double.class, new DoubleSerializer());
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        gson = gsonBuilder.disableHtmlEscaping().setLenient().create();
    }

    public static String toJsonString(Object obj) {
        if (obj == null) {
            return EMPTY;
        }
        return gson.toJson(obj);
    }

    public static <T> T fromJsonString(String sJson, Class<T> t) {
        return gson.fromJson(sJson, t);
    }

    public static <T> T fromMapToObject(Map map, Class<T> t) {
        JsonElement jsonElement = gson.toJsonTree(map);
        return gson.fromJson(jsonElement, t);
    }

    public static boolean isJsonString(String str) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(str);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static <T> T json2Collection(String sJson, Type t) {
        return gson.fromJson(sJson, t);
    }

    public static String valueOf(Object obj) {
        if (obj == null)
            return "";

        return String.valueOf(obj);
    }

    public static boolean isEmpty(Object obj) {
        return valueOf(obj).isEmpty();
    }

    private static class DoubleSerializer implements JsonSerializer<Double> {
        @Override
        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
            return src == src.longValue() ? new JsonPrimitive(src.longValue()) : new JsonPrimitive(src);
        }
    }

    public static boolean isIPAddress(String str) {
        return InetAddresses.isInetAddress(str);
    }

    public static boolean isEmail(String email ) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(valueOf(email));
        return m.matches();
    }

    public static String getFileIdFromImage(String imageUrl) {
        String fileId = "";
        try {
            if (!imageUrl.startsWith(Constant.GOOGLE_DRIVE_IMAGE_FORMAT)) {
                throw new Exception("Image not start with GOOGLE_DRIVE_IMAGE_FORMAT");
            }
            return imageUrl.substring(Constant.GOOGLE_DRIVE_IMAGE_FORMAT.length());

        } catch (Exception e) {
            log.warn(String.format("[getFileIdFromImage] Url [%s] ex %s", imageUrl, e.getMessage()));
        }
        return fileId;
    }

    public static String genEmployeeId(String companyId, int id){
        if (id < 10) {
            return String.format("%s-0000%s", companyId, id);
        } else if (id < 100) {
            return String.format("%s-000%s", companyId, id);
        } else if (id < 1000) {
            return String.format("%s-00%s", companyId, id);
        } else if (id < 10000) {
            return String.format("%s-0%s", companyId, id);
        } else {
            return String.format("%s-%s", companyId, id);
        }
    }

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
