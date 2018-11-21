package com.example.zhw.piontandpiont2.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeParese {
    public static  String getTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy K:m:s a",Locale.ENGLISH);
        Date d2 = null;
        try {
            d2 = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d2 == null){
            return "";
        }
        return sdf.format(d2).substring(12,18);
    }

}
