/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namvn.shopping.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Welcome
 */
public class DateIncrease {
    
    
    
    public String[] caculateDeathTime(String inputdate, int dayscope){
        // date viet duoi dang: 12/07/1995
        // consumeWater: luong nuoc tieu thu trong 1 ngay
        // currentWater: lượng nước còn hiện tại
        String datepharses[] = inputdate.split("/");
        Date date = new Date(Integer.valueOf(datepharses[2]),Integer.valueOf(datepharses[1]),Integer.valueOf(datepharses[0]));
        Calendar c;
        String resultdate[] = new String[dayscope];
        for (int i = 0; i < dayscope; i++) {
            c = Calendar.getInstance();
            c.setTime(date); 
            c.add(Calendar.DATE, (-1));
            date = c.getTime();
            resultdate[i] =  date.getDate()+"/"+date.getMonth()+"/"+date.getYear();
        }
        return resultdate;
    }
//    public static void main(String[] args) throws ParseException {
//        DateIncrease dateIncrease = new DateIncrease();
//        String date[] = dateIncrease.caculateDeathTime("12/08/2018", 15);
//        System.out.println("Sẽ trừ đi: 15 ngày");
//        System.out.println("Ngày bắt đầu: 12/08/2018");
//        for (int i = 0; i < date.length-1; i++) {
//            System.out.println("Ngày thứ: "+(i +1)+" là ngày: "+date[i]);
//        }
//    }
}
