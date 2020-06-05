package com.rtx.treadmill.GlobalData;

import android.content.Context;
import android.util.Log;

import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by jerry on 2017/6/23.
 */

public class EngSetting {

    //STATE MACHINE TIME SETTING/////////////////////////////////////////////////////////////////////////////
    public static final int DEF_UNIT_TIME = 10 ;
    public static final int DEF_SEC_COUNT = 1000 / DEF_UNIT_TIME;
    public static final int DEF_HSEC_COUNT = 500 / DEF_UNIT_TIME;
    public static final int DEF_QSEC_COUNT = 250 / DEF_UNIT_TIME;
    public static final int  DEF_100ms_COUNT = 100 / DEF_UNIT_TIME;

    public static final int DEF_EXERCISE_REFRESH = 100 / DEF_UNIT_TIME;//運動主畫面更新時間
    public static final int DEF_EXERCISE_BOTTOM_REFRESH = 100 / DEF_UNIT_TIME; //運動時的下方bar更新時間
    public static final int DEF_EXERCISE_PHYSICAL_REFRESH = 250 / DEF_UNIT_TIME;//Physical運動畫面更新時間

    public static final int DEF_UPDATE_DEV_STATUS = DEF_SEC_COUNT * 300;//固定週期上傳機器狀態
    public static final int DEF_POLLING_DEV_STATUS = DEF_100ms_COUNT * 1;//無運動時，固定週期更新機器狀態

    ///////////////////Screen Rotation////////////////////////////////////////
    public static int  iROTATION_AUTO     = 0;
    public static int  iROTATION_00       = 1;
    public static int  iROTATION_90       = 2;
    public static int  iROTATION_180      = 3;
    public static int  iROTATION_270      = 4;
    public static int  irotation_def       = iROTATION_AUTO;
    public static int i_Get_Screen_Rotation(){
        return irotation_def;
    }
    public static void v_Set_Screen_Rotation(int ival){
        irotation_def = ival;
    }

    //    public static final String ENG_DEFAULT_ID = "c1@c";   //default帳號
    //    public static final String ENG_DEFAULT_PASSWORD = "123456";           //default密碼
    public static final String ENG_DEFAULT_ID = "j@j";   //default帳號
    public static final String ENG_DEFAULT_PASSWORD = "123456";           //default密碼

    /////////////////////////////////////////////////////////////////////////////////////
    public static String PASSWORD_ENTER = "newsoft";
    public static String PASSWORD_EXIT = "rtx";

    public static String APK_VER = "60.00";
    public static String s_Get_APK_VER(){
        return APK_VER;
    }
    public static void v_Set_APK_VER(String str){
        APK_VER = str;
    }

    //APK最後更新時間
    public static String SW_UPDATE_TIME = "2011-01-01 00:00:00";
    public static String s_Get_SW_UPDATE_TIME(){
        return SW_UPDATE_TIME;
    }
    public static void v_Set_SW_UPDATE_TIME(String str){
        SW_UPDATE_TIME = str;
    }

    //GYM///////////////////////////////////////////////////////////////////////////////////
    //設備所屬客戶
    public static String ENG_OWN_DLR = "";
    public static String s_Get_ENG_OWN_DLR(){
        return ENG_OWN_DLR;
    }
    public static void v_Set_ENG_OWN_DLR(String str){
        ENG_OWN_DLR = str;
    }

    //設備所屬GYM ID
    public static String ENG_GYM_ID = "0000-0000-0000";
    public static String s_Get_ENG_GYM_ID(){
        return ENG_GYM_ID;
    }
    public static void v_Set_ENG_GYM_ID(String str){
        ENG_GYM_ID = str;
    }

    //機台編號
    public static String ENG_DEV_MSN = "";
    public static String s_Get_ENG_DEV_MSN(){
        return ENG_DEV_MSN;
    }
    public static void v_Set_ENG_DEV_MSN(String str){
        ENG_DEV_MSN = str;
    }

    //設備世代
    public static String ENG_GNR = "00";
    public static String s_Get_ENG_GNR(){
        return ENG_GNR;
    }
    public static void v_Set_ENG_GNR(String str){
        ENG_GNR = str;
    }

    //Console "Eplus"=? "E"=? "S"=8吋
    public static String CON_ID = "S";
    public static String s_Get_CON_ID(){
        return CON_ID;
    }
    public static void v_Set_CON_ID(String str){
        CON_ID = str;
    }

    //設備型號
    public static String ENG_MDL = "M8";
    public static String s_Get_ENG_MDL(){
        return ENG_MDL;
    }
    public static void v_Set_ENG_MDL(String str){
        ENG_MDL = str;
    }

    //設備編號
    public static String ENG_CSN = "1";
    public static String s_Get_ENG_CSN(){
        return ENG_CSN;
    }
    public static void v_Set_ENG_CSN(String str){
        ENG_CSN = str;
    }

