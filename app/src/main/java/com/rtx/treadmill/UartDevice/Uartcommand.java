package com.rtx.treadmill.UartDevice;

import android.util.Log;

import com.retronix.circleuart.guart;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxTools.WriteLogUtil;

/**
 * hide status and software button
 * 
 */
public class Uartcommand {
	private String TAG = "Jerry";
	private boolean DEBUG = true;
	private float fTempBikeLevel = 0;

	//Create
	public Uartcommand() {
		if (DEBUG) Log.d(TAG, "----------Create");
	}

	private int cal_uart_num(int iwlen, String sdata) {
		if(sdata == null)
		{
			return 0 ;
		}

		byte[] b = sdata.getBytes();
		int i = 0 ;

		if (DEBUG) Log.d(TAG, "----------cal_uart_num=" + b.length);
		if (b.length > 0)
		{
			for(i = 0 ; i < b.length ; i++)
			{
				guart.bwbuf[iwlen+i] = b[i];
				UartData.bWriteBuf[iwlen+i] = b[i];
			}
		}

		return i;
	}

	//return 0:pass, 1:fail.
	//argv imode 0:check tm.iwlen == iwlen, 1:don't care iwlen
	public int uart_command_string(int imode , int cmd , String sh) {
		guart.uart_command tm = guart.uart_command_tree.get(cmd);
		int i;
		int iwlen = 0;
		int result = 1 ;
		int ret ;

		if (DEBUG) Log.d(TAG, "----------uart_command_string imode=" + imode + " cmd=" + cmd + " sh=" + sh);
		if(tm!=null)
		{
			for (i = 0; i < tm.bwbuf.bcmd.length; i++) {
				guart.bwbuf[iwlen] = tm.bwbuf.bcmd[i];
				UartData.bWriteBuf[iwlen] = tm.bwbuf.bcmd[i];

				iwlen++;
			}
			ret = cal_uart_num(iwlen, sh);
			if (DEBUG) Log.d(TAG, "----------ret=" + ret);
			iwlen += ret ;
			for (i = 0; i < tm.bwbuf.bend.length; i++)
			{
				guart.bwbuf[iwlen] = tm.bwbuf.bend[i];
				UartData.bWriteBuf[iwlen] = tm.bwbuf.bend[i];
				iwlen++;
			}
			guart.irlen = 0;

			if (DEBUG) Log.d(TAG, "----------imode=" + imode + "-----tm.iwlen=" + tm.iwlen +"-----iwlen=" + iwlen);

			if (imode == 0) {
				if (tm.iwlen == iwlen) {
					guart.iwlen = iwlen;
					UartData.iWriteLen = iwlen;
					result = 0;
				}
			}
			else
			{
				guart.iwlen = iwlen;
				UartData.iWriteLen = iwlen;
				result = 0;
			}
		}

//		if(cmd == 65)
//		{
//			Log.e("Jerry", "----------imode=" + imode + "-----tm.iwlen=" + tm.iwlen + "-----iwlen=" + iwlen);
//			Log.e("Jerry","UartData.iWriteLen = " + UartData.iWriteLen);
//			int iIndex;
//			for(iIndex = 0 ; iIndex<20 ; iIndex++)
//			{
//				Log.e("Jerry","UartData.bWriteBuf[" + iIndex + "] = " + String.format("0x%02x", UartData.bWriteBuf[iIndex]));
//			}
//		}

		return result ;
	}

