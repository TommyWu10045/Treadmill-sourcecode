package com.rtx.treadmill.GlobalData;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

/**
 * Created by jerry on 2017/6/23.
 */

public class Perf {
    private static String TAG = "Jerry=";
    private final static boolean DEBUG = false;

    public static SharedPreferences settings;
    public static final String PREF = "PREF";
    public static final String PREF_LAGUAGE = "LAGUAGE";
    public static final String PREF_SIIP = "SIIP"; //F05 unit (0:SI 1:IP)

    public static final String PREF_MODEL = "MODEL";
    public static final String PREF_DEALER_ID = "DEALER_ID";
    public static final String PREF_GYM_ID = "GYM_ID";
    public static final String PREF_MACHINE_ID = "MACHINE_ID";
    public static final String PREF_TIMEZONE_OFFSET_0 = "TIMEZONE_OFFSET_0";
    public static final String PREF_TIMEZONE_OFFSET_1 = "TIMEZONE_OFFSET_1";
    public static final String PREF_TIMEZONE_OFFSET_2 = "TIMEZONE_OFFSET_2";
    public static final String PREF_TIMEZONE_OFFSET_3 = "TIMEZONE_OFFSET_3";
    public static final String PREF_TIMEZONE_OFFSET_4 = "TIMEZONE_OFFSET_4";
    public static final String PREF_COUNTRY = "COUNTRY";
    public static final String PREF_MACHINE_CITY = "MACHINE_CITY";
    public static final String PREF_WEATHER_INTERVAL = "WEATHER_INTERVAL";

    public static final String PREF_REGISTER_REGISTER = "REGISTER_REGISTER";
    public static final String PREF_REGISTER_CONNECT = "REGISTER_CONNECT";

    public static final String PREF_MACHINE_TYPE = "MACHINE_TYPE";

    public static final String PREF_SPEED = "SPEED";//default speed
    public static final String PREF_SPEED_MIN = "SPEED_MIN";//min speed
    public static final String PREF_SPEED_MAX = "SPEED_MAX";//max speed
    public static final String PREF_INCLINE = "INCLINE";//default incline
    public static final String PREF_INCLINE_MIN = "INCLINE_MIN";//min incline
    public static final String PREF_INCLINE_MAX = "INCLINE_MAX";//max incline
    public static final String PREF_TREADMILL_IDLE_DETECT = "TREADMILL_IDLE_DETECT";
    public static final String PREF_TREADMILL_HZ_PARAMETER = "TREADMILL_HZ_PARAMETER";
    public static final String PREF_TREADMILL_1KM_PARAMETER = "TREADMILL_1KM_PARAMETER";
    public static final String PREF_TREADMILL_PARAMETER1 = "TREADMILL_PARAMETER1";
    public static final String PREF_TREADMILL_ZERO_PARAMETER = "TREADMILL_ZERO_PARAMETER";
    public static final String PREF_SPEED_ADD_PARAMETER = "SPEED_ADD_PARAMETER";
    public static final String PREF_SPEED_SUB_PARAMETER = "SPEED_SUB_PARAMETER";

    public static final String PREF_LEVEL = "LEVEL";//default levle
    public static final String PREF_LEVEL_MIN = "LEVEL_MIN";//min levle
    public static final String PREF_LEVEL_MAX = "LEVEL_MAX";//max levle
    public static final String PREF_BIKE_INCREMENT_DUTY = "BIKE_INCREMENT_DUTY";
    public static final String PREF_BIKE_TOQUE = "BIKE_TOQUE";//ARRAY,seperator is ,
    public static final String PREF_BIKE_RN = "BIKE_RN";//ARRAY,seperator is ,
    public static final String PREF_BIKE_TOQUE_UBIKE = "BIKE_TOQUE_UBIKE";//ARRAY,seperator is ,
    public static final String PREF_BIKE_RN_UBIKE = "BIKE_RN_UBIKE";//ARRAY,seperator is ,
    public static final String PREF_BIKE_TOQUE_RBIKE = "BIKE_TOQUE_RBIKE";//ARRAY,seperator is ,
    public static final String PREF_BIKE_RN_RBIKE = "BIKE_RN_RBIKE";//ARRAY,seperator is ,
    public static final String PREF_BIKE_TOQUE_ELLIPTICAL = "BIKE_TOQUE_ELLIPTICAL";//ARRAY,seperator is ,
    public static final String PREF_BIKE_RN_ELLIPTICAL = "BIKE_RN_ELLIPTICAL";//ARRAY,seperator is ,

