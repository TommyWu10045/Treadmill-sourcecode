package com.rtx.treadmill.RtxMainFunctionBike.HeartRate;

import android.content.Context;

import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunctionBike.BaseLayout.SummaryLayout;

/**
 * Created by chasechang on 3/22/17.
 */

public class AllFinishLayout extends SummaryLayout {
    private String TAG = "Jerry" ;

    private MainActivity        mMainActivity;
    private Context mContext;

    private int imode ;

    public AllFinishLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;

    }

    @Override
    protected void add_CustomerUpdateView()
    {
        String stitle;
        String smode;
        String sdata;
        //        menu

        smode = LanguageData.s_get_string(mContext, R.string.heart_rate_control).toUpperCase();

        switch (imode)
        {
            case 0x00:
                stitle = LanguageData.s_get_string(mContext, R.string.fatburn);
                break;
            case 0x01:
                stitle = LanguageData.s_get_string(mContext, R.string.aerobic);
                break;
            case 0x02:
                stitle = LanguageData.s_get_string(mContext, R.string.performance);
                break;
            case 0x03:
                stitle = LanguageData.s_get_string(mContext, R.string.customize);
                break;
            default:
                stitle = LanguageData.s_get_string(mContext, R.string.customize);
                break;
        }

        sdata = LanguageData.s_get_string(mContext, R.string.summary) + " - " + smode;
        vSetTitleText(sdata.toUpperCase());

        v_set_type_name(smode, stitle.toUpperCase());


    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void vSet_mode(int imode)
    {
        this.imode = imode;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void v_logout()
    {
        mMainActivity.mMainProcBike.heartrateProc.vSetNextState(HeartRateState.PROC_SHOW_LOGOUT);
    }

    @Override
    protected void v_done()
    {
        mMainActivity.mMainProcBike.heartrateProc.vSetNextState(HeartRateState.PROC_SHOW_DONE);
    }

    /////////////////////////////////////////////////////



}
