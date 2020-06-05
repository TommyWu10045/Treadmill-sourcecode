package com.rtx.treadmill.RtxMainFunctionBike.MyGym;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rtx.treadmill.GlobalData.Common;

import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;



/**
 * Created by chasechang on 3/22/17.
 */

public class MyGym_Bulletin_DetailInfoLayout_Exercise extends MyGym_Bulletin_DetailInfoLayout {


    public MyGym_Bulletin_DetailInfoLayout_Exercise(Context context, MainActivity mMainActivity) {
        super(context,mMainActivity);
    }

    @Override
    protected void add_View()
    {
        init_BackPrePage();
        init_Title();
        vSetTitleText(R.string.bulletin);

        {
            addViewToLayout(doubleStringView_Title, 147, 132, 674, 38);
            doubleStringView_Title.setPaint(Common.Font.Relay_Medium, Common.Color.white, 26.10f, Common.Font.Agenda_Black, Common.Color.pink, 20.31f);
            doubleStringView_Title.setAlignLeft(Gravity.LEFT);
            doubleStringView_Title.setEllipsis(true);
        }

        {
            addRtxTextViewToLayout(fillerTextView_Date, -1, 24.43f, Common.Color.white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 957, 129, 171, 39, Common.Color.blue_1);
            fillerTextView_Date.setMode(5);
        }

        {
            addViewToLayout(mScrollView,-1,203,1040,440);

            {
                LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(1040,440);
                mLinearLayout.setOrientation(LinearLayout.VERTICAL);
                mLinearLayout.setLayoutParams(mLayoutParams);
                if(mScrollView.getChildCount() != 0)
                {
                    mScrollView.removeAllViews();
                }
                mScrollView.addView(mLinearLayout);
            }
        }
    }

    @Override
    protected void addContentsView()
    {
        setTextArg(textView_Contents, 21.88f, Common.Color.white, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.LEFT | Gravity.TOP);

        try
        {
            mLinearLayout.addView(textView_Contents);
        }
        catch (IllegalStateException e)
        {

        }
    }

    @Override
    protected void addPhotoView()
    {
        //imageView_Pho.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView_Pho.setScaleType(ImageView.ScaleType.FIT_START);
        imageView_Pho.setBackgroundColor(0x00000000);
        imageView_Pho.setImageResource(R.drawable.gym_default_image);

        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(840,630);
        mLayoutParams.setMargins(0,30,0,-60);
        mLayoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;

        try
        {
            mLinearLayout.addView(imageView_Pho,mLayoutParams);
        }
        catch (IllegalStateException e)
        {

        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}