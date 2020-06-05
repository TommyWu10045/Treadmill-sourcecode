package com.rtx.treadmill.RtxMainFunctionBike.Hiit;

import android.util.Log;

import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;


/**
 * Created by chasechang on 3/27/17.
 */

public class HiitProc {
    private String TAG = "Hiit" ;

    private MainActivity mMainActivity ;

    private HiitState mState = HiitState.PROC_INIT ;
    private HiitState mNextState = HiitState.PROC_NULL ;
    private HiitState tempState = HiitState.PROC_NULL ;
    private HiitState mPreState = HiitState.PROC_NULL ;

    //private TargetTrainMainLayout                       mTargetTrainMainLayout;


    private int         iCount = 0;

    public HiitProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = HiitState.PROC_INIT ;

        //Layout
        //if(mTargetTrainMainLayout == null)                          { mTargetTrainMainLayout = new TargetTrainMainLayout(mMainActivity.mContext , mMainActivity); }

    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(HiitState nextState)
    {
        mNextState = nextState;
    }

    public void vSetPreState(HiitState preState)
    {
        mPreState = preState;
    }

    public HiitState vGerPreState()
    {
        return mPreState;
    }

    /* ------------------------------------------------------------------------ */

    private void vProc_Init()
    {
        mState = HiitState.PROC_SHOW_PAGE_MAIN;
    }

    private void vProc_Idle()
    {
        if(mNextState != HiitState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = HiitState.PROC_NULL;
        }
    }

    private void vProc_Exit()
    {
        mMainActivity.mMainProcBike.vSetIdleState();
        mState = HiitState.PROC_INIT ;
        mNextState = HiitState.PROC_NULL;
    }

    private void vProc_ShowPage_Main()
    {
        //vChangeDisplayPage(mTargetTrainMainLayout);

        mState = HiitState.PROC_IDLE ;
    }

    /////////////////////////////////

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
        mMainActivity.mMainProcBike.hiitProc.vSetNextState(HiitState.PROC_EXIT);
        mMainActivity.mMainProcBike.vSetNextState(state);
    }

    public void run() {

        if(tempState != mState)
        {
            Log.e("Jerry", "[hiitProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                                  :{   vProc_Init();                              break;  }

            case PROC_SHOW_PAGE_MAIN                        :{   vProc_ShowPage_Main();                     break;  }

            case PROC_IDLE                                  :{   vProc_Idle();                              break;  }
            case PROC_EXIT                                  :{   vProc_Exit();                              break;  }
            default                                         :{   vProc_Idle();                              break;  }
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////

}
