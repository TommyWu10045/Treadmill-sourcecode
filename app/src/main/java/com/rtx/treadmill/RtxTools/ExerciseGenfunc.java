package com.rtx.treadmill.RtxTools;

import android.content.Context;

import com.rtx.treadmill.GlobalData.Calc_Data;
import com.rtx.treadmill.GlobalData.CoolDownData;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.WattControl;
import com.rtx.treadmill.UartDevice.Uartcommand;

import static com.rtx.treadmill.GlobalData.EngSetting.mile2km;
import static com.rtx.treadmill.GlobalData.ExerciseData.mCaculate_Data;

//import com.rtx.treadmill.RtxMainFunctionBike.Exercise.WattControl;

/**
 * 儲存全域設定
 * @author Tom
 *
 */
public class ExerciseGenfunc {
	private static String TAG = "Jerry";
	private static boolean DEBUG = false;

	private Uartcommand mUartcmd ;

	public ExerciseGenfunc(Uartcommand mUartcmd) {
		super();

		this.mUartcmd = mUartcmd;
	}

	public static void v_Exercise_Keep() {
		int iLoop;

		for (iLoop = 0; iLoop < ExerciseData.list_original_setting.size(); iLoop++) {
			ExerciseData.DEVICE_COMMAND suart_cmd_orig = ExerciseData.list_original_setting.get(iLoop);
			ExerciseData.DEVICE_COMMAND suart_cmd_real = new ExerciseData.DEVICE_COMMAND();

			suart_cmd_real.bcal = suart_cmd_orig.bcal;
			suart_cmd_real.isec = suart_cmd_orig.isec;
			suart_cmd_real.fspeed = suart_cmd_orig.fspeed;
			suart_cmd_real.fincline = suart_cmd_orig.fincline;
			suart_cmd_real.fheart_rate = suart_cmd_orig.fheart_rate;

			ExerciseData.list_real_setting.add(suart_cmd_real);
		}
	}

	public static void f_Calculate_total(Calc_Data sData, float finput)
	{
		sData.icount++;
		sData.ftotal += finput;

		return;
	}

	public static void f_Calculate_average(Calc_Data sData, float finput)
	{
		sData.fave = sData.ftotal / sData.icount;
		return;
	}

	public static void f_Calculate_Max(Calc_Data sData, float finput)
	{
		float forig;

		forig = sData.fmax;

		if (forig < finput) {
			sData.fmax = finput;
		}
		else if(forig == 0)
		{
			sData.fmax = finput;
		}

		return;
	}

	public static void f_Calculate_Min(Calc_Data sData, float finput)
	{
		float forig;

		forig = sData.fmin;


		if (forig > finput) {
			sData.fmin = finput;
		}
		else if(forig == 0)
		{
			sData.fmin = finput;
		}

		return;
	}

	//0 normal; 1 time; 2 distance; 3 calories; 4 heartrate; 5 normal no remain time; 6 watt; 7 tr_manual; 99 not define
	public static int iGet_target_mode( )
	{
		int itarget_mode = 99;

		switch (ExerciseData.E_mode)
		{
			case PROC_EXERCISE_TR_HILL: //normal
			case PROC_EXERCISE_TR_FATBURN:
			case PROC_EXERCISE_TR_CARDIO:
			case PROC_EXERCISE_TR_STRENGHT:
			case PROC_EXERCISE_TR_INTERVAL:
			case PROC_EXERCISE_TR_MOUNTANT:
            case PROC_EXERCISE_TR_INCINE:
                itarget_mode = 0 ;
				break;
			case PROC_EXERCISE_TR_TIME: //time
				itarget_mode = 1 ;
				break;
			case PROC_EXERCISE_TR_DISTANCE: //distance
				itarget_mode = 2 ;
				break;
			case PROC_EXERCISE_TR_CALORIES: //calories
				itarget_mode = 3 ;
				break;
			case PROC_EXERCISE_HEARTRATE: //distance
				itarget_mode = 4 ;
				break;
			case PROC_EXERCISE_QUICKSTART: //normal no remain time
			case PROC_EXERCISE_WORKOUT:
			case PROC_EXERCISE_HIIT:
				itarget_mode = 5 ;
				break;
			case PROC_EXERCISE_TR_CONSWATT: //watt
				itarget_mode = 6 ;
				break;
			case PROC_EXERCISE_TR_MANUAL:
				itarget_mode = 7 ;
				break;
			default:
				break;
		}

		return itarget_mode;
	}

