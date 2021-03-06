package com.rtx.treadmill.RtxMainFunctionBike.Login;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Keyboard;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class LoginPasswordLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;
    private RtxEditText editText_Password;
    private RtxImageView imageView_Next;
    private RtxTextView textView_ErrorMsg;
    private RtxTextView textView_ForgotPassword;

    public LoginPasswordLayout(Context context, MainActivity mMainActivity) {
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
        setNextButtonEnable(false);
        setEditViewRule();
        editText_Password.requestFocus();
        editText_Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        Rtx_Keyboard.openSoftKeybord(this,mContext);


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
            init_SubTitle();
            vSetSubTitleText(R.string.password);
        }

        {
            if(editText_Password == null)       {   editText_Password = new RtxEditText(mContext); }
            if(imageView_Next == null)          {   imageView_Next = new RtxImageView(mContext); }
            if(textView_ErrorMsg == null)       {   textView_ErrorMsg = new RtxTextView(mContext); }
            if(textView_ForgotPassword == null) {   textView_ForgotPassword = new RtxTextView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Next.setOnClickListener(mButtonListener);
        textView_ForgotPassword.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxEditViewToLayout(editText_Password, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD,214,207,852,107);
        addRtxImageViewToLayout(imageView_Next, R.drawable.next_small_button_enable, 1118, 207, 107, 107);
        addRtxTextViewToLayout(textView_ErrorMsg, 24.73f, Common.Color.login_word_pink, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, 830, 350, 400, 100);
        addRtxTextViewToLayout(textView_ForgotPassword, R.string.forgot_password ,20.77f, Common.Color.white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 500, 350, 280, 55);
    }

    private void setEditViewRule()
    {
        editText_Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int iStrLen = editText_Password.getText().toString().length();

                if(iStrLen > 0)
                {
                    textView_ErrorMsg.setText("");
                }

                if((iStrLen >= 6) && (iStrLen <= 10))
                {
                    setNextButtonEnable(true);
                }
                else
                {
                    setNextButtonEnable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setNextButtonEnable(boolean bFlag)
    {
        int resId = 0;

        if(bFlag)
        {
            resId = R.drawable.next_small_button_enable;
        }
        else
        {
            resId = R.drawable.next_small_button_disable;
        }
        imageView_Next.setImageResource(resId);
        imageView_Next.setEnabled(bFlag);
    }

    protected void setErrorMsg(int iResId)
    {
        if(textView_ErrorMsg != null)
        {
            textView_ErrorMsg.setText(LanguageData.s_get_string(mContext,iResId));
        }
    }

    protected void setPassword(String sPassword)
    {
        if(editText_Password != null)
        {
            if(sPassword == null)
            {
                editText_Password.setText("");
            }
            else
            {
                editText_Password.setText(sPassword);
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickBack()
    {
        Rtx_Keyboard.closeSoftKeybord(this,mContext);
        mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_LOGIN_EMAIL);
    }

    private void vClickNext()
    {
        mMainActivity.mMainProcBike.loginProc.sInput_Password = editText_Password.getText().toString();
        mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_CLOUD_LOGIN);
    }

    private void vClickForgotButton()
    {
        mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_FORGOT_PASSWORD);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)          { vClickBack(); }
            else if(v == imageView_Next)            { vClickNext(); }
            else if(v == textView_ForgotPassword)   { vClickForgotButton(); }
        }
    }
}
