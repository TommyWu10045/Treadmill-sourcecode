package com.rtx.treadmill.RtxMainFunction.Exercise.CountDown;

import android.util.Log;

import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.retronix.circleuart.guart;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.Exercise.HeartRateControl;
import com.rtx.treadmill.UartDevice.UartData;
import com.rtx.treadmill.UartDevice.Uartcommand;

import static com.rtx.treadmill.GlobalData.ExerciseRunState.PROC_EXERCISE_PHY_GERKIN;
import static com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseState.PROC_EXERCISE_RUN;
import static com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseState.PROC_EXERCISE_STOP;

/**
 * Created by chasechang on 3/27/17.
 */

public class CountDownProc {
    private String TAG = "Jerry=" ;

    private MainActivity mMainActivity ;

    private CountDownState mState = CountDownState.PROC_INIT ;
    private CountDownState mNextState = CountDownState.PROC_NULL ;
    private CountDownState tempState = CountDownState.PROC_NULL ;

    private CountDown03Layout mCountDown03Layout;
    private CountDown02Layout mCountDown02Layout;
    private CountDown01Layout mCountDown01Layout;
    private CountDowngoLayout mCountDowngoLayout;
    private CountDownnullLayout mCountDownnullLayout;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    private int iStage_count ;
    private int icount ;
    private Uartcommand mUartcmd ;

    final private int iDelayCount = 0;

    public CountDownProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = CountDownState.PROC_INIT ;

        if(mUartcmd == null)              { mUartcmd =  mMainActivity.mUartcmd; }

        if(mCountDown03Layout == null)                { mCountDown03Layout = new CountDown03Layout(mMainActivity.mContext , mMainActivity); }
        if(mCountDown02Layout == null)                { mCountDown02Layout = new CountDown02Layout(mMainActivity.mContext , mMainActivity); }
        if(mCountDown01Layout == null)                { mCountDown01Layout = new CountDown01Layout(mMainActivity.mContext , mMainActivity); }
        if(mCountDowngoLayout == null)                { mCountDowngoLayout = new CountDowngoLayout(mMainActivity.mContext , mMainActivity); }
        if(mCountDownnullLayout == null)                { mCountDownnullLayout = new CountDownnullLayout(mMainActivity.mContext , mMainActivity); }