	//////////////Exercise Function Start////////////////
	//運動距離 meter
	public static float f_get_total_distance()
	{
		float fval;

		fval = mCaculate_Data.sdistance.ftotal;

		return fval;
	}

	//運動距離 km
	public static float f_get_total_distance_km()
	{
		float fval;

		fval = mCaculate_Data.sdistance.ftotal / 1000;

		return fval;
	}

	//運動距離 km
	public static float f_get_total_distance_show_km()
	{
		float fval;

		fval = (mCaculate_Data.sdistance.ftotal / 1000) - 0.05f;

		return fval;
	}

	//機器運轉未計算在運動距離內的值 km
	public static float f_get_total_distance_km_eng()
	{
		float fval;

		fval = (mCaculate_Data.sdistance_eng.ftotal + CoolDownData.mCaculate_Data.sdistance.ftotal) / 1000;

		return fval;
	}


	//運動時間
	public static int i_get_elapsed_time()
	{
		return i_get_total_time();
	}

	//運動時間
	public static int i_get_total_time()
	{
		int ival;

		ival = mCaculate_Data.itotal_time;

		return ival;
	}

	//機器運轉未計算在運動時間內的值
	public static int i_get_total_time_eng()
	{
		int ival;

		ival = (mCaculate_Data.itotal_time_eng + CoolDownData.mCaculate_Data.itotal_time);

		return ival;
	}

	//剩下秒數
	public static int i_get_remaining_time()
	{
		int ival;

		if(mCaculate_Data.itarget_time > 300)
		{
			ival = mCaculate_Data.itarget_time - mCaculate_Data.itotal_time;
		}
		else
		{
			ival = ExerciseData.list_real_setting.size() * 60 - mCaculate_Data.itotal_time;
		}

		return ival;
	}

	//全部卡洛里
	public static float f_get_total_calories()
	{
		float fval;

		fval = mCaculate_Data.scalories.ftotal;

		return fval;
	}

	//目前每秒卡洛里
	public static float f_get_curr_calories()
	{
		float fval;

		fval = mCaculate_Data.scalories.fcurr;

		return fval;
	}

	//目前每小時卡洛里
	public static float f_get_hour_calories()
	{
		float fval;

		fval = mCaculate_Data.scalories.fcurr * 3600;

		return fval;
	}

	//METS
	public static float f_get_METS()
	{
		float fval;
		float fwatthr;
		float fweight;

		fwatthr = f_get_curr_watt();

		fweight = ExerciseData.f_get_weight();

		fval = ((10.8f * (int)fwatthr / fweight ) + 7f)/3.8f;

		return fval;
	}

	//目前心跳
	public static float f_get_curr_heartrate()
	{
		float fval;

		fval = ExerciseData.fHR_Get();

		return fval;
	}

	//最小心跳
	public static float f_get_min_heartrate()
	{
		float fval;

		fval = mCaculate_Data.sheart_rate.fmin;

		return fval;
	}

	//最大心跳
	public static float f_get_max_heartrate()
	{
		float fval;

		fval = mCaculate_Data.sheart_rate.fmax;

		return fval;
	}

	//平均心跳
	public static float f_get_ave_heartrate()
	{
		float fval;

		fval = mCaculate_Data.sheart_rate.fave;

		return fval;
	}

	//目前步伐
	public static float f_get_curr_pace()
	{
		float fval;

		fval = mCaculate_Data.space.fcurr;

		return fval;
	}

	//最快步伐
	public static float f_get_best_pace()
	{
		float fval;

		fval = mCaculate_Data.space.fmin;

		return fval;
	}

