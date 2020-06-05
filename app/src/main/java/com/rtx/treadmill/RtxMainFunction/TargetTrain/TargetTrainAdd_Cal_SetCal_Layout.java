package com.rtx.treadmill.RtxMainFunction.TargetTrain;

import android.content.Context;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_CircularSeekBar_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.GlobalData.LanguageData;



/**
 * Created by chasechang on 3/22/17.
 */

public class TargetTrainAdd_Cal_SetCal_Layout extends Rtx_CircularSeekBar_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;

    CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal;

    public TargetTrainAdd_Cal_SetCal_Layout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void add_CustomerView()
    {
        String sdata;

        v_set_title(R.string.calories);
        v_set_icon(R.drawable.input_calories_icon);

        sdata = LanguageData.s_get_string(mContext,R.string.kcal);
        v_set_unit(sdata.toLowerCase());

        v_set_progress_val_size(105.2f);

        v_set_default_val(100,99900,100,6000,100);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_CHOOSE_TARGET);
    }

    @Override
    public void vClickButton_Confirm()
    {
        {
            cloud_TargetGoal.sGol_Item = "Calories";
            cloud_TargetGoal.sGol_Val = Rtx_TranslateValue.sInt2String(getCurrentVal());
        }

        mMainActivity.mMainProcTreadmill.targetTrainProc.vSetNextState(TargetTrainState.PROC_SHOW_PAGE_SET_DAY);
    }

    protected void vSetCloudTargetGoal(CloudDataStruct.CLOUD_TATGET_GOAL cloud_TargetGoal)
    {
        this.cloud_TargetGoal = cloud_TargetGoal;

        if(cloud_TargetGoal.sGol_Val != null)
        {
            setCurrentVal(Rtx_TranslateValue.iString2Int(cloud_TargetGoal.sGol_Val));
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

