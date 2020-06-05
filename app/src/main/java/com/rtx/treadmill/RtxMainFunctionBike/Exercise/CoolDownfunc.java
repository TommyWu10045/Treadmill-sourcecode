package com.rtx.treadmill.RtxMainFunctionBike.Exercise;

import android.content.Context;
import android.util.Log;

import com.rtx.treadmill.GlobalData.Calc_Data;
import com.rtx.treadmill.GlobalData.Change_UI_Info;
import com.rtx.treadmill.GlobalData.CoolDownData;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.UartDevice.UartData;
import com.rtx.treadmill.UartDevice.Uartcommand;

import static com.rtx.treadmill.GlobalData.CoolDownData.mCaculate_Data;

/**
 * 儲存全域設定
 * @author Tom
 *
 */
public class CoolDownfunc {
	private static String TAG = "Jerry";
	private static boolean DEBUG = false;

	private Context mContext;
	private MainActivity mMainActivity;
	private Uartcommand mUartcmd ;

	private boolean bCoolDownFinish = true;

	public CoolDownfunc(Context context, MainActivity mMainActivity) {
		super();

		this.mContext = context;
		this.mMainActivity = mMainActivity;
		this.mUartcmd = mMainActivity.mUartcmd;
	}


	//f_Calculate_Data
	private void f_Calculate_Data(Calc_Type_Status status, float finput, long ltime)
	{
		Calc_Data sData;

		if(finput <= 0)
		{
			return;
		}

		switch (status) {
			case CALORIES:
				sData = mCaculate_Data.scalories;
				sData.fcurr = finput * 1000 / ltime;// 每秒卡洛里
				ExerciseGenfunc.f_Calculate_total(sData, finput);
				ExerciseGenfunc.f_Calculate_Max(sData, sData.fcurr);
				ExerciseGenfunc.f_Calculate_Min(sData, sData.fcurr);
				break;
			case DISTANCE:
				sData = mCaculate_Data.sdistance;
				sData.fcurr = finput * 1000 / ltime; // 每秒公尺
				ExerciseGenfunc.f_Calculate_total(sData, finput);
				ExerciseGenfunc.f_Calculate_Max(sData, sData.fcurr);
				ExerciseGenfunc.f_Calculate_Min(sData, sData.fcurr);
				break;
			case HEARTRATE:
				sData = mCaculate_Data.sheart_rate;
				if(finput >= 40 && finput <= 220) { //合法心跳
					sData.fcurr = finput;
					ExerciseGenfunc.f_Calculate_total(sData, finput);
					ExerciseGenfunc.f_Calculate_average(sData, finput);
					ExerciseGenfunc.f_Calculate_Max(sData, finput);
				}
				break;
			case PACE:
				sData = mCaculate_Data.space;
				sData.fcurr = finput;
				ExerciseGenfunc.f_Calculate_total(sData, finput);
				ExerciseGenfunc.f_Calculate_average(sData, finput);
				ExerciseGenfunc.f_Calculate_Min(sData, finput);
				ExerciseGenfunc.f_Calculate_Max(sData, finput);
				break;
			case WATT:
				sData = mCaculate_Data.swatt;
				sData.fcurr = finput;
				ExerciseGenfunc.f_Calculate_total(sData, finput);
				ExerciseGenfunc.f_Calculate_average(sData, finput);
				ExerciseGenfunc.f_Calculate_Min(sData, finput);
				ExerciseGenfunc.f_Calculate_Max(sData, finput);
				break;
			case RPM:
				break;
			default:
				break;
		}

	}

	private enum Calc_Type_Status {
		CALORIES		(0x0001),
		DISTANCE		(0x0002),
		HEARTRATE		(0x0003),
		PACE			(0x0004),
		WATT			(0x0005),
		RPM				(0x0006),
		NULL			(0X0000);

		private int value;

		private Calc_Type_Status(int value) {
			this.value = value;
		}

		private int getValue() {
			return this.value;
		}

		private Calc_Type_Status fromid(int ival){
			for(Calc_Type_Status status : Calc_Type_Status.values())
			{
				if(status.getValue() == ival)
				{
					return status;
				}
			}
			return NULL;
		}
	}

	/////////////////////////////////////////////////////////
	public static float f_watt_calculate(long ltime, int imode)
	{
		float fcalhr = 0;
		float fwatt = 0;

		fcalhr = ExerciseGenfunc.f_get_hour_calories();
		fwatt = fcalhr / 4.148f;

		if(DEBUG) Log.e(TAG, "==========f_watt_calculate=========fwatt=" + fwatt);
		return fwatt;
	}