	//最慢步伐
	public static float f_get_worst_pace()
	{
		float fval;

		fval = mCaculate_Data.space.fmax;

		return fval;
	}

	//平均步伐
	public static float f_get_ave_pace()
	{
		float fval;

		fval = mCaculate_Data.space.fave;

		return fval;
	}

	//目前Watt
	public static float f_get_curr_watt()
	{
		float fval;

		fval = mCaculate_Data.swatt.fcurr;

		return fval;
	}

	//平均Watt
	public static float f_get_ave_watt()
	{
		float fval;

		fval = mCaculate_Data.swatt.fave;

		return fval;
	}

	//目前rpm
	public static float f_get_curr_rpm()
	{
		float fval;

		fval = mCaculate_Data.srpm.fcurr;

		return fval;
	}

	//平均rpm
	public static float f_get_ave_rpm()
	{
		float fval;

		fval = mCaculate_Data.srpm.fave;

		return fval;
	}

	//最大rpm
	public static float f_get_max_rpm()
	{
		float fval;

		fval = mCaculate_Data.srpm.fmax;

		return fval;
	}
	//////////////Exercise Function End////////////////


	//////////////Target Parameter Function Start////////////////
	//get target time second
	public static int f_get_target_time()
	{
		int ival;

		ival = mCaculate_Data.itarget_time;

		return ival;
	}

	//set target time second
	public static void v_set_target_time(int ival)
	{
		float fdata ;

		fdata = f_get_target_time() + ival;

		if(fdata < f_get_target_limit_min())
		{
			mCaculate_Data.itarget_time = (int)f_get_target_limit_min();
		}
		else if(fdata > f_get_target_limit_max())
		{
			mCaculate_Data.itarget_time = (int)f_get_target_limit_max();
		}
		else if(fdata <= i_get_total_time()) //不可比當下小
		{

		}
		else
		{
			mCaculate_Data.itarget_time = (int)fdata;
		}

		return ;
	}


	//get target calorie
	public static float f_get_target_calorie()
	{
		float fval;

		fval = mCaculate_Data.ftarget_calorie;

		return fval;
	}

	//set target calorie
	public static void v_set_target_calorie(float fval)
	{
		float fdata ;

		fdata = f_get_target_calorie() + fval;

		if(fdata < f_get_target_limit_min())
		{
			mCaculate_Data.ftarget_calorie = f_get_target_limit_min();
		}
		else if(fdata > f_get_target_limit_max())
		{
			mCaculate_Data.ftarget_calorie = f_get_target_limit_max();
		}
		else if(fdata <= f_get_total_calories()) //不可比當下小
		{

		}
		else
		{
			mCaculate_Data.ftarget_calorie = fdata;
		}

		return;
	}

	//get target watt
	public static float f_get_target_watt()
	{
		float fval = 0;

		fval = WattControl.ftarget_watt;

		return fval;
	}

	//set target watt
	public static void v_set_target_watt(float fval)
	{
		float fdata ;

		fdata = f_get_target_watt() + fval;

		if(fdata < f_get_target_limit_min())
		{
			WattControl.ftarget_watt = f_get_target_limit_min();
		}
		else if(fdata > f_get_target_limit_max())
		{
			WattControl.ftarget_watt = f_get_target_limit_max();
		}
		else
		{
			WattControl.ftarget_watt = fdata;
		}

		return;
	}

	//get target distance meter
	public static float f_get_target_distance()
	{
		float fval;

		fval = mCaculate_Data.ftarget_distance;

		return fval;
	}

	//get target distance km
	public static float f_get_target_distance_km()
	{
		float fval;

		fval = mCaculate_Data.ftarget_distance/1000;

		return fval;
	}

	//set target distance
	public static void v_set_target_distance(float fval)
	{
		float fdata ;

		fdata = f_get_target_distance() + (fval * 1000f);

		if(fdata < f_get_target_limit_min())
		{
			mCaculate_Data.ftarget_distance = f_get_target_limit_min();
		}
		else if(fdata > f_get_target_limit_max())
		{
			mCaculate_Data.ftarget_distance = f_get_target_limit_max();
		}
		else if(fdata <= f_get_total_distance()) //不可比當下小
		{

		}
		else
		{
			mCaculate_Data.ftarget_distance = fdata;
		}

		return;
	}

