package com.rtx.treadmill.RtxMainFunctionBike.Performance;

import android.content.Context;

import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_Distance_BaseLayout;


/**
 * Created by chasechang on 3/22/17.
 */

public class DistanceLayout extends Rtx_Distance_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;

    public DistanceLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void init_CustomerView()
    {
        vSetTitleText(R.string.distance);
        if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
        {
            vDefault_init(1f, 200f, 1f, 1f);
        }
        else
        {
            vDefault_init(1f, 200f, 1f, 1f);
        }
    }

    @Override
    protected void add_CustomerView()
    {
        i_Confirm.setImageResource(R.drawable.next_big_button_enable);
    }

    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_ADD_DURATION);
    }

    @Override
    public void vClickButton_Confirm()
    {
        mMainActivity.mMainProcBike.performanceProc.vSet_distance(getCurrentVal_Metric());
        mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_ADD_CALORIES);
    }

}
