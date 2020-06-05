package com.rtx.treadmill.RtxMainFunction.HeartRate;

import android.content.Context;

import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_CircularHeartRate_BaseLayout;


/**
 * Created by chasechang on 3/22/17.
 */

public class TargetLayout extends Rtx_CircularHeartRate_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;
    private float fage_set = 0;

    public TargetLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void add_CustomerView()
    {
        String sdata;

        v_set_title(R.string.heart_rate);
        v_set_icon(R.drawable.hrc_heart);

        sdata = LanguageData.s_get_string(mContext,R.string.bpm);
        v_set_unit(sdata.toLowerCase());
        v_calculate_bpm();
    }

    private void v_calculate_bpm()
    {
        int imode = mMainActivity.mMainProcTreadmill.heartrateProc.imode;
        int[] idata;
        float fage ;

        fage = fage_set;

        idata = HeartRateFunc.v_calculate_bpm(imode, fage);

        v_set_default_val(idata[0], idata[1], idata[2]);

    }

    public void v_set_age(float fage) {
        if (EngSetting.Age.checkLimit(fage))
        {
            fage_set = fage;
        }
        else
        {
            fage_set = EngSetting.Age.getDef(mContext);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcTreadmill.heartrateProc.vSetNextState(HeartRateState.PROC_SHOW_TIME);
    }

    @Override
    public void vClickButton_Confirm()
    {
        mMainActivity.mMainProcTreadmill.heartrateProc.ftarget = (float) getCurrentVal();
        mMainActivity.mMainProcTreadmill.heartrateProc.vSetNextState(HeartRateState.PROC_SHOW_START);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

