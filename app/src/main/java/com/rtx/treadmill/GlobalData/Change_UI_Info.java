package com.rtx.treadmill.GlobalData;

/**
 * 儲存全域設定
 * @author Tom
 *
 */

public class Change_UI_Info
{
	public static int istate_change_mask = 0x000F;
	public static int istate_speed = 0x0001; //0x0001 : speed chage
	public static int istate_incline = 0x0002; //0x0002 : incline chage
	public static int istate_sinfo = 0x0004; //0x0004 : sinfo warning

	public static int istate_change = 0; //UI提示用, 如上參數

	public static int istate_delay_def = 3; // Default
	public static int istate_delay = istate_delay_def; // 改變前幾秒顯示UI
	public static int i_countdown ; //speed and incline show 的時間
	public static int istage = 3;
	public static float[] fcurrent = new float[istage];
	public static float[] fnext = new float[istage];
	public static int ispeed_flash ;//speed 閃動的速度 0 : 不閃動
	public static int iinclne_flash ;//incline 閃動的速度 0 : 不閃動

	public static int i_countdown_infomation ; //infomation show 的時間
	public static String sinfomation ;	//infomation text 資訊
	public static int iinfomation_flash ;//infomation 閃動的速度 0 : 不閃動

	public static void v_set_stage( int ival )
	{
		istate_change |= ival;
	}

	public static void v_clear_stage( int ival )
	{
		istate_change &= ~ival;
	}

	public static int i_get_stage( )
	{
		return istate_change;
	}

	public static boolean b_get_stage( int ival )
	{
		return (istate_change & ival) == ival;
	}

	public static void clear()
	{
		int iLoop;
		istate_change = 0;
		istate_delay = istate_delay_def;
		ispeed_flash = 0;
		iinclne_flash = 0;
		for (iLoop = 0; iLoop < istage; iLoop++)
		{
			fcurrent[iLoop] = 0;
			fnext[iLoop] = 0;
		}

		i_countdown_infomation = Integer.MAX_VALUE;
		sinfomation = "";
		iinfomation_flash = 0;

	}

}
