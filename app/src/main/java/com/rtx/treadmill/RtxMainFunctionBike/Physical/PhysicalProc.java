package com.rtx.treadmill.RtxMainFunctionBike.Physical;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * Created by chasechang on 3/27/17.
 */

public class PhysicalProc {
    private String TAG = "Jerry=" ;

    private MainActivity mMainActivity ;

    private PhysicalState mState = PhysicalState.PROC_INIT ;
    private PhysicalState mNextState = PhysicalState.PROC_NULL ;
    private PhysicalState tempState = PhysicalState.PROC_NULL ;

    private PhysicalLayout              mPhysicalLayout              ;
    private WeightLayout                mWeightLayout               ;
    private AgeLayout                   mAgeLayout                  ;
    private GenderLayout                mGenderLayout               ;
    private GoLayout                    mGoLayout                   ;

    private GerkinLayout                mGerkinLayout                   ;

    private GerkinFinishLayout                mGerkinFinishLayout                   ;


    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    public int itype_tread_list[][] = {
            //item name
            { R.drawable.phy_vo2          ,         R.string.vo2       ,                R.drawable.phy_vo2_icon      ,    R.string.phy_vo2_info              },
    };

    public String[] sinfotreadlist = {
            "tr_vo2"
    };

    //     Treadmill                    Bike
//    0x01 : gerkin                     vo2
//    0x02 : cooper                     NA
//    0x03 : usmc                       NA
//    0x04 : army                       NA
//    0x05 : navy                       NA
//    0x06 : usaf                       NA
//    0x07 : federral                   NA

    private int imode ;

    private float fweight ;
    private int igender ;
    private float fage ;

    public PhysicalProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = PhysicalState.PROC_INIT ;

//        EngSetting.v_Set_ExerciseType(EngSetting.BIKING);

        if(mPhysicalLayout          == null) { mPhysicalLayout           =  new PhysicalLayout       (mMainActivity.mContext, mMainActivity);}
        if(mWeightLayout            == null) { mWeightLayout             =  new WeightLayout         (mMainActivity.mContext, mMainActivity);}
        if(mAgeLayout               == null) { mAgeLayout                =  new AgeLayout            (mMainActivity.mContext, mMainActivity);}
        if(mGenderLayout            == null) { mGenderLayout             =  new GenderLayout         (mMainActivity.mContext, mMainActivity);}
        if(mGoLayout                == null) { mGoLayout                 =  new GoLayout             (mMainActivity.mContext, mMainActivity);}

        if(mGerkinLayout            == null) { mGerkinLayout             =  new GerkinLayout         (mMainActivity.mContext, mMainActivity);}

        if(mGerkinFinishLayout            == null) { mGerkinFinishLayout             =  new GerkinFinishLayout         (mMainActivity.mContext, mMainActivity);}

    }


    /* ------------------------------------------------------------------------ */

    public void vSetNextState(PhysicalState nextState)
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

    public void vSet_weight(float fval)
    {
        this.fweight = fval;
    }

    public void vSet_Gender(int igender)
    {
        this.igender = igender;
    }

    public void vSet_Age(float fage)
    {
        this.fage = fage;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        if(mNextState == PhysicalState.PROC_NULL)
        {
            mState = PhysicalState.PROC_SHOW_PHYSICAL;
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_ShowPage_Physical() {

        vChangeDisplayPage(mPhysicalLayout);

        mState = PhysicalState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Weight() {

        vChangeDisplayPage(mWeightLayout);

        mState = PhysicalState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Gender() {

        vChangeDisplayPage(mGenderLayout);

        mState = PhysicalState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Age() {

        vChangeDisplayPage(mAgeLayout);

        mState = PhysicalState.PROC_IDLE ;
    }

    private void vProc_ShowPage_Go() {

        mGoLayout.v_set_data(imode, fweight, igender, fage);
        vChangeDisplayPage(mGoLayout);

        mState = PhysicalState.PROC_IDLE ;
    }

    private void vProc_Check_Is_Countdown() {

        boolean bret = ExerciseData.bGet_Is_Coundown();

        if(bret)
        {
            vSetNextState(PhysicalState.PROC_SHOW_GERKIN);
        }

        vProc_Idle() ;
    }

    private void vProc_ShowPage_GERKIN() {

        vChangeDisplayPage(mGerkinLayout);

        mState = PhysicalState.PROC_SHOW_GERKIN_REFRESH ;
    }

    private void vProc_GERKIN_REFRESH() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(mGerkinLayout != null)
                {
                    mGerkinLayout.Refresh();
                }
            }
        });

        vProc_Idle();
    }

    private void vProc_ShowPage_GERKIN_Finish() {

        vChangeDisplayPage(mGerkinFinishLayout);

        mState = PhysicalState.PROC_IDLE ;
    }

    private void vProc_Done() {

        vMainChangePage(MainState.PROC_HOME);
        mState = PhysicalState.PROC_IDLE ;
    }

    private void vProc_Logout() {
        CloudDataStruct.CloudData_20.set_log_in(false);

        vMainChangePage(MainState.PROC_HOME);
        mState = PhysicalState.PROC_IDLE ;
    }

    private void vProc_Idle()
    {
        if(mNextState != PhysicalState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = PhysicalState.PROC_NULL;
        }
    }

    private void vProc_Exit() {
        mMainActivity.mMainProcBike.vSetIdleState();
        mState = PhysicalState.PROC_INIT ;
        mNextState = PhysicalState.PROC_NULL;
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
        mMainActivity.mMainProcBike.physicalProc.vSetNextState(PhysicalState.PROC_EXIT);
        mMainActivity.mMainProcBike.vSetNextState(state);
    }

    /* ------------------------------------------------------------------------ */
    public void run() {

        if(tempState != mState)
        {
//            Log.e("Jerry", "[physicalProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                              :{   vProc_Init();                          break;  }
            case PROC_SHOW_PHYSICAL                     :{   vProc_ShowPage_Physical();             break;  }
            case PROC_SHOW_WEIGHT                       :{   vProc_ShowPage_Weight();               break;  }
            case PROC_SHOW_AGE                          :{   vProc_ShowPage_Age();                  break;  }
            case PROC_SHOW_GENDER                       :{   vProc_ShowPage_Gender();               break;  }
            case PROC_SHOW_GO                           :{   vProc_ShowPage_Go();                   break;  }
            case PROC_SHOW_GERKIN                       :{   vProc_ShowPage_GERKIN();               break;  }
            case PROC_EXERCISE_CHECK_COUNTDOWN          :{   vProc_Check_Is_Countdown();              break;  }

            case PROC_SHOW_GERKIN_REFRESH               :{   vProc_GERKIN_REFRESH();                break;  }

            case PROC_SHOW_GERKIN_FINISH                :{   vProc_ShowPage_GERKIN_Finish();               break;  }

            case PROC_SHOW_DONE                         :{   vProc_Done();                          break;  }
            case PROC_SHOW_LOGOUT                       :{   vProc_Logout();                        break;  }

            case PROC_IDLE                                  :{   vProc_Idle();                          break;  }
            case PROC_EXIT                                  :{   vProc_Exit();                          break;  }
            default                                         :{   vProc_Idle();                          break;  }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////


}
