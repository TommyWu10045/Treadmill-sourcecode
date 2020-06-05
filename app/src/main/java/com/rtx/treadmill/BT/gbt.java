package com.rtx.treadmill.BT;

/**
 * 儲存全域設定
 * @author Tom
 *
 */
public class gbt {
	private static String TAG = "Jerry=gbt";
	private static boolean DEBUG = false;

	public static String BTname = "Circle Fitness";
	public static String semail = "";
	public static String spassword = "";
	public static BT_login_Peripheral bt_class = null ;
	public static BT_login_Peripheral_Bike bt_class_bike = null ;
	public static boolean bt_state = false;

	public static void clear()
	{
		semail = "";
		spassword = "";
	}

}
