package com.rtx.treadmill.TutorialView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class RtxTutorialLayout_LastPage extends RtxTutorialBaseLayout {

    private Context mContext;
    public RtxTextView   textView_Start;

    public RtxTutorialLayout_LastPage(Context context) {
        super(context);

        mContext = context;

        if(textView_Start == null)          { textView_Start = new RtxTextView(mContext);   }
        display();
    }

    @Override
    public void display()
    {
        vSetBackgroundImage(R.drawable.tutorial_page_last);

        addRtxTextViewToLayout(textView_Str, R.string.tutorial_last_str,32.75f, Common.Color.white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER_VERTICAL, 658 - 25, 198 - 50, 552 + 50, 172 + 100);

        addRtxTextViewToLayout(textView_Start, R.string.start,51.91f, Common.Color.white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 658, 444, 499, 99);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
