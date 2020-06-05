package com.rtx.treadmill.RtxView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.RtxTools.Rtx_Log;


/**
 * Created by jerry on 2016/12/29.
 */

public class RtxHomeInfoItemScrollView extends ScrollView
{
    Context mContext;

    private LinearLayout flayout = null;

    public RtxHomeInfoItemScrollView(Context context) {
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

    public void clear()
    {
        flayout.removeAllViews();
    }

    public void addItem(int iImgResId_Tag, String sContents) {

        if(iImgResId_Tag != -1)
        {
            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            RtxImageView imageView = new RtxImageView(mContext);
            imageView.setImageResource(iImgResId_Tag);
            imageView.setPadding(0,8,15,0);
            linearLayout.addView(imageView);

            RtxTextView textView = new RtxTextView(mContext);
            textView.setText(sContents);
            setTextArg(textView, 24f, Common.Color.white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.LEFT);
            linearLayout.addView(textView);

            linearLayout.setPadding(0,0,0,30);
            flayout.addView(linearLayout);
        }
        else
        {
            RtxTextView textView = new RtxTextView(mContext);
            textView.setText(sContents);
            setTextArg(textView, 24f, Common.Color.white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.LEFT);


            flayout.addView(textView);
        }
    }

    public void setTextArg(RtxTextView view, float fSize, int iColor, String sFont, int style, int gravity)
    {
        view.setGravity(gravity);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,fSize);
        view.setTextColor(iColor);
        view.setTypeface(view.getTypeface(), style);//Typeface.BOLD
        view.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), sFont));
    }
}
