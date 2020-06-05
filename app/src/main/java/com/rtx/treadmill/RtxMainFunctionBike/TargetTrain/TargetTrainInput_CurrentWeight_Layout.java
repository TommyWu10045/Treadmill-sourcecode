package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;

import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_NumberWheel_Weight_BaseLayout;



/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainInput_CurrentWeight_Layout extends Rtx_NumberWheel_Weight_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;

    public TargetTrainInput_CurrentWeight_Layout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void init_CustomerView()
    {
        vSetTitleText(R.string.current_weight);
    }

    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_DETAIL_WEIGHT);
    }

    @Override
    public void vClickButton_Confirm()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_CLOUD_UPLOAD_WEIGHT);
    }
}