    //機台使用距離(公里)
    public static float ENG_DEV_DISTANCE = 0;
    public static float f_Get_ENG_DEV_DISTANCE(){
        return ENG_DEV_DISTANCE;
    }
    public static void v_Set_ENG_DEV_DISTANCE(float fval){
        ENG_DEV_DISTANCE = fval;
    }

    //機台使用時間(小時)
    public static float ENG_DEV_TIME = 0;
    public static float f_Get_ENG_DEV_TIME(){
        return ENG_DEV_TIME;
    }
    public static void v_Set_ENG_DEV_TIME(float fval){
        ENG_DEV_TIME = fval;
    }

    //Timezone//////////////////////////////////////////////////////////////////////////////////
    public static String DEFAULT_TIMEZONE_OFFSET[] = {"+","0","8","0","0"};
    public static String s_Get_DEFAULT_TIMEZONE_OFFSET(int iIndex){
        return DEFAULT_TIMEZONE_OFFSET[iIndex];
    }
    public static void v_Set_DEFAULT_TIMEZONE_OFFSET(int iIndex, String str){
        DEFAULT_TIMEZONE_OFFSET[iIndex] = str;
    }

    public static String DEFAULT_COUNTRY = "Unknown";
    public static String s_Get_DEFAULT_COUNTRY(){
        return DEFAULT_COUNTRY;
    }
    public static void v_Set_DEFAULT_COUNTRY(String str){
        DEFAULT_COUNTRY = str;
    }

    //Weather///////////////////////////////////////////////////////////////////////////////////
    public static boolean ENABLE_WEATHER = true;

    public static String DEFAULT_CITY = "Taipei";
    public static String s_Get_DEFAULT_CITY(){
        return DEFAULT_CITY;
    }
    public static void v_Set_DEFAULT_CITY(String str){
        DEFAULT_CITY = str;
    }

    public static String DEFAULT_TEMP = "-- °C";
    public static String s_Get_DEFAULT_TEMP(){
        return DEFAULT_TEMP;
    }
    public static void v_Set_DEFAULT_TEMP(String str){
        DEFAULT_TEMP = str;
    }

    public static float[] fweather_interval_limit  = {10f, 9999f};
    public static int iWeather_Check_interval = 60;
    public static int i_Get_Weather_Check_interval(){
        return iWeather_Check_interval;
    }
    public static void v_Set_Weather_Check_interval(int ival){
        iWeather_Check_interval = ival;
    }

    //Register/////////////////////////////////////////////////////////////////////////////////

    public static String DEFAULT_REGISTER_REGISTER = "";
    public static String s_Get_DEFAULT_REGISTER_REGISTER(){
        return DEFAULT_REGISTER_REGISTER;
    }
    public static void v_Set_DEFAULT_REGISTER_REGISTER(String str){
        DEFAULT_REGISTER_REGISTER = str;
    }

    public static String DEFAULT_REGISTER_CONNECT = "";
    public static String s_Get_DEFAULT_RTGISTER_CONNECT(){
        return DEFAULT_REGISTER_CONNECT;
    }
    public static void v_Set_DEFAULT_REGISTER_CONNECT(String str){
        DEFAULT_REGISTER_CONNECT = str;
    }

    //Age///////////////////////////////////////////////////////////////////////////////////
    public static float fage_min        = 10f ;
    public static float fage_max        = 99f ;
    public static float fage_def        = 25f ;
    public static float f_Get_Min_Age(){
        return fweight_min;
    }
    public static float f_Get_Max_Age(){
        return fage_max;
    }
    public static float f_Get_Def_Age(){
        return fage_def;
    }

    //height///////////////////////////////////////////////////////////////////////////////////
    public static float fheight_min     = 140f ;
    public static float fheight_max     = 220f ;
    public static float fheight_def     = 170f ;	//cm
    public static float f_Get_Def_Height(){
        return fheight_def;
    }

    //weight///////////////////////////////////////////////////////////////////////////////////
    public static float fweight_min     = 35f ;
    public static float fweight_max     = 220f ;
    public static float fweight_def     = 80f ;	    //kg
    public static float f_Get_Min_Weight(){
        return fweight_min;
    }
    public static float f_Get_Max_Weight(){
        return fweight_max;
    }
    public static float f_Get_Def_Weight(){
        return fweight_def;
    }

    //Speed///////////////////////////////////////////////////////////////////////////////////
    //20181129修改規格,公制 min:0.5f~1f 英制 min:0.3f~0.6f
    //                公制 max:20f~25f 英制 min:12.5f~15.6f

