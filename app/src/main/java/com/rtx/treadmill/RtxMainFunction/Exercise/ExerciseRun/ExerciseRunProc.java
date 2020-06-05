package com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseRun;

import android.util.Log;

import com.retronix.circleuart.guart;
import com.rtx.treadmill.GlobalData.Bottom_UI_Info;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.ExerciseRunState;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseState;
import com.rtx.treadmill.RtxMainFunction.Exercise.Exercisefunc;
import com.rtx.treadmill.RtxMainFunction.Exercise.HeartRateControl;
import com.rtx.treadmill.RtxMainFunction.Exercise.Pause.PauseState;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxTools.Rtx_Debug;
import com.rtx.treadmill.UartDevice.UartData;
import com.rtx.treadmill.UartDevice.Uartcommand;

/**
 * Created by chasechang on 3/27/17.
 */

public class ExerciseRunProc {
    private String TAG = "Jerry=" ;
    private boolean DEBUG = false ;

    private MainActivity mMainActivity ;

    private ExerciseRunState mState;
    private ExerciseRunState mNextState = ExerciseRunState.PROC_NULL ;
    private ExerciseRunState tempState = ExerciseRunState.PROC_NULL ;

    private Uartcommand mUartcmd ;
    private Exercisefunc mExercisefunc;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    private int icount ;
    private long        lStartTimeMillis    = 0 ;
    private long        lNowTimeMillis      = 0 ;

    public ExerciseRunProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = ExerciseRunState.PROC_INIT ;

