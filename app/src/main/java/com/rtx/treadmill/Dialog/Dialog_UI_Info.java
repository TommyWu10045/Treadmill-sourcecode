package com.rtx.treadmill.Dialog;

import android.widget.ImageView;

/**
 * 儲存全域設定
 * @author Tom
 *
 */

public class Dialog_UI_Info
{

	public static DialogState Dialog_mode = DialogState.PROC_IDLE;
	public static int iButton_info = 0x00;
	public static int iCloud_cmd = 0;

	public static void v_Set_Dialog_mode(DialogState mval)
	{
		Dialog_mode = mval;
		v_Set_Dialog_Button(i_Button_Idle);
	}

	public static int i_Button_Idle = 0x00;
	public static int i_Button01 = 0x01;
	public static int i_Button02 = 0x02;
	public static void v_Set_Dialog_Button(int ival)
	{
		iButton_info = ival;
	}
	public static int v_Get_Dialog_Button()
	{
		return iButton_info ;
	}

	public static void v_Set_Dialog_Cloud_retryCmd(int ival)
	{
		iCloud_cmd = ival;
	}
	public static int v_Get_Dialog_Cloud_retryCmd()
	{
		return iCloud_cmd ;
	}

	public static void clear()
	{
		Dialog_mode = DialogState.PROC_EXIT;
		iButton_info = i_Button_Idle;
		iCloud_cmd = 0;
	}


	public static int i_title01_id = -1;
	public static int i_title02_id = -1;
	public static String s_title01 = null;
	public static String s_title02 = null;
	public static String s_info01 = null;
	public static String s_info02 = null;
	public static String s_info_tag = null;
	public static ImageView.ScaleType scaletype = null;

	public static void v_tist_Dialog(int ititle01_id, int ititle02_id, String stitle01, String stitle02, String sinfo01, String sinfo02, String sinfo_tag, ImageView.ScaleType title01_scaletype)
	{
		i_title01_id = ititle01_id;
		i_title02_id = ititle02_id;
		s_title01 = stitle01;
		s_title02 = stitle02;
		s_info01 = sinfo01;
		s_info02 = sinfo02;
		s_info_tag = sinfo_tag;
		scaletype = title01_scaletype;
	}

	public static int iWifiUiState = -1;

	public static void vDialogUiInfo_SetWifiState(int iWifiState)
	{
		iWifiUiState = iWifiState;
	}

	public static int iDialogUiInfo_GetWifiState()
	{
		return iWifiUiState;
	}
}
