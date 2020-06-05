package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

/**
 * Created by chasechang on 3/22/17.
 */

public class RtxDialog_Timeout_Reset extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView             view_InfoBackground;
    public RtxTextView          textView_Reset;
    public RtxTextView          textView_TimeVal;
    public RtxTextView          textView_Description;
    public RtxFillerTextView    fillerTextView_Continue;

    public RtxDialog_Timeout_Reset(Context context) {
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
            if(textView_Reset == null)              { textView_Reset = new RtxTextView(mContext);   }
            if(textView_TimeVal == null)            { textView_TimeVal = new RtxTextView(mContext);   }
            if(textView_Description == null)        { textView_Description = new RtxTextView(mContext);   }
            if(fillerTextView_Continue == null)     { fillerTextView_Continue = new RtxFillerTextView(mContext);   }
        }
    }

    private void add_View()
    {
        addViewToLayout(view_InfoBackground,0,0,932,550);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);


        addRtxTextViewToLayout(textView_Reset, R.string.reset, 50f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 327, 89, 175, 53);
        addRtxTextViewToLayout(textView_TimeVal, 50f, Common.Color.green_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 503, 89, 101, 53);

        String sDescription = LanguageData.s_get_string(mContext,R.string.system_will_be_auto_reset) + "\n" + LanguageData.s_get_string(mContext,R.string.press_continue);

        addRtxTextViewToLayout(textView_Description, sDescription, 20f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 188, 932, 97);
        addRtxTextViewToLayout(fillerTextView_Continue, R.string.continue_str, 45.41f, Common.Color.black, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 457-125, 379, 128, Common.Color.green_1);
    }

    public void vSetTimeVal(int iSec)
    {
        textView_TimeVal.setText(Rtx_TranslateValue.sInt2String(iSec));
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
