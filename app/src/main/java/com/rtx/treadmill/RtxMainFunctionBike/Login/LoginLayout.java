package com.rtx.treadmill.RtxMainFunctionBike.Login;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Draw;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;

/**
 * Created by chasechang on 3/22/17.
 */

public class LoginLayout extends Rtx_BaseLayout {

    private Context mContext;

    protected RtxFillerTextView fillerTextView_Login;
    protected RtxFillerTextView fillerTextView_SignUp;
    protected RtxTextView       textView_ForgotPassword;
    private   RtxView           view_ExplainBackground;

    private   RtxImageView      imageView_CircleFit;
    private   RtxImageView      imageView_Code_Apple;
    private   RtxImageView      imageView_Code_Android;
    private   RtxImageView      imageView_Icon_Apple;
    private   RtxImageView      imageView_Icon_Android;
    private   RtxImageView      imageView_Explain_No1;
    private   RtxImageView      imageView_Explain_No2;
    private   RtxImageView      imageView_Explain_No3;

    private   RtxTextView       textView_QuickLogInWith;
    private   RtxTextView       textView_Explain_1;
    private   RtxTextView       textView_Explain_2;
    private   RtxTextView       textView_Explain_3;
    private   RtxTextView       textView_Note;
    private   RtxTextView       textView_NoteContents;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    public LoginLayout(Context context, MainActivity mMainActivity) {
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
            vSetTitleText(R.string.log_in);
        }

        {
            if(fillerTextView_Login == null)    {fillerTextView_Login = new RtxFillerTextView(mContext);}
            if(fillerTextView_SignUp == null)   {fillerTextView_SignUp = new RtxFillerTextView(mContext);}
            if(textView_ForgotPassword == null) {textView_ForgotPassword = new RtxTextView(mContext);}
            if(view_ExplainBackground == null)  {view_ExplainBackground = new RtxView(mContext);}
        }

        //ImageView
        {
            if(imageView_CircleFit == null)     {imageView_CircleFit = new RtxImageView(mContext);}
            if(imageView_Code_Apple == null)    {imageView_Code_Apple = new RtxImageView(mContext);}
            if(imageView_Code_Android == null)  {imageView_Code_Android = new RtxImageView(mContext);}
            if(imageView_Icon_Apple == null)    {imageView_Icon_Apple = new RtxImageView(mContext);}
            if(imageView_Icon_Android == null)  {imageView_Icon_Android = new RtxImageView(mContext);}
            if(imageView_Explain_No1 == null)   {imageView_Explain_No1 = new RtxImageView(mContext);}
            if(imageView_Explain_No2 == null)   {imageView_Explain_No2 = new RtxImageView(mContext);}
            if(imageView_Explain_No3 == null)   {imageView_Explain_No3 = new RtxImageView(mContext);}
        }

        //TextView
        {
            if(textView_QuickLogInWith == null) {textView_QuickLogInWith = new RtxTextView(mContext);}
            if(textView_Explain_1 == null)      {textView_Explain_1 = new RtxTextView(mContext);}
            if(textView_Explain_2 == null)      {textView_Explain_2 = new RtxTextView(mContext);}
            if(textView_Explain_3 == null)      {textView_Explain_3 = new RtxTextView(mContext);}
            if(textView_Note == null)           {textView_Note = new RtxTextView(mContext);}
            if(textView_NoteContents == null)   {textView_NoteContents = new RtxTextView(mContext);}
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        fillerTextView_Login.setOnClickListener(mButtonListener);
        fillerTextView_SignUp.setOnClickListener(mButtonListener);
        textView_ForgotPassword.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        int iWidth = -1;
        iWidth = Rtx_Draw.iGetTextWidth(mContext, LanguageData.s_get_string(mContext, R.string.quick_log_in_with),Common.Font.KatahdinRound,20.77f);
        if(iWidth > 600)
        {
            iWidth = 600;
        }

        {
            addRtxTextViewToLayout(fillerTextView_Login, R.string.log_in, 20.77f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 500, 149, 280, 55, Common.Color.login_button_yellow);
            addRtxTextViewToLayout(fillerTextView_SignUp, R.string.sign_up, 20.77f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 500, 240, 280, 55, Common.Color.login_button_green);
            addRtxTextViewToLayout(textView_ForgotPassword, R.string.forgot_password, 20.77f, Common.Color.login_word_white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 500, 331, 280, 55);
            addRtxViewToLayout(view_ExplainBackground,0,422,1280,378,Common.Color.white);
        }

        {
            addRtxImageViewToLayout(imageView_CircleFit, R.drawable.circle_fit_icon, 110 + iWidth - 15, 441, 53, 62);
            addRtxImageViewToLayout(imageView_Code_Apple, R.drawable.qr_code_apple, 864, 457, 147, 147);
            addRtxImageViewToLayout(imageView_Code_Android, R.drawable.qr_code_android, 1086, 457, 147, 147);
            addRtxImageViewToLayout(imageView_Icon_Apple, R.drawable.apple_store, 852, 629, 170, 56);
            addRtxImageViewToLayout(imageView_Icon_Android, R.drawable.google_play, 1063, 629, 192, 57);
            addRtxImageViewToLayout(imageView_Explain_No1, R.drawable.login_note_1, 111, 537, 34, 34);
            addRtxImageViewToLayout(imageView_Explain_No2, R.drawable.login_note_2, 111, 537+45, 34, 34);
            addRtxImageViewToLayout(imageView_Explain_No3, R.drawable.login_note_3, 111, 537+90, 34, 34);
        }

        {
            addRtxTextViewToLayout(textView_QuickLogInWith, R.string.quick_log_in_with, 20.77f, Common.Color.login_word_blue, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER_VERTICAL | Gravity.LEFT, 110, 461, 600, 28);

            addRtxTextViewToLayout(textView_Explain_1, R.string.login_expand_1, 24.73f, Common.Color.login_word_dark_black, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.CENTER_VERTICAL | Gravity.LEFT, 195, 535, 641, 34);//By Alan
            addRtxTextViewToLayout(textView_Explain_2, R.string.login_expand_2, 24.73f, Common.Color.login_word_dark_black, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.CENTER_VERTICAL | Gravity.LEFT, 195, 535+46, 641, 34);//By Alan
            addRtxTextViewToLayout(textView_Explain_3, R.string.login_expand_3, 24.73f, Common.Color.login_word_dark_black, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.TOP | Gravity.LEFT, 195, 535+92, 641, 68);//By Alan

            addRtxTextViewToLayout(textView_Note, R.string.note, 19.78f, Common.Color.login_word_green, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.TOP | Gravity.LEFT, 108, 713, 100, 46);//By Alan
            addRtxTextViewToLayout(textView_NoteContents, R.string.note_contents, 19.78f, Common.Color.login_word_blue, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.CENTER_VERTICAL | Gravity.LEFT, 198, 713, 984, 46);//By Alan
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickLoginButton()
    {
        mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_LOGIN_EMAIL);
    }

    private void vClickSignUpButton()
    {
        GlobalData.global_RegData.clear();
        mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_EMAIL);
    }

    private void vBackHome()
    {
        mMainActivity.mMainProcBike.loginProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vClickForgotButton()
    {
        mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_FORGOT_PASSWORD);
    }

    class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)          { vBackHome(); }
            else if(v == fillerTextView_Login)      { vClickLoginButton(); }
            else if(v == fillerTextView_SignUp)     { vClickSignUpButton(); }
            else if(v == textView_ForgotPassword)   { vClickForgotButton(); }
        }
    }
}
