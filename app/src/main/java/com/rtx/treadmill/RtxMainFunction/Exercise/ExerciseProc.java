package com.rtx.treadmill.RtxMainFunction.Exercise;

import android.util.Log;

import com.retronix.circleuart.guart;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxCheck.CheckTask;
import com.rtx.treadmill.RtxMainFunction.Exercise.CoolDown.CoolDownProc;
import com.rtx.treadmill.RtxMainFunction.Exercise.CountDown.CountDownProc;
import com.rtx.treadmill.RtxMainFunction.Exercise.CountDown.CountDownState;
import com.rtx.treadmill.RtxMainFunction.Exercise.Emergency.EmergencyProc;
import com.rtx.treadmill.RtxMainFunction.Exercise.Error.ErrorProc;
import com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseRun.ExerciseRunProc;
import com.rtx.treadmill.RtxMainFunction.Exercise.Pause.PauseProc;
import com.rtx.treadmill.RtxMainFunction.Exercise.Stop.StopProc;
import com.rtx.treadmill.RtxTools.Rtx_Debug;
import com.rtx.treadmill.UartDevice.UartData;
import com.rtx.treadmill.UartDevice.Uartcommand;
import com.rtx.treadmill.UartDevice.VirtualUartFrame;

import static com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseState.PROC_IDLE;
import static com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseState.PROC_INIT;
import static com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseState.PROC_NULL;


/**
 * hide status and software button
 * 
 */
public class ExerciseProc{
	private String TAG = "Jerry=" ;
	private boolean DEBUG = false ;

	private MainActivity mMainActivity ;

	private ExerciseState mState = PROC_INIT ;
	private ExerciseState mNextState = PROC_NULL ;
	private ExerciseState tempState = PROC_NULL ;

	public ExerciseRunProc exerciserunProc ;
	public CountDownProc countdownProc ;
	public ErrorProc errorProc ;
	public EmergencyProc emergencyProc ;
	public PauseProc pauseProc ;
	public StopProc stopProc ;
	public CoolDownProc cooldownProc ;

	public CheckTask    checkTask;
	private VirtualUartFrame f_uart;
	private boolean b_virtual_uart = Rtx_Debug.bGetVirtualUartEnable();

	private int icount = 0;
	private Uartcommand mUartcmd ;

	//Create
	public ExerciseProc( MainActivity mMainActivity ) {

		this.mMainActivity = mMainActivity ;
		mState = PROC_INIT ;

		if(checkTask == null)                { checkTask = new CheckTask(mMainActivity.mUI_Handler); }
		if(f_uart == null && Rtx_Debug.bGetVirtualUartEnable()) { f_uart = new VirtualUartFrame(mMainActivity.mContext, mMainActivity); }

		if(countdownProc == null) { countdownProc = new CountDownProc(mMainActivity); }
		if(pauseProc == null) { pauseProc = new PauseProc(mMainActivity); }
		if(cooldownProc == null) { cooldownProc = new CoolDownProc(mMainActivity); }
		if(mUartcmd == null)              { mUartcmd =  mMainActivity.mUartcmd; }

	}

	/* ------------------------------------------------------------------------ */

	public void vSetNextState(ExerciseState nextState)
	{
		mNextState = nextState;
		icount = 0;
	}

	public void vSetState(ExerciseState State)
	{
		mState = State;

	}

	public void vSetIdleState()
	{
		mState = PROC_IDLE;
	}

	/* ------------------------------------------------------------------------ */
	private void vProc_init() {

		vSetState(PROC_IDLE) ;

		icount = 0;
	}

	private void vProc_ErrorCode() {
		cooldownProc.mCoolDownfunc.vCoolDownfunc_SetFinish(true);
		if(errorProc == null)
		{
			errorProc = new ErrorProc(mMainActivity);
		}

		errorProc.run();
		icount = 0;
	}

	private void vProc_Emergency() {
		cooldownProc.mCoolDownfunc.vCoolDownfunc_SetFinish(true);
		if(emergencyProc == null)
		{
			emergencyProc = new EmergencyProc(mMainActivity);
		}

		emergencyProc.run();
		icount = 0;
	}

	private void vProc_Pause() {
		pauseProc.run();
		icount = 0;
	}

	private void vProc_CountDown() {
		countdownProc.run();

		if ( ExerciseData.i_get_finish() == 0x02 ) //emergency key
		{
			countdownProc.vSetState(CountDownState.PROC_INIT);
			vSetState(ExerciseState.PROC_EXERCISE_EMERGENCY);
		}else if (ExerciseData.i_get_finish() == 0x03 ) //Device error
		{
			countdownProc.vSetState(CountDownState.PROC_INIT);
			vSetState(ExerciseState.PROC_EXERCISE_ERROR_CODE);
		}

		icount = 0;
	}

	private void vProc_CoolDown() {
		cooldownProc.mCoolDownfunc.vCoolDownfunc_SetFinish(false);
		cooldownProc.run();
		icount = 0;
	}

	private void vProc_RunMode() {
		if(cooldownProc.mCoolDownfunc.bCoolDownfunc_GetFinish())
		{
			if (exerciserunProc == null)
			{
				exerciserunProc = new ExerciseRunProc(mMainActivity);
			}

			if (b_virtual_uart && f_uart != null)
			{
				vAdd_VirturalUartPage(f_uart);
				b_virtual_uart = false;
			}
		}
		else
		{
			vSetState(ExerciseState.PROC_EXERCISE_COOLDOWN);
		}
		exerciserunProc.run();
		icount = 0;
	}

