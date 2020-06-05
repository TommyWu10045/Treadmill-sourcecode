package com.rtx.treadmill.GlobalData;

import com.rtx.treadmill.Dialog.DialogState;
import com.rtx.treadmill.Dialog.Dialog_UI_Info;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * 儲存全域設定
 * @author Tom
 *
 */
public class ExerciseData {
	private static String TAG = "Jerry";
	private static boolean DEBUG = false;

	public static int layer00 = 0;
	public static int layer01 = 1;
	public static int layer02 = 2;
	public static int layer03 = 3;
	public static int layer04 = 4;
	public static int layer05 = 5;
	public static int layer06 = 6;
	public static int layer_max = layer06 + 1;

	public static int Virtual_Uart_layer = layer06;
	public static int Error_layer = layer05;
	public static int Emergency_layer = layer05;
	public static int CountDown_layer = layer05;
	public static int Pause_layer = layer05;
	public static int ChoiceItem_layer = layer05;
	public static int ChnageInfo_layer = layer04;
	public static int BottomInfo_layer = layer03;
	public static int CoolDown_layer = layer02;
	public static int Dialog_layer = layer01;


	public static int E_imode = ExerciseRunState.PROC_NULL.getValue();
	public static ExerciseRunState E_mode = ExerciseRunState.PROC_NULL;
	public static int M_mask_type = 0x0000FF00;
	public static void v_Set_All_E_mode( ExerciseRunState mval)
	{
		E_mode.set_MState_AllValue(mval);
		E_mode = mval;
	}

	public static int iexercise_def_page ; //0:profile 1:simple 2:lap
	public static int iLapMeter = 400;	//操場一圈幾公尺
	public static float fage ;
	public static float fweight ;	//kg
	public static float fheight ;	//cm
	public static int igender ; //0:female 1:male
	public static int itarget_pass_count ;
	public static float fheart_rate ; //※Heart Rate
    public static int icount ; //※loop count

	public static int ilist_count ;
	public static ArrayList<DEVICE_COMMAND> list_original_setting = new ArrayList<DEVICE_COMMAND>();
	public static ArrayList<DEVICE_COMMAND> list_real_setting = new ArrayList<DEVICE_COMMAND>();
	public static Uart_Data1 uart_data1 = new Uart_Data1();
	public static Calculate_info mCaculate_Data = new Calculate_info();

	// -1 : null
	// 0x00 : Complete;
	// 0x01 : Stop key;
	// 0x02 : Emergency;
	// 0x03 : Error
	// 0x04 : Pause key;
	// 0x05 : CoolDown key;
	// 0x06 : Start key;
	// 0x07 : CoolDown Complete;
	// 0x08 : CoolDown Stop key;
	// 0x10 : No Heart Rate Pause
	public static int iFinish_status = -1;
	public static boolean bExercising ; //True is Exercising; False is not Exercising.

	public static int ilist_virtual_count ;
	public static int iprofile_num = 30;//profile draw 預設筆數
	public static int ilist_virtual_num = iprofile_num ;//profile draw筆數
	public static int ilist_max = iprofile_num * 2; //若無profile時，預設筆數
	public static int iprofile_center = 6;//profile shift 筆數 v_Refresh_Time
	public static int iprofile_shift_num = 5;//profile shift 筆數 v_Refresh_Time01
    public static int iStep_time = 60;		//unit is sec 每一格program時間
	public static ArrayList<Float> list_incline = new ArrayList<Float>();
	public static ArrayList<Float> list_speed = new ArrayList<Float>();

	public static boolean bCheckCountDown = false; // false : wait coundown page, true : coundown page

	public static void clear()
	{
		iexercise_def_page = 0;
		fage = EngSetting.fage_def;
		fweight = EngSetting.fweight_def;
		fheight = EngSetting.fheight_def;
		if(CloudDataStruct.CloudData_17.is_Male())
		{
			igender = 1;
		}
		else {
			igender = 0;
		}
		itarget_pass_count = 0;
		fheart_rate = 0;

		ilist_count = 0;
		list_original_setting.clear();
		list_real_setting.clear();
		uart_data1.clear();
		mCaculate_Data.clear();

		ilist_virtual_count = 0;
		list_incline.clear();
		list_speed.clear();

		iFinish_status = -1;
		bExercising = false;

		CoolDownData.clear();
		Change_UI_Info.clear();

		bCheckCountDown = false;

	}

