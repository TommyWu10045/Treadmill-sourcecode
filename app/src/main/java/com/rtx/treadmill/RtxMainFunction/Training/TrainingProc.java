package com.rtx.treadmill.RtxMainFunction.Training;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * Created by chasechang on 3/27/17.
 */

public class TrainingProc {
    private String TAG = "Jerry=" ;

    private MainActivity mMainActivity ;

    private TrainingState mState = TrainingState.PROC_INIT ;
    private TrainingState mNextState = TrainingState.PROC_NULL ;
    private TrainingState tempState = TrainingState.PROC_NULL ;

    private TrainingLayout  mTrainingLayout             ;
    private WeightLayout    mWeightLayout               ;
    private TimeLayout      mTimeLayout                 ;
    private TargetLayout    mTargetLayout               ;
    private GoLayout        mGoLayout                   ;
    private LevelLayout     mLevelLayout                ;
    private CaloriesLayout  mCaloriesLayout                   ;
    private DistanceLayout  mDistanceLayout                ;

    private TrainingExerciseLayout mExerciseLayout                ;
    private AllFinishLayout  mAllFinishLayout                ;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    public int itype_tread_list[][] = {
            //item name
            { R.drawable.tr_manual          ,         R.string.manual       ,                R.drawable.tr_manual_icon      ,    R.string.tr_manual_info              },
            { R.drawable.tr_hill            ,           R.string.hill       ,                  R.drawable.tr_hill_icon      ,    R.string.tr_hill_info              },
            { R.drawable.tr_fatburn         ,        R.string.fatburn       ,               R.drawable.tr_fatburn_icon      ,    R.string.tr_fatburn_info               },
            { R.drawable.tr_cardio          ,         R.string.cardio       ,                R.drawable.tr_cardio_icon      ,    R.string.tr_cardio_info               },
            { R.drawable.tr_strength        ,       R.string.strenght       ,              R.drawable.tr_strength_icon      ,    R.string.tr_strenght_info               },
            { R.drawable.tr_interval        ,       R.string.interval       ,              R.drawable.tr_interval_icon      ,    R.string.tr_interval_info                   },
    };

    public String[] sinfotreadlist = {
            "tr_manual"       ,       "tr_hill"       ,       "tr_fatburn"       ,       "tr_cardio"       ,       "tr_strength"       ,       "tr_interval"
    };

    public int i_target_start = 0x0a;

//     Treadmill                    Bike
//    0x00 : manual                 manual
//    0x01 : hill                   rolling hill
//    0x02 : fatburn                mountain peak
//    0x03 : cardio                 fatburn
//    0x04 : strength               incline
//    0x05 : interval               strenght
//    0x06 : NA                     interval
//    0x07 : NA                     constant
//    0x0a : calories               calories
//    0x0b : time                   time
//    0x0c : distance               distance
    private int imode ;

//    0 : easy
//    1 : medium
//    2 : hard
    private int ilevel ;
    private float fweight ;
    private float ftarget ;

    public TrainingProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = TrainingState.PROC_INIT ;

        //EngSetting.v_Set_ExerciseType(EngSetting.BIKING);

        if(mTrainingLayout    == null) { mTrainingLayout   =  new TrainingLayout       (mMainActivity.mContext, mMainActivity);}
        if(mWeightLayout      == null) { mWeightLayout     =  new WeightLayout         (mMainActivity.mContext, mMainActivity);}
        if(mTimeLayout        == null) { mTimeLayout       =  new TimeLayout           (mMainActivity.mContext, mMainActivity);}
        if(mTargetLayout      == null) { mTargetLayout     =  new TargetLayout         (mMainActivity.mContext, mMainActivity);}
        if(mGoLayout          == null) { mGoLayout         =  new GoLayout             (mMainActivity.mContext, mMainActivity);}
        if(mLevelLayout       == null) { mLevelLayout      =  new LevelLayout          (mMainActivity.mContext, mMainActivity);}
        if(mCaloriesLayout       == null) { mCaloriesLayout      =  new CaloriesLayout          (mMainActivity.mContext, mMainActivity);}
        if(mDistanceLayout       == null) { mDistanceLayout      =  new DistanceLayout          (mMainActivity.mContext, mMainActivity);}

        if(mExerciseLayout       == null) { mExerciseLayout      =  new TrainingExerciseLayout(mMainActivity.mContext, mMainActivity);}
        if(mAllFinishLayout       == null) { mAllFinishLayout      =  new AllFinishLayout          (mMainActivity.mContext, mMainActivity);}

    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(TrainingState nextState)
    {
        mNextState = nextState;
    }

