package az.iktlab.learnlink.util;

import java.util.Date;

public class DateHelper {
    public static Date now(){
        return new Date();
    }
    public static Date now(Long millis) {
        return new Date(millis);
    }
}
