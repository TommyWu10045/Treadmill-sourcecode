package com.rtx.treadmill.RtxTools;

import android.util.Log;

/**
 * Created by jerry on 2017/6/16.
 */

public class Rtx_Log
{
    public static final Boolean ERROR_DEBUG = true;
    public static final String  ERROR_TAG = "RTX_ERROR";

    public static final Boolean DEBUG = true;
    public static final String  RTX_TAG = "RTX";


    public static void Loge(String sTag , String sLog)
    {
        if(DEBUG)
        {
            Log.e(sTag, sLog);
        }
    }

    public static void Loge(String sLog)
    {
        if(DEBUG)
        {
            Log.e(RTX_TAG, sLog);
        }
    }

    public static void Log_Error(String sLog)
    {
        if(ERROR_DEBUG)
        {
            Log.e(ERROR_TAG,sLog);
        }
    }

    public static void Loge(Boolean bDebug , String sTag , String sLog)
    {
        if(DEBUG)
        {
            if(bDebug) {
                Log.e(sTag, sLog);
            }
        }
    }

    public static String __FLIE__()
    {
        StackTraceElement traceElement = ((new Exception()).getStackTrace()[1]) ;
        return traceElement.getFileName();
//        return Thread.currentThread().getStackTrace()[2].getFileName();
    }

    public static int __LINE__()
    {
        StackTraceElement traceElement = ((new Exception()).getStackTrace()[1]) ;
        return traceElement.getLineNumber();
//        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }
}
