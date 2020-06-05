package com.rtx.treadmill.RtxMainFunction.Setting;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;

/**
 * Created by chasechang on 3/22/17.
 */

public class PasswordGarminLayout extends PasswordLayout {

    private Context mContext;

    private MainActivity   mMainActivity;

    public PasswordGarminLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;
    }

    @Override
    public void add_CustomerView()
    {
        int iResID = R.xml.comfirm_tick;
        imageView_Next.setImageResource(iResID);

        vSetTitleText(R.string.garmin_connect);

        String sdata = LanguageData.s_get_string(mContext,R.string.password);
        vSetSubTitleText(sdata.toUpperCase());

        editText_Password.setText("");
        mMainActivity.mMainProcTreadmill.settingProc.spassword = "";

    }

    @Override
    public void setEditViewRule()
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

                if(iStrLen >= 1)
                {
                    imageView_Next.setEnabled(true);
                }
                else
                {
                    imageView_Next.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void vClickBack()
    {
        mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_SHOW_DATASYNC_GARMIN);
    }

    @Override
    public void vClickNext()
    {
        String sdata = editText_Password.getText().toString();
        mMainActivity.mMainProcTreadmill.settingProc.vSet_trd_pw(sdata);

        mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_CLOUD_43_SET);

    }

}