	private float f_calories_calculate(long ltime, int imode)
	{
		float fval = 0;
		float fspeed = 0;
		float fincline = 0;
		float fweight = 0;
		float fwatt = 0;

		CoolDownData.DEVICE_COMMAND Uart_command = null;

		if (CoolDownData.list_real_setting.size() > CoolDownData.ilist_count) {
			Uart_command = CoolDownData.list_real_setting.get(CoolDownData.ilist_count);
		}

		if (Uart_command != null) {
			fspeed = Uart_command.fspeed;
			fincline = Uart_command.fincline;
			fweight = ExerciseData.fweight;

			if(fspeed > 0) {
				if (fspeed < 6) {
					fval = (float) ((((3.5 + (10 * fspeed) / 6) + (30 * fspeed * fincline)/100) * (fweight / 200)) * ltime / 60000);
				} else {
					fval = (float) ((((3.5 + (20 * fspeed) / 6) + (15 * fspeed * fincline)/100) * (fweight / 200)) * ltime / 60000);
				}
			}

		}

		if(DEBUG) Log.e(TAG, "==========f_calories_calculate=========fval=" + fval + "       fspeed=" + fspeed + "       fincline=" + fincline + "       fweight=" + fweight);
		return fval;
	}

	private float f_distance_calculate(long ltime, int imode)
	{
		float fval = 0;
		float fspeed = 0;
		float frpm = 0;

		CoolDownData.DEVICE_COMMAND Uart_command = null;

		if (CoolDownData.list_real_setting.size() > CoolDownData.ilist_count) {
			Uart_command = CoolDownData.list_real_setting.get(CoolDownData.ilist_count);
		}

		if (Uart_command != null) {

			fspeed = Uart_command.fspeed;

			if (fspeed > 0) {
				fval =  fspeed * ltime / 3600f;
			}
		}

		if(DEBUG) Log.e(TAG, "==========f_distance_calculate=========fval=" + fval);
		return fval;
	}

	private float f_heartrate_calculate()
	{
		float fval;

		fval = ExerciseData.uart_data1.fheart_rate;

		if(fval > 220 || fval < 0)
		{
			fval = 0;
		}

		if(DEBUG) Log.e(TAG, "==========f_heartrate_calculate=========fval=" + fval);
		return fval;
	}

	//pace at treadmill ; EP,RB,UB return rpm
	private float f_pace_calculate(long ltime, int imode)
	{
		float fval = 0;
		float fspeed = 0;
		float frpm = 0;

		CoolDownData.DEVICE_COMMAND Uart_command = null;

		if (CoolDownData.list_real_setting.size() > CoolDownData.ilist_count) {
			Uart_command = CoolDownData.list_real_setting.get(CoolDownData.ilist_count);
		}

		if (Uart_command != null) {

			fspeed = Uart_command.fspeed;

			if(fspeed > 0)
			{
				fval = 3600 / fspeed;
			}
		}

		if(fval > 18000)
		{
			fval = 0;
		}

		if(DEBUG) Log.e(TAG, "==========f_pace_calculate=========fval=" + fval);
		return fval;
	}

	//bCaculte_Info for Treadmill
	private void bCaculte_Info(long ltime)
	{
		float fcalorie;
		float fdistance;
		float fheartrate;
		float fpace;
		float fwatt;

		//Calories
		fcalorie = f_calories_calculate(ltime, EngSetting.i_Get_ExerciseType());
		f_Calculate_Data(Calc_Type_Status.CALORIES, fcalorie, ltime);

		//Distance
		fdistance = f_distance_calculate(ltime, EngSetting.i_Get_ExerciseType());
		f_Calculate_Data(Calc_Type_Status.DISTANCE, fdistance, ltime);

		//HeartRate
		fheartrate = f_heartrate_calculate();
		f_Calculate_Data(Calc_Type_Status.HEARTRATE, fheartrate, ltime);

		//Pace
		fpace = f_pace_calculate(ltime, EngSetting.i_Get_ExerciseType());
		f_Calculate_Data(Calc_Type_Status.PACE, fpace, ltime);

		//watt
		fwatt = f_watt_calculate(ltime, EngSetting.i_Get_ExerciseType());
		f_Calculate_Data(Calc_Type_Status.WATT, fwatt, ltime);

		return ;
	}

