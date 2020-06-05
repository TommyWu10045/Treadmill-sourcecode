package com.rtx.treadmill.RtxMainFunctionBike.Login;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.CloudDataStruct;
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

public class LoginEmailLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;
    private RtxEditText editText_Email;
    private RtxImageView imageView_Next;
    private RtxTextView textView_Error;

    public LoginEmailLayout(Context context, MainActivity mMainActivity) {
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
        editText_Email.requestFocus();
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
            vSetSubTitleText(R.string.email);
        }

        {
            if(editText_Email == null)  {   editText_Email = new RtxEditText(mContext); }
            if(imageView_Next == null)  {   imageView_Next = new RtxImageView(mContext); }
            if(textView_Error == null)  {   textView_Error = new RtxTextView(mContext); }
        }
    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        imageView_Next.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        addRtxEditViewToLayout(editText_Email, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,214,207,852,107);
        editText_Email.setSingleLine(true);
        addRtxImageViewToLayout(imageView_Next, R.drawable.next_small_button_enable, 1118, 207, 107, 107);

        addRtxTextViewToLayout(textView_Error, 24.73f, Common.Color.login_word_pink, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.CENTER, -1, 337, 440, 96);
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

                if(str.length() > 0)
                {
                    setNextButtonEnable(true);
                    textView_Error.setText("");
                }
                else
                {
                    setNextButtonEnable(false);
                    textView_Error.setText("");
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
            textView_Error.setText("");
            mMainActivity.mMainProcBike.loginProc.sInput_Name = null;
            mMainActivity.mMainProcBike.loginProc.sInput_Password = null;
        }
        mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_LOGIN);
    }

    private void vClickNext()
    {
        String str = editText_Email.getText().toString();
        int searchLoc = str.indexOf("@");

        if((searchLoc > 0) && (str.length() > (searchLoc)))
        {
            Rtx_Keyboard.closeSoftKeybord(this,mContext);
            {
                mMainActivity.mMainProcBike.loginProc.sInput_Name = editText_Email.getText().toString();
            }
            mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_LOGIN_PASSWORD);
        }
        else
        {
            textView_Error.setText(LanguageData.s_get_string(mContext,R.string.not_a_valid_email_address));
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
