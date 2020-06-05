package com.rtx.treadmill.RtxMainFunctionBike.Login;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class RegGenderLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private TouchListener  mTouchListener;
    private MainActivity   mMainActivity;

    private RtxImageView imageView_Female;
    private RtxImageView imageView_Male;

    private RtxTextView textView_Female;
    private RtxTextView textView_Male;

    private RtxFillerTextView textView_Mask_Female;
    private RtxFillerTextView textView_Mask_Male;

    public RegGenderLayout(Context context, MainActivity mMainActivity) {
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
        if(mButtonListener == null)
        {
            mButtonListener = new ButtonListener();
        }

        if(mTouchListener == null)
        {
            mTouchListener = new TouchListener();
        }
    }

    @Override
    public void display()
    {
        init_View();
        init_event();
        add_View();

        textView_Mask_Female.setMode(1);
        textView_Mask_Male.setMode(1);

        textView_Mask_Female.setVisibility(INVISIBLE);
        textView_Mask_Male.setVisibility(INVISIBLE);
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        {
            init_BackPrePage();

            init_Title();
            vSetTitleText(R.string.circlecloud_go_sign_up);
            init_SubTitle();
            vSetSubTitleText(R.string.gender);
        }

        {
            if(imageView_Female == null)        {   imageView_Female = new RtxImageView(mContext); }
            if(imageView_Male == null)          {   imageView_Male = new RtxImageView(mContext); }
            if(textView_Female == null)         {   textView_Female = new RtxTextView(mContext); }
            if(textView_Male == null)           {   textView_Male = new RtxTextView(mContext); }
            if(textView_Mask_Female == null)    {   textView_Mask_Female = new RtxFillerTextView(mContext); }
            if(textView_Mask_Male == null)      {   textView_Mask_Male = new RtxFillerTextView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);

        imageView_Female.setOnClickListener(mButtonListener);
        imageView_Male.setOnClickListener(mButtonListener);

        imageView_Female.setOnTouchListener(mTouchListener);
        imageView_Male.setOnTouchListener(mTouchListener);
    }

    private void add_View()
    {
        addRtxImageViewToLayout(imageView_Female, R.drawable.reg_female, 203, 284, 310, 310);
        addRtxImageViewToLayout(imageView_Male, R.drawable.reg_male, 762, 284, 310, 310);

        addRtxTextViewToLayout(textView_Female, R.string.female, 28f, Common.Color.login_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 203, 637, 310, 40);
        addRtxTextViewToLayout(textView_Male, R.string.male, 28f, Common.Color.login_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 762, 637, 310, 40);

        addRtxTextViewToLayout(textView_Mask_Female, -1, 44.51f, Common.Color.login_word_dark_black, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 203, 284, 310, 310, Common.Color.mask_color);
        addRtxTextViewToLayout(textView_Mask_Male, -1, 44.51f, Common.Color.login_word_dark_black, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 762, 284, 310, 310, Common.Color.mask_color);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickBack()
    {
        mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_UNIT);
    }

    private void vClickFemale()
    {
        GlobalData.global_RegData.iGender = 0;
        mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_HEIGHT);
    }

    private void vClickMale()
    {
        GlobalData.global_RegData.iGender = 1;
        mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_HEIGHT);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)  { vClickBack(); }
            else if(v == imageView_Female)  { vClickFemale(); }
            else if(v == imageView_Male)    { vClickMale(); }
        }
    }

    class TouchListener implements OnTouchListener
    {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            View view_mask = null;

            if(v == imageView_Female)
            {
                view_mask = textView_Mask_Female;
            }
            else if(v == imageView_Male)
            {
                view_mask = textView_Mask_Male;
            }

            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
                view_mask.setVisibility(VISIBLE);
            }
            else
            if(event.getAction() == MotionEvent.ACTION_UP)
            {
                view_mask.setVisibility(INVISIBLE);
            }

            return false;
        }
    }
}
