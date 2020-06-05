package com.rtx.treadmill.RtxMainFunctionBike.Exercise;

import com.rtx.treadmill.GlobalData.Change_UI_Info;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;

import java.util.ArrayList;

import static com.rtx.treadmill.GlobalData.ExerciseData.mCaculate_Data;

/**
 * 儲存全域設定
 * @author Tom
 *
 */
public class WattControl {
	private static String TAG = "Jerry";
	private static boolean DEBUG = false;

	public static int icount;

	public static int iWATT_status; //0x00 : 不計數 ； 0x01 : 偵測中

	public static long lcount_nowatt; //未偵測到watt的累積時間，即rpm為0
	public static long lcount_watt;  //偵測到watt的累積時間
	public static long lcount_watt_out_under;  //偵測到watt小於範圍的累積時間
	public static long lcount_watt_out_over;  //偵測到watt大於範圍的累積時間

	public static long lstart_delay  ; //啟動後等待多久時間後，開始控制level, ms
	public static int ichange_delay ; // 改變前幾秒，顯示提示UI
	public static long lcheck_interval1 ; //連續多久時間未偵測到watt, ms，alarm or pause
    public static long lcheck_interval2 ; //連續多久時間未偵測到watt, ms，Stop

    public static long lcheck_interval3 ; //連續可偵測時間，調整level
	public static long lcheck_last_rpm ; //連續可偵測時間，最後時間Avg RPM，調整level

	public static float flevel_constwatt ;	//contantwatt level watt
    public static float ftarget_watt ;		//target watt

	public static float[] fdiff_val = {20f, 5f}; //diff watt for
	public static float[] fdiff_level = {1f, 0.1f};

	//step, time , heartrate, RPM, level
	public static ArrayList<float[]> fvo2_list = new ArrayList<>();
	//step, target_watt
	public static ArrayList<float[]> fvo2_target_watt = new ArrayList<>();

	public static void clear()
	{
		icount = 0;

		iWATT_status = 0;

		lcount_nowatt = 0;
		lcount_watt = 0;
		lcount_watt_out_under = 0;
		lcount_watt_out_over = 0;

		lstart_delay = 10000;
		ichange_delay = 3;
		lcheck_interval1 = 10000;
		lcheck_interval2 = 20000 ;
        lcheck_interval3 = 2000 ;
		lcheck_last_rpm = 1000;

		ftarget_watt = 55f;
		fdiff_val[0] = 20f;
		fdiff_level[0] = 1f;
		fdiff_val[1] = 5f;
		fdiff_level[1] = 0.1f;

		fvo2_list.clear();
		fvo2_target_watt.clear();

	}

	//get icount
	public static int iGet_count_val()
	{
		return  icount;
	}

	//get status
	public static void icount_add()
	{
		icount++;
	}

	//get Watt status
	public static int iGet_Watt_Status()
	{
		return iWATT_status;
	}

	//set Watt status
	public static void v_Set_Watt_Status(int istatus)
	{
		iWATT_status = istatus;
	}

	//set no watt detect time
	public static void v_Set_nowatt_Time(long ltime)
	{
		lcount_nowatt = ltime;
	}

	//set watt detect time
	public static void v_Set_haswatt_Time(long ltime)
	{
		lcount_watt = ltime;
	}

	//set watt under the range time
	public static void v_Set_watt_under_range_Time(long ltime)
	{
		lcount_watt_out_under = ltime;
	}

	//set watt over the range time
	public static void v_Set_watt_over_range_Time(long ltime)
	{
		lcount_watt_out_over = ltime;
	}

	//set lstart_delay
	public static void v_Set_Watt_lstart_delay(long ltime)
	{
		lstart_delay = ltime;
	}

	//set ichange_delay
	public static void v_Set_Watt_ichange_delay(int itime)
	{
		ichange_delay = itime;
	}

	//set interval1
	public static void v_Set_Watt_interval1_Timeout(long ltime)
	{
		lcheck_interval1 = ltime;
	}

