package sum.ike.control.utils;

import java.util.Locale;

public class StringTrimmer {

    public static String trim (String str) {
        StringBuilder sb = new StringBuilder();
        String[] strings = str.split(" ");
        for (int i = 0; i < strings.length; i++) {
            if(!(strings[i].isEmpty())) {
                sb.append(strings[i].substring(0, 1).toUpperCase(Locale.ROOT)).append(strings[i].substring(1).toLowerCase(Locale.ROOT));
                if (!(i == strings.length-1)) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
}
