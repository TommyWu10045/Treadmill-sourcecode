package com.rtx.treadmill.RtxMainFunction.Exercise.CoolDown;

import android.util.Log;

import com.retronix.circleuart.guart;
import com.rtx.treadmill.GlobalData.CoolDownData;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.Exercise.CoolDownfunc;
import com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseState;
import com.rtx.treadmill.RtxMainFunction.Exercise.HeartRateControl;
import com.rtx.treadmill.UartDevice.UartData;
import com.rtx.treadmill.UartDevice.Uartcommand;

/**
 * Created by chasechang on 3/27/17.
 */

public class CoolDownProc {
    private String TAG = "Jerry=" ;

    private MainActivity mMainActivity ;

    private CoolDownState mState = CoolDownState.PROC_INIT ;
    private CoolDownState mNextState = CoolDownState.PROC_NULL ;
    private CoolDownState tempState = CoolDownState.PROC_NULL ;

    private Uartcommand mUartcmd ;
    public CoolDownfunc mCoolDownfunc;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    private int icount ;
    private long        lStartTimeMillis    = 0 ;
    private long        lNowTimeMillis      = 0 ;

    public CoolDownProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = CoolDownState.PROC_INIT ;

        if(mUartcmd == null)              { mUartcmd = mMainActivity.mUartcmd; }
        if(mCoolDownfunc == null)         { mCoolDownfunc = new CoolDownfunc(mMainActivity.mContext, mMainActivity); }

    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(CoolDownState nextState)
    {
        mNextState = nextState;
    }

