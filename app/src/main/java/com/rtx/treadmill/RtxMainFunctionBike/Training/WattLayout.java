package com.rtx.treadmill.RtxMainFunctionBike.Training;

import android.content.Context;

import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_CircularSeekBar_BaseLayout;



/**
 * Created by chasechang on 3/22/17.
 */

public class WattLayout extends Rtx_CircularSeekBar_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;

    public WattLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void add_CustomerView()
    {
        String sdata;

        v_set_title(R.string.constant_watt);
        v_set_icon(R.drawable.input_watt_icon);

        sdata = LanguageData.s_get_string(mContext,R.string.watt);
        v_set_unit(sdata.toLowerCase());

        v_set_progress_val_size(105.2f);

        v_set_default_val(50, 400, 50, 250, 10);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_TIME);
    }

    @Override
    public void vClickButton_Confirm()
    {
        mMainActivity.mMainProcBike.trainingProc.vSet_Watt(getCurrentVal());
        mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_GO);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

