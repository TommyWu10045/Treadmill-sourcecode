package com.rtx.treadmill.RtxMainFunctionBike.Exercise;

import android.util.Log;

import com.rtx.treadmill.GlobalData.Calc_Data;
import com.rtx.treadmill.GlobalData.Change_UI_Info;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.ExerciseRunState;
import com.rtx.treadmill.RtxMainFunctionBike.Physical.Vo2Control;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.UartDevice.UartData;
import com.rtx.treadmill.UartDevice.Uartcommand;

import static com.rtx.treadmill.GlobalData.ExerciseData.mCaculate_Data;

/**
 * 儲存全域設定
 * @author Tom
 *
 */
public class Exercisefunc {
	private static String TAG = "Jerry";
	private static boolean DEBUG = false;

	private Uartcommand mUartcmd ;

	public Exercisefunc(Uartcommand mUartcmd) {
		super();

		this.mUartcmd = mUartcmd;
	}

	//f_Calculate_Data
	public static void f_Calculate_Data(Calc_Type_Status status, float finput, long ltime)
	{
		Calc_Data sData;

		if(finput < 0)
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
			case PACE: //Treadmill is Pace ; EP/UB/RB is Speed
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
				int FinputValue = (int) (finput+0.5);//By Alan
				UartData.set_uart_watt(FinputValue);//By Alan
				ExerciseGenfunc.f_Calculate_total(sData, finput);
				ExerciseGenfunc.f_Calculate_average(sData, finput);
				ExerciseGenfunc.f_Calculate_Min(sData, finput);
				ExerciseGenfunc.f_Calculate_Max(sData, finput);
				break;
			case RPM:
				sData = mCaculate_Data.srpm;
				sData.fcurr = finput;
				ExerciseGenfunc.f_Calculate_average(sData, finput);
				ExerciseGenfunc.f_Calculate_Max(sData, finput);
				break;
			case DISTANCE_ENG:
				sData = mCaculate_Data.sdistance_eng;
				sData.fcurr = finput * 1000 / ltime; // 每秒公尺
				ExerciseGenfunc.f_Calculate_total(sData, finput);
				ExerciseGenfunc.f_Calculate_Max(sData, sData.fcurr);
				ExerciseGenfunc.f_Calculate_Min(sData, sData.fcurr);
				break;
			default:
				break;
		}

	}

	public enum Calc_Type_Status {
		CALORIES		(0x0001),
		DISTANCE		(0x0002),
		HEARTRATE		(0x0003),
		PACE			(0x0004),
		WATT			(0x0005),
		RPM				(0x0006),
		DISTANCE_ENG	(0x0102),
		NULL			(0X0000);

		private int value;

		private Calc_Type_Status(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}

		public static Calc_Type_Status fromid(int ival){
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
	public static float f_level_calculate(float fwatt, float frpm)
	{
		float ftouqe = 0;
		float fval = -1f;
		int iLoop;

		if(frpm > 0) {
			ftouqe = (fwatt * 100f) / (1.027f * frpm);

			for (iLoop = 0; iLoop < EngSetting.fmachine_tq.length; iLoop++) {
				if(ftouqe <= EngSetting.f_Get_fmachine_tq(iLoop)) {
					iLoop ++;
					break;
				}
			}

			if(iLoop < EngSetting.f_Get_Min_level())
			{
				iLoop = (int)EngSetting.f_Get_Min_level()  ;
			}
			else if(iLoop > EngSetting.f_Get_Max_level())
			{
				iLoop = (int)EngSetting.f_Get_Max_level()  ;
			}

			fval = iLoop;
		}

		if(DEBUG) Log.e(TAG, "==========f_level_calculate==========" + fval + "     ftouqe=" + ftouqe);
		return fval;
	}

	public static float f_watt_calculate()
	{
		float frpm ;
		float flvl ;
		float ftouqe = 0;
		float fval = 0;

		ExerciseData.DEVICE_COMMAND Uart_command = null;

		if (ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
			Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
		}

		if (Uart_command != null) {
			frpm = Uart_command.frpm;
			flvl = Uart_command.fincline - 1;
			ftouqe = EngSetting.f_Get_fmachine_tq((int)flvl) / 100f;

			if(ftouqe > 0 && frpm > 0)
			{
				fval = ftouqe * frpm * 1.027f;
			}

		}

		if(DEBUG) Log.e(TAG, "==========f_watt_calculate==========" + fval + "  ===ftouqe=" + ftouqe);
		return fval;
	}

	public static float f_watt_calculate(int istep, float frange)
	{
		float frpm ;
		float flvl ;
		float ftouqe = 0;
		float fval = 0;

		ExerciseData.DEVICE_COMMAND Uart_command = null;

		if (ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
			Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
		}

		if (Uart_command != null) {
			if(frange <= 0) {
				frpm = Uart_command.frpm;
			}
			else
			{
				frpm = WattControl.v_Calculate_Avg_RPM(istep, frange);
			}
			flvl = Uart_command.fincline - 1;
			ftouqe = EngSetting.f_Get_fmachine_tq((int)flvl) / 100f;

			if(ftouqe > 0 && frpm > 0)
			{
				fval = ftouqe * frpm * 1.027f;
			}

		}

		if(DEBUG) Log.e(TAG, "==========f_watt_calculate==========" + fval + "  ===ftouqe=" + ftouqe);
		return fval;
	}

	public static float f_calories_calculate(long ltime)
	{
		float fval = 0;
		float fweight = 0;
		float fwatt = 0;

		ExerciseData.DEVICE_COMMAND Uart_command = null;

		if (ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
			Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
		}

		if (Uart_command != null) {
			fweight = ExerciseData.fweight;
			fwatt = f_watt_calculate();
			fval = (1.8f * fwatt * 6f / fweight + 7f) * fweight * ltime / (200f * 1000f * 60f);
		}

		if(DEBUG) Log.e(TAG, "==========f_calories_calculate==========" + fval);
		return fval;
	}

	public static float f_rpm_to_speed(int imode, float frpm)
	{
		float fval = 0;

		if(imode == EngSetting.RBIKING || imode == EngSetting.UBIKING || imode == EngSetting.RBIKING6 || imode == EngSetting.UBIKING6) //RB and UB, 由RPM轉speed
		{
			fval = (78f * 3.14159f * 2.54f * frpm) * 60 / 100000;
		}
		else if(imode == EngSetting.ELLIPTICAL || imode == EngSetting.ELLIPTICAL6) //EP, 由RPM轉speed
		{
			fval = 101.6f * frpm * 60 / 100000;
		}

		if(DEBUG) Log.e(TAG, "==========f_rpm_to_speed==========" + fval);
		return fval;
	}

	public static float f_distance_calculate(long ltime)
	{
		float fval = 0;
		float fspeed = 0;

		ExerciseData.DEVICE_COMMAND Uart_command = null;

		if (ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
			Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
		}

		if (Uart_command != null) {
			fspeed = Uart_command.fspeed;

			fval =  fspeed * ltime / 3600f;
		}

		if(DEBUG) Log.e(TAG, "==========f_distance_calculate=========fval=" + fval);
		return fval;
	}

	public static float f_heartrate_calculate()
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
	public static float f_pace_calculate(long ltime)
	{
		float fval = 0;
		float fspeed = 0;

		ExerciseData.DEVICE_COMMAND Uart_command = null;

		if (ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
			Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
		}

		if (Uart_command != null) {
			fspeed = Uart_command.fspeed;

			if(fspeed > 0)
			{
				fval = fspeed;
			}
		}

		if(fval > 18000)
		{
			fval = 0;
		}

		if(DEBUG) Log.e(TAG, "==========f_pace_calculate=========fval=" + fval);
		return fval;
	}

	//EP,RB,UB return RPM
	public static float f_rpm_calculate(long ltime, int imode)
	{
		float fval = 0;
		float frpm = 0;

		ExerciseData.DEVICE_COMMAND Uart_command = null;

		if (ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
			Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
		}

		if (Uart_command != null) {

			frpm = Uart_command.frpm;

			if(frpm > 0)
			{
				fval = frpm;
			}
		}

		if(fval > 1800)
		{
			fval = 0;
		}

		if(DEBUG) Log.e(TAG, "==========f_rpm_calculate=========fval=" + fval);
		return fval;
	}

	//bCaculte_Info for Treadmill
	public static void bCaculte_Info(long ltime)
	{
		float fwatt;
		float fcalorie;
		float fdistance;
		float fheartrate;
		float fpace;

		//WATT
		fwatt = f_watt_calculate();
		Exercisefunc.f_Calculate_Data(Calc_Type_Status.WATT, fwatt, ltime);

		//Calories
		fcalorie = f_calories_calculate(ltime);
		Exercisefunc.f_Calculate_Data(Exercisefunc.Calc_Type_Status.CALORIES, fcalorie, ltime);

		//Distance
		fdistance = f_distance_calculate(ltime);
		Exercisefunc.f_Calculate_Data(Exercisefunc.Calc_Type_Status.DISTANCE, fdistance, ltime);

		//HeartRate
		fheartrate = f_heartrate_calculate();
		Exercisefunc.f_Calculate_Data(Exercisefunc.Calc_Type_Status.HEARTRATE, fheartrate, ltime);

		//Speed
		fpace = f_pace_calculate(ltime);
		Exercisefunc.f_Calculate_Data(Exercisefunc.Calc_Type_Status.PACE, fpace, ltime);

		//Rpm
		fpace = f_rpm_calculate(ltime, EngSetting.i_Get_ExerciseType());
		Exercisefunc.f_Calculate_Data(Exercisefunc.Calc_Type_Status.RPM, fpace, ltime);

		return ;
	}

	public static void v_calculate_info(long ltime)
	{
        bCaculte_Info(ltime);
	}

	//bCaculte_Info for Treadmill
	public static void bCaculte_eng_Info(long ltime)
	{
		float fdistance;

		//Distance
		fdistance = f_distance_calculate(ltime);
		Exercisefunc.f_Calculate_Data(Calc_Type_Status.DISTANCE_ENG, fdistance, ltime);

		return ;
	}

	public static void v_calculate_eng_info(long ltime)
	{
		bCaculte_eng_Info(ltime);
	}

	////////////////////////////////////////////////////////////
	private void v_Exercise_mode_Key_Mask(){
		ExerciseRunState imode = ExerciseData.E_mode.fromId(ExerciseData.E_imode & ExerciseData.M_mask_type);

		switch (imode)
		{
			case PROC_EXERCISE_PHY:
				if(ExerciseData.i_get_finish() == 0x04) //pause
				{
					v_set_Exercise_Status(-1);
				}
				else if(ExerciseData.i_get_finish() == 0x05) //cooldonw
				{
					v_set_Exercise_Status(-1);
				}
				else if(ExerciseData.i_get_finish() == 0x06) //start
				{
					v_set_Exercise_Status(-1);
				}
				break;
			default:
				break;
		}

	}

	private boolean b_Exercise_mode_Key_Skip(){
		boolean bret;

		switch (ExerciseData.E_mode)
		{
			case PROC_EXERCISE_PHY_GERKIN:
				bret = true;
				break;
			default:
				bret = false;
				break;
		}
		return bret;
	}

	public static void v_Reset_Current_time(int isec){
		mCaculate_Data.lcurrent_time %= (isec *1000);
		mCaculate_Data.icurrent_time = 0;
	}

	public static void v_Total_time_add(long ldiff){
		mCaculate_Data.ltotal_time += ldiff;
		mCaculate_Data.itotal_time = (int)(mCaculate_Data.ltotal_time/1000);
	}

	public static void v_Total_time_Eng_add(long ldiff){
		mCaculate_Data.ltotal_time_eng += ldiff;
		mCaculate_Data.itotal_time_eng = (int)(mCaculate_Data.ltotal_time_eng/1000);
	}

	public static void v_Current_time_add(long ldiff){
		mCaculate_Data.lcurrent_time += ldiff;
		mCaculate_Data.icurrent_time = (int)(mCaculate_Data.lcurrent_time/1000);

	}

	private void v_check_motor_befor_cheange(int itarget_time){
		ExerciseData.DEVICE_COMMAND Uart_command_Curr = null;
		ExerciseData.DEVICE_COMMAND Uart_command_Next = null;

		if(Change_UI_Info.i_get_stage() == 0) {
			if (mCaculate_Data.icurrent_time >= itarget_time - Change_UI_Info.istate_delay) {
				if (ExerciseData.list_real_setting.size() > ExerciseData.ilist_count + 1) {
					Uart_command_Curr = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
					Uart_command_Next = ExerciseData.list_real_setting.get(ExerciseData.ilist_count + 1);

					if (Uart_command_Curr.fincline != Uart_command_Next.fincline) {
						Change_UI_Info.v_set_stage(Change_UI_Info.istate_incline);
						Change_UI_Info.fcurrent[1] = Uart_command_Curr.fincline;
						Change_UI_Info.fnext[1] = Uart_command_Next.fincline;
						Change_UI_Info.i_countdown = itarget_time - mCaculate_Data.icurrent_time;
					}
				}
			}
		}
	}

	public static void vUpdate_reallist_from_incline(int index)
	{
		int iLoop;
		ExerciseData.DEVICE_COMMAND Uart_command ;
		float fincline = 0f;

		for(iLoop = index; iLoop < ExerciseData.list_real_setting.size(); iLoop++)
		{
			Uart_command = ExerciseData.list_real_setting.get(iLoop);
			if (Uart_command != null) {
				if(iLoop != index) {
					Uart_command.fincline = fincline;
				}
				else
				{
					fincline = Uart_command.fincline;
				}
			}
		}

	}

	//Speed and Incline change by key
	private void vUpdate_reallist_from_key(ExerciseRunState mState, int imode)
	{
		switch (mState)
		{
			case PROC_EXERCISE_TR_MANUAL:
			case PROC_EXERCISE_TR_TIME:
			case PROC_EXERCISE_TR_CALORIES:
			case PROC_EXERCISE_TR_DISTANCE:
			case PROC_EXERCISE_TR_CONSWATT:
			case PROC_EXERCISE_QUICKSTART  :
            case PROC_EXERCISE_HEARTRATE   :
				if(imode == 1) {
					vUpdate_reallist_from_incline(ExerciseData.ilist_count);
				}
				break;
			case PROC_EXERCISE_TR_HILL:
			case PROC_EXERCISE_TR_FATBURN:
			case PROC_EXERCISE_TR_CARDIO:
			case PROC_EXERCISE_TR_STRENGHT:
			case PROC_EXERCISE_TR_INTERVAL:
			case PROC_EXERCISE_WORKOUT	   :
			default:
				break;
		}
	}

	private boolean bCheck_level_is_change()
	{
		boolean bret = false;
		ExerciseData.DEVICE_COMMAND Uart_command = null;
		float fval ;

		if(ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
			Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
		}

		//fval = ExerciseData.uart_data1.fincline + 1f;
		fval = ExerciseData.uart_data1.fincline;

		if(fval >= EngSetting.f_Get_Min_level() && fval <= EngSetting.f_Get_Max_level() ) {
			if(HeartRateControl.ikey_incline_count <= HeartRateControl.ikey_delay_count)
			{
				HeartRateControl.ikey_incline_count ++;
				if(HeartRateControl.ikey_incline_count == HeartRateControl.ikey_delay_count)
				{
					if (Uart_command != null) {
						Uart_command.fincline = ExerciseData.uart_data1.fincline;
					}
					HeartRateControl.v_Set_HR_hasdetect_Time(0);
					bret = true;
				}
			}
			else if(HeartRateControl.fkey_incline != ExerciseData.uart_data1.fincline)
			{
				HeartRateControl.fkey_incline = ExerciseData.uart_data1.fincline;
				HeartRateControl.ikey_incline_count = 0;
			}
		}

		return bret;
	}

	private  boolean b_check_update_key()
	{
		boolean bret = true;
		if(ExerciseData.i_get_finish() == 0x00)
		{
			bret = false;
		}
		else if(ExerciseData.i_get_finish() == 0x01)
		{
			bret = false; //stop key
		}
		else if(ExerciseData.i_get_finish() == 0x02)
		{
			bret = false; //emergency key
		}
		else if(ExerciseData.i_get_finish() == 0x03) //Device error
		{
			bret = false;
		}
		else if(ExerciseData.i_get_finish() == 0x04) //pause key
		{

		}
		else if(ExerciseData.i_get_finish() == 0x06) //start key
		{

		}
		else if(ExerciseData.i_get_finish() == 0x10) //heartrate start detect timeout
		{

		}
		else
		{
			bret = true;
		}

		return bret;

	}

	public void v_get_uart_report()
	{
		ExerciseData.DEVICE_COMMAND Uart_command = null;
		if(ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
			Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
		}

		if (Uart_command != null) {
			Uart_command.fheart_rate = ExerciseData.uart_data1.fheart_rate;
			Uart_command.frpm = ExerciseData.uart_data1.fspeed;
			Uart_command.fspeed = f_rpm_to_speed(EngSetting.i_Get_ExerciseType(), Uart_command.frpm);

			if (!b_Exercise_mode_Key_Skip() && b_check_update_key()) {
				if(bCheck_level_is_change())
				{
					vUpdate_reallist_from_key(ExerciseData.E_mode, 1);
				}
			}
		}

		v_Exercise_mode_Key_Mask();

		return ;
	}
	////////////////////////////////////////////////////////////
	//Target_check
	//return false : DEVICE_COMMAND is null
	public boolean Target_check(long ldiff)
	{
		boolean bret;

        v_get_uart_report();

        switch (ExerciseData.E_mode)
        {
            case PROC_EXERCISE_HEARTRATE:
                bret = HeartRateControl.Target_HearRate_mode(ldiff);
                break;
			case PROC_EXERCISE_TR_CONSWATT:
				bret = WattControl.Constant_Watt_Controll(ldiff);
				break;
			case PROC_EXERCISE_PHY_GERKIN:
				bret = Vo2Control.Physical_Gerkin_Start(ldiff);
				break;
			default:
				bret = Target_check_allmode(ldiff);
				break;
        }

        if(bret) {
            status_check_mode(ExerciseData.E_mode, ldiff);
			HeartRateControl.HeartRate_update_Curr(ldiff);
			WattControl.Watt_update_Curr(ldiff);
		}
        else
        {
            v_set_Exercise_Status(0x00);
        }

		return bret;
	}

	public static float fGetRealSpeed(int iIndex)
	{
		float fSpeed = 0;

		ExerciseData.DEVICE_COMMAND Uart_command = null;
		if(ExerciseData.list_real_setting.size() > 0) {
			if (ExerciseData.list_real_setting.size() > iIndex) {
				Uart_command = ExerciseData.list_real_setting.get(iIndex);
			} else {
				Uart_command = ExerciseData.list_real_setting.get(ExerciseData.list_real_setting.size() - 1);
			}
		}

		if(Uart_command != null)
		{
			fSpeed = Uart_command.fspeed;
		}


		return fSpeed;
	}

	public static float fGetCurrentSpeed()
	{
		float fSpeed = 0;

		fSpeed = fGetRealSpeed(ExerciseData.ilist_count);

		return fSpeed;
	}

	public static float fGetRealIncline(int iIndex)
	{
		float fIncline = 0;

		ExerciseData.DEVICE_COMMAND Uart_command = null;
		if(ExerciseData.list_real_setting.size() > 0) {
			if (ExerciseData.list_real_setting.size() > iIndex) {
				Uart_command = ExerciseData.list_real_setting.get(iIndex);
			} else {
				Uart_command = ExerciseData.list_real_setting.get(ExerciseData.list_real_setting.size() - 1);
			}
		}


		if(Uart_command != null)
		{
			fIncline = Uart_command.fincline;
		}


		return fIncline;
	}

	public static float fGetCurrentIncline()
	{
		float fIncline = 0;

		fIncline = fGetRealIncline(ExerciseData.ilist_count);

		return fIncline;
	}

	public static int iGetCurrentTargetSec()
	{
		int iSec = 0;

		ExerciseData.DEVICE_COMMAND Uart_command = null;
		if(ExerciseData.list_real_setting.size() > ExerciseData.ilist_count)
		{
			Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
		}

		if(Uart_command != null)
		{
			iSec = Uart_command.isec;
		}


		return iSec;
	}
	///////////////////////////////////////////////////////
    public boolean Target_check_allmode(long ldiff)
    {
        boolean bret = true;

        if(ldiff > 0)
        {
            bret = Target_time_check(ldiff);
        }

        return bret;
    }

	//Target_time_check
	//return false : DEVICE_COMMAND is null
	public boolean Target_time_check(long ldiff)
	{
		boolean bret = false;
		int itarget_time ;

		ExerciseData.DEVICE_COMMAND Uart_command = null;
		if(ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
			Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
		}

		if(Uart_command != null)
		{
			v_Current_time_add(ldiff);

			itarget_time = Uart_command.isec;

			if(Uart_command.bcal)
			{
				v_Total_time_add(ldiff);

				v_calculate_info(ldiff);
			}
			else
			{
				v_Total_time_Eng_add(ldiff); //Device runing Time;
				v_calculate_eng_info(ldiff);
			}

			bret = true;

			if(mCaculate_Data.icurrent_time >= itarget_time)
			{
				ExerciseData.ilist_count++;

				if(ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
					v_Reset_Current_time(ExerciseData.iStep_time);
				}
				else
				{
					bret = false;
				}
			}

			v_check_motor_befor_cheange(itarget_time);

		}

		return bret;
	}

	private void status_check_mode(ExerciseRunState mState, long ldiff)
	{
		float fval1 = 0;
		float fval2 = 33;

		switch (mState)
		{
			//target is time
			case PROC_EXERCISE_TR_MANUAL:
			case PROC_EXERCISE_TR_TIME:
				fval1 = ExerciseGenfunc.i_get_total_time();
				fval2 = ExerciseGenfunc.f_get_target_time();
				if(fval1 >= fval2)
				{
					v_set_Exercise_Status(0x00);
				}
				break;
			//target is CALORIES
			case PROC_EXERCISE_TR_CALORIES:
				fval1 = ExerciseGenfunc.f_get_total_calories();
				fval2 = ExerciseGenfunc.f_get_target_calorie();

				if(fval1 >= fval2)
				{
					v_set_Exercise_Status(0x00);
				}
				break;
			//target is MHR
			case PROC_EXERCISE_PHY_GERKIN:
				fval1 = ExerciseGenfunc.f_get_curr_heartrate();
				fval2 = ExerciseGenfunc.f_get_target_heart_rate();
				if(fval1 >= fval2)
				{
					long ldata = ldiff + HeartRateControl.lcount_MHR;
					HeartRateControl.v_Set_MHR_detect_Time(ldata);
					if(HeartRateControl.bHR_MHR_timeout(HeartRateControl.lcheck_MHR_continue)) {
						v_set_Exercise_Status(0x00);
					}
				}
				else
				{
					HeartRateControl.v_Set_MHR_detect_Time(0);
				}
				break;
			//target is distance
			case PROC_EXERCISE_TR_DISTANCE :
				fval1 = ExerciseGenfunc.f_get_total_distance();
				fval2 = ExerciseGenfunc.f_get_target_distance();
				if(fval1 >= fval2)
				{
					v_set_Exercise_Status(0x00);
				}
				break;
			default:
				break;
		}
		if(DEBUG)
		{
			Log.e(TAG, "===status_check_mode====fval1=" + fval1 + "  fval2=" + fval2);
		}

	}

    //////////////Target Parameter Function End////////////////
	public static void v_set_Exercise_Status(int istatus)
	{
		ExerciseData.v_set_finish(istatus);

	}


}
