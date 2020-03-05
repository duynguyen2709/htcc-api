package util;

public class NumberUtil {

    public static long getLongValue(Object obj){
        try {
            String val = String.valueOf(obj);
            return Long.valueOf(val);
        } catch (Exception e){
            return 0L;
        }
    }

    public static int getIntValue(Object obj){
        return (int)getLongValue(obj);
    }
}
