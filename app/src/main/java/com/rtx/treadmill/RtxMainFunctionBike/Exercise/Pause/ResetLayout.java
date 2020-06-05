package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Pause;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;


/**
 * Created by chasechang on 3/22/17.
 */

abstract public class ResetLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;
    private ButtonListener mButtonListener;

    private RtxView v_Background1;
    private RtxView              v_Background2;
    public RtxTextView          t_data2;
    public RtxTextView          t_data3;
    private RtxFillerTextView f_button1;


    public ResetLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

        setBackgroundColor(Common.Color.transparent);
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void init()
    {
        if(mButtonListener == null)
        {
            mButtonListener = new ButtonListener();
        }
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
        if(v_Background1 == null)        { v_Background1 = new RtxView(mContext);   }
        if(v_Background2 == null)        { v_Background2 = new RtxView(mContext);   }
        if(t_data2 == null)             { t_data2 = new RtxTextView(mContext);   }
        if(t_data3 == null)             { t_data3 = new RtxTextView(mContext);   }
        if(f_button1 == null)           { f_button1 = new RtxFillerTextView(mContext);   }

    }

    private void init_event()
    {
        v_Background1.setOnClickListener(mButtonListener);
        f_button1.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        int ix, iy, iw, ih;
        float fsize;
        String sdata;

        ix = 0;
        iy = 0;
        iw = 1280 ;
        ih = 800;
        addViewToLayout(v_Background1,ix, iy, iw, ih);
        v_Background1.setBackgroundColor(Common.Color.pause_back01);

        ix = 174;
        iy = 126;
        iw = 932 ;
        ih = 535;
        addViewToLayout(v_Background2,ix, iy, iw, ih);
        v_Background2.setBackgroundColor(Common.Color.pause_back02);

        iy = 328;
        ih = 30;
        fsize = 20f;
        sdata = LanguageData.s_get_string(mContext, R.string.system_will_be_auto_reset) ;
        addRtxTextView(this, t_data2, sdata.toUpperCase(), fsize, Common.Color.pause_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy = 361;
        sdata = LanguageData.s_get_string(mContext, R.string.press_continue) ;
        addRtxTextView(this, t_data3, sdata.toUpperCase(), fsize, Common.Color.pause_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 447;
        iy = 455;
        iw = 384 ;
        ih = 132;
        fsize = 45.41f;
        addRtxTextViewToLayout(f_button1, R.string.continue01, fsize, Common.Color.pause_word_black, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih, Common.Color.pause_word_green);
        f_button1.setFillet(ih/2);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    abstract protected void init_CustomerView();

    abstract protected void init_CustomerEvent();

    abstract protected void add_CustomerView();

    public void vClickButton01()
    {

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == v_Background1)            { }
            else if(v == f_button1)                     { vClickButton01(); }

        }
    }

}

