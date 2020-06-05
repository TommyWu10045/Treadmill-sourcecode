package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.rtx.treadmill.RtxTools.Rtx_Log;


/**
 * Created by jerry on 2016/12/29.
 */

public class RtxScrollView extends ScrollView
{
    Context mContext;

    private LinearLayout flayout = null;

    public RtxScrollView(Context context) {
        super(context);

        mContext = context;

        init();
    }

    private void init()
    {
        if(flayout == null) {
            flayout = new LinearLayout(mContext);
            flayout.setOrientation(LinearLayout.VERTICAL);
            addView(flayout);
        }
    }

    private void setTextArg(RtxTextView view, float fSize, int iColor, String sFont, int style, int gravity)
    {
        view.setGravity(gravity);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,fSize);
        view.setTextColor(iColor);
        view.setTypeface(view.getTypeface(), style);//Typeface.BOLD
        view.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), sFont));
        view.setGravity(Gravity.LEFT | Gravity.TOP);
    }

    public void addRtxTextView(RtxTextView view, float fSize, int iColor, String sFont, int style, int gravity) {

        if(view == null)
        {
            Rtx_Log.Log_Error("view is null in addRtxTextView!!!");
            return;
        }

        try {
            if(flayout != null) {
                setTextArg(view, fSize, iColor, sFont, style, gravity);
                flayout.addView(view);
            }
        }
        catch (Exception e)
        {

        }
    }


}
