package com.rtx.treadmill.GlobalData;

import com.rtx.treadmill.GlobalData.ExerciseData.SimpleObj;

/**
 * 儲存全域設定
 * @author Tom
 *
 */

public class Choice_UI_Info
{
	public static int istate_change = 0; //UI顯示, 0x01 Choice Item

	//Choice Item Parameter
	public static float fsize;
	public static int ixcenter;
	public static int iy_bot;
	public static SimpleObj Obj;

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
	public static void v_clear_stage( )
	{
		istate_change = 0x00;
	}

	public static void clear()
	{
		istate_change = 0x00;

		fsize = 0;
		ixcenter = 0;
		iy_bot = 0;
		Obj = null;
	}

	public static void vSet_Choice(int ix, int iy, float fs, SimpleObj obj)
	{
		fsize = fs;
		ixcenter = ix;
		iy_bot = iy;
		Obj = obj;

		v_set_stage(0x02);

	}

	public static SimpleObj S_get_Choice_Obj()
	{
		return Obj ;
	}

	public static float f_get_Choice_size()
	{
		return fsize ;
	}

	public static int f_get_Choice_xceter()
	{
		return ixcenter ;
	}

	public static int f_get_Choice_ybot()
	{
		return iy_bot ;
	}

}
