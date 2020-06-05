package com.rtx.treadmill.RtxMainFunction.Training;

import android.content.Context;

import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_CircularSeekBar_BaseLayout;



/**
 * Created by chasechang on 3/22/17.
 */

public class CaloriesLayout extends Rtx_CircularSeekBar_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;

    public CaloriesLayout(Context context, MainActivity mMainActivity) {
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

        v_set_default_val(100, 2000, 100, 600, 10);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_SHOW_TARGET);
    }

    @Override
    public void vClickButton_Confirm()
    {
        mMainActivity.mMainProcTreadmill.trainingProc.vSet_target(getCurrentVal());
        mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_SHOW_GO);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

