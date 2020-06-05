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
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class ForgotPasswordLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;
    private RtxTextView    textView_Describe;
    protected RtxFillerTextView fillerTextView_Send;
    protected RtxEditText editText_Email;
    protected RtxTextView textView_ErrorMsg;

    public ForgotPasswordLayout(Context context, MainActivity mMainActivity) {
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
        setEditViewRule();
        editText_Email.requestFocus();
        Rtx_Keyboard.openSoftKeybord(this,mContext);
        setEmail(null);
        textView_ErrorMsg.setText("");
        setSendButtonEnable(false);
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
            vSetTitleText(R.string.forgot_password);
        }

        {
            if(textView_Describe == null)       { textView_Describe = new RtxTextView(mContext);   }
            if(editText_Email == null)          { editText_Email = new RtxEditText(mContext);     }
            if(fillerTextView_Send == null)     { fillerTextView_Send = new RtxFillerTextView(mContext);}
            if(textView_ErrorMsg == null)       { textView_ErrorMsg = new RtxTextView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        fillerTextView_Send.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxTextViewToLayout(textView_Describe, R.string.enter_your_account, 24.73f, Common.Color.login_word_white, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.CENTER, 0,136,1280,42);
        addRtxEditViewToLayout(editText_Email, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,214,207,852,107);
        editText_Email.setSingleLine(true);
        addRtxTextViewToLayout(fillerTextView_Send, R.string.send, 20.77f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 500, 356, 280, 55, Common.Color.login_button_yellow);
        addRtxTextViewToLayout(textView_ErrorMsg, 24.73f, Common.Color.login_word_pink, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, 814, 330, 400, 100);
    }

    protected void setErrorMsg(String sError)
    {
        textView_ErrorMsg.setText(sError);
    }

    private boolean bCheckEmailFormat()
    {
        boolean bRet = false;

        String str = editText_Email.getText().toString();
        int searchLoc = str.indexOf("@");

        if((searchLoc > 0) && (str.length() > (searchLoc)))
        {
            textView_ErrorMsg.setText("");
            bRet = true;
        }
        else
        {
            textView_ErrorMsg.setText(LanguageData.s_get_string(mContext,R.string.not_a_valid_email_address));
            bRet = false;
        }

        return bRet;
    }

    private void setEditViewRule()
    {
        editText_Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = editText_Email.getText().toString();

                if((str!= null) && (str.length() > 0))
                {
                    setSendButtonEnable(true);
                    textView_ErrorMsg.setText("");
                }
                else
                {
                    setSendButtonEnable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setSendButtonEnable(boolean bFlag)
    {
        if(bFlag)
        {
            fillerTextView_Send.setBackgroundColor(Common.Color.login_button_yellow);
        }
        else
        {
            fillerTextView_Send.setBackgroundColor(Common.Color.login_button_yellow_disable);
        }

        fillerTextView_Send.setEnabled(bFlag);
    }

    protected void setEmail(String sEmail)
    {
        if(editText_Email != null)
        {
            if(sEmail == null)
            {
                editText_Email.setText("");
            }
            else
            {
                editText_Email.setText(sEmail);
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickBack()
    {
        Rtx_Keyboard.closeSoftKeybord(this,mContext);
        {
            editText_Email.setText("");
        }
        mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_LOGIN);
    }

    private void vClickSend()
    {
        if(bCheckEmailFormat())
        {
            mMainActivity.mMainProcTreadmill.loginProc.sInput_ForgotPasswordEmail = editText_Email.getText().toString();
            mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_CLOUD_SEND_EMAIL);
        }
        else
        {
            setEmail(null);
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)      { vClickBack(); }
            else if(v == fillerTextView_Send)   { vClickSend(); }

        }
    }
}