	//return 0:pass, 1:fail.
	//write data by guart command.
	public int uart_command_write( int cmd ) {
		guart.uart_command tm = guart.uart_command_tree.get(cmd);
		int i;
		int iwlen = 0;
		int result = 1 ;

		if (DEBUG) Log.d(TAG, "----------uart_command_write cmd=" + cmd);
		if(tm!=null)
		{
			for (i = 0; i < tm.bwbuf.bcmd.length; i++) {
				guart.bwbuf[iwlen] = tm.bwbuf.bcmd[i];
				iwlen++;
			}
			if( tm.bwbuf.bdata != null) {
				for (i = 0; i < tm.bwbuf.bdata.length; i++) {
					guart.bwbuf[iwlen] = tm.bwbuf.bdata[i];
					iwlen++;
				}
				for (i = 0; i < tm.bwbuf.bend.length; i++) {
					guart.bwbuf[iwlen] = tm.bwbuf.bend[i];
					iwlen++;
				}
				guart.irlen = 0;
				if (DEBUG) Log.d(TAG, "----------tm.iwlen=" + tm.iwlen +"-----iwlen=" + iwlen);
				if(tm.iwlen == iwlen)
				{
					guart.iwlen = iwlen;
					result = 0 ;
				}
			}

			{
				byte[] bData= new byte[iwlen];
				UartData.iWriteLen = iwlen;

				for (i = 0; i < iwlen; i++)
				{
					if (DEBUG) {
						Log.d(TAG, "----------guart.bwbuf[" + i + "]=" + guart.bwbuf[i]);
					}
					bData[i] = guart.bwbuf[i];
					UartData.bWriteBuf[i] = guart.bwbuf[i];
				}
			}
		}
		return result ;
	}


	//By Alan for 0x30
	public int uart_command_write( int cmd ,String Scmd) {
		guart.uart_command tm = guart.uart_command_tree.get(cmd);
		int i;
		int iwlen = 0;
		int result = 1 ;
		byte[] ScmdTemp = Scmd.getBytes();

		if (DEBUG) Log.d(TAG, "----------uart_command_write cmd=" + cmd);
		if(tm!=null)
		{
			for (i = 0; i < tm.bwbuf.bcmd.length; i++) {
				guart.bwbuf[iwlen] = tm.bwbuf.bcmd[i];
				iwlen++;
			}
			if( tm.bwbuf.bdata != null) {
				for (i = 0; i < tm.bwbuf.bdata.length; i++) {
					guart.bwbuf[iwlen] = ScmdTemp[i];
					iwlen++;
				}
				for (i = 0; i < tm.bwbuf.bend.length; i++) {
					guart.bwbuf[iwlen] = tm.bwbuf.bend[i];
					iwlen++;
				}
				guart.irlen = 0;
				if (DEBUG) Log.d(TAG, "----------tm.iwlen=" + tm.iwlen +"-----iwlen=" + iwlen);
				if(tm.iwlen == iwlen)
				{
					guart.iwlen = iwlen;
					result = 0 ;
				}
			}

			{
				byte[] bData= new byte[iwlen];
				UartData.iWriteLen = iwlen;

				for (i = 0; i < iwlen; i++)
				{
					if (DEBUG) {
						Log.d(TAG, "----------guart.bwbuf[" + i + "]=" + guart.bwbuf[i]);
					}
					bData[i] = guart.bwbuf[i];
					UartData.bWriteBuf[i] = guart.bwbuf[i];
				}
			}
		}
		return result ;
	}

	//respnse pass or ng;
	//return 0:pass, 1:fail.
	//write guart command pass or ng.imode=0:pass, 1:ng
	public int uart_command_write( int imode , int cmd ) {
		guart.uart_command tm = guart.uart_command_tree.get(cmd);
		int i;
		int iwlen = 0;
		int result = 1 ;

		if(tm!=null)
		{
			if(tm.bwbuf.bcmd != null) {
				for (i = 0; i < tm.bwbuf.bcmd.length; i++) {
					guart.bwbuf[iwlen] = tm.bwbuf.bcmd[i];
					iwlen++;
				}
				guart.bwbuf[0] = 'R';
			}

			if( imode == 0) {
				guart.bwbuf[iwlen] = guart.bOK;
			}
			else
			{
				guart.bwbuf[iwlen] = guart.bNG;
			}
			iwlen++;

			if(tm.bwbuf.bend != null) {
				for (i = 0; i < tm.bwbuf.bend.length; i++) {
					guart.bwbuf[iwlen] = tm.bwbuf.bend[i];
					iwlen++;
				}
			}
			guart.irlen = 0;
			if (DEBUG) Log.d(TAG, "-----iwlen=" + iwlen);
			if(6 == iwlen)
			{
				guart.iwlen = iwlen;
				result = 0 ;

				{
					byte[] bData= new byte[iwlen];
					UartData.iWriteLen = iwlen;

					for (i = 0; i < iwlen; i++)
					{
						if (DEBUG) {
							Log.d(TAG, "----------guart.bwbuf[" + i + "]=" + guart.bwbuf[i]);
						}
						bData[i] = guart.bwbuf[i];
						UartData.bWriteBuf[i] = guart.bwbuf[i];
					}

				}
			}
		}
		return result ;
	}

