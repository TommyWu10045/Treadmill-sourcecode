package com.rtx.treadmill.Engmode;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.Perf;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_Keyboard;
import com.rtx.treadmill.RtxTools.Rtx_Log;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView;

import java.lang.reflect.Field;

public class EngAboutFrame extends Rtx_BaseLayout {
    private static String TAG = "Jerry=";
    private final static boolean DEBUG = false;

    Context mContext;
    EngModeActivity mEngModeActivity;

    ButtonListener mButtonListener;

    RtxImageView i_eng_background;
    RtxImageView i_eng_logo;
    RtxTextView t_eng_mode;
    RtxImageView i_exit;
    RtxTextView t_exit;
    RtxImageView i_line00;

    RtxImageView i_wifi;
    RtxImageView i_line01;
    RtxImageView i_about;
    RtxImageView i_line02;
    RtxImageView i_setting;
    RtxImageView i_line03;
    RtxImageView i_update;

    RtxTextView t_wifi;
    RtxTextView t_about;
    RtxTextView t_setting;
    RtxTextView t_update;

    private ScrollView scroller;
    private FrameLayout fscroll;

    RtxTextView t_machine_type;
    RtxTextView t_machone_val;
    RtxImageView i_machine_line;

    RtxTextView t_model;
    RtxEditText e_model_val;
    RtxImageView i_model_line;

    RtxTextView t_app_ver;
    RtxTextView t_app_val;
    RtxImageView i_00_line;

    RtxTextView t_bundle_ver;
    RtxTextView t_bundle_val;
    RtxImageView i_bundle_line;

    RtxTextView t_swupd_ver;
    RtxTextView t_swupd_val;
    RtxImageView i_swupd_line;

    RtxTextView t_dev_distance;
    RtxTextView t_dev_distance_val;
    RtxTextView t_dev_distance_clear;
    RtxImageView i_dev_distance_clear;
    RtxImageView i_01_line;

    RtxTextView t_dev_time;
    RtxTextView t_dev_time_val;
    RtxTextView t_dev_time_clear;
    RtxImageView i_dev_time_clear;
    RtxImageView i_02_line;

    RtxImageView i_exit_launcher;
    RtxTextView t_exit_launcher;

    RtxTextView t_error_dayly;

    FrameLayout fdialog;
    RtxView dialog_back;
    RtxView dialog_box;
    RtxTextView dialog_info;
    RtxEditText dialog_input;
    RtxTextView dialog_ok;
    RtxTextView dialog_cancel;

    public EngAboutFrame(Context context, EngModeActivity mEngModeActivity) {
        super(context);

        this.mContext = context;
        this.mEngModeActivity = mEngModeActivity;

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
        v_EngAboutFrame_Refresh_data();
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    public void v_EngAboutFrame_Refresh_data() {
        if (DEBUG) Log.e(TAG, "Refresh_data");
        String sdata;
        float fval;

        if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7)
        {
            t_machone_val.setText("Treadmill");
        }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING6)
        {
            t_machone_val.setText("Upright Bike");
        }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING6)
        {
            t_machone_val.setText("Recumbent Bike");
        }
        else if(EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL || EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL6)
        {
            t_machone_val.setText("Elliptical");
        }

        sdata = EngSetting.s_Get_ENG_MDL();
        e_model_val.setText(sdata);

        t_app_val.setText(EngSetting.s_Get_APK_VER());

        t_bundle_val.setText("V" + Build.VERSION.SDK + "_" + EngSetting.s_Get_APK_VER().split("\\.")[2]);
