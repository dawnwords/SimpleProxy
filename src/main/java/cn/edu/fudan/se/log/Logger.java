package cn.edu.fudan.se.log;

import java.util.Date;

/**
 * Created by Dawnwords on 2015/6/24.
 */
public class Logger {
    private String tag;

    public Logger(String tag) {
        this.tag = tag;
    }

    public void info(String msg) {
        Date date = new Date();
        System.out.printf("%tY-%tm-%td %tk:%tM:%tS[%s]%s\n", date, date, date, date, date, date, tag, msg);
    }
}
