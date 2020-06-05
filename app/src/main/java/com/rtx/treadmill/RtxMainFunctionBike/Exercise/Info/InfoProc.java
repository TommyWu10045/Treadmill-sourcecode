package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Info;

import com.rtx.treadmill.GlobalData.Change_UI_Info;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * Created by chasechang on 3/27/17.
 */

public class InfoProc {
    private String TAG = "Jerry=" ;

    private MainActivity mMainActivity ;

    private InfoState mState = InfoState.PROC_INIT ;
    private InfoState mNextState = InfoState.PROC_NULL ;

    private ChangeValLayout mChangeValLayout;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    private int icount ;

    public InfoProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = InfoState.PROC_INIT ;

        if(mChangeValLayout == null)                { mChangeValLayout = new ChangeValLayout(mMainActivity.mContext , mMainActivity); }

    }

    /* ------------------------------------------------------------------------ */

    public void vSetNextState(InfoState nextState)
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

        if(mNextState == InfoState.PROC_NULL)
        {
            mState = InfoState.PROC_IDLE;
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_INFO_FLASH() {
        if(mChangeValLayout != null)
        {
            mMainActivity.mUI_Handler.post(new Runnable() {
                @Override
                public void run()
                {
                    mChangeValLayout.Refresh(icount);
                }
            });
        }

        int ival = Change_UI_Info.i_get_stage();

        if((ival & Change_UI_Info.istate_change_mask) == 0)
        {
            mState = InfoState.PROC_EXIT ;
        }

        icount++ ;
    }

    private void vProc_Idle()
    {
        int ival = Change_UI_Info.i_get_stage();

        if((ival & Change_UI_Info.istate_change_mask) != 0)
        {
            vChangeDisplayPage(mChangeValLayout);
            mState = InfoState.PROC_INFO_FLASH ;
            icount = 0 ;
        }

    }

    private void vProc_Exit() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.removeExerciseViews(ExerciseData.ChnageInfo_layer);
            }
        });

        mState = InfoState.PROC_INIT ;
        mNextState = InfoState.PROC_NULL;
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeExerciseViews(ExerciseData.ChnageInfo_layer);
                mMainActivity.addExerciseView(ExerciseData.ChnageInfo_layer, layout);
                layout.setClickable(false);
                layout.display();
            }
        });
    }

    /* ------------------------------------------------------------------------ */
    public void run() {
        switch( mState )
        {
            case PROC_INIT                             :{   vProc_Init();                          break;  }
            case PROC_INFO_FLASH                       :{   vProc_INFO_FLASH();                          break;  }

            case PROC_IDLE                              :{   vProc_Idle();                          break;  }
            case PROC_EXIT                              :{   vProc_Exit();                          break;  }
            default                                     :{   vProc_Idle();                          break;  }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////


}
