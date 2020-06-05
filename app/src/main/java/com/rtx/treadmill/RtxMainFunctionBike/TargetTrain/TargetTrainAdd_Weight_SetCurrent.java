package com.rtx.treadmill.RtxMainFunctionBike.TargetTrain;

import android.content.Context;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_NumberWheel_Weight_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainAdd_Weight_SetCurrent extends Rtx_NumberWheel_Weight_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;

    CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal;

    public TargetTrainAdd_Weight_SetCurrent(Context context, MainActivity mMainActivity) {
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
    protected void add_CustomerView()
    {
        imageView_Confirm.setImageResource(R.drawable.next_big_button_enable);
    }

    @Override
    protected void changeDefaultValRange()
    {
        //User Override Can Change value range

        //DEFAULT_METRIC_SCALE = 0.1f;
        //DEFAULT_METRIC_MAX_VAL = 220.0f;
        //DEFAULT_METRIC_MIN_VAL = 35.0f;
        //DEFAULT_METRIC_VAL = 80.0f;
        //
        //
        //20190121文件 規格變更 英制體重只能設整數
        DEFAULT_IMPERIAL_SCALE = 1f;
        //DEFAULT_IMPERIAL_MAX_VAL = 484.0f;
        //DEFAULT_IMPERIAL_MIN_VAL = 77.0f;
        //DEFAULT_IMPERIAL_VAL = 176.0f;
    }

    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_CHOOSE_TARGET);
    }

    @Override
    public void vClickButton_Confirm()
    {
        cloud_TargetGoal.sUsr_Wei = Rtx_TranslateValue.sFloat2String(getCurrentVal_Metric(),5);

        {
            cloud_TargetGoal.sGol_Item = "Target Weight";
            cloud_TargetGoal.sUsr_Wei = Rtx_TranslateValue.sFloat2String(getCurrentVal_Metric(),5);
        }

        mMainActivity.mMainProcBike.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_ADD_WEI_SET_TARGET);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetCloudTargetGoal(CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal)
    {
        this.cloud_TargetGoal = cloud_TargetGoal;
    }
}