        icount = 0 ;
    }


    /* ------------------------------------------------------------------------ */

    public void vSetNextState(CountDownState nextState)
    {
        mNextState = nextState;
    }

    public void vSetState(CountDownState State)
    {
        mState = State;
    }
    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {

        icount = 0 ;
        iStage_count = 0;
        ExerciseData.v_set_exercising(true);

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                //Dismiss Language Info Dialog
                if(mMainActivity.dialogLayout_Language != null)
                {
                    if(mMainActivity.dialogLayout_Language.getDisplay() != null)
                    {
                        mMainActivity.dismissInfoDialog();
                    }
                }
            }
        });

        if(mNextState == CountDownState.PROC_NULL)
        {
            vSetState(CountDownState.PROC_COUNTDOWN_NULL);
        }
        else
        {
            vSetState(mNextState);
        }
    }

    private void vProc_ShowPage_null() {

        vChangeDisplayPage(mCountDownnullLayout);

        vSetState(CountDownState.PROC_GETSTATUS) ;
    }

    private void vProc_ShowPage_CountDown03() {

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.removeExerciseViews(ExerciseData.Pause_layer);
            }
        });

        ExerciseData.bSet_Is_Coundown(true);
        vChangeDisplayPage(mCountDown03Layout);

        vSetState(CountDownState.PROC_GETSTATUS);
    }

    private void vProc_ShowPage_CountDown02() {

        vChangeDisplayPage(mCountDown02Layout);

        vSetState(CountDownState.PROC_GETSTATUS) ;
    }

    private void vProc_ShowPage_CountDown01() {

        vChangeDisplayPage(mCountDown01Layout);

        vSetState(CountDownState.PROC_GETSTATUS) ;
    }

    private void vProc_ShowPage_CountDowngo() {

        vChangeDisplayPage(mCountDowngoLayout);

        vSetState(CountDownState.PROC_GETSTATUS) ;
    }

    private void vProc_ShowPage_start() {
        boolean bret = false;
        ExerciseData.DEVICE_COMMAND Uart_command;

        switch (ExerciseData.E_mode) {
            case PROC_EXERCISE_PHY_GERKIN:
                break;
            case PROC_EXERCISE_HEARTRATE:
                if((HeartRateControl.iHR_Get_status() & 0xF0) == 0x10)
                {
                    UartData.vUartCmd_61(20, "3");
                }
                break;
            default:
                UartData.vUartCmd_61(20, "3");
                break;
        }

        if (ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
            Uart_command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
            if (Uart_command != null) {
                UartData.set_uart_speed(Uart_command.fspeed);//20190220
                UartData.set_uart_incline(Uart_command.fincline);
                bret = true;
            }
        }

        if(bret)
        {
            mMainActivity.mMainProcTreadmill.exerciseProc.vSetNextState(PROC_EXERCISE_RUN);
        }
        else
        {
            mMainActivity.mMainProcTreadmill.exerciseProc.vSetNextState(PROC_EXERCISE_STOP);
        }

        vSetState(CountDownState.PROC_EXIT);
    }

    private void vProc_Uart15Cmd() {
        UartData.vUartCmd_clean_all();
        UartData.vUartCmd_15(500);

        icount = 0;
        vSetState(CountDownState.PROC_DELAY) ;
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
                    vSetState(CountDownState.PROC_CACULATION);
                }
                else
                {
                    if(UartData.b_Can_clear_UartCmd())
                    {
                        mState = CountDownState.PROC_GETSTATUS;
                    }
                }
            } else {
                vSetState(CountDownState.PROC_DELAY);
            }
        }
        else {
            vSetState(CountDownState.PROC_GETSTATUS);
        }
    }

    private void vProc_Caculation() {
        ExerciseData.uart_data1.clear();
        vNext_Stage();
    }

    private void vNext_Stage()
    {
        iStage_count++;
        switch (iStage_count)
        {
            case iDelayCount + 1 :
                vSetState(CountDownState.PROC_COUNTDOWN_3SEC) ;
                break;
            case iDelayCount + 3 :
                vSetState(CountDownState.PROC_COUNTDOWN_2SEC) ;
                break;
            case iDelayCount + 5 :
                vSetState(CountDownState.PROC_COUNTDOWN_1SEC) ;
                break;
            case iDelayCount + 7 :
                vSetState(CountDownState.PROC_COUNTDOWN_START) ;
                //vSetState(CountDownState.PROC_COUNTDOWN_GO) ;
                break;
            case iDelayCount + 9 :
                vSetState(CountDownState.PROC_COUNTDOWN_START) ;
                break;
            default:
                vSetState(CountDownState.PROC_COUNTDOWN_NULL) ;
                break;
        }
    }

    private void vProc_Run_Start_First() {
        UartData.vUartCmd_clean_all();

        UartData.set_uart_add_sub(5, EngSetting.f_Get_fspeed_add_val());
        UartData.set_uart_add_sub(6, EngSetting.f_Get_fspeed_sub_val());

        if(bCheckLogin())
        {
            UartData.vUartCmd_05(50, sGetUserUnit());
        }
        else
        {
            if(EngSetting.getUnit() == EngSetting.UNIT_METRIC) {
                UartData.vUartCmd_05(50, "0");
            }
            else
            {
                UartData.vUartCmd_05(50, "1");
            }
        }

        UartData.set_uart_limit_treadmill(Float.valueOf(EngSetting.Distance.getValString(EngSetting.f_Get_Min_Speed(), 1)), Float.valueOf(EngSetting.Distance.getValString(EngSetting.f_Get_Max_Speed(), 1)), EngSetting.f_Get_Min_Incline(), EngSetting.f_Get_Max_Incline());

        switch (ExerciseData.E_mode)
        {
            case PROC_EXERCISE_PHY_GERKIN:
                UartData.vUartCmd_64(20, "0");
                break;
            case PROC_EXERCISE_PHY_COOPER:
                UartData.vUartCmd_64(20, "2");
                break;
            case PROC_EXERCISE_PHY_USMC:
                UartData.vUartCmd_64(20, "2");
                break;
            case PROC_EXERCISE_PHY_ARMY:
                UartData.vUartCmd_64(20, "2");
                break;
            case PROC_EXERCISE_PHY_NAVY:
                UartData.vUartCmd_64(20, "2");
                break;
            case PROC_EXERCISE_PHY_USAF:
                UartData.vUartCmd_64(20, "2");
                break;
            case PROC_EXERCISE_PHY_FEDERAL:
                UartData.vUartCmd_64(20, "2");
                break;
            default:
                UartData.vUartCmd_64(20, "1");
                break;
        }

        icount = 0;
        iStage_count = 0;
        vSetState(CountDownState.PROC_COUNTDOWN_NULL);

    }

    private void vProc_Idle()
    {
        if(mNextState != CountDownState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = CountDownState.PROC_NULL;
        }
    }

    private void vProc_Exit() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.removeExerciseViews(ExerciseData.CountDown_layer);
            }
        });
        
        mMainActivity.mMainProcTreadmill.exerciseProc.vSetIdleState();

        mState = CountDownState.PROC_INIT ;
        mNextState = CountDownState.PROC_NULL;
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeExerciseViews(ExerciseData.CountDown_layer);
                mMainActivity.addExerciseView(ExerciseData.CountDown_layer, layout);
                layout.display();
            }
        });
    }

    private boolean bCheckLogin()
    {
        boolean bLogin = false;

        bLogin = CloudDataStruct.CloudData_20.is_log_in();

        return bLogin;
    }

    private String sGetUserUnit()
    {
        String sUnit = "0";

        if(CloudDataStruct.CloudData_17.Get_output(Cloud_17_GET_USR_BSC.Output.WEI_UNT).equals("E"))
        {
            sUnit = "1";
        }
        else if(CloudDataStruct.CloudData_17.Get_output(Cloud_17_GET_USR_BSC.Output.WEI_UNT).equals("M"))
        {
            sUnit = "0";
        }

        return sUnit;
    }

    /* ------------------------------------------------------------------------ */
    public void run() {
        if(tempState != mState)
        {
//            Log.e("Jerry", "[CountDownProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                              :{   vProc_Init();                          break;  }
            case PROC_COUNTDOWN_INIT                    :{   vProc_Run_Start_First();               break;  }
            case PROC_COUNTDOWN_3SEC                    :{   vProc_ShowPage_CountDown03();          break;  }
            case PROC_COUNTDOWN_2SEC                    :{   vProc_ShowPage_CountDown02();          break;  }
            case PROC_COUNTDOWN_1SEC                    :{   vProc_ShowPage_CountDown01();          break;  }
            case PROC_COUNTDOWN_GO                      :{   vProc_ShowPage_CountDowngo();          break;  }
            case PROC_COUNTDOWN_START                   :{   vProc_ShowPage_start();                break;  }
            case PROC_COUNTDOWN_NULL                    :{   vProc_ShowPage_null();                 break;  }

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
