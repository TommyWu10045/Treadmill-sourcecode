package com.rtx.treadmill.RtxMainFunction.Login;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxBaseLayout.Rtx_NumKeyboard_Height_BaseLayout;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxNumKeyboardView_Height;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class RegHeightLayout extends Rtx_NumKeyboard_Height_BaseLayout {

    private Context mContext;
    private MainActivity   mMainActivity;

    public RegHeightLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void add_CustomerView()
    {
        vSetTitleText(R.string.circlecloud_go_sign_up);
        vSetSubTitleText(R.string.height);

        if(GlobalData.global_RegData.fHeight <= 0)
        {
            clearNumKeyboard();
        }
        else
        {
            setCurrentVal_Metric(GlobalData.global_RegData.fHeight);
        }
    }

    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_GENDER);
    }

    @Override
    public void vClickButton_Confirm()
    {
        float fVal = getCurrentVal_Metric();

        GlobalData.global_RegData.fHeight = fVal;
        mMainActivity.mMainProcTreadmill.loginProc.vSetNextState(LoginState.PROC_SHOW_PAGE_REG_BIRTHDAY);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
