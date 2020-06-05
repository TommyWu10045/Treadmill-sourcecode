package com.rtx.treadmill.RtxMainFunction.Physical;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class GerkinLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry";

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity mMainActivity;

    private RtxImageView i_heart;

    private RtxTextView[] t_heart;
    private RtxTextView[] t_heartmax;

    private RtxFillerTextView[] f_step;
    private RtxTextView[][] t_step;
    private RtxImageView[][] i_step;

    private RtxTextView t_noheart;

    private int imax = 3;
    private int istep = 2;

    private String s_stage;

    private int icount = 0;

    public GerkinLayout(Context context, MainActivity mMainActivity) {
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
    public void init() {
        if (mButtonListener == null) {
            mButtonListener = new ButtonListener();
        }

        icount = 0;
    }

    @Override
    public void display() {
        init_View();
        init_event();
        add_View();
    }

    public void onDestroy() {
        removeAllViews();
        System.gc();
    }

    public void init_View() {
        int iLoop, iLoop1;

        init_Title();

        if (i_heart == null) {
            i_heart = new RtxImageView(mContext);
        }

        if (t_heart == null) {
            t_heart = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_heart[iLoop] = new RtxTextView(mContext);
            }
        }

        if (t_heartmax == null) {
            t_heartmax = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_heartmax[iLoop] = new RtxTextView(mContext);
            }
        }

        if (f_step == null) {
            f_step = new RtxFillerTextView[istep];
            for (iLoop = 0; iLoop < istep; iLoop++) {
                f_step[iLoop] = new RtxFillerTextView(mContext);
            }
        }

        if (t_step == null) {
            t_step = new RtxTextView[istep][imax];
            for (iLoop = 0; iLoop < istep; iLoop++) {
                for (iLoop1 = 0; iLoop1 < imax; iLoop1++) {
                    t_step[iLoop][iLoop1] = new RtxTextView(mContext);
                }
            }
        }

        if (i_step == null) {
            i_step = new RtxImageView[istep][imax];
            for (iLoop = 0; iLoop < istep; iLoop++) {
                for (iLoop1 = 0; iLoop1 < imax; iLoop1++) {
                    i_step[iLoop][iLoop1] = new RtxImageView(mContext);
                }
            }
        }

        if (t_noheart == null) {
            t_noheart = new RtxTextView(mContext);
        }

    }

    public void init_event() {

    }

    public void add_View() {
        int iLoop, iLoop1;
        int ix, iy, iw, ih;
        float fsize;
        int ix_temp, iy_temp, iw_temp, ih_temp;
        String sdata;
        int ipadding = 0;

//        menu
        sdata = LanguageData.s_get_string(mContext, R.string.gerkin_protocol);
        vSetTitleText(sdata.toUpperCase());

//      heart rate
        ix = 637;
        iy = 174;
        iw = 457;
        ih = 156;

        ix_temp = ix;
        iy_temp = iy + 50;
        iw_temp = 60;
        ih_temp = 60;
        addRtxImagePaddingViewToLayout(i_heart, R.drawable.hrc_heart, ix_temp, iy_temp, iw_temp, ih_temp, ipadding);

        iLoop = 0;
        ix_temp = ix;
        iy_temp = iy;
        iw_temp = iw;
        ih_temp = ih;
        fsize = 150f;
        addRtxTextViewToLayout(t_heart[iLoop], "", fsize, Common.Color.physical_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);

        iLoop = 1;
        ix_temp = ix + 360;
        iy_temp = iy + 50;
        iw_temp = 90;
        ih_temp = 50;
        fsize = 22f;
        sdata = LanguageData.s_get_string(mContext, R.string.bpm);
        addRtxTextViewToLayout(t_heart[iLoop], sdata.toLowerCase(), fsize, Common.Color.physical_word_blue, Common.Font.Relay_BlackItalic, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);

        iLoop = 2;
        ix_temp = ix;
        iy_temp = iy + ih;
        iw_temp = iw;
        ih_temp = 50;
        fsize = 20f;
        sdata = LanguageData.s_get_string(mContext, R.string.current_heart_rate);
        addRtxTextViewToLayout(t_heart[iLoop], sdata, fsize, Common.Color.physical_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);

//      max heart rate
        ix = 76;
        iy = 174;
        iw = 457;
        ih = 156;

        iLoop = 0;
        ix_temp = ix;
        iy_temp = iy;
        iw_temp = iw;
        ih_temp = ih;
        fsize = 150f;
        addRtxTextViewToLayout(t_heartmax[iLoop], "", fsize, Common.Color.physical_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);

        iLoop = 1;
        ix_temp = ix + 360;
        iy_temp = iy + 50;
        iw_temp = 90;
        ih_temp = 50;
        fsize = 22f;
        sdata = LanguageData.s_get_string(mContext, R.string.bpm);
        addRtxTextViewToLayout(t_heartmax[iLoop], sdata.toLowerCase(), fsize, Common.Color.physical_word_blue, Common.Font.Relay_BlackItalic, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);

        iLoop = 2;
        ix_temp = ix;
        iy_temp = iy + ih;
        iw_temp = iw;
        ih_temp = 50;
        fsize = 20f;
        sdata = LanguageData.s_get_string(mContext, R.string.maximum_heart_rate);
        addRtxTextViewToLayout(t_heartmax[iLoop], sdata, fsize, Common.Color.physical_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);

//      f_step
        ix = 80;
        iy = 490;
        iw = 256;
        ih = 100;//By Alan
        fsize = 28.00f;//By Alan
        for (iLoop = 0; iLoop < istep; iLoop++) {
            if (iLoop == 0) {
                s_stage = LanguageData.s_get_string(mContext, R.string.stage) + " ";
                addRtxTextViewToLayout(f_step[iLoop], R.string.warm_up, fsize, Common.Color.physical_word_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih, Common.Color.physical_word_yellow);
            } else {
                addRtxTextViewToLayout(f_step[iLoop], R.string.next, fsize, Common.Color.physical_word_white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih, Common.Color.physical_transparent);
            }
            f_step[iLoop].setFillet(ih / 2);
            f_step[iLoop].setMode(4);
            iy += 160;
        }

//      t_step
        ix = 374;
        iy = 503;
        iw = 277;
        ih = 70;
        fsize = 66.67f;
        sdata = "";
        for (iLoop = 0; iLoop < istep; iLoop++) {
            for (iLoop1 = 0; iLoop1 < imax; iLoop1++) {
                ix_temp = ix;
                iy_temp = iy;
                iw_temp = iw;
                ih_temp = ih;
                addRtxTextViewToLayout(t_step[iLoop][iLoop1], sdata, fsize, Common.Color.physical_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp, iw_temp, ih_temp);
                if (iLoop1 == 0) {
                    ix += 327;
                    if (iLoop == 0) {
                        t_step[iLoop][iLoop1].setTextColor(Common.Color.yellow);
                    }
                } else if (iLoop1 == 1) {
                    ix_temp = ix + 3;
                    iy_temp = iy + 23;
                    iw_temp = 40;
                    ih_temp = 24;
                    addRtxImagePaddingViewToLayout(i_step[iLoop][iLoop1], R.drawable.speed_icon, ix_temp, iy_temp, iw_temp, ih_temp, 0);
                    ix += 280;
                } else if (iLoop1 == 2) {
                    ix_temp = ix + 12;
                    iy_temp = iy + 20;
                    iw_temp = 40;
                    ih_temp = 31;
                    addRtxImagePaddingViewToLayout(i_step[iLoop][iLoop1], R.drawable.incline_icon, ix_temp, iy_temp, iw_temp, ih_temp, 0);
                    ix += 280;
                }

            }

            ix = 374;
            iy += 160;
        }

//        no heart information
        ix = 0;
        iy = 130;
        iw = 1280;
        ih = 50;
        fsize = 20f;
        sdata = LanguageData.s_get_string(mContext, R.string.no_heart_rate_detect).toUpperCase();
        addRtxTextView(this, t_noheart, sdata, fsize, Common.Color.info_word_green, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih, Common.Color.background);
        t_noheart.setVisibility(INVISIBLE);

        init_ViewMask();
        vVisibleMaskView(true);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void v_heart_detect() {
        String sdata = "";
        float fval;

        sdata = ExerciseGenfunc.s_get_HR_String();
        t_heart[0].setText(sdata);
        if(icount > EngSetting.DEF_SEC_COUNT * 15) {
            fval = ExerciseGenfunc.f_get_curr_heartrate();
            if (!ExerciseGenfunc.bHR_is_void(fval)) {
                if ((ExerciseData.iGet_count_val() / EngSetting.DEF_HSEC_COUNT) % 2 == 0) {
                    t_noheart.setVisibility(VISIBLE);
                } else {
                    t_noheart.setVisibility(INVISIBLE);
                }
            }
            else
            {
                t_noheart.setVisibility(INVISIBLE);
            }
        }
        else
        {
            t_noheart.setVisibility(INVISIBLE);
        }
    }

    private void v_heart_target()
    {
        String sdata = "";
        float fval;

        fval = ExerciseData.mCaculate_Data.ftarget_heart_rate;
        if(!ExerciseGenfunc.bHR_is_void(fval)) {
            sdata = "NA";
        }
        else
        {
            sdata = Rtx_TranslateValue.sFloat2String(fval, 0);
        }
        t_heartmax[0].setText(sdata);
    }

    private void v_heart_stage_curr()
    {
        String sdata = "";
        int ival;
        float fval;

        if(ExerciseData.ilist_count == 0) {
            sdata = LanguageData.s_get_string(mContext, R.string.warm_up);
        }
        else if(ExerciseData.ilist_count <= 11)
        {
            sdata = s_stage + Rtx_TranslateValue.sInt2String(ExerciseData.ilist_count);
        }
        f_step[0].setText(sdata);

        if(ExerciseData.ilist_count <= 11)
        {
            ExerciseData.DEVICE_COMMAND Uart_Command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count);
            if(Uart_Command != null)
            {
                ival = Uart_Command.isec - ExerciseData.mCaculate_Data.icurrent_time;
                t_step[0][0].setText(Rtx_Calendar.s_trans_integer(3, ival));

                fval = Uart_Command.fspeed;
                t_step[0][1].setText(EngSetting.Distance.getValString(fval));

                fval = Uart_Command.fincline;
                t_step[0][2].setText(Rtx_TranslateValue.sFloat2String(fval, 1));

            }
        }

    }

    private void v_heart_stage_next()
    {
        float fval;

        if(ExerciseData.ilist_count < 11)
        {
            f_step[1].setVisibility(VISIBLE);
            t_step[1][0].setVisibility(VISIBLE);
            i_step[1][1].setVisibility(VISIBLE);
            t_step[1][1].setVisibility(VISIBLE);
            i_step[1][2].setVisibility(VISIBLE);
            t_step[1][2].setVisibility(VISIBLE);

            if(ExerciseData.list_real_setting.size() > ExerciseData.ilist_count) {
                ExerciseData.DEVICE_COMMAND Uart_Command = ExerciseData.list_real_setting.get(ExerciseData.ilist_count + 1);

                if (Uart_Command != null) {
                    t_step[1][0].setText(Rtx_Calendar.s_trans_integer(3, Uart_Command.isec));

                    fval = Uart_Command.fspeed;
                    t_step[1][1].setText(EngSetting.Distance.getValString(fval));

                    fval = Uart_Command.fincline;
                    t_step[1][2].setText(Rtx_TranslateValue.sFloat2String(fval, 1));

                }
            }
        }
        else {
            f_step[1].setVisibility(INVISIBLE);
            t_step[1][0].setVisibility(INVISIBLE);
            i_step[1][1].setVisibility(INVISIBLE);
            t_step[1][1].setVisibility(INVISIBLE);
            i_step[1][2].setVisibility(INVISIBLE);
            t_step[1][2].setVisibility(INVISIBLE);

        }

    }


    public void Refresh()
    {
        if(icount % EngSetting.DEF_EXERCISE_PHYSICAL_REFRESH == 0)
        {
            v_heart_detect();
            v_heart_target();
            v_heart_stage_curr();
            v_heart_stage_next();
        }

        if(icount % (EngSetting.DEF_SEC_COUNT * 2) == 0)
        {
            vVisibleMaskView(false);
        }

        icount++;

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {

        }
    }
}
