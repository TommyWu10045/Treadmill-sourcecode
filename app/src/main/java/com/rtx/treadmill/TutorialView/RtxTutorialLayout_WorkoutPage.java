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

public class RtxTutorialLayout_WorkoutPage extends RtxTutorialBaseLayout {

    private Context mContext;

    public RtxTutorialLayout_WorkoutPage(Context context) {
        super(context);

        mContext = context;

        display();
    }

    @Override
    public void display()
    {
        vSetBackgroundImage(R.drawable.tutorial_page_workout);

        addRtxTextViewToLayout(textView_Str,28.00f, Common.Color.tutorial_workout, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 30, 677 - 50, 1200, 34 + 100);//By Alan

        LanguageData.s_set_string(mContext, textView_Str, R.string.tutorial_workout_1st, R.string.tutorial_workout_2st, R.string.tutorial_icon, R.drawable.tutorial_icon_workout);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
