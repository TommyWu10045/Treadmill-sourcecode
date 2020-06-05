package com.rtx.treadmill.RtxMainFunctionBike.Training;

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
            vDefault_init(1f, 50f, 5f, 1f);
        }
        else
        {
            vDefault_init(1f, 30f, 5f, 0.1f);
        }
    }

    @Override
    protected void add_CustomerView()
    {
        //Let user can override this.
        i_Confirm.setImageResource(R.drawable.next_big_button_enable);
    }


    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_TARGET);
    }

    @Override
    public void vClickButton_Confirm()
    {
        mMainActivity.mMainProcBike.trainingProc.vSet_target(getCurrentVal_Metric());
        mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_GO);
    }

}
