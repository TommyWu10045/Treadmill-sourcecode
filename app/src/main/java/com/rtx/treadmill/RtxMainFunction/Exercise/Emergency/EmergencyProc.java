package com.rtx.treadmill.RtxMainFunction.Exercise.Emergency;

import com.retronix.circleuart.guart;
import com.rtx.treadmill.GlobalData.CloudCmd_Info;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseState;
import com.rtx.treadmill.RtxMainFunction.Exercise.Exercisefunc;
import com.rtx.treadmill.RtxMainFunction.Exercise.HeartRateControl;
import com.rtx.treadmill.RtxMainFunction.Home.HomeState;
import com.rtx.treadmill.UartDevice.UartData;
import com.rtx.treadmill.UartDevice.Uartcommand;

/**
 * Created by chasechang on 3/27/17.
 */

public class EmergencyProc {
    private String TAG = "Jerry=" ;

    private MainActivity mMainActivity ;

    private EmergencyState mState = EmergencyState.PROC_INIT ;
    private EmergencyState mNextState = EmergencyState.PROC_NULL ;
    private EmergencyState tempState = EmergencyState.PROC_NULL ;

    private EmergencyUPLayout mEmergencyUPLayout;
    private EmergencyDOWNLayout mEmergencyDOWNLayout;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    private int icount ;
    private Uartcommand mUartcmd ;
    private int i_debouns_count = 0;
    private int i_debouns = 3;
    private boolean b_UPDOWN = true;

    public EmergencyProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = EmergencyState.PROC_INIT ;

        if(mEmergencyUPLayout == null)                { mEmergencyUPLayout = new EmergencyUPLayout(mMainActivity.mContext , mMainActivity); }
        if(mEmergencyDOWNLayout == null)              { mEmergencyDOWNLayout = new EmergencyDOWNLayout(mMainActivity.mContext , mMainActivity); }
        if(mUartcmd == null)              { mUartcmd =  mMainActivity.mUartcmd; }

        icount = 0 ;
    }


    /* ------------------------------------------------------------------------ */

    public void vSetNextState(EmergencyState nextState)
    {
        mNextState = nextState;
    }

    public void vSetState(EmergencyState State)
    {
        mState = State;
    }
    /* ------------------------------------------------------------------------ */
    private void v_check_finish_UI()
    {
        ExerciseData.v_set_exercising(false);
        CloudDataStruct.CloudData_20.set_log_in(false);

        mMainActivity.mMainProcTreadmill.v_Goto_Home();

        CloudCmd_Info.vUpdate_Device03_info();
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {

        icount = 0 ;

        mMainActivity.mMainProcTreadmill.vSetNextState(MainState.PROC_HOME);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                mMainActivity.dismissInfoDialog();
            }
        });

        if(mNextState == EmergencyState.PROC_NULL)
        {
            vSetState(EmergencyState.PROC_EMERGENCY_UP);
            i_debouns_count = 0;
        }
        else
        {
            mState = mNextState;
        }

        v_check_finish_UI();

    }

    private void vProc_ShowPage_UP() {
        vChangeDisplayPage(mEmergencyUPLayout);

        icount = 0 ;
        b_UPDOWN = true;

        vSetState(EmergencyState.PROC_GETSTATUS) ;
    }

    private void vProc_ShowPage_DOWN() {

        vChangeDisplayPage(mEmergencyDOWNLayout);

        icount = 0 ;
        b_UPDOWN = false;

        vSetState(EmergencyState.PROC_GETSTATUS) ;
    }

    private void vProc_Uart15Cmd() {
        UartData.vUartCmd_clean_all();
        UartData.vUartCmd_15(500);

        i_debouns_count++;
        icount++;
        vSetState(EmergencyState.PROC_DELAY) ;
    }

    private void vProc_UartCmd_Delay() {

        icount++;
        UartData.S_CMD uartcmd = UartData.vUartCmd_get_list(UartData.vUartCmd_get_list_size() - 1);

        if(uartcmd != null) {
            int imode = uartcmd.imode & UartData.imode_read_mask;
            if (imode == 0) {
                if (uartcmd.icmd == guart.GET_READ_DATA1 || uartcmd.icmd == guart.GET_READ_DATA2) {
                    Exercisefunc.v_set_Exercise_Status(-1);
                    if(uartcmd.iresult == 0)
                    {
                        if(mUartcmd.bData1_parser())
                        {
                            HeartRateControl.HeartRate_from_Uart(ExerciseData.uart_data1.fheart_rate);
                        }
                    }
                    mState = EmergencyState.PROC_CACULATION;
                }
                else
                {
                    if(UartData.b_Can_clear_UartCmd())
                    {
                        mState = EmergencyState.PROC_GETSTATUS;
                    }
                }
            } else {
                vSetState(EmergencyState.PROC_DELAY);
            }
        }
        else {
            vSetState(EmergencyState.PROC_GETSTATUS);
        }

    }

    private void vProc_Caculation() {

        icount++;

        if(ExerciseData.i_get_finish() != 0x02 && i_debouns_count > i_debouns)
        {
            vSetNextState(EmergencyState.PROC_EXIT) ;
        }
        else
        {
            if(b_UPDOWN)
            {
                vSetNextState(EmergencyState.PROC_EMERGENCY_DOWN) ;
            }
            else
            {
                vSetNextState(EmergencyState.PROC_EMERGENCY_UP) ;
            }
        }

        vProc_Idle();
    }

    private void vProc_Idle()
    {
        if(mNextState != EmergencyState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = EmergencyState.PROC_NULL;
        }
    }

    private void vProc_Exit() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.removeExerciseViews(ExerciseData.Emergency_layer);
            }
        });

        mMainActivity.mMainProcTreadmill.exerciseProc.vSetIdleState();
        mMainActivity.mMainProcTreadmill.exerciseProc.vSetNextState(ExerciseState.PROC_EXIT);

        Exercisefunc.v_set_Exercise_Status(-1);
        mState = EmergencyState.PROC_INIT ;
        mNextState = EmergencyState.PROC_NULL;
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeExerciseViews(ExerciseData.Emergency_layer);
                mMainActivity.addExerciseView(ExerciseData.Emergency_layer, layout);
                layout.display();
            }
        });
    }

    /* ------------------------------------------------------------------------ */
    public void run() {
        if(tempState != mState)
        {
            //Log.e("Jerry", "[EmergencyProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                              :{   vProc_Init();                          break;  }
            case PROC_EMERGENCY_UP                      :{   vProc_ShowPage_UP();                          break;  }
            case PROC_EMERGENCY_DOWN                    :{   vProc_ShowPage_DOWN();          break;  }

            case PROC_GETSTATUS                :{   vProc_Uart15Cmd();           break;  }
            case PROC_DELAY                    :{   vProc_UartCmd_Delay();           break;  }
            case PROC_CACULATION               :{   vProc_Caculation();           break;  }

            case PROC_IDLE                              :{   vProc_Idle();                          break;  }
            case PROC_EXIT                              :{   vProc_Exit();                          break;  }
            default                                     :{   vProc_Idle();                          break;  }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////


}
