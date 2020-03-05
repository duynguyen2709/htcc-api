package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Type;

public class StringUtil {

    public static final String EMPTY = "";

    private static final Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        gson = gsonBuilder.disableHtmlEscaping().create();
    }

    public static String toJsonString(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJsonString(String sJson, Class<T> t) {
        return gson.fromJson(sJson, t);
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

}
