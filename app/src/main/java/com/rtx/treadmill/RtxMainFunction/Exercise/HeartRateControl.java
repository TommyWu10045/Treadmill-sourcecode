package com.rtx.treadmill.RtxMainFunction.Exercise;

import android.util.Log;

import com.rtx.treadmill.GlobalData.Change_UI_Info;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.UartDevice.UartData;

import static com.rtx.treadmill.GlobalData.ExerciseData.mCaculate_Data;
import static com.rtx.treadmill.GlobalData.ExerciseRunState.PROC_EXERCISE_PHY_GERKIN;

/**
 * 儲存全域設定
 * @author Tom
 *
 */
public class HeartRateControl {
	private static String TAG = "Jerry";
	private static boolean DEBUG = false;

	public static int iHR_status; //0x01 : 未跑步，偵測心跳中； 0x02 : pause，偵測心跳中； 0x10 : 跑步中

	public static float fheart_rate_now ; //※Heart Rate

	public static long lcount_no_heartrate; //未偵測到心跳的累積時間
	public static long lcount_has_heartrate;  //偵測到心跳的累積時間

	//啟動前
	public static long lstart_undetect ; //啟動前，連續多久時間未偵測到心跳 ms
	public static long lstart_detect ; //啟動前，連續多久時間偵測到心跳 ms

	//啟動後
	public static long lruning_undetect ; //啟動後，連續多久時間未偵測到心跳 ms

	//HeartRate Mode 控制變數
	public static int iruning_state; //調整type 0/1 : speed ; 2 : incline
	public static long lstart_delay  ; //啟動後等待多久時間後，開始控制 ms
	public static int ichange_delay ; // 改變前幾秒，顯示提示UI
	public static long lcheck_interval1 ; //啟動後，連續多久時間未偵測到心跳 ms，心跳數顯示---->
	public static long lcheck_interval2 ; //啟動後，連續可偵測時間，調整speed or incline.
	public static long lcheck_interval3 ; //啟動後，連續多久時間未偵測到心跳 ms，warning

	public static long lcount_MHR; //到達MHR心跳的累積時間
	public static long lcheck_MHR_continue ; //大於MHR心跳的累積時間，結束跑步

	public static float fspeed ;	//要改變的speed
	public static float fincline ;	//要改變的incline

	public static int ikey_delay_count = 3;	//update key delay loop
	public static int ikey_speed_count ;	//original speed
	public static int ikey_incline_count ;	//original incline
	public static float fkey_speed ;		//speed from key
	public static float fkey_incline ;		//incline from key

	public static float[] fdiff_val = {20f ,10f, 5f}; //diff bpm 10 , 5
	public static float[] fdiff_speed = {1f, 0.5f, 0.3f};
	public static float[] fdiff_incline = {0.5f, 0.5f};

	public static void clear()
	{
		iHR_status = 0;

		fheart_rate_now = 0;

		lcount_no_heartrate = 0;
		lcount_has_heartrate = 0;

		lstart_undetect = 10000;
		lstart_detect = 3000;

		iruning_state = 0;
		lstart_delay = 0;
		ichange_delay = 5;
		lcheck_interval1 = 10000;
		lcheck_interval2 = 30000 ;
		lcheck_interval3 = 20000 ;

		lcount_MHR = 0;
		lcheck_MHR_continue = 10000;

		fspeed = 0f;
		fincline = 0f;

		ikey_delay_count = 3;
		ikey_speed_count = 4;
		ikey_incline_count = 4;
		fkey_speed = 0;
		fkey_incline = 0;

	}

	//get hr control status
	public static int iHR_Get_status()
	{
		return  iHR_status;
	}

	//set hr control status
	public static void iHR_Set_status(int ival)
	{
		iHR_status = ival;
	}

	//get hr now value
	public static float fHR_Get_now()
	{
		return  fheart_rate_now;
	}

	//set hr now value
	public static void vHR_Set_now(float fval)
	{
		fheart_rate_now = fval;
	}

	//set hr detect time for starting
	public static void v_Set_HR_nodetect_Time(long ltime)
	{
		lcount_no_heartrate = ltime;
	}

