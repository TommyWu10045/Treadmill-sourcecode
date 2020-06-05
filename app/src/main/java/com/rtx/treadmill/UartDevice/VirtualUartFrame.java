package com.rtx.treadmill.UartDevice;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.retronix.circleuart.guart_virtual;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

public class VirtualUartFrame extends Rtx_BaseLayout {
    private static String TAG = "Jerry=VUF";
    private final static boolean DEBUG = false;

    private     Context mContext;
    private MainActivity mMainActivity;

    boolean bmode = true;

    ButtonListener mButtonListener;

    RtxFillerTextView i_show;

    RtxTextView tincline_info;
    RtxImageView i_incline_add;
    RtxTextView tincline;
    RtxImageView i_incline_dec;

    RtxTextView tfan_info;
    RtxImageView i_fan_add;
    RtxTextView tfanduty;
    RtxImageView i_fan_dec;

    RtxTextView theart_info;
    RtxImageView i_heart_add;
    RtxTextView theart;
    RtxImageView i_heart_dec;

    RtxTextView tspeed_info;
    RtxImageView i_speed_add;
    RtxTextView tspeed;
    RtxImageView i_speed_dec;

    RtxFillerTextView i_emergency;
    RtxFillerTextView i_start;
    RtxFillerTextView i_pause;
    RtxFillerTextView i_stop;
    RtxFillerTextView i_cooldown;
    RtxFillerTextView i_error;
    RtxEditText e_error;

    public VirtualUartFrame(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

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

    public void v_Refresh_speed() {
        float fval;
        int isiip = guart_virtual.F05_data.iunit;

        if (DEBUG) Log.e(TAG, "v_Refresh_speed");

        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7) {
            if (isiip == 1) {
                fval = guart_virtual.F15_data.fspeed * EngSetting.km2mile;
            } else {
                fval = guart_virtual.F15_data.fspeed;
            }
            tspeed.setText(Rtx_TranslateValue.sFloat2String(fval, 1));
        }
        else
        {
            fval = guart_virtual.F16_data.frpm;
            tspeed.setText(Rtx_TranslateValue.sFloat2String(fval, 0));
        }


    }

