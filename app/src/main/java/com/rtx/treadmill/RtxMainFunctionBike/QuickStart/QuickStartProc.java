package com.rtx.treadmill.RtxMainFunctionBike.QuickStart;

import android.content.Context;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.ExerciseRunState;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.CountDown.CountDownState;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.ExerciseState;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.HeartRateControl;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;

/**
 * Created by chasechang on 3/27/17.
 */

public class QuickStartProc {
    private String TAG = "Jerry=" ;

    private MainActivity mMainActivity ;
    private Context mContext;

    private QuickStartState mState = QuickStartState.PROC_INIT ;
    private QuickStartState mNextState = QuickStartState.PROC_NULL ;
    private QuickStartState tempState = QuickStartState.PROC_NULL ;

    private QuickStartExerciseLayout mExerciseLayout                ;
    private AllFinishLayout  mAllFinishLayout                ;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    private int ilevel ;
    private float fweight ;
    private float ftarget ;

    public QuickStartProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        this.mContext = mMainActivity.mContext;

        mState = QuickStartState.PROC_INIT ;

        if(mExerciseLayout       == null) { mExerciseLayout      =  new QuickStartExerciseLayout(mMainActivity.mContext, mMainActivity);}
        if(mAllFinishLayout       == null) { mAllFinishLayout      =  new AllFinishLayout          (mMainActivity.mContext, mMainActivity);}

    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(QuickStartState nextState)
    {
        mNextState = nextState;
    }
    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        if(mNextState == QuickStartState.PROC_NULL)
        {
            v_Create_distance(ftarget);
            mState = QuickStartState.PROC_EXERCISE_CHECK_COUNTDOWN;
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_Check_Is_Countdown() {

        boolean bret = ExerciseData.bGet_Is_Coundown();

        if(bret)
        {
            vSetNextState(QuickStartState.PROC_EXERCISE_SHOW);
        }

        vProc_Idle() ;
    }

    private void vProc_ShowPage_Exercise() {

        vChangeDisplayPage(mExerciseLayout);

        mState = QuickStartState.PROC_EXERCISE_INIT ;
    }

    private void vProc_Exercise_Init() {

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(mExerciseLayout != null)
                {
                    mExerciseLayout.Refresh();
                }
            }
        });

        mState = QuickStartState.PROC_EXERCISE_REFRESH ;
    }

    private void vProc_Exercise_Refresh() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(mExerciseLayout != null)
                {
                    mExerciseLayout.Refresh();
                }
            }
        });

        vProc_Idle();
    }

    private void vProc_Exercise_Summary() {

        vChangeDisplayPage(mAllFinishLayout);

        mState = QuickStartState.PROC_IDLE ;
    }

    private void vProc_Done() {

        vMainChangePage(MainState.PROC_HOME);
        mState = QuickStartState.PROC_IDLE ;
    }

    private void vProc_Logout() {
        CloudDataStruct.CloudData_20.set_log_in(false);

        vMainChangePage(MainState.PROC_HOME);
        mState = QuickStartState.PROC_IDLE ;
    }

    private void vProc_Idle()
    {
        if(mNextState != QuickStartState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = QuickStartState.PROC_NULL;
        }
    }

    private void vProc_Exit() {
        mMainActivity.mMainProcBike.vSetIdleState();
        mState = QuickStartState.PROC_INIT ;
        mNextState = QuickStartState.PROC_NULL;
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeAllViews();
                mMainActivity.addView(layout);
                layout.display();
            }
        });
    }

    public void vMainChangePage(MainState state)
    {
        mMainActivity.mMainProcBike.quickStartProc.vSetNextState(QuickStartState.PROC_EXIT);
        mMainActivity.mMainProcBike.vSetNextState(state);
    }

    public void vReturnMainProc()
    {
        if(mMainActivity.mMainProcBike.vGetBeforeLoginState() == MainState.PROC_NULL)
        {
            mMainActivity.mMainProcBike.trainingProc.vMainChangePage(MainState.PROC_HOME);
        }
        else
        {
            mMainActivity.mMainProcBike.trainingProc.vMainChangePage(mMainActivity.mMainProcBike.vGetBeforeLoginState());
            mMainActivity.mMainProcBike.vClearBeforeLoginState();
        }
    }

    ///////////////////////////////////
    private void v_Create_distance(float ftarget)
    {
        int iLoop ;
        int istep_sec = ExerciseData.iStep_time;
        int imax = ExerciseData.ilist_max;
        float fw;

        ExerciseData.clear(); //clear all data
        HeartRateControl.clear();

        fw = CloudDataStruct.CloudData_17.f_get_user_weight();
        ExerciseData.v_set_weight(fw);

        for(iLoop = 0; iLoop < imax; iLoop++) {
            ExerciseData.DEVICE_COMMAND suart_cmd_orig = new ExerciseData.DEVICE_COMMAND();

            suart_cmd_orig.bcal = true;
            suart_cmd_orig.isec = istep_sec;
            suart_cmd_orig.fspeed = EngSetting.f_Get_Def_Speed();
            suart_cmd_orig.fincline = EngSetting.f_Get_Def_level();

            ExerciseData.list_original_setting.add(suart_cmd_orig);
        }
        ExerciseGenfunc.v_Exercise_Keep();

        ExerciseGenfunc.v_set_target_limit(1000, Integer.MAX_VALUE);
        ExerciseGenfunc.v_set_target_distance(Integer.MAX_VALUE);

        ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_QUICKSTART);
        mMainActivity.mMainProcBike.exerciseProc.countdownProc.vSetNextState(CountDownState.PROC_COUNTDOWN_INIT);
        mMainActivity.mMainProcBike.exerciseProc.vSetNextState(ExerciseState.PROC_EXERCISE_COUNTDOWN);
    }

    /* ------------------------------------------------------------------------ */
    public void run() {
        if(tempState != mState)
        {
            //Log.e("Jerry", "[quickStartProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                              :{   vProc_Init();                          break;  }
            case PROC_EXERCISE_SHOW                     :{   vProc_ShowPage_Exercise();             break;  }
            case PROC_EXERCISE_INIT                     :{   vProc_Exercise_Init();                 break;  }
            case PROC_EXERCISE_REFRESH                  :{   vProc_Exercise_Refresh();              break;  }
            case PROC_EXERCISE_QS_FINISH                   :{   vProc_Exercise_Summary();              break;  }
            case PROC_EXERCISE_CHECK_COUNTDOWN          :{   vProc_Check_Is_Countdown();              break;  }

            case PROC_SHOW_DONE                         :{   vProc_Done();                          break;  }
            case PROC_SHOW_LOGOUT                       :{   vProc_Logout();                        break;  }

            case PROC_IDLE                                  :{   vProc_Idle();                          break;  }
            case PROC_EXIT                                  :{   vProc_Exit();                          break;  }
            default                                         :{   vProc_Idle();                          break;  }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////



}
