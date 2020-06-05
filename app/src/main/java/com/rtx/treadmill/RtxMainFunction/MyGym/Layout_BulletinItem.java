package com.rtx.treadmill.RtxMainFunction.MyGym;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;

/**
 * Created by chasechang on 3/22/17.
 */

public class Layout_BulletinItem extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView             view_InfoBackground;
    public RtxTextView          textView_Status;
    public RtxTextView          textView_Content;
    public RtxFillerTextView    fillerTextView_Date;
    private RtxView             view_Line;

    public Layout_BulletinItem(Context context) {
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
        if(view_InfoBackground == null)     { view_InfoBackground = new RtxView(mContext);              }
        if(view_Line == null)               { view_Line = new RtxView(mContext);              }
        if(textView_Status == null)         { textView_Status = new RtxTextView(mContext);              }
        if(textView_Content == null)        { textView_Content = new RtxTextView(mContext);               }
        if(fillerTextView_Date == null)     { fillerTextView_Date = new RtxFillerTextView(mContext);    }

        view_InfoBackground.setEnabled(false);
        textView_Status.setEnabled(false);
        textView_Content.setEnabled(false);
        fillerTextView_Date.setEnabled(false);


        view_InfoBackground.setClickable(false);
        textView_Status.setClickable(false);
        textView_Content.setClickable(false);
        fillerTextView_Date.setClickable(false);
    }

    private void init_event()
    {

    }

    public void setContents(String sStatus, String sTitle, String sDate)
    {
        textView_Status.setText(sStatus);
        textView_Content.setText(sTitle);

        if(sDate.length() > 0)
        {
            fillerTextView_Date.setText(sDate);
            fillerTextView_Date.setVisibility(VISIBLE);
        }
        else
        {
            fillerTextView_Date.setVisibility(INVISIBLE);
        }
    }

    private void add_View()
    {
        addViewToLayout(view_InfoBackground,0,0,1280,68);
        view_InfoBackground.setBackgroundColor(Common.Color.blue_7);
        addViewToLayout(view_Line,185,67,1011,1);
        view_Line.setBackgroundColor(Common.Color.blue_1);

        addRtxTextViewToLayout(textView_Status, -1, 22.16f, Common.Color.pink, Common.Font.Agenda_Black, Typeface.NORMAL, Gravity.CENTER, 54, 0, 144, 43);
        addRtxTextViewToLayout(textView_Content, -1, 28.48f, Common.Color.white, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, 200, 0, 740, 43);
        textView_Content.setSingleLine();
        textView_Content.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        addRtxTextViewToLayout(fillerTextView_Date, -1, 26.5f, Common.Color.white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 992, 0, 186, 43, Common.Color.blue_1);
        fillerTextView_Date.setMode(5);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
