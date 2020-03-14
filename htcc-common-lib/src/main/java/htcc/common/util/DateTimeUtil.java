package htcc.common.util;

import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
public class DateTimeUtil {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String parseTimestampToDateString(long timestamp){
        return parseTimestampToString(timestamp, DATETIME_FORMAT);
    }

    public static String parseTimestampToString(long timestamp, String format){
        try {
            String tempFormat = StringUtil.valueOf(format).isEmpty() ? DATETIME_FORMAT : format;
            return new SimpleDateFormat(tempFormat).format(new Timestamp(timestamp));
        } catch (Exception e){
            log.warn("parseTimestampToString {} format {}", timestamp, format);
            return "";
        }
    }

    public static String parseDateToString(Date dt) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).format(dt);
        } catch (Exception e){
            log.warn("parseDateToString {}", dt);
            return "";
        }
    }

    public static Date parseStringToDate(String str){
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(str);
        } catch (Exception e){
            log.warn("parseStringToDate {}", str);
            return null;
        }
    }

}
