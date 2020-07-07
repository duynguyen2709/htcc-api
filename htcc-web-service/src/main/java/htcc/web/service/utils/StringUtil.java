package htcc.web.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.lang.reflect.Type;
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
}
