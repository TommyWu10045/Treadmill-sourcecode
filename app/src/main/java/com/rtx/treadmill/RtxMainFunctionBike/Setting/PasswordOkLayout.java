package com.rtx.treadmill.RtxMainFunctionBike.Setting;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class PasswordOkLayout extends Rtx_BaseLayout {

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    RtxImageView i_ok;
    RtxTextView t_Msg;
    RtxFillerTextView fillerTextView_ok;

    public PasswordOkLayout(Context context, MainActivity mMainActivity) {
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

        if(i_ok == null)      {   i_ok = new RtxImageView(mContext); }
        if(t_Msg == null)   {   t_Msg = new RtxTextView(mContext); }
        if(fillerTextView_ok == null)        { fillerTextView_ok = new RtxFillerTextView(mContext);   }

    }

    private void init_event()
    {
        fillerTextView_ok.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        String sdata;

        addRtxImagePaddingViewToLayout(i_ok, R.drawable.setting_pw_tick, 445, 111, 378, 378, 0);
        sdata = LanguageData.s_get_string(mContext,R.string.password_changed);
        addRtxTextViewToLayout(t_Msg, sdata.toUpperCase(), 34.69f, Common.Color.green_1, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 140, 560, 1000, 50);
        addRtxTextViewToLayout(fillerTextView_ok, R.string.ok, 22.22f, Common.Color.setting_word_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, 494, 673, 284, 61, Common.Color.setting_word_yellow);

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickBack()
    {
        mMainActivity.mMainProcBike.settingProc.vSetNextState(SettingState.PROC_CLOUD_45_SET);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == fillerTextView_ok)  { vClickBack(); }
        }
    }
}