        if(mUartcmd == null)              { mUartcmd =  mMainActivity.mUartcmd; }
        if(mExercisefunc == null)                { mExercisefunc = new Exercisefunc(mMainActivity.mUartcmd); }

    }


    /* ------------------------------------------------------------------------ */

    public void vSetNextState(ExerciseRunState nextState)
    {
        mNextState = nextState;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {

        vExerciseRunProc_SetBottomUIStage();

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        mState = ExerciseData.E_mode;
        Exercisefunc.v_set_Exercise_Status(-1);
    }

    private void vProc_Phy_GERKIN() {
        vProc_QuickStart();
    }

    private void vProc_Phy_COOPER() {
        vProc_QuickStart();
    }

    private void vProc_Phy_USMC() {
        vProc_QuickStart();
    }

    private void vProc_TraingCourse() {
        vProc_QuickStart();
    }

    private void vProc_HeartRateStart() {
        vProc_QuickStart();
    }

    private void vProc_WorkoutStart() {
        vProc_QuickStart();
    }

    private void vProc_HiitStart() {
        vProc_QuickStart();
    }

    private void vProc_QuickStart() {
        lStartTimeMillis = System.currentTimeMillis() ;
        mState = ExerciseRunState.PROC_EXERCISE_GETSTATUS;
    }

    private void vProc_Uart15Cmd() {
        UartData.vUartCmd_clean_all();
        UartData.vUartCmd_15(200);
        icount = 0;
        mState = ExerciseRunState.PROC_EXERCISE_DELAY;
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
                    mState = ExerciseRunState.PROC_EXERCISE_CACULATION;
                }
                else
                {
                    if(UartData.b_Can_clear_UartCmd())
                    {
                        mState = ExerciseRunState.PROC_EXERCISE_GETSTATUS;
                    }
                }
            } else {
                if(icount < uartcmd.idelay)//uart command，在idelay時間內無法完成，即判定為timeout
                {
                    mState = ExerciseRunState.PROC_EXERCISE_DELAY;
                }
                else
                {
                    mState = ExerciseRunState.PROC_EXERCISE_CACULATION;
                }
            }
        }
        else {
            mState = ExerciseRunState.PROC_EXERCISE_GETSTATUS;
        }

        vProc_Idle();

    }

    private void vProc_Caculation() {
        long ldiff;

        lNowTimeMillis = System.currentTimeMillis() ;
        ldiff = lNowTimeMillis - lStartTimeMillis;

        if(ldiff > 0 && ldiff < 10000)
        {
            mExercisefunc.Target_check(ldiff);
        }
        else
        {
            //防止ldiff數值異常現象
            Log.e(TAG, "lNowTimeMillis=" + lNowTimeMillis + "     lStartTimeMillis=" + lStartTimeMillis + "     ldiff=" + ldiff);
        }

        lStartTimeMillis = lNowTimeMillis;

        ExerciseData.v_set_finish(ExerciseData.iFinish_status);

        if(ExerciseData.i_get_finish() == 0x00)
        {
            UartData.vUartCmd_61(20, "2");
            vProc_Exit(ExerciseState.PROC_EXERCISE_STOP); //finish
        }
        else if(ExerciseData.i_get_finish() == 0x01)
        {
            vProc_Exit(ExerciseState.PROC_EXERCISE_STOP); //stop key
        }
        else if(ExerciseData.i_get_finish() == 0x02)
        {
            vProc_Exit(ExerciseState.PROC_EXERCISE_EMERGENCY); //emergency key
        }
        else if(ExerciseData.i_get_finish() == 0x03) //Device error
        {
            vProc_Exit(ExerciseState.PROC_EXERCISE_ERROR_CODE);
        }
        else if(ExerciseData.i_get_finish() == 0x04) //pause key
        {
            vProc_Exit(ExerciseState.PROC_EXERCISE_PAUSE);
        }
        else if(ExerciseData.i_get_finish() == 0x05) //cooldown key
        {
            vCheck_CoolDown_Mode();
        }
        else if(ExerciseData.i_get_finish() == 0x06) //start key
        {
            mState = ExerciseRunState.PROC_EXERCISE_GETSTATUS;
        }
        else if(ExerciseData.i_get_finish() == 0x10) //heartrate start detect timeout
        {
            mMainActivity.mMainProcTreadmill.exerciseProc.pauseProc.vSetNextState(PauseState.PROC_PAUSE_MODE2);
            vProc_Exit(ExerciseState.PROC_EXERCISE_PAUSE);
        }
        else
        {
            mState = ExerciseRunState.PROC_EXERCISE_GETSTATUS;
        }

        //設定目標為時間時，檢查目標是否有改變，有改變時，運動的list也要更新
        v_targettime_change();

        //設定目標為距離、卡洛里、無目標(如quick start)時，檢查運動的list是否已到顯示的後端，到達時增加運動的list
        v_infinite_loop();

        vProc_Idle();
    }

    private void vCheck_CoolDown_Mode()
    {
        ExerciseRunState imode = ExerciseData.E_mode.fromId(ExerciseData.E_imode & ExerciseData.M_mask_type);

        mState = ExerciseRunState.PROC_EXERCISE_GETSTATUS;

        switch (imode)
        {
            case PROC_EXERCISE_TR:
                vGoto_CoolDown_Mode();
                break;

            case PROC_EXERCISE_PHY:
                vGoto_CoolDown_Mode();
                break;

            case PROC_EXERCISE_HEARTRATE:
                vGoto_CoolDown_Mode();
                break;

            case PROC_EXERCISE_HIIT:
                //vGoto_CoolDown_Mode(); //modify by Steven 2020/04/10
                break;

            case PROC_EXERCISE_WORKOUT:
                vGoto_CoolDown_Mode();
                break;

            case PROC_EXERCISE_QUICKSTART:
                vGoto_CoolDown_Mode();
                break;

            default:
                break;
        }

//        if(ExerciseData.uart_data1.fspeed > 3f) {
//            vProc_Exit(ExerciseState.PROC_EXERCISE_COOLDOWN);
//        }
//        else
//        {
//            vProc_Exit(ExerciseState.PROC_EXERCISE_STOP);
//            ExerciseData.v_set_finish(0x01);          //CoolDown finish
//        }
    }

    private void vGoto_CoolDown_Mode()
    {
//        if(!Rtx_Debug.bGetRELEASE_MODE())
        {
            vProc_Exit(ExerciseState.PROC_EXERCISE_COOLDOWN);
        }
    }

    private void vProc_Idle()
    {
        if(mNextState != ExerciseRunState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = ExerciseRunState.PROC_NULL;
        }
    }

    private void vProc_Exit(ExerciseState mstate) {
        mMainActivity.mMainProcTreadmill.exerciseProc.vSetIdleState();
        mMainActivity.mMainProcTreadmill.exerciseProc.vSetNextState(mstate);

        mState = ExerciseRunState.PROC_INIT ;
        mNextState = ExerciseRunState.PROC_NULL;
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    ////////////////////////////////////////////
    private void v_targettime_change() {
        switch (ExerciseData.E_mode)
        {
            case PROC_EXERCISE_TR_MANUAL:
            case PROC_EXERCISE_TR_TIME:
                v_CheckTime_list();
            default:
                break;
        }
    }

    //Check Target Time list
    private void v_CheckTime_list() {

        int iLoop, iAdd;
        int icount_total ;
        int itime_total ;
        int istep_sec = ExerciseData.iStep_time;

        ExerciseData.DEVICE_COMMAND Uart_command = null;

        icount_total = ExerciseData.list_real_setting.size();
        itime_total = ExerciseGenfunc.f_get_target_time()/istep_sec;

        if(icount_total > itime_total) {
            iAdd = icount_total - itime_total;
            for (iLoop = 0; iLoop < iAdd; iLoop++) {
                ExerciseData.list_real_setting.remove(icount_total - iLoop - 1);
                ExerciseData.list_original_setting.remove(icount_total - iLoop - 1);
            }
        }
        else if(icount_total < itime_total) {
            iAdd = itime_total - icount_total;

            Uart_command = ExerciseData.list_real_setting.get(ExerciseData.list_real_setting.size() - 1);

            if (Uart_command != null) {
                for (iLoop = 0; iLoop < iAdd; iLoop++) {
                    ExerciseData.DEVICE_COMMAND suart_cmd_orig = new ExerciseData.DEVICE_COMMAND();

                    suart_cmd_orig.bcal = true;
                    suart_cmd_orig.isec = istep_sec;
                    suart_cmd_orig.fspeed = Uart_command.fspeed;
                    suart_cmd_orig.fincline = Uart_command.fincline;

                    ExerciseData.list_real_setting.add(suart_cmd_orig);
                    ExerciseData.list_original_setting.add(suart_cmd_orig);
                }
            }
        }

    }

    //list_real_setting 要比 ilist_count 大，保待profile有資料
    private void v_infinite_loop() {
        switch (ExerciseData.E_mode)
        {
            case PROC_EXERCISE_TR_DISTANCE:
            case PROC_EXERCISE_TR_CALORIES:
            case PROC_EXERCISE_QUICKSTART:

            case PROC_EXERCISE_PHY_USMC:
            case PROC_EXERCISE_PHY_ARMY:
            case PROC_EXERCISE_PHY_NAVY:
            case PROC_EXERCISE_PHY_USAF:
            case PROC_EXERCISE_PHY_FEDERAL:
                if(b_Infinite_Time()) {
                    v_Create_Infinite_list();
                }
                break;
            default:
                break;
        }

    }

    private boolean b_Infinite_Time()
    {
        boolean bret = false;
        int icount_total;
        int icount_list;
        int iprofile_total = ExerciseData.ilist_virtual_num;

        if (ExerciseData.list_real_setting != null) {

            icount_total = ExerciseData.list_real_setting.size();
            icount_list = ExerciseData.ilist_count + iprofile_total;

            if (icount_list >= icount_total) {
                return true;
            }

        }

        return bret;
    }

    private void v_Create_Infinite_list() {
        int iLoop;
        int iAdd = ExerciseData.ilist_virtual_num;
        int istep_sec = ExerciseData.iStep_time;
        ExerciseData.DEVICE_COMMAND Uart_command = null;

        if(ExerciseData.list_real_setting.size() > 0) {

            Uart_command = ExerciseData.list_real_setting.get(ExerciseData.list_real_setting.size() - 1);

            if (Uart_command != null) {
                for (iLoop = 0; iLoop < iAdd; iLoop++) {
                    ExerciseData.DEVICE_COMMAND suart_cmd_orig = new ExerciseData.DEVICE_COMMAND();

                    suart_cmd_orig.bcal = true;
                    suart_cmd_orig.isec = istep_sec;
                    suart_cmd_orig.fspeed = Uart_command.fspeed;
                    suart_cmd_orig.fincline = Uart_command.fincline;

                    ExerciseData.list_real_setting.add(suart_cmd_orig);
                    ExerciseData.list_original_setting.add(suart_cmd_orig);
                }
            }
        }

    }

    //恢復在 countdown 狀態下按 stop 產生的 Bottom_UI_Info.clear(); 的狀態
    private void vExerciseRunProc_SetBottomUIStage() {
        switch (ExerciseData.E_mode)
        {
            case PROC_EXERCISE_TR_MANUAL:
            case PROC_EXERCISE_TR_HILL:
            case PROC_EXERCISE_TR_FATBURN:
            case PROC_EXERCISE_TR_CARDIO:
            case PROC_EXERCISE_TR_STRENGHT:
            case PROC_EXERCISE_TR_INTERVAL:
            case PROC_EXERCISE_TR_CALORIES:
            case PROC_EXERCISE_TR_TIME:
            case PROC_EXERCISE_TR_DISTANCE:

            case PROC_EXERCISE_QUICKSTART:
            case PROC_EXERCISE_HEARTRATE:
            case PROC_EXERCISE_WORKOUT:
                if (Bottom_UI_Info.iGet_PageState() == 1)  //Simple
                {
                    Bottom_UI_Info.v_clear_stage(0x01);
                }
                else
                {
                    Bottom_UI_Info.v_set_stage(0x01);
                }
                break;
            default:
                break;
        }

    }

    /* ------------------------------------------------------------------------ */
    public void run() {
        if(tempState != mState)
        {
            //Log.e("Jerry", "[ExerciseRunProc] mState = " + mState);
            tempState = mState;
        }
        
        switch( mState )
        {
            case PROC_INIT                              :{   vProc_Init();                   break;  }

            case PROC_EXERCISE_PHY_GERKIN               :{   vProc_Phy_GERKIN();             break;  }
            case PROC_EXERCISE_PHY_COOPER               :{   vProc_Phy_COOPER();             break;  }
            case PROC_EXERCISE_PHY_USMC                 :{   vProc_Phy_USMC();               break;  }
            case PROC_EXERCISE_PHY_ARMY                 :{   vProc_Phy_USMC();               break;  }
            case PROC_EXERCISE_PHY_NAVY                 :{   vProc_Phy_USMC();               break;  }
            case PROC_EXERCISE_PHY_USAF                 :{   vProc_Phy_USMC();               break;  }
            case PROC_EXERCISE_PHY_FEDERAL              :{   vProc_Phy_USMC();               break;  }

            case PROC_EXERCISE_TR_MANUAL:                {   vProc_TraingCourse();           break;  }
            case PROC_EXERCISE_TR_HILL:                  {   vProc_TraingCourse();           break;  }
            case PROC_EXERCISE_TR_FATBURN:               {   vProc_TraingCourse();           break;  }
            case PROC_EXERCISE_TR_CARDIO:                {   vProc_TraingCourse();           break;  }
            case PROC_EXERCISE_TR_STRENGHT:              {   vProc_TraingCourse();           break;  }
            case PROC_EXERCISE_TR_INTERVAL:              {   vProc_TraingCourse();           break;  }
            case PROC_EXERCISE_TR_CALORIES:              {   vProc_TraingCourse();           break;  }
            case PROC_EXERCISE_TR_TIME:                  {   vProc_TraingCourse();           break;  }
            case PROC_EXERCISE_TR_DISTANCE:              {   vProc_TraingCourse();           break;  }

            case PROC_EXERCISE_QUICKSTART:               {   vProc_QuickStart();             break;  }
            case PROC_EXERCISE_HEARTRATE:                {   vProc_HeartRateStart();         break;  }
            case PROC_EXERCISE_WORKOUT:                  {   vProc_WorkoutStart();           break;  }
            case PROC_EXERCISE_HIIT:                     {   vProc_HiitStart();              break;  }


            case PROC_EXERCISE_GETSTATUS                :{   vProc_Uart15Cmd();              break;  }
            case PROC_EXERCISE_DELAY                    :{   vProc_UartCmd_Delay();          break;  }
            case PROC_EXERCISE_CACULATION               :{   vProc_Caculation();             break;  }

            case PROC_IDLE                              :{   vProc_Idle();                   break;  }
            default                                     :{   vProc_Idle();                   break;  }
        }


    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////


}
