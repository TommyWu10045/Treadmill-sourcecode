package com.rtx.treadmill.RtxMainFunction.Exercise.Pause;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class ResetMode01Layout extends ResetLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;

    private RtxTextView      t_info;
    private String sinfo;

    private int icount_flash = 0;

    public ResetMode01Layout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;
    }
    @Override
    protected void init_CustomerView()
    {
        icount_flash = 0;
        if(t_info == null) {t_info = new RtxTextView(mContext);}
    }

    @Override
    protected void init_CustomerEvent()
    {

    }

    @Override
    protected void add_CustomerView()
    {
        int ix, iy, iw, ih;
        float fsize;
        String sdata;

        ix = 274;
        iy = 210;
        iw = 732 ;
        ih = 60;
        fsize = 45.41f;
        sinfo = LanguageData.s_get_string(mContext, R.string.reset) + " ";
        addRtxTextView(this, t_info, sinfo.toUpperCase(), fsize, Common.Color.pause_word_green, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

    }

    @Override
    public void vClickButton01()
    {
        mMainActivity.mMainProcTreadmill.exerciseProc.pauseProc.vSetNextState(PauseState.PROC_PAUSE_RESET2_RESUME);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String s_count(boolean brev, int itotal, int icount)
    {
        String sdata;
        int idata;

        if(brev)
        {
            idata = itotal - icount;
        }
        else
        {
            idata = icount;
        }

        idata /= EngSetting.DEF_SEC_COUNT;

        sdata = Rtx_Calendar.s_trans_integer(1, idata);

        return sdata;
    }

    public void Refresh(boolean brev, int itotal, int icount)
    {
        String sdata;
        icount_flash++;

        if((icount_flash % 4) == 0) {
            sdata = sinfo + s_count(brev, itotal, icount);
            t_info.setText(sdata);

        }
    }

}

