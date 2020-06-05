package com.rtx.treadmill.GlobalData;

/**
 * 儲存全域設定
 * @author Tom
 *
 */

public class Bottom_UI_Info
{
	public static int istate_change = 0; //UI顯示, 0x01 Bottom

	//Choice Item Parameter

	public static void v_set_stage( int ival )
	{
		istate_change |= ival;
	}

	public static int i_get_stage( )
	{
		return istate_change;
	}

	public static boolean b_get_stage( int ival )
	{
		return (istate_change & ival) == ival;
	}

	public static void v_clear_stage( int ival )
	{
		istate_change &= ~ival;
	}

	public static void clear()
	{
		istate_change = 0x0;
	}

	//////////////////////////////////////////////////////////////////////////////////////////

	public static int iStatePage = 0; //UI顯示, 0:Profile 1:Sample 2:Lap

	public static void vSet_PageState(int isel)
	{
		iStatePage = isel;
	}

	public static int iGet_PageState()
	{
		return iStatePage;
	}

}
