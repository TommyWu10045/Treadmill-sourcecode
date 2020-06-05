package com.rtx.treadmill.RtxMainFunctionBike.Setting;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.GlobalData;
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

public class NameLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    RtxEditText editText_Name;
    RtxImageView imageView_Next;
    RtxTextView textView_ErrorMsg;

    public NameLayout(Context context, MainActivity mMainActivity) {
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
        editText_Name.requestFocus();
        Rtx_Keyboard.openSoftKeybord(this,mContext);

        setName(GlobalData.global_RegData.sName);
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
            vSetTitleText(R.string.username);
        }

        {
            if(editText_Name == null)       {   editText_Name = new RtxEditText(mContext); }
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
        addRtxEditViewToLayout(editText_Name, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD,214,207,852,107);
        addRtxImageViewToLayout(imageView_Next, R.xml.comfirm_tick, 1101, 631, 134, 134);
        addRtxTextViewToLayout(textView_ErrorMsg, 24.73f, Common.Color.login_word_pink, Common.Font.Relay_Medium, Typeface.NORMAL, Gravity.CENTER, 0, 346, 1280, 74);
    }

    private void setEditViewRule()
    {
        editText_Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int iStrLen = editText_Name.getText().toString().length();

                if(iStrLen > 0)
                {
                    textView_ErrorMsg.setText("");
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
        imageView_Next.setEnabled(bFlag);
    }

    protected void setErrorMsg(String sError)
    {
        textView_ErrorMsg.setText(sError);
    }

    protected void setName(String sPassword)
    {
        if(editText_Name != null)
        {
            if(sPassword == null)
            {
                editText_Name.setText("");
            }
            else
            {
                editText_Name.setText(sPassword);
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickBack()
    {
        Rtx_Keyboard.closeSoftKeybord(this,mContext);
        mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_PROFILE);
    }

    private void vClickNext()
    {
        CloudDataStruct.CloudData_17.Set_output(Cloud_17_GET_USR_BSC.Output.USR_NAM, editText_Name.getText().toString());
        mMainActivity.mMainProcBike.settingProc.bupdate_info = true;
        mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_PROFILE);
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
