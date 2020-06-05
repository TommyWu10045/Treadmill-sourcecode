package com.rtx.treadmill.UartDevice;

import android.util.Log;

import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxCheck.CheckTask;

import static com.rtx.treadmill.UartDevice.UartState.PROC_UART_INIT;
import static com.rtx.treadmill.UartDevice.UartState.PROC_UART_NULL;
import static com.rtx.treadmill.UartDevice.UartState.PROC_UART_SEND;


/**
 * hide status and software button
 * 
 */
public class UartProc{
	private String TAG = "Jerry=" ;
	private boolean DEBUG = false ;

	private MainActivity mMainActivity ;

	private UartState mState = PROC_UART_INIT ;
	private UartState mNextState = PROC_UART_NULL ;

	private Uartcommand mUartcmd = null ;


	public CheckTask    checkTask;
	private int iCount = 0;

	//Create
	public UartProc( MainActivity mMainActivity ) {

		this.mMainActivity = mMainActivity ;
		mState = PROC_UART_SEND ;

		if(mUartcmd == null)                 { mUartcmd = mMainActivity.mUartcmd; }
		if(checkTask == null)                { checkTask = new CheckTask(mMainActivity.mUI_Handler); }

	}

	/* ------------------------------------------------------------------------ */
	private void vProc_Send_Command() {
		iCount = 0;
		if(mUartcmd != null)
		{
			UartData.S_CMD uartcmd = UartData.vUartCmd_get_list(UartData.iCmd_index);

			if(uartcmd != null) {
				int imode = uartcmd.imode & UartData.imode_write_mask;
				if(DEBUG)Log.e(TAG, "vProc_Send_Command====iCmd_index===" + UartData.iCmd_index + "=========UartCMD_List===" + UartData.UartCMD_List.size());

				if (imode != 0) {
					if (imode == 0x10) {
						mUartcmd.uart_command_write(uartcmd.icmd);
					} else if (imode == 0x20) {
						mUartcmd.uart_command_string(1, uartcmd.icmd, uartcmd.scmd);
					} else if (imode == 0x30) { // By Alan
						mUartcmd.uart_command_write(uartcmd.icmd,uartcmd.scmd);//By Alan
					}
					if (uartcmd.iprocdelay <= 0) {
						mState = UartState.PROC_UART_GET;
					} else {
						mState = UartState.PROC_UART_DELAY;
					}
					uartcmd.imode &= ~UartData.imode_write_mask;
				}
				else {
					UartData.iCmd_index++;
				}
			}

		}

	}

	private void vProc_Send_Delay() {
		iCount++;
		UartData.S_CMD uartcmd = UartData.vUartCmd_get_list(UartData.iCmd_index);
		if(uartcmd != null) {
			if(iCount >= uartcmd.iprocdelay)
			{
				mState = UartState.PROC_UART_GET;
			}
			else
			{
				mState = UartState.PROC_UART_DELAY;
			}
		}

	}

	private void vProc_Send_Get() {
		if(mUartcmd != null)
		{
			UartData.S_CMD uartcmd = UartData.vUartCmd_get_list(UartData.iCmd_index);
			if(uartcmd != null) {
				int imode = uartcmd.imode & UartData.imode_read_mask;

				if (imode != 0) {
					uartcmd.imode &= ~UartData.imode_read_mask;
					uartcmd.iresult = mUartcmd.uart_command_check_result(imode - 1, uartcmd.icmd);

					if(imode == 1 || imode == 3)
					{
						if(uartcmd.iresult == 0) {
							UartData.iCmd_index++;
						}
						else
						{
							UartData.vUartCmd_retry(uartcmd);
						}
					}
					else
					{
						UartData.iCmd_index++;
					}
				}
			}
		}

		mState = UartState.PROC_UART_SEND;

	}

	private void vProc_Send_Check() {

		mState = PROC_UART_SEND;

	}

	private void vProc_Idle() {
		if(mNextState != PROC_UART_NULL)
		{
			mState = mNextState;
			mNextState = PROC_UART_NULL;
		}
	}

	/* ------------------------------------------------------------------------ */

	public void vSetNextState(UartState state)
	{
		mNextState = state;
	}

	/* ------------------------------------------------------------------------ */
	public void run() {

		switch( mState )
		{
			case PROC_UART_SEND                  	:{   vProc_Send_Command();                         break;  }
			case PROC_UART_DELAY                  	:{   vProc_Send_Delay();                         break;  }
			case PROC_UART_GET                  	:{   vProc_Send_Get();                         break;  }
			case PROC_UART_CHECK                  	:{   vProc_Send_Check();                         break;  }
			case PROC_UART_IDLE		                :{   vProc_Idle();                         break;  }
			default                                 :{   vProc_Idle();                         break;  }
		}

	}

}
