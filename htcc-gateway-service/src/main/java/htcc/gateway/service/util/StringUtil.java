package htcc.gateway.service.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Type;

@Log4j2
public class StringUtil {

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

    public static <T> T fromJsonStringDefaultNull(String sJson, Class<T> t) {
        try {
            return gson.fromJson(sJson, t);
        } catch (Exception var3) {
            log.error(String.format("[fromJsonStringDefaultNull] parse %s to %s ex \n{%s}", sJson, t.getName(), var3.getMessage()));
            return null;
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