    public static float[] fSpeed_min_array    = {0.5f, 1f};
    public static float[] fSpeed_max_array    = {20f, 25f};
    public static float fSpeed_min  = 0.5f;
    public static float fSpeed_max  = fSpeed_max_array[1];
    public static float fSpeed_def      = 0.5f;
    public static float f_Get_Min_Speed(){
        return fSpeed_min;
    }
    public static void v_Set_Min_Speed(float fval){
        fSpeed_min = fval;
    }
    public static float f_Get_Max_Speed(){
        return fSpeed_max;
    }
    public static void v_Set_Max_Speed(float fval){
        fSpeed_max = fval;
    }
    public static float f_Get_Def_Speed(){
        return fSpeed_def;
    }
    public static void v_Set_Def_Speed(float fval){
        fSpeed_def = fval;
    }

    //Incline///////////////////////////////////////////////////////////////////////////////////
    public static float[] fIncline_min_array  = {0f, 0f};
    public static float[] fIncline_max_array  = {15f, 20f};
    public static float fIncline_min  = fIncline_min_array[0];
    public static float fIncline_max  = fIncline_max_array[1];
    public static float fIncline_def    = 0.0f;
    public static float f_Get_Min_Incline(){
        return fIncline_min;
    }
    public static void v_Set_Min_Incline(float fval){
        fIncline_min = fval;
    }
    public static float f_Get_Max_Incline(){
        return fIncline_max;
    }
    public static void v_Set_Max_Incline(float fval){
        fIncline_max = fval;
    }
    public static float f_Get_Def_Incline(){
        return fIncline_def;
    }
    public static void v_Set_Def_Incline(float fval){
        fIncline_def = fval;
    }

    //level///////////////////////////////////////////////////////////////////////////////////
    public static float[] flevel_min_array    = {1f, 1f};
    public static float[] flevel_max_array    = {16f, 25f};
    public static float flevel_min  = flevel_min_array[0];
    public static float flevel_max  = flevel_max_array[1];
    public static float flevel_def      = 1f;
    public static float f_Get_Min_level(){
        return flevel_min;
    }
    public static void v_Set_Min_level(float fval){
        flevel_min = fval;
    }
    public static float f_Get_Max_level(){
        return flevel_max;
    }
    public static void v_Set_Max_level(float fval){
        flevel_max = fval;
    }
    public static float f_Get_Def_level(){
        return flevel_def;
    }
    public static void v_Set_Def_level(float fval){
        flevel_def = fval;
    }

    //HeartRate///////////////////////////////////////////////////////////////////////////////////
    public static float fhr_min     = 40f ;
    public static float fhr_max     = 220f ;
    public static float f_Get_Min_HR(){
        return fhr_min;
    }
    public static float f_Get_Max_HR(){
        return fhr_max;
    }

    //Unit///////////////////////////////////////////////////////////////////////////////////
    public static int UNIT_METRIC = 0;
    public static int UNIT_IMPERIAL = 1;
    public static int UNIT_UNKNOW = 2;
    public static int Unit = UNIT_METRIC;
    public static int reg_temp_unit = UNIT_UNKNOW;

    public static void setTempUnit(int iUnit)
    {
        reg_temp_unit = iUnit;
    }

    public static void setUnit(int iUnit)
    {
        Unit = iUnit;
    }

    public static void vClearTempUnit()
    {
        if(reg_temp_unit != UNIT_UNKNOW)
        {
            reg_temp_unit = UNIT_UNKNOW;
        }
    }

    public static int getUnit()
    {
        int ival = Unit;

        if(CloudDataStruct.CloudData_20.bLogin)
        {
            String sdata = CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.WEI_UNT];
            if(sdata != null && sdata.length() > 0) {
                String sval = sdata.substring(0, 1);
                if(sval != null) {
                    if (sval.toLowerCase().compareTo("m") == 0) {
                        ival = UNIT_METRIC;
                    } else if (sval.toLowerCase().compareTo("e") == 0){
                        ival = UNIT_IMPERIAL;
                    }
                }
            }
        }
        else
        if(reg_temp_unit != UNIT_UNKNOW)
        {
            ival = reg_temp_unit;
        }

