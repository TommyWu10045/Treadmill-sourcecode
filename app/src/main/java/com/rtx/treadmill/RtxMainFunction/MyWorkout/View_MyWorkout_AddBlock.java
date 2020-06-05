package com.rtx.treadmill.RtxMainFunction.MyWorkout;

import android.content.Context;
import android.graphics.Color;
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

public class View_MyWorkout_AddBlock extends Rtx_BaseLayout {

    private Context mContext;

    private RtxImageView    imageView_Add;
    private RtxTextView     textView_Add;


    public View_MyWorkout_AddBlock(Context context) {
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
        if(imageView_Add == null)   { imageView_Add = new RtxImageView(mContext); }

        if(textView_Add == null)    { textView_Add = new RtxTextView(mContext); }
    }

    private void add_View()
    {
        addRtxImagePaddingViewToLayout(imageView_Add, R.drawable.workout_block_add, -1, 224, 117, 117, 0);
        addRtxTextViewToLayout(textView_Add, -1, 43.33f, Common.Color.purple_2, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 392 - 50, 183, 14 + 100);

        imageView_Add.setClickable(false);
        imageView_Add.setEnabled(false);
        textView_Add.setClickable(false);
        textView_Add.setEnabled(false);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