	//set interval2
	public static void v_Set_Watt_interval2_Timeout(long ltime)
	{
		lcheck_interval2 = ltime;
	}

	//set interval3
	public static void v_Set_Watt_interval3_Timeout(long ltime)
	{
		lcheck_interval3 = ltime;
	}

	//set interval3 last rpm time
	public static void v_Set_Watt_interval3_RPM_time(long ltime)
	{
		lcheck_last_rpm = ltime;
	}

	//set ConstantWatt_level
	public static void v_Set_ConstantWatt_level(float fval)
	{
		flevel_constwatt = fval;
	}

	//set ftarget_watt
	public static void v_Set_Watt_target(float fval)
	{
		ftarget_watt = fval;
	}

	//set target_watt different
	public static void v_Set_Watt_target_diff(float[] fval)
	{
		fdiff_val[0] = fval[0];
		fdiff_val[1] = fval[1];
	}

	//set target_watt level step
	public static void v_Set_Watt_target_level(float[] fval)
	{
		fdiff_level[0] = fval[0];
		fdiff_level[1] = fval[1];
	}


	///////////////////////////////////////////////////////////////
	public static void Watt_update_Curr(long ldiff) {
		long ldata ;
		float fval;

		if(ldiff <= 0)
		{
			return;
		}

		fval = Exercisefunc.f_watt_calculate();

		if (fval == 0) {  // RPM = 0
			v_Set_haswatt_Time(0); //clear watt has detect time;

			ldata = ldiff + lcount_nowatt;
			v_Set_nowatt_Time(ldata);
		}
		else
		{
			v_Set_nowatt_Time(0);//clear no watt time;

			ldata = ldiff + lcount_watt;
			v_Set_haswatt_Time(ldata);
		}

		return;
	}

	public static boolean b_watt_under_range(float fwatt, float flimit)
	{
		boolean bret = false;
		float ftarget, fval;

		ftarget = ftarget_watt;
		fval = fwatt ;

		if(fval < (ftarget - flimit))
		{
			bret = true;
		}

		return bret;
	}

	public static boolean b_watt_underequal_range(float fwatt, float flimit)
	{
		boolean bret = false;
		float ftarget, fval;

		ftarget = ftarget_watt;
		fval = fwatt ;

		if(fval <= (ftarget - flimit))
		{
			bret = true;
		}

		return bret;
	}

	public static boolean b_watt_over_range(float fwatt, float flimit)
	{
		boolean bret = false;
		float ftarget, fval;

		ftarget = ftarget_watt;
		fval = fwatt ;

		if(fval > (ftarget + flimit))
		{
			bret = true;
		}

		return bret;
	}

	public static boolean b_watt_overequal_range(float fwatt, float flimit)
	{
		boolean bret = false;
		float ftarget, fval;

		ftarget = ftarget_watt;
		fval = fwatt ;

		if(fval >= (ftarget + flimit))
		{
			bret = true;
		}

		return bret;
	}


	//check is nowatt timeout
	public static boolean bWatt_nowatt_timeout(float flimit)
	{
		boolean bret = false;
		float fval = lcount_nowatt;

		if(fval > flimit)
		{
			v_Set_nowatt_Time(0);
			bret = true;
		}

		return bret;
	}

	//check is detecting watt timeout
	public static boolean bWatt_detecting_timeout(float flimit)
	{
		boolean bret = false;
		float fval = lcount_watt;

		if(fval > flimit)
		{
			v_Set_haswatt_Time(0); //clear watt has detect time;
			bret = true;
		}

		return bret;
	}

	//check is Watt under range timeout
	public static boolean bWatt_Under_Range_timeout(float flimit)
	{
		boolean bret = false;
		float fval = lcount_watt_out_under;

		if(fval > flimit)
		{
			bret = true;
		}

		return bret;
	}

	//check is Watt over range timeout
	public static boolean bWatt_Over_Range_timeout(float flimit)
	{
		boolean bret = false;
		float fval = lcount_watt_out_over;

		if(fval > flimit)
		{
			bret = true;
		}

		return bret;
	}