        return ival;
    }

    //ExerciseType///////////////////////////////////////////////////////////////////////////////////
    public static int  RUNNING      = 0;
    public static int  UBIKING      = 1;
    public static int  RBIKING      = 2;
    public static int  ELLIPTICAL   = 3;
    public static int  ALL          = 4;
    public static int  RUNNING6     = 5;
    public static int  RUNNING7     = 6;
    public static int  UBIKING6     = 7;
    public static int  RBIKING6     = 8;
    public static int  ELLIPTICAL6  = 9;
    public static int  ExerciseType = RUNNING;
    public static void v_Set_ExerciseType(int ival)
    {
        ExerciseType = ival;
    }

    public static int i_Get_ExerciseType()
    {
        int ival = ExerciseType;

        return ival;
    }

    public static String S_Get_ExerciseType()
    {
        String smch_type;

        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
        {
            smch_type = "T";
        }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING6)
        {
            smch_type = "U";
        }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING6)
        {
            smch_type = "R";
        }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL || EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL6)
        {
            smch_type = "E";
        }
        else
        {
            smch_type = "A";
        }
        return smch_type;
    }

    public static String S_Get_ExerciseMode()
    {
        String smch_mode;

        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
        {
            smch_mode = "R";
        }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING6)
        {
            smch_mode = "B";
        }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING6)
        {
            smch_mode = "B";
        }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL || EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL6)
        {
            smch_mode = "E";
        }
        else
        {
            smch_mode = "A";
        }
        return smch_mode;
    }

    public static String S_Get_ExerciseName()
    {
        String smch_name;

        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
        {
            smch_name = "Treadmill";
        }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING6)
        {
            smch_name = "Upright Bike";
        }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING6)
        {
            smch_name = "Recumbent Bike";
        }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL || EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL6)
        {
            smch_name = "Elliptical";
        }
        else
        {
            smch_name = "Treadmill";
        }
        return smch_name;
    }


    /////////////////////////////////////////////////////////////////////////////////////
    //Uart F62
    public static float[] fspeed_add_limit  = {1f, 50f};
    public static float fspeed_add_val              =   5.0f;
    public static float f_Get_fspeed_add_val(){
        return fspeed_add_val;
    }
    public static void v_Set_fspeed_add_val(float fval){
        fspeed_add_val = fval;
    }

    public static float[] fspeed_sub_limit  = {1f, 50f};
    public static float fspeed_sub_val              =   5.0f;
    public static float f_Get_fspeed_sub_val(){
        return fspeed_sub_val;
    }
    public static void v_Set_fspeed_sub_val(float fval){
        fspeed_sub_val = fval;
    }

    //Uart F18 for Treadmill(machine code 3)
    public static float[] fidle_detect_limit = {0f, 1f};
    public static float fmachine_idle_detect        =  0f;
    public static float f_Get_fmachine_idle_detect(){
        return fmachine_idle_detect;
    }
    public static void v_Set_fmachine_idle_detect(float fval){
        fmachine_idle_detect = fval;
    }

    public static float[] fHZ_parameter_limit = {1f, 999f};
    //20190111 規格變更 530 -> 600
    public static float fmachine_HZ_parameter       =  600f;
    public static float f_Get_fmachine_HZ_parameter(){
        return fmachine_HZ_parameter;
    }
    public static void v_Set_fmachine_HZ_parameter(float fval){
        fmachine_HZ_parameter = fval;
    }

    public static float[] fkm_parameter_limit  = {1f, 999f};
    public static float fmachine_1km_parameter      =  600f;
    public static float f_Get_fmachine_1km_parameter(){
        return fmachine_1km_parameter;
    }
    public static void v_Set_fmachine_1km_parameter(float fval){
        fmachine_1km_parameter = fval;
    }

    public static float[] fkm_parameter1_limit  = {1f, 9999f};
    //20190111 規格變更 35 -> 15
    public static float fmachine_parameter1         =  15f;
    public static float f_Get_fmachine_parameter1(){
        return fmachine_parameter1;
    }
    public static void v_Set_fmachine_parameter1(float fval){
        fmachine_parameter1 = fval;
    }

    public static float[] fkm_zero_parameter_limit  = {1f, 199f};
    //20190111 規格變更 130 -> 135
    public static float fmachine_zero_parameter     =  100f;
    public static float f_Get_fmachine_zero_parameter(){
        return fmachine_zero_parameter;
    }
    public static void v_Set_fmachine_zero_parameter(float fval){
        fmachine_zero_parameter = fval;
    }

    //Uart F18 for U-bike(machine code 5); R-bite(machine code 6); ecllipe(machine code 4)
    public static float[] fincrement_duty_limit  = {1f, 255f};
    public static float fmachine_increment_duty     =  100f;
    public static float f_Get_fmachine_increment_duty(){
        return fmachine_increment_duty;
    }
    public static void v_Set_fmachine_increment_duty(float fval){
        fmachine_increment_duty = fval;
    }

    public static float[] ftorqe_limit  = {1f, 999f};
    public static float[] fmachine_tq =
            {
                49 , 65 , 81 , 97 , 114, 130, 146, 162, 179, 195,
                211, 230, 250, 269, 289, 308, 328, 347, 367, 386,
                438, 490, 542, 594, 649
            };
    public static float f_Get_fmachine_tq(int index){
        float fval;

        if(index >= 0 && index < fmachine_tq.length)
        {
            fval = fmachine_tq[index];
        }
        else
        {
            fval = 0f;
        }
        return fval;
    }
    public static void v_Set_fmachine_tq(int index, float fval){
        if(index < fmachine_tq.length)
        {
            fmachine_tq[index] = fval;
        }
    }

    public static float[] fmachine_tq_ubike =
            {
                    49 , 65 , 81 , 97 , 114, 130, 146, 162, 179, 195,
                    211, 230, 250, 269, 289, 308, 328, 347, 367, 386,
                    438, 490, 542, 594, 649
            };

    public static float f_Get_fmachine_tq_ubike(int index){
        float fval;

        if(index >= 0 && index < fmachine_tq_ubike.length)
        {
            fval = fmachine_tq_ubike[index];
        }
        else
        {
            fval = 0f;
        }
        return fval;
    }
    public static void v_Set_fmachine_tq_ubike(int index, float fval){
        if(index < fmachine_tq_ubike.length)
        {
            fmachine_tq_ubike[index] = fval;
        }
    }

    public static float[] fmachine_tq_rbike =
            {
                    49 , 65 , 81 , 97 , 114, 130, 146, 162, 179, 195,
                    211, 230, 250, 269, 289, 308, 328, 347, 367, 386,
                    438, 490, 542, 594, 649
            };

    public static float f_Get_fmachine_tq_rbike(int index){
        float fval;

        if(index >= 0 && index < fmachine_tq_rbike.length)
        {
            fval = fmachine_tq_rbike[index];
        }
        else
        {
            fval = 0f;
        }
        return fval;
    }
    public static void v_Set_fmachine_tq_rbike(int index, float fval){
        if(index < fmachine_tq_rbike.length)
        {
            fmachine_tq_rbike[index] = fval;
        }
    }

    public static float[] fmachine_tq_elliptical =
            {
                    49 , 65 , 81 , 97 , 114, 130, 146, 162, 179, 195,
                    211, 230, 250, 269, 289, 308, 328, 347, 367, 386,
                    438, 490, 542, 594, 649
            };

    public static float f_Get_fmachine_tq_elliptical(int index){
        float fval;

        if(index >= 0 && index < fmachine_tq_elliptical.length)
        {
            fval = fmachine_tq_elliptical[index];
        }
        else
        {
            fval = 0f;
        }
        return fval;
    }
    public static void v_Set_fmachine_tq_elliptical(int index, float fval){
        if(index < fmachine_tq_elliptical.length)
        {
            fmachine_tq_elliptical[index] = fval;
        }
    }


    //fwatt = frpm * fmachine_tq[0] * 1.027
    public static float[] fresistance_limit  = {1f, 9999f};
    public static float[] fmachine_rn =
            {
                     235,  314,  370,  410,  520,  555,  590,  630,  680,  720,
                     760,  790,  820,  850,  885,  920,  950,  985, 1020, 1055,
                    1130, 1210, 1290, 1365, 1445
            };
    public static float f_Get_fmachine_rn(int index){
        float fval;

        if(index >= 0 && index < fmachine_rn.length)
        {
            fval = fmachine_rn[index];
        }
        else
        {
            fval = 0f;
        }
        return fval;
    }
    public static void v_Set_fmachine_rn(int index, float fval){
        if(index < fmachine_rn.length)
        {
            fmachine_rn[index] = fval;
        }
    }

    public static float[] fmachine_rn_ubike =
            {
                    235,  314,  370,  410,  520,  555,  590,  630,  680,  720,
                    760,  790,  820,  850,  885,  920,  950,  985, 1020, 1055,
                    1130, 1210, 1290, 1365, 1445
            };
    public static float f_Get_fmachine_rn_ubike(int index){
        float fval;

        if(index >= 0 && index < fmachine_rn_ubike.length)
        {
            fval = fmachine_rn_ubike[index];
        }
        else
        {
            fval = 0f;
        }
        return fval;
    }
    public static void v_Set_fmachine_rn_ubike(int index, float fval){
        if(index < fmachine_rn_ubike.length)
        {
            fmachine_rn_ubike[index] = fval;
        }
    }

    public static float[] fmachine_rn_rbike =
            {
                    235,  314,  370,  410,  520,  555,  590,  630,  680,  720,
                    760,  790,  820,  850,  885,  920,  950,  985, 1020, 1055,
                    1130, 1210, 1290, 1365, 1445
            };
    public static float f_Get_fmachine_rn_rbike(int index){
        float fval;

        if(index >= 0 && index < fmachine_rn_rbike.length)
        {
            fval = fmachine_rn_rbike[index];
        }
        else
        {
            fval = 0f;
        }
        return fval;
    }
    public static void v_Set_fmachine_rn_rbike(int index, float fval){
        if(index < fmachine_rn_rbike.length)
        {
            fmachine_rn_rbike[index] = fval;
        }
    }

    public static float[] fmachine_rn_elliptical =
            {
                    235,  314,  370,  410,  520,  555,  590,  630,  680,  720,
                    760,  790,  820,  850,  885,  920,  950,  985, 1020, 1055,
                    1130, 1210, 1290, 1365, 1445
            };
    public static float f_Get_fmachine_rn_elliptical(int index){
        float fval;

        if(index >= 0 && index < fmachine_rn_elliptical.length)
        {
            fval = fmachine_rn_elliptical[index];
        }
        else
        {
            fval = 0f;
        }
        return fval;
    }
    public static void v_Set_fmachine_rn_elliptical(int index, float fval){
        if(index < fmachine_rn_elliptical.length)
        {
            fmachine_rn_elliptical[index] = fval;
        }
    }

    public static String s_F18_parameter()
    {
        String sdata;

        if(i_Get_ExerciseType() == RUNNING)
        {
            sdata = "1" + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_idle_detect(),1) + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_HZ_parameter(),3) + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_1km_parameter(),3) + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_parameter1(),2) + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_zero_parameter(),3) ;

        }else if(i_Get_ExerciseType() == RUNNING7)
        {
            sdata = "2" + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_idle_detect(),1) + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_HZ_parameter(),3) + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_1km_parameter(),3) + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_parameter1(),2) + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_zero_parameter(),3) ;

        }
        else if(i_Get_ExerciseType() == RUNNING6)
        {
            sdata = "3" + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_idle_detect(),1) + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_HZ_parameter(),3) + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_1km_parameter(),3) + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_parameter1(),2) + ","
                    + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_zero_parameter(),3) ;

        }
        else
        {
            sdata = "5" + "," + Rtx_TranslateValue.sInt2String((int)f_Get_fmachine_HZ_parameter(),3);
        }

        return sdata;
    }


    /////////////////////////////////////////////////////////////////////////////////////
    //inch to cm; cm to inch
    public static float inch2cm = (float) 2.54;
    public static float cm2inch = (float) 1/inch2cm;
    public static float fInch2CmMin     = 2f ;
    public static float fInch2CmMax     = 3f ;
    public static float f_Get_MinInch2Cm(){
        return fInch2CmMin;
    }
    public static float f_Get_MaxInch2Cm(){
        return fInch2CmMax;
    }
    public static float f_Get_EngInch2Cm(){
        return inch2cm;
    }
    public static void v_Set_EngInch2Cm(float fval){
        inch2cm = fval;
        cm2inch = (float) 1/inch2cm;
    }


    public static float fMetric_Height_Max = 220;
    public static float fMetric_Height_Min = 130;

    public static float fImperial_Height_Min = (4 * 12 + 3) * inch2cm;   //4'3"
    public static float fImperial_Height_Max = (7 * 12 + 3) * inch2cm;   //7'3"

    //20181204修改規格 2.2 -> 2.2046
    public static float kg2lb = (float) 2.2046;
	public static float lb2kg = (float) 1/kg2lb;
    public static float fKg2LbMin     = 2f ;
    public static float fKg2LbMax     = 3f ;
    public static float f_Get_MinKg2Lb(){
        return fKg2LbMin;
    }
    public static float f_Get_MaxKg2Lb(){
        return fKg2LbMax;
    }
    public static float f_Get_EngKg2Lb(){
        return kg2lb;
    }
    public static void v_Set_EngKg2Lb(float fval){
        kg2lb = fval;
        lb2kg = (float) 1/kg2lb;
    }

    //20181223 修改規格 1.6 -> 1.6093
    public static float mile2km = (float) 1.6093;
	public static float km2mile = (float) 1/mile2km;
    public static float fMile2KmMin     = 1f ;
    public static float fMile2KmMax     = 2f ;
    public static float f_Get_MinMile2Km(){
        return fMile2KmMin;
    }
    public static float f_Get_MaxMile2Km(){
        return fMile2KmMax;
    }
    public static float f_Get_EngMile2Km(){
        return mile2km;
    }
    public static void v_Set_EngMile2Km(float fval){
        mile2km = fval;
        km2mile = (float) 1/mile2km;
    }


    /////////////////////////////////////////////////////////////////////////////////////

    public static float LETTER_SPACING = 0.050f;

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    public static class Weight
    {
        public static String getUnitString(Context mContext)
        {
            String sUnit = null;

            if(getUnit() == UNIT_METRIC)
            {
                sUnit = LanguageData.s_get_string(mContext, R.string.kg);
            }
            else
            {
                sUnit = LanguageData.s_get_string(mContext, R.string.lb);
            }

            return sUnit;
        }

        public static String getValString(float fKg)
        {
            String sVal = null;

            if(getUnit() == UNIT_METRIC)
            {
                sVal = Rtx_TranslateValue.sFloat2String(fKg,1);
            }
            else
            {
                sVal = Rtx_TranslateValue.sFloat2String(fKg*kg2lb,1);
            }

            return sVal;
        }

        public static String getValString(float fKg, int iDecimalNumber)
        {
            String sVal = null;

            if(getUnit() == UNIT_METRIC)
            {
                sVal = Rtx_TranslateValue.sFloat2String(fKg,iDecimalNumber);
            }
            else
            {
                sVal = Rtx_TranslateValue.sFloat2String(fKg*kg2lb,iDecimalNumber);
            }

            return sVal;
        }

        public static String getValString(String sKg)
        {
            float fKg = Rtx_TranslateValue.fString2Float(sKg);

            String sVal = null;

            sVal = getValString(fKg);

            return sVal;
        }

        public static float getVal(float fKg)
        {
            float fVal = 0;

            if(getUnit() == UNIT_METRIC)
            {
                fVal = fKg;
            }
            else
            {
                fVal = fKg * kg2lb;
            }

            return fVal;
        }

        public static float fTranslat2KG(float fWeight)
        {
            float fVal = 0;

            if(getUnit() == UNIT_METRIC)
            {
                fVal = fWeight;
            }
            else
            {
                fVal = fWeight * lb2kg;
            }

            return fVal;
        }

        public static String getRangeString(Context mContext, float fmin, float fmax, int iDecimalNumber)
        {
            String sdata = "35-220";

            try
            {
                sdata = getValString(fmin, iDecimalNumber)
                        + " - " + getValString(fmax, iDecimalNumber)
                        + " " + getUnitString(mContext);
            }
            catch (Exception e)
            {

            }

            return sdata;
        }
    }

    public static class Distance
    {
        public static String getUnitString(Context mContext)
        {
            String sUnit = null;

            if(getUnit() == UNIT_METRIC)
            {
                sUnit = LanguageData.s_get_string(mContext, R.string.km);
            }
            else
            {
                sUnit = LanguageData.s_get_string(mContext, R.string.mi);
            }

            return sUnit;
        }

        public static String getSpeedUnitString(Context mContext)
        {
            String sUnit = null;

            if(getUnit() == UNIT_METRIC)
            {
                sUnit = LanguageData.s_get_string(mContext, R.string.kph);
            }
            else
            {
                sUnit = LanguageData.s_get_string(mContext, R.string.mph);
            }

            return sUnit;
        }

        public static String getSpeedUnitLowerString(Context mContext)
        {
            String sUnit = null;

            if(getUnit() == UNIT_METRIC)
            {
                sUnit = LanguageData.s_get_string(mContext, R.string.kph_lower);
            }
            else
            {
                sUnit = LanguageData.s_get_string(mContext, R.string.mph_lower);
            }

            return sUnit;
        }

        public static float getVal(float fKM)
        {
            float fVal = 0;

            if(getUnit() == UNIT_METRIC)
            {
                fVal = fKM;
            }
            else
            {
                fVal = fKM*km2mile;
            }

            return fVal;
        }

        public static String getValString(float fKM)
        {
            String sVal = null;
            float fval ;

            fval = fKM;
            if(fval < 0)
            {
                fval = 0;
            }

            if(getUnit() == UNIT_METRIC)
            {
                sVal = Rtx_TranslateValue.sFloat2String(fval,1);
            }
            else
            {
                sVal = Rtx_TranslateValue.sFloat2String(fval*km2mile,1);
            }

            return sVal;
        }

        public static String getValString(float fKM, int iDecimalNumber)
        {
            String sVal = null;

            if(getUnit() == UNIT_METRIC)
            {
                sVal = Rtx_TranslateValue.sFloat2String(fKM,iDecimalNumber);
            }
            else
            {
                sVal = Rtx_TranslateValue.sFloat2String(fKM*km2mile,iDecimalNumber);
            }

            return sVal;
        }

        public static String getValString(String sKM)
        {
            float fKM = Rtx_TranslateValue.fString2Float(sKM);
            String sVal = null;

            if(getUnit() == UNIT_METRIC)
            {
                sVal = Rtx_TranslateValue.sFloat2String(fKM,1);
            }
            else
            {
                sVal = Rtx_TranslateValue.sFloat2String(fKM*km2mile,1);
            }

            return sVal;
        }

        public static float fTranslat2KM(float fVal)
        {
            float fKM = 0;

            if(getUnit() == UNIT_METRIC)
            {
                fKM = fVal;
            }
            else
            {
                fKM = mile2km * fVal;
            }


            return fKM;
        }
    }

    public static class Height
    {
        public static float getMax()
        {
            float fVal = 0;

            if(getUnit() == UNIT_METRIC)
            {
                fVal = fMetric_Height_Max;
            }
            else
            {
                fVal = fImperial_Height_Max;
            }

            return fVal;
        }

        public static float getMin()
        {
            float fVal = 0;

            if(getUnit() == UNIT_METRIC)
            {
                fVal = fMetric_Height_Min;
            }
            else
            {
                fVal = fImperial_Height_Min;
            }

            return fVal;
        }

        public static boolean checkLimit(float fVal)
        {
            boolean bValid = false;

            if(fVal >= 130f)
            {
                //20181226 規格變更 大於130cm 無條件捨去
                if((Math.floor((double)fVal) >= getMin()) && (Math.floor((double)fVal) <= getMax()))
                {
                    bValid = true;
                }
            }
            else
            {
                //20181226 規格變更 小於130cm 無條件進位
                if((Math.ceil((double)fVal) >= getMin()) && (Math.ceil((double)fVal) <= getMax()))
                {
                    bValid = true;
                }
            }

            return bValid;
        }

        public static float fInch2Cm(int iFt , int iIn)
        {
            float fVal = 0;

            fVal = (iFt * 12 + iIn) * inch2cm;

            return fVal;
        }

        public static String getString(float fval)
        {
            String sdata = "";
            int ift = 0;
            int iinch = 0;

            try{
                if(getUnit() == UNIT_METRIC)
                {
                    //sdata = String.format("%.00f cm", fval);
//                    sdata = Rtx_TranslateValue.sFloat2String(fval,0);
                    //20181226 規格變更 顯示為無條件捨去 範圍為 130 ~ 220 cm
                    if(fval < 130f)
                    {
                        sdata = Rtx_TranslateValue.sFloat2StringInt(130f, 0);
                    }
                    else
                    {
                        sdata = Rtx_TranslateValue.sFloat2StringInt(fval, 0);
                    }
                }
                else
                {
                    fval *= cm2inch;
//                    ift = (int) ((fval + 0.5)/ 12) ;
//                    iinch = (int) (fval + 0.5) % 12 ;
                    //20181226 規格變更 顯示為無條件進位 範圍為 4'3" ~ 7'3"
                    ift = (int) ((fval + 0.999999)/ 12) ;
                    iinch = (int) (fval + 0.999999) % 12 ;

                    if(iinch < 10)
                    {
                        sdata = String.format("%d' %d\"", ift, iinch);
                    }
                    else
                    {
                        sdata = String.format("%d'%d\"", ift, iinch);
                    }
                }
            }
            catch (Exception e)
            {

            }

            return sdata;
        }

        public static int getHeightForInputFormat(float fval)
        {
            int iVal = 0;

            int ift = 0;
            int iinch = 0;

            try{
                if(getUnit() == UNIT_METRIC)
                {
                    iVal = (int)fval;
                    //20181226 規格變更 顯示為無條件捨去 範圍為 130 ~ 220 cm
                    //依據新規格 針對129cm特別處理
                    if(iVal == 129)
                    {
                        iVal = 130;
                    }
                }
                else
                {
                    fval *= cm2inch;
//                    ift = (int) ((fval + 0.5)/ 12) ;
//                    iinch = (int) (fval + 0.5) % 12 ;
                    //20181226 規格變更 顯示為無條件進位 範圍為 4'3" ~ 7'3"
                    ift = (int) ((fval + 0.999999)/ 12) ;
                    iinch = (int) (fval + 0.999999) % 12 ;

                    if(iinch < 10)
                    {
                        iVal = ift * 10 + iinch;
                    }
                    else
                    {
                        iVal = ift * 100 + iinch;
                    }
                }
            }
            catch (Exception e)
            {

            }

            return iVal;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    public static class Age
    {
        public static float getMin()
        {
            float fVal = fage_min;

            return fVal;
        }

        public static float getMax()
        {
            float fVal = fage_max;

            return fVal;
        }

        public static float getDef()
        {
            float fVal = fage_def;

            return fVal;
        }

        public static String getRangeString(Context mContext)
        {
            String sdata = "10-99";

            try
            {
                sdata = String.format("%d-%d", getMin(), getMax());
            }
            catch (Exception e)
            {

            }

            return sdata;
        }

        public static String getMin(Context mContext)
        {
            String sdata = "10";

            try
            {
                sdata = String.format("%d", getMin());
            }
            catch (Exception e)
            {

            }

            return sdata;
        }

        public static String getMax(Context mContext)
        {
            String sdata = "99";

            try
            {
                sdata = String.format("%d", getMax());
            }
            catch (Exception e)
            {

            }

            return sdata;
        }

        public static int getDef(Context mContext)
        {
            int ival;

            ival = CloudDataStruct.CloudData_17.f_get_user_age(mContext);

            return ival;
        }

        public static boolean checkLimit(float fVal)
        {
            boolean bValid = false;

            if((fVal >= getMin()) && (fVal <= getMax()))
            {
                bValid = true;
            }

            return bValid;
        }

    }
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

}
