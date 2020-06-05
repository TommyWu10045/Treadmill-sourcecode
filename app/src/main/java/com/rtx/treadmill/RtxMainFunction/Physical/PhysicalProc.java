package com.rtx.treadmill.RtxMainFunction.Physical;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.ExerciseRunState;
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
    private CooperLayout                mCooperLayout                   ;
    private UsmcLayout                  mUsmcLayout                   ;

    private GerkinFinishLayout                mGerkinFinishLayout                   ;
    private CooperFinishLayout                mCooperFinishLayout                   ;
    private UsmcFinishLayout                mUsmcFinishLayout                   ;



    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    public int itype_tread_list[][] = {
            //item name
            { R.drawable.phy_gerkin,       R.string.gerkin_protocol,       R.drawable.phy_gerkin_icon,   R.string.phy_gerkin_protocol_info              },
            { R.drawable.phy_cooper,       R.string.cooper_test,           R.drawable.phy_cooper_icon,   R.string.phy_cooper_test_info              },
            { R.drawable.phy_usmc,         R.string.usmc_ptf,              R.drawable.phy_usmc_icon,     R.string.phy_usmc_ptf_info               },
            { R.drawable.phy_army,         R.string.army_prt,              R.drawable.phy_army_icon,     R.string.phy_army_prt_info               },
            { R.drawable.phy_navy,         R.string.navy_prt,              R.drawable.phy_navy_icon,     R.string.phy_navy_prt_info               },
            { R.drawable.phy_usaf,         R.string.usaf_ptf,              R.drawable.phy_usaf_icon,     R.string.phy_usaf_ptf_info                   },
            { R.drawable.phy_federal,      R.string.federal_law,           R.drawable.phy_federal_icon,  R.string.phy_federal_law_info                 }
    };

    public String[] sinfotreadlist = {
            "phy_gerkin"       ,       "phy_cooper"       ,       "phy_usmc"       ,       "phy_army"       ,       "phy_navy"       ,       "phy_usaf"       ,       "phy_federal"
    };

    public int itype_bike_list[][] = {
            //item name
            { R.drawable.phy_vo2          ,         R.string.vo2       ,                R.drawable.phy_vo2_icon      ,    R.string.phy_vo2_info              },
    };

    public String[] sinfobikelist = {
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
        if(mCooperLayout            == null) { mCooperLayout             =  new CooperLayout         (mMainActivity.mContext, mMainActivity);}
        if(mUsmcLayout              == null) { mUsmcLayout               =  new UsmcLayout           (mMainActivity.mContext, mMainActivity);}

        if(mGerkinFinishLayout            == null) { mGerkinFinishLayout             =  new GerkinFinishLayout         (mMainActivity.mContext, mMainActivity);}
        if(mCooperFinishLayout            == null) { mCooperFinishLayout             =  new CooperFinishLayout         (mMainActivity.mContext, mMainActivity);}
        if(mUsmcFinishLayout            == null) { mUsmcFinishLayout             =  new UsmcFinishLayout         (mMainActivity.mContext, mMainActivity);}

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
            switch (imode)
            {
                case 0: //gerkin mode
                    vSetNextState(PhysicalState.PROC_SHOW_GERKIN);
                    break;
                case 1: //cooper mode
                    vSetNextState(PhysicalState.PROC_SHOW_COOPER);
                    break;
                case 2: //usmc mode
                    vSetNextState(PhysicalState.PROC_SHOW_USMC);
                    break;
                case 3: //army mode
                    vSetNextState(PhysicalState.PROC_SHOW_ARMY);
                    break;
                case 4: //navy mode
                    vSetNextState(PhysicalState.PROC_SHOW_NAVY);
                    break;
                case 5: //usaf mode
                    vSetNextState(PhysicalState.PROC_SHOW_USAF);
                    break;
                case 6: //federal mode
                    vSetNextState(PhysicalState.PROC_SHOW_FEDERAL);
                    break;
                default:
                    break;
            }
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

    private void vProc_ShowPage_COOPER() {

        vChangeDisplayPage(mCooperLayout);

        mState = PhysicalState.PROC_SHOW_COOPER_REFRESH ;
    }

    private void vProc_COOPER_REFRESH() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(mCooperLayout != null)
                {
                    mCooperLayout.Refresh();
                }
            }
        });

        vProc_Idle();
    }

    private void vProc_ShowPage_USMC() {

        vChangeDisplayPage(mUsmcLayout);

        mState = PhysicalState.PROC_SHOW_USMC_REFRESH ;
    }

    private void vProc_USMC_REFRESH() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(mUsmcLayout != null)
                {
                    mUsmcLayout.Refresh();
                }
            }
        });

        vProc_Idle();
    }

    private void vProc_ShowPage_GERKIN_Finish() {

        vChangeDisplayPage(mGerkinFinishLayout);

        mState = PhysicalState.PROC_IDLE ;
    }

    private void vProc_ShowPage_COOPER_Finish() {

        vChangeDisplayPage(mCooperFinishLayout);

        mState = PhysicalState.PROC_IDLE ;
    }

    private void vProc_ShowPage_USMC_Finish() {

        vChangeDisplayPage(mUsmcFinishLayout);

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
        mMainActivity.mMainProcTreadmill.vSetIdleState();
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
        mMainActivity.mMainProcTreadmill.physicalProc.vSetNextState(PhysicalState.PROC_EXIT);
        mMainActivity.mMainProcTreadmill.vSetNextState(state);
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
            case PROC_SHOW_COOPER                       :{   vProc_ShowPage_COOPER();               break;  }
            case PROC_SHOW_USMC                         :{   vProc_ShowPage_USMC();                   break;  }
            case PROC_SHOW_ARMY                         :{   vProc_ShowPage_USMC();                   break;  }
            case PROC_SHOW_NAVY                         :{   vProc_ShowPage_USMC();                   break;  }
            case PROC_SHOW_USAF                         :{   vProc_ShowPage_USMC();                   break;  }
            case PROC_SHOW_FEDERAL                      :{   vProc_ShowPage_USMC();                   break;  }
            case PROC_EXERCISE_CHECK_COUNTDOWN          :{   vProc_Check_Is_Countdown();              break;  }

            case PROC_SHOW_GERKIN_REFRESH               :{   vProc_GERKIN_REFRESH();                break;  }
            case PROC_SHOW_COOPER_REFRESH               :{   vProc_COOPER_REFRESH();                  break;  }
            case PROC_SHOW_USMC_REFRESH                 :{   vProc_USMC_REFRESH();                   break;  }
            case PROC_SHOW_ARMY_REFRESH                 :{   vProc_USMC_REFRESH();                   break;  }
            case PROC_SHOW_NAVY_REFRESH                 :{   vProc_USMC_REFRESH();                   break;  }
            case PROC_SHOW_USAF_REFRESH                 :{   vProc_USMC_REFRESH();                   break;  }
            case PROC_SHOW_FEDERAL_REFRESH              :{   vProc_USMC_REFRESH();                   break;  }

            case PROC_SHOW_GERKIN_FINISH                :{   vProc_ShowPage_GERKIN_Finish();               break;  }
            case PROC_SHOW_COOPER_FINISH                :{   vProc_ShowPage_COOPER_Finish();               break;  }
            case PROC_SHOW_USMC_FINISH                  :{   vProc_ShowPage_USMC_Finish();               break;  }
            case PROC_SHOW_ARMY_FINISH                  :{   vProc_ShowPage_USMC_Finish();               break;  }
            case PROC_SHOW_NAVY_FINISH                  :{   vProc_ShowPage_USMC_Finish();               break;  }
            case PROC_SHOW_USAF_FINISH                  :{   vProc_ShowPage_USMC_Finish();               break;  }
            case PROC_SHOW_FEDERAL_FINISH               :{   vProc_ShowPage_USMC_Finish();               break;  }

            case PROC_SHOW_DONE                         :{   vProc_Done();                          break;  }
            case PROC_SHOW_LOGOUT                       :{   vProc_Logout();                        break;  }

            case PROC_IDLE                                  :{   vProc_Idle();                          break;  }
            case PROC_EXIT                                  :{   vProc_Exit();                          break;  }
            default                                         :{   vProc_Idle();                          break;  }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////


}
