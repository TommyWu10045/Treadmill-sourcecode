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

public class RtxTutorialLayout_FistPage extends RtxTutorialBaseLayout {

    private Context mContext;

    public RtxTutorialLayout_FistPage(Context context) {
        super(context);

        mContext = context;

        display();
    }

    @Override
    public void display()
    {
        vSetBackgroundImage(R.drawable.tutorial_page_first);

        addRtxTextViewToLayout(textView_Str,28.00f, Common.Color.tutorial_first, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 30, 632 - 80, 1200, 80 + 200);//By Alan

        String str1 = LanguageData.s_get_string(mContext,R.string.tutorial_first_1st);
        String str2 = LanguageData.s_get_string(mContext,R.string.tutorial_first_2st);
        String str = str1 + "\n" +str2;

        textView_Str.setText(str);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
