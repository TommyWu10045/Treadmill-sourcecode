package com.rtx.treadmill.RtxMainFunction.HeartRate;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * Created by chasechang on 3/27/17.
 */

public class HeartRateProc {
    private String TAG = "Jerry" ;

    private MainActivity mMainActivity ;

    private HeartRateState mState = HeartRateState.PROC_INIT ;
    private HeartRateState mNextState = HeartRateState.PROC_NULL ;
    private HeartRateState tempState = HeartRateState.PROC_NULL ;

    private MainLayout            mMainLayout           ;
    private WeightLayout          mWeightLayout         ;
    private AgeLayout             mAgeLayout            ;
    private TargetLayout          mTargetLayout         ;
    private TimeLayout            mTimeLayout           ;
    private StartLayout           mStartLayout          ;

    private HeartRateExerciseLayout mExerciseLayout                ;
    private AllFinishLayout mAllFinishLayout                ;

    public int imode ;
    public float fweight  ;
    public float ftarget   ;
    public float ftime    ;
    public float fage     ;

    public int itype_list[][] = {
            //item name
            { R.drawable.hrc_fatburn,       R.string.fatburn,       R.drawable.hrc_fatburn,     R.string.hrc_info_fatburn                },
            { R.drawable.hrc_aerobic,       R.string.aerobic,       R.drawable.hrc_aerobic,     R.string.hrc_info_aerobic                },
            { R.drawable.hrc_performance,   R.string.performance,   R.drawable.hrc_performance,     R.string.hrc_info_performance                },
            { R.drawable.hrc_customize,     R.string.customize,     R.drawable.hrc_customize,     R.string.hrc_info_customize                }
    };

    public String[] sinfolist = {
            "hrc_fatburn"       ,       "hrc_aerobic"       ,       "hrc_performance"       ,       "hrc_customize"
    };

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    public HeartRateProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = HeartRateState.PROC_INIT ;

        if(mMainLayout    ==  null)           { mMainLayout    =  new MainLayout    (mMainActivity.mContext , mMainActivity); }
        if(mWeightLayout  ==  null)           { mWeightLayout  =  new WeightLayout  (mMainActivity.mContext , mMainActivity); }
        if(mAgeLayout     ==  null)           { mAgeLayout     =  new AgeLayout     (mMainActivity.mContext , mMainActivity); }
        if(mTargetLayout  ==  null)           { mTargetLayout  =  new TargetLayout  (mMainActivity.mContext , mMainActivity); }
        if(mTimeLayout    ==  null)           { mTimeLayout    =  new TimeLayout    (mMainActivity.mContext , mMainActivity); }
        if(mStartLayout   ==  null)           { mStartLayout   =  new StartLayout   (mMainActivity.mContext , mMainActivity); }

        if(mExerciseLayout       == null) { mExerciseLayout      =  new HeartRateExerciseLayout(mMainActivity.mContext, mMainActivity);}
        if(mAllFinishLayout       == null) { mAllFinishLayout      =  new AllFinishLayout(mMainActivity.mContext, mMainActivity);}
    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(HeartRateState nextState)
    {
        mNextState = nextState;
    }

    public void vSet_Select(int imode)
    {
        this.imode = imode;
    }
    public int i_Get_mode()
    {
        return imode;
    }
    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {
        fweight  = 0 ;
        ftarget   = 0 ;
        ftime    = 0 ;
        fage     = 0 ;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        if(mNextState == HeartRateState.PROC_NULL)
        {
            mState = HeartRateState.PROC_SHOW_MAIN;
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_ShowPage_Main() {

        vChangeDisplayPage(mMainLayout);
        mState = HeartRateState.PROC_IDLE ;
    }


    private void vProc_ShowPage_Weight() {

        vChangeDisplayPage(mWeightLayout);

        mState = HeartRateState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Age() {

        vChangeDisplayPage(mAgeLayout);

        mState = HeartRateState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Target() {

        mTargetLayout.v_set_age(fage);
        vChangeDisplayPage(mTargetLayout);

        mState = HeartRateState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Time() {

        vChangeDisplayPage(mTimeLayout);

        mState = HeartRateState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Start() {

        mStartLayout.v_set_data(fweight, ftarget, ftime, fage);
        vChangeDisplayPage(mStartLayout);

        mState = HeartRateState.PROC_IDLE ;
    }

    private void vProc_Check_Is_Countdown() {

        boolean bret = ExerciseData.bGet_Is_Coundown();

        if(bret)
        {
            vSetNextState(HeartRateState.PROC_EXERCISE_SHOW);
        }

        vProc_Idle() ;
    }

    private void vProc_ShowPage_Exercise() {

        vChangeDisplayPage(mExerciseLayout);

        mState = HeartRateState.PROC_EXERCISE_INIT ;

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

        mState = HeartRateState.PROC_EXERCISE_REFRESH ;
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

        mAllFinishLayout.vSet_mode(imode);
        vChangeDisplayPage(mAllFinishLayout);

        mState = HeartRateState.PROC_IDLE ;
    }

    private void vProc_Done() {

        vMainChangePage(MainState.PROC_HOME);
        mState = HeartRateState.PROC_IDLE ;
    }

    private void vProc_Logout() {
        CloudDataStruct.CloudData_20.set_log_in(false);

        vMainChangePage(MainState.PROC_HOME);
        mState = HeartRateState.PROC_IDLE ;
    }

    private void vProc_Idle()
    {
        if(mNextState != HeartRateState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = HeartRateState.PROC_NULL;
        }
    }

    private void vProc_Exit() {
        mMainActivity.mMainProcTreadmill.vSetIdleState();
        mState = HeartRateState.PROC_INIT ;
        mNextState = HeartRateState.PROC_NULL;
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
        mMainActivity.mMainProcTreadmill.heartrateProc.vSetNextState(HeartRateState.PROC_EXIT);
        mMainActivity.mMainProcTreadmill.vSetNextState(state);
    }

    /* ------------------------------------------------------------------------ */
    public void run() {

        if(tempState != mState)
        {
//            Log.e("Jerry", "[heartrateProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                                    :{   vProc_Init();                          break;  }
            case PROC_SHOW_MAIN                               :{   vProc_ShowPage_Main();              break;  }
            case PROC_SHOW_WEIGHT                             :{   vProc_ShowPage_Weight();               break;  }
            case PROC_SHOW_AGE                                :{   vProc_ShowPage_Age();               break;  }
            case PROC_SHOW_TARGET                             :{   vProc_ShowPage_Target();              break;  }
            case PROC_SHOW_TIME                               :{   vProc_ShowPage_Time();                break;  }
            case PROC_SHOW_START                              :{   vProc_ShowPage_Start();                  break;  }

            case PROC_EXERCISE_SHOW                           :{   vProc_ShowPage_Exercise();                break;  }
            case PROC_EXERCISE_INIT                           :{   vProc_Exercise_Init();                break;  }
            case PROC_EXERCISE_REFRESH                        :{   vProc_Exercise_Refresh();                break;  }
            case PROC_EXERCISE_HR_FINISH                      :{   vProc_Exercise_Summary();                break;  }
            case PROC_EXERCISE_CHECK_COUNTDOWN                :{   vProc_Check_Is_Countdown();              break;  }

            case PROC_SHOW_DONE                               :{   vProc_Done();                          break;  }
            case PROC_SHOW_LOGOUT                             :{   vProc_Logout();                        break;  }

            case PROC_IDLE                                    :{   vProc_Idle();                          break;  }
            case PROC_EXIT                                    :{   vProc_Exit();                          break;  }
            default                                           :{   vProc_Idle();                          break;  }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////


}