    public static void v_set_finish(int ival)
    {
		iFinish_status = ival;
		if(iFinish_status >= 0x00 && iFinish_status <= 0x03
				|| iFinish_status == 0x07 || iFinish_status == 0x08)
		{
			Bottom_UI_Info.clear();
			Choice_UI_Info.clear();
			Change_UI_Info.clear();
			Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_EXIT);
		}
	}

	public static int i_get_list_count( ) { return ilist_count; }

    public static int i_get_finish()
    {
        return iFinish_status ;
    }

	public static void v_set_exercising(boolean bval)
	{
		bExercising = bval;
	}

	public static boolean b_is_exercising()
	{
		return bExercising ;
	}

	public static void v_set_age(float fval)
	{
		fage = fval;
	}

    public static float f_get_age()
    {
        return fage;
    }

	public static int i_get_age()
	{
		return (int)fage;
	}


	public static void v_set_weight(float fval)
	{
		fweight = fval;
	}

    public static float f_get_weight()
    {
        return fweight ;
    }

    public static void v_set_height(float fval)
	{
		fheight = fval;
	}

    public static float f_get_height()
    {
        return fheight;
    }

    public static void v_set_gender(int ival)
    {
        igender = ival;
    }

    public static int i_get_gender()
	{
		return igender ;
	}

	//get hr value
	public static float fHR_Get()
	{
		return  fheart_rate;
	}

	//set hr value
	public static void vHR_Set(float fval)
	{
		fheart_rate = fval;
	}

    //get icount
    public static int iGet_count_val()
    {
        return  icount;
    }

    //get hr control status
    public static void icount_add()
    {
        icount++;
    }

	public static void bSet_Is_Coundown(boolean bret)
	{
		bCheckCountDown = bret;
	}

	public static boolean bGet_Is_Coundown()
	{
		return bCheckCountDown;
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

	public static class Uart_Data1
	{
		public float fspeed ;				//speed kmh at treadmill ; rpm at EP/RB/UB
		public float fincline;				//incline at treadmill ; level at EP/RB/UB
		public float fheart_rate ;			//※Heart Rate 數值=0時代表停止偵測, 1時代表偵測中, 大於40表示可以顯示數值
		public String serror_code = "00";	//
		public int iemergency_stop_flag ;	//※Emergency Stop Flag : 0=Normal / 1=Emergency Stop(Response & safety恢復後自動清為0
		public int ifan_duty ;				//
		public int ikey_event ;				//※Key_event : 0=none / 1=Pause / 2=Stop / 3=Start / 4=Cool Down

		public void clear()
		{
			fspeed = 0;
			fincline = 0;
			fheart_rate = 0;

			serror_code = "00";
			iemergency_stop_flag = 0;
			ifan_duty = 0;
			ikey_event = 0;

			create_error_code();
		}
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

		public int itotal_time_eng ;	//unit is sec.Device runing Time
		public long ltotal_time_eng ;	//unit is msec.Device runing Time
		public Calc_Data sdistance_eng = new Calc_Data();	//unit is meter.Device runing Distance

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

			itotal_time_eng = 0;
			ltotal_time_eng = 0;
			sdistance_eng.clear();

			itarget_time = 0;
			ftarget_heart_rate = 0;
			ftarget_distance = 0;
			ftarget_calorie = 0;
			ftarget_min = 0;
            ftarget_max = 0;

			ioptimal_time = 0;
		}

	}

	//Create Error and warning common teemap
	public static TreeMap<String, String> uart_error_code_tree;
	public static TreeMap<String, String> uart_warning_code_tree;
	public static void create_error_code_treemap(int imode, String scode, String str) {
		if (scode == null || str == null ) {
			return;
		}

		String scommon = str;

		if(imode == 0) {
			if (uart_error_code_tree == null) {
				create_error_code();
			}
			uart_error_code_tree.put(scode, scommon);
		}
		else
		{
			if (uart_warning_code_tree == null) {
				create_warning_code();
			}
			uart_warning_code_tree.put(scode, scommon);
		}
	}

	public static void create_error_code() {
		if (uart_error_code_tree != null) {
			return;
		}

		uart_error_code_tree = new TreeMap<String, String>();

		create_error_code_treemap(0, "01", "Voltage too low during operation");
		create_error_code_treemap(0, "02", "Temperature sensor error");
		create_error_code_treemap(0, "04", "Output over-current");
		create_error_code_treemap(0, "06", "Input over-current");
		create_error_code_treemap(0, "08", "Ground fault");
		//create_error_code_treema0, p("09", "Over Heat");
		create_error_code_treemap(0, "0A", "Motor overload");
		create_error_code_treemap(0, "0B", "Inverter overload");
		create_error_code_treemap(0, "0C", "System overload");
		create_error_code_treemap(0, "0D", "Motor disconnect");
		create_error_code_treemap(0, "0E", "Dynamic braking error");
		create_error_code_treemap(0, "21", "Memory error");
		create_error_code_treemap(0, "22", "Memory error");
		create_error_code_treemap(0, "23", "Voltage too low");
		create_error_code_treemap(0, "25", "Emergency stop circuit error");
		create_error_code_treemap(0, "29", "Inverter overheat");
		create_error_code_treemap(0, "FE", "Incline fault");
	}

	public static void create_warning_code() {
		if (uart_warning_code_tree != null) {
			return;
		}

		uart_warning_code_tree = new TreeMap<String, String>();

		create_error_code_treemap(1, "30", "Running belt or beck need to maintenance");
		create_error_code_treemap(1, "31", "Running belt or beck need to replace");
		create_error_code_treemap(1, "32", "Driving belt loosen");
	}


	public static String Check_error_code(String scode) {
		String sdata = null;

		if (scode == null ) {
			return sdata;
		}

		if(uart_error_code_tree == null)
		{
			create_error_code();
		}

		sdata = uart_error_code_tree.get(scode);

		return sdata;
	}

	public static String Check_warning_code(String scode) {
		String sdata = null;

		if (scode == null ) {
			return sdata;
		}

		if(uart_warning_code_tree == null)
		{
			create_warning_code();
		}

		sdata = uart_warning_code_tree.get(scode);

		return sdata;
	}

	public static String Check_error_type(String scode) {
		String sdata = "S";

		if (scode == null ) {
			return sdata;
		}

		sdata = Check_error_code(scode);

		if(sdata == null)
		{
			sdata = Check_warning_code(scode);
			if(sdata == null)
			{
				if(!ExerciseData.bExercising)
				{
					sdata = "S";
				}
				else
				{
					sdata = "R";
				}
			}
			else
			{
				sdata = "W";
			}
		}
		else
		{
			sdata = "E";
		}

		return sdata;
	}
	/////////////////////////////////////////////
	public static class SimpleObj
	{
		public int i_Item;
		public int icon_id;
		public String sVal = "";
		public String sUnit = "";
		public String sTitle = "";
		public int iArrow;
		public int iChoice;
		public int[] i_item_list;
		public int[] i_val_list;
		public int i_item_type; //0 no triangle; 1 triangle;

		public int ixcenter;

	}



}