	//get target heartrate
	public static float f_get_target_heart_rate()
	{
		float fval;

		fval = mCaculate_Data.ftarget_heart_rate;

		return fval;
	}

	//set target heartrate
	public static void v_set_target_heart_rate(float fval)
	{
		float fdata ;

		fdata = f_get_target_heart_rate() + fval;

		if(fdata < f_get_target_limit_min())
		{
			mCaculate_Data.ftarget_heart_rate = f_get_target_limit_min();
		}
		else if(fdata > f_get_target_limit_max())
		{
			mCaculate_Data.ftarget_heart_rate = f_get_target_limit_max();
		}
		else if(fdata <= f_get_curr_heartrate()) //不可比當下小
		{

		}
		else
		{
			mCaculate_Data.ftarget_heart_rate = fdata;
		}

		return ;
	}

	//set limit min/max
	public static void v_set_target_limit(float fmin, float fmax)
	{
		mCaculate_Data.ftarget_min = fmin;
		mCaculate_Data.ftarget_max = fmax;

		return ;
	}

	//get limit min
	public static float f_get_target_limit_min()
	{
		return mCaculate_Data.ftarget_min;
	}

	//get limit max
	public static float f_get_target_limit_max()
	{
		return mCaculate_Data.ftarget_max;
	}

	//target up
	public static void vTarget_up(int itarget_mode, int itimes)
	{
		float fval;

		//1 time; 2 distance; 3 calories; 4 heartrate
		switch (itarget_mode)
		{
			case 1: //time
			case 7: //manual time
				v_set_target_time(60 * itimes);
				break;
			case 2: //distance
				if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
				{
					fval = 0.1f * itimes;
				}
				else
				{
					fval = 0.1f * mile2km * itimes;
				}
				v_set_target_distance(fval);
				break;
			case 3: //calories
				v_set_target_calorie(10 * itimes);
				break;
			case 4: //heartrate
				v_set_target_heart_rate(1 * itimes);
				break;
			case 6: //watt
				v_set_target_watt(10 * itimes);
				break;
			default:
				break;
		}

	}

	//target down
	public static void vTarget_down(int itarget_mode, int itimes)
	{
		float fval;

		//1 time; 2 distance; 3 calories; 4 heartrate
		switch (itarget_mode)
		{
			case 1: //time
			case 7: //manual time
				v_set_target_time(-60 * itimes);
				break;
			case 3: //calories
				v_set_target_calorie(-10 * itimes);
				break;
			case 2: //distance
				if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
				{
					fval = -0.1f * itimes;
				}
				else
				{
					fval = -0.1f * mile2km * itimes;
				}
				v_set_target_distance(fval);
				break;
			case 4: //heartrate
				v_set_target_heart_rate(-1 * itimes);
				break;
			case 6: //watt
				v_set_target_watt(-10 * itimes);
				break;
			default:
				break;
		}

	}

	public static String[] s_Get_target(Context mContext, int itarget_mode)
	{
		String[] sdata = new String[2];
		float fval;
		int ival;

		//1 time; 2 distance; 3 calories; 4 heartrate
		switch (itarget_mode)
		{
			case 1: //time
			case 7: //manual time
				ival = f_get_target_time();
				sdata[0] = Rtx_Calendar.s_trans_integer(5, ival);
				sdata[1] = "";
				break;
			case 2: //distance
				fval = f_get_target_distance_km();
				sdata[0] = EngSetting.Distance.getValString(fval);
				sdata[1] = EngSetting.Distance.getUnitString(GlobalData.global_context);
				break;
			case 3: //calories
				fval = f_get_target_calorie();
				sdata[0] = Rtx_TranslateValue.sFloat2String(fval, 0);
				sdata[1] = "";
				break;
			case 4: //heartrate
				fval = f_get_target_heart_rate();
				sdata[0] = Rtx_TranslateValue.sFloat2String(fval, 0);
				sdata[1] = LanguageData.s_get_string(mContext, R.string.bpm).toLowerCase();
				break;
			case 6: //watt
				fval = f_get_target_watt();
				sdata[0] = Rtx_TranslateValue.sFloat2String(fval, 0);
				sdata[1] = "";
				break;
			default:
				sdata[0] = "";
				sdata[1] = "";
				break;
		}

		sdata[1].toLowerCase();

		return sdata;

	}