    public void vSet_mode(int imode)
    {
        this.imode = imode;
    }
    public int i_Get_mode()
    {
        return imode;
    }

    public void vSet_Level(int ilvl)
    {
        this.ilevel = ilvl;
    }

    public void vSet_weight(float fval)
    {
        this.fweight = fval;
    }

    public void vSet_target(float fval)
    {
        this.ftarget = fval;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        if(mNextState == TrainingState.PROC_NULL)
        {
            mState = TrainingState.PROC_SHOW_TRAINING;
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_ShowPage_Training() {

        vChangeDisplayPage(mTrainingLayout);

        mState = TrainingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Weight() {

        vChangeDisplayPage(mWeightLayout);

        mState = TrainingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Time() {

        vChangeDisplayPage(mTimeLayout);

        mState = TrainingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Level() {

        vChangeDisplayPage(mLevelLayout);

        mState = TrainingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_calories() {

        vChangeDisplayPage(mCaloriesLayout);

        mState = TrainingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_distance() {

        vChangeDisplayPage(mDistanceLayout);

        mState = TrainingState.PROC_SHOW_DISTANCE_REFRESH ;
    }

    private void vProc_ShowPage_distance_refresh() {

        if(mDistanceLayout != null)
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run() {
                    mDistanceLayout.v_Slide_bar_refresh();
                }
            });

        }

        vProc_Idle();
    }


    private void vProc_ShowPage_Go() {

        mGoLayout.v_set_data(imode, ilevel, fweight, ftarget);
        vChangeDisplayPage(mGoLayout);

        mState = TrainingState.PROC_IDLE ;
    }

    private void vProc_Check_Is_Countdown() {

        boolean bret = ExerciseData.bGet_Is_Coundown();

        if(bret)
        {
            vSetNextState(TrainingState.PROC_EXERCISE_SHOW);
        }

        vProc_Idle() ;
    }

    private void vProc_ShowPage_Exercise() {

        vChangeDisplayPage(mExerciseLayout);

        mState = TrainingState.PROC_EXERCISE_INIT ;

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

        mState = TrainingState.PROC_EXERCISE_REFRESH ;
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

        mState = TrainingState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Target() {

        vChangeDisplayPage(mTargetLayout);

        mState = TrainingState.PROC_IDLE ;
    }

    private void vProc_Done() {

        vMainChangePage(MainState.PROC_HOME);
        mState = TrainingState.PROC_IDLE ;
    }

    private void vProc_Logout() {
        CloudDataStruct.CloudData_20.set_log_in(false);

        vMainChangePage(MainState.PROC_HOME);
        mState = TrainingState.PROC_IDLE ;
    }

    private void vProc_Idle()
    {
        if(mNextState != TrainingState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = TrainingState.PROC_NULL;
        }
    }

    private void vProc_Exit() {
        mMainActivity.mMainProcTreadmill.vSetIdleState();
        mState = TrainingState.PROC_INIT ;
        mNextState = TrainingState.PROC_NULL;
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
        mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_EXIT);
        mMainActivity.mMainProcTreadmill.vSetNextState(state);
    }

    /* ------------------------------------------------------------------------ */
    public void run() {

        if(tempState != mState)
        {
            //Log.e("Jerry", "[trainingProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                              :{   vProc_Init();                          break;  }
            case PROC_SHOW_TRAINING                     :{   vProc_ShowPage_Training();                break;  }
            case PROC_SHOW_WEIGHT                       :{   vProc_ShowPage_Weight();                break;  }
            case PROC_SHOW_TIME                         :{   vProc_ShowPage_Time();                break;  }
            case PROC_SHOW_LEVEL                        :{   vProc_ShowPage_Level();                break;  }
            case PROC_SHOW_TARGET                       :{   vProc_ShowPage_Target();                break;  }
            case PROC_SHOW_CALORIES                     :{   vProc_ShowPage_calories();                break;  }
            case PROC_SHOW_DISTANCE                     :{   vProc_ShowPage_distance();                break;  }
            case PROC_SHOW_GO                           :{   vProc_ShowPage_Go();                break;  }
            case PROC_SHOW_DISTANCE_REFRESH             :{   vProc_ShowPage_distance_refresh();                break;  }

            case PROC_EXERCISE_SHOW                     :{   vProc_ShowPage_Exercise();                break;  }
            case PROC_EXERCISE_INIT                     :{   vProc_Exercise_Init();                break;  }
            case PROC_EXERCISE_REFRESH                  :{   vProc_Exercise_Refresh();                break;  }
            case PROC_EXERCISE_TR_FINISH                :{   vProc_Exercise_Summary();                break;  }
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
