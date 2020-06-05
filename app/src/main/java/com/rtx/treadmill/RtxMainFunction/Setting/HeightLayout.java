package com.rtx.treadmill.RtxMainFunction.Setting;

import android.content.Context;

import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_NumKeyboard_Height_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;

/**
 * Created by chasechang on 3/22/17.
 */

public class HeightLayout extends Rtx_NumKeyboard_Height_BaseLayout {

    private Context mContext;
    private MainActivity   mMainActivity;

    public HeightLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void add_CustomerView()
    {
        float fval;

        vSetTitleText(R.string.height);
        vSetSubTitleText("");

        int iResID = R.xml.comfirm_tick;
        imageView_Next.setImageResource(iResID);

        fval = Rtx_TranslateValue.fString2Float(CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_HIG]);
        if(fval <= 0)
        {
            clearNumKeyboard();
        }
        else
        {
            setCurrentVal_Metric(fval);
        }
    }

    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_SHOW_PROFILE);
    }

    @Override
    public void vClickButton_Confirm()
    {
        float fval = getCurrentVal_Metric();

        CloudDataStruct.CloudData_17.Set_output(Cloud_17_GET_USR_BSC.Output.USR_HIG, Rtx_TranslateValue.sFloat2String(fval,5));
        mMainActivity.mMainProcTreadmill.settingProc.bupdate_info = true;
        mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_SHOW_PROFILE);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
