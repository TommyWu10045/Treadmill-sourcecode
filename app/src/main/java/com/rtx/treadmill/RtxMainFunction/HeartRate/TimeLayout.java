package com.rtx.treadmill.RtxMainFunction.HeartRate;

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
        mMainActivity.mMainProcTreadmill.heartrateProc.ftime = f_get_input_val();
        mMainActivity.mMainProcTreadmill.heartrateProc.vSetNextState(HeartRateState.PROC_SHOW_TARGET);
    }

    @Override
    public void set_back_state()
    {
        mMainActivity.mMainProcTreadmill.heartrateProc.vSetNextState(HeartRateState.PROC_SHOW_AGE);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}

