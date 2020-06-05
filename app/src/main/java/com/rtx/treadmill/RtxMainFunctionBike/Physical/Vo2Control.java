package com.rtx.treadmill.RtxMainFunctionBike.Physical;

import com.rtx.treadmill.GlobalData.Change_UI_Info;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.Exercisefunc;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.HeartRateControl;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.WattControl;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

import static com.rtx.treadmill.GlobalData.ExerciseData.mCaculate_Data;

/**
 * 儲存全域設定
 * @author Tom
 *
 */
public class Vo2Control {
	private static String TAG = "Jerry";
	private static boolean DEBUG = false;

    //已啟動
	public static long lcheck_target_watt = 60000; //計算下一個step，取上一個Step最後多久的時間值
	public static long lcalculate_hr_avg = 120000; //計算成績取最後二個step，最後時間多久的心跳平均值

	//fvo2
	public static float[][] fvo2 = {//step, time, watt < 80, watt 80~89, watt 90~100, watt > 100
			{ 1 , 180 , 55f , 55f , 55f , 55f },
			{ 2 , 180 ,125f ,100f , 75f , 55f },
			{ 3 , 180 ,150f ,125f ,100f , 75f },
			{ 4 , 180 ,175f ,150f ,125f ,100f },
			{ 5 , 180 ,200f ,175f ,150f ,125f },
			{ 6 , 180 ,225f ,200f ,175f ,150f },
			{ 7 , 180 ,250f ,225f ,200f ,175f },
			{ 8 , 180 ,250f ,250f ,225f ,200f },
			{ 9 , 180 ,250f ,250f ,250f ,225f },
			{10 , 180 ,250f ,250f ,250f ,250f }
	};

    ////////////Physical VO2 mode/////////////
    public static boolean Physical_Gerkin_Start(long ldiff)
    {
        boolean bret = true;

        if(ldiff <= 0) {
            return bret;
        }

        switch (HeartRateControl.iHR_Get_status())
        {
            case 0x01 :
                HeartRateControl.bHeartRate_Start_check();
                break;
            case 0x02 :
                HeartRateControl.vHeartRate_Start_Timeout();
                break;
            case 0x10 :
            case 0x11 :
                if(bCheck_Vo2_Status(ldiff))
                {
                    bret = Target_check_Vo2(ldiff);
                }
                break;
            default:
                break;
        }

        return bret;
    }

    private static boolean bCheck_Vo2_Status(long ldiff)
    {
        boolean bret = false;

        if(!bHeartRate_nodetect_timeout(ldiff))
        {
            if(bCheck_Watt_OutOfRange(ldiff))
            {
                if(!bCheck_Watt_OutofRange_timeout(0, WattControl.lcheck_interval2))
                {
                    bCheck_Watt_OutofRange_timeout(1,  WattControl.lcheck_interval1);
                    bret = true;
                }
            }
            else
            {
                bret = true;
            }
        }
        else
        {
            bret = true;
        }

        return bret;
    }

    private static boolean bHeartRate_nodetect_timeout(long ldiff)
    {
        boolean bret = false;

//        if(HeartRateControl.bHR_detect_timeout(HeartRateControl.lcheck_interval2))
//        {
//            Exercisefunc.v_set_Exercise_Status(0x01); //No Heart Rate detect
//            bret = true;
//        }

        return bret;
    }

    private static boolean bCheck_Watt_OutOfRange(long ldiff)
    {
        boolean bret = false;
        float fval;
        long ldata;

        //Check watt is out of range
        fval = Exercisefunc.f_watt_calculate();


            if (WattControl.b_watt_under_range(fval, WattControl.fdiff_val[0])) {
                WattControl.v_Set_watt_over_range_Time(0); //clear over the range time;

                ldata = ldiff + WattControl.lcount_watt_out_under;
                WattControl.v_Set_watt_under_range_Time(ldata);
                bret = true;
            } else {
                WattControl.v_Set_watt_under_range_Time(0); //clear under the range time;

                if (WattControl.b_watt_over_range(fval, WattControl.fdiff_val[0])) {
                    ldata = ldiff + WattControl.lcount_watt_out_over;
                    WattControl.v_Set_watt_over_range_Time(ldata);
                    bret = true;
                } else {
                    WattControl.v_Set_watt_over_range_Time(0); //clear over the range time;
                }
            }

//        }

        return bret;
    }

