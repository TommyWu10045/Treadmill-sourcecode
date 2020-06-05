package com.rtx.treadmill.GlobalData;

import java.util.ArrayList;

/**
 * 儲存全域設定
 * @author Tom
 *
 */
public class CoolDownData {
	private static String TAG = "Jerry";
	private static boolean DEBUG = false;

	public static int iCoolDown_status;
	public static long lcount_cooldown_time;  //cooldown調整累積時間(ms)
	public static long lcooldown_ajust ;  //cooldown調整時間(ms)
	public static float lbefore_ajust_time ;  //cooldown調整前info出現時間(ms)
	public static float lcooldown_factor ;  //cooldown調整比

	public static float f_cooldown_min_speed  ; //cooldown min speed
	public static float f_cooldown_min_level  ; //cooldown min level
	public static float f_cooldown_time ; //cooldown time(ms)

	public static long lcooldown_key_check_delay ;  //檢查speed and inclne是否由實體key改變時，須大於此時間，是為了避開此改變是由軟體下的指令(ms)
	public static long lcount_cooldown_key_time;  //soft key調整後累積時間(ms)

	public static int ilist_count ;
	public static ArrayList<DEVICE_COMMAND> list_real_setting = new ArrayList<DEVICE_COMMAND>();
	public static Calculate_info mCaculate_Data = new Calculate_info();

	public static void clear()
	{
		iCoolDown_status = 0x00;
		lcount_cooldown_time = 0;
		lcooldown_ajust = 300000;
		lbefore_ajust_time = 0;
		lcooldown_factor = 0.5f;

		f_cooldown_min_speed = EngSetting.f_Get_Min_Speed();
		f_cooldown_min_level = EngSetting.f_Get_Min_level();
		f_cooldown_time = 300000f;

		lcooldown_key_check_delay = 3000;
		lcount_cooldown_key_time = 0;

		ilist_count = 0;
		list_real_setting.clear();
		mCaculate_Data.clear();
	}

	//get hr control status
	public static int iCD_Get_status()
	{
		return  iCoolDown_status;
	}

	//set hr control status
	public static void vCD_Set_status(int ival)
	{
		iCoolDown_status = ival;
	}

	//set cooldown time(ms)
	public static void v_Set_CD_time(long ltime)
	{
		lcount_cooldown_time = ltime;
	}

	//get cooldown time(ms)
	public static long l_Get_CD_time()
	{
		return lcount_cooldown_time;
	}

	//set softkey after time(ms)
	public static void v_Set_Softkey_time(long ltime)
	{
		lcount_cooldown_key_time = ltime;
	}

	//get softkey after time(ms)
	public static long l_Get_Softkey_time()
	{
		return lcount_cooldown_key_time;
	}

	//check is timeout
	public static boolean bCD_check_timeout(float flimit, float ftime)
	{
		boolean bret = false;
		float fval = ftime;

		if(fval > flimit)
		{
			bret = true;
		}

		return bret;
	}

	public static class DEVICE_COMMAND
	{
		public boolean bcal;		//true : 列入計算； false : 不列入計算，如warm up
		public int isec;			//step time
		public float fspeed;		//speed(km/h)
		public float fincline;		//step incline if treadmill; step level if EP/RB/UB
		public float fheart_rate;   //step heartrate
		public float frpm;		    //for Bike is RPM
	}

	public static class Calculate_info
	{
		public Calc_Data scalories = new Calc_Data();	//calorie
		public Calc_Data sdistance = new Calc_Data();	//unit is meter
		public Calc_Data sheart_rate = new Calc_Data();	//pulse per min.
		public Calc_Data space = new Calc_Data();		//unit is sec per km; //SPEED at EP/RB/UB
		public Calc_Data swatt = new Calc_Data();	    //watt
		public Calc_Data srpm = new Calc_Data();	    //RPM

		public int itotal_time ;		//unit is msec.
		public int icurrent_time ;		//unit is msec.
		public long ltotal_time ;		//unit is sec.
		public long lcurrent_time ;		//unit is sec.

		public int itarget_time ;		//unit is sec.target is time
		public float ftarget_heart_rate;//unit is pulse.target is heart rate
		public float ftarget_distance;	//unit is meter.target is distance
		public float ftarget_calorie;	//unit is cal.target is calorie
		public float ftarget_min ;
		public float ftarget_max ;

		public int ioptimal_time ;		//unit is sec. time optimal

		public void clear()
		{
			scalories.clear();
			sdistance.clear();
			sheart_rate.clear();
			space.clear();
			swatt.clear();
			srpm.clear();

			itotal_time = 0;
			icurrent_time = 0;
			ltotal_time = 0;
			lcurrent_time = 0;

			itarget_time = 0;
			ftarget_heart_rate = 0;
			ftarget_distance = 0;
			ftarget_calorie = 0;
			ftarget_min = 0;
            ftarget_max = 0;

			ioptimal_time = 0;
		}

	}

}
