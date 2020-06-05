package com.rtx.treadmill.RtxMainFunction.Login;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Keyboard;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class RegUnitLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private TouchListener  mTouchListener;
    private MainActivity   mMainActivity;

    private RtxFillerTextView textView_Metric;
    private RtxFillerTextView textView_Imperial;

    private RtxFillerTextView textView_Mask_Metric;
    private RtxFillerTextView textView_Mask_Imperial;

    public RegUnitLayout(Context context, MainActivity mMainActivity) {
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

        textView_Metric.setMode(1);
        textView_Imperial.setMode(1);
        textView_Mask_Metric.setMode(1);
        textView_Mask_Imperial.setMode(1);

        textView_Mask_Metric.setVisibility(INVISIBLE);
        textView_Mask_Imperial.setVisibility(INVISIBLE);
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
            vSetSubTitleText(R.string.units);
        }

        {
            if(textView_Metric == null)         {   textView_Metric = new RtxFillerTextView(mContext); }
            if(textView_Imperial == null)       {   textView_Imperial = new RtxFillerTextView(mContext); }
            if(textView_Mask_Metric == null)    {   textView_Mask_Metric = new RtxFillerTextView(mContext); }
            if(textView_Mask_Imperial == null)  {   textView_Mask_Imperial = new RtxFillerTextView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        textView_Metric.setOnClickListener(mButtonListener);
        textView_Imperial.setOnClickListener(mButtonListener);

        textView_Metric.setOnTouchListener(mTouchListener);
        textView_Imperial.setOnTouchListener(mTouchListener);
    }

    private void add_View()
    {
        addRtxTextViewToLayout(textView_Metric, R.string.metric, 44.51f, Common.Color.login_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 206, 286, 307, 307, Common.Color.blue_1);
        addRtxTextViewToLayout(textView_Imperial, R.string.imperial, 44.51f, Common.Color.login_word_dark_black, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 764, 286, 307, 307, Common.Color.yellow_3);

        addRtxTextViewToLayout(textView_Mask_Metric, -1, 44.51f, Common.Color.login_word_dark_black, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 206, 286, 307, 307, Common.Color.mask_color);
        addRtxTextViewToLayout(textView_Mask_Imperial, -1, 44.51f, Common.Color.login_word_dark_black, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 764, 286, 307, 307, Common.Color.mask_color);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickBack()
    {
        mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_NAME);
    }

    private void vClickMetric()
    {
        EngSetting.setTempUnit(EngSetting.UNIT_METRIC);
        GlobalData.global_RegData.iUnit = 0;
        mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_GENDER);
    }

    private void vClickImperial()
    {
        EngSetting.setTempUnit(EngSetting.UNIT_IMPERIAL);
        GlobalData.global_RegData.iUnit = 1;
        mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_GENDER);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)  { vClickBack(); }
            else if(v == textView_Metric)   { vClickMetric(); }
            else if(v == textView_Imperial) { vClickImperial(); }
        }
    }

    class TouchListener implements OnTouchListener
    {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            View view_mask = null;

            if(v == textView_Metric)
            {
                view_mask = textView_Mask_Metric;
            }
            else if(v == textView_Imperial)
            {
                view_mask = textView_Mask_Imperial;
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