//        t_bundle_val.setText("V" + Build.VERSION.SDK + "_" + EngSetting.s_Get_APK_VER());

        sdata = Rtx_Calendar.s_trans_DateTime_Str(1, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss", EngSetting.s_Get_SW_UPDATE_TIME(), 0, 0);
        t_swupd_val.setText(sdata);

        fval = EngSetting.f_Get_ENG_DEV_DISTANCE();
        sdata = Rtx_TranslateValue.sFloat2String(fval, 0) + "  " + EngSetting.Distance.getUnitString(mContext);
        t_dev_distance_val.setText(sdata);

        fval = EngSetting.f_Get_ENG_DEV_TIME();
        sdata = Rtx_TranslateValue.sFloat2String(fval, 0) + "  Hr.";
        t_dev_time_val.setText(sdata);
    }

    private void add_scrollview(ScrollView mScrollView, int iX , int iY , int iWidth , int iHeight) {
        FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(iWidth,iHeight);
        if(iX == -1)
        {
            mLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        }
        else
        {
            mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;
            mLayoutParams.leftMargin = iX;
        }
        mLayoutParams.topMargin = iY;
        mScrollView.setLayoutParams(mLayoutParams);

        addView(mScrollView);
    }

    private void init_View()
    {
        if(i_eng_background == null)           {i_eng_background = new RtxImageView(mContext);}
        if(i_eng_logo == null)           {i_eng_logo = new RtxImageView(mContext);}
        if(t_eng_mode == null)           {t_eng_mode = new RtxTextView(mContext);}

        if(i_exit == null)           {i_exit = new RtxImageView(mContext);}
        if(t_exit == null)           {t_exit = new RtxTextView(mContext);}

        if(i_line00 == null)           {i_line00 = new RtxImageView(mContext);}
        if(i_wifi == null)           {i_wifi = new RtxImageView(mContext);}
        if(i_line01 == null)           {i_line01 = new RtxImageView(mContext);}
        if(i_about == null)           {i_about = new RtxImageView(mContext);}
        if(i_line02 == null)           {i_line02 = new RtxImageView(mContext);}
        if(i_setting == null)           {i_setting = new RtxImageView(mContext);}
        if(i_line03 == null)           {i_line03 = new RtxImageView(mContext);}
        if(i_update == null)           {i_update = new RtxImageView(mContext);}

        if(t_wifi == null)           {t_wifi = new RtxTextView(mContext);}
        if(t_setting == null)           {t_setting = new RtxTextView(mContext);}
        if(t_about == null)           {t_about = new RtxTextView(mContext);}
        if(t_update == null)           {t_update = new RtxTextView(mContext);}

        //frame same
        if(scroller == null)           {scroller = new ScrollView(mContext);}
        if(fscroll == null)           {
            fscroll = new FrameLayout(mContext);
            scroller.addView(fscroll);
        }

        if(t_machine_type == null)           {t_machine_type = new RtxTextView(mContext);}
        if(t_machone_val == null)           {t_machone_val = new RtxTextView(mContext);}
        if(i_machine_line == null)           {i_machine_line = new RtxImageView(mContext);}

        if(t_model == null)           {t_model = new RtxTextView(mContext);}
        if(e_model_val == null)           {e_model_val = new RtxEditText(mContext);}
        if(i_model_line == null)           {i_model_line = new RtxImageView(mContext);}

        if(t_app_ver == null)           {t_app_ver = new RtxTextView(mContext);}
        if(t_app_val == null)           {t_app_val = new RtxTextView(mContext);}
        if(i_00_line == null)           {i_00_line = new RtxImageView(mContext);}

        if(t_bundle_ver == null)           {t_bundle_ver = new RtxTextView(mContext);}
        if(t_bundle_val == null)           {t_bundle_val = new RtxTextView(mContext);}
        if(i_bundle_line == null)           {i_bundle_line = new RtxImageView(mContext);}

        if(t_swupd_ver == null)           {t_swupd_ver = new RtxTextView(mContext);}
        if(t_swupd_val == null)           {t_swupd_val = new RtxTextView(mContext);}
        if(i_swupd_line == null)           {i_swupd_line = new RtxImageView(mContext);}

        if(t_dev_distance == null)           {t_dev_distance = new RtxTextView(mContext);}
        if(t_dev_distance_val == null)           {t_dev_distance_val = new RtxTextView(mContext);}
        if(t_dev_distance_clear == null)           {t_dev_distance_clear = new RtxTextView(mContext);}
        if(i_dev_distance_clear == null)           {i_dev_distance_clear = new RtxImageView(mContext);}
        if(i_01_line == null)           {i_01_line = new RtxImageView(mContext);}

        if(t_dev_time == null)           {t_dev_time = new RtxTextView(mContext);}
        if(t_dev_time_val == null)           {t_dev_time_val = new RtxTextView(mContext);}
        if(t_dev_time_clear == null)           {t_dev_time_clear = new RtxTextView(mContext);}
        if(i_dev_time_clear == null)           {i_dev_time_clear = new RtxImageView(mContext);}
        if(i_02_line == null)           {i_02_line = new RtxImageView(mContext);}

        if(i_exit_launcher == null)           {i_exit_launcher = new RtxImageView(mContext);}
        if(t_exit_launcher == null)           {t_exit_launcher = new RtxTextView(mContext);}

        if(t_error_dayly == null)           {t_error_dayly = new RtxTextView(mContext);}

        if(fdialog == null)           {fdialog = new FrameLayout(mContext);}
        if(dialog_back == null)           {dialog_back = new RtxView(mContext);}
        if(dialog_box == null)           {dialog_box = new RtxView(mContext);}
        if(dialog_info == null)           {dialog_info = new RtxTextView(mContext);}
        if(dialog_input == null)           {dialog_input = new RtxEditText(mContext);}
        if(dialog_ok == null)           {dialog_ok = new RtxTextView(mContext);}
        if(dialog_cancel == null)           {dialog_cancel = new RtxTextView(mContext);}

    }

    private void init_event()
    {
        i_wifi.setOnClickListener(mButtonListener);
        i_about.setOnClickListener(mButtonListener);
        i_setting.setOnClickListener(mButtonListener);
        i_update.setOnClickListener(mButtonListener);
        i_exit.setOnClickListener(mButtonListener);
        i_exit_launcher.setOnClickListener(mButtonListener);
        t_error_dayly.setOnClickListener(mButtonListener);

        t_wifi.setEnabled(false);
        t_wifi.setClickable(false);
        t_about.setEnabled(false);
        t_about.setClickable(false);
        t_setting.setEnabled(false);
        t_setting.setClickable(false);
        t_update.setEnabled(false);
        t_update.setClickable(false);
        t_exit.setEnabled(false);
        t_exit.setClickable(false);
        t_exit_launcher.setEnabled(false);
        t_exit_launcher.setClickable(false);

        dialog_back.setOnClickListener(mButtonListener);
        dialog_ok.setOnClickListener(mButtonListener);
        dialog_cancel.setOnClickListener(mButtonListener);

        t_dev_distance_clear.setOnClickListener(mButtonListener);
        t_dev_time_clear.setOnClickListener(mButtonListener);

        e_model_val.setOnFocusChangeListener(edittext_focus);
    }

    private void addRtxEditView(FrameLayout mFram, RtxEditText view, int iType, int iX, int iY, int iWidth, int iHeight)
    {
        if(view == null)
        {
            Rtx_Log.Log_Error("view is null in addRtxEditViewToLayout!!!");
            return;
        }

        view.setBackgroundColor(Common.Color.engmode_word_backgroud);
        view.setTextColor(Common.Color.engmode_word_gray);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,28f);
        view.setGravity(Gravity.CENTER);
        view.setTypeface(view.getTypeface(), Typeface.NORMAL);//Typeface.BOLD
        view.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), Common.Font.Relay_Black));

        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(view, R.drawable.cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.setInputType(iType);
        view.setSingleLine(true);

        addViewToLayout(mFram, view,iX,iY,iWidth,iHeight);
    }

    private void add_View()
    {
        if (DEBUG) Log.e(TAG, "init_view");
        int ix;
        int iy;
        int iw;
        int ih;
        float fsize;
        String sdata;

        ix = 0;
        iy = 0;
        iw = 1280;
        ih = 800;
        addRtxImageViewToLayout(i_eng_background, R.drawable.eng_background, ix, iy, iw, ih);

        ix = 90;
        iy = 115;
        iw = 40;
        ih = 40;
        addRtxImageViewToLayout(i_eng_logo, R.drawable.eng_logo, ix, iy, iw, ih);

        ix = 150;
        iy = 110;
        iw = 400;
        ih = 50;
        fsize = 40f;
        sdata = mContext.getResources().getString(R.string.engineering_mode);
        addRtxTextViewToLayout(t_eng_mode, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 1060;
        iy = 110;
        iw = 140;
        ih = 60;
        fsize = 36f;
        addRtxImageViewToLayout(i_exit, R.drawable.eng_exit, ix, iy, iw, ih);

        sdata = mContext.getResources().getString(R.string.exit);
        addRtxTextViewToLayout(t_exit, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 45;
        iy = 180;
        iw = 1190;
        ih = 10;
        addRtxImage(null, i_line00, R.drawable.eng_line_blue, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ix = 45;
        iy = 180;
        iw = 240;

        ih = 525/4+5;
        addRtxImageViewToLayout(i_wifi, -1, ix, iy, iw, ih);

        iy += 525/4-5;
        ih = 10;
        addRtxImage(null, i_line01, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        ih = 525/4;
        iy += 5;
        addRtxImageViewToLayout(i_about, R.drawable.eng_but, ix, iy, iw, ih);
        i_about.setScaleType(ImageView.ScaleType.FIT_XY);
        iy -= 5;

        iy += 525/4;
        ih = 10;
        addRtxImage(null, i_line02, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        ih = 525/4+5;
        addRtxImageViewToLayout(i_setting, -1, ix, iy, iw, ih);

        iy += 525/4;
        ih = 10;
        addRtxImage(null, i_line03, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        ih = 525/4+5;
        addRtxImageViewToLayout(i_update, -1, ix, iy, iw, ih);

        ix = 40;
        iy = 185;
        iw = 245;
        ih = 525/4;
        fsize = 36f;
        sdata = mContext.getResources().getString(R.string.engwifi);
        addRtxTextViewToLayout(t_wifi, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 525/4;
        sdata = mContext.getResources().getString(R.string.engabout);
        addRtxTextViewToLayout(t_about, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 525/4;
        sdata = mContext.getResources().getString(R.string.engsetting);
        addRtxTextViewToLayout(t_setting, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 525/4;
        sdata = mContext.getResources().getString(R.string.engupdate);
        addRtxTextViewToLayout(t_update, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        ix = 285;
        iy = 190;
        iw = (1190 - 245);//995;
        ih = 525 - 10;//500;
        add_scrollview(scroller, ix, iy, iw, ih);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Machine Type
        ix = 50;
        iy = 50;
        iw = 350;//250;
        ih = 50;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.engmachine_type);
        addRtxTextView(fscroll, t_machine_type, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;//ix=iw+10//560
        iw = 500;//250+600 = 650
        addRtxTextView(fscroll, t_machone_val, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 50;//315
        ih = 10;
        addRtxImage(fscroll, i_machine_line, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        //Model
        ix = 50;
        iy +=50;//iy = 265;
        iw = 350;
        ih = 60;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.engmodel);
        addRtxTextView(fscroll, t_model, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;
        iw = 500;
        addRtxEditView(fscroll, e_model_val, InputType.TYPE_CLASS_TEXT , ix, iy, iw, ih);

        iy += 50;
        ih = 10;
        addRtxImage(fscroll, i_model_line, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        //APP VERSION
        ix = 50;
        iy +=50;//iy = 265;
        iw = 350;
        ih = 50;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.engapp_version);
        addRtxTextView(fscroll, t_app_ver, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;
        iw = 500;
        addRtxTextView(fscroll, t_app_val, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 50;
        ih = 10;
        addRtxImage(fscroll, i_00_line, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        //BUNDLE VERSION
        ix = 50;
        iy +=50;//iy = 265;
        iw = 350;
        ih = 50;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.engbundle_version);
        addRtxTextView(fscroll, t_bundle_ver, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;
        iw = 500;
        addRtxTextView(fscroll, t_bundle_val, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 50;
        ih = 10;
        addRtxImage(fscroll, i_bundle_line, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        //LAST UPDATE TIME
        ix = 50;
        iy +=50;//iy = 265;
        iw = 350;
        ih = 50;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.englast_update_time);
        addRtxTextView(fscroll, t_swupd_ver, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;
        iw = 500;
        addRtxTextView(fscroll, t_swupd_val, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 50;
        ih = 10;
        addRtxImage(fscroll, i_swupd_line, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        //TOTAL DISTANCE
        ix = 50;
        iy +=50;//iy = 365;
        iw = 350;
        ih = 50;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.eng_dev_total_disance);
        addRtxTextView(fscroll, t_dev_distance, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;//ix=iw+10//660
        iw = 500 - 140;//350+500 850
        addRtxTextView(fscroll, t_dev_distance_val, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += (500 - 140);
        iw = 140;
        ih = 50;
        addRtxImage(fscroll, i_dev_distance_clear, R.drawable.eng_time, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.engdistanceclear);
        addRtxTextView(fscroll, t_dev_distance_clear, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix -= (500 - 140);
        iy += 50;
        iw = 500;
        ih = 10;
        addRtxImage(fscroll, i_01_line, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        //TOTAL TIME
        ix = 50;
        iy +=50;//iy = 465;
        iw = 350;
        ih = 50;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.eng_dev_total_time);
        addRtxTextView(fscroll, t_dev_time, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;
        iw = 500 - 140;
        addRtxTextView(fscroll, t_dev_time_val, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += (500 - 140);
        iw = 140;
        ih = 50;
        addRtxImage(fscroll, i_dev_time_clear, R.drawable.eng_time, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.engtimeclear);
        addRtxTextView(fscroll, t_dev_time_clear, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix -= (500 - 140);
        iy += 50;
        iw = 500;
        ih = 10;
        addRtxImage(fscroll, i_02_line, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        //EXIT LAUNCHER
        ix = 85;
        iy += 50;
        iw = 400;
        ih = 80;
        fsize = 36f;
        addRtxImage(fscroll, i_exit_launcher, R.drawable.eng_exit, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        sdata = mContext.getResources().getString(R.string.engexit_launcher);
        addRtxTextView(fscroll, t_exit_launcher, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        //SHOW ERROR DAYLY
        ix += iw + 50;
        fsize = 20f;
        sdata = "SHOW ERROR DAYLY";
        addRtxTextView(fscroll, t_error_dayly, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 50;
        iy += 100;
        iw = 850;
        ih = 10;
        addRtxImage(fscroll, i_model_line, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        //exit dialog
        ix = 0;
        iy = 0;
        iw = 1280;
        ih = 800;
        addViewToLayout(fdialog, dialog_back, ix, iy, iw, ih);
        dialog_back.setBackgroundColor(Common.Color.engmode_dialog_back);

        ix = 340;
        iy = 200;
        iw = 600;
        ih = 200;
        addViewToLayout(fdialog, dialog_box, ix, iy, iw, ih);
        dialog_box.setBackgroundColor(Common.Color.engmode_word_white);

        ix += 50;
        iy += 10;
        ih = 50;
        fsize = 28f;
        sdata = "Input password to exit";
        addRtxTextView(fdialog, dialog_info, sdata, fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT, ix, iy, iw, ih);

        iy += ih;
        iw -= 100;
        ih = 60;
        addRtxEditView(fdialog, dialog_input, InputType.TYPE_CLASS_TEXT , ix, iy, iw, ih);

        iy += ih;
        iw = 100;
        sdata = "OK";
        addRtxTextView(fdialog, dialog_ok, sdata, fsize, Common.Color.engmode_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += 400;
        sdata = "Cancel";
        addRtxTextView(fdialog, dialog_cancel, sdata, fsize, Common.Color.engmode_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        addView(fdialog);
        fdialog.setVisibility(INVISIBLE);
    }

    private void vEngAboutFrame_DevDistanceClear()
    {
        EngSetting.v_Set_ENG_DEV_DISTANCE(0f);
        Perf.v_Data_SetPreferences(mContext, Perf.PREF_MACHINE_DISTANCE, Rtx_TranslateValue.sFloat2String(-11f, 3));
//        Perf.v_Update_Perf_Device_Info(mContext);
        t_dev_distance_val.setText("0  " + EngSetting.Distance.getUnitString(mContext));
    }

    private void vEngAboutFrame_DevTimeClear()
    {
        EngSetting.v_Set_ENG_DEV_TIME(0f);
        Perf.v_Data_SetPreferences(mContext, Perf.PREF_MACHINE_TIME, Rtx_TranslateValue.sFloat2String(-11f, 3));
//        Perf.v_Update_Perf_Device_Info(mContext);
        t_dev_time_val.setText("0  Hr.");
    }

    private void v_exit_launcher_show()
    {
        fdialog.setVisibility(VISIBLE);
    }

    private void v_exit_launcher_hide()
    {
        vClickBackgroud();
        fdialog.setVisibility(INVISIBLE);
    }

    private void v_check_launcher()
    {
        String sinput = dialog_input.getText().toString();

        if(sinput.compareTo(EngSetting.PASSWORD_EXIT) == 0)
        {
            SwitchLauncherApp(mContext); // By Alan
            //mEngModeActivity.mEngModeProc.vExitThread();
            //v_exit_launcher_hide();
        }
        else
        {
            dialog_info.setText("Wrong password");
        }

    }

    // By Alan *
    public static void SwitchLauncherApp(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, com.rtx.treadmill.MainActivity.class);
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        Intent selector = new Intent(Intent.ACTION_MAIN);
        selector.addCategory(Intent.CATEGORY_HOME);
        selector.addCategory(Intent.CATEGORY_DEFAULT);
        Intent chooser = Intent.createChooser(selector, "Please select launcher APP");
        context.startActivity(chooser);

        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
    }
    // * By Alan

    private void vClickBackgroud()
    {
        Rtx_Keyboard.closeSoftKeybord(this,mContext);
    }

    private void set_scroller_y(int istep)
    {
        int iy = 100;

        scroller.setScrollY(iy*istep);
    }

    private OnFocusChangeListener edittext_focus = new OnFocusChangeListener(){
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (DEBUG) Log.e(TAG, "=========v=" + hasFocus);
            if (v == e_model_val) {
                if (hasFocus) {
                    set_scroller_y(1);
                }
            }
        }
    };

    private void v_update_text_setting()
    {
        int ival;
        float fval;
        float[] flimit;

        if(DEBUG)Log.e(TAG,"=========v_update_text_setting=");

        EngSetting.v_Set_ENG_MDL(e_model_val.getText().toString());
    }

    class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if (v == i_wifi) {
                if (DEBUG) Log.e(TAG, "==========i_wifi========");
                v_update_text_setting();
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_WIFI);
                vClickBackgroud();
            }
            else if (v == i_about) {
                if (DEBUG) Log.e(TAG, "==========i_about========");
                v_update_text_setting();
                //mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_ABOUT);
                vClickBackgroud();
            }
            else if (v == i_setting) {
                if (DEBUG) Log.e(TAG, "==========i_setting========");
                v_update_text_setting();
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_SETTING);
                vClickBackgroud();
            }
            else if (v == i_update) {
                if (DEBUG) Log.e(TAG, "==========i_update========");
                v_update_text_setting();
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_UPDATE);
                vClickBackgroud();
            }
            else if (v == i_exit) {
                if (DEBUG) Log.e(TAG, "==========i_exit========");
                v_update_text_setting();
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_EXIT);
                vClickBackgroud();
            }
            else if (v == i_exit_launcher) {
                if (DEBUG) Log.e(TAG, "==========i_exit_launcher========");
                v_exit_launcher_show();
            }
            else if (v == dialog_ok) {
                if (DEBUG) Log.e(TAG, "==========dialog_ok========");
                v_check_launcher();
            }
            else if (v == dialog_cancel) {
                if (DEBUG) Log.e(TAG, "==========dialog_cancel========");
                v_exit_launcher_hide();
            }
            else if (v == t_error_dayly) {
                if (DEBUG) Log.e(TAG, "==========t_error_dayly========");
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_ERROR);
            }
            else if (v == t_dev_distance_clear) {
                if (DEBUG) Log.e(TAG, "==========t_dev_distance_clear========");
                vEngAboutFrame_DevDistanceClear();
            }
            else if (v == t_dev_time_clear) {
                if (DEBUG) Log.e(TAG, "==========t_dev_time_clear========");
                vEngAboutFrame_DevTimeClear();
            }
        }

    }
}
