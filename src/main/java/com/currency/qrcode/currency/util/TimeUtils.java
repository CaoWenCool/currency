package com.currency.qrcode.currency.util;

import com.sun.org.apache.regexp.internal.RE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtils {

  public static String getMonthOfFirstDay(){
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date date = new Date();
      Calendar calendar = new GregorianCalendar();
      calendar.setTime(date);
      //获得本月第一天
      calendar.add(Calendar.MONTH, 0);
      calendar.set(Calendar.DAY_OF_MONTH, 1);
      String firstDay = sdf.format(calendar.getTime());
      return firstDay;
  }

    public static String getMonthOfLastOneDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //获得本月第一天
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String firstDay = sdf.format(calendar.getTime());
        return firstDay;
    }

    public static void main(String args[]){
      System.out.println(getMonthOfFirstDay());
      System.out.println(getMonthOfLastOneDay());
    }
}