    public void vSetState(CoolDownState State)
    {
        mState = State;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {

        icount = 0 ;

        if(mNextState == CoolDownState.PROC_NULL)
        {
            vSetState(CoolDownState.PROC_COOLDOWN_MODE1);
            mCoolDownfunc.v_init_Cooldown_data1(CoolDownData.f_cooldown_time);
        }
        else
        {
            vSetState(mNextState);
        }

    }

    //Pause mode01
    private void vProc_CoolDown_Mode01() {
        lStartTimeMillis = System.currentTimeMillis() ;
        vSetState(CoolDownState.PROC_GETSTATUS);
    }

    //Restart and set speed
    private void vProc_CoolDown_Mode02() {
        if(mCoolDownfunc.vCoolDownfunc_setSpeed())
        {
            lStartTimeMillis = System.currentTimeMillis();
            vSetState(CoolDownState.PROC_GETSTATUS);
        }
        else
        {
            vSetState(CoolDownState.PROC_INIT);
        }
    }


    //Check Uart15 CMD
    private void vProc_Uart15Cmd() {
        UartData.vUartCmd_clean_all();
        UartData.vUartCmd_15(200);
        icount = 0;
        vSetState(CoolDownState.PROC_DELAY) ;

    }

    private void vProc_UartCmd_Delay() {
        icount++;
        UartData.S_CMD uartcmd = UartData.vUartCmd_get_list(UartData.vUartCmd_get_list_size() - 1);
        if(uartcmd != null) {
            int imode = uartcmd.imode & UartData.imode_read_mask;
            if (imode == 0) {
                if (uartcmd.icmd == guart.GET_READ_DATA1 || uartcmd.icmd == guart.GET_READ_DATA2) {
                    if(uartcmd.iresult == 0)
                    {
                        if(mUartcmd.bData1_parser())
                        {
                            HeartRateControl.HeartRate_from_Uart(ExerciseData.uart_data1.fheart_rate);
                        }
                    }
                    vSetState(CoolDownState.PROC_CACULATION) ;
                }
                else
                {
                    if(UartData.b_Can_clear_UartCmd())
                    {
                        vSetState(CoolDownState.PROC_GETSTATUS) ;
                    }
                }
            } else {
                if(icount < uartcmd.idelay)//uart command，在idelay時間內無法完成，即判定為timeout
                {
                    vSetState(CoolDownState.PROC_DELAY) ;
                }
                else
                {
                    vSetState(CoolDownState.PROC_CACULATION) ;
                }
            }
        }
        else {
            vSetState(CoolDownState.PROC_GETSTATUS) ;
        }

        vProc_Idle();

    }

    private void vProc_Caculation() {
        long ldiff;

        lNowTimeMillis = System.currentTimeMillis() ;
        ldiff = lNowTimeMillis - lStartTimeMillis;

        if(ldiff > 0 && ldiff < 2000)
        {
            mCoolDownfunc.Target_check(ldiff);
        }
        else
        {
            //防止ldiff數值異常現象
            Log.e(TAG, "lNowTimeMillis=" + lNowTimeMillis + "     lStartTimeMillis=" + lStartTimeMillis + "     ldiff=" + ldiff);
        }

        lStartTimeMillis = lNowTimeMillis;

        if(ExerciseData.i_get_finish() == 0x00)
        {
            UartData.vUartCmd_61(20, "2");
            vProc_Exit(ExerciseState.PROC_EXERCISE_STOP); //list finish
            ExerciseData.v_set_finish(0x07);          //CoolDown finish
        }
        else if(ExerciseData.i_get_finish() == 0x01)
        {
            vProc_Exit(ExerciseState.PROC_EXERCISE_STOP); //stop key
            ExerciseData.v_set_finish(0x08);          //CoolDown Stop key
        }
        else if(ExerciseData.i_get_finish() == 0x02)
        {
            vProc_Exit(ExerciseState.PROC_EXERCISE_EMERGENCY); //emergency key
            ExerciseData.v_set_finish(0x08);          //CoolDown Stop key
        }
        else if(ExerciseData.i_get_finish() == 0x03) //Device error
        {
            vProc_Exit(ExerciseState.PROC_EXERCISE_ERROR_CODE);
            ExerciseData.v_set_finish(0x08);          //CoolDown Stop key
        }
        else if(ExerciseData.i_get_finish() == 0x04) //pause key
        {
            vProc_Exit(ExerciseState.PROC_EXERCISE_PAUSE);
            mState = CoolDownState.PROC_COOLDOWN_MODE2 ;
            mNextState = CoolDownState.PROC_NULL;
        }
        else if(ExerciseData.i_get_finish() == 0x05) //cooldown key
        {
            vSetState(CoolDownState.PROC_GETSTATUS) ;
        }
        else if(ExerciseData.i_get_finish() == 0x06) //start key
        {
            vSetState(CoolDownState.PROC_GETSTATUS) ;
        }
        else
        {
            vSetState(CoolDownState.PROC_GETSTATUS) ;
        }

        vProc_Idle();
    }

    private void vProc_Idle()
    {
        if(mNextState != CoolDownState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = CoolDownState.PROC_NULL;
        }
    }

    private void vProc_Exit(ExerciseState state) {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.removeExerciseViews(ExerciseData.CoolDown_layer);
            }
        });

        mMainActivity.mMainProcTreadmill.exerciseProc.vSetIdleState();
        mMainActivity.mMainProcTreadmill.exerciseProc.vSetNextState(state);

        mState = CoolDownState.PROC_INIT ;
        mNextState = CoolDownState.PROC_NULL;
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeExerciseViews(ExerciseData.CoolDown_layer);
                mMainActivity.addExerciseView(ExerciseData.CoolDown_layer, layout);
                layout.display();
            }
        });
    }

    /* ------------------------------------------------------------------------ */
    public void run() {
        if(tempState != mState)
        {
//            Log.e("Jerry", "[CoolDownProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                              :{   vProc_Init();                          break;  }
            case PROC_COOLDOWN_MODE1                    :{   vProc_CoolDown_Mode01();               break;  }
            case PROC_COOLDOWN_MODE2                    :{   vProc_CoolDown_Mode02();               break;  }

            case PROC_GETSTATUS                         :{   vProc_Uart15Cmd();           break;  }
            case PROC_DELAY                             :{   vProc_UartCmd_Delay();           break;  }
            case PROC_CACULATION                        :{   vProc_Caculation();           break;  }

            case PROC_IDLE                              :{   vProc_Idle();                          break;  }
            case PROC_EXIT                              :{   vProc_Exit(ExerciseState.PROC_EXERCISE_STOP);                          break;  }
            default                                     :{   vProc_Idle();                          break;  }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////


}
