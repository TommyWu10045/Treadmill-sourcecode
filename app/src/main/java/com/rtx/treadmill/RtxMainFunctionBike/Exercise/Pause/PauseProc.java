package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Pause;

import com.rtx.treadmill.GlobalData.Choice_UI_Info;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.ChoiceItem.ChoiceItemState;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.CountDown.CountDownState;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.ExerciseState;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.Exercisefunc;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.HeartRateControl;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.UartDevice.UartData;
import com.rtx.treadmill.UartDevice.Uartcommand;

/**
 * Created by chasechang on 3/27/17.
 */

public class PauseProc {
    private String TAG = "Jerry=" ;

    private MainActivity mMainActivity ;

    private PauseState mState = PauseState.PROC_INIT ;
    private PauseState mNextState = PauseState.PROC_NULL ;
    private PauseState tempState = PauseState.PROC_NULL ;

    private PauseMode01Layout mPauseMode01Layout;
    private PauseMode02Layout mPauseMode02Layout;
    private ResetMode01Layout mResetMode01Layout;

    private Uartcommand mUartcmd ;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    private int icount ;
    private int itotal ;
    private PauseState flash_mode = PauseState.PROC_PAUSE_MODE1_FLASH;

    public PauseProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = PauseState.PROC_INIT ;

        if(mPauseMode01Layout == null)            { mPauseMode01Layout = new PauseMode01Layout(mMainActivity.mContext , mMainActivity); }
        if(mPauseMode02Layout == null)            { mPauseMode02Layout = new PauseMode02Layout(mMainActivity.mContext , mMainActivity); }
        if(mResetMode01Layout == null)            { mResetMode01Layout = new ResetMode01Layout(mMainActivity.mContext , mMainActivity); }

        if(mUartcmd == null)              { mUartcmd =  mMainActivity.mUartcmd; }