    private static boolean bCheck_Watt_OutofRange_timeout(int imode, long llimit)
    {
        boolean bret = false;
        int ival = 0;
        String sdata = "";

        if (WattControl.bWatt_Under_Range_timeout(llimit)) {
            bret = true;
            sdata = LanguageData.s_get_string(GlobalData.global_context, R.string.vo2_faster_to_keep)
                    + " " + Rtx_TranslateValue.sFloat2String(WattControl.ftarget_watt, 0) + " W";
        } else if (WattControl.bWatt_Over_Range_timeout(llimit)) {
            bret = true;
            sdata = LanguageData.s_get_string(GlobalData.global_context, R.string.vo2_slowly_to_keep)
                    + " " + Rtx_TranslateValue.sFloat2String(WattControl.ftarget_watt, 0) + " W";
        }

        if(bret) {
            if (imode == 0) {
                ival = 0x01;
                Exercisefunc.v_set_Exercise_Status(ival);
            } else if (imode == 1) {
                Change_UI_Info.v_set_stage(Change_UI_Info.istate_sinfo);
                Change_UI_Info.sinfomation = sdata;
                Change_UI_Info.iinfomation_flash = EngSetting.DEF_SEC_COUNT;
            }
        }

        return bret;
    }

    private static boolean Target_check_Vo2(long ldiff)
    {
        boolean bret = true;

        if(ldiff > 0)
        {
            bret = Target_time_Vo2(ldiff);
        }

        return bret;
    }

    //return false : DEVICE_COMMAND is null
    private static boolean Target_time_Vo2(long ldiff)
    {
        boolean bret = false;
        int itarget_time ;

        ExerciseData.DEVICE_COMMAND Uart_command = null;
        if(ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
            Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
        }

        if(Uart_command != null)
        {
            Exercisefunc.v_Current_time_add(ldiff);

            itarget_time = Uart_command.isec;

            WattControl.Physical_VO2_Add_list((float) ExerciseData.ilist_count, (float) ldiff, Uart_command.fheart_rate, Uart_command.frpm, Uart_command.fincline);

            if(Uart_command.bcal)
            {
                Exercisefunc.v_Total_time_add(ldiff);

                Exercisefunc.v_calculate_info(ldiff);

                WattControl.v_Calculate_Level_Watt(-1);
            }
            else
            {
                Exercisefunc.v_Total_time_Eng_add(ldiff); //Device runing Time;
                Exercisefunc.v_calculate_eng_info(ldiff);
            }


            bret = true;

            if(mCaculate_Data.icurrent_time >= itarget_time)
            {
                ExerciseData.ilist_count++;

                if(ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
                    Exercisefunc.v_Reset_Current_time(ExerciseData.iStep_time);

                    v_Calculate_Watt_Target(ExerciseData.ilist_count, Vo2Control.lcheck_target_watt);
                }
                else
                {
                    bret = false;
                }
            }
        }

        return bret;
    }

    private static void v_Calculate_Watt_Target(int istep, float ftime_range)
    {
        int iLoop;
        float fhr_avg ;
        float frpm_avg ;
        float[] farray_hr = { 80f, 90f, 100f, 999f };
        float[] farray_watt = new float[2];
        int istip_prev;
        float[] farray_watt_prev;
        float flvl;

        farray_watt[0] = (float)istep ;

        if(istep > 0) {
            istip_prev = istep - 1;
            if(istip_prev < WattControl.fvo2_target_watt.size()) {
                farray_watt_prev = WattControl.fvo2_target_watt.get(istip_prev);
                if(farray_watt_prev != null) {
                    farray_watt[1] = farray_watt_prev[1];

                    fhr_avg = WattControl.v_Calculate_Avg_HR(istip_prev, ftime_range);
                    frpm_avg = WattControl.v_Calculate_Avg_RPM(istip_prev, ftime_range);

                    if(ExerciseGenfunc.bHR_is_void(fhr_avg))
                    {
                        for(iLoop = 0; iLoop < farray_hr.length; iLoop++)
                        {
                            if(fhr_avg < farray_hr[iLoop])
                            {
                                farray_watt[1] = Vo2Control.fvo2[istep][iLoop + 2];
                                break;
                            }
                        }
                    }

                    flvl = Exercisefunc.f_level_calculate(farray_watt[1], frpm_avg);
                    WattControl.v_Set_Watt_target(farray_watt[1]);
                    if(flvl >= EngSetting.f_Get_Min_level() && flvl <= EngSetting.f_Get_Max_level()) {
                        WattControl.v_check_motor_change(0, flvl);
                    }
                }
                else
                {
                    farray_watt[1] = 55f;
                }
            }
            else
            {
                farray_watt[1] = 55f;
            }
        }
        else
        {
            farray_watt[1] = 55f;
        }

        WattControl.fvo2_target_watt.add(farray_watt);

//        for (iLoop = 0 ; iLoop < WattControl.fvo2_target_watt.size(); iLoop++)
//        {
//            farray_watt_prev = WattControl.fvo2_target_watt.get(iLoop);
//            Log.e(TAG, "   fvo2_target_watt[" + iLoop + "]=" + farray_watt_prev[0] + ", " + farray_watt_prev[1]);
//        }

        return ;
    }


}