	//imode:0 => return 0:pass, 1:fail.
	//imode:1 => return byte[3]
	//imode:2 => return 0:pass, 1:fail. only check 0x0D and 0x0A
	public int uart_command_check_result( int imode ,int icmd ) {
		int ret = 1 ;

		if(guart.irlen >= 6) {
			if(DEBUG) {
				for (int i = 0; i < guart.irlen; i++) {
					Log.d(TAG, "guart.rbuf[" + i + "]=" + guart.brbuf[i]);
				}
			}
			guart.uart_command tm = guart.uart_command_tree.get(icmd);
			if (tm != null) {
				if (guart.brbuf[guart.irlen - 2] == 0x0D && guart.brbuf[guart.irlen - 1] == 0x0A)
				{
					if (imode == 0) {
						if(guart.brbuf[0] == 'R' && guart.brbuf[1] == tm.bwbuf.bcmd[1] && guart.brbuf[2] == tm.bwbuf.bcmd[2] ) {
							if (guart.brbuf[3] == guart.bOK) {
								ret = 0;
							}
						}
					}
					else if (imode == 1) {
						if(guart.brbuf[0] == 'R' && guart.brbuf[1] == tm.bwbuf.bcmd[1] && guart.brbuf[2] == tm.bwbuf.bcmd[2] ) {
							ret = guart.brbuf[3];
						}
					}else if (imode == 2) {
						ret = 0;
					}
				}
			}
		}

		if (DEBUG) Log.e(TAG, "----------icmd=" + icmd + "====ret=" + ret );
		return ret ;
	}

	//return 0:pass, 1:fail.
	public int uart_command_write_ok( int icmd ) {
		return uart_command_write(0, icmd) ;
	}

	//return 0:pass, 1:fail.
	public int uart_command_write_ng( int icmd ) {
		return uart_command_write(1, icmd) ;
	}

