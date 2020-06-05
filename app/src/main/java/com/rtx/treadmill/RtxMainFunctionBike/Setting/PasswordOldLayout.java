package com.rtx.treadmill.RtxMainFunctionBike.Setting;

import android.content.Context;

import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;

/**
 * Created by chasechang on 3/22/17.
 */

public class PasswordOldLayout extends PasswordLayout {

    private Context mContext;

    private MainActivity   mMainActivity;

    public PasswordOldLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;
    }

    @Override
    public void add_CustomerView()
    {
        int iResID = R.xml.comfirm_arrow_next;
        imageView_Next.setImageResource(iResID);

        String sdata = LanguageData.s_get_string(mContext,R.string.old_password);
        vSetSubTitleText(sdata.toUpperCase());

        editText_Password.setText("");

    }

    @Override
    public void vClickBack()
    {
        mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_SETTING);
    }

    @Override
    public void vClickNext()
    {
        String sdata = editText_Password.getText().toString();
        String soldpw = CloudDataStruct.CloudData_17.Get_output(Cloud_17_GET_USR_BSC.Output.USR_PW);
        if(sdata.compareTo(soldpw) == 0) {
            mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_CHANGEPASSWORD01);
        }
        else
        {
            sdata = LanguageData.s_get_string(mContext, R.string.old_password_incorrect);
            setErrorMsg(sdata);
        }
    }

}