    public void v_Refresh_incline() {
        float fval;

        if (DEBUG) Log.e(TAG, "v_Refresh_incline");

        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7) {
            fval = guart_virtual.F15_data.fincline;
            tincline.setText(Rtx_TranslateValue.sFloat2String(fval, 1));
        }
        else
        {
            fval = guart_virtual.F16_data.flevel;
            tincline.setText(Rtx_TranslateValue.sFloat2String(fval, 0));
        }
    }


    public void v_Refresh_data() {
        if (DEBUG) Log.e(TAG, "Refresh_data");
        if(!bmode)
        {
            tincline_info.setVisibility(INVISIBLE);
            i_incline_add.setVisibility(INVISIBLE);
            tincline.setVisibility(INVISIBLE);
            i_incline_dec.setVisibility(INVISIBLE);

            tfan_info.setVisibility(INVISIBLE);
            i_fan_add.setVisibility(INVISIBLE);
            tfanduty.setVisibility(INVISIBLE);
            i_fan_dec.setVisibility(INVISIBLE);

            theart_info.setVisibility(INVISIBLE);
            i_heart_add.setVisibility(INVISIBLE);
            theart.setVisibility(INVISIBLE);
            i_heart_dec.setVisibility(INVISIBLE);

            tspeed_info.setVisibility(INVISIBLE);
            i_speed_add.setVisibility(INVISIBLE);
            tspeed.setVisibility(INVISIBLE);
            i_speed_dec.setVisibility(INVISIBLE);

            i_emergency.setVisibility(INVISIBLE);
            i_start.setVisibility(INVISIBLE);
            i_stop.setVisibility(INVISIBLE);
            i_cooldown.setVisibility(INVISIBLE);
            i_pause.setVisibility(INVISIBLE);
            i_error.setVisibility(INVISIBLE);
            e_error.setVisibility(INVISIBLE);

            i_show.setText("SHOW");
        }
        else
        {
            tincline_info.setVisibility(VISIBLE);
            i_incline_add.setVisibility(VISIBLE);
            tincline.setVisibility(VISIBLE);
            i_incline_dec.setVisibility(VISIBLE);

            tfan_info.setVisibility(VISIBLE);
            i_fan_add.setVisibility(VISIBLE);
            tfanduty.setVisibility(VISIBLE);
            i_fan_dec.setVisibility(VISIBLE);

            theart_info.setVisibility(VISIBLE);
            i_heart_add.setVisibility(VISIBLE);
            theart.setVisibility(VISIBLE);
            i_heart_dec.setVisibility(VISIBLE);

            tspeed_info.setVisibility(VISIBLE);
            i_speed_add.setVisibility(VISIBLE);
            tspeed.setVisibility(VISIBLE);
            i_speed_dec.setVisibility(VISIBLE);

            i_emergency.setVisibility(VISIBLE);
            i_start.setVisibility(VISIBLE);
            i_stop.setVisibility(VISIBLE);
            i_cooldown.setVisibility(VISIBLE);
            i_pause.setVisibility(VISIBLE);
            i_error.setVisibility(VISIBLE);
            e_error.setVisibility(VISIBLE);

            i_show.setText("HIDE");
        }

        v_Refresh_speed();
        v_Refresh_incline();
        tfanduty.setText(Rtx_TranslateValue.sFloat2String(guart_virtual.F15_data.ifanduty, 0));
        theart.setText(Rtx_TranslateValue.sFloat2String(guart_virtual.F15_data.fheartrate, 1));

    }

    private void init_View()
    {
        if(i_show == null)       { i_show = new RtxFillerTextView(mContext);   }

        if(tincline_info == null)        { tincline_info = new RtxTextView(mContext);   }
        if(i_incline_add == null)        { i_incline_add = new RtxImageView(mContext);   }
        if(tincline == null)        { tincline = new RtxTextView(mContext);   }
        if(i_incline_dec == null)        { i_incline_dec = new RtxImageView(mContext);   }

        if(tfan_info == null)        { tfan_info = new RtxTextView(mContext);   }
        if(i_fan_add == null)        { i_fan_add = new RtxImageView(mContext);   }
        if(tfanduty == null)        { tfanduty = new RtxTextView(mContext);   }
        if(i_fan_dec == null)        { i_fan_dec = new RtxImageView(mContext);   }

        if(theart_info == null)        { theart_info = new RtxTextView(mContext);   }
        if(i_heart_add == null)        { i_heart_add = new RtxImageView(mContext);   }
        if(theart == null)        { theart = new RtxTextView(mContext);   }
        if(i_heart_dec == null)        { i_heart_dec = new RtxImageView(mContext);   }

        if(tspeed_info == null)        { tspeed_info = new RtxTextView(mContext);   }
        if(i_speed_add == null)        { i_speed_add = new RtxImageView(mContext);   }
        if(tspeed == null)        { tspeed = new RtxTextView(mContext);   }
        if(i_speed_dec == null)        { i_speed_dec = new RtxImageView(mContext);   }

        if(i_emergency == null)       { i_emergency = new RtxFillerTextView(mContext);   }
        if(i_start == null)       { i_start = new RtxFillerTextView(mContext);   }
        if(i_pause == null)       { i_pause = new RtxFillerTextView(mContext);   }
        if(tspeed == null)        { tspeed = new RtxTextView(mContext);   }
        if(i_stop == null)       { i_stop = new RtxFillerTextView(mContext);   }
        if(i_cooldown == null)       { i_cooldown = new RtxFillerTextView(mContext);   }
        if(i_error == null)       { i_error = new RtxFillerTextView(mContext);   }
        if(e_error == null)       { e_error = new RtxEditText(mContext);   }


    }

    private void init_event()
    {
        i_show.setOnClickListener(mButtonListener);
        i_incline_add.setOnClickListener(mButtonListener);
        i_incline_dec.setOnClickListener(mButtonListener);
        i_fan_add.setOnClickListener(mButtonListener);
        i_fan_dec.setOnClickListener(mButtonListener);
        i_heart_add.setOnClickListener(mButtonListener);
        i_heart_dec.setOnClickListener(mButtonListener);
        i_speed_add.setOnClickListener(mButtonListener);
        i_speed_dec.setOnClickListener(mButtonListener);

        i_emergency.setOnClickListener(mButtonListener);
        i_start.setOnClickListener(mButtonListener);
        i_pause.setOnClickListener(mButtonListener);
        i_stop.setOnClickListener(mButtonListener);
        i_cooldown.setOnClickListener(mButtonListener);
        i_error.setOnClickListener(mButtonListener);

    }

    private void add_View()
    {
        String sdata;
        int ix, iy, iw, ih;
        float fsize;

        ix = 50;
        iy = 110;
        iw = 120;
        ih = 100;
        fsize = 28f;
        addRtxTextViewToLayout(i_show, R.string.delete, fsize, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih, Common.Color.red_1);
        i_show.setText("HIDE");

        ix = 10;
        iy = 300;
        iw = 100;
        ih = 30;
        fsize = 20f;
        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7) {
            sdata = "Incline";
        }
        else
        {
            sdata = "Level";
        }
        addRtxTextViewToLayout(tincline_info, sdata, fsize, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        iw = 100;
        ih = 100;
        iy += 30;
        addRtxImageViewToLayout(i_incline_add, R.drawable.uart_up, ix, iy, iw, ih);
        iy += 110;
        sdata = Rtx_TranslateValue.sFloat2String(guart_virtual.F15_data.fincline, 1);
        addRtxTextViewToLayout(tincline, sdata, fsize, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        iy += 110;
        addRtxImageViewToLayout(i_incline_dec, R.drawable.uart_down, ix, iy, iw, ih);

        ix = 120;
        iy = 300;
        iw = 100;
        ih = 30;
        addRtxTextViewToLayout(tfan_info, "FAN", fsize, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        iw = 100;
        ih = 100;
        iy += 30;
        addRtxImageViewToLayout(i_fan_add, R.drawable.uart_up, ix, iy, iw, ih);
        iy += 110;
        sdata = Rtx_TranslateValue.sFloat2String(guart_virtual.F15_data.ifanduty, 0);
        addRtxTextViewToLayout(tfanduty, sdata, fsize, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        iy += 110;
        addRtxImageViewToLayout(i_fan_dec, R.drawable.uart_down, ix, iy, iw, ih);

        ix = 1020;
        iy = 300;
        iw = 100;
        ih = 30;
        addRtxTextViewToLayout(theart_info, "HEART", fsize, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        iw = 100;
        ih = 100;
        iy += 30;
        addRtxImageViewToLayout(i_heart_add, R.drawable.uart_up, ix, iy, iw, ih);
        iy += 110;
        sdata = Rtx_TranslateValue.sFloat2String(guart_virtual.F15_data.fheartrate, 1);
        addRtxTextViewToLayout(theart, sdata, fsize, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        iy += 110;
        addRtxImageViewToLayout(i_heart_dec, R.drawable.uart_down, ix, iy, iw, ih);

        ix = 1150;
        iy = 300;
        iw = 100;
        ih = 30;
        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7) {
            sdata = "SPEED";
        }
        else
        {
            sdata = "RPM";
        }
        addRtxTextViewToLayout(tspeed_info, sdata, fsize, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        iw = 100;
        ih = 100;
        iy += 30;
        addRtxImageViewToLayout(i_speed_add, R.drawable.uart_up, ix, iy, iw, ih);
        iy += 110;
        sdata = Rtx_TranslateValue.sFloat2String(guart_virtual.F15_data.fspeed, 1);
        addRtxTextViewToLayout(tspeed, sdata, fsize, Common.Color.white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        iy += 110;
        addRtxImageViewToLayout(i_speed_dec, R.drawable.uart_down, ix, iy, iw, ih);

        ix = 100;
        iy = 10;
        iw = 160;
        ih = 100;
        fsize = 28f;
        addRtxTextViewToLayout(i_emergency, "EMER", fsize, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        ix += 200;
        addRtxTextViewToLayout(i_start, "START", fsize, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        ix += 200;
        addRtxTextViewToLayout(i_pause, "PAUSE", fsize, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        ix += 200;
        addRtxTextViewToLayout(i_stop, "STOP", fsize, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        ix += 200;
        addRtxTextViewToLayout(i_cooldown, "COOL", fsize, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        ix += 200;
        addRtxTextViewToLayout(i_error, "ERROR", fsize, Common.Color.login_word_dark_black, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 120;
        addRtxEditViewToLayout(e_error, InputType.TYPE_CLASS_NUMBER , ix, iy, iw, ih);
        e_error.setText("1");
    }

    private void v_F15_speed_change(float fadd)
    {
        float fval = guart_virtual.F15_data.fspeed + fadd;

        if(fval < guart_virtual.F15_speed_min)
        {
            fval = guart_virtual.F15_speed_min;
        }
        else if(fval > guart_virtual.F15_speed_max)
        {
            fval = guart_virtual.F15_speed_max;
        }

        guart_virtual.F15_data.fspeed = fval;

    }

    private void v_F15_incline_change(float fadd)
    {
        float fval = guart_virtual.F15_data.fincline + fadd;

        if(fval < guart_virtual.F15_incline_min)
        {
            fval = guart_virtual.F15_incline_min;
        }
        else if(fval > guart_virtual.F15_incline_max)
        {
            fval = guart_virtual.F15_incline_max;
        }

        guart_virtual.F15_data.fincline = fval;

    }

    private void v_F16_level_change(float fadd)
    {
        float fval = guart_virtual.F16_data.flevel + fadd;

        if(fval < guart_virtual.F16_level_min)
        {
            fval = guart_virtual.F16_level_min;
        }
        else if(fval > guart_virtual.F16_level_max)
        {
            fval = guart_virtual.F16_level_max;
        }

        guart_virtual.F16_data.flevel = fval;

    }

    private void v_F16_rpm_change(float fadd)
    {
        float fval = guart_virtual.F16_data.frpm + fadd;

        if(fval < 0)
        {
            fval = 0;
        }
        else if(fval > 500)
        {
            fval = 500;
        }

        guart_virtual.F16_data.frpm = fval;

    }

    private void v_F15_fan_change(float fadd)
    {
        float fval = guart_virtual.F15_data.ifanduty + fadd;

        if(fval < 0)
        {
            fval = 2;
        }
        else if(fval > 2)
        {
            fval = 0;
        }

        guart_virtual.F15_data.ifanduty = (int)fval;
//        guart_virtual.F15_data.ikey = 4;
    }

    private void v_F15_HR_change(float fadd)
    {
        float fval = guart_virtual.F15_data.fheartrate + fadd;

//        if(fval < EngSetting.f_Get_Min_HR())
//        {
//            fval = EngSetting.f_Get_Min_HR();
//        }
//        else if(fval > EngSetting.f_Get_Max_HR())
//        {
//            fval = EngSetting.f_Get_Max_HR();
//        }

        guart_virtual.F15_data.fheartrate = fval;

    }

    private void v_F16_HR_change(float fadd)
    {
        float fval = guart_virtual.F16_data.fheartrate + fadd;

//        if(fval < EngSetting.f_Get_Min_HR())
//        {
//            fval = EngSetting.f_Get_Min_HR();
//        }
//        else if(fval > EngSetting.f_Get_Max_HR())
//        {
//            fval = EngSetting.f_Get_Max_HR();
//        }

        guart_virtual.F16_data.fheartrate = fval;

    }

    class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if (v == i_show) {
                if (DEBUG) Log.e(TAG, "==========i_show========");
                bmode = !bmode;
                v_Refresh_data();
            }
            else if (v == i_incline_add) {
                if (DEBUG) Log.e(TAG, "==========i_incline_add========");
                if (guart_virtual.F64_data.ikey_en == 1)
                {
                    v_F15_incline_change(0.5f);
                    v_F16_level_change(1f);
                    v_Refresh_data();
                }
            }
            else if (v == i_incline_dec) {
                if (DEBUG) Log.e(TAG, "==========i_incline_dec========");
                if (guart_virtual.F64_data.ikey_en == 1)
                {
                    v_F15_incline_change(-0.5f);
                    v_F16_level_change(-1f);
                    v_Refresh_data();
                }
            }
            else if (v == i_fan_add) {
                if (DEBUG) Log.e(TAG, "==========i_fan_add========");
                v_F15_fan_change(1f);
                v_Refresh_data();
            }
            else if (v == i_fan_dec) {
                if (DEBUG) Log.e(TAG, "==========i_fan_dec========");
                v_F15_fan_change(-1f);
                v_Refresh_data();
            }
            else if (v == i_heart_add) {
                if (DEBUG) Log.e(TAG, "==========i_heart_add========");
                v_F15_HR_change(3f);
                v_F16_HR_change(3f);
                v_Refresh_data();
            }
            else if (v == i_heart_dec) {
                if (DEBUG) Log.e(TAG, "==========i_heart_dec========");
                v_F15_HR_change(-3f);
                v_F16_HR_change(-3f);
                v_Refresh_data();
            }
            else if (v == i_speed_add) {
                if (DEBUG) Log.e(TAG, "==========i_speed_add========");
                if (guart_virtual.F64_data.ikey_en == 1) {
                    v_F15_speed_change(0.5f);
                }
                v_F16_rpm_change(10f);
                v_Refresh_data();
            }
            else if (v == i_speed_dec) {
                if (DEBUG) Log.e(TAG, "==========i_speed_dec========");
                if (guart_virtual.F64_data.ikey_en == 1) {
                    v_F15_speed_change(-0.5f);
                }
                v_F16_rpm_change(-10f);
                v_Refresh_data();
            }
            else if (v == i_emergency) {
                if (DEBUG) Log.e(TAG, "==========i_emergency========");
                guart_virtual.F15_data.iemergency = 1;
            }
            else if (v == i_stop) {
                if (DEBUG) Log.e(TAG, "==========i_stop========");
                guart_virtual.F15_data.ikey = 2;
                guart_virtual.F16_data.ikey = 2;
            }
            else if (v == i_start) {
                if (DEBUG) Log.e(TAG, "==========i_start========");
                guart_virtual.F15_data.ikey = 3;
                guart_virtual.F16_data.ikey = 3;
            }
            else if (v == i_pause) {
                if (DEBUG) Log.e(TAG, "==========i_pause========");
                guart_virtual.F15_data.ikey = 1;
                guart_virtual.F16_data.ikey = 1;
            }
            else if (v == i_cooldown) {
                if (DEBUG) Log.e(TAG, "==========i_cooldown========");
                guart_virtual.F15_data.ikey = 4;
                guart_virtual.F16_data.ikey = 4;
            }
            else if (v == i_error) {
                if (DEBUG) Log.e(TAG, "==========i_error========");
                String sval = e_error.getText().toString();
                int ival = 0;
                if(sval != null)
                {
                    ival = Rtx_TranslateValue.iString2Int(sval);
                    if(ival >= 0 && ival <= 0xff)
                    {
                        guart_virtual.F15_data.ierrocode = ival;
                    }
                }
            }

        }

    }
}
