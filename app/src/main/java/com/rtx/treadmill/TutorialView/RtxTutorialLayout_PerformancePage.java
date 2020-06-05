package com.rtx.treadmill.TutorialView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;


/**
 * Created by chasechang on 3/22/17.
 */

public class RtxTutorialLayout_PerformancePage extends RtxTutorialBaseLayout {

    private Context mContext;

    public RtxTutorialLayout_PerformancePage(Context context) {
        super(context);

        mContext = context;

        display();
    }

    @Override
    public void display()
    {
        vSetBackgroundImage(R.drawable.tutorial_page_performance);

        addRtxTextViewToLayout(textView_Str,32.75f, Common.Color.tutorial_performanec, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 0, 677 - 50, 1280, 34 + 100);

        LanguageData.s_set_string(mContext, textView_Str, R.string.tutorial_performance_1st, R.string.tutorial_performance_2st, R.string.tutorial_icon, R.drawable.tutorial_icon_performance);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
