package com.rtx.treadmill.RtxMainFunctionBike.Physical;

import android.content.Context;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_NumberWheel_Weight_BaseLayout;


/**
 * Created by chasechang on 3/22/17.
 */
public class WeightLayout extends Rtx_NumberWheel_Weight_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;

    public WeightLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void changeDefaultValRange()
    {
        if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
        {
            DEFAULT_METRIC_SCALE = 0.5f;
            DEFAULT_METRIC_MAX_VAL = 180.0f;
            DEFAULT_METRIC_MIN_VAL = 35.0f;
        }
        else
        {
            DEFAULT_IMPERIAL_SCALE = 1f;
            DEFAULT_IMPERIAL_MAX_VAL = 396.0f;
            DEFAULT_IMPERIAL_MIN_VAL = 77.0f;
        }
        fDefaultVal = EngSetting.Weight.getVal(CloudDataStruct.CloudData_17.f_get_user_weight());
    }

    @Override
    protected void init_CustomerView()
    {
        mMainActivity.dismissInfoDialog();
    }

    @Override
    protected void add_CustomerView()
    {
        vSetTitleText(R.string.weight);
        v_set_netx_icon(R.drawable.next_big_button_enable);
    }


    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcBike.physicalProc.vSetNextState(PhysicalState.PROC_SHOW_PHYSICAL);
    }

    @Override
    public void vClickButton_Confirm()
    {
        mMainActivity.mMainProcBike.physicalProc.vSet_weight(getCurrentVal_Metric());
        mMainActivity.mMainProcBike.physicalProc.vSetNextState(PhysicalState.PROC_SHOW_AGE);
    }
}

