package com.rtx.treadmill.RtxMainFunction.Login;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Keyboard;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class RegPolicyLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    private RtxTextView textView_Agree;
    private RtxTextView textView_ViewPolicy;
    private RtxTextView textView_Exit;
    private RtxImageView imageView_ViewPolocyFrame;

    public RegPolicyLayout(Context context, MainActivity mMainActivity) {
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
        {
            init_BackPrePage();

            init_Title();
            vSetTitleText(R.string.circlecloud_go_sign_up);
            init_SubTitle();
            vSetSubTitleText(R.string.privacy_policy);
        }

        {
            if(textView_Agree == null)              { textView_Agree = new RtxTextView(mContext); }
            if(textView_ViewPolicy == null)         { textView_ViewPolicy = new RtxTextView(mContext); }
            if(textView_Exit == null)               { textView_Exit = new RtxTextView(mContext); }
            if(imageView_ViewPolocyFrame == null)   { imageView_ViewPolocyFrame = new RtxImageView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        textView_Agree.setOnClickListener(mButtonListener);
        imageView_ViewPolocyFrame.setOnClickListener(mButtonListener);
        textView_ViewPolicy.setOnClickListener(mButtonListener);
        textView_Exit.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxTextViewToLayout(textView_Agree, R.string.i_agree, 44.51f, Common.Color.black_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 110, 328, 1060, 141);
        textView_Agree.setBackgroundColor(Common.Color.yellow_1);
        addRtxImageViewToLayout(imageView_ViewPolocyFrame, R.drawable.reg_view_policy_frame, 394, 525, 485, 83);
        addRtxTextViewToLayout(textView_ViewPolicy, R.string.view_privacy_policy, 29.68f, Common.Color.blue_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 424, 525, 395, 83);
        addRtxTextViewToLayout(textView_Exit, R.string.exit, 29.68f, Common.Color.login_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 582, 702, 112, 62);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickBack()
    {
        Rtx_Keyboard.closeSoftKeybord(this,mContext);
        mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_BIRTHDAY);
    }

    private void vClickAgree()
    {
        mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_CLOUD_REG);
    }

    private void vClickView()
    {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_TITLE_INFO,R.string.privacy_policy,R.string.privacy_policy_contents);
    }

    private void vClickExit()
    {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_REG_EXIT,-1,-1);

        mMainActivity.dialogLayout_RegExit.fillerTextView_Exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.mMainProcTreadmill.loginProc.vMainChangePage(MainState.PROC_HOME);
                mMainActivity.dismissInfoDialog();
            }
        });



    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)          { vClickBack(); }
            else if(v == textView_Agree)            { vClickAgree(); }
            else if(v == imageView_ViewPolocyFrame) { vClickView(); }
            else if(v == textView_ViewPolicy)       { vClickView(); }
            else if(v == textView_Exit)             { vClickExit(); }
        }
    }
}
