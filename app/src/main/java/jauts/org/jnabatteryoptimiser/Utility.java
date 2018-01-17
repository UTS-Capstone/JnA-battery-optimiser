package jauts.org.jnabatteryoptimiser;

/**
 * Created by liangze on 15/1/18.
 */

public class Utility {
    public static String shortify (String s) {
        if (s == null || s.length() == 0) {
            return "ERROR";
        }
        String str = null;
        if (s.length() > 32) {
            str = s.substring(0, 32);
        } else {
            str = s;
        }
        return str;
    }
}
