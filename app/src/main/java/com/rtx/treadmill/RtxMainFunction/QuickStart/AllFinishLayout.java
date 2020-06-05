package com.rtx.treadmill.RtxMainFunction.QuickStart;

import android.content.Context;

import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunction.BaseLayout.SummaryLayout;

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
        smode = LanguageData.s_get_string(mContext, R.string.quick_start).toUpperCase();
        stitle = LanguageData.s_get_string(mContext, R.string.quick_start);

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
        mMainActivity.mMainProcTreadmill.quickStartProc.vSetNextState(QuickStartState.PROC_SHOW_LOGOUT);
    }

    @Override
    protected void v_done()
    {
        mMainActivity.mMainProcTreadmill.quickStartProc.vSetNextState(QuickStartState.PROC_SHOW_DONE);
    }


}
