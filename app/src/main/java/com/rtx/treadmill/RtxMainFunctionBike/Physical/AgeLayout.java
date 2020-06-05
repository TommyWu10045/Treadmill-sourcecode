package com.rtx.treadmill.RtxMainFunctionBike.Physical;

import android.content.Context;

import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_AgeBaseLayout;


/**
 * Created by chasechang on 3/22/17.
 */

public class AgeLayout extends Rtx_AgeBaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;

    public AgeLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;
    }

    @Override
    public void set_next_state()
    {
        mMainActivity.mMainProcBike.physicalProc.vSet_Age(f_get_input_val());
        mMainActivity.mMainProcBike.physicalProc.vSetNextState(PhysicalState.PROC_SHOW_GENDER);
    }

    @Override
    public void set_back_state()
    {
        mMainActivity.mMainProcBike.physicalProc.vSetNextState(PhysicalState.PROC_SHOW_WEIGHT);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}