	private void v_calculate_info(long ltime)
	{
		bCaculte_Info(ltime);
	}

	////////////////////////////////////////////////////////////
	private void v_Exercise_mode_Key_Mask(){

		if(ExerciseData.i_get_finish() == 0x04) //pause
		{
//			v_set_Exercise_Status(-1);
		}
		else if(ExerciseData.i_get_finish() == 0x05) //cooldonw
		{
			v_set_Exercise_Status(-1);
		}
		else if(ExerciseData.i_get_finish() == 0x06) //start
		{
			v_set_Exercise_Status(-1);
		}

	}

	private boolean b_Exercise_mode_Key_Skip(){
		boolean bret = false;;

		return bret;
	}

	private void v_Force_Reset_Current_time(){
		mCaculate_Data.lcurrent_time = 0;
		mCaculate_Data.icurrent_time = 0;
	}

	private void v_Reset_Current_time(int iPreTime){
		mCaculate_Data.lcurrent_time %= (iPreTime *1000);
		mCaculate_Data.icurrent_time = 0;
	}

	private void v_Total_time_add(long ldiff){
		mCaculate_Data.ltotal_time += ldiff;
		mCaculate_Data.itotal_time = (int)(mCaculate_Data.ltotal_time/1000);
	}

	private void v_Current_time_add(long ldiff){
		mCaculate_Data.lcurrent_time += ldiff;
		mCaculate_Data.icurrent_time = (int)(mCaculate_Data.lcurrent_time/1000);

	}

	private boolean bCheck_speed_is_change()
	{
		boolean bret = false;
		CoolDownData.DEVICE_COMMAND Uart_command = null;
		float fval;

		if(CoolDownData.list_real_setting.size() > CoolDownData.ilist_count) {
			Uart_command = CoolDownData.list_real_setting.get(CoolDownData.ilist_count);
		}

		fval = ExerciseData.uart_data1.fspeed;

		if (fval >= EngSetting.f_Get_Min_Speed() && fval <= EngSetting.f_Get_Max_Speed()) {
			if (HeartRateControl.ikey_speed_count <= HeartRateControl.ikey_delay_count)
			{
				HeartRateControl.ikey_speed_count++;
				if (HeartRateControl.ikey_speed_count == HeartRateControl.ikey_delay_count)
				{
					if (Uart_command != null) {
						Uart_command.fspeed = ExerciseData.uart_data1.fspeed;
					}
					HeartRateControl.v_Set_HR_hasdetect_Time(0);
					bret = true;
				}
			}
			else if (HeartRateControl.fkey_speed != ExerciseData.uart_data1.fspeed)
			{
				HeartRateControl.fkey_speed = ExerciseData.uart_data1.fspeed;
				HeartRateControl.ikey_speed_count = 0;

				//加快 speed更新,略過 HeartRateControl.ikey_delay_count 測試版
//				HeartRateControl.ikey_incline_count = 2;
//				if (HeartRateControl.ikey_speed_count == HeartRateControl.ikey_delay_count)
				{
					if (Uart_command != null) {
						Uart_command.fspeed = ExerciseData.uart_data1.fspeed;
					}
					HeartRateControl.v_Set_HR_hasdetect_Time(0);
					bret = true;
				}
			}
		}

		return bret;
	}

	private boolean bCheck_incline_is_change() {
		boolean bret = false;
		CoolDownData.DEVICE_COMMAND Uart_command = null;
		float fval;

		if (CoolDownData.list_real_setting.size() > CoolDownData.ilist_count) {
			Uart_command = CoolDownData.list_real_setting.get(CoolDownData.ilist_count);
		}

		fval = ExerciseData.uart_data1.fincline;

		if (fval >= EngSetting.f_Get_Min_Incline() && fval <= EngSetting.f_Get_Max_Incline()) {
			if (HeartRateControl.ikey_incline_count <= HeartRateControl.ikey_delay_count)
			{
				HeartRateControl.ikey_incline_count++;
				if (HeartRateControl.ikey_incline_count == HeartRateControl.ikey_delay_count)
				{
					if (Uart_command != null) {
						Uart_command.fincline = ExerciseData.uart_data1.fincline;
					}
					HeartRateControl.v_Set_HR_hasdetect_Time(0);
					bret = true;
				}
			}
			else if (HeartRateControl.fkey_incline != ExerciseData.uart_data1.fincline)
			{
				if(ExerciseData.uart_data1.fincline == 0)
				{
//					v_set_Exercise_Status(0x00);
				}
				else {
					HeartRateControl.fkey_incline = ExerciseData.uart_data1.fincline;
					HeartRateControl.ikey_incline_count = 0;

					//加快 speed更新,略過 HeartRateControl.ikey_delay_count 測試版
//					HeartRateControl.ikey_incline_count = 2;
//					if (HeartRateControl.ikey_incline_count == HeartRateControl.ikey_delay_count)
					{
						if (Uart_command != null) {
							Uart_command.fincline = ExerciseData.uart_data1.fincline;
						}
						HeartRateControl.v_Set_HR_hasdetect_Time(0);
						bret = true;
					}
				}
			}
		}

		return bret;
	}

