package com.rtx.treadmill.RtxMainFunctionBike.Exercise.ChoiceItem;

import com.rtx.treadmill.GlobalData.Choice_UI_Info;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * Created by chasechang on 3/27/17.
 */

public class ChoiceItemProc {
    private String TAG = "Jerry=" ;

    private MainActivity mMainActivity ;

    private ChoiceItemState mState = ChoiceItemState.PROC_INIT ;
    private ChoiceItemState mNextState = ChoiceItemState.PROC_NULL ;

    private ItemChoiceLayout mItemChoice;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    private int icount ;

    public ChoiceItemProc(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity ;
        mState = ChoiceItemState.PROC_INIT ;

        if(mItemChoice == null)                { mItemChoice = new ItemChoiceLayout(mMainActivity.mContext , mMainActivity); }

        icount = 0 ;
    }

    /* ------------------------------------------------------------------------ */
    
    public void vSetNextState(ChoiceItemState nextState)
    {
        mNextState = nextState;
    }

    public void vSetState(ChoiceItemState state)
    {
        mState = state;
    }

    /* ------------------------------------------------------------------------ */
    private void vProc_Init() {

        icount = 0 ;

        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        if(mNextState == ChoiceItemState.PROC_NULL)
        {
            mState = ChoiceItemState.PROC_IDLE;
        }
        else
        {
            mState = mNextState;
        }
    }

    private void vProc_ChoiceItem_Check() {

        if(Choice_UI_Info.i_get_stage() == 0)
        {
            mState = ChoiceItemState.PROC_EXIT ;
        }
    }

    private void vProc_Idle()
    {

       if(Choice_UI_Info.i_get_stage() != 0)
       {
           vChangeDisplayPage(mItemChoice);
           mState = ChoiceItemState.PROC_CHOICE_CHECK ;
       }

    }

    private void vProc_Exit() {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run()
            {
                mMainActivity.removeExerciseViews(ExerciseData.ChoiceItem_layer);
            }
        });

        mState = ChoiceItemState.PROC_INIT ;
        mNextState = ChoiceItemState.PROC_NULL;
    }

    public void vChangeDisplayPage(final Rtx_BaseLayout layout)
    {
        mMainActivity.mUI_Handler.post(new Runnable() {
            @Override
            public void run() {
                layout.init();
                layout.removeAllViews();
                mMainActivity.removeExerciseViews(ExerciseData.ChoiceItem_layer);
                mMainActivity.addExerciseView(ExerciseData.ChoiceItem_layer, layout);
                layout.display();
            }
        });
    }

    /* ------------------------------------------------------------------------ */
    public void run() {

        switch( mState )
        {
            case PROC_INIT                              :{   vProc_Init();                          break;  }
            case PROC_CHOICE_CHECK                      :{   vProc_ChoiceItem_Check();                          break;  }

            case PROC_IDLE                              :{   vProc_Idle();                          break;  }
            case PROC_EXIT                              :{   vProc_Exit();                          break;  }
            default                                     :{   vProc_Idle();                          break;  }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////


}
