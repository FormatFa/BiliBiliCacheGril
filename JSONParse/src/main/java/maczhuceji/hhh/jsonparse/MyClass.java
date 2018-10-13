package maczhuceji.hhh.jsonparse;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyClass {
    public static void main(String[] args)
    {
        long due = 1562169600000L;

        Date date = new Date();
        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
        System.out.print(format.format(new Date(due)));

    }
}