	//check is Watt out of range timeout
	public static boolean bWatt_Out_Range_timeout(float flimit)
	{
		boolean bret = false;

		if(bWatt_Under_Range_timeout(flimit))
		{
			bret = true;
		}
		else if(bWatt_Over_Range_timeout(flimit))
		{
			bret = true;
		}

		return bret;
	}

	///////////////////////////////////////////

	//Add every loop info to VO2 list
	public static void Physical_VO2_Add_list(float fstep, float ftime, float fheart, float frpm, float flevel)
	{
		float[] farray = new float[5];

		farray[0] = fstep;
		farray[1] = ftime;
		farray[2] = fheart;
		farray[3] = frpm;
		farray[4] = flevel;

		WattControl.fvo2_list.add(farray);
	}

	//1min = 240 筆
	public static void Check_VO2_list_size(int isize)
	{
		if(fvo2_list.size() > isize)
		{
			fvo2_list.remove(0);
		}
	}

	//Get ftime_range 時間內的心跳平均值; istep 不為-1時，指定那一個Step
	public static float v_Calculate_Avg_HR(int istep, float ftime_range)
	{
		int imax ;
		int iLoop;
		float ftime = 0;
		float fhr = 0;
		float[] farray;
		int icount = 0;
		float fhr_avg = 0;
		int imode = 2;//HeartRate

		if(WattControl.fvo2_list.size() > 0)
		{
			imax = WattControl.fvo2_list.size();
			for (iLoop = imax - 1; iLoop >= 0; iLoop--)
			{
				farray = WattControl.fvo2_list.get(iLoop);
				if(farray != null)
				{
					if(ExerciseGenfunc.bHR_is_void(farray[imode]))
					{
						if(istep == -1 || farray[0] == istep)
						{
							icount++;
							ftime += farray[1];
							fhr += farray[imode];
							if(ftime >= ftime_range)
							{
								break;
							}
						}
						else
						{
							break;
						}
					}

				}
			}
		}

		if(icount > 0 && ftime > 0)
		{
			fhr_avg = fhr / icount;
		}

		return fhr_avg;
	}

	//Get ftime_range 時間內的RPM平均值; istep 不為-1時，指定那一個Step
	public static float v_Calculate_Avg_RPM(int istep, float ftime_range)
	{
		int imax ;
		int iLoop;
		float ftime = 0;
		float fhr = 0;
		float[] farray;
		int icount = 0;
		float fhr_avg = 0;
		int imode = 3;//RPM

		if(WattControl.fvo2_list.size() > 0) {
			imax = WattControl.fvo2_list.size();
			for (iLoop = imax - 1; iLoop >= 0; iLoop--) {
				farray = WattControl.fvo2_list.get(iLoop);
				if (farray != null) {
					if (farray[imode] > 0) {
						if (istep == -1 || farray[0] == istep) {
							icount++;
							ftime += farray[1];
							fhr += farray[imode];
							if (ftime >= ftime_range) {
								break;
							}
						} else {
							break;
						}
					}

				}
			}
		}

		if(icount > 0 && ftime > 0)
		{
			fhr_avg = fhr / icount;
		}

		return fhr_avg;
	}

