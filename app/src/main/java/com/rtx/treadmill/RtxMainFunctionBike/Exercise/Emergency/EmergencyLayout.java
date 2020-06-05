package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Emergency;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public abstract class EmergencyLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;

    private RtxImageView     i_logo;
    private RtxTextView      t_emer;
    private RtxTextView      t_stop;
    private RtxTextView      t_note;
    private RtxImageView     i_button;

    public EmergencyLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

        setBackgroundColor(Common.Color.background);
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void display()
    {
        init_View();
        init_CustomerView();    //Let user can override this.
        init_event();
        init_CustomerEvent();   //Let user can override this.
        add_View();
        add_CustomerView();    //Let user can override this.
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        init_TopBackground();
        if(i_logo == null) {i_logo = new RtxImageView(mContext);}
        if(t_emer == null) {t_emer = new RtxTextView(mContext);}
        if(t_stop == null) {t_stop = new RtxTextView(mContext);}
        if(t_note == null) {t_note = new RtxTextView(mContext);}
        if(i_button == null) {i_button = new RtxImageView(mContext);}

    }

    private void init_event()
    {

    }

    private void add_View()
    {
        int ix, iy, iw, ih;
        float fsize;
        String sdata;

        ix = 50;
        iy = 48;
        iw = 143 ;
        ih = 30;
        addRtxImagePaddingViewToLayout(i_logo, R.drawable.circle_logo, ix, iy, iw, ih, 0);

        ix = 0;
        iy = 140;
        iw = 1280 ;
        ih = 80;
        fsize = 63.3f;
        sdata = LanguageData.s_get_string(mContext, R.string.emergency);
        addRtxTextView(this, t_emer, sdata.toUpperCase(), fsize, Common.Color.emergency_word_red, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy = 220;
        ih = 170;
        fsize = 132.38f;
        sdata = LanguageData.s_get_string(mContext, R.string.stop) + "!";
        addRtxTextView(this, t_stop, sdata.toUpperCase(), fsize, Common.Color.emergency_word_red, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy = 460;
        ih = 40;
        fsize = 32.75f;
        sdata = LanguageData.s_get_string(mContext, R.string.emergency_note);
        addRtxTextView(this, t_note, sdata, fsize, Common.Color.emergency_word_yellow, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 545;
        iy = 690;
        iw = 185 ;
        ih = 65;
        addRtxImagePaddingViewToLayout(i_button, R.drawable.emergency_button, ix, iy, iw, ih, 0);


    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    abstract protected void init_CustomerView();

    abstract protected void init_CustomerEvent();

    abstract protected void add_CustomerView();

}