        icount = 0 ;
    }


    /* ------------------------------------------------------------------------ */

    public void vSetNextState(PauseState nextState)
    {
        mNextState = nextState;
    }

    public void vSetState(PauseState State)
    {
        mState = State;
    }
    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {

        icount = 0 ;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        {
            Choice_UI_Info.v_clear_stage();
            mMainActivity.mMainProcBike.choiceItemProc.vSetState(ChoiceItemState.PROC_IDLE);
        }

        if(mNextState == PauseState.PROC_NULL)
        {
            vSetState(PauseState.PROC_PAUSE_MODE1);
        }
        else
        {
            vSetState(mNextState);
        }
    }

    //Pause mode01
    private void vProc_Pause_Mode01() {

        icount = 0;
        itotal = 120 * EngSetting.DEF_SEC_COUNT ;
        vChangeDisplayPage(mPauseMode01Layout);

        flash_mode = PauseState.PROC_PAUSE_MODE1_FLASH;
        vSetState(flash_mode);
    }

    public void vProc_Pause_Mode01_flash()
    {
        icount++;
        if(icount >= itotal)
        {
            UartData.vUartCmd_61(20, "2");
            vSetState(PauseState.PROC_PAUSE_STOP);
        }
        else {
                mMainActivity.mUI_Handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mPauseMode01Layout != null) {
                            mPauseMode01Layout.Refresh(true, itotal, icount);
                        }
                    }
                });

            vSetState(PauseState.PROC_GETSTATUS);
        }
    }



    public void vProc_Pause_Mode01_resume()
    {
        vProc_Exit(ExerciseState.PROC_EXERCISE_COUNTDOWN);
    }

    //Pause mode02
    private void vProc_Pause_Mode02() {
        icount = 0;
        itotal = 120 * EngSetting.DEF_SEC_COUNT ;
        vChangeDisplayPage(mPauseMode02Layout);

        flash_mode = PauseState.PROC_PAUSE_MODE2_FLASH;
        vSetState(flash_mode);
    }

    public void vProc_Pause_Mode02_flash()
    {
        icount++;
        if(icount >= itotal)
        {
            UartData.vUartCmd_61(20, "2");
            vSetState(PauseState.PROC_PAUSE_STOP);
        }
        else {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run() {
                    if (mPauseMode02Layout != null) {
                        mPauseMode02Layout.Refresh(true, itotal, icount);
                    }
                }
            });
            vSetState(PauseState.PROC_GETSTATUS);
        }
    }

    public void vProc_Pause_Mode02_resume()
    {
        HeartRateControl.iHR_Set_status(0x01);
        vProc_Exit(ExerciseState.PROC_EXERCISE_COUNTDOWN);
        mMainActivity.mMainProcBike.exerciseProc.countdownProc.vSetNextState(CountDownState.PROC_COUNTDOWN_START);
    }

    //Reset mode01
    private void vProc_Reset_Mode01() {

        icount = 0;
        itotal = 90 * EngSetting.DEF_SEC_COUNT ;
        vChangeDisplayPage(mResetMode01Layout);

        flash_mode = PauseState.PROC_PAUSE_RESET1_FLASH;
        vSetState(flash_mode);
    }

    public void vProc_Reset_Mode01_flash()
    {
        icount++;
        if(icount >= itotal)
        {
            vSetState(PauseState.PROC_PAUSE_STOP);
        }
        else {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run() {
                    if (mResetMode01Layout != null) {
                        mResetMode01Layout.Refresh(true, itotal, icount);
                    }
                }
            });
            vSetState(PauseState.PROC_GETSTATUS);
        }
    }

    public void vProc_Reset_Mode01_resume()
    {
        vProc_Exit(ExerciseState.PROC_EXERCISE_RUN);
    }

    //Reset mode02


    //Stop mode
    private void vProc_Stop()
    {
        UartData.vUartCmd_61(20, "2");
        Exercisefunc.v_set_Exercise_Status(0x01);
        vProc_Exit(ExerciseState.PROC_EXERCISE_STOP);
    }

    //Check Uart15 CMD
    private void vProc_Uart16Cmd() {
        UartData.vUartCmd_clean_all();
        UartData.vUartCmd_16(200);

        icount++;
        vSetState(PauseState.PROC_DELAY) ;
    }

    private void vProc_UartCmd_Delay() {
        icount++;
        UartData.S_CMD uartcmd = UartData.vUartCmd_get_list(UartData.vUartCmd_get_list_size() - 1);

        if(uartcmd != null) {
            int imode = uartcmd.imode & UartData.imode_read_mask;
            if (imode == 0) {
                Exercisefunc.v_set_Exercise_Status(-1);
                if(mUartcmd.bData1_parser())
                {
                    HeartRateControl.HeartRate_from_Uart(ExerciseData.uart_data1.fheart_rate);
                }
                vSetState(PauseState.PROC_CACULATION);
            } else {
                vSetState(PauseState.PROC_DELAY);
            }
        }
        else {
            vSetState(PauseState.PROC_GETSTATUS);
        }

        vProc_Idle();
    }

    private void vProc_Caculation() {

        icount++;

        GlobalData.vResetInteractionTime();

        switch (flash_mode)
        {
            case PROC_PAUSE_MODE1_FLASH:
                vSetState(flash_mode);
                break;
            case PROC_PAUSE_MODE2_FLASH:
                if(ExerciseGenfunc.bHR_is_void(ExerciseGenfunc.f_get_curr_heartrate()))
                {
                    HeartRateControl.iHR_Set_status(0x10);
                    vProc_Exit(ExerciseState.PROC_EXERCISE_COUNTDOWN);
                }
                else
                {
                    vSetState(flash_mode);
                }
                break;
            case PROC_PAUSE_RESET1_FLASH:
                vSetState(flash_mode);
                break;
            default:
                break;
        }

        if(ExerciseData.i_get_finish() == 0x01)
        {
            vProc_Exit(ExerciseState.PROC_EXERCISE_STOP);
        }
        else if(ExerciseData.i_get_finish() == 0x02)
        {
            vProc_Exit(ExerciseState.PROC_EXERCISE_EMERGENCY);
        }
        else if(ExerciseData.i_get_finish() == 0x03)
        {
            vProc_Exit(ExerciseState.PROC_EXERCISE_ERROR_CODE);
        }
        else
        {
            Exercisefunc.v_set_Exercise_Status(-1);
        }

        vProc_Idle();
    }

    private void vProc_Idle()
    {
        if(mNextState != PauseState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = PauseState.PROC_NULL;
        }
    }

    private void vProc_Exit(ExerciseState state) {

        if(state != ExerciseState.PROC_EXERCISE_COUNTDOWN)
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mMainActivity.removeExerciseViews(ExerciseData.Pause_layer);
                }
            });
        }

        mMainActivity.mMainProcBike.exerciseProc.vSetIdleState();
        mMainActivity.mMainProcBike.exerciseProc.vSetNextState(state);

        mState = PauseState.PROC_INIT ;
        mNextState = PauseState.PROC_NULL;
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeExerciseViews(ExerciseData.Pause_layer);
                mMainActivity.addExerciseView(ExerciseData.Pause_layer, layout);
                layout.display();
            }
        });
    }

    /* ------------------------------------------------------------------------ */
    public void run() {
//        if(tempState != mState)
//        {
//            Log.e("Jerry", "[PauseProc] mState = " + mState);
//            tempState = mState;
//        }

        switch( mState )
        {
            case PROC_INIT                              :{   vProc_Init();                          break;  }
            case PROC_PAUSE_MODE1                       :{   vProc_Pause_Mode01();                          break;  }
            case PROC_PAUSE_MODE1_FLASH                 :{   vProc_Pause_Mode01_flash();                          break;  }
            case PROC_PAUSE_MODE1_RESUME                :{   vProc_Pause_Mode01_resume();   break;  }

            case PROC_PAUSE_MODE2                       :{   vProc_Pause_Mode02();          break;  }
            case PROC_PAUSE_MODE2_FLASH                 :{   vProc_Pause_Mode02_flash();                          break;  }
            case PROC_PAUSE_MODE2_RESUME                :{   vProc_Pause_Mode02_resume();   break;  }

            case PROC_PAUSE_RESET1                       :{   vProc_Reset_Mode01();          break;  }
            case PROC_PAUSE_RESET1_FLASH                 :{   vProc_Reset_Mode01_flash();                          break;  }
            case PROC_PAUSE_RESET1_RESUME                :{   vProc_Reset_Mode01_resume();   break;  }

            case PROC_PAUSE_STOP                        :{   vProc_Stop();        break;  }

            case PROC_GETSTATUS                :{   vProc_Uart16Cmd();           break;  }
            case PROC_DELAY                    :{   vProc_UartCmd_Delay();           break;  }
            case PROC_CACULATION               :{   vProc_Caculation();           break;  }

            case PROC_IDLE                              :{   vProc_Idle();                          break;  }
            case PROC_EXIT                              :{   vProc_Exit(ExerciseState.PROC_EXERCISE_STOP);                          break;  }
            default                                     :{   vProc_Idle();                          break;  }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////


}
