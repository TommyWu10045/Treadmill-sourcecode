package com.rtx.treadmill.RtxMainFunction.TargetTrain;

import android.content.Context;

import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_NumberWheel_BodyFat_BaseLayout;



/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainInput_CurrentBodyFat_Layout extends Rtx_NumberWheel_BodyFat_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;

    public TargetTrainInput_CurrentBodyFat_Layout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void init_CustomerView()
    {
        vSetTitleText(R.string.current_body_fat);
    }

    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_DETAIL_BODYFAT);
    }

    @Override
    public void vClickButton_Confirm()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_CLOUD_UPLOAD_BodyFat);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}

