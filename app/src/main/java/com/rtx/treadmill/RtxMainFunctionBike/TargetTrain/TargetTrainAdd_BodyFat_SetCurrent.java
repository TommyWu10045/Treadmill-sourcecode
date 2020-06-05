package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;
import android.util.Log;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_NumberWheel_BodyFat_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainAdd_BodyFat_SetCurrent extends Rtx_NumberWheel_BodyFat_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;

    CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal;

    public TargetTrainAdd_BodyFat_SetCurrent(Context context, MainActivity mMainActivity) {
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
    protected void add_CustomerView()
    {
        imageView_Confirm.setImageResource(R.drawable.next_big_button_enable);
    }

    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_CHOOSE_TARGET);
    }

    @Override
    public void vClickButton_Confirm()
    {
        {
            cloud_TargetGoal.sGol_Item = "Target Body Fat";
            cloud_TargetGoal.sUsr_Wei = Rtx_TranslateValue.sFloat2String(getCurrentVal(), 5);
        }

        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_ADD_BDY_FAT_SET_TARGET);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetCloudTargetGoal(CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal)
    {
        this.cloud_TargetGoal = cloud_TargetGoal;
    }
}