	//Speed and Incline change by real key
	private void vUpdate_reallist_from_key()
	{
		if(CoolDownData.bCD_check_timeout(CoolDownData.lcooldown_key_check_delay, CoolDownData.l_Get_Softkey_time())) {
			if(CoolDownData.iCD_Get_status() != 0x11 || CoolDownData.iCD_Get_status() != 0x02) {
				CoolDownData.vCD_Set_status(0x02);
				CoolDownData.v_Set_CD_time(0);
			}
		}
	}


	//Uart command 讀回後處理
	private void v_get_uart_report()
	{
		CoolDownData.DEVICE_COMMAND Uart_command = null;
		if(CoolDownData.list_real_setting.size() > CoolDownData.ilist_count) {
			Uart_command = CoolDownData.list_real_setting.get(CoolDownData.ilist_count);
		}

		if (Uart_command != null) {
			Uart_command.fheart_rate = ExerciseData.uart_data1.fheart_rate;
			if (!b_Exercise_mode_Key_Skip()) {
//				if(bCheck_speed_is_change())
//				{
//					vUpdate_reallist_from_key();
//				}

				if(bCheck_incline_is_change())
				{
					vUpdate_reallist_from_key();
				}
			}

		}

		v_Exercise_mode_Key_Mask();

		return ;
	}
	////////////////////////////////////////////////////////////
	public void v_init_Cooldown_data1(float ftime)
	{
		int iLoop;
		int istep_sec = ExerciseData.iStep_time;
		int imax = (int)ftime/60000;
		float fspeed ;
		float flevel ;
		float[] flevel_array = new float[imax*2] ;

		//避開ikey_incline_count初始判斷
		HeartRateControl.ikey_incline_count = 5;
		if (HeartRateControl.fkey_incline != ExerciseData.uart_data1.fincline)
		{
			HeartRateControl.fkey_incline = ExerciseData.uart_data1.fincline;
		}

		CoolDownData.vCD_Set_status(0x01);
		CoolDownData.v_Set_CD_time(0);

		HeartRateControl.ikey_speed_count = 4;

		fspeed = EngSetting.f_Get_Min_Speed();
		flevel = Exercisefunc.fGetCurrentIncline();

		for(iLoop = 0; iLoop < (imax * 2); iLoop++) {
			if((iLoop % 2) == 0)
			{
				flevel = flevel * CoolDownData.lcooldown_factor;
			}
			if(flevel < CoolDownData.f_cooldown_min_level)
			{
				flevel = CoolDownData.f_cooldown_min_level;
			}
			flevel_array[iLoop] = flevel;
		}

		for(iLoop = 0; iLoop < imax; iLoop++) {
			CoolDownData.DEVICE_COMMAND suart_cmd_orig = new CoolDownData.DEVICE_COMMAND();

			suart_cmd_orig.bcal = true;
			suart_cmd_orig.isec = istep_sec;
			suart_cmd_orig.fspeed = fspeed;
			suart_cmd_orig.fincline = (int)(flevel_array[iLoop] + 0.5f);

			CoolDownData.list_real_setting.add(suart_cmd_orig);
			Log.e(TAG, "===fincline===" + suart_cmd_orig.fincline);

		}

		if (CoolDownData.list_real_setting.size() > 0) {
			CoolDownData.DEVICE_COMMAND Uart_command;
			Uart_command = CoolDownData.list_real_setting.get(0);
			if (Uart_command != null) {
//				UartData.set_uart_incline(Uart_command.fincline);
				CoolDownData.v_Set_Softkey_time(0);

				v_Level_Change_infoshow(Uart_command.fincline, 0);
			}
		}

		UartData.vUartCmd_64(20, "1");

	}

