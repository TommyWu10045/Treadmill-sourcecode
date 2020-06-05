package com.rtx.treadmill.RtxBaseLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextScrollView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;

/**
 * Created by chasechang on 3/22/17.
 */

public class Rtx_TargetTrainSimple_BaseLayout extends Rtx_BaseLayout {

    int iColor_Red = 0xFFFF5050;
    int iColor_Yellow = 0xFFFBF000;
    //int iColor_Green = 0xFF4FECBE;
    int iColor_Green = iColor_Red;

    protected Context mContext;

    protected RtxView             view_InfoBackground;
    public RtxView             view_ClickLayout;
    protected RtxTextView         textView_Item;

    protected RtxTextView         textView_DaysLeft;
    protected RtxTextView         textView_ViewDetails;

    protected RtxFillerTextView   mRtxFillerTextView;

    private int iMode = -1;

    public Rtx_TargetTrainSimple_BaseLayout(Context context, int iMode) {
        super(context);

        mContext = context;

        this.iMode = iMode;

        init_View();
        add_SimpleBaseLayout();
        init_ClickView();

    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        {
            if(view_InfoBackground == null)     { view_InfoBackground = new RtxView(mContext);  }
            if(textView_Item == null)           { textView_Item = new RtxTextView(mContext);    }

            if(textView_DaysLeft == null)       { textView_DaysLeft = new RtxTextView(mContext);    }
            if(textView_ViewDetails == null)    { textView_ViewDetails = new RtxTextView(mContext);    }

            if(mRtxFillerTextView == null)      { mRtxFillerTextView = new RtxFillerTextView(mContext);    }
        }
    }

    private void init_ClickView()
    {
        if(view_ClickLayout == null)        { view_ClickLayout = new RtxView(mContext); }
        view_ClickLayout.setBackgroundColor(Common.Color.transparent);
    }

    protected void add_ClickView()
    {
        if(iMode == 0)
        {
            addViewToLayout(view_ClickLayout,0,0,426,570);
        }
        else
        {
            addViewToLayout(view_ClickLayout,0,0,426,453);
        }

    }

    public void add_SimpleBaseLayout()
    {
        if(iMode == 0)  //Normal
        {
            addViewToLayout(view_InfoBackground,0,0,426,570);
            view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);

            addRtxTextViewToLayout(textView_Item, 21.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 13, 426, 57);

            addRtxTextViewToLayout(textView_DaysLeft, R.string.days_left, 16.67f, Common.Color.blue_1, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 0, 475, 426, 53);
            addRtxTextViewToLayout(textView_ViewDetails, R.string.view_details, 16.67f, Common.Color.gray_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 506, 426, 53);

            addRtxTextViewToLayout(mRtxFillerTextView, -1, 36.67f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 393, 81, 81, Common.Color.blue_1);
            {
                mRtxFillerTextView.setText("-");
                mRtxFillerTextView.setMode(1);
            }
        }
        else        //Exercise
        {
            addViewToLayout(view_InfoBackground,0,0,426,453);
            view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);

            addRtxTextViewToLayout(textView_Item, 17.54f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 9, 426, 34);

            addRtxTextViewToLayout(textView_DaysLeft, R.string.days_left, 13.46f, Common.Color.blue_1, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 0, 374, 426, 50);
            addRtxTextViewToLayout(textView_ViewDetails, R.string.view_details, 13.46f, Common.Color.gray_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 399, 426, 50);

            addRtxTextViewToLayout(mRtxFillerTextView, -1, 26.69f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 312, 65, 65, Common.Color.blue_1);
            {
                mRtxFillerTextView.setText("-");
                mRtxFillerTextView.setMode(1);
            }
        }

    }

    public void setInfoTitle(int iResId)
    {
        textView_Item.setText(LanguageData.s_get_string(mContext,iResId));
    }

    public void setLeft(int iValue , boolean bDays)
    {
        mRtxFillerTextView.setText(Rtx_TranslateValue.sInt2String(iValue));

        if(bDays)
        {
            textView_DaysLeft.setText(LanguageData.s_get_string(mContext,R.string.days_left));
        }
        else
        {
            textView_DaysLeft.setText(LanguageData.s_get_string(mContext,R.string.weeks_left));
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected int iGetValColor(float fStartVal, float fTargetVal, float fCurrentVal)
    {
        int iColor = 0x00000000;

        if(fStartVal < fTargetVal)
        {
            if(fCurrentVal < fStartVal)
            {
                iColor = iColor_Green;
            }
            else
            if(fCurrentVal > fTargetVal)
            {
                iColor = iColor_Red;
            }
            else
            {
                iColor = iColor_Yellow;
            }
        }
        else
        {
            if(fCurrentVal < fTargetVal)
            {
                iColor = iColor_Green;//green
            }
            else
            if(fCurrentVal > fStartVal)
            {
                iColor = iColor_Red;//red
            }
            else
            {
                iColor = iColor_Yellow;//yellow
            }
        }

        return iColor;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
