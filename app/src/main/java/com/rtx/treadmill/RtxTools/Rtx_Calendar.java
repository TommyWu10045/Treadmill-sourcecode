package com.rtx.treadmill.RtxTools;

import android.util.Log;

import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.GlobalData;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by jerry on 2017/6/16.
 */

public class Rtx_Calendar {

    public static Calendar cStr2Calendar(String str, String sFormat)
    {
        Calendar cal = GlobalData.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat, Locale.ENGLISH);
        GlobalData.vSetTimezone(sdf);
        try {
            cal.setTime(sdf.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            cal = null;
        } catch (NullPointerException e) {
            e.printStackTrace();
            cal = null;
        }

        return cal;
    }

    public static String sTodayCalendar2Str(String sFormat)
    {
        Calendar cal = GlobalData.getInstance();
        String formatted = null;

        if(cal != null)
        {
            SimpleDateFormat format = new SimpleDateFormat(sFormat);
            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            GlobalData.vSetTimezone(format);

            formatted = format.format(cal.getTime());
        }

        return formatted;
    }

    public static int iGetDaysLeftOfWeek(Calendar cal)
    {
        int iDays = -1;
        int iDaysLeft = -1;

        if(cal != null)
        {
            iDays = cal.get(Calendar.DAY_OF_WEEK);

            iDaysLeft = 7 - iDays + 1;
        }

        return iDaysLeft;
    }

    public static String sCalendar2Str(Calendar cal, String sFormat)
    {
        String formatted = null;
        SimpleDateFormat ref;

        if(cal != null)
        {
            ref = new SimpleDateFormat(sFormat, Locale.ENGLISH);
            GlobalData.vSetTimezone(ref);
            DateFormatSymbols symbols = new DateFormatSymbols();
            symbols.setAmPmStrings(new String[] { "am", "pm" });
            ref.setDateFormatSymbols(symbols);
            //SimpleDateFormat format = new SimpleDateFormat(sFormat);
            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            formatted = ref.format(cal.getTime());
        }

        return formatted;
    }

    //sFortmat only support HH, mm and ss
    public static int cStr2Sec(String str, String sFormat)
    {
        int ival = 0;
        Calendar cal = GlobalData.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat, Locale.ENGLISH);
        GlobalData.vSetTimezone(sdf);
        try {
            cal.setTime(sdf.parse(str));
            ival = cal.get(Calendar.HOUR) * 3600 + cal.get(Calendar.MINUTE) * 60 + cal.get(Calendar.SECOND);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return ival;
    }

    public static String sParseDate2FormatStr(String sDate, String sSourceFormat, String sTargetFormat)
    {
        String sTargetTime = null;
        Calendar cal = cStr2Calendar(sDate,sSourceFormat);

        if(cal != null)
        {
            sTargetTime = sCalendar2Str(cal,sTargetFormat);
        }

        return sTargetTime;
    }

    //Get 當月份天數
    public static int getDaysOfMonth(int iyear, int imonth)
    {
        Calendar cd = GlobalData.getInstance() ;

        cd.set(iyear,imonth-1,1);

        return cd.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static long getDiffSec(Calendar befor, Calendar after)
    {
        long lSec = 0;
        long difference = after.getTimeInMillis() - befor.getTimeInMillis();

        lSec = difference / 1000;

        return lSec;
    }

    public static int getDiffDays(Calendar befor, Calendar after)
    {
        if(befor == null)
        {
            return 0;
        }

        if(after == null)
        {
            return 0;
        }

        int day1= befor.get(Calendar.DAY_OF_YEAR);
        int day2 = after.get(Calendar.DAY_OF_YEAR);

        int year1 = befor.get(Calendar.YEAR);
        int year2 = after.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            return day2-day1;
        }
    }

    public static boolean isDayInRange(Calendar cal1, int iAfterDays) // iAfterDays = 0 is today
    {
        boolean bResult = false;
        Calendar cal2 = GlobalData.getInstance();
        int iIndex = 0;

        for( ; iIndex <= iAfterDays ; iIndex++)
        {
            cal2.add(Calendar.DATE,iIndex);
            bResult = isSameDate(cal1, cal2);

            if(bResult)
            {
                break;
            }
        }

        return bResult;
    }

    public static boolean isSameDate(Calendar cal1,Calendar cal2)
    {
        if(cal1 == null) { cal1 = GlobalData.getInstance(); }
        if(cal2 == null) { cal2 = GlobalData.getInstance(); }

        if(cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR))
        {
            return false;
        }

        if(cal1.get(Calendar.DAY_OF_YEAR) != cal2.get(Calendar.DAY_OF_YEAR))
        {
            return false;
        }

