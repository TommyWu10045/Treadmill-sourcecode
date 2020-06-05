package com.rtx.treadmill.RtxMainFunctionBike.Training;

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

    int imode = 0;

    public TimeLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;
    }

    @Override
    public void add_add_View()
    {
        v_set_title(R.string.time);

        if(imode > 7)
        {
            v_set_default_val(10, 120, 30, 60);
        }
        else {
            v_set_default_val(10, 60, 30, 60);
        }
    }

    @Override
    public void set_next_state()
    {
        int imode = mMainActivity.mMainProcBike.trainingProc.i_Get_mode();

        mMainActivity.mMainProcBike.trainingProc.vSet_target(f_get_input_val());
        if(imode > 0 && imode < 7) {
            mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_LEVEL);
        }
        else if(imode == 7){
            mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_WATT);
        }
        else {
            mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_GO);
        }
    }

    @Override
    public void set_back_state()
    {
        int imode = mMainActivity.mMainProcBike.trainingProc.i_Get_mode();

        if(imode < mMainActivity.mMainProcBike.trainingProc.i_target_start) {
            mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_WEIGHT);
        }
        else
        {
            mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_TARGET);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void v_set_mode(int imode)
    {
        this.imode = imode;
    }

}