	public static boolean bHR_is_void(float fval)
	{
		boolean bret = true;

		if(fval < EngSetting.f_Get_Min_HR() || fval > EngSetting.f_Get_Max_HR())
		{
			bret = false;
		}

		return bret;
	}


	public static String s_get_HR_String()
	{
		float fval;
		String sdata;

		fval = f_get_curr_heartrate();
		if(!bHR_is_void(fval)) {
			if(fval == 0)
			{
				sdata = "";
			}
			else
			{
				if ((ExerciseData.iGet_count_val() / EngSetting.DEF_HSEC_COUNT ) % 2 == 0) {
					sdata = "--";
				} else {
					sdata = "";
				}
			}
		}
		else
		{
			sdata = Rtx_TranslateValue.sFloat2String(fval, 0);
		}

		return sdata;
	}

	public static void v_simple_refresh(Context mContext, ExerciseData.SimpleObj obj)
	{
		int isel;
		String sdata;
		float fval;
		int ival;

		if(obj.i_val_list == null)
		{
			return;
		}

		isel = obj.i_val_list[obj.iChoice];

		obj.sTitle = LanguageData.s_get_string(mContext, obj.i_item_list[obj.iChoice]);

		// 0x10 distance ; 0x11 pace ; 0x12 best pace ; 0x13 avg pace ;
		// 0x14 speed ; 0x15 avg speed ; 0x16 watt; 0x17 RPM; 0x18 Ave RPM; 0x19 Max RPM
		// 0x20 calories ; 0x21 kcal/h ; 0x22 METS
		// 0x30 heartrate ; 0x31 max hr ; 0x32 avg hr
		// 0x40 elapsed time ; 0x41 remanining time
		switch (isel)
		{
			case 0x10:
				fval = f_get_total_distance_show_km();
				obj.sVal = EngSetting.Distance.getValString(fval);
				obj.sUnit = EngSetting.Distance.getUnitString(mContext);
				obj.icon_id = R.drawable.distance_01;
				break;
			case 0x11:
				fval = f_get_curr_pace();
				if(EngSetting.getUnit() != EngSetting.UNIT_METRIC)
				{
					fval *= EngSetting.mile2km;
				}
				obj.sVal = Rtx_Calendar.s_trans_integer(5, (int)fval);
				obj.sUnit = "";
				obj.icon_id = R.drawable.distance_01;
				break;
			case 0x12:
				fval = f_get_best_pace();
				if(EngSetting.getUnit() != EngSetting.UNIT_METRIC)
				{
					fval *= EngSetting.mile2km;
				}
				obj.sVal = Rtx_Calendar.s_trans_integer(5, (int)fval);
				obj.sUnit = "";
				obj.icon_id = R.drawable.distance_01;
				break;
			case 0x13:
				fval = f_get_ave_pace();
				if(EngSetting.getUnit() != EngSetting.UNIT_METRIC)
				{
					fval *= EngSetting.mile2km;
				}
				obj.sVal = Rtx_Calendar.s_trans_integer(5, (int)fval);
				obj.sUnit = "";
				obj.icon_id = R.drawable.distance_01;
				break;
			case 0x14: //bike speed
				fval = f_get_curr_pace();
				obj.sVal = EngSetting.Distance.getValString(fval, 1);
				obj.sUnit = EngSetting.Distance.getSpeedUnitString(mContext).toLowerCase();
				obj.icon_id = R.drawable.speed_icon;
				break;
			case 0x15: //bike avg speed
				fval = f_get_ave_pace();
				obj.sVal = EngSetting.Distance.getValString(fval, 1);
				obj.sUnit = EngSetting.Distance.getSpeedUnitString(mContext).toLowerCase();
				obj.icon_id = R.drawable.speed_icon;
				break;
			case 0x16: //bike watt
				fval = f_get_curr_watt();
				sdata = LanguageData.s_get_string(mContext, R.string.watt);
				obj.sVal = Rtx_TranslateValue.sFloat2String(fval, 0);
				obj.sUnit = "";
				//obj.sUnit = sdata.toLowerCase();
				obj.icon_id = R.drawable.input_watt_icon;
				break;
			case 0x17: //bike rpm
				fval = f_get_curr_rpm();
				sdata = LanguageData.s_get_string(mContext, R.string.rpm);
				obj.sVal = Rtx_TranslateValue.sFloat2String(fval, 0);
				obj.sUnit = "";
				//obj.sUnit = sdata.toLowerCase();
				obj.icon_id = R.drawable.speed_icon;
				break;
			case 0x18: //avg rpm
				fval = f_get_ave_rpm();
				sdata = LanguageData.s_get_string(mContext, R.string.rpm);
				obj.sVal = Rtx_TranslateValue.sFloat2String(fval, 0);
				obj.sUnit = "";
				//obj.sUnit = sdata.toLowerCase();
				obj.icon_id = R.drawable.speed_icon;
				break;
			case 0x19: //max rpm
				fval = f_get_max_rpm();
				sdata = LanguageData.s_get_string(mContext, R.string.rpm);
				obj.sVal = Rtx_TranslateValue.sFloat2String(fval, 0);
				obj.sUnit = "";
				//obj.sUnit = sdata.toLowerCase();
				obj.icon_id = R.drawable.speed_icon;
				break;
			case 0x20:
				fval = f_get_total_calories();
				sdata = LanguageData.s_get_string(mContext, R.string.kcal);
				obj.sVal = Rtx_TranslateValue.sFloat2String(fval, 0);
				obj.sUnit = sdata.toLowerCase();
				obj.icon_id = R.drawable.tr_calories_icon;
				break;
			case 0x21:
				fval = f_get_hour_calories();
				sdata = LanguageData.s_get_string(mContext, R.string.kcal);
				obj.sVal = Rtx_TranslateValue.sFloat2String(fval, 0);
				obj.sUnit = sdata.toLowerCase();
				obj.icon_id = R.drawable.tr_calories_icon;
				break;
			case 0x22:
				fval = f_get_METS();
				sdata = LanguageData.s_get_string(mContext, R.string.mets);
				obj.sVal = Rtx_TranslateValue.sFloat2String(fval, 1);
				obj.sUnit = "";
				obj.icon_id = R.drawable.tr_calories_icon;
				break;
			case 0x30:
				sdata = s_get_HR_String();
				obj.sVal = sdata;
				obj.sUnit = "";
				obj.icon_id = R.drawable.hrc_heart;
				break;
			case 0x31:
				fval = f_get_max_heartrate();
				obj.sVal = Rtx_TranslateValue.sFloat2String(fval, 0);
				obj.sUnit = "";
				obj.icon_id = R.drawable.hrc_heart;
				break;
			case 0x32:
				fval = f_get_ave_heartrate();
				obj.sVal = Rtx_TranslateValue.sFloat2String(fval, 0);
				obj.sUnit = "";
				obj.icon_id = R.drawable.hrc_heart;
				break;
			case 0x40:
				ival = i_get_elapsed_time();
				obj.sVal = Rtx_Calendar.s_trans_integer(5, ival);
				obj.sUnit = "";
				obj.icon_id = R.drawable.tr_time_icon;
				break;
			case 0x41:
				ival = i_get_remaining_time();
				obj.sVal = Rtx_Calendar.s_trans_integer(5, ival);
				obj.sUnit = "";
				obj.icon_id = R.drawable.tr_time_icon;
				break;
			default:
				break;
		}
	}

}
