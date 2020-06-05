package com.rtx.treadmill.Dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class CloudUploadFail02 extends Rtx_BaseLayout {

    private     Context mContext;
    private     MainActivity        mMainActivity;
    private ButtonListener mButtonListener;

    private FrameLayout f_back;
    private RtxImageView        i_Title;
    private RtxTextView         t_Info1;
    private RtxTextView         t_Info2;
    private RtxFillerTextView   f_close;
    private RtxFillerTextView   f_retry;

    private boolean bServerResponseFlag = false;
    private int     iServerResponse = -1;

    public CloudUploadFail02(Context context, MainActivity mMainActivity) {
        super(context);

        this.mContext = context;
        this.mMainActivity = mMainActivity;

        setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LOW_PROFILE |
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        setBackgroundColor(Common.Color.dialog_background);

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
        if(f_back == null)                      { f_back = new FrameLayout(mContext);   }
        if(i_Title == null)             { i_Title = new RtxImageView(mContext);   }
        if(t_Info1 == null)          { t_Info1 = new RtxTextView(mContext);   }
        if(t_Info2 == null)          { t_Info2 = new RtxTextView(mContext);   }
        if(f_close == null)     { f_close = new RtxFillerTextView(mContext);     }
        if(f_retry == null)     { f_retry = new RtxFillerTextView(mContext);     }

    }

    private void init_event()
    {
        f_close.setOnClickListener(mButtonListener);
        f_retry.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        int ix, iy, iw, ih;
        float fsize;
        int ipadding = 30;
        String sdata;

        ix = 140 ;
        iy = 45;
        iw = 1000 ;
        ih = 710;
        addViewToLayout(f_back,ix, iy, iw, ih);
        f_back.setBackgroundColor(Common.Color.background_dialog);

        ix = 410 ;
        iy = 100;
        iw = 180 ;
        ih = 180;
        addRtxImage(f_back, i_Title, R.drawable.wifi_disconect_icon, ix, iy, iw, ih, 0, null);

        ix = 100 ;
        iy += 255;
        iw = 800 ;
        ih = 80;
        fsize = 29.68f;
        sdata = "";
        addRtxTextView(f_back, t_Info1, sdata, fsize, Common.Color.info_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 80;
        sdata = "";
        addRtxTextView(f_back, t_Info2, sdata, fsize, Common.Color.info_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 360 ;
        iy += 150;
        iw = 278 ;
        ih = 60;
        fsize = 29.68f;
        sdata = "ok";
        addRtxTextView(f_back, f_close, sdata.toUpperCase(), fsize, Common.Color.background_dialog, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        f_close.setBackgroundColor(Common.Color.info_word_pink);

        ix += 360 ;
        sdata = "Retry";
        addRtxTextView(f_back, f_retry, sdata, fsize, Common.Color.background_dialog, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        f_retry.setBackgroundColor(Common.Color.info_word_green);
        f_retry.setVisibility(INVISIBLE);

        v_update_data();

        if(ExerciseData.b_is_exercising()) {
            f_back.setScaleX(0.82f);
            f_back.setScaleY(0.82f);
        }
        else
        {
            f_back.setScaleX(1f);
            f_back.setScaleY(1f);
        }

    }

    private void v_update_data()
    {
        v_update_data(Dialog_UI_Info.i_title01_id, Dialog_UI_Info.s_title01, Dialog_UI_Info.s_info01);
    }

    private void v_update_data(int ititle, String stitle01, String sinfo01)
    {
        if(ititle != -1) {
            i_Title.setImageResource(ititle);
        }

        if(stitle01 != null) {
            t_Info1.setText(stitle01.toUpperCase());
        }

        if(sinfo01 != null) {
            t_Info2.setText(sinfo01);
        }

    }


    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    private void vClickClose()
    {
        Dialog_UI_Info.vDialogUiInfo_SetWifiState(0);

        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_EXIT);
    }

    private void vClickRetry()
    {
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_DIALOG_CLOUD_RESEND);
    }

    /////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == f_close)      { vClickClose(); }
            else if(v == f_retry)      { vClickRetry(); }
        }
    }


}
