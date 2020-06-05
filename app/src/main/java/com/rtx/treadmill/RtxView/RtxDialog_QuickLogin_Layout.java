package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.ProgressBar;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * Created by chasechang on 3/22/17.
 */

public class RtxDialog_QuickLogin_Layout extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView         view_InfoBackground;
    private ProgressBar     progressBar;
    private RtxTextView     textView_Msg;

    public RtxDialog_QuickLogin_Layout(Context context) {
        super(context);

        mContext = context;

        init();
        display();
    }

    @Override
    public void init()
    {

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
        {
            if(view_InfoBackground == null) { view_InfoBackground = new RtxView(mContext);   }
            if(progressBar == null)         { progressBar = new ProgressBar(mContext);   }
            if(textView_Msg == null)        { textView_Msg = new RtxTextView(mContext);     }
        }
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        addViewToLayout(view_InfoBackground,0,0,1280,800);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);
        addRtxViewToLayout(progressBar,-1,173,256,256,0);
        addRtxTextViewToLayout(textView_Msg, R.string.initializing_quick_login,34.69f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 510, 1280, 40);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
