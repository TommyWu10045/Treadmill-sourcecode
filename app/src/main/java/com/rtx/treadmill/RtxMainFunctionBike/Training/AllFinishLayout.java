package com.rtx.treadmill.RtxMainFunctionBike.Training;

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
        int ix, iy, iw, ih;
        String stitle;
        String smode;
        String sdata;
        //        menu

        smode = LanguageData.s_get_string(mContext, R.string.training).toUpperCase();

        switch (imode)
        {
            case 0x00:
                stitle = LanguageData.s_get_string(mContext, R.string.manual);
                break;
            case 0x01:
                stitle = LanguageData.s_get_string(mContext, R.string.rolling_hill);
                break;
            case 0x02:
                stitle = LanguageData.s_get_string(mContext, R.string.mountain_peak);
                break;
            case 0x03:
                stitle = LanguageData.s_get_string(mContext, R.string.fatburn);
                break;
            case 0x04:
                stitle = LanguageData.s_get_string(mContext, R.string.incline);
                break;
            case 0x05:
                stitle = LanguageData.s_get_string(mContext, R.string.strenght);
                break;
            case 0x06:
                stitle = LanguageData.s_get_string(mContext, R.string.interval);
                break;
            case 0x07:
                stitle = LanguageData.s_get_string(mContext, R.string.constant_watt);
                break;
            case 0x0a:
                stitle = LanguageData.s_get_string(mContext, R.string.calories);
                break;
            case 0x0b:
                stitle = LanguageData.s_get_string(mContext, R.string.time);
                break;
            case 0x0c:
                stitle = LanguageData.s_get_string(mContext, R.string.distance);
                break;
            default:
                stitle = LanguageData.s_get_string(mContext, R.string.time);
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
        mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_LOGOUT);
    }

    @Override
    protected void v_done()
    {
        mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_DONE);
    }




}
