package wall.bilibili.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatFaUtils {

    public static String stampToStr(long time)
    {
        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
        return format.format(new Date(time));

    }

}
