package com.rtx.treadmill.RtxMainFunction.Training;

import android.content.Context;

import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_TimeBaseLayout;


/**
 * Created by chasechang on 3/22/17.
 */

public class TimeLayout extends Rtx_TimeBaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;

    public TimeLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;
    }

    @Override
    public void add_add_View()
    {
        v_set_title(R.string.time);
        v_set_default_val(10, 120, 30, 60);
    }

    @Override
    public void set_next_state()
    {
        mMainActivity.mMainProcTreadmill.trainingProc.vSet_target(f_get_input_val());
        if(mMainActivity.mMainProcTreadmill.trainingProc.i_Get_mode() > 0 && mMainActivity.mMainProcTreadmill.trainingProc.i_Get_mode() < mMainActivity.mMainProcTreadmill.trainingProc.i_target_start) {
            mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_SHOW_LEVEL);
        }
        else {
            mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_SHOW_GO);
        }
    }

    @Override
    public void set_back_state()
    {
        if(mMainActivity.mMainProcTreadmill.trainingProc.i_Get_mode() < mMainActivity.mMainProcTreadmill.trainingProc.i_target_start) {
            mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_SHOW_WEIGHT);
        }
        else
        {
            mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_SHOW_TARGET);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}

