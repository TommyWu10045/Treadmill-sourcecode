package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_Distance_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainAdd_Dis_Set extends Rtx_Distance_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;

    CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal;

    public TargetTrainAdd_Dis_Set(Context context, MainActivity mMainActivity) {
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
            vDefault_init(5f, 999f, 5f, 1f);
        }
        else
        {
            vDefault_init(5f, 999f, 5f, 1f);
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
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_ADD_SELECT_TYPE);
    }

    @Override
    public void vClickButton_Confirm()
    {
        {
            cloud_TargetGoal.sGol_Item = "Distance";
            cloud_TargetGoal.sGol_Val = Rtx_TranslateValue.sFloat2String(getCurrentVal_Metric(), 1);
        }

        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_SET_DAY);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetCloudTargetGoal(CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal)
    {
        this.cloud_TargetGoal = cloud_TargetGoal;

        if(this.cloud_TargetGoal.sGol_Val != null)
        {
            setCurrentVal_Metric(Rtx_TranslateValue.fString2Float(this.cloud_TargetGoal.sGol_Val));
        }
    }
}