        return true;
    }

    public static boolean isSameWeek(Calendar cal1,Calendar cal2)
    {
        if(cal1 == null) { cal1 = GlobalData.getInstance(); }
        if(cal2 == null) { cal2 = GlobalData.getInstance(); }

        if(cal1.get(Calendar.WEEK_OF_YEAR) != cal2.get(Calendar.WEEK_OF_YEAR))
        {
            return false;
        }

        return true;
    }

    public static String sMin2Str(int iMin, String sFormat)
    {
        String str;
        Calendar cal = cMin2Calendar(iMin);

        if(sFormat == null)
        {
            if(iMin >= 60)
            {
                str = sCalendar2Str(cal,"H:mm:ss");
            }
            else
            {
                str = sCalendar2Str(cal,"m:ss");
            }
        }
        else
        {
            str = sCalendar2Str(cal,sFormat);
        }


        return str;
    }

    public static Calendar cMin2Calendar(int iMin)
    {
        Calendar    cal = null;

        cal = cStr2Calendar("00:00:00","HH:mm:ss");
        cal.add(Calendar.MINUTE,(int)iMin);

        return cal;
    }

    public static String sSec2Str(int iSec)
    {
        String str;
        Calendar cal = cSec2Calendar(iSec);

        if(iSec >= 3600)
        {
            str = sCalendar2Str(cal,"H:mm:ss");
        }
        else
        {
            str = sCalendar2Str(cal,"m:ss");
        }

        return str;
    }

    public static String sSec2Str(int iSec, String sFormat)
    {
        String str;
        Calendar cal = cSec2Calendar(iSec);

        if(sFormat == null)
        {
            if(iSec >= 3600)
            {
                str = sCalendar2Str(cal,"H:mm:ss");
            }
            else
            {
                str = sCalendar2Str(cal,"m:ss");
            }
        }
        else
        {
            str = sCalendar2Str(cal,sFormat);
        }

        return str;
    }

    public static Calendar cSec2Calendar(int iSec)
    {
        Calendar    cal = null;

        cal = cStr2Calendar("00:00:00","HH:mm:ss");
        cal.add(Calendar.SECOND,(int)iSec);

        return cal;
    }
    //itimezone :
    // 0 : don't care it. ;
    // 1 : sosdf = sisdf + timeoffset ;
    // 2 : sosdf = sisdf - timeoffset=> GMT+0
    //sosdf : return format
    //sisdf : input format, current time if null
    //s : input datetime, current time if null
    //iday : add days
    //isec : add second
    //s or sisdf is null get current time.
    public static String s_trans_DateTime_Str(int itimezone, String sosdf , String sisdf, String s, int iday, int isec) {
        SimpleDateFormat sdf;
        SimpleDateFormat ref;
        Calendar cd = GlobalData.getInstance();
//        int itimeoffset = cd.getTimeZone().getRawOffset() / 1000;
        int itimeoffset = Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(1)) * 10 * 60 * 60
                + Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(2)) * 60 * 60
                + Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(3)) * 10 * 60
                + Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(4)) * 60;
        String sret = null;

        if(sosdf != null) {
            ref = new SimpleDateFormat(sosdf, Locale.ENGLISH);
            GlobalData.vSetTimezone(ref);
            if (s != null && sisdf != null) {
                sdf = new SimpleDateFormat(sisdf);
                GlobalData.vSetTimezone(sdf);
                DateFormatSymbols symbols = new DateFormatSymbols();
                symbols.setAmPmStrings(new String[]{"am", "pm"});
                ref.setDateFormatSymbols(symbols);
                try {
                    cd.setTime(sdf.parse(s));
                } catch (Exception e) {
                    return sret;
                }
            }

            try {
                cd.add(Calendar.DATE, iday);
                cd.add(Calendar.SECOND, isec);
                if (itimezone == 1) {
                    if(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(0).equals("+"))
                    {
                        cd.add(Calendar.SECOND, itimeoffset);
                    }
                    else
                    {
                        cd.add(Calendar.SECOND, -itimeoffset);
                    }
                } else if (itimezone == 2)
                {
//                    cd.add(Calendar.SECOND, -itimeoffset);
                    ref.setTimeZone(TimeZone.getTimeZone("GMT"+"+00:00"));
                }
                sret = ref.format(cd.getTime());
            } catch (Exception e) {
                return sret;
            }
        }

        return sret;
    }

    //String to Long
    public static Long l_trans_DateTime_Str(String sisdf, String s) {
        SimpleDateFormat sdf;
        Calendar cd = GlobalData.getInstance();
        Long lret = new Long(0);

        if(sisdf != null) {
            sdf = new SimpleDateFormat(sisdf);
            GlobalData.vSetTimezone(sdf);
            if (s != null) {
                try {
                    cd.setTime(sdf.parse(s));
                    lret = cd.getTimeInMillis();
                    return lret;
                } catch (Exception e) {
                    return lret;
                }
            }
        }

        return lret;
    }

    //itimezone : 0 : don't care it. ; 1 : sosdf = sisdf + timeoffset ; 2 : sosdf = sisdf - timeoffset
    //s or sisdf is null get current time.
    public static int i_get_datetime(int itimezone, int imode, String sisdf, String s)
    {
        SimpleDateFormat sdf;
        Calendar cd = GlobalData.getInstance() ;
//        int itimeoffset = cd.getTimeZone().getRawOffset() / 1000;
        int itimeoffset = Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(1)) * 10 * 60 * 60
                + Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(2)) * 60 * 60
                + Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(3)) * 10 * 60
                + Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(4)) * 60;
        int idata = 0;

        if (sisdf != null && s != null) {
            sdf = new SimpleDateFormat(sisdf);
            GlobalData.vSetTimezone(sdf);
            try {
                cd.setTime(sdf.parse(s));
                if(itimezone == 1)
                {
                    if(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(0).equals("+"))
                    {
                        cd.add(Calendar.SECOND, itimeoffset);
                    }
                    else
                    {
                        cd.add(Calendar.SECOND, -itimeoffset);
                    }
                }
                else if(itimezone == 2)
                {
//                    cd.add(Calendar.SECOND , -itimeoffset);
                }
            } catch (Exception e) {
                return 0;
            }
        }
        else
        {
            if(itimezone == 1)
            {
                cd.add(Calendar.SECOND , itimeoffset);
            }
            else if(itimezone == 2)
            {
                cd.add(Calendar.SECOND , -itimeoffset);
            }
        }

        switch(imode)
        {
            case 0:
                idata = cd.get(Calendar.YEAR);
                break;
            case 1:
                idata = cd.get(Calendar.MONTH) + 1;
                break;
            case 2:
                idata = cd.get(Calendar.DATE);
                break;
            case 3:
                idata = cd.get(Calendar.HOUR_OF_DAY);
                break;
            case 4:
                idata = cd.get(Calendar.MINUTE);
                break;
            case 5:
                idata = cd.get(Calendar.SECOND);
                break;
            default:
                break;
        }

        return idata;
    }

    //imode => 0 : hh:mm:ss ; 1 : *m:ss ; 2 : *h:mm:ss ; 3 : mm:ss ; 4 : **:mm:ss ; 5 : **:*m:ss
    public static String s_trans_integer(int imode, int ival )
    {
        String sdata = "";
        String shour = "";
        int ihour = 0;
        String smin = "";
        int imin = 0;
        String ssec = "";
        int isec = 0;
        int imax_val = 99;

        if(imode == 0) {
            if (ival <= (3600 * imax_val))
            {
                ihour = ival / 3600;
                imin = (ival / 60) % 60;
                isec = ival % 60;
            }
            else
            {
                ihour = imax_val;
                imin = imax_val;
                isec = imax_val;
            }
            shour = String.format("%02d:", ihour);
            smin = String.format("%02d:", imin);
            ssec = String.format("%02d", isec);
        }
        else if(imode == 1) {
            if (ival <= (60 * imax_val))
            {
                imin = ival / 60;
                isec = ival % 60;
            }
            else
            {
                imin = imax_val;
                isec = imax_val;
            }
            smin = String.format("%d:", imin);
            ssec = String.format("%02d", isec);
        }
        else if(imode == 2) {
            if (ival <= (3600 * imax_val))
            {
                ihour = ival / 3600;
                imin = (ival / 60) % 60;
                isec = ival % 60;
            }
            else
            {
                ihour = imax_val;
                imin = imax_val;
                isec = imax_val;
            }
            shour = String.format("%d:", ihour);
            smin = String.format("%02d:", imin);
            ssec = String.format("%02d", isec);
        }
        else if(imode == 3) {
            if (ival <= (60 * imax_val))
            {
                imin = ival / 60;
                isec = ival % 60;
            }
            else
            {
                imin = imax_val;
                isec = imax_val;
            }
            smin = String.format("%02d:", imin);
            ssec = String.format("%02d", isec);
        }
        else if(imode == 4) {
            if (ival <= (3600 * imax_val))
            {
                ihour = ival / 3600;
                imin = (ival / 60) % 60;
                isec = ival % 60;
                if (ihour > 0) {
                    shour = String.format("%d:", ihour);
                    smin = String.format("%02d:", imin);
                    ssec = String.format("%02d", isec);
                } else {
                    smin = String.format("%02d:", imin);
                    ssec = String.format("%02d", isec);
                }
            }
            else
            {
                ihour = imax_val;
                imin = imax_val;
                isec = imax_val;
                shour = String.format("%d:", ihour);
                smin = String.format("%02d:", imin);
                ssec = String.format("%02d", isec);
            }

        }
        else if(imode == 5) {
            if (ival <= (3600 * imax_val))
            {
                ihour = ival / 3600;
                imin = (ival / 60) % 60;
                isec = ival % 60;
                if (ihour > 0) {
                    shour = String.format("%d:", ihour);
                    smin = String.format("%02d:", imin);
                    ssec = String.format("%02d", isec);
                } else {
                    smin = String.format("%d:", imin);
                    ssec = String.format("%02d", isec);
                }
            }
             else
            {
                ihour = imax_val;
                imin = imax_val;
                isec = imax_val;
                shour = String.format("%d:", ihour);
                smin = String.format("%02d:", imin);
                ssec = String.format("%02d", isec);
            }
        }

        sdata = shour + smin + ssec;

        return sdata;
    }
}