	private void vProc_Stop() {

		cooldownProc.mCoolDownfunc.vCoolDownfunc_SetFinish(true);
		if(stopProc == null)
		{
			stopProc = new StopProc(mMainActivity);
		}

		stopProc.run();
		icount = 0;
	}

	private void vProc_Idle() {
		if(mNextState != PROC_NULL)
		{
			vSetState(mNextState) ;
			mNextState = PROC_NULL;
		}
		else {
			icount++;
            //get device status
            if(icount % EngSetting.DEF_POLLING_DEV_STATUS == 0)
            {
                vSetNextState(ExerciseState.PROC_GETSTATUS);
            }
		}
	}

	private void vProc_Exit() {
		mMainActivity.mUI_Handler.post(new Runnable() {
			@Override
			public void run()
			{
				mMainActivity.mMainProcTreadmill.exerciseProc.vSetIdleState();
				if(!b_virtual_uart) {
					mMainActivity.removeExerciseViews(ExerciseData.Virtual_Uart_layer);
					b_virtual_uart = Rtx_Debug.bGetVirtualUartEnable();
				}
			}
		});
		vSetState(PROC_INIT) ;
		mNextState = PROC_NULL;
	}


	/* ------------------------------------------------------------------------ */
	public void vAdd_VirturalUartPage(final Rtx_BaseLayout layout)
	{
		mMainActivity.mUI_Handler.post(new Runnable() {
			@Override
			public void run() {
				layout.init();
				layout.removeAllViews();
				mMainActivity.removeExerciseViews(ExerciseData.Virtual_Uart_layer);
				mMainActivity.addExerciseView(ExerciseData.Virtual_Uart_layer, layout);
				layout.display();
				layout.setClickable(false);

			}
		});
	}

	public void vVirtual_Uart_Refresh_Speed() {
		if(f_uart != null && b_virtual_uart)
		{
			mMainActivity.mUI_Handler.post(new Runnable() {
				@Override
				public void run() {
					f_uart.v_Refresh_speed();
				}
			});
		}
	}

	public void vVirtual_Uart_Refresh_Incline() {
		if(f_uart != null && b_virtual_uart)
		{
			mMainActivity.mUI_Handler.post(new Runnable() {
				@Override
				public void run() {
					f_uart.v_Refresh_incline();
				}
			});
		}
	}

	/* ------------------------------------------------------------------------ */
	public void run() {
		if(tempState != mState)
		{
			//Log.e("Jerry", "[ExerciseProc] mState = " + mState);
			tempState = mState;
		}

		switch( mState )
		{
			case PROC_INIT						:{   vProc_init();				break;  }
			case PROC_EXERCISE_ERROR_CODE	:{   vProc_ErrorCode();			break;  }
			case PROC_EXERCISE_EMERGENCY	:{   vProc_Emergency();			break;  }
			case PROC_EXERCISE_PAUSE			:{   vProc_Pause();				break;  }
			case PROC_EXERCISE_COUNTDOWN	:{   vProc_CountDown();			break;  }
			case PROC_EXERCISE_RUN			:{   vProc_RunMode();			break;  }
			case PROC_EXERCISE_COOLDOWN	:{   vProc_CoolDown();			break;  }
			case PROC_EXERCISE_STOP			:{   vProc_Stop();				break;  }
			case PROC_GETSTATUS				:{   vProc_Uart15Cmd();			break;  }
			case PROC_DELAY                  	:{   vProc_UartCmd_Delay();		break;  }
			case PROC_CACULATION				:{   vProc_Caculation();		break;  }
			case PROC_EXIT						:{   vProc_Exit();				break;  }
			case PROC_IDLE						:{   vProc_Idle();				break;  }
			default								:{   vProc_Idle();				break;  }
		}

//		checkTask.checkAll();
//		checkTask.clearAll();
	}


	////////////////////////////////////////////////////////////
	private void vProc_Uart15Cmd() {
		UartData.vUartCmd_clean_all();
		if(mMainActivity.mMainProcTreadmill.getPageState() != MainState.PROC_ENGMODE) UartData.vUartCmd_15(200);
		icount = 0;
		vSetState(ExerciseState.PROC_DELAY) ;
	}

	private void vProc_UartCmd_Delay() {
		icount ++;
		UartData.S_CMD uartcmd = UartData.vUartCmd_get_list(UartData.vUartCmd_get_list_size() - 1);
		if(uartcmd != null) {
			int imode = uartcmd.imode & UartData.imode_read_mask;

			if (imode == 0) {
				if (uartcmd.icmd == guart.GET_READ_DATA1 || uartcmd.icmd == guart.GET_READ_DATA2) {
					if(uartcmd.iresult == 0)
					{
						mUartcmd.bData1_parser();
					}

					if(ExerciseData.i_get_finish() == 0x02)
					{
						vSetNextState(ExerciseState.PROC_EXERCISE_EMERGENCY); //emergency key
					}
					else if(ExerciseData.i_get_finish() == 0x03) //Device error
					{
						vSetNextState(ExerciseState.PROC_EXERCISE_ERROR_CODE);
					}
				}
				vSetState(ExerciseState.PROC_CACULATION);
			} else {
				if(icount < uartcmd.idelay)
				{
					vSetState(ExerciseState.PROC_DELAY);
				}
				else
				{
					vSetState(ExerciseState.PROC_CACULATION);
				}
			}
		}
		else {
			vSetState(ExerciseState.PROC_CACULATION);
		}
	}

	private void vProc_Caculation() {
		icount = 0;
		vSetState(ExerciseState.PROC_IDLE);
	}

}
