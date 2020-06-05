package com.rtx.treadmill.UartDevice;

import android.util.Log;

import com.retronix.circleuart.UartCommunication;
import com.retronix.circleuart.UartCommunicationVirtual;
import com.retronix.circleuart.guart;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.RtxTools.Rtx_Log;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

import java.util.ArrayList;

/**
 * 儲存全域設定
 * @author Tom
 * uart command
 */
public class
UartData {
	private static String TAG = "Jerry";
	private static boolean DEBUG = false;

	public static UartCommunication m_UartCommunicationThread = null ;
	public static UartCommunicationVirtual m_UartCommunicationVirtualThread = null ;

	public static byte[] bWriteBuf = new byte[512];
	public static int iWriteLen = 0;
	public static int imode_write_mask = 0xF0;
	public static int imode_read_mask = 0x0F;
	public static int iuart_minmum_delay = 20;//ms
	public static String MSTempNum;

	public static int iCmd_index = 0;
	public static ArrayList<S_CMD> UartCMD_List = new ArrayList<S_CMD>();

	public static class S_CMD{
		// 0x00 : idle ;
		// 0x1* : write for only cmd and default scmd(define at library); 0x2* : write for String cmd;
		// 0x*1 : read pass or ng ; 0x*2 : read byte[3] ; 0x*3 : read only check 0x0D and 0x0A
		public int imode = 0x00; //如上說明
		public int imode_keep = 0x00; //retry 時回復的command
		public int iretry_max = 2; //command retry 次數(retry次數+第一次command=total次數)
		public int iretry_count = 0; //command retry 次數累加
		public int icmd = 0; //command
		public String scmd = ""; //command的argument
		public int iprocdelay = 0; //delay time(ms) after send command
		public int iresult = -1; //command return info
		public int idelay = 0; //command timeout時間

		public void clear()
		{
			imode = 0x00;
			imode_keep = 0x00;
			iretry_max = 2;
			iretry_count = 0;
			icmd = 0;
			scmd = "";
			iprocdelay = 0;
			idelay = 0;

			iresult = -1;

		}
	}

	/**
	 * uart command function
	 *
	 * vUartCmd_05(int idelay, String scmd)
	 * vUartCmd_10(int idelay)
	 * 13 set_uart_speed(float fval)
	 * 14 set_uart_incline(float fval)
	 * vUartCmd_15(int idelay)
	 * vUartCmd_16(int idelay)
	 * 17 set_uart_level(float flvl, float fres)
	 * 18 set_uart_machine_parameter(String scmd)
	 * vUartCmd_61(int idelay, String scmd)
	 * 62 set_uart_add_sub(float fadd, float fval)
	 * vUartCmd_64(int idelay, String scmd)
	 * 65 set_uart_limit_treadmill(float fsmin, float fsmax, float fimin, float fimax)
	 * 65 set_uart_limit_bike(float fsmax)
	 *
	 *
	 */

	//scmd : '0' 公制單位 ; '1' 英制單位
	public static void vUartCmd_05(int idelay, String scmd) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x21;
		uartcmd.imode_keep = 0x21;
		uartcmd.icmd = guart.SET_UNIT;
		uartcmd.scmd = scmd;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_05==" + scmd);
	}

	public static void vUartCmd_10(int idelay) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x11;
		uartcmd.imode_keep = 0x11;
		uartcmd.icmd = guart.SET_RESET;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_10==");
	}

	public static void vUartCmd_speed_13(int idelay, String scmd) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x21;
		uartcmd.imode_keep = 0x21;
		uartcmd.icmd = guart.SET_SPEED;
		uartcmd.scmd = scmd;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_speed_13==" + scmd);
	}

	public static void vUartCmd_incline_14(int idelay, String scmd) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x21;
		uartcmd.imode_keep = 0x21;
		uartcmd.icmd = guart.SET_INCLINE;
		uartcmd.scmd = scmd;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_incline_14==" + scmd);
	}

	public static void vUartCmd_15(int idelay) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x13;
		uartcmd.iretry_max = 1;
		if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7) {
			uartcmd.icmd = guart.GET_READ_DATA1;
		}
		else
		{
			uartcmd.icmd = guart.GET_READ_DATA2;
		}
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_15==" + idelay);
	}

	public static void vUartCmd_16(int idelay) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x13;
		uartcmd.iretry_max = 1;
		uartcmd.icmd = guart.GET_READ_DATA2;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_16==");
	}

	public static void vUartCmd_level_17(int idelay, String scmd) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x21;
		uartcmd.imode_keep = 0x21;
		uartcmd.icmd = guart.SET_LEVEL;
		uartcmd.scmd = scmd;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_level_17==" + scmd);
	}

	public static void vUartCmd_18(int idelay, String scmd) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x21;
		uartcmd.imode_keep = 0x21;
		uartcmd.icmd = guart.SET_MACHINE_PAR;
		uartcmd.scmd = scmd;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_18==" + scmd);
	}

	public static void vUartCmd_19(int idelay, String scmd) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x31;
		uartcmd.imode_keep = 0x31;
		uartcmd.icmd = guart.SET_WATT;
		uartcmd.scmd = scmd;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_incline_14==" + scmd);
	}


	//20190201 set Resistance
	public static void vUartCmd_20(int idelay, String scmd) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x21;
		uartcmd.imode_keep = 0x21;
		uartcmd.icmd = guart.SET_RESISTANCE;
		uartcmd.scmd = scmd;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_20==" + scmd);
	}

	//scmd : '1' pause ; '2' stop ; '3' start/resume
	public static void vUartCmd_61(int idelay, String scmd) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x21;
		uartcmd.imode_keep = 0x21;
		uartcmd.icmd = guart.SET_STOP;
		uartcmd.scmd = scmd;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_61==" + scmd);
	}

	public static void vUartCmd_62(int idelay, String scmd) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x21;
		uartcmd.imode_keep = 0x21;
		uartcmd.icmd = guart.SET_SPEED_ADD;
		uartcmd.scmd = scmd;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_62==" + scmd);
	}

	//scmd : '0' Fan off ; '1' Fan Low ; '2' Fan Hi
	public static void vUartCmd_63(int idelay, String scmd) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x21;
		uartcmd.imode_keep = 0x21;
		uartcmd.icmd = guart.SET_FAN_CONTROL;
		uartcmd.scmd = scmd;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_63==" + scmd);
	}

	//scmd : '0' disable external key ; '1' enable external key
	public static void vUartCmd_64(int idelay, String scmd) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x21;
		uartcmd.imode_keep = 0x21;
		uartcmd.icmd = guart.SET_KEY_ENABLE;
		uartcmd.scmd = scmd;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_64==" + scmd);
	}

	public static void vUartCmd_65(int idelay, String scmd) {
		S_CMD uartcmd = new S_CMD();
		uartcmd.imode = 0x21;
		uartcmd.imode_keep = 0x21;
		uartcmd.icmd = guart.SET_LIMIT;
		uartcmd.scmd = scmd;
		uartcmd.iprocdelay = i_Uart_MCU_Physical_delay(idelay);
		uartcmd.idelay = i_Uart_Check_Result_delay(uartcmd);
		UartCMD_List.add(uartcmd);
		if(DEBUG) Log.e(TAG, "============vUartCmd_65==" + scmd);
	}

	public static void vUartCmd_retry(S_CMD uartcmd) {
		uartcmd.iretry_count++;
		if(uartcmd.iretry_count <= uartcmd.iretry_max)
		{
			uartcmd.imode = uartcmd.imode_keep;
		}
		else
		{
			iCmd_index++;
		}
	}

	public static S_CMD vUartCmd_get_list(int index) {
		S_CMD uartcmd = null;
		if(index < 0)
		{
			Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : [Unknown Error]index = " + index);
		}
		if(UartCMD_List.size() > index && index >= 0) {
			uartcmd = UartCMD_List.get(index);
		}

		return uartcmd;
	}

	public static boolean b_Can_clear_UartCmd() {
		boolean bret = false;

		if(iCmd_index >= UartCMD_List.size() ) {
			bret = true;
		}

		return bret;
	}

	public static void vUartCmd_clean_list(int index) {
		if(UartCMD_List.size() > index) {
			UartCMD_List.remove(index);
		}
	}

	public static int vUartCmd_get_list_size() {
		return UartCMD_List.size();
	}

	public static void vUartCmd_clean_all() {
		if(iCmd_index >= UartCMD_List.size()) {
			UartCMD_List.clear();
			iCmd_index = 0;
		}
	}

	public static void vUartCmd_clean_all(boolean force) {
		if(iCmd_index >= UartCMD_List.size() || force) {
			UartCMD_List.clear();
			iCmd_index = 0;
		}
	}

	//set_uart_speed 13
	public static boolean set_uart_speed(float fval)
	{
		String sdata;
		//20190103 新增規則 一旦超過最小/最大值則小數點無條件捨去
		if(fval > EngSetting.f_Get_Max_Speed() && (fval - 1) < EngSetting.f_Get_Max_Speed())
		{
			fval = EngSetting.f_Get_Max_Speed();
		}
		if(fval < EngSetting.f_Get_Min_Speed() && (fval + 1) > EngSetting.f_Get_Min_Speed())
		{
			fval = EngSetting.f_Get_Min_Speed();
		}
		int ival = (int)(Rtx_TranslateValue.fRoundingVal(EngSetting.Distance.getVal(fval),1) * 10);
		//sdata = Rtx_TranslateValue.sInt2String(ival);
		sdata = Rtx_TranslateValue.sInt2String(ival, 3);
		if(sdata == null || sdata.compareTo("") == 0)
		{
			return false;
		}

		vUartCmd_speed_13(20, sdata);

		return true;
	}

	//set_uart_incline 14
	public static boolean set_uart_incline(float fval)
	{
		String sdata;
		int ival = (int)(fval * 10);

		//sdata = Rtx_TranslateValue.sInt2String(ival);
		sdata = Rtx_TranslateValue.sInt2String(ival, 3);

		if(sdata == null || sdata.compareTo("") == 0)
		{
			return false;
		}

		vUartCmd_incline_14(20, sdata);
		return true;
	}

	//set_uart_level 17
	public static boolean set_uart_level(float flvl)
	{
		boolean bret = false;
		int ilvl = (int)flvl - 1;
		float fres = EngSetting.f_Get_fmachine_rn(ilvl);

		if(fres != 0)
		{
			bret = set_uart_level(flvl, fres);
		}

		return bret;
	}

	public static boolean set_uart_level(float flvl, float fres)
	{
		String sdata, slvl, sres;
		int ilvl = (int)(flvl * 1);
		int ires = (int)(fres * 1);

		//slvl = Rtx_TranslateValue.sInt2String(ilvl);
		//sres = Rtx_TranslateValue.sInt2String(ires);

		slvl = Rtx_TranslateValue.sInt2String(ilvl, 2);
		sres = Rtx_TranslateValue.sInt2String(ires, 4);

		if(slvl == null || slvl.compareTo("") == 0)
		{
			return false;
		}

		if(sres == null || sres.compareTo("") == 0)
		{
			return false;
		}

		sdata = slvl + ',' + sres;

		vUartCmd_level_17(50, sdata);
		return true;
	}

	//set_uart_machine_parameter_treadmill 18
	public static boolean set_uart_machine_parameter(String scmd)
	{
		String sdata;

		if(scmd == null || scmd.compareTo("") == 0)
		{
			return false;
		}

		sdata = scmd;

		vUartCmd_18(200, sdata);
		return true;
	}

	//set_uart_watt 19 By Alan
	public static boolean set_uart_watt(int fval)
	{
		String fvalValue=Integer.toString(fval);
		fvalValue = String.format("%03d",Integer.valueOf(fvalValue));
		vUartCmd_19(20, fvalValue);
		return true;
	}

	//set_uart_resistance 20
	public static boolean set_uart_resistance(float flvl)
	{
		boolean bret = false;
		int ilvl = (int)flvl - 1;
		float fres = EngSetting.f_Get_fmachine_rn(ilvl);

		if(fres != 0)
		{
			bret = set_uart_resistance(flvl, fres);
		}

		return bret;
	}

	public static boolean set_uart_resistance(float flvl, float fres)
	{
		String sdata, sres;

		int ires = (int)(fres * 1);

		sres = Rtx_TranslateValue.sInt2String(ires, 4);

		if(sres == null || sres.compareTo("") == 0)
		{
			return false;
		}

		sdata = sres;

		vUartCmd_20(50, sdata);
		return true;
	}

	//set_uart_add_sub 62
	public static boolean set_uart_add_sub(float fadd, float fval)
	{
		String sdata, slvl, sres;
		int ilvl = (int)(fadd);
		int ires = (int)(fval * 10);

		slvl = Rtx_TranslateValue.sInt2String(ilvl);
		sres = Rtx_TranslateValue.sInt2String(ires);
		if(slvl == null || slvl.compareTo("") == 0)
		{
			return false;
		}

		if(sres == null || sres.compareTo("") == 0)
		{
			return false;
		}

		sdata = slvl + '/' + sres;

		vUartCmd_62(100, sdata);
		return true;
	}

	//set_uart_limit_treadmill 65
	public static boolean set_uart_limit_treadmill(float fsmin, float fsmax, float fimin, float fimax)
	{
		String sdata, stype, ssmin, ssmax, simin, simax;
		int ismin, ismax, iimin, iimax, itimes;

		itimes = 10;
		ismin = (int)(fsmin * itimes);
		ismax = (int)(fsmax * itimes);
		iimin = (int)(fimin * itimes);
		iimax = (int)(fimax * itimes);



		if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6)
		{
			MSTempNum = "3";
		}

		if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
		{
			MSTempNum = "2";
		}

		if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING)
		{
			MSTempNum = "1";
		}

		stype = MSTempNum;

		ssmin = Rtx_TranslateValue.sInt2String(ismin, 3);
		ssmax = Rtx_TranslateValue.sInt2String(ismax, 3);
		simin = Rtx_TranslateValue.sInt2String(iimin, 3);
		simax = Rtx_TranslateValue.sInt2String(iimax, 3);

		//ssmin = Rtx_TranslateValue.sInt2String(ismin);
		//ssmax = Rtx_TranslateValue.sInt2String(ismax);
		//simin = Rtx_TranslateValue.sInt2String(iimin);
		//simax = Rtx_TranslateValue.sInt2String(iimax);

		if(ssmin == null || ssmin.compareTo("") == 0)
		{
			return false;
		}

		if(ssmax == null || ssmax.compareTo("") == 0)
		{
			return false;
		}

		if(simin == null || simin.compareTo("") == 0)
		{
			return false;
		}

		if(simax == null || simax.compareTo("") == 0)
		{
			return false;
		}


		sdata = stype + ',' + ssmin + ',' + ssmax + ',' + simin + ',' + simax;

		vUartCmd_65(200, sdata);
		return true;
	}

	//set_uart_limit_bike 65
	public static boolean set_uart_limit_bike(float fsmax)
	{
		String sdata, stype, ssmin, ssmax;
		int ismax, itimes;

		itimes = 1;
		ismax = (int)(fsmax * itimes);

		stype = "5";
		ssmax = Rtx_TranslateValue.sInt2String(ismax, 2);
		//ssmax = Rtx_TranslateValue.sInt2String(ismax);

		if(ssmax == null || ssmax.compareTo("") == 0)
		{
			return false;
		}

		sdata = stype + ',' + ssmax;

		vUartCmd_65(200, sdata);
		return true;
	}

	public static int i_Uart_MCU_Physical_delay(int idelay) {
		int ival;

		ival = ((iuart_minmum_delay + idelay)/ EngSetting.DEF_UNIT_TIME);
		if(DEBUG) Log.e(TAG, "i_Uart_MCU_Physical_delay==" + ival);
		return ival;
	}

	public static int i_Uart_Check_Result_delay(S_CMD uartcmd) {
		int ival;

		ival = 2 + uartcmd.iprocdelay * (uartcmd.iretry_max + 1);
		if(DEBUG) Log.e(TAG, "i_Uart_Check_Result_delay==" + ival);
		return ival;
	}
}
