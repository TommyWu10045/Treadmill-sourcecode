package com.rtx.treadmill.IdleTimeout;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;

import com.rtx.treadmill.Dialog.Dialog_UI_Info;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainProcBike;
import com.rtx.treadmill.MainProcTreadmill;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.Home.HomeState;

import java.io.IOException;

/**
 * Created by chasechang on 3/27/17.
 */

public class IdleTimeoutProc {
    private boolean DEBUG = false ;
    private String TAG = "Jerry" ;

    private Context mContext;
    private MainActivity mMainActivity ;

    private IdleTimeoutState mState = IdleTimeoutState.PROC_INIT ;
    private IdleTimeoutState mNextState = IdleTimeoutState.PROC_NULL ;
    private IdleTimeoutState tempState = IdleTimeoutState.PROC_NULL ;

    private int iIdleTimeOutProcCountDownFlag = 0;
    private int iIdleTimeOutProcShowFlag = 0;

    private int icount = 0 ;
    private int iretry_count = 0 ;

    public IdleTimeoutProc(MainActivity mMainActivity) {
        this.mContext = mMainActivity.mContext;
        this.mMainActivity = mMainActivity ;

        mState = IdleTimeoutState.PROC_INIT;

        icount = 0 ;
    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(IdleTimeoutState nextState)
    {
        mNextState = nextState;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {
        icount = 0 ;
        iretry_count = 0 ;
        if(mNextState == IdleTimeoutState.PROC_NULL)
        {
            mNextState = IdleTimeoutState.PROC_IDLE_TIMEOUT_CHECK;
        }
        else
        {

        }

        mState = IdleTimeoutState.PROC_IDLE;
    }

    private boolean bCheckLogin()
    {
        boolean bCheck = false;

        bCheck = CloudDataStruct.CloudData_20.is_log_in();

        return bCheck;
    }

    private void vLogout()
    {
        CloudDataStruct.CloudData_20.set_log_in(false);
    }

    private boolean bCheckInExercise()
    {
        boolean bCheck = false;

        bCheck = ExerciseData.b_is_exercising();

        return bCheck;
    }

    private boolean bCheckInHome(MainProcTreadmill procTreadmill)
    {
        boolean bCheck = false;

        if(procTreadmill != null)
        {
            if(procTreadmill.getPageState() == MainState.PROC_HOME)
            {
                bCheck = true;
            }
        }

        return bCheck;
    }

    private boolean bCheckInHome(MainProcBike procBike)
    {
        boolean bCheck = false;

        if(procBike != null)
        {
            if(procBike.getPageState() == MainState.PROC_HOME)
            {
                bCheck = true;
            }
        }

        return bCheck;
    }

    private void vProc_Check()
    {
        if(mMainActivity.mMainProcTreadmill != null)
        {
            vProc_Check(mMainActivity.mMainProcTreadmill);
        }
        else if(mMainActivity.mMainProcBike != null)
        {
            vProc_Check(mMainActivity.mMainProcBike);
        }
    }

    private void vProc_Check(MainProcTreadmill procTreadmill)
    {
        if(!bCheckInExercise())
        {
            if(bCheckLogin())
            {
                if(!bCheckInHome(procTreadmill))
                {
                    if(GlobalData.bCheckInteractionTime(GlobalData.CHECK_INTERACTION_120_SEC))
                    {
                        GlobalData.vResetInteractionTime();
                        GlobalData.vSetInteractionStartTime();
                        iIdleTimeOutProcCountDownFlag = 1;
                    }

                    //進入60秒倒數
                    if (iIdleTimeOutProcCountDownFlag == 1) {
                        if(GlobalData.bCheckInteractionStartTime(GlobalData.CHECK_INTERACTION_60_SEC))
                        {
                            mMainActivity.mUI_Handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mMainActivity.dismissInfoDialog();
                                }
                            });

                            iIdleTimeOutProcCountDownFlag = 0;
                            Dialog_UI_Info.clear();
                            vLogout();
                            procTreadmill.v_Goto_Home();
                            GlobalData.vResetInteractionTime();
                        }
                        else
                        {
                            icount = 0;

                            mMainActivity.mUI_Handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //show dialog
                                    int iSec = 0;

                                    iSec = 60 - (int) GlobalData.lGetInteractionDiffSec(GlobalData.lInteractionStartTime);
                                    if(iSec > 60){ iSec = 60; }
                                    else if(iSec < 0){ iSec = 0; }

                                    mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_TIMEOUT_RESET, iSec, null);
                                }
                            });

                            if (mMainActivity.dialogLayout_TimeoutReset != null) {
                                if (iIdleTimeOutProcShowFlag == 1)
                                {
                                    if (mMainActivity.dialogLayout_TimeoutReset.isShown()) {
                                    } else {
                                        iIdleTimeOutProcCountDownFlag = 0;
                                        iIdleTimeOutProcShowFlag = 0;
                                    }
                                }
                                else {
                                    if (mMainActivity.dialogLayout_TimeoutReset.isShown()) {
                                        iIdleTimeOutProcShowFlag = 1;
                                    }
                                }
                            }
                        }
                    }
                    else if (icount == 1)
                    {
                        mMainActivity.mUI_Handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mMainActivity.dismissInfoDialog();
                            }
                        });
                    }
                    else { }
                }
                else
                {
                    if(GlobalData.bCheckInteractionTime(GlobalData.CHECK_INTERACTION_180_SEC))
                    {
                        mMainActivity.mUI_Handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mMainActivity.dismissInfoDialog();
                            }
                        });

                        Dialog_UI_Info.clear();
                        procTreadmill.homeProc.vSetNextState(HomeState.PROC_LOGOUT);
                        GlobalData.vResetInteractionTime();
                    }
                }
            }
            else
            {
                if(!bCheckInHome(procTreadmill))
                {
                    if(GlobalData.bCheckInteractionTime(GlobalData.CHECK_INTERACTION_120_SEC))
                    {
                        //返回兩次,解除鍵盤被系統卡住的問題
                        try {
                            Runtime.getRuntime().exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Runtime.getRuntime().exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        mMainActivity.mUI_Handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mMainActivity.dismissInfoDialog();
                            }
                        });

                        Dialog_UI_Info.clear();
                        procTreadmill.v_Goto_Home();
                        GlobalData.vResetInteractionTime();
                    }
                }
            }
        }
        else
        {
            GlobalData.vResetInteractionTime();
        }

        icount++;
    }

    private void vProc_Check(MainProcBike procBike)
    {
        if(!bCheckInExercise())
        {
            if(bCheckLogin())
            {
                if(!bCheckInHome(procBike))
                {
                    if(GlobalData.bCheckInteractionTime(GlobalData.CHECK_INTERACTION_120_SEC))
                    {
                        GlobalData.vResetInteractionTime();
                        GlobalData.vSetInteractionStartTime();
                        iIdleTimeOutProcCountDownFlag = 1;
                    }

                    //進入60秒倒數
                    if (iIdleTimeOutProcCountDownFlag == 1) {
                        if(GlobalData.bCheckInteractionStartTime(GlobalData.CHECK_INTERACTION_60_SEC))
                        {
                            mMainActivity.mUI_Handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mMainActivity.dismissInfoDialog();
                                }
                            });

                            iIdleTimeOutProcCountDownFlag = 0;
                            vLogout();
                            procBike.v_Goto_Home();
                            GlobalData.vResetInteractionTime();
                        }
                        else
                        {
                            icount = 0;

                            mMainActivity.mUI_Handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //show dialog
                                    int iSec = 0;

                                    iSec = 60 - (int) GlobalData.lGetInteractionDiffSec(GlobalData.lInteractionStartTime);
                                    if(iSec > 60){ iSec = 60; }
                                    else if(iSec < 0){ iSec = 0; }

                                    mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_TIMEOUT_RESET, iSec, null);
                                }
                            });

                            if (mMainActivity.dialogLayout_TimeoutReset != null) {
                                if (iIdleTimeOutProcShowFlag == 1)
                                {
                                    if (mMainActivity.dialogLayout_TimeoutReset.isShown()) {
                                    } else {
                                        iIdleTimeOutProcCountDownFlag = 0;
                                        iIdleTimeOutProcShowFlag = 0;
                                    }
                                }
                                else {
                                    if (mMainActivity.dialogLayout_TimeoutReset.isShown()) {
                                        iIdleTimeOutProcShowFlag = 1;
                                    }
                                }
                            }
                        }
                    }
                    else if (icount == 1)
                    {
                        mMainActivity.mUI_Handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mMainActivity.dismissInfoDialog();
                            }
                        });
                    }
                    else { }
                }
                else
                {
                    if(GlobalData.bCheckInteractionTime(GlobalData.CHECK_INTERACTION_180_SEC))
                    {
                        mMainActivity.mUI_Handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mMainActivity.dismissInfoDialog();
                            }
                        });

                        procBike.homeProc.vSetNextState(com.rtx.treadmill.RtxMainFunctionBike.Home.HomeState.PROC_LOGOUT);
                        GlobalData.vResetInteractionTime();
                    }
                }
            }
            else
            {
                if(!bCheckInHome(procBike))
                {
                    if(GlobalData.bCheckInteractionTime(GlobalData.CHECK_INTERACTION_120_SEC))
                    {
                        //返回兩次,解除鍵盤被系統卡住的問題
                        try {
                            Runtime.getRuntime().exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Runtime.getRuntime().exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        mMainActivity.mUI_Handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mMainActivity.dismissInfoDialog();
                            }
                        });

                        procBike.v_Goto_Home();
                        GlobalData.vResetInteractionTime();
                    }
                }
            }
        }
        else
        {
            GlobalData.vResetInteractionTime();
        }

        icount++;
    }

    private void vProc_Exit() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                //mMainActivity.removeExerciseViews(ExerciseData.Dialog_layer);
            }
        });

        mState = mState = IdleTimeoutState.PROC_INIT;
        mNextState = IdleTimeoutState.PROC_NULL;
    }

    private void vProc_Idle()
    {
        if(mNextState != IdleTimeoutState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = IdleTimeoutState.PROC_NULL;
        }
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeExerciseViews(ExerciseData.Dialog_layer);
                mMainActivity.addExerciseView(ExerciseData.Dialog_layer, layout);
                layout.setClickable(true);
                layout.display();
            }
        });
    }

    /* ------------------------------------------------------------------------ */
    public void run() {

//        if(tempState != mState)
//        {
//            Log.e("Jerry", "[IdleTimeout] mState = " + mState);
//            tempState = mState;
//        }

        switch( mState )
        {
            case PROC_INIT                             :{   vProc_Init();                          break;  }
            case PROC_IDLE_TIMEOUT_CHECK               :{   vProc_Check();                         break;  }


            case PROC_IDLE                              :{   vProc_Idle();                          break;  }
            case PROC_EXIT                              :{   vProc_Exit();                          break;  }
            default                                     :{   vProc_Idle();                          break;  }
        }
    }
}
