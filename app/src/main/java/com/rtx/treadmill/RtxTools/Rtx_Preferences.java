package com.rtx.treadmill.RtxTools;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by jerry on 2017/6/16.
 */

public class Rtx_Preferences
{

    public static SharedPreferences settings;
    public static boolean DEBUG = true;
    public static String TAG = "Rtx_Proferences";

    public static final String PREF = "PREF";
    public static final String PREF_LAGUAGE = "LAGUAGE";
    public static final String PREF_SIIP = "SIIP"; //F05 unit (0:SI 1:IP)
    public static final String PREF_GYM_ID = "GYM_ID";
    public static final String PREF_MACHINE_ID = "MACHINE_ID";
    public static final String PREF_MACHINE_CITY = "MACHINE_CITY";
    public static final String PREF_WEATHER_INTERVAL = "WEATHER_INTERVAL";
    public static final String PREF_MACHINE_TYPE = "MACHINE_TYPE";

    public static final String PREF_SPEED = "SPEED";//default speed
    public static final String PREF_INCLINE = "INCLINE";//default incline
    public static final String PREF_LEVEL = "LEVEL";//default levle

    public static final String PREF_TREADMILL_LIMIT = "TREADMILL_LIMIT";//for treadmill F65
    public static final String PREF_BIKE_LIMIT = "BIKE_LIMIT";//for bike F65

    public static final String PREF_MACHINE_TYPE0_PARAMETER = "MACHINE_TYPE0_PARAMETER"; //for treadmill F18
    public static final String PREF_MACHINE_TYPE1_PARAMETER = "MACHINE_TYPE1_PARAMETER"; //for bike F18
    public static final String PREF_SPEED_ADD_PARAMETER = "SPEED_ADD_PARAMETER"; //for treadmill F62(0->60Hz)
    public static final String PREF_SPEED_SUB_PARAMETER = "SPEED_SUB_PARAMETER"; //for treadmill F62(60->0Hz)

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

        Rtx_Log.Loge(DEBUG,TAG,"SetPreferences skey=" + skey + "====sval=" + sval);
    }

    public static int i_Data_GetPreferences(Context mContext, String skey)
    {
        String sdata;

        if(skey == null)
        {
            return -1;
        }

        v_Data_getPref(mContext);
        if(settings == null )
        {
            return -1;
        }

        int iret;
        try {
            sdata = settings.getString(skey, null);
            if(sdata == null)
            {
                iret = -1;
            }
            else
            {
                iret = Integer.parseInt(sdata);
            }
        }
        catch (Exception e)
        {
            iret = -1;
        }

        return iret;
    }

    public static float f_Data_GetPreferences(Context mContext, String skey)
    {
        String sdata;

        if(skey == null)
        {
            return -1;
        }

        v_Data_getPref(mContext);
        if(settings == null )
        {
            return -1;
        }

        float fret;
        try {
            sdata = settings.getString(skey, null);
            if(sdata == null)
            {
                fret = -1;
            }
            else
            {
                fret = Float.parseFloat(sdata);
            }
        }
        catch (Exception e)
        {
            fret = -1;
        }

        return fret;
    }

    public static String s_Data_GetPreferences(Context mContext, String skey)
    {
        if(skey == null)
        {
            return null;
        }

        v_Data_getPref(mContext);

        if(settings == null )
        {
            return null;
        }

        return settings.getString(skey, null);
    }

}
