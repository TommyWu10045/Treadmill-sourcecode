package com.rtx.treadmill.RtxMainFunctionBike.Login;

import android.content.Context;

import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_NumberWheel_Birthday_BaseLayout;

/**
 * Created by chasechang on 3/22/17.
 */

public class RegBirthdayLayout extends Rtx_NumberWheel_Birthday_BaseLayout {

    private Context mContext;
    private MainActivity   mMainActivity;

    public RegBirthdayLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void add_CustomerView()
    {
        setCalendar(GlobalData.global_RegData.cBirthDay);

        vSetTitleText(R.string.circlecloud_go_sign_up);
        vSetSubTitleText(R.string.date_of_birth);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_HEIGHT);
    }

    @Override
    public void vClickButton_Confirm()
    {
        GlobalData.global_RegData.cBirthDay = getCalendar();
        mMainActivity.mMainProcBike.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_POLICY);
    }
}
