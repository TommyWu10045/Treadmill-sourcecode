package com.rtx.treadmill.Dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;

/**
 * Created by chasechang on 3/22/17.
 */

public class BodyInfoLayoutE extends Rtx_BaseLayout {

    private Context mContext;
    private     MainActivity        mMainActivity;

    private ButtonListener      mButtonListener;

    private RtxView view_InfoBackground;

    private RtxTextView[]         t_data;
    private RtxImageView[]        i_data;

    public RtxFillerTextView fillerTextView_bdinfo;

    public BodyInfoLayoutE(Context context, MainActivity mMainActivity) {
        super(context);

        this.mMainActivity = mMainActivity;
        mContext = context;

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
        int iLoop ;
        int imax = 4;

        if(view_InfoBackground == null)         { view_InfoBackground = new RtxView(mContext);   }

        if(t_data == null) {
            t_data = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_data[iLoop] = new RtxTextView(mContext);
            }
        }

        imax = 2;
        if(i_data == null) {
            i_data = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                i_data[iLoop] = new RtxImageView(mContext);
            }
        }

        if(fillerTextView_bdinfo == null)         { fillerTextView_bdinfo = new RtxFillerTextView(mContext);   }

    }

    private void init_event()
    {
        fillerTextView_bdinfo.setOnClickListener(mButtonListener);
        view_InfoBackground.setOnClickListener(mButtonListener);
    }

    private void add_View() {
        int ix, iy, iw, ih;
        float fsize;

        ix = 185 ;
        iy = 60;
        iw = 900 ;
        ih = 525;
        addViewToLayout(view_InfoBackground,ix, iy, iw, ih);
        view_InfoBackground.setBackgroundColor(Common.Color.background_dialog);

        iy += 50;
        ih = 40;
        fsize = 21.5f;
        addRtxTextViewToLayout(t_data[0], R.string.bd_infomation_title, fsize, Common.Color.bd_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 40;
        ih = 40;
        addRtxTextViewToLayout(t_data[1], R.string.bd_infomation_content, fsize, Common.Color.bd_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 425;
        iy += 260;
        iw = 235;
        ih = 30;
        fsize = 19f;
        addRtxTextViewToLayout(t_data[2], R.string.circle_fitness_iba, fsize, Common.Color.bd_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += iw ;
        iw = 180;
        ih = 30;
        addRtxTextViewToLayout(t_data[3], R.string.inbody_570, fsize, Common.Color.bd_word_white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 500;
        iy = 223;
        iw = 86;
        ih = 180;
        addRtxImage(null, i_data[0], R.drawable.circle_iba_icon, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        ix = 695;
        iw = 103;
        ih = 175;
        addRtxImage(null, i_data[1], R.drawable.inbody_570_icon, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        ix = 470;
        iy = 470;
        iw = 343;
        ih = 68;
        addRtxTextViewToLayout(fillerTextView_bdinfo, R.string.ok, 28f, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih, Common.Color.bd_button_green);
        fillerTextView_bdinfo.setFillet(ih/2);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vClickClose()
    {
        Dialog_UI_Info.v_Set_Dialog_mode(DialogState.PROC_EXIT);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == fillerTextView_bdinfo)      { vClickClose(); }

        }
    }



}
