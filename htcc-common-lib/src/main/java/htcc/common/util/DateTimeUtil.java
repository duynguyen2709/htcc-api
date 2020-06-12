package htcc.common.util;

import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Log4j2
public class DateTimeUtil {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String HHMM_FORMAT = "HH:mm";

    public static String convertToOtherFormat(String raw, String sourceFormat, String targetFormat){
        try {
            Date d = parseStringToDate(raw, sourceFormat);
            String newDate = parseDateToString(d, targetFormat);
            return newDate;
        } catch (Exception e){
            log.warn("convertToOtherFormat {}, source format {}, target format {} , ex {}",
                    raw, sourceFormat, targetFormat, e.getMessage());
            return "";
        }
    }

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

    public static int getWeekDayInt(String yyyyMMdd){
        Date d = parseStringToDate(yyyyMMdd, "yyyyMMdd");
        Calendar c = Calendar.getInstance(new Locale("vi","VN"));
        c.setTime(d);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static int getWeekNum(String yyyyMMdd) {
        Date d = parseStringToDate(yyyyMMdd, "yyyyMMdd");
        Calendar c = Calendar.getInstance(new Locale("vi","VN"));
        c.setTime(d);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public static String parseDateToString(Date dt, String format) {
        try {
            return new SimpleDateFormat(format).format(dt);
        } catch (Exception e){
            log.warn("parseDateToString {}, format {}, ex {}", dt, format, e.getMessage());
            return "";
        }
    }

    public static String subtractMonthFromDate(Date dt, int month){
        Calendar c = Calendar.getInstance(new Locale("vi","VN"));
        c.setTime(dt);
        c.add(Calendar.MONTH, month * (-1));
        return parseDateToString(c.getTime(), "yyyyMM");
    }

    public static String parseDateToString(Date dt) {
        return parseDateToString(dt, DATE_FORMAT);
    }

    public static Date parseStringToDate(String str, String format) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (Exception e){
            log.warn("parseStringToDate {} - {}", str, format);
            return null;
        }
    }

    public static String getDateStringFromWeek(int plusDay, int week, int year, String format) {
        LocalDate desiredDate = LocalDate.ofYearDay(year, 1)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .plusDays(plusDay);

        String formattedDate = desiredDate.format(DateTimeFormatter.ofPattern(format));
        return formattedDate;
    }

    public static String getDateStringFromWeek(int week, String format) {
        LocalDate desiredDate = LocalDate.now()
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        String formattedDate = desiredDate.format(DateTimeFormatter.ofPattern(format));
        return formattedDate;
    }

    public static int calcMonthDiff(String from, String to, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        Period diff = Period.between(
                LocalDate.parse(from, formatter).withDayOfMonth(1),
                LocalDate.parse(to, formatter).withDayOfMonth(1));

        return diff.getMonths();
    }

    public static int calcDayDiff(String from, String to, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        Period diff = Period.between(
                LocalDate.parse(from, formatter),
                LocalDate.parse(to, formatter));

        return Math.abs(diff.getDays());
    }

    public static Date parseStringToDate(String str){
        return parseStringToDate(str, DATE_FORMAT);
    }

    public static long getSecondUntilEndOfDay() {
        LocalDate today = LocalDate.now();

        LocalDateTime zdtStart = LocalDateTime.now();
        LocalDateTime zdtStop  = today.atTime(23, 59, 59, 999);

        Instant start = zdtStart.toInstant(ZoneOffset.MIN);
        Instant stop  = zdtStop.toInstant(ZoneOffset.MIN);

        Duration timeElapsed = Duration.between(start, stop);
        return timeElapsed.toMillis() / 1000;
    }

    public static boolean isBefore(long time, String validTime) {
        try {
            String HHmm = parseTimestampToString(time, HHMM_FORMAT);
            Date   dt   = parseStringToDate(HHmm, HHMM_FORMAT);
            Date   dt2  = parseStringToDate(validTime, HHMM_FORMAT);
            return dt.before(dt2);
        } catch (Exception e){
            log.warn("isBefore time: {} - validTime {} ex", time, validTime, e);
            return false;
        }
    }

    public static boolean isAfter(long time, String validTime) {
        try {
            String HHmm = parseTimestampToString(time, HHMM_FORMAT);
            Date   dt   = parseStringToDate(HHmm, HHMM_FORMAT);
            Date   dt2  = parseStringToDate(validTime, HHMM_FORMAT);
            return dt.after(dt2);
        } catch (Exception e){
            log.warn("isAfter time: {} - validTime {} ex {}", time, validTime, e.getMessage());
            return false;
        }
    }

    public static boolean isRightFormat(String str, String format) {
        try {
            new SimpleDateFormat(format).parse(str);
            return true;
        } catch (Exception e){
            return false;
        }
    }


    public static boolean isBeforeToday(String date) {
        try {
            String today = parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");
            Long todayNum = Long.parseLong(today);
            Long dateNum = Long.parseLong(date);
            return dateNum < todayNum;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isBetweenTwoTime(String argStartTime, String argEndTime, String argCurrentTime) {
        if (argCurrentTime.equals(argStartTime) || argCurrentTime.equals(argEndTime)) {
            return false;
        }

        boolean valid = false;
        try {
            // Start Time
            java.util.Date startTime = new SimpleDateFormat("HH:mm").parse(argStartTime);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);

            // Current Time
            java.util.Date currentTime = new SimpleDateFormat("HH:mm").parse(argCurrentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);

            // End Time
            java.util.Date endTime = new SimpleDateFormat("HH:mm").parse(argEndTime);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTime);

            if (currentTime.compareTo(endTime) < 0) {
                currentCalendar.add(Calendar.DATE, 1);
                currentTime = currentCalendar.getTime();
            }

            if (startTime.compareTo(endTime) < 0) {
                startCalendar.add(Calendar.DATE, 1);
                startTime = startCalendar.getTime();
            }

            if (currentTime.before(startTime)) {
                valid = false;
            }
            else {
                if (currentTime.after(endTime)) {
                    endCalendar.add(Calendar.DATE, 1);
                    endTime = endCalendar.getTime();
                }

                if (currentTime.before(endTime)) {
                    valid = true;
                }
            }
        } catch (Exception e) {
            log.error("[isBetweenTwoTime] ex", e);
        }
        return valid;
    }

    public static boolean isConflictTime(String start1, String end1, String start2, String end2) {
        if (start1.equals(start2) || end1.equals(end2)) {
            return true;
        }

        return (isBetweenTwoTime(start1, end1, start2) ||
                isBetweenTwoTime(start1, end1, end2) ||
                isBetweenTwoTime(start2, end2, start1) ||
                isBetweenTwoTime(start2, end2, end1)
        );
    }

    public static boolean isBeforeMidDay(String startTime) {
        return isBefore(parseStringToDate(startTime, "HH:mm").getTime(), "12:00");
    }
}
