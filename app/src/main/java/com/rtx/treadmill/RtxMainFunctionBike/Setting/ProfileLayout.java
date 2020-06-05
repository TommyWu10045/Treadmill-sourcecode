package com.rtx.treadmill.RtxMainFunctionBike.Setting;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.retonix.circlecloud.Cloud_17_GET_USR_BSC;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.GlobalData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class ProfileLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    RtxImageView i_name_edit;
    RtxImageView i_unit;
    RtxImageView i_gender;
    RtxImageView i_height_edit;
    RtxImageView i_birth_edit;

    RtxTextView[] t_show;

    RtxTextView t_name;
    RtxTextView t_height;
    RtxTextView t_birth;

    RtxTextView t_metric;
    RtxTextView t_imperial;
    RtxTextView t_male;
    RtxTextView t_female;

    private int istr_list[] = {
            R.string.user_name,       R.string.unit,       R.string.gender,       R.string.height,       R.string.date_of_birth
    };

    public ProfileLayout(Context context, MainActivity mMainActivity) {
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
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void init_View()
    {
        int imax = istr_list.length;
        int iLoop;

        init_BackPrePage();
        init_Title();

        if(i_name_edit == null)  {   i_name_edit = new RtxImageView(mContext); }
        if(i_unit == null)  {   i_unit = new RtxImageView(mContext); }
        if(i_gender == null)  {   i_gender = new RtxImageView(mContext); }
        if(i_height_edit == null)  {   i_height_edit = new RtxImageView(mContext); }
        if(i_birth_edit == null)  {   i_birth_edit = new RtxImageView(mContext); }

        if(t_show == null) {
            t_show = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_show[iLoop] = new RtxTextView(mContext);
            }
        }

        if(t_name == null)  {   t_name = new RtxTextView(mContext); }
        if(t_height == null)  {   t_height = new RtxTextView(mContext); }
        if(t_birth == null)  {   t_birth = new RtxTextView(mContext); }

        if(t_metric == null)  {   t_metric = new RtxTextView(mContext); }
        if(t_imperial == null)  {   t_imperial = new RtxTextView(mContext); }
        if(t_male == null)  {   t_male = new RtxTextView(mContext); }
        if(t_female == null)  {   t_female = new RtxTextView(mContext); }


    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        i_name_edit.setOnClickListener(mButtonListener);
        i_height_edit.setOnClickListener(mButtonListener);
        i_birth_edit.setOnClickListener(mButtonListener);
        t_metric.setOnClickListener(mButtonListener);
        t_imperial.setOnClickListener(mButtonListener);
        t_male.setOnClickListener(mButtonListener);
        t_female.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        int iLoop;
        int imax = istr_list.length;
        int ix, iy, iw, ih;
        float fsize;
        String sdata;
        int iy_shift ;
        int ipadding = 30;

        //        menu
        sdata = LanguageData.s_get_string(mContext, R.string.profile);
        vSetTitleText(sdata.toUpperCase());

        ix = 100;
        iy = 185;
        iw = 38;
        ih = 38;
        addRtxImagePaddingViewToLayout(i_name_edit, R.drawable.setting_edit, ix, iy, iw, ih, ipadding);

        iy += 285;
        addRtxImagePaddingViewToLayout(i_height_edit, R.drawable.setting_edit, ix, iy, iw, ih, ipadding);

        iy += 85;
        addRtxImagePaddingViewToLayout(i_birth_edit, R.drawable.setting_edit, ix, iy, iw, ih, ipadding);

//        text
        ix = 493;
        iy = 185;
        iw = 800;
        ih = 38;
        fsize = 30.9f;
        sdata = CloudDataStruct.CloudData_17.Get_output(Cloud_17_GET_USR_BSC.Output.USR_NAM);
        addRtxTextViewToLayout(t_name, sdata, fsize, Common.Color.setting_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);
        GlobalData.global_RegData.sName = CloudDataStruct.CloudData_17.Get_output(Cloud_17_GET_USR_BSC.Output.USR_NAM);

        iy += 285;
        addRtxTextViewToLayout(t_height, sdata, fsize, Common.Color.setting_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        iy += 85;
        String sCloudBirthday;
        sCloudBirthday = CloudDataStruct.CloudData_17.Get_output(Cloud_17_GET_USR_BSC.Output.USR_BIR);
        sdata = Rtx_Calendar.sParseDate2FormatStr(sCloudBirthday,"yyyy-MM-dd","M/dd/yyyy");
        addRtxTextViewToLayout(t_birth, sdata, fsize, Common.Color.setting_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

//      Item name
        ix = 173;
        iy = 185;
        iw = 300;
        ih = 38;
        iy_shift = 85 ;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            sdata = LanguageData.s_get_string(mContext, istr_list[iLoop]);
            addRtxTextViewToLayout(t_show[iLoop], sdata.toUpperCase(), fsize, Common.Color.setting_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);
            if(iLoop == 0 || iLoop == 2)
            {
                iy += 100;
            }
            else
            {
                iy += iy_shift;
            }

        }

//        unit and gender
        ix = 447;
        iy = 262;//By Alan
        iw = 415;//By Alan
        ih = 75;//By Alan
        addRtxImagePaddingViewToLayout(i_unit, R.drawable.switch_left, ix, iy, iw, ih, ipadding);

        iy += 85;
        addRtxImagePaddingViewToLayout(i_gender, R.drawable.switch_left, ix, iy, iw, ih, ipadding);

        ix = 447;
        iy = 262;//By Alan
        iw = 416/2;
        ih = 70;//By Alan
        float tmpfsize=24.0f;
        sdata = LanguageData.s_get_string(mContext, R.string.metric);
        addRtxTextViewToLayout(t_metric, sdata.toUpperCase(), tmpfsize, Common.Color.setting_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        ix += iw;
        sdata = LanguageData.s_get_string(mContext, R.string.imperial);
        addRtxTextViewToLayout(t_imperial, sdata.toUpperCase(), tmpfsize, Common.Color.setting_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 447;
        iy += 85;
        sdata = LanguageData.s_get_string(mContext, R.string.male);
        addRtxTextViewToLayout(t_male, sdata.toUpperCase(), tmpfsize, Common.Color.setting_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        ix += iw;
        sdata = LanguageData.s_get_string(mContext, R.string.female);
        addRtxTextViewToLayout(t_female, sdata.toUpperCase(), tmpfsize, Common.Color.setting_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        v_check_unit();

        v_check_gender();

    }

    private void v_check_unit()
    {
        String sdata;
        float fval ;

        fval = CloudDataStruct.CloudData_17.f_get_user_height();
        sdata = CloudDataStruct.CloudData_17.Get_output(Cloud_17_GET_USR_BSC.Output.WEI_UNT);

        if(sdata.toLowerCase().compareTo("m") == 0)
        {
            i_unit.setImageResource(R.drawable.switch_left);
            t_metric.setTextColor(Common.Color.setting_word_white);
            t_imperial.setTextColor(Common.Color.setting_word_blue);
        }
        else
        {
            i_unit.setImageResource(R.drawable.switch_right);
            t_metric.setTextColor(Common.Color.setting_word_blue);
            t_imperial.setTextColor(Common.Color.setting_word_white);
        }

        GlobalData.Weather.bUpdate = true;

        sdata = EngSetting.Height.getString(fval);
        t_height.setText(sdata);

    }

    private void v_check_gender()
    {
        String sdata = CloudDataStruct.CloudData_17.Get_output(Cloud_17_GET_USR_BSC.Output.USR_SEX);

        if(sdata.toLowerCase().compareTo("m") == 0)
        {
            i_gender.setImageResource(R.drawable.switch_left);
            t_male.setTextColor(Common.Color.setting_word_white);
            t_female.setTextColor(Common.Color.setting_word_blue);
        }
        else
        {
            i_gender.setImageResource(R.drawable.switch_right);
            t_male.setTextColor(Common.Color.setting_word_blue);
            t_female.setTextColor(Common.Color.setting_word_white);
        }

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickBack()
    {
        mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_CLOUD_37_SET);
    }

    private void vClickName()
    {
        mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_PROFILE_NAME);
    }

    private void vClickHeight()
    {
        mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_PROFILE_HEIGHT);
    }

    private void vClickBirth()
    {
        mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_PROFILE_BIRTH);
    }

    private void vClickMetric()
    {
        CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.WEI_UNT] = "M";
        v_check_unit();

        mMainActivity.mMainProcBike.settingProc.bupdate_info = true;

    }

    private void vClickImperial()
    {
        CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.WEI_UNT] = "E";
        v_check_unit();

        mMainActivity.mMainProcBike.settingProc.bupdate_info = true;

    }

    private void vClickMale()
    {
        CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_SEX] = "M";
        v_check_gender();

        mMainActivity.mMainProcBike.settingProc.bupdate_info = true;

    }

    private void vClickFemale() {
        CloudDataStruct.CloudData_17.sdata_out[Cloud_17_GET_USR_BSC.Output.USR_SEX] = "F";
        v_check_gender();

        mMainActivity.mMainProcBike.settingProc.bupdate_info = true;

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)  { vClickBack(); }
            else if(v == i_name_edit)    { vClickName(); }
            else if(v == i_height_edit)    { vClickHeight(); }
            else if(v == i_birth_edit)    { vClickBirth(); }
            else if(v == t_metric)    { vClickMetric(); }
            else if(v == t_imperial)    { vClickImperial(); }
            else if(v == t_male)    { vClickMale(); }
            else if(v == t_female)    { vClickFemale(); }

        }
    }
}
