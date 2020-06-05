package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Error;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class ErrorNULLLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;

    private RtxTextView      t_error;
    private RtxTextView      t_errorcode;
    private RtxTextView      t_errornote;

    public ErrorNULLLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

        setBackgroundColor(Common.Color.background);
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void display()
    {
        init_View();
        init_event();
        add_View();
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        if(t_error == null) {t_error = new RtxTextView(mContext);}
        if(t_errorcode == null) {t_errorcode = new RtxTextView(mContext);}
        if(t_errornote == null) {t_errornote = new RtxTextView(mContext);}
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        int ix, iy, iw, ih;
        float fsize;
        String sdata;

        ix = 0;
        iy = 120;
        iw = 1280 ;
        ih = 170;
        fsize = 132.38f;
        sdata = LanguageData.s_get_string(mContext, R.string.error);
        //addRtxTextView(this, t_error, sdata.toUpperCase(), fsize, Common.Color.error_word_red, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy = 295;
        ih = 80;
        fsize = 63.3f;
        sdata = LanguageData.s_get_string(mContext, R.string.error_codes) + ExerciseData.uart_data1.serror_code;
        addRtxTextView(this, t_errorcode, sdata.toUpperCase(), fsize, Common.Color.error_word_red, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy = 460;
        ih = 40;
        fsize = 32.75f;
        sdata = ExerciseData.Check_error_code(ExerciseData.uart_data1.serror_code);
        addRtxTextView(this, t_errornote, sdata, fsize, Common.Color.error_word_yellow, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}

