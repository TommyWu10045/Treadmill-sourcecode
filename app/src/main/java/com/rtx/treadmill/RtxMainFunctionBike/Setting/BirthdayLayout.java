package com.rtx.treadmill.RtxMainFunctionBike.Setting;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_NumberWheel_Birthday_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.Calendar;

/**
 * Created by chasechang on 3/22/17.
 */

public class BirthdayLayout extends Rtx_NumberWheel_Birthday_BaseLayout {

    private Context mContext;
    private MainActivity   mMainActivity;

    private RtxTextView         t_month;
    private RtxTextView         t_day;
    private RtxTextView         t_year;

    public BirthdayLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;
        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void init_CustomerView()
    {
        if(t_month == null) {t_month = new RtxTextView(mContext);}
        if(t_day == null) {t_day = new RtxTextView(mContext);}
        if(t_year == null) {t_year = new RtxTextView(mContext);}
    }

    @Override
    protected void add_CustomerView()
    {
        String sdata;
        float fsize = 30f;

        Calendar cBirthDay = Rtx_Calendar.cStr2Calendar(CloudDataStruct.CloudData_17.Get_output(Cloud_17_GET_USR_BSC.Output.USR_BIR), "yyyy-MM-dd");

        setCalendar(cBirthDay);

        int iResID = R.xml.comfirm_tick;
        imageView_Next.setImageResource(iResID);

        vSetTitleText(R.string.date_of_birth);
        vSetSubTitleText("");

        //sdata = LanguageData.s_get_string(mContext, R.string.month);
        //addRtxTextViewToLayout(t_month, sdata.toLowerCase(), fsize, Common.Color.login_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 124,610,298,40);
        //sdata = LanguageData.s_get_string(mContext, R.string.day);
        //addRtxTextViewToLayout(t_day, sdata.toLowerCase(), fsize, Common.Color.login_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 441,610,298,40);
        //sdata = LanguageData.s_get_string(mContext, R.string.year);
        //addRtxTextViewToLayout(t_year, sdata.toLowerCase(), fsize, Common.Color.login_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, 808,610,298,40);

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void vClickButton_Back()
    {
        mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_PROFILE);
    }

    @Override
    public void vClickButton_Confirm()
    {
        Calendar cBirthDay = getCalendar();

        CloudDataStruct.CloudData_17.Set_output(Cloud_17_GET_USR_BSC.Output.USR_BIR, Rtx_Calendar.sCalendar2Str(cBirthDay, "yyyy-MM-dd"));
        mMainActivity.mMainProcBike.settingProc.bupdate_info = true;
        mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_PROFILE);
    }
}