	private void v_Level_Change_infoshow(float flevel, float ftime)
	{
		String sdata;
		Log.e(TAG, "===v_Level_Change_infoshow===" + flevel);

//		sdata = LanguageData.s_get_string(mContext, R.string.level_decreases_to) + " "
//				+ Rtx_TranslateValue.sFloat2String(flevel, 0) + " "
//				+ LanguageData.s_get_string(mContext, R.string.in) + " "
//				+ Rtx_TranslateValue.sFloat2String(ftime, 0) + " "
//				+ LanguageData.s_get_string(mContext, R.string.seconds);
		//format :Resistance level decrease to %1$.1f in 3 seconds
		sdata = String.format(LanguageData.s_get_string(mContext, R.string.resistance_level_decreases), flevel);

		Change_UI_Info.v_set_stage(Change_UI_Info.istate_sinfo);
		Change_UI_Info.i_countdown_infomation = Integer.MAX_VALUE;
		Change_UI_Info.iinfomation_flash = 0;
		Change_UI_Info.sinfomation = "";
	}

	private void v_CoolDown_Disable_infoshow()
	{
		String sdata;

		sdata = LanguageData.s_get_string(mContext, R.string.auto_resistance_level_down_disabled);

		Change_UI_Info.v_set_stage(Change_UI_Info.istate_sinfo);
		Change_UI_Info.i_countdown_infomation = Integer.MAX_VALUE;
		Change_UI_Info.iinfomation_flash = 0;
		Change_UI_Info.sinfomation = sdata;
	}

	private void v_CoolDown_countdown_infoshow(float total, float ftime)
	{
		String sdata;
		float fval;

		fval = (total - ftime)/1000;

		sdata = LanguageData.s_get_string(mContext, R.string.workout_will_stop_in);
		//format :WORKOUT WILL STOP IN %1$d:%2$02d
		sdata = String.format( sdata, (int)fval / 60, (int)fval % 60);

		Change_UI_Info.v_set_stage(Change_UI_Info.istate_sinfo);
		Change_UI_Info.i_countdown_infomation = Integer.MAX_VALUE;
		Change_UI_Info.iinfomation_flash = 0;
		Change_UI_Info.sinfomation = sdata;
	}

	private void v_Level_Change_Command(int index)
	{

		if (CoolDownData.list_real_setting.size() > index) {
			CoolDownData.DEVICE_COMMAND Uart_command;
			Uart_command = CoolDownData.list_real_setting.get(index);
			if (Uart_command != null) {
				UartData.set_uart_level(Uart_command.fincline);
				CoolDownData.v_Set_Softkey_time(0);
				Log.e(TAG, "===v_Level_Change_Command===" + Uart_command.fincline);

			}
		}

	}

	//Target_check
	//return false : DEVICE_COMMAND is null
	public boolean Target_check(long ldiff)
	{
		boolean bret = true;
		long ldata;

        v_get_uart_report();

		if(ldiff <= 0)
		{
			return bret;
		}

		ldata = ldiff + CoolDownData.l_Get_CD_time();
		CoolDownData.v_Set_CD_time(ldata);

		ldata = ldiff + CoolDownData.l_Get_Softkey_time();
		CoolDownData.v_Set_Softkey_time(ldata);


		bret = Target_check_allmode(ldiff);

        if(bret) {
			HeartRateControl.HeartRate_update_Curr(ldiff);
		}
        else
        {
			v_set_Exercise_Status(0x00);
        }

		return bret;
	}

