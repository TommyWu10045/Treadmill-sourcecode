package com.rtx.treadmill.RtxMainFunctionBike.MyWorkout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;

/**
 * Created by jerry on 2016/12/29.
 */

public class View_MyWorkout_DataBlock extends Rtx_BaseLayout {

    private Context mContext;

    private RtxView         view_BlockBackground;

    private RtxImageView    imageView_Time;
//    private RtxImageView    imageView_Speed;
    private RtxImageView    imageView_Incline;

    private RtxTextView     textView_Number;
    private RtxTextView     textView_Time;
//    private RtxTextView     textView_Speed;
    private RtxTextView     textView_Incline;


    public View_MyWorkout_DataBlock(Context context) {
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
        add_View();
    }

    private void init_View()
    {
        if(view_BlockBackground == null)     { view_BlockBackground = new RtxView(mContext); }

        if(imageView_Time == null)           { imageView_Time = new RtxImageView(mContext); }
//        if(imageView_Speed == null)          { imageView_Speed = new RtxImageView(mContext); }
        if(imageView_Incline == null)        { imageView_Incline = new RtxImageView(mContext); }

        if(textView_Number == null)          { textView_Number = new RtxTextView(mContext); }
        if(textView_Time == null)            { textView_Time = new RtxTextView(mContext); }
//        if(textView_Speed == null)           { textView_Speed = new RtxTextView(mContext); }
        if(textView_Incline == null)         { textView_Incline = new RtxTextView(mContext); }
    }

    private void add_View()
    {
        addViewToLayout(view_BlockBackground,0,91,183,454);
        view_BlockBackground.setBackgroundColor(Common.Color.yellow_1);

        addRtxTextViewToLayout(textView_Number, -1, 43.33f, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 0, 0, 184, 90);

        addRtxTextViewToLayout(textView_Time, -1, 43.33f, Common.Color.purple_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 26, 249, 131, 46);
//        addRtxTextViewToLayout(textView_Speed, -1, 43.33f, Common.Color.purple_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 26, 322, 131, 46);
        addRtxTextViewToLayout(textView_Incline, -1, 43.33f, Common.Color.purple_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 26, 403, 131, 46);

        addRtxImagePaddingViewToLayout(imageView_Time, R.drawable.workout_block_time, 76, 202, 30, 30, 0);
//        addRtxImagePaddingViewToLayout(imageView_Speed, R.drawable.workout_block_speed, 76, 277, 29, 15, 0);
        addRtxImagePaddingViewToLayout(imageView_Incline, R.drawable.workout_block_level, 76, 361, 30, 23, 0);

        setViewTouchDisable(view_BlockBackground);
        setViewTouchDisable(textView_Number);
        setViewTouchDisable(textView_Time);
//        setViewTouchDisable(textView_Speed);
        setViewTouchDisable(textView_Incline);
        setViewTouchDisable(imageView_Time);
//        setViewTouchDisable(imageView_Speed);
        setViewTouchDisable(imageView_Incline);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void vSetNumber(int iNum)
    {
        textView_Number.setText(Rtx_TranslateValue.sInt2String(iNum));
    }

    protected void vSetData(int iTime, float fSpeed, float fIncline)
    {
        textView_Time.setText(Rtx_TranslateValue.sInt2String(iTime));
//        textView_Speed.setText(Rtx_TranslateValue.sFloat2String(fSpeed,1));
        textView_Incline.setText(Rtx_TranslateValue.sFloat2String(fIncline,0));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
