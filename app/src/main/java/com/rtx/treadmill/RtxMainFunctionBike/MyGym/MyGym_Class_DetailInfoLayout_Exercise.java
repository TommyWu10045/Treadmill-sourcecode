package com.rtx.treadmill.RtxMainFunctionBike.MyGym;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.UiDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxTextScrollView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;


/**
 * Created by chasechang on 3/22/17.
 */

public class MyGym_Class_DetailInfoLayout_Exercise extends MyGym_Class_DetailInfoLayout {

    public MyGym_Class_DetailInfoLayout_Exercise(Context context, MainActivity mMainActivity) {
        super(context,mMainActivity);
    }

    @Override
    protected void add_View()
    {
        init_BackPrePage();
        init_Title();

        addViewToLayout(dateIcon,126,125,74,74);
        addRtxTextViewToLayout(textView_Time,-1,24f,Common.Color.yellow_1,Common.Font.Relay_Bold,Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, 234, 125, 282, 74);
        addRtxTextViewToLayout(textView_ClassName,-1,25.63f,Common.Color.white,Common.Font.Relay_Medium,Typeface.NORMAL, Gravity.LEFT | Gravity.TOP, 126, 216, 282, 64);
        addRtxTextViewToLayout(textView_Instructor,-1,18.98f,Common.Color.blue_5,Common.Font.Relay_Bold,Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, 126, 282, 300, 28);
        addRtxTextScrollViewToLayout(textScrollView_Contents,23.74f,Common.Color.white,Common.Font.Relay_Medium,Typeface.NORMAL, Gravity.LEFT | Gravity.TOP, 123, 340, 282, 304);
        addRtxTextViewToLayout(fillerTextView_Operator, -1, 16.00f, Common.Color.white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 810, 137, 328, 49, 0x00);//By Alan

        textView_ClassName.setLines(2);
        textView_ClassName.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        textView_Instructor.setSingleLine();
        textView_Instructor.setEllipsize(TextUtils.TruncateAt.valueOf("END"));

        addRtxImagePaddingViewToLayout(imageView_Default, R.drawable.gym_default_image, 793,252,361,361, 0);
        viewPager_Photo.setAdapter(pagerAdapter_Photo);
        addViewToLayout(viewPager_Photo,793,262,361,361);
    }
}