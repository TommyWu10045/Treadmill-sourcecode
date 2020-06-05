package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Error;

import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.ExerciseState;

/**
 * Created by chasechang on 3/27/17.
 */

public class ErrorProc {
    private String TAG = "Jerry=" ;

    private MainActivity mMainActivity ;

    private ErrorState mState = ErrorState.PROC_INIT ;
    private ErrorState mNextState = ErrorState.PROC_NULL ;
    private ErrorState tempState = ErrorState.PROC_NULL ;

    private ErrorLayout mErrorLayout;
    private ErrorNULLLayout mErrorNULLLayout;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    private int icount ;

    public ErrorProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = ErrorState.PROC_INIT ;

        if(mErrorLayout == null)                { mErrorLayout = new ErrorLayout(mMainActivity.mContext , mMainActivity); }
        if(mErrorNULLLayout == null)            { mErrorNULLLayout = new ErrorNULLLayout(mMainActivity.mContext , mMainActivity); }

        icount = 0 ;
    }


    /* ------------------------------------------------------------------------ */

    public void vSetNextState(ErrorState nextState)
    {
        mNextState = nextState;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {

        icount = 0 ;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        if(mNextState == ErrorState.PROC_NULL)
        {
            mState = ErrorState.PROC_ERROR_CLOUD02;
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_ShowPage_SHOW() {

        vChangeDisplayPage(mErrorLayout);

        mState = ErrorState.PROC_IDLE ;
    }

    private void vProc_ShowPage_NULL() {

        vChangeDisplayPage(mErrorNULLLayout);

        mState = ErrorState.PROC_IDLE ;
    }

    private void vProc_Idle()
    {
        if(mNextState != ErrorState.PROC_NULL)
        {
            mState = mNextState;
            mNextState = ErrorState.PROC_NULL;
        }

        if(icount % EngSetting.DEF_HSEC_COUNT == 0)
        {
            switch ((icount / EngSetting.DEF_HSEC_COUNT) % 2)
            {
                case 1 :
                    mState = ErrorState.PROC_ERROR_NULL ;
                    break;
                default:
                    mState = ErrorState.PROC_ERROR_SHOW ;
                    break;
            }

        }

        icount++;

    }

    private void vProc_Cloud_02()
    {
        bServerResponseFlag = false;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.showProgressBar();
                new Thread(CloudRunnable_02).start();
            }
        });

        mState = ErrorState.PROC_ERROR_CLOUD02_CHECK;
    }

    private void vProc_Cloud_02_Check()
    {
        if(bServerResponseFlag)
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mMainActivity.closeProgressBar();
                }
            });
            mState = ErrorState.PROC_ERROR_SHOW;
        }
    }

    private void vProc_Exit() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.removeExerciseViews(ExerciseData.Error_layer);
            }
        });

        mMainActivity.mMainProcBike.exerciseProc.vSetIdleState();
        mMainActivity.mMainProcBike.exerciseProc.vSetNextState(ExerciseState.PROC_EXIT);

        mState = ErrorState.PROC_INIT ;
        mNextState = ErrorState.PROC_NULL;
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeExerciseViews(ExerciseData.Error_layer);
                mMainActivity.addExerciseView(ExerciseData.Error_layer, layout);
                layout.display();
            }
        });
    }

    /* ------------------------------------------------------------------------ */
    public void run() {
        if(tempState != mState)
        {
            //Log.e("Jerry", "[ErrorProc] mState = " + mState);
            tempState = mState;
        }

        switch( mState )
        {
            case PROC_INIT                              :{   vProc_Init();                          break;  }
            case PROC_ERROR_SHOW                        :{   vProc_ShowPage_SHOW();                          break;  }
            case PROC_ERROR_NULL                        :{   vProc_ShowPage_NULL();          break;  }

            case PROC_ERROR_CLOUD02                      :{   vProc_Cloud_02();                       break;  }
            case PROC_ERROR_CLOUD02_CHECK                :{   vProc_Cloud_02_Check();                 break;  }

            case PROC_IDLE                              :{   vProc_Idle();                          break;  }
            case PROC_EXIT                              :{   vProc_Exit();                          break;  }
            default                                     :{   vProc_Idle();                          break;  }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Runnable CloudRunnable_02 = new Runnable() {
        @Override
        public void run()
        {
            iServerResponse = -1;

            iServerResponse = mMainActivity.mCloudCmd.iCloudCmd_SetDeviceStatus();

            bServerResponseFlag = true;
        }
    };

}