	//set detect timeout for befort start
	public static void v_Set_HR_start_undetect_Timeout(long ltime)
	{
		lstart_undetect = ltime;
	}

	//set detect timeout for befort start
	public static void v_Set_HR_start_detect_Timeout(long ltime)
	{
		lstart_detect = ltime;
	}

	//set runing state
	public static void v_Set_HR_runing_state(int ival)
	{
		iruning_state = ival;
	}

	//get runing state
	public static int i_Get_HR_runing_state()
	{
		return iruning_state;
	}

	//set hr detect time for runing
	public static void v_Set_HR_hasdetect_Time(long ltime)
	{
		lcount_has_heartrate = ltime;
	}

	//set detect timeout interval1
	public static void v_Set_HR_interval1_Timeout(long ltime)
	{
		lcheck_interval1 = ltime;
	}

	//set detect timeout interval2
	public static void v_Set_HR_interval2_Timeout(long ltime)
	{
		lcheck_interval2 = ltime;
	}

	//set detect timeout interval3
	public static void v_Set_HR_interval3_Timeout(long ltime)
	{
		lcheck_interval3 = ltime;
	}

	//set detect time for MHR
	public static void v_Set_MHR_detect_Time(long ltime)
	{
		lcount_MHR = ltime;
	}

	//set detect timeout MHR
	public static void v_Set_MHR_Timeout(long ltime)
	{
		lcheck_MHR_continue = ltime;
	}

	//set chanage speed
	public static void v_Set_HR_speed(float fval)
	{
		fspeed = fval;
	}

	//get chanage speed
	public static float v_Get_HR_speed()
	{
		return fspeed;
	}

	//set chanage incline
	public static void v_Set_HR_incline(float fval)
	{
		fincline = fval;
	}

	//get chanage inclne
	public static float v_Get_HR_incline()
	{
		return fincline;
	}


	//check is detect timeout
	public static boolean bHR_detect_timeout(float flimit)
	{
		boolean bret = false;
		float fval = lcount_no_heartrate;

		if(fval > flimit)
		{
			bret = true;
		}

		return bret;
	}

	//check is runing timeout
	public static boolean bHR_runing_timeout(float flimit)
	{
		boolean bret = false;
		float fval = lcount_has_heartrate;

		if(fval > flimit)
		{
			bret = true;
		}

		return bret;
	}

	//check is MHR timeout
	public static boolean bHR_MHR_timeout(float flimit)
	{
		boolean bret = false;
		float fval = lcount_MHR;

		if(fval > flimit)
		{
			bret = true;
		}

		return bret;
	}

	////////////Heart Rate Controll Func///////////
	public static boolean Target_HearRate_mode(long ldiff)
	{
		boolean bret = true;

		if(ldiff <= 0) {
			return bret;
		}

		switch (iHR_Get_status())
		{
			case 0x01 :
				bHeartRate_Start_check();
				break;
			case 0x02 :
				vHeartRate_Start_Timeout();
				break;
			case 0x10 :
				HeartRate_time_first(ldiff);
				bret = v_Step_time_check(ldiff);
				break;
			case 0x11 :
				HeartRate_time_check(ldiff);
				bret = v_Step_time_check(ldiff);
				break;
			default:
				break;
		}

		return bret;
	}

	public static boolean bHeartRate_Start_check()
	{
		boolean bret = true;

		//開始時,檢查心跳的功能
//		if(ExerciseGenfunc.bHR_is_void(ExerciseData.fHR_Get())) {
//			if(bHR_runing_timeout(lstart_detect)) {
				iHR_Set_status(0x10);
				UartData.vUartCmd_61(20, "3");

				if (ExerciseData.list_real_setting.size() > ExerciseData.ilist_count)
				{
					ExerciseData.DEVICE_COMMAND Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
					if (Uart_command != null)
					{
						UartData.set_uart_speed(Uart_command.fspeed);
						//if(ExerciseData.E_mode != PROC_EXERCISE_PHY_GERKIN) UartData.set_uart_speed(3.0f);
						UartData.set_uart_incline(Uart_command.fincline);
					}
				}
//			}
//		}
//		else
//		{
//			if(bHR_detect_timeout(lstart_undetect))
//			{
//				iHR_Set_status(0x02);
//				v_Set_HR_nodetect_Time(0);
//			}
//		}

		return bret;
	}

