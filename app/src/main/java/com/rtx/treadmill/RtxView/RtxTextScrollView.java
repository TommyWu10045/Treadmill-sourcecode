package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ScrollView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.infolist;


/**
 * Created by jerry on 2016/12/29.
 */

public class RtxTextScrollView extends ScrollView
{
    Context mContext;

    public RtxTextView textView;


    public RtxTextScrollView(Context context) {
        super(context);

        mContext = context;

        init();
    }

    public void setText(int iResID)
    {
        textView.setText(LanguageData.s_get_string(mContext, iResID));
    }

    public void set_infolist(String info_tag)
    {
        if (info_tag != null) {
            infolist.v_set_info(textView, info_tag);
        }
    }

    public void setText(String sStr)
    {
        textView.setText(sStr);
    }

    public void setTextArg(float fSize, int iColor, String sFont, int style, int gravity)
    {
        textView.setGravity(gravity);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,fSize);
        textView.setTextColor(iColor);
        textView.setTypeface(textView.getTypeface(), style);//Typeface.BOLD
        textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), sFont));
        textView.setGravity(Gravity.LEFT | Gravity.TOP);
    }

    private void init()
    {
        init_TextView();
    }

    protected void setTextColor(int iColor)
    {
        textView.setTextColor(iColor);
    }

    private void init_TextView()
    {
        textView = new RtxTextView(mContext);

        textView.setGravity(Gravity.LEFT | Gravity.TOP);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,20f);
        textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
        textView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), Common.Font.Relay_Bold));
        textView.setTextColor(Common.Color.white);
        textView.setSingleLine(false);
        textView.setLineSpacing(0f, 1.5f);

        addView(textView);
    }

}