	public boolean bData1_parser()
	{
		boolean bResult = false;

		String str = null;
		String sdata = "";

		try{
			if(guart.irlen > 6) {
				str = new String(guart.brbuf, 3, guart.irlen - 5);
			}
		}
		catch (Exception e)
		{

		}

		if(str == null)
		{
			return bResult;
		}

		String[] array;
		array = str.split(",");

		if(array.length == 7)
		{
			ExerciseData.uart_data1.fspeed = EngSetting.Distance.fTranslat2KM(Rtx_TranslateValue.fString2Float(array[0])/10);//speed
			ExerciseData.uart_data1.fincline = Rtx_TranslateValue.fString2Float(array[1])/10;//incline
			ExerciseData.uart_data1.fheart_rate = Rtx_TranslateValue.fString2Float(array[2]);//heartrate
			ExerciseData.uart_data1.serror_code =array[3].toUpperCase();	//Error Code
			ExerciseData.uart_data1.iemergency_stop_flag = Rtx_TranslateValue.iString2Int(array[4]); //0:normal; 1: emergency
			ExerciseData.uart_data1.ifan_duty = Rtx_TranslateValue.iString2Int(array[5]);	//fan duty
			ExerciseData.uart_data1.ikey_event = Rtx_TranslateValue.iString2Int(array[6]);	//0:none; 1:pause; 2:stop; 3:start; 4:cooldown

			bResult = true;

			if (DEBUG)
			{
				Log.e(TAG, "--------fspeed=" + ExerciseData.uart_data1.fspeed );
				Log.e(TAG, "--------fincline=" + ExerciseData.uart_data1.fincline );
				Log.e(TAG, "--------fheart_rate=" + ExerciseData.uart_data1.fheart_rate );
				Log.e(TAG, "--------serror_code=" + ExerciseData.uart_data1.serror_code );
				Log.e(TAG, "--------iemergency_stop_flag=" + ExerciseData.uart_data1.iemergency_stop_flag );
				Log.e(TAG, "--------ifan_duty=" + ExerciseData.uart_data1.ifan_duty );
				Log.e(TAG, "--------ikey_event=" + ExerciseData.uart_data1.ikey_event );
			}


		}
		else if(array.length == 5)
		{
			ExerciseData.uart_data1.fspeed = Rtx_TranslateValue.fString2Float(array[0]); //RMP
			ExerciseData.uart_data1.fincline = Rtx_TranslateValue.fString2Float(array[1]); //LEVEL
			ExerciseData.uart_data1.fheart_rate = Rtx_TranslateValue.fString2Float(array[2]);//heartrate
			ExerciseData.uart_data1.ikey_event = Rtx_TranslateValue.iString2Int(array[3]);//0:none; 1:pause; 2:stop; 3:start;
			ExerciseData.uart_data1.ifan_duty = Rtx_TranslateValue.iString2Int(array[4]);//fan duty

			{
				if(fTempBikeLevel != ExerciseData.uart_data1.fincline)
				{
					//20190201 規格變更 使用 F20 cmd
					UartData.set_uart_resistance(ExerciseData.uart_data1.fincline);
					fTempBikeLevel = ExerciseData.uart_data1.fincline;
				}
			}

			bResult = true;
			if (DEBUG)
			{
				Log.e(TAG, "--------frpm=" + ExerciseData.uart_data1.fspeed );
				Log.e(TAG, "--------flevel=" + ExerciseData.uart_data1.fincline );
				Log.e(TAG, "--------fheart_rate=" + ExerciseData.uart_data1.fheart_rate );
				Log.e(TAG, "--------ikey_event=" + ExerciseData.uart_data1.ikey_event );
				Log.e(TAG, "--------ifan_duty=" + ExerciseData.uart_data1.ifan_duty );
			}
		}
		else if(array.length == 4)
		{
			ExerciseData.uart_data1.fspeed = Rtx_TranslateValue.fString2Float(array[0]); //RMP
			ExerciseData.uart_data1.fincline = Rtx_TranslateValue.fString2Float(array[1]); //LEVEL
			ExerciseData.uart_data1.fheart_rate = Rtx_TranslateValue.fString2Float(array[2]);//heartrate
			ExerciseData.uart_data1.ikey_event = Rtx_TranslateValue.iString2Int(array[3]);//0:none; 1:pause; 2:stop; 3:start;

			{
				if(fTempBikeLevel != ExerciseData.uart_data1.fincline)
				{
					//20190201 規格變更 使用 F20 cmd
					UartData.set_uart_resistance(ExerciseData.uart_data1.fincline);
					fTempBikeLevel = ExerciseData.uart_data1.fincline;
				}
			}

			bResult = true;
			if (DEBUG)
			{
				Log.e(TAG, "--------frpm=" + ExerciseData.uart_data1.fspeed );
				Log.e(TAG, "--------flevel=" + ExerciseData.uart_data1.fincline );
				Log.e(TAG, "--------fheart_rate=" + ExerciseData.uart_data1.fheart_rate );
				Log.e(TAG, "--------ikey_event=" + ExerciseData.uart_data1.ikey_event );
			}
		}

		if(bResult)
		{
			if(ExerciseData.Check_error_type(ExerciseData.uart_data1.serror_code) == "E") //error
			{
				WriteLogUtil.vWrite_LogInfo_Code(true);
				ExerciseData.v_set_finish(0x03);
			}
			else if(ExerciseData.Check_error_type(ExerciseData.uart_data1.serror_code) == "W") //warning
			{
				WriteLogUtil.vWrite_LogInfo_Code(true);
			}
			else if(ExerciseData.uart_data1.iemergency_stop_flag == 1) //emergency
			{
				ExerciseData.v_set_finish(0x02);
			}
			else if(ExerciseData.uart_data1.ikey_event == 1) //pause
			{
				ExerciseData.v_set_finish(0x04);
			}
			else if(ExerciseData.uart_data1.ikey_event == 2) //stop
			{
				ExerciseData.v_set_finish(0x01);
			}
			else if(ExerciseData.uart_data1.ikey_event == 3) //start
			{
				ExerciseData.v_set_finish(0x06);
			}
			else if(ExerciseData.uart_data1.ikey_event == 4) //cooldonw
			{
				ExerciseData.v_set_finish(0x05);
			}
		}

		return bResult;
	}

}
