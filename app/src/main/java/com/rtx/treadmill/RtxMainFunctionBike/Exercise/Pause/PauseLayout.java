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

public abstract class PauseLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;
    private ButtonListener mButtonListener;

    private RtxView              v_Background1;
    private RtxView              v_Background2;
    public RtxTextView          t_data1;
    private RtxFillerTextView    f_button1;
    private RtxFillerTextView    f_button2;


    public PauseLayout(Context context, MainActivity mMainActivity) {
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
        if(t_data1 == null)             { t_data1 = new RtxTextView(mContext);   }
        if(f_button1 == null)           { f_button1 = new RtxFillerTextView(mContext);   }
        if(f_button2 == null)           { f_button2 = new RtxFillerTextView(mContext);   }

    }

    private void init_event()
    {
        v_Background1.setOnClickListener(mButtonListener);
        f_button1.setOnClickListener(mButtonListener);
        f_button2.setOnClickListener(mButtonListener);
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

        iy = 200;
        ih = 60;
        fsize = 50f;
        sdata = LanguageData.s_get_string(mContext, R.string.pause) + "!";
        addRtxTextView(this, t_data1, sdata.toUpperCase(), fsize, Common.Color.pause_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 273;
        iy = 460;
        iw = 315 ;
        ih = 132;
        fsize = 45.41f;
        addRtxTextViewToLayout(f_button1, R.string.stop, fsize, Common.Color.pause_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih, Common.Color.pause_word_pink);
        f_button1.setFillet(ih/2);
        ix = 690;
        addRtxTextViewToLayout(f_button2, R.string.resume, fsize, Common.Color.pause_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih, Common.Color.pause_word_blue);
        f_button2.setFillet(ih/2);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    abstract protected void init_CustomerView();

    abstract protected void init_CustomerEvent();

    abstract protected void add_CustomerView();

    public void vClickButton01()
    {

    }

    protected void vClickButton02()
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
            else if(v == f_button2)                     { vClickButton02(); }
        }
    }
}

