package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Pause;

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
import com.rtx.treadmill.UartDevice.UartData;


/**
 * Created by chasechang on 3/22/17.
 */

public class PauseMode02Layout extends PauseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;

    private RtxTextView t_note;

    private RtxTextView      t_info;
    private String sinfo;

    private int icount_flash = 0;

    public PauseMode02Layout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;
    }
    @Override
    protected void init_CustomerView()
    {
        icount_flash = 0;
        if(t_note == null) {t_note = new RtxTextView(mContext);}
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
        iy = 270;
        iw = 732 ;
        ih = 50;
        fsize = 20f;
        sdata = LanguageData.s_get_string(mContext, R.string.no_heart_rate_detect);
        addRtxTextView(this, t_note, sdata.toUpperCase(), fsize, Common.Color.pause_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 274;
        iy = 320;
        iw = 732 ;
        ih = 50;
        fsize = 20f;
        sinfo = LanguageData.s_get_string(mContext, R.string.workout_will_stop_in);
        addRtxTextView(this, t_info, sinfo.toUpperCase(), fsize, Common.Color.pause_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

    }

    @Override
    public void vClickButton01()
    {
        UartData.vUartCmd_61(20, "2");
        mMainActivity.mMainProcBike.exerciseProc.pauseProc.vSetNextState(PauseState.PROC_PAUSE_STOP);
    }

    @Override
    public void vClickButton02()
    {
        mMainActivity.mMainProcBike.exerciseProc.pauseProc.vSetNextState(PauseState.PROC_PAUSE_MODE2_RESUME);
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

        if((icount_flash % 6) < 2) {
            t_data1.setVisibility(INVISIBLE);
        }
        else
        {
            t_data1.setVisibility(VISIBLE);
        }

//        sdata = sinfo + s_count(brev, itotal, icount);
        //format :WORKOUT WILL STOP IN %1$d:%2$02d
        sdata = String.format( sinfo, ((itotal - icount) / EngSetting.DEF_SEC_COUNT) / 60, ((itotal - icount) / EngSetting.DEF_SEC_COUNT) % 60);
        t_info.setText(sdata);
    }

}