	private boolean Target_check_allmode(long ldiff)
    {
        boolean bret = true;

        if(ldiff <= 0)
        {
			return bret;
        }


		switch (CoolDownData.iCD_Get_status())
		{
			case 0x01 :
				if(CoolDownData.bCD_check_timeout(CoolDownData.lbefore_ajust_time, CoolDownData.l_Get_CD_time()))
				{
					v_Level_Change_Command(CoolDownData.ilist_count);
					CoolDownData.vCD_Set_status(0x10);
					CoolDownData.v_Set_CD_time(0);
				}
				else
				{
					if (CoolDownData.list_real_setting.size() > CoolDownData.ilist_count) {
						CoolDownData.DEVICE_COMMAND Uart_command;
						Uart_command = CoolDownData.list_real_setting.get(CoolDownData.ilist_count);
						if (Uart_command != null) {
							v_Level_Change_infoshow(Uart_command.fincline, 0);
						}
					}
				}
				break;
			case 0x02 :
				if(CoolDownData.bCD_check_timeout(CoolDownData.lbefore_ajust_time, CoolDownData.l_Get_CD_time()))
				{
					CoolDownData.vCD_Set_status(0x11);
					CoolDownData.v_Set_CD_time(0);
				}
				else
				{
					//v_CoolDown_Disable_infoshow();
				}
				break;
			case 0x10 :
				v_CoolDown_countdown_infoshow(CoolDownData.f_cooldown_time, CoolDownData.mCaculate_Data.ltotal_time);
				if(CoolDownData.bCD_check_timeout(CoolDownData.lcooldown_ajust, CoolDownData.l_Get_CD_time()))
				{
					CoolDownData.vCD_Set_status(0x01);
					CoolDownData.v_Set_CD_time(0);
				}
				break;
			case 0x11 :
				v_CoolDown_countdown_infoshow(CoolDownData.f_cooldown_time, CoolDownData.mCaculate_Data.ltotal_time);
				break;
			default:
				break;
		}

		bret = Target_time_check(ldiff);

		return bret;
    }

	private float fGetRealSpeed(int iIndex)
	{
		float fSpeed = 0;

		CoolDownData.DEVICE_COMMAND Uart_command = null;
		if(CoolDownData.list_real_setting.size() > iIndex)
		{
			Uart_command = CoolDownData.list_real_setting.get(iIndex);
		}

		if(Uart_command != null)
		{
			fSpeed = Uart_command.fspeed;
		}


		return fSpeed;
	}

	private float fGetCurrentSpeed()
	{
		float fSpeed = 0;

		fSpeed = fGetRealSpeed(CoolDownData.ilist_count);

		return fSpeed;
	}

	private float fGetRealIncline(int iIndex)
	{
		float fIncline = 0;

		CoolDownData.DEVICE_COMMAND Uart_command = null;
		if(CoolDownData.list_real_setting.size() > iIndex)
		{
			Uart_command = CoolDownData.list_real_setting.get(iIndex);
		}

		if(Uart_command != null)
		{
			fIncline = Uart_command.fincline;
		}


		return fIncline;
	}

	private float fGetCurrentIncline()
	{
		float fIncline = 0;

		fIncline = fGetRealIncline(CoolDownData.ilist_count);

		return fIncline;
	}

	private int iGetCurrentTargetSec()
	{
		int iSec = 0;

		CoolDownData.DEVICE_COMMAND Uart_command = null;
		if(CoolDownData.list_real_setting.size() > CoolDownData.ilist_count)
		{
			Uart_command = CoolDownData.list_real_setting.get(CoolDownData.ilist_count);
		}

		if(Uart_command != null)
		{
			iSec = Uart_command.isec;
		}


		return iSec;
	}

    //Target_time_check
	//return false : DEVICE_COMMAND is null
	private boolean Target_time_check(long ldiff)
	{
		boolean bret = false;
		int itarget_time ;

		CoolDownData.DEVICE_COMMAND Uart_command = null;
		if(CoolDownData.list_real_setting.size() > CoolDownData.ilist_count) {
			Uart_command = CoolDownData.list_real_setting.get(CoolDownData.ilist_count);
		}

		if(Uart_command != null)
		{
			v_Current_time_add(ldiff);

			itarget_time = Uart_command.isec;

			v_Total_time_add(ldiff);

			v_calculate_info(ldiff);

			bret = true;

			if(mCaculate_Data.icurrent_time >= itarget_time)
			{
				CoolDownData.ilist_count++;

				if(CoolDownData.list_real_setting.size() > CoolDownData.ilist_count) {
					v_Reset_Current_time(itarget_time);
				}
				else
				{
					bret = false;
				}
			}

		}

		return bret;
	}


	//////////////Target Parameter Function End////////////////
	private void v_set_Exercise_Status(int istatus)
	{
		ExerciseData.v_set_finish(istatus);
	}

	///////////////////////////////////////////////////////////
	public void vCoolDownfunc_SetFinish(boolean bStatus)
	{
		bCoolDownFinish = bStatus;
	}

	public boolean bCoolDownfunc_GetFinish()
	{
		return  bCoolDownFinish;
	}

	public boolean vCoolDownfunc_setLevel()
	{
		if(CoolDownData.list_real_setting.size() > CoolDownData.ilist_count)
		{
			////設定啟動Level
			v_Level_Change_Command(CoolDownData.ilist_count);
			return true;
		}
		else
		{
			return false;
		}
	}
}
