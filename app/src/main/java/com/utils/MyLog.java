package com.utils;

import android.util.Log;

/**
 * ＬＯＧ ＤＥＢＵＧ用
 * @author forever
 *
 */
public class MyLog
{
    public static boolean isDeubg = true;
    private static String tag = "treadmill";

    public static void v(String msg)
    {
        if(isDeubg)
            Log.v(tag, msg);
    }

    public static void e(String msg)
    {
        if(isDeubg)
            Log.e(tag, msg);
    }

    public static void d(String msg)
    {
        if(isDeubg)
            Log.d(tag, msg);
    }

    public static void i(String msg)
    {
        if(isDeubg)
            Log.i(tag, msg);
    }

}
