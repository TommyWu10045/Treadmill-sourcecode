package com.rtx.treadmill.GlobalData;

/**
 * 儲存全域設定
 * @author Tom
 *
 */
public class Calc_Data {

	public int icount;
	public float ftotal;
	public float fave;
	public float fmin;
	public float fmax;
	public float fcurr;

	public void clear()
	{
		icount = 0;
		ftotal = 0;
		fave = 0;
		fmin = 0;
		fmax = 0;
		fcurr = 0;
	}

}