	public static void vHeartRate_Start_Timeout()
	{
		Exercisefunc.v_set_Exercise_Status(0x10); //No Heart Rate Pause
	}

	public static void vHeartRate_Start_Timeout_Stop()
	{
		Exercisefunc.v_set_Exercise_Status(0x00); //No Heart Rate Complete
	}

	private static boolean HeartRate_warning_timeCheck()
	{
		boolean bret = false;

		if(DEBUG) Log.e(TAG, "===lcount_no_heartrate===" + lcount_no_heartrate);
		if(bHR_detect_timeout(lcheck_interval3))
		{
			Change_UI_Info.v_set_stage(Change_UI_Info.istate_sinfo);
			Change_UI_Info.sinfomation = LanguageData.s_get_string(GlobalData.global_context, R.string.no_heart_rate_detected);
			Change_UI_Info.iinfomation_flash = EngSetting.DEF_SEC_COUNT;
			bret = true;
		}
		else
		{
			Change_UI_Info.v_clear_stage(Change_UI_Info.istate_sinfo);
		}

		return bret;
	}

	public static boolean HeartRate_time_first(long ldiff)
	{
		boolean bret = false;
		long ldata;

		if(ExerciseData.mCaculate_Data.ltotal_time > lstart_delay)
		{
			iHR_Set_status(0x11);
		}

		HeartRate_warning_timeCheck();

		return bret;
	}

	public static boolean HeartRate_time_check(long ldiff)
	{
		boolean bret = false;
		long ldata;
		float fval;
		ExerciseData.DEVICE_COMMAND Uart_command = null;
		int istate;

		if(HeartRate_warning_timeCheck())
		{

		}
		else if(bHR_runing_timeout(lcheck_interval2))
		{
			v_Set_HR_hasdetect_Time(0);
			fval = HeartRateControl_diff();
			Uart_command = S_Get_Uart_command();

			if(Uart_command != null) {
				istate = i_Get_HR_runing_state();
				switch (istate) {
					case 0x00:
					case 0x01:
						//if(b_Speed_Change(fval, Uart_command.fspeed))
						if(b_Incline_Change(fval, Uart_command.fincline))
						{
							//istate += 1;
							v_Set_HR_runing_state(istate);
						}
						//}
						break;
					//case 0x02:
					//	if(b_Incline_Change(fval, Uart_command.fincline))
					//	{
					//		v_Set_HR_runing_state(0x00);
					//	}
					//	break;
					default:
						break;
				}
			}

		}

		return bret;
	}

	//Target_time_check
	//return false : DEVICE_COMMAND is null
	public static boolean  v_Step_time_check(long ldiff)
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

