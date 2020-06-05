package com.rtx.treadmill.RtxMainFunctionBike.Physical;

import android.content.Context;

import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.RtxBaseLayout.Rtx_GenderBaseLayout;

/**
 * Created by chasechang on 3/22/17.
 */

public class GenderLayout extends Rtx_GenderBaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;

    public GenderLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void vClickBack()
    {
        mMainActivity.mMainProcBike.physicalProc.vSetNextState(PhysicalState.PROC_SHOW_AGE);
    }

    @Override
    protected void vClickFemale()
    {
        mMainActivity.mMainProcBike.physicalProc.vSet_Gender(0);
        mMainActivity.mMainProcBike.physicalProc.vSetNextState(PhysicalState.PROC_SHOW_GO);
    }

    @Override
    protected void vClickMale()
    {
        mMainActivity.mMainProcBike.physicalProc.vSet_Gender(1);
        mMainActivity.mMainProcBike.physicalProc.vSetNextState(PhysicalState.PROC_SHOW_GO);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
