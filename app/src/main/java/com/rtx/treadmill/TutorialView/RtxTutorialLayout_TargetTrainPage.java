package com.rtx.treadmill.TutorialView;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.widget.TextView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.R;


/**
 * Created by chasechang on 3/22/17.
 */

public class RtxTutorialLayout_TargetTrainPage extends RtxTutorialBaseLayout {

    private Context mContext;

    public RtxTutorialLayout_TargetTrainPage(Context context) {
        super(context);

        mContext = context;

        display();
    }

    @Override
    public void display()
    {
        vSetBackgroundImage(R.drawable.tutorial_page_target);

        addRtxTextViewToLayout(textView_Str,28.00f, Common.Color.tutorial_target, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 30, 677 - 80, 1200, 34 + 100);//By Alan

        LanguageData.s_set_string(mContext, textView_Str, R.string.tutorial_target_1st, R.string.tutorial_target_2st, R.string.tutorial_icon, R.drawable.tutorial_icon_target);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