			if(Uart_command.bcal)
			{
				Exercisefunc.v_Total_time_add(ldiff);

				Exercisefunc.v_calculate_info(ldiff);
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
					Exercisefunc.v_Reset_Current_time(itarget_time, Uart_command.bcal);
				}
				else
				{
					bret = false;
				}
			}
		}

		return bret;
	}

	////////////Heart Rate func///////////
	public static void HeartRate_from_Uart(float fval) {

		if (ExerciseGenfunc.bHR_is_void(fval)) {
			ExerciseData.vHR_Set(fval);
		}

		vHR_Set_now(fval);

		return;
	}

	public static void HeartRate_update_Curr(long ldiff) {
		long ldata ;

		if(ldiff <= 0)
		{
			return;
		}

		if (ExerciseGenfunc.bHR_is_void(fHR_Get_now())) {
			v_Set_HR_nodetect_Time(0); //clear no detect count;

			ldata = ldiff + lcount_has_heartrate;
			v_Set_HR_hasdetect_Time(ldata);
		}
		else
		{
			v_Set_HR_hasdetect_Time(0);//clear detect count;

			ldata = ldiff + lcount_no_heartrate;
			v_Set_HR_nodetect_Time(ldata);
		}

		if(bHR_detect_timeout(lcheck_interval1))
		{
			if(fHR_Get_now() == 1) {
				ExerciseData.vHR_Set(1);
			}
			else
			{
				ExerciseData.vHR_Set(0);
			}
		}

		return;
	}

	private static float HeartRateControl_diff()
	{
		float fval;

		fval = ExerciseGenfunc.f_get_target_heart_rate() - ExerciseGenfunc.f_get_curr_heartrate();

		return fval;
	}

	private static boolean b_Speed_Change(float fdiff, float fdata)
	{
		boolean bret = false;

		if(fdiff >= fdiff_val[0])
		{
			fspeed = fdata + fdiff_speed[0];
			bret = true;
		}
		else if(fdiff >= fdiff_val[1])
		{
			fspeed = fdata + fdiff_speed[1];
			bret = true;
		}
		else if(fdiff >= fdiff_val[2])
		{
			fspeed = fdata + fdiff_speed[2];
			bret = true;
		}
		else if(fdiff <= -fdiff_val[0])
		{
			fspeed = fdata - fdiff_speed[0];
			bret = true;
		}
		else if(fdiff <= -fdiff_val[1])
		{
			fspeed = fdata - fdiff_speed[1];
			bret = true;
		}
		else if(fdiff <= -fdiff_val[2])
		{
			fspeed = fdata - fdiff_speed[2];
			bret = true;
		}

		if(fspeed >= EngSetting.f_Get_Max_Speed())
		{
			if(fdata == EngSetting.f_Get_Max_Speed())
			{
				bret = false;
			}
		}
		else if(fspeed <= EngSetting.f_Get_Min_Speed())
		{
			if(fdata == EngSetting.f_Get_Min_Speed())
			{
				bret = false;
			}
		}

		if(bret)
		{
			if(fspeed < EngSetting.f_Get_Min_Speed())
			{
				fspeed = EngSetting.f_Get_Min_Speed();
			}

			if(fspeed > EngSetting.f_Get_Max_Speed())
			{
				fspeed = EngSetting.f_Get_Max_Speed();
			}
			Change_UI_Info.v_set_stage(Change_UI_Info.istate_speed);
			Change_UI_Info.fcurrent[0] = fdata;
			Change_UI_Info.fnext[0] = fspeed;
			Change_UI_Info.i_countdown = ichange_delay;
		}

		return bret;
	}

	private static boolean b_Incline_Change(float fdiff, float fdata)
	{
		boolean bret = false;

		if(fdiff >= fdiff_val[0])
		{
			fincline = fdata + fdiff_incline[0];
			bret = true;
		}
		else if(fdiff <= -fdiff_val[0])
		{
			fincline = fdata - fdiff_incline[0];
			bret = true;
		}
		else if(fdiff >= fdiff_val[1])
		{
			fincline = fdata + fdiff_incline[0];
			bret = true;
		}
		else if(fdiff <= -fdiff_val[1])
		{
			fincline = fdata - fdiff_incline[0];
			bret = true;
		}
		else if(fdiff >= fdiff_val[2])
		{
			fincline = fdata + fdiff_incline[1];
			bret = true;
		}
		else if(fdiff <= -fdiff_val[2])
		{
			fincline = fdata - fdiff_incline[1];
			bret = true;
		}


		if(fincline >= EngSetting.f_Get_Max_Incline())
		{
			if(fdata == EngSetting.f_Get_Max_Incline())
			{
				bret = false;
			}
		}
		else if(fincline <= EngSetting.f_Get_Min_Incline())
		{
			if(fdata == EngSetting.f_Get_Min_Incline())
			{
				bret = false;
			}
		}

		if(bret)
		{
			if(fincline < EngSetting.f_Get_Min_Incline())
			{
				fincline = EngSetting.f_Get_Min_Incline();
			}

			if(fincline > EngSetting.f_Get_Max_Incline())
			{
				fincline = EngSetting.f_Get_Max_Incline();
			}
			Change_UI_Info.v_set_stage(Change_UI_Info.istate_incline);
			Change_UI_Info.fcurrent[1] = fdata;
			Change_UI_Info.fnext[1] = fincline;
			Change_UI_Info.i_countdown = ichange_delay;
		}

		return bret;
	}

	private static ExerciseData.DEVICE_COMMAND S_Get_Uart_command()
	{
		ExerciseData.DEVICE_COMMAND Uart_command  = null;

		if(ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
			Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
		}

		return Uart_command;

	}

}

