package com.rtx.treadmill.RtxMainFunction.Hiit;

import android.content.Context;

import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunction.BaseLayout.SummaryLayout;

/**
 * Created by chasechang on 3/22/17.
 */

public class AllFinishLayout extends HiitSummaryLayout {
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
        String sdata;

        //        menu
        stitle = LanguageData.s_get_string(mContext, R.string.hiit);
        sdata = LanguageData.s_get_string(mContext, R.string.summary) + " - " + stitle;
        vSetTitleText(sdata.toUpperCase());

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
        mMainActivity.mMainProcTreadmill.hiitProc.vSetNextState(HiitState.PROC_EXERCISE_SHOW_LOGOUT);
    }

    @Override
    protected void v_done()
    {
        mMainActivity.mMainProcTreadmill.hiitProc.vSetNextState(HiitState.PROC_EXERCISE_SHOW_DONE);
    }


}
