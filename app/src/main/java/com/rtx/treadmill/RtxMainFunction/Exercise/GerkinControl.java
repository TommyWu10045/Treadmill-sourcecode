package com.rtx.treadmill.RtxMainFunction.Exercise;

import com.rtx.treadmill.GlobalData.Change_UI_Info;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.UartDevice.Uartcommand;

import static com.rtx.treadmill.GlobalData.ExerciseData.mCaculate_Data;

/**
 * 儲存全域設定
 * @author Tom
 *
 */
public class GerkinControl {
	private static String TAG = "Jerry";
	private static boolean DEBUG = false;

	private Uartcommand mUartcmd ;

	public GerkinControl(Uartcommand mUartcmd) {
		super();

		this.mUartcmd = mUartcmd;
	}

	////////////Physical Gerkin mode/////////////
	public static boolean Physical_Gerkin_Start(long ldiff)
	{
		boolean bret = true;

		if(ldiff <= 0) {
			return bret;
		}

		switch (HeartRateControl.iHR_Get_status())
		{
			case 0x01 :
				com.rtx.treadmill.RtxMainFunction.Exercise.HeartRateControl.lstart_undetect=60000;
				HeartRateControl.bHeartRate_Start_check();
				Change_UI_Info.istate_delay = 3;	//0 -> 3
				break;
			case 0x02 :
//				com.rtx.treadmill.RtxMainFunction.Exercise.HeartRateControl.lstart_undetect=10000;
//				HeartRateControl.vHeartRate_Start_Timeout();
				HeartRateControl.vHeartRate_Start_Timeout_Stop();
				break;
			case 0x10 :
			case 0x11 :
				bret = Target_check_allmode(ldiff);
				break;
			default:
				break;
		}

		return bret;
	}


    public static boolean Target_check_allmode(long ldiff)
    {
        boolean bret = true;

		if(HeartRateControl.bHR_detect_timeout(0))
		{
			if(HeartRateControl.bHR_detect_timeout(43000))
			{
//				Exercisefunc.v_set_Exercise_Status(0x05); //No Heart Rate into CoolDown mode
				Exercisefunc.v_set_Exercise_Status(0x00); //No Heart Rate Complete
			}
		}
		else if(ldiff > 0)
        {
            bret = Target_time_check(ldiff);
        }

        return bret;
    }

    //Target_time_check
	//return false : DEVICE_COMMAND is null
	public static boolean Target_time_check(long ldiff)
	{
		boolean bret = false;
		int itarget_time ;

		ExerciseData.DEVICE_COMMAND Uart_command = null;
		if(ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
			Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
		}

		if(Uart_command != null)
		{
			Exercisefunc.v_Current_time_add(ldiff);

			itarget_time = Uart_command.isec;

			if(Uart_command.bcal)
			{
				Exercisefunc.v_Total_time_add(ldiff);

				Exercisefunc.v_calculate_info(ldiff);
			}
			else
			{
				Exercisefunc.v_Total_time_Eng_add(ldiff); //Device runing Time;
				Exercisefunc.v_calculate_eng_info(ldiff);
			}

			bret = true;

			if(mCaculate_Data.icurrent_time >= itarget_time)
			{
				ExerciseData.ilist_count++;

				if(ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
					Exercisefunc.v_Reset_Current_time(itarget_time, Uart_command.bcal);
				}
				else
				{
					bret = false;
				}
			}

			Exercisefunc.v_check_motor_befor_cheange(itarget_time);

		}

		return bret;
	}

}
