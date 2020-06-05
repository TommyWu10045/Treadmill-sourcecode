package com.rtx.treadmill.RtxMainFunction.Login;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
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

public class RegConfirmPasswordLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;
    private RtxEditText editText_Password;
    private RtxImageView imageView_Next;
    private RtxTextView textView_ErrorMsg;

    public RegConfirmPasswordLayout(Context context, MainActivity mMainActivity) {
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
        GlobalData.global_RegData.sConfirmPassword = null;
        setPassword(GlobalData.global_RegData.sConfirmPassword);
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
            vSetSubTitleText(R.string.confirm_password);
        }

        {
            if(editText_Password == null)   {   editText_Password = new RtxEditText(mContext); }
            if(imageView_Next == null)      {   imageView_Next = new RtxImageView(mContext); }
            if(textView_ErrorMsg == null)   {   textView_ErrorMsg = new RtxTextView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Next.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxEditViewToLayout(editText_Password, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD,214,207,852,107);
        addRtxImageViewToLayout(imageView_Next, R.drawable.next_small_button_enable, 1118, 207, 107, 107);
        addRtxTextViewToLayout(textView_ErrorMsg, 24.73f, Common.Color.login_word_pink, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.CENTER, 0, 346, 1280, 74);
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
                    setErrorMsg("");
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

    protected void setErrorMsg(String sError)
    {
        textView_ErrorMsg.setText(sError);
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
        mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_PASSWORD);
    }

    private void vClickNext()
    {
        if(GlobalData.global_RegData.sPassword.equals(editText_Password.getText().toString()))
        {
            GlobalData.global_RegData.sConfirmPassword = editText_Password.getText().toString();
            mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_NAME);
        }
        else
        {
            setErrorMsg(LanguageData.s_get_string(mContext,R.string.please_make_sure_your_passwords_match));
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)  { vClickBack(); }
            else if(v == imageView_Next)    { vClickNext(); }

        }
    }
}