	public static void v_Calculate_Level()
	{
        float fval = 0;
        float fwatt;
		float flvl;
        int ilvl0, ilvl1;
        ExerciseData.DEVICE_COMMAND Uart_command_Curr = null;

        if(WattControl.bWatt_detecting_timeout(WattControl.lcheck_interval3)) {
			fwatt = Exercisefunc.f_watt_calculate(-1, lcheck_last_rpm);
			//fwatt = Exercisefunc.f_watt_calculate();
            if (b_watt_over_range(fwatt, WattControl.fdiff_val[0])) {
                fval = -WattControl.fdiff_level[0];
            }
            else if (b_watt_overequal_range(fwatt, WattControl.fdiff_val[1])) {
                fval = -WattControl.fdiff_level[1];
            }
            else if (b_watt_under_range(fwatt, WattControl.fdiff_val[0])) {
                fval = WattControl.fdiff_level[0];
            }
            else if (b_watt_underequal_range(fwatt, WattControl.fdiff_val[1])) {
                fval = WattControl.fdiff_level[1];
            }

			ilvl0 = (int)flevel_constwatt;

			if(fval != 0)
            {
                flevel_constwatt += fval;
            }

			ilvl1 = (int)flevel_constwatt;

            if(ilvl0 != ilvl1)
            {
                if (ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
                    Uart_command_Curr = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
                    if (Uart_command_Curr != null)
                    {
                        flvl = ilvl1;
                        if(flvl >= EngSetting.f_Get_Min_level() && flvl <= EngSetting.f_Get_Max_level()) {
                            v_check_motor_change(0, flvl);
                        }
                    }
                }
            }

		}

		return ;
	}

	public static void v_Calculate_Level_Watt(int istep)
	{
		float frpm;
		float ftarget = ftarget_watt;
		float flvl;

		if(WattControl.bWatt_detecting_timeout(WattControl.lcheck_interval3)) {

			frpm = WattControl.v_Calculate_Avg_RPM(istep, lcheck_last_rpm);
			flvl = Exercisefunc.f_level_calculate(ftarget, frpm);

			if(flvl >= EngSetting.f_Get_Min_level() && flvl <= EngSetting.f_Get_Max_level()) {
				v_check_motor_change(0, flvl);
			}
		}

		return ;
	}

	public static void v_check_motor_change(int idelay, float fnext){
		ExerciseData.DEVICE_COMMAND Uart_command_Curr = null;

		if(Change_UI_Info.i_get_stage() == 0) {
			if (ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
				Uart_command_Curr = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
				v_Set_haswatt_Time(0); //clear watt has detect time;
				v_Set_nowatt_Time(0);//clear no watt time;

				if (Uart_command_Curr != null)
                {
                    if(Uart_command_Curr.fincline != fnext)
                    {
//                        Log.e(TAG, "==========v_check_motor_change=========flevel=" + fnext);

                        v_Set_watt_under_range_Time(0); //clear under the range time;
                        v_Set_watt_over_range_Time(0); //clear over the range time;

                        Change_UI_Info.v_set_stage(Change_UI_Info.istate_incline);
                        Change_UI_Info.fcurrent[1] = Uart_command_Curr.fincline;
                        Change_UI_Info.fnext[1] = fnext;
                        Change_UI_Info.i_countdown = idelay;
                        Uart_command_Curr.fincline = fnext;
                    }
                }
			}
		}
	}

	////////////Contant Watt Controll mode/////////////
	public static boolean Constant_Watt_Controll(long ldiff)
	{
		boolean bret = true;

		if(ldiff <= 0) {
			return bret;
		}

		switch (iGet_Watt_Status())
		{
			case 0x01 :
				if(bCheck_Watt_Status(ldiff))
				{
					bret = Target_time_Watt(ldiff);
				}
				break;
			default:
				break;
		}

		return bret;
	}

	private static boolean bCheck_Watt_Status(long ldiff)
	{
		boolean bret;

		if (bWatt_nowatt_timeout(lcheck_interval1)) {
			Exercisefunc.v_set_Exercise_Status(0x11); //no Watt detect go to pause
			bret = false;
		}
		else {
			bret = true;
		}

		return bret;
	}

	//return false : DEVICE_COMMAND is null
	private static boolean Target_time_Watt(long ldiff)
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

			Physical_VO2_Add_list((float) ExerciseData.ilist_count, (float) ldiff, Uart_command.fheart_rate, Uart_command.frpm, Uart_command.fincline);
			Check_VO2_list_size(2400);

			if(Uart_command.bcal)
			{
				Exercisefunc.v_Total_time_add(ldiff);

				Exercisefunc.v_calculate_info(ldiff);

				v_Calculate_Level();
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
				}
				else
				{
					bret = false;
				}
			}
		}

		return bret;
	}

}

