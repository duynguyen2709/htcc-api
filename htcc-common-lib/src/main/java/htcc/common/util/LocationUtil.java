package htcc.common.util;

public class LocationUtil {

    public static double calculateDistance(float lat1, float lon1, float lat2, float lon2) {
        double p = 0.017453292519943295;    // Math.PI / 180

        double a = 0.5 - Math.cos(((lat2 - lat1) * p)) / 2 +
                Math.cos(lat1 * p) * Math.cos(lat2 * p) * (1 - Math.cos((lon2 - lon1) * p)) / 2;
        return 12742 * Math.asin(Math.sqrt(a)) * 1000; // 2 * R; R = 6371 km * 1000 => m
    }
}
