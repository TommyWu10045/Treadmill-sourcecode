package com.rtx.treadmill.RtxMainFunctionBike.Setting;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;

/**
 * Created by chasechang on 3/22/17.
 */

public class SettingLayout extends Rtx_BaseLayout {

    private Context mContext;

    protected RtxFillerTextView fillerTextView_Login;
    protected RtxFillerTextView fillerTextView_SignUp;
    protected RtxTextView       textView_ForgotPassword;
    private   RtxView           view_ExplainBackground;

    private   RtxImageView[]      i_setting;
    private   RtxTextView[]       t_setting;

    private int istr_list[][] = {
            //Modify by Steven Chen 20200320
            //{R.drawable.setting_profile,       R.drawable.setting_chang_pw,       R.drawable.setting_data_sync},
            //{R.string.profile,       R.string.change_password,       R.string.data_syncing}
            {R.drawable.setting_profile,       R.drawable.setting_chang_pw},
            {R.string.profile,       R.string.change_password}
    };

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    public SettingLayout(Context context, MainActivity mMainActivity) {
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
        int imax = istr_list[0].length;
        int iLoop;

        init_Title();
        init_BackHome();

        if(i_setting == null) {
            i_setting = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                i_setting[iLoop] = new RtxImageView(mContext);
            }
        }

        if(t_setting == null) {
            t_setting = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_setting[iLoop] = new RtxTextView(mContext);
            }
        }

    }

    private void init_event()
    {
        int imax = istr_list[0].length;
        int iLoop;

        imageView_BackHome.setOnClickListener(mButtonListener);

        for (iLoop = 0; iLoop < imax; iLoop++) {
            if(i_setting != null) {
                i_setting[iLoop].setOnClickListener(mButtonListener);
            }
        }
    }

    private void add_View()
    {
        int iLoop;
        int imax = istr_list[0].length;
        int ix, iy, iw, ih;
        float fsize;
        String sdata;
        int iy_shift ;

        //        menu
        vSetTitleText(R.string.settings);

//        icon back
        ix = 290;
        iy = 190;
        iw = 700;
        ih = 128;
        iy_shift = 178 ;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            addRtxImagePaddingViewToLayout(i_setting[iLoop], istr_list[0][iLoop], ix, iy, iw, ih, 0);
            iy += iy_shift;
        }

//        icon text
        ix += 145;
        iy = 190;
        iw = 700-145;
        ih = 128;
        fsize = 30.9f;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            sdata = LanguageData.s_get_string(mContext, istr_list[1][iLoop]);
            addRtxTextViewToLayout(t_setting[iLoop], sdata.toUpperCase(), fsize, Common.Color.setting_word_black, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);
            iy += iy_shift;
            t_setting[iLoop].setEnabled(false);

        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vBackHome()
    {
        mMainActivity.mMainProcBike.settingProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vClickitem(int iLoop)
    {
        switch (iLoop)
        {
            case 0:
                mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_PROFILE);
                break;
            case 1:
                mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_CHANGEPASSWORD00);
                break;
            case 2:
                mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_SHOW_DATASYNC);
                break;
            default:
                break;
        }

    }


    class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackHome)          { vBackHome(); }
            else{
                int iLoop;
                int imax = istr_list[0].length;
                for(iLoop = 0; iLoop < imax; iLoop++)
                {
                    if(v == i_setting[iLoop])
                    {
                        vClickitem(iLoop);
                        break;
                    }
                }
            }
        }
    }
}
