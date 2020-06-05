package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Bottominfo;

import com.rtx.treadmill.GlobalData.Bottom_UI_Info;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * Created by chasechang on 3/27/17.
 */

public class BottomInfoProc {
    private String TAG = "Jerry=" ;

    private MainActivity mMainActivity ;

    private BottomInfoState mState = BottomInfoState.PROC_INIT ;
    private BottomInfoState mNextState = BottomInfoState.PROC_NULL ;

    private BottomLayout mBottomLayout;

    private int icount ;

    public BottomInfoProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = BottomInfoState.PROC_INIT ;

        if(mBottomLayout == null)                { mBottomLayout = new BottomLayout(mMainActivity.mContext , mMainActivity); }

        icount = 0 ;
    }

    /* ------------------------------------------------------------------------ */
    
    public void vSetNextState(BottomInfoState nextState)
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

        if(mNextState == BottomInfoState.PROC_NULL)
        {
            mState = BottomInfoState.PROC_IDLE;
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_Normal()
    {
        icount++ ;
        if(mBottomLayout != null)
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mBottomLayout.Refresh();
                }
            });
        }

        vProc_Check_Exercise_State();

    }

    private void vProc_Idle()
    {
       if(Bottom_UI_Info.i_get_stage() != 0)
       {
           vChangeDisplayPage(mBottomLayout);
           mState = BottomInfoState.PROC_BOTTOM ;
       }

    }

    private void vProc_Null()
    {

    }

    private void vProc_Check_Exercise_State()
    {
        if(Bottom_UI_Info.i_get_stage() == 0)
        {
            mState = BottomInfoState.PROC_EXIT ;
        }
    }

    private void vProc_Exit() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.removeExerciseViews(ExerciseData.BottomInfo_layer);
            }
        });

        mState = BottomInfoState.PROC_INIT ;
        mNextState = BottomInfoState.PROC_NULL;
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeExerciseViews(ExerciseData.BottomInfo_layer);
                mMainActivity.addExerciseView(ExerciseData.BottomInfo_layer, layout);
                layout.display();
            }
        });
    }

    /* ------------------------------------------------------------------------ */
    public void run() {
        switch( mState )
        {
            case PROC_INIT                             :{   vProc_Init();                          break;  }
            case PROC_BOTTOM                           :{   vProc_Normal();                          break;  }

            case PROC_IDLE                              :{   vProc_Idle();                          break;  }
            case PROC_EXIT                              :{   vProc_Exit();                          break;  }
            default                                     :{   vProc_Null();                          break;  }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////


}
