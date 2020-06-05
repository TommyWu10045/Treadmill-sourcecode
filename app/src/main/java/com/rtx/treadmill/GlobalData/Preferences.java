package com.rtx.treadmill.GlobalData;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by jerry on 2017/6/23.
 */

public class Preferences {
    private static String TAG = "Jerry=";
    private final static boolean DEBUG = false;

    public static SharedPreferences settings;
    public static final String PREF                             =       "PREF";
    public static final String PREF_LAGUAGE                     =       "LAGUAGE";
    public static final String PREF_SIIP                        =       "SIIP"; //F05 For Unit
    public static final String PREF_GYM_ID                      =       "GYM_ID";
    public static final String PREF_MACHINE_ID                  =       "MACHINE_ID";
    public static final String PREF_MACHINE_CITY                =       "MACHINE_CITY";
    public static final String PREF_WEATHER_INTERVAL            =       "WEATHER_INTERVAL";
    public static final String PREF_MACHINE_TYPE                =       "MACHINE_TYPE";

    public static final String PREF_SPEED                       =       "SPEED";//default speed
    public static final String PREF_INCLINE                     =       "INCLINE";//default incline
    public static final String PREF_LEVEL                       =       "LEVEL";//default levle

    public static final String PREF_TREADMILL_LIMIT             =       "TREADMILL_LIMIT";//for treadmill F65
    public static final String PREF_BIKE_LIMIT                  =       "BIKE_LIMIT";//for bike F65

    public static final String PREF_MACHINE_TYPE0_PARAMETER     =       "MACHINE_TYPE0_PARAMETER"; //for treadmill F18
    public static final String PREF_MACHINE_TYPE1_PARAMETER     =       "MACHINE_TYPE1_PARAMETER"; //for bike F18
    public static final String PREF_SPEED_ADD_PARAMETER         =       "SPEED_ADD_PARAMETER"; //for treadmill F62(0->60Hz)
    public static final String PREF_SPEED_SUB_PARAMETER         =       "SPEED_SUB_PARAMETER"; //for treadmill F62(60->0Hz)


    /////////////////////////////////////////////////////////////////////////////////////
    // Get preferences Head
    public static void v_Data_getPref (Context ctxt) {
        if(ctxt != null) {
            settings = ctxt.getSharedPreferences(PREF, 0);
        }
        return ;
    }

    public static void v_Data_SetPreferences(Context mContext, String skey , String sval)
    {
        if(skey == null || sval == null)
        {
            return ;
        }

        v_Data_getPref(mContext);

        if(settings == null )
        {
            return ;
        }

        settings.edit()
                .putString(skey, sval)
                .commit();

        if(DEBUG) Log.e(TAG, "SetPreferences skey=" + skey + "====sval=" + sval);
    }

    public static String s_GetPreferences(Context mContext, String skey)
    {
        String sdata = null;
        if(skey == null)
        {
            return sdata;
        }

        v_Data_getPref(mContext);

        if(settings == null )
        {
            return sdata;
        }

        sdata = settings.getString(skey, null);
        return sdata;
    }

    public static float f_GetPreferences(Context mContext, String skey)
    {
        String sdata;
        float fret = -1;

        sdata = s_GetPreferences(mContext, skey);
        if(sdata == null)
        {
            return fret;
        }

        try {
            fret = Float.parseFloat(sdata);
        }
        catch (Exception e)
        {
        }

        return fret;
    }

     /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

}
