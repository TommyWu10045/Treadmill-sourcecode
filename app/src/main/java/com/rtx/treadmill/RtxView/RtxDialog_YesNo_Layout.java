package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;

/**
 * Created by chasechang on 3/22/17.
 */

public class RtxDialog_YesNo_Layout extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView             view_InfoBackground;
    public RtxTextView          textView_Description;
    public RtxFillerTextView    fillerTextView_Delete;
    public RtxFillerTextView    fillerTextView_Cancel;

    public RtxDialog_YesNo_Layout(Context context) {
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
            if(view_InfoBackground == null)         { view_InfoBackground = new RtxView(mContext);   }
            if(textView_Description == null)        { textView_Description = new RtxTextView(mContext);   }
            if(fillerTextView_Delete == null)       { fillerTextView_Delete = new RtxFillerTextView(mContext);   }
            if(fillerTextView_Cancel == null)       { fillerTextView_Cancel = new RtxFillerTextView(mContext);   }
        }
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        addViewToLayout(view_InfoBackground,0,0,1002,487);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);

        addRtxTextViewToLayout(textView_Description, -1, 28f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 85, 1002, 100);

        addRtxTextViewToLayout(fillerTextView_Delete, R.string.yes_str, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, -1, 223, 378, 75, Common.Color.red_1);
        addRtxTextViewToLayout(fillerTextView_Cancel, R.string.no_str, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, -1, 345, 378, 75, Common.Color.white);
    }

    public void vSetDesciption(String sText)
    {
        textView_Description.setText(sText);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