    public static final String PREF_MACHINE_TIME = "MACHINE_TIME";//Device Use time(Hr)
    public static final String PREF_MACHINE_DISTANCE = "MACHINE_DISTANCE";//Device Use Distance(km)

    public static final String PREF_SCREEN_ROTATE = "SCREEN_ROTATE";

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

        if(DEBUG) Log.e(TAG, "GetPreferences skey=" + skey + "====iret=" + iret);

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

        if(DEBUG) Log.e(TAG, "GetPreferences skey=" + skey + "====fret=" + fret);

        return fret;
    }

    public static String s_Data_GetPreferences(Context mContext, String skey)
    {
        String sdata = null;

        if(skey == null)
        {
            return null;
        }

        v_Data_getPref(mContext);

        if(settings == null )
        {
            return null;
        }

        sdata = settings.getString(skey, null);

        if(DEBUG) Log.e(TAG, "GetPreferences skey=" + skey + "====sdata=" + sdata);

        return sdata;
    }

    public static void v_Update_Perf_Language(Context mContext)
    {
        v_Data_SetPreferences(mContext, PREF_LAGUAGE, Rtx_TranslateValue.sInt2String(LanguageData.i_get_language()));
    }

    public static void v_Update_Perf_genenal(Context mContext)
    {
        int ival;

        v_Data_SetPreferences(mContext, PREF_SIIP, Rtx_TranslateValue.sInt2String(EngSetting.Unit));

        v_Data_SetPreferences(mContext, PREF_MODEL, EngSetting.s_Get_ENG_MDL());
        v_Data_SetPreferences(mContext, PREF_DEALER_ID, EngSetting.s_Get_ENG_OWN_DLR());
        v_Data_SetPreferences(mContext, PREF_GYM_ID, EngSetting.s_Get_ENG_GYM_ID());
        v_Data_SetPreferences(mContext, PREF_MACHINE_ID, EngSetting.s_Get_ENG_DEV_MSN());

        v_Data_SetPreferences(mContext, PREF_TIMEZONE_OFFSET_0, EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(0));
        v_Data_SetPreferences(mContext, PREF_TIMEZONE_OFFSET_1, EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(1));
        v_Data_SetPreferences(mContext, PREF_TIMEZONE_OFFSET_2, EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(2));
        v_Data_SetPreferences(mContext, PREF_TIMEZONE_OFFSET_3, EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(3));
        v_Data_SetPreferences(mContext, PREF_TIMEZONE_OFFSET_4, EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(4));
        v_Data_SetPreferences(mContext, PREF_COUNTRY, EngSetting.s_Get_DEFAULT_COUNTRY());

        v_Data_SetPreferences(mContext, PREF_MACHINE_CITY, EngSetting.s_Get_DEFAULT_CITY());
        v_Data_SetPreferences(mContext, PREF_WEATHER_INTERVAL, Rtx_TranslateValue.sInt2String(EngSetting.i_Get_Weather_Check_interval()));

        v_Data_SetPreferences(mContext, PREF_REGISTER_REGISTER, EngSetting.s_Get_DEFAULT_REGISTER_REGISTER());
        v_Data_SetPreferences(mContext, PREF_REGISTER_CONNECT, EngSetting.s_Get_DEFAULT_RTGISTER_CONNECT());

        v_Data_SetPreferences(mContext, PREF_MACHINE_TYPE, Rtx_TranslateValue.sInt2String(EngSetting.i_Get_ExerciseType()));

        ival = EngSetting.i_Get_Screen_Rotation();
        v_Data_SetPreferences(mContext, PREF_SCREEN_ROTATE, Rtx_TranslateValue.sInt2String(ival));

    }

    //Speed,Incline min and max
    public static void v_create_Treadmill(Context mContext)
    {

        v_Data_SetPreferences(mContext, PREF_SPEED, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Def_Speed(), 3));
        v_Data_SetPreferences(mContext, PREF_SPEED_MIN, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Min_Speed(), 3));
        v_Data_SetPreferences(mContext, PREF_SPEED_MAX, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Max_Speed(), 3));

        v_Data_SetPreferences(mContext, PREF_INCLINE, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Def_Incline(), 3));
        v_Data_SetPreferences(mContext, PREF_INCLINE_MIN, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Min_Incline(), 3));
        v_Data_SetPreferences(mContext, PREF_INCLINE_MAX, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Max_Incline(), 3));

        v_Data_SetPreferences(mContext, PREF_TREADMILL_IDLE_DETECT, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_idle_detect(), 3));
        v_Data_SetPreferences(mContext, PREF_TREADMILL_HZ_PARAMETER, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_HZ_parameter(), 3));
        v_Data_SetPreferences(mContext, PREF_TREADMILL_1KM_PARAMETER, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_1km_parameter(), 3));
        v_Data_SetPreferences(mContext, PREF_TREADMILL_PARAMETER1, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_parameter1(), 3));
        v_Data_SetPreferences(mContext, PREF_TREADMILL_ZERO_PARAMETER, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_zero_parameter(), 3));

        v_Data_SetPreferences(mContext, PREF_SPEED_ADD_PARAMETER, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fspeed_add_val(), 3));
        v_Data_SetPreferences(mContext, PREF_SPEED_SUB_PARAMETER, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fspeed_sub_val(), 3));

        return ;
    }

    public static void v_create_bike(Context mContext)
    {
        String sdata ;
        int imax = EngSetting.fmachine_tq.length ;
        int iLoop;

        v_Data_SetPreferences(mContext, PREF_LEVEL, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Def_level(), 3));
        v_Data_SetPreferences(mContext, PREF_LEVEL_MIN, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Min_level(), 3));
        v_Data_SetPreferences(mContext, PREF_LEVEL_MAX, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Max_level(), 3));

        v_Data_SetPreferences(mContext, PREF_BIKE_INCREMENT_DUTY, Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_increment_duty(), 3));

        sdata = "";
        for(iLoop = 0; iLoop < imax; iLoop++)
        {
//            sdata += Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_tq(iLoop), 1);
            sdata += String.valueOf(EngSetting.f_Get_fmachine_tq(iLoop));
            if(iLoop != (imax -1))
            {
                sdata += ",";
            }
        }
        v_Data_SetPreferences(mContext, PREF_BIKE_TOQUE, sdata);


        sdata = "";
        for(iLoop = 0; iLoop < imax; iLoop++)
        {
//            sdata += Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_rn(iLoop), 1);
            sdata += String.valueOf(EngSetting.f_Get_fmachine_rn(iLoop));
            if(iLoop != (imax -1))
            {
                sdata += ",";
            }
        }
        v_Data_SetPreferences(mContext, PREF_BIKE_RN, sdata);

        sdata = "";
        for(iLoop = 0; iLoop < imax; iLoop++)
        {
//            sdata += Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_tq(iLoop), 1);
            sdata += String.valueOf(EngSetting.f_Get_fmachine_tq_ubike(iLoop));
            if(iLoop != (imax -1))
            {
                sdata += ",";
            }
        }
        v_Data_SetPreferences(mContext, PREF_BIKE_TOQUE_UBIKE, sdata);


        sdata = "";
        for(iLoop = 0; iLoop < imax; iLoop++)
        {
//            sdata += Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_rn(iLoop), 1);
            sdata += String.valueOf(EngSetting.f_Get_fmachine_rn_ubike(iLoop));
            if(iLoop != (imax -1))
            {
                sdata += ",";
            }
        }
        v_Data_SetPreferences(mContext, PREF_BIKE_RN_UBIKE, sdata);

        sdata = "";
        for(iLoop = 0; iLoop < imax; iLoop++)
        {
//            sdata += Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_tq(iLoop), 1);
            sdata += String.valueOf(EngSetting.f_Get_fmachine_tq_rbike(iLoop));
            if(iLoop != (imax -1))
            {
                sdata += ",";
            }
        }
        v_Data_SetPreferences(mContext, PREF_BIKE_TOQUE_RBIKE, sdata);


        sdata = "";
        for(iLoop = 0; iLoop < imax; iLoop++)
        {
//            sdata += Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_rn(iLoop), 1);
            sdata += String.valueOf(EngSetting.f_Get_fmachine_rn_rbike(iLoop));
            if(iLoop != (imax -1))
            {
                sdata += ",";
            }
        }
        v_Data_SetPreferences(mContext, PREF_BIKE_RN_RBIKE, sdata);

        sdata = "";
        for(iLoop = 0; iLoop < imax; iLoop++)
        {
//            sdata += Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_tq(iLoop), 1);
            sdata += String.valueOf(EngSetting.f_Get_fmachine_tq_elliptical(iLoop));
            if(iLoop != (imax -1))
            {
                sdata += ",";
            }
        }
        v_Data_SetPreferences(mContext, PREF_BIKE_TOQUE_ELLIPTICAL, sdata);


        sdata = "";
        for(iLoop = 0; iLoop < imax; iLoop++)
        {
//            sdata += Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_rn(iLoop), 1);
            sdata += String.valueOf(EngSetting.f_Get_fmachine_rn_elliptical(iLoop));
            if(iLoop != (imax -1))
            {
                sdata += ",";
            }
        }
        v_Data_SetPreferences(mContext, PREF_BIKE_RN_ELLIPTICAL, sdata);

        return ;
    }

    public static void v_Update_Perf_EngSetting(Context mContext)
    {
        //General
        v_Update_Perf_genenal(mContext);

        //Treadmill
        v_create_Treadmill(mContext);

        //Bike
        v_create_bike(mContext);

    }

    public static void v_Update_Perf_Device_Info(Context mContext)
    {
        float fval;

        fval = EngSetting.f_Get_ENG_DEV_DISTANCE();
        v_Data_SetPreferences(mContext, PREF_MACHINE_DISTANCE, Rtx_TranslateValue.sFloat2String(fval, 3));

        fval = EngSetting.f_Get_ENG_DEV_TIME();
        v_Data_SetPreferences(mContext, PREF_MACHINE_TIME, Rtx_TranslateValue.sFloat2String(fval, 3));

    }

    public static void v_ReLoad_Perf_genenal(Context mContext)
    {
        int ival;
        float[] flimit;
        String sdata;

        ival = i_Data_GetPreferences(mContext, PREF_SIIP);
        if(ival == EngSetting.UNIT_METRIC || ival == EngSetting.UNIT_IMPERIAL) {
            EngSetting.setUnit(ival);
        }

        sdata = s_Data_GetPreferences(mContext, PREF_MODEL);
        if(sdata != null)
        {
            EngSetting.v_Set_ENG_MDL(sdata);
        }

        sdata = s_Data_GetPreferences(mContext, PREF_DEALER_ID);
        if(sdata != null)
        {
            EngSetting.v_Set_ENG_OWN_DLR(sdata);
        }

        sdata = s_Data_GetPreferences(mContext, PREF_GYM_ID);
        if(sdata != null)
        {
            EngSetting.v_Set_ENG_GYM_ID(sdata);
        }

        sdata = s_Data_GetPreferences(mContext, PREF_MACHINE_ID);
        if(sdata != null)
        {
            EngSetting.v_Set_ENG_DEV_MSN(sdata);
        }

        sdata = s_Data_GetPreferences(mContext, PREF_TIMEZONE_OFFSET_0);
        if(sdata != null)
        {
            EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(0, sdata);
        }
        sdata = s_Data_GetPreferences(mContext, PREF_TIMEZONE_OFFSET_1);
        if(sdata != null)
        {
            EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(1, sdata);
        }
        sdata = s_Data_GetPreferences(mContext, PREF_TIMEZONE_OFFSET_2);
        if(sdata != null)
        {
            EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(2, sdata);
        }
        sdata = s_Data_GetPreferences(mContext, PREF_TIMEZONE_OFFSET_3);
        if(sdata != null)
        {
            EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(3, sdata);
        }
        sdata = s_Data_GetPreferences(mContext, PREF_TIMEZONE_OFFSET_4);
        if(sdata != null)
        {
            EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(4, sdata);
        }

        sdata = s_Data_GetPreferences(mContext, PREF_COUNTRY);
        if(sdata != null)
        {
            EngSetting.v_Set_DEFAULT_COUNTRY(sdata);
        }

        sdata = s_Data_GetPreferences(mContext, PREF_MACHINE_CITY);
        if(sdata != null)
        {
            EngSetting.v_Set_DEFAULT_CITY(sdata);
        }

        ival = i_Data_GetPreferences(mContext, PREF_WEATHER_INTERVAL);
        flimit = EngSetting.fweather_interval_limit;
        if(ival >= (int)flimit[0] && ival <= (int)flimit[1]) {
            EngSetting.v_Set_Weather_Check_interval(ival);
        }

        sdata = s_Data_GetPreferences(mContext, PREF_REGISTER_REGISTER);
        if(sdata != null)
        {
            EngSetting.v_Set_DEFAULT_REGISTER_REGISTER(sdata);
        }

        sdata = s_Data_GetPreferences(mContext, PREF_REGISTER_CONNECT);
        if(sdata != null)
        {
            EngSetting.v_Set_DEFAULT_REGISTER_CONNECT(sdata);
        }

        ival = i_Data_GetPreferences(mContext, PREF_MACHINE_TYPE);
        if(ival == EngSetting.RBIKING || ival == EngSetting.RBIKING6 || ival == EngSetting.UBIKING || ival == EngSetting.UBIKING6 || ival == EngSetting.RBIKING || ival == EngSetting.RBIKING6 || ival == EngSetting.ELLIPTICAL || ival == EngSetting.ELLIPTICAL6 || ival == EngSetting.RUNNING6 ||ival == EngSetting.RUNNING7)
        {
            EngSetting.v_Set_ExerciseType(ival);
        }

        ival = i_Data_GetPreferences(mContext, PREF_SCREEN_ROTATE);
        if(ival > 0) {
            EngSetting.v_Set_Screen_Rotation(ival);
        }

    }

    //Speed,Incline min and max
    public static void v_ReLoad_Treadmill(Context mContext)
    {
        float fval;
        float[] flimit;

        //speed
        fval = f_Data_GetPreferences(mContext, PREF_SPEED_MIN);
        flimit = EngSetting.fSpeed_min_array;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_Min_Speed(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_SPEED_MAX);
        flimit = EngSetting.fSpeed_max_array;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_Max_Speed(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_SPEED);
        flimit = new float[]{EngSetting.f_Get_Min_Speed(), EngSetting.f_Get_Max_Speed()};
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_Def_Speed(fval);
        }

        //incline
        fval = f_Data_GetPreferences(mContext, PREF_INCLINE_MIN);
        flimit = EngSetting.fIncline_min_array;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_Min_Incline(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_INCLINE_MAX);
        flimit = EngSetting.fIncline_max_array;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_Max_Incline(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_INCLINE);
        flimit = new float[]{EngSetting.f_Get_Min_Incline(), EngSetting.f_Get_Max_Incline()};
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_Def_Incline(fval);
        }

        //other
        fval = f_Data_GetPreferences(mContext, PREF_TREADMILL_IDLE_DETECT);
        flimit = EngSetting.fidle_detect_limit;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_fmachine_idle_detect(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_TREADMILL_HZ_PARAMETER);
        flimit = EngSetting.fHZ_parameter_limit;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_fmachine_HZ_parameter(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_TREADMILL_1KM_PARAMETER);
        flimit = EngSetting.fkm_parameter_limit;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_fmachine_1km_parameter(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_TREADMILL_PARAMETER1);
        flimit = EngSetting.fkm_parameter1_limit;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_fmachine_parameter1(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_TREADMILL_ZERO_PARAMETER);
        flimit = EngSetting.fkm_zero_parameter_limit;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_fmachine_zero_parameter(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_SPEED_ADD_PARAMETER);
        flimit = EngSetting.fspeed_add_limit;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_fspeed_add_val(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_SPEED_SUB_PARAMETER);
        flimit = EngSetting.fspeed_sub_limit;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_fspeed_sub_val(fval);
        }

        return ;
    }

    public static void v_ReLoad_bike(Context mContext)
    {
        String sdata ;
        int imax = EngSetting.fmachine_tq.length ;
        int iLoop;
        String[] array;

        float fval;
        float[] flimit;

        //level
        fval = f_Data_GetPreferences(mContext, PREF_LEVEL_MIN);
        flimit = EngSetting.flevel_min_array;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_Min_level(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_LEVEL_MAX);
        flimit = EngSetting.flevel_max_array;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_Max_level(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_LEVEL);
        flimit = new float[]{EngSetting.f_Get_Min_level(), EngSetting.f_Get_Max_level()};
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_Def_level(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_BIKE_INCREMENT_DUTY);
        flimit = EngSetting.fincrement_duty_limit;
        if(fval >= flimit[0] && fval <= flimit[1]) {
            EngSetting.v_Set_fmachine_increment_duty(fval);
        }

        flimit = EngSetting.ftorqe_limit;
        sdata = s_Data_GetPreferences(mContext, PREF_BIKE_TOQUE);
        if(sdata != null)
        {
            array = sdata.split(",");
            if(array.length == imax)
            {
                for (iLoop = 0; iLoop < imax; iLoop++)
                {
                    fval = Rtx_TranslateValue.fString2Float(array[iLoop]);
                    if(fval >= flimit[0] && fval <= flimit[1]) {
                        EngSetting.v_Set_fmachine_tq(iLoop, fval);
                    }
                }
            }
        }

        flimit = EngSetting.fresistance_limit;
        sdata = s_Data_GetPreferences(mContext, PREF_BIKE_RN);
        if(sdata != null)
        {
            array = sdata.split(",");
            if(array.length == imax)
            {
                for (iLoop = 0; iLoop < imax; iLoop++)
                {
                    fval = Rtx_TranslateValue.fString2Float(array[iLoop]);
                    if(fval >= flimit[0] && fval <= flimit[1]) {
                        EngSetting.v_Set_fmachine_rn(iLoop, fval);
                    }
                }
            }
        }

        flimit = EngSetting.ftorqe_limit;
        sdata = s_Data_GetPreferences(mContext, PREF_BIKE_TOQUE_UBIKE);
        if(sdata != null)
        {
            array = sdata.split(",");
            if(array.length == imax)
            {
                for (iLoop = 0; iLoop < imax; iLoop++)
                {
                    fval = Rtx_TranslateValue.fString2Float(array[iLoop]);
                    if(fval >= flimit[0] && fval <= flimit[1]) {
                        EngSetting.v_Set_fmachine_tq_ubike(iLoop, fval);
                    }
                }
            }
        }

        flimit = EngSetting.fresistance_limit;
        sdata = s_Data_GetPreferences(mContext, PREF_BIKE_RN_UBIKE);
        if(sdata != null)
        {
            array = sdata.split(",");
            if(array.length == imax)
            {
                for (iLoop = 0; iLoop < imax; iLoop++)
                {
                    fval = Rtx_TranslateValue.fString2Float(array[iLoop]);
                    if(fval >= flimit[0] && fval <= flimit[1]) {
                        EngSetting.v_Set_fmachine_rn_ubike(iLoop, fval);
                    }
                }
            }
        }

        flimit = EngSetting.ftorqe_limit;
        sdata = s_Data_GetPreferences(mContext, PREF_BIKE_TOQUE_RBIKE);
        if(sdata != null)
        {
            array = sdata.split(",");
            if(array.length == imax)
            {
                for (iLoop = 0; iLoop < imax; iLoop++)
                {
                    fval = Rtx_TranslateValue.fString2Float(array[iLoop]);
                    if(fval >= flimit[0] && fval <= flimit[1]) {
                        EngSetting.v_Set_fmachine_tq_rbike(iLoop, fval);
                    }
                }
            }
        }

        flimit = EngSetting.fresistance_limit;
        sdata = s_Data_GetPreferences(mContext, PREF_BIKE_RN_RBIKE);
        if(sdata != null)
        {
            array = sdata.split(",");
            if(array.length == imax)
            {
                for (iLoop = 0; iLoop < imax; iLoop++)
                {
                    fval = Rtx_TranslateValue.fString2Float(array[iLoop]);
                    if(fval >= flimit[0] && fval <= flimit[1]) {
                        EngSetting.v_Set_fmachine_rn_rbike(iLoop, fval);
                    }
                }
            }
        }

        flimit = EngSetting.ftorqe_limit;
        sdata = s_Data_GetPreferences(mContext, PREF_BIKE_TOQUE_ELLIPTICAL);
        if(sdata != null)
        {
            array = sdata.split(",");
            if(array.length == imax)
            {
                for (iLoop = 0; iLoop < imax; iLoop++)
                {
                    fval = Rtx_TranslateValue.fString2Float(array[iLoop]);
                    if(fval >= flimit[0] && fval <= flimit[1]) {
                        EngSetting.v_Set_fmachine_tq_elliptical(iLoop, fval);
                    }
                }
            }
        }

        flimit = EngSetting.fresistance_limit;
        sdata = s_Data_GetPreferences(mContext, PREF_BIKE_RN_ELLIPTICAL);
        if(sdata != null)
        {
            array = sdata.split(",");
            if(array.length == imax)
            {
                for (iLoop = 0; iLoop < imax; iLoop++)
                {
                    fval = Rtx_TranslateValue.fString2Float(array[iLoop]);
                    if(fval >= flimit[0] && fval <= flimit[1]) {
                        EngSetting.v_Set_fmachine_rn_elliptical(iLoop, fval);
                    }
                }
            }
        }

        return ;
    }

    public static void v_ReLoad_Language(Context mContext)
    {
        int ival;
        float[] flimit;

        ival = i_Data_GetPreferences(mContext, PREF_LAGUAGE);
        flimit = new float[]{0, LanguageData.llan_array.length - 1};
        if(ival >= (int)flimit[0] && ival <= (int)flimit[1]) {
            LanguageData.v_set_language(ival);
        }

    }

    public static void v_ReLoad_Device_Info(Context mContext)
    {
        float fval;

        fval = f_Data_GetPreferences(mContext, PREF_MACHINE_TIME);
        //檢查是否有值或clear
        if(fval > 0 || fval == -11) {
            EngSetting.v_Set_ENG_DEV_TIME(fval);
        }

        fval = f_Data_GetPreferences(mContext, PREF_MACHINE_DISTANCE);
        //檢查是否有值或clear
        if(fval > 0 || fval == -11) {
            EngSetting.v_Set_ENG_DEV_DISTANCE(fval);
        }

    }

    public static void v_ReLoad_Perf(Context mContext)
    {
        //General
        v_ReLoad_Perf_genenal(mContext);

        //Treadmill
        v_ReLoad_Treadmill(mContext);

        //Bike
        v_ReLoad_bike(mContext);

        //Bike
        v_ReLoad_Language(mContext);

        //Device info
        v_ReLoad_Device_Info(mContext);

    }

}
