package com.rtx.treadmill.RtxMainFunctionBike.Setting;

import android.content.Context;

import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;

/**
 * Created by chasechang on 3/22/17.
 */

public class PasswordNewLayout extends PasswordLayout {

    private Context mContext;

    private MainActivity   mMainActivity;

    public PasswordNewLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;
    }

    @Override
    public void add_CustomerView()
    {
        int iResID = R.xml.comfirm_arrow_next;
        imageView_Next.setImageResource(iResID);

        String sdata = LanguageData.s_get_string(mContext,R.string.new_password);
        vSetSubTitleText(sdata.toUpperCase());

        editText_Password.setText("");
        mMainActivity.mMainProcBike.settingProc.spassword = "";

    }

    @Override
    public void vClickBack()
    {
        mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_CHANGEPASSWORD00);
    }

    @Override
    public void vClickNext()
    {
        String sdata = editText_Password.getText().toString();
        mMainActivity.mMainProcBike.settingProc.spassword = sdata;

        mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_CHANGEPASSWORD02);

    }

}
