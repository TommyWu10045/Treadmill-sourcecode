package com.rtx.treadmill.RtxMainFunction.Login;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class RegFinishLayout extends Rtx_BaseLayout {

    private Context mContext;

    private MainActivity   mMainActivity;

    private RtxImageView    imageView_Done;
    private RtxTextView     textView_Congratulation;
    private RtxTextView     textView_RegDone;

    public RegFinishLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

        setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        setBackgroundColor(Common.Color.background);
        this.mMainActivity = mMainActivity;
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
        if(imageView_Done == null)          { imageView_Done = new RtxImageView(mContext); }
        if(textView_Congratulation == null) { textView_Congratulation = new RtxTextView(mContext); }
        if(textView_RegDone == null)        { textView_RegDone = new RtxTextView(mContext); }
    }

    private void init_event()
    {

    }

    private void add_View()
    {
        addRtxTextViewToLayout(textView_Congratulation, R.string.congratulations_lower, 34.69f, Common.Color.login_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 58, 1280, 64);
        addRtxTextViewToLayout(textView_RegDone, R.string.registration_done, 34.69f, Common.Color.green_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, -1, 614, 1280, 60);
        addRtxImageViewToLayout(imageView_Done, R.drawable.reg_done, -1, 172, 375, 375);
    }

    protected void setName(String sName)
    {
        textView_Congratulation.setText(LanguageData.s_get_string(mContext,R.string.congratulations_lower) + " " + sName + "!");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
