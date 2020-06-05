package com.rtx.treadmill.Engmode;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.retonix.circlecloud.Cloud_04_CHK_AVL_MSN_DLR;
import com.retonix.circlecloud.Cloud_27_GET_DEV_BSC;
import com.retonix.circlecloud.cloudglobal;
import com.rtx.treadmill.GlobalData.CloudCmd_Info;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Keyboard;
import com.rtx.treadmill.RtxTools.Rtx_Log;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxEditText;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.lang.reflect.Field;

public class EngSettingFrame extends Rtx_BaseLayout {
    private static String TAG = "Jerry=";
    private final static boolean DEBUG = false;

    Context mContext;
    EngModeActivity mEngModeActivity;

    ButtonListener mButtonListener;

    private ImageView i_eng_background;
    private RtxImageView i_eng_logo;
    private RtxTextView t_eng_mode;
    private RtxImageView i_exit;
    private RtxTextView t_exit;
    private RtxImageView i_line00;

    private RtxImageView i_wifi;
    private RtxImageView i_line01;
    private RtxImageView i_about;
    private RtxImageView i_line02;
    private RtxImageView i_setting;
    private RtxImageView i_line03;
    private RtxImageView i_update;

    private RtxTextView t_wifi;
    private RtxTextView t_about;
    private RtxTextView t_setting;
    private RtxTextView t_update;

    private ScrollView scroller;
    private FrameLayout fscroll;

    private RtxTextView t_gym_id;
    private RtxEditText e_gym_id;
    private RtxTextView t_dealer_id;
    private RtxEditText e_dealer_id;
    private RtxTextView t_machine_id;
    private RtxEditText e_machine_id;

    private RtxTextView t_timezone_offset_ver;
    private RtxTextView t_timezone_offset[];
    private RtxTextView t_timezone_offset_colon;
    private RtxImageView i_timezone_offset[];
    private RtxImageView i_timezone_offset_line;

    private RtxTextView t_country_ver;
    private RtxEditText e_country_val;
    private RtxImageView i_country_line;

    private RtxTextView t_dev_city;
    private RtxEditText e_dev_city;
    private RtxTextView t_weather_interval;
    private RtxEditText e_weather_interval;

    private RtxTextView t_register_ver;
    private RtxTextView t_register_register_ver;
    private RtxTextView t_register_register_val;
    private RtxTextView t_register_connect_ver;
    private RtxTextView t_register_connect_val;
    private RtxTextView t_defaule_set;
    private RtxImageView i_register_register;
    private RtxImageView i_register_connect;
    private RtxImageView i_register_line;
    private RtxImageView i_defaule_set;

    private RtxTextView t_screen_rotaion;

    private RtxTextView t_machine_type;
    private RadioButton mRadio[];

    private FrameLayout ftreadmill;
    private RtxEditText t_speed_min_val;
    private RtxEditText t_speed_max_val;
    private RtxEditText t_speed_val;

    private RtxEditText t_incline_min_val;
    private RtxEditText t_incline_max_val;
    private RtxEditText t_incline_val;

    private RtxEditText t_idle_detect_val;
    private RtxEditText t_HZ_parameter_val;
    private RtxEditText t_km_parameter_val;
    private RtxEditText t_parameter1_val;
    private RtxEditText t_zero_parameter_val;

    private RtxEditText t_add_val;
    private RtxEditText t_sub_val;

    private FrameLayout fbike_ep;
    private RtxEditText t_level_min_val;
    private RtxEditText t_level_max_val;
    private RtxEditText t_level_val;

    private RtxEditText t_increment_duty_val;
    private RtxEditText[] t_torqe_val;
    private RtxEditText[] t_resistance_val ;

    private int imax_torqe = EngSetting.fmachine_tq.length;;
    private int imax_Radio = 17;
    private int imax_TimezoneOffset = 5;

    //limite data
    float[] fspeed_min_limit = EngSetting.fSpeed_min_array;
    float[] fspeed_max_limit = EngSetting.fSpeed_max_array;

    float[] fincline_min_limit = EngSetting.fIncline_min_array;
    float[] fincline_max_limit = EngSetting.fIncline_max_array;

    float[] fidle_detect_limit = EngSetting.fidle_detect_limit;
    float[] fHZ_parameter_limit = EngSetting.fHZ_parameter_limit;
    float[] fkm_parameter_limit = EngSetting.fkm_parameter_limit;
    float[] fkm_parameter1_limit = EngSetting.fkm_parameter1_limit;
    float[] fkm_zero_parameter_limit = EngSetting.fkm_zero_parameter_limit;

    float[] flevel_min_limit = EngSetting.flevel_min_array;
    float[] flevel_max_limit = EngSetting.flevel_max_array;

    float[] fincrement_duty_limit = EngSetting.fincrement_duty_limit;
    float[] ftorqe_limit = EngSetting.ftorqe_limit;
    float[] fresistance_limit = EngSetting.fresistance_limit;

    float[] fweather_interval_limit = EngSetting.fweather_interval_limit;

    float[] fspeed_add_limit = EngSetting.fspeed_add_limit;
    float[] fspeed_sub_limit = EngSetting.fspeed_sub_limit;

    public EngSettingFrame(Context context, EngModeActivity mEngModeActivity) {
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

        v_EngSettingFrame_Refresh_data();

        //Rtx_Keyboard.openSoftKeybord(this,mContext);

    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    private void v_RadioBut_Fresh()
    {
        int iLoop;
        int ival;

        for(iLoop = 0; iLoop < imax_Radio; iLoop++)
        {
            mRadio[iLoop].setChecked(false);
        }

        ival = EngSetting.getUnit();
        if (DEBUG) Log.e(TAG, "===v_RadioBut_Fresh====Unit=" + ival);
        if(ival == EngSetting.UNIT_METRIC)
        {
            mRadio[0].setChecked(true);
        }
        else if(ival == EngSetting.UNIT_IMPERIAL)
        {
            mRadio[1].setChecked(true);
        }

        ival = EngSetting.i_Get_ExerciseType();
        if (DEBUG) Log.e(TAG, "===v_RadioBut_Fresh====i_Get_ExerciseType=" + ival);

        if(ival == EngSetting.RUNNING)
        {
            mRadio[2].setChecked(true);
            fbike_ep.setVisibility(GONE);
            ftreadmill.setVisibility(VISIBLE);
        }
        else if(ival == EngSetting.UBIKING)
        {
            mRadio[3].setChecked(true);
            fbike_ep.setVisibility(VISIBLE);
            ftreadmill.setVisibility(GONE);
        }
        else if(ival == EngSetting.RBIKING)
        {
            mRadio[4].setChecked(true);
            fbike_ep.setVisibility(VISIBLE);
            ftreadmill.setVisibility(GONE);
        }
        else if(ival == EngSetting.ELLIPTICAL)
        {
            mRadio[5].setChecked(true);
            fbike_ep.setVisibility(VISIBLE);
            ftreadmill.setVisibility(GONE);
        }
        else if(ival == EngSetting.RUNNING6)
        {
            mRadio[11].setChecked(true);
            fbike_ep.setVisibility(GONE);
            ftreadmill.setVisibility(VISIBLE);
        }
        else if(ival == EngSetting.RUNNING7)
        {
            mRadio[12].setChecked(true);
            fbike_ep.setVisibility(GONE);
            ftreadmill.setVisibility(VISIBLE);
        }
        else if(ival == EngSetting.UBIKING6)
        {
            mRadio[14].setChecked(true);
            fbike_ep.setVisibility(VISIBLE);
            ftreadmill.setVisibility(GONE);
        }
        else if(ival == EngSetting.RBIKING6)
        {
            mRadio[15].setChecked(true);
            fbike_ep.setVisibility(VISIBLE);
            ftreadmill.setVisibility(GONE);
        }
        else if(ival == EngSetting.ELLIPTICAL6)
        {
            mRadio[16].setChecked(true);
            fbike_ep.setVisibility(VISIBLE);
            ftreadmill.setVisibility(GONE);
        }

        v_refresh_treadmill();
        v_refresh_bike();

        ival = EngSetting.i_Get_Screen_Rotation();
        Log.e(TAG, "=====i_Get_Screen_Rotation===== " + ival);
        if(ival == EngSetting.iROTATION_AUTO)
        {
            mRadio[6].setChecked(true);
        }
        else if(ival == EngSetting.iROTATION_00)
        {
            mRadio[7].setChecked(true);
        }
        else if(ival == EngSetting.iROTATION_90)
        {
            mRadio[8].setChecked(true);
        }
        else if(ival == EngSetting.iROTATION_180)
        {
            mRadio[9].setChecked(true);
        }
        else if(ival == EngSetting.iROTATION_270)
        {
            mRadio[10].setChecked(true);
        }


    }

    private void v_refresh_treadmill()
    {
        float fval;
        String sperf_val;
        String sdata;

        if(t_speed_min_val != null)
        {
            sdata = EngSetting.Distance.getValString(EngSetting.f_Get_Min_Speed(), 1);
            t_speed_min_val.setText(sdata);
        }

        if(t_speed_max_val != null)
        {
            sdata = EngSetting.Distance.getValString(EngSetting.f_Get_Max_Speed(), 1);
            t_speed_max_val.setText(sdata);
        }

        if(t_speed_val != null)
        {
            sdata = EngSetting.Distance.getValString(EngSetting.f_Get_Def_Speed(), 1);
            t_speed_val.setText(sdata);
        }

        if(t_incline_min_val != null)
        {
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Min_Incline(), 1);
            t_incline_min_val.setText(sdata);
        }

        if(t_incline_max_val != null)
        {
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Max_Incline(), 1);
            t_incline_max_val.setText(sdata);
        }

        if(t_incline_val != null)
        {
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Def_Incline(), 1);
            t_incline_val.setText(sdata);
        }

        if(t_idle_detect_val != null && t_HZ_parameter_val != null && t_km_parameter_val != null)
        {
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_idle_detect(), 0);
            t_idle_detect_val.setText(sdata);
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_HZ_parameter(), 0);
            t_HZ_parameter_val.setText(sdata);
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_1km_parameter(), 0);
            t_km_parameter_val.setText(sdata);
        }

        if(t_parameter1_val != null)
        {
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_parameter1(), 0);
            t_parameter1_val.setText(sdata);
        }

        if(t_zero_parameter_val != null)
        {
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_zero_parameter(), 0);
            t_zero_parameter_val.setText(sdata);
        }

        if(t_add_val != null)
        {
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fspeed_add_val(), 1);
            t_add_val.setText(sdata);
        }

        if(t_sub_val != null)
        {
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fspeed_sub_val(), 1);
            t_sub_val.setText(sdata);
        }

    }

    private void v_refresh_bike()
    {
        float fval;

        int iLoop, iLoop_max;
        String sdata;

        if(t_level_min_val != null)
        {
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Min_level(), 0);
            t_level_min_val.setText(sdata);
        }

        if(t_level_max_val != null)
        {
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Max_level(), 0);
            t_level_max_val.setText(sdata);
        }

        if(t_level_val != null)
        {
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_Def_level(), 0);
            t_level_val.setText(sdata);
        }

        if(fbike_ep != null && t_increment_duty_val != null)
        {
            sdata = Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_increment_duty(), 1);
            t_increment_duty_val.setText(sdata);

            iLoop_max = EngSetting.fmachine_tq.length;

            if(EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING6)
            {
                for (iLoop = 0; iLoop < iLoop_max; iLoop++) {
                    EngSetting.v_Set_fmachine_tq(iLoop, EngSetting.f_Get_fmachine_tq_ubike(iLoop));
                    t_torqe_val[iLoop].setText(Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_tq(iLoop), 0));

                    EngSetting.v_Set_fmachine_rn(iLoop, EngSetting.f_Get_fmachine_rn_ubike(iLoop));
                    t_resistance_val[iLoop].setText(Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_rn(iLoop), 0));
                }
            }

            if(EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING6)
            {
                for (iLoop = 0; iLoop < iLoop_max; iLoop++) {
                    EngSetting.v_Set_fmachine_tq(iLoop, EngSetting.f_Get_fmachine_tq_rbike(iLoop));
                    t_torqe_val[iLoop].setText(Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_tq(iLoop), 0));

                    EngSetting.v_Set_fmachine_rn(iLoop, EngSetting.f_Get_fmachine_rn_rbike(iLoop));
                    t_resistance_val[iLoop].setText(Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_rn(iLoop), 0));
                }
            }

            if(EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL || EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL6)
            {
                for (iLoop = 0; iLoop < iLoop_max; iLoop++) {
                    EngSetting.v_Set_fmachine_tq(iLoop, EngSetting.f_Get_fmachine_tq_elliptical(iLoop));
                    t_torqe_val[iLoop].setText(Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_tq(iLoop), 0));

                    EngSetting.v_Set_fmachine_rn(iLoop, EngSetting.f_Get_fmachine_rn_elliptical(iLoop));
                    t_resistance_val[iLoop].setText(Rtx_TranslateValue.sFloat2String(EngSetting.f_Get_fmachine_rn(iLoop), 0));
                }
            }

        }

    }

    private void v_TimezoneOffsetBut_Fresh()
    {
        if (DEBUG) Log.e(TAG, "v_TimezoneOffsetBut_Fresh");
        t_timezone_offset[0].setText(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(0));
        t_timezone_offset[1].setText(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(1));
        t_timezone_offset[2].setText(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(2));
        t_timezone_offset[3].setText(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(3));
        t_timezone_offset[4].setText(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(4));
    }

    private void v_Register_Fresh()
    {
        if (DEBUG) Log.e(TAG, "v_Register_Fresh");
        if(EngSetting.s_Get_DEFAULT_REGISTER_REGISTER().equals("OK"))
        {
            t_register_register_val.setText(mContext.getResources().getString(R.string.engregister_ok));
        }
        else if(EngSetting.s_Get_DEFAULT_REGISTER_REGISTER().equals("Fail!"))
        {
//            EngSetting.v_Set_DEFAULT_REGISTER_REGISTER("");
            t_register_register_val.setText(mContext.getResources().getString(R.string.engregister_fail));
        }
    }

    private void v_Connect_Fresh()
    {
        if (DEBUG) Log.e(TAG, "v_Connect_Fresh");
        if(EngSetting.s_Get_DEFAULT_RTGISTER_CONNECT().equals("OK"))
        {
            t_register_connect_val.setText(mContext.getResources().getString(R.string.engconnected));
        }
        else if(EngSetting.s_Get_DEFAULT_RTGISTER_CONNECT().equals("Fail!"))
        {
//            EngSetting.v_Set_DEFAULT_REGISTER_CONNECT("");
            t_register_connect_val.setText(mContext.getResources().getString(R.string.engconnect_fail));
        }
    }

    public void v_EngSettingFrame_Refresh_data() {
        if (DEBUG) Log.e(TAG, "Refresh_data");
        String sdata;

        sdata = EngSetting.s_Get_ENG_GYM_ID();
        e_gym_id.setText(sdata);

        sdata = EngSetting.s_Get_ENG_OWN_DLR();
        e_dealer_id.setText(sdata);

        sdata = EngSetting.s_Get_ENG_DEV_MSN();
        e_machine_id.setText(sdata);

        v_TimezoneOffsetBut_Fresh();

        sdata = EngSetting.s_Get_DEFAULT_COUNTRY();
        e_country_val.setText(sdata);

        sdata = EngSetting.s_Get_DEFAULT_CITY();
        e_dev_city.setText(sdata);

        sdata = Rtx_TranslateValue.sInt2String(EngSetting.i_Get_Weather_Check_interval());
        e_weather_interval.setText(sdata);

        v_Register_Fresh();
        v_Connect_Fresh();

        v_RadioBut_Fresh();

//        vEngSettingFrame_Register();
//
//        vEngSettingFrame_Connect();

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

    private void add_view(FrameLayout mFram, FrameLayout mFram_child, int iX , int iY , int iWidth , int iHeight) {
        LayoutParams mLayoutParams = new LayoutParams(iWidth,iHeight);
        mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;
        mLayoutParams.leftMargin = iX;
        mLayoutParams.topMargin = iY;
        mFram_child.setLayoutParams(mLayoutParams);
        //mView.setOnCheckedChangeListener(sw_unit_but);

    }

    private void setTextArg(RadioButton view, float fSize, int iColor, String sFont, int style, int gravity)
    {
        view.setGravity(gravity);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,fSize);
        view.setTextColor(iColor);
        view.setTypeface(view.getTypeface(), style);//Typeface.BOLD
        view.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), sFont));
    }

    private void add_view(FrameLayout mFram, RadioButton mView ,String sdata, int iResid, float fSize, int iColor, String sFont, int style, int gravity, int iX , int iY , int iWidth , int iHeight) {
        mView.setButtonDrawable(iResid);
        mView.setPadding(20, 0, 0, 0);
        mView.setText(sdata);

        setTextArg(mView, fSize, iColor, sFont, style, gravity);

        LayoutParams mLayoutParams = new LayoutParams(iWidth,iHeight);
        mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;
        mLayoutParams.leftMargin = iX;
        mLayoutParams.topMargin = iY;
        mView.setLayoutParams(mLayoutParams);

    }

    private void init_View()
    {
        int iLoop;

        if(i_eng_background == null)           {i_eng_background = new ImageView(mContext);}
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
        if(t_gym_id == null)           {t_gym_id = new RtxTextView(mContext);}
        if(e_gym_id == null)           {e_gym_id = new RtxEditText(mContext);}
        if(t_dealer_id == null)           {t_dealer_id = new RtxTextView(mContext);}
        if(e_dealer_id == null)           {e_dealer_id = new RtxEditText(mContext);}
        if(t_machine_id == null)           {t_machine_id = new RtxTextView(mContext);}
        if(e_machine_id == null)           {e_machine_id = new RtxEditText(mContext);}

        if(t_timezone_offset_ver == null)           {t_timezone_offset_ver = new RtxTextView(mContext);}
        if(t_timezone_offset_colon == null)           {t_timezone_offset_colon = new RtxTextView(mContext);}
        if(t_timezone_offset == null)           {t_timezone_offset = new RtxTextView[imax_TimezoneOffset];
        if(t_timezone_offset[0] == null)           {t_timezone_offset[0] = new RtxTextView(mContext);}
        if(t_timezone_offset[1] == null)           {t_timezone_offset[1] = new RtxTextView(mContext);}
        if(t_timezone_offset[2] == null)           {t_timezone_offset[2] = new RtxTextView(mContext);}
        if(t_timezone_offset[3] == null)           {t_timezone_offset[3] = new RtxTextView(mContext);}
        if(t_timezone_offset[4] == null)           {t_timezone_offset[4] = new RtxTextView(mContext);}}
        if(i_timezone_offset == null)           {i_timezone_offset = new RtxImageView[imax_TimezoneOffset];
        if(i_timezone_offset[0] == null)           {i_timezone_offset[0] = new RtxImageView(mContext);}
        if(i_timezone_offset[1] == null)           {i_timezone_offset[1] = new RtxImageView(mContext);}
        if(i_timezone_offset[2] == null)           {i_timezone_offset[2] = new RtxImageView(mContext);}
        if(i_timezone_offset[3] == null)           {i_timezone_offset[3] = new RtxImageView(mContext);}
        if(i_timezone_offset[4] == null)           {i_timezone_offset[4] = new RtxImageView(mContext);}}
        if(i_timezone_offset_line == null)           {i_timezone_offset_line = new RtxImageView(mContext);}

        if(t_country_ver == null)           {t_country_ver = new RtxTextView(mContext);}
        if(e_country_val == null)           {e_country_val = new RtxEditText(mContext);}
        if(i_country_line == null)           {i_country_line = new RtxImageView(mContext);}

        if(t_dev_city == null)           {t_dev_city = new RtxTextView(mContext);}
        if(e_dev_city == null)           {e_dev_city = new RtxEditText(mContext);}
        if(t_weather_interval == null)           {t_weather_interval = new RtxTextView(mContext);}
        if(e_weather_interval == null)           {e_weather_interval = new RtxEditText(mContext);}

        if(t_register_ver == null)           {t_register_ver = new RtxTextView(mContext);}
        if(t_register_register_ver == null)           {t_register_register_ver = new RtxTextView(mContext);}
        if(t_register_register_val == null)           {t_register_register_val = new RtxTextView(mContext);}
        if(t_register_connect_ver == null)           {t_register_connect_ver = new RtxTextView(mContext);}
        if(t_register_connect_val == null)           {t_register_connect_val = new RtxTextView(mContext);}
        if(t_defaule_set == null)           {t_defaule_set = new RtxTextView(mContext);}
        if(i_register_register == null)           {i_register_register = new RtxImageView(mContext);}
        if(i_defaule_set == null)           {i_defaule_set = new RtxImageView(mContext);}
        if(i_register_connect == null)           {i_register_connect = new RtxImageView(mContext);}
        if(i_register_line == null)           {i_register_line = new RtxImageView(mContext);}

        if(t_screen_rotaion == null)           {t_screen_rotaion = new RtxTextView(mContext);}
        if(t_machine_type == null)           {t_machine_type = new RtxTextView(mContext);}
        if(mRadio == null)           {
            mRadio = new RadioButton[imax_Radio];
            for(iLoop = 0; iLoop < imax_Radio; iLoop++)
            {
                mRadio[iLoop] = new RadioButton(mContext);
                fscroll.addView(mRadio[iLoop]);
            }
        }

        //frame Treadmill
        if(ftreadmill == null)           {
            ftreadmill = new FrameLayout(mContext);
            fscroll.addView(ftreadmill);
        }
        if(t_speed_min_val == null)           {t_speed_min_val = new RtxEditText(mContext);}
        if(t_speed_max_val == null)           {t_speed_max_val = new RtxEditText(mContext);}
        if(t_speed_val == null)           {t_speed_val = new RtxEditText(mContext);}

        if(t_incline_min_val == null)           {t_incline_min_val = new RtxEditText(mContext);}
        if(t_incline_max_val == null)           {t_incline_max_val = new RtxEditText(mContext);}
        if(t_incline_val == null)           {t_incline_val = new RtxEditText(mContext);}

        if(t_idle_detect_val == null)           {t_idle_detect_val = new RtxEditText(mContext);}
        if(t_HZ_parameter_val == null)           {t_HZ_parameter_val = new RtxEditText(mContext);}
        if(t_km_parameter_val == null)           {t_km_parameter_val = new RtxEditText(mContext);}
        if(t_parameter1_val == null)           {t_parameter1_val = new RtxEditText(mContext);}
        if(t_zero_parameter_val == null)           {t_zero_parameter_val = new RtxEditText(mContext);}
        if(t_add_val == null)           {t_add_val = new RtxEditText(mContext);}
        if(t_sub_val == null)           {t_sub_val = new RtxEditText(mContext);}

        //frame Bike
        if(fbike_ep == null)           {
            fbike_ep = new FrameLayout(mContext);
            fscroll.addView(fbike_ep);
        }
        if(t_level_min_val == null)           {t_level_min_val = new RtxEditText(mContext);}
        if(t_level_max_val == null)           {t_level_max_val = new RtxEditText(mContext);}
        if(t_level_val == null)           {t_level_val = new RtxEditText(mContext);}
        if(t_increment_duty_val == null)           {t_increment_duty_val = new RtxEditText(mContext);}

        if(t_torqe_val == null)
        {
            t_torqe_val = new RtxEditText[imax_torqe];
            for(iLoop = 0; iLoop < imax_torqe; iLoop++)
            {
                t_torqe_val[iLoop] = new RtxEditText(mContext);
            }
        }

        if(t_resistance_val == null)
        {
            t_resistance_val = new RtxEditText[imax_torqe];
            for(iLoop = 0; iLoop < imax_torqe; iLoop++)
            {
                t_resistance_val[iLoop] = new RtxEditText(mContext);
            }
        }

    }

    private void init_event()
    {
        int iLoop;

        i_eng_background.setOnClickListener(mButtonListener);
        i_wifi.setOnClickListener(mButtonListener);
        i_about.setOnClickListener(mButtonListener);
        i_setting.setOnClickListener(mButtonListener);
        i_update.setOnClickListener(mButtonListener);
        i_exit.setOnClickListener(mButtonListener);

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

        i_register_register.setOnClickListener(mButtonListener);
        i_defaule_set.setOnClickListener(mButtonListener);
        t_register_register_ver.setEnabled(false);
        t_register_register_ver.setClickable(false);
        t_defaule_set.setEnabled(false);
        t_defaule_set.setClickable(false);
        i_register_connect.setOnClickListener(mButtonListener);
        i_defaule_set.setOnClickListener(mButtonListener);
        t_register_connect_ver.setEnabled(false);
        t_register_connect_ver.setClickable(false);


        e_gym_id.setOnFocusChangeListener(edittext_focus);
        e_dealer_id.setOnFocusChangeListener(edittext_focus);
        e_machine_id.setOnFocusChangeListener(edittext_focus);
        e_country_val.setOnFocusChangeListener(edittext_focus);
        e_dev_city.setOnFocusChangeListener(edittext_focus);
        e_weather_interval.setOnFocusChangeListener(edittext_focus);

        t_speed_min_val.setOnFocusChangeListener(edittext_focus);
        t_speed_max_val.setOnFocusChangeListener(edittext_focus);
        t_speed_val.setOnFocusChangeListener(edittext_focus);

        t_incline_min_val.setOnFocusChangeListener(edittext_focus);
        t_incline_max_val.setOnFocusChangeListener(edittext_focus);
        t_incline_val.setOnFocusChangeListener(edittext_focus);

        t_idle_detect_val.setOnFocusChangeListener(edittext_focus);
        t_HZ_parameter_val.setOnFocusChangeListener(edittext_focus);
        t_km_parameter_val.setOnFocusChangeListener(edittext_focus);
        t_parameter1_val.setOnFocusChangeListener(edittext_focus);
        t_zero_parameter_val.setOnFocusChangeListener(edittext_focus);
        t_add_val.setOnFocusChangeListener(edittext_focus);
        t_sub_val.setOnFocusChangeListener(edittext_focus);

        t_level_min_val.setOnFocusChangeListener(edittext_focus);
        t_level_max_val.setOnFocusChangeListener(edittext_focus);
        t_level_val.setOnFocusChangeListener(edittext_focus);
        t_increment_duty_val.setOnFocusChangeListener(edittext_focus);

        for(iLoop = 0; iLoop < imax_torqe; iLoop++)
        {
            t_torqe_val[iLoop].setOnFocusChangeListener(edittext_focus);
            t_resistance_val[iLoop].setOnFocusChangeListener(edittext_focus);
        }

        for(iLoop = 0; iLoop < imax_Radio; iLoop++)
        {
            mRadio[iLoop].setOnClickListener(mButtonListener);
        }

        for(iLoop = 0; iLoop < imax_TimezoneOffset; iLoop++)
        {
            i_timezone_offset[iLoop].setOnClickListener(mButtonListener);
            t_timezone_offset[iLoop].setEnabled(false);
            t_timezone_offset[iLoop].setClickable(false);
        }
    }

    private void add_View()
    {
        if (DEBUG) Log.e(TAG, "init_view");
        int ix;
        int iy;
        int iw;
        int ih;
        float fsize;
        int ix_temp;
        int iy_temp;
        int iw_temp;
        int ih_temp;
        int isize_temp;
        String sdata;
        int iLoop;

        ix = 0;
        iy = 0;
        iw = 1280;
        ih = 800;
//        addRtxImageViewToLayout(i_eng_background, R.drawable.eng_background, ix, iy, iw, ih);
        i_eng_background.setImageResource(R.drawable.eng_background);
        addViewToLayout(i_eng_background, ix, iy, iw, ih);

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

        ih = 525/4+5;
        addRtxImageViewToLayout(i_about, -1, ix, iy, iw, ih);

        iy += 525/4;
        ih = 10;
        addRtxImage(null, i_line02, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        ih = 525/4;
        iy += 5;
        addRtxImageViewToLayout(i_setting, R.drawable.eng_but, ix, iy, iw, ih);
        i_setting.setScaleType(ImageView.ScaleType.FIT_XY);
        iy -= 5;
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
        addRtxTextViewToLayout(t_about, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 525/4;
        sdata = mContext.getResources().getString(R.string.engsetting);
        addRtxTextViewToLayout(t_setting, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 525/4;
        sdata = mContext.getResources().getString(R.string.engupdate);
        addRtxTextViewToLayout(t_update, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        ix = 285;
        iy = 190;
        iw = 945;//995;
        ih = 500;
        add_scrollview(scroller, ix, iy, iw, ih);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //gym id
        ix = 50;
        iy = 50;
        iw = 350;//250;
        ih = 50;//60;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.enggym_id);
        addRtxTextView(fscroll, t_gym_id, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;//260;
        iw = 500;//600;
        addRtxEditView(fscroll, e_gym_id, InputType.TYPE_CLASS_TEXT , ix, iy, iw, ih);

        iy += 50;
        ih = 10;

        //dealer id
        ix = 50;
        iy += 50;//80;
        iw = 350;//250;
        ih = 50;
        sdata = mContext.getResources().getString(R.string.engdealer_id);
        addRtxTextView(fscroll, t_dealer_id, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;//260;
        iw = 500;//600;
        addRtxEditView(fscroll, e_dealer_id, InputType.TYPE_CLASS_TEXT , ix, iy, iw, ih);

        iy += 50;
        ih = 10;

        //machine id
        ix = 50;
        iy += 50;//80;
        iw = 350;//250;
        ih = 50;
        sdata = mContext.getResources().getString(R.string.engmachine_id);
        addRtxTextView(fscroll, t_machine_id, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;//260;
        iw = 500;//600;
        addRtxEditView(fscroll, e_machine_id, InputType.TYPE_CLASS_TEXT , ix, iy, iw, ih);

        iy += 50;
        ih = 10;

        //Timezone Offset
        ix = 50;
        iy +=50;//iy = 265;6
        iw = 350;
        ih = 50;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.engtimezone_offset);
        addRtxTextView(fscroll, t_timezone_offset_ver, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 370;//360;
        iy -=15;
        iw = 80;
        ih = 80;
        addRtxImage(fscroll, i_timezone_offset[0], R.drawable.eng_time, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);
        addRtxTextView(fscroll, t_timezone_offset[0], sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += 100;
        iw = 80;
        ih = 80;
        addRtxImage(fscroll, i_timezone_offset[1], R.drawable.eng_time, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);
        addRtxTextView(fscroll, t_timezone_offset[1], sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += 100;
        iw = 80;
        ih = 80;
        addRtxImage(fscroll, i_timezone_offset[2], R.drawable.eng_time, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);
        addRtxTextView(fscroll, t_timezone_offset[2], sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += 90;
        iw = 10;
        sdata = mContext.getResources().getString(R.string.engtimezone_offset_colon);
        addRtxTextView(fscroll, t_timezone_offset_colon, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += 20;
        iw = 80;
        ih = 80;
        addRtxImage(fscroll, i_timezone_offset[3], R.drawable.eng_time, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);
        addRtxTextView(fscroll, t_timezone_offset[3], sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += 100;
        iw = 80;
        ih = 80;
        addRtxImage(fscroll, i_timezone_offset[4], R.drawable.eng_time, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);
        addRtxTextView(fscroll, t_timezone_offset[4], sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        iy += 15;

        iy += 50;
        ih = 10;

        //Country
        ix = 50;
        iy +=50;//iy = 265;
        iw = 350;
        ih = 50;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.engcountry);
        addRtxTextView(fscroll, t_country_ver, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;
        iw = 500;
        addRtxEditView(fscroll, e_country_val, InputType.TYPE_CLASS_TEXT , ix, iy, iw, ih);

        iy += 50;
        ih = 10;

        //city
        ix = 50;
        iy += 50;//80;
        iw = 350;//300;
        ih = 50;
        sdata = mContext.getResources().getString(R.string.engdevice_city);
        addRtxTextView(fscroll, t_dev_city, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;//310;
        iw = 500;//550;
        addRtxEditView(fscroll, e_dev_city, InputType.TYPE_CLASS_TEXT , ix, iy, iw, ih);

        iy += 50;
        ih = 10;

        //weather interval
        ix = 50;
        iy += 50;//80;
        iw = 350;//450;
        ih = 50;
        sdata = mContext.getResources().getString(R.string.engweather_interval);
        addRtxTextView(fscroll, t_weather_interval, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;//460;
        iw = 500;//400;
        addRtxEditView(fscroll, e_weather_interval, InputType.TYPE_CLASS_TEXT , ix, iy, iw, ih);

        iy += 50;
        ih = 10;

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Register
        ix = 50;
        iy +=50;//iy = 265;
        iw = 350;
        ih = 50;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.engregister_colon);
        addRtxTextView(fscroll, t_register_ver, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;
        iw = 500;
//        addRtxTextView(fscroll, t_register_val, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 20;
        ih = 10;

        ix = 50+50;
        iy +=50;//iy = 265;
        iw = 150;
        ih = 50;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.engregister);
        addRtxImage(fscroll, i_register_register, R.drawable.eng_time, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);
        addRtxTextView(fscroll, t_register_register_ver, sdata, fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);



        ix += 160;
        iw = 700-50;
        sdata = "";
        addRtxTextView(fscroll, t_register_register_val, sdata, fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 20;
        ih = 10;
//        addRtxImage(fscroll, i_register_line, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        ix = 50+50;
        iy +=50;//iy = 265;
        iw = 150;
        ih = 50;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.engconnect);
        addRtxImage(fscroll, i_register_connect, R.drawable.eng_time, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);
        addRtxTextView(fscroll, t_register_connect_ver, sdata, fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += 160;
        iw = 700-50;
        sdata = "";
        addRtxTextView(fscroll, t_register_connect_val, sdata, fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 20;
        ih = 10;
//        addRtxImage(fscroll, i_register_line, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        //screen rotation
        ix = 50;
        iy += 50;//80;
        iw = 450;
        ih = 50;
        sdata = "Unit:";
        addRtxTextView(fscroll, t_screen_rotaion, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        //t_screen_rotaion.setVisibility(INVISIBLE);

        ix = 50;
        iy += 0;
        iw = 900;
        iw_temp = iw / 3;
        fsize = 30f;
        for(iLoop = 6; iLoop < imax_Radio; iLoop++)
        {
            if (iLoop == 6) {
                sdata = "AUTO";
            } else if (iLoop == 7) {
                sdata = "ROTATE00";
            } else if (iLoop == 8) {
                sdata = "ROTATE90";
            } else if (iLoop == 9) {
                sdata = "ROTATE180";
            } else if (iLoop == 10) {
                sdata = "ROTATE270";
            }

            add_view(fscroll, mRadio[iLoop], sdata, R.xml.radio_small, fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw_temp, ih);
            if (iLoop == 6 || iLoop == 8 || iLoop == 10) {
                ix += (iw_temp);
            }
        }
        mRadio[6].setVisibility(INVISIBLE);
        mRadio[7].setVisibility(INVISIBLE);
        mRadio[8].setVisibility(INVISIBLE);
        mRadio[9].setVisibility(INVISIBLE);
        mRadio[10].setVisibility(INVISIBLE);

        //Unit
        ix =  50;
        iy += 50;
        iw = 980;
        iw_temp = iw / 4;
        fsize = 30f;
        for(iLoop = 0; iLoop < 2; iLoop++)
        {
            if(iLoop == 0)
            {
                sdata = mContext.getResources().getString(R.string.metric);
            }
            else if(iLoop == 1)
            {
                sdata = mContext.getResources().getString(R.string.imperial);

            }
            add_view(fscroll, mRadio[iLoop], sdata, R.xml.radio_small, fsize, Common.Color.engmode_word_white,Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw_temp, ih);
            ix += (iw_temp * 2);
        }

        ix = 50;
        iy += 100;
        iw = 500;
        sdata = mContext.getResources().getString(R.string.engmachine_type);
        addRtxTextView(fscroll, t_machine_type, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT, ix, iy, iw, ih);


        ix = 50;
        iy += 50;
        iw = 950;
        iw_temp = iw / 3;
        fsize = 30f;
        for(iLoop = 11; iLoop < 14; iLoop++)
        {
            if(iLoop == 11)
            {
                sdata = "Treadmill_M6";
            }
            else if(iLoop == 12)
            {
                sdata = "Treadmill_M7";
            }
            else if(iLoop == 13)
            {
                sdata =  mContext.getResources().getString(R.string.engtreadmill);
            }

            if(iLoop==13)
            {
                add_view(fscroll, mRadio[2], sdata, R.xml.radio_small, fsize, Common.Color.engmode_word_white,Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw_temp, ih);
            }else{

                add_view(fscroll, mRadio[iLoop], sdata, R.xml.radio_small, fsize, Common.Color.engmode_word_white,Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw_temp, ih);
            }

            //add_view(fscroll, mRadio[iLoop], sdata, R.xml.radio_small, fsize, Common.Color.engmode_word_white,Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw_temp, ih);
            ix += (iw_temp);
        }

        ix = 50;
        iy += 60;
        iw = 950;
        iw_temp = iw / 3;
        fsize = 30f;
        for(iLoop = 14; iLoop < 17; iLoop++)
        {
            if(iLoop == 14)
            {
                sdata = mContext.getResources().getString(R.string.engubike)+"_B6";
            }
            else if(iLoop == 15)
            {
                sdata = mContext.getResources().getString(R.string.engrbike)+"_R6";
            }
            else if(iLoop == 16)
            {
                sdata = mContext.getResources().getString(R.string.engelliptical)+"_E6";
            }
            add_view(fscroll, mRadio[iLoop], sdata, R.xml.radio_small, fsize, Common.Color.engmode_word_white,Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw_temp, ih);
            ix += (iw_temp);
        }

        ix = 50;
        iy += 60;
        iw = 950;
        iw_temp = iw / 3;
        fsize = 30f;
        for(iLoop = 3; iLoop < 6; iLoop++)
        {
            if(iLoop == 3)
            {
                sdata = mContext.getResources().getString(R.string.engubike)+"_B7";
            }
            else if(iLoop == 4)
            {
                sdata = mContext.getResources().getString(R.string.engrbike)+"_R7";
            }
            else if(iLoop == 5)
            {
                sdata = mContext.getResources().getString(R.string.engelliptical)+"_E7";
            }
            add_view(fscroll, mRadio[iLoop], sdata, R.xml.radio_small, fsize, Common.Color.engmode_word_white,Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix, iy, iw_temp, ih);
            ix += (iw_temp);
        }



        //Treadmill Frame
        ix = 0;
        iy += 60;
        iw = 900;
        ih = 1040;//80+80+80+80+80+80+80+80+80+80+(240鍵盤空間)
        add_view(fscroll, ftreadmill, ix, iy, iw, ih);

        //U-Bike, R-Bike, EP Frame
        ih = 2050;//80+80+80+60+(60*25)+10+(240鍵盤空間)
        add_view(fscroll, fbike_ep, ix, iy, iw, ih);

        //Treadmill parameter
        ix = 0;
        iy = 0;
        iw_temp = iw / 4;
        ih = 60;
        fsize = 28f;
        ix += iw_temp;

        addRtxImage(fscroll, i_defaule_set, R.drawable.eng_exit, 50, 1350, 130, 49, 0, ImageView.ScaleType.FIT_XY);
        addRtxTextView(fscroll, t_defaule_set, "SET DEF", 25, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 50, 1350, 130, 49);


        RtxTextView tspeed_min = new RtxTextView(mContext);
        sdata = "min";
        addRtxTextView(ftreadmill, tspeed_min, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        RtxTextView tspeed_max = new RtxTextView(mContext);
        sdata = "max";
        addRtxTextView(ftreadmill, tspeed_max, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        RtxTextView tspeed_def = new RtxTextView(mContext);
        sdata = "def";
        addRtxTextView(ftreadmill, tspeed_def, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        //F13, 65 Speed default, min, max
        ix = 0;
        iy += 80;
        RtxTextView tspeed = new RtxTextView(mContext);
        sdata = mContext.getResources().getString(R.string.speed)+":";
        addRtxTextView(ftreadmill, tspeed, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        addRtxEditView(ftreadmill, t_speed_min_val, InputType.TYPE_NUMBER_FLAG_DECIMAL , ix, iy, iw_temp-10, ih);

        ix += iw_temp;
        addRtxEditView(ftreadmill, t_speed_max_val, InputType.TYPE_NUMBER_FLAG_DECIMAL , ix, iy, iw_temp-10, ih);

        ix += iw_temp;
        addRtxEditView(ftreadmill, t_speed_val, InputType.TYPE_NUMBER_FLAG_DECIMAL , ix, iy, iw_temp-10, ih);

        //F14, 65 incline default, min, max
        ix = 0;
        iy += 80;
        RtxTextView tincline = new RtxTextView(mContext);
        sdata = mContext.getResources().getString(R.string.incline)+":";
        addRtxTextView(ftreadmill, tincline, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        addRtxEditView(ftreadmill, t_incline_min_val, InputType.TYPE_NUMBER_FLAG_DECIMAL , ix, iy, iw_temp-10, ih);

        ix += iw_temp;
        addRtxEditView(ftreadmill, t_incline_max_val, InputType.TYPE_NUMBER_FLAG_DECIMAL , ix, iy, iw_temp-10, ih);

        ix += iw_temp;
        addRtxEditView(ftreadmill, t_incline_val, InputType.TYPE_NUMBER_FLAG_DECIMAL , ix, iy, iw_temp-10, ih);

        //F18 Treadmill Machine Type Write parameter
        ix = 0;
        iy += 80;
        iw_temp = iw/2;
        RtxTextView tidle_detect = new RtxTextView(mContext);
        sdata = "Idle Dectect:";
        addRtxTextView(ftreadmill, tidle_detect, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        addRtxEditView(ftreadmill, t_idle_detect_val, InputType.TYPE_CLASS_NUMBER , ix, iy, iw_temp-10, ih);

        ix = 0;
        iy += 80;
        RtxTextView tHZ_parameter = new RtxTextView(mContext);
        sdata = "Hz Parameter:";
        addRtxTextView(ftreadmill, tHZ_parameter, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        addRtxEditView(ftreadmill, t_HZ_parameter_val, InputType.TYPE_CLASS_NUMBER , ix, iy, iw_temp-10, ih);

        ix = 0;
        iy += 80;
        RtxTextView tkm_parameter = new RtxTextView(mContext);
        sdata = "1Km Parameter:";
        addRtxTextView(ftreadmill, tkm_parameter, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        addRtxEditView(ftreadmill, t_km_parameter_val, InputType.TYPE_CLASS_NUMBER , ix, iy, iw_temp-10, ih);

        ix = 0;
        iy += 80;
        RtxTextView tparameter1 = new RtxTextView(mContext);
        sdata = "parameter1:";
        addRtxTextView(ftreadmill, tparameter1, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        addRtxEditView(ftreadmill, t_parameter1_val, InputType.TYPE_CLASS_NUMBER , ix, iy, iw_temp-10, ih);

        tparameter1.setVisibility(INVISIBLE);
        t_parameter1_val.setVisibility(INVISIBLE);

        ix = 0;
        iy += 0;
        RtxTextView zero_parameter = new RtxTextView(mContext);
        sdata = "zero_parameter:";
        addRtxTextView(ftreadmill, zero_parameter, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        addRtxEditView(ftreadmill, t_zero_parameter_val, InputType.TYPE_CLASS_NUMBER , ix, iy, iw_temp-10, ih);

        //F62 ADD/SUB speed
        ix = 0;
        iy += 80;
        iw_temp = iw/2;
        RtxTextView tspeed_add_parameter = new RtxTextView(mContext);
        sdata = "Speed Add time:";
        addRtxTextView(ftreadmill, tspeed_add_parameter, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        addRtxEditView(ftreadmill, t_add_val, InputType.TYPE_CLASS_NUMBER , ix, iy, iw_temp-10, ih);

        ix = 0;
        iy += 80;
        iw_temp = iw/2;
        RtxTextView tspeed_sub_parameter = new RtxTextView(mContext);
        sdata = "Speed Sub time:";
        addRtxTextView(ftreadmill, tspeed_sub_parameter, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        addRtxEditView(ftreadmill, t_sub_val, InputType.TYPE_CLASS_NUMBER , ix, iy, iw_temp-10, ih);

        //U-Bike, R-Bike, EP parameter
        ix = 0;
        iy = 0;
        iw_temp = iw / 4;
        ih = 60;
        fsize = 28f;
        ix += iw_temp;
        RtxTextView tlevel_min = new RtxTextView(mContext);
        sdata = "min";
        addRtxTextView(fbike_ep, tlevel_min, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        RtxTextView tlevel_max = new RtxTextView(mContext);
        sdata = "max";
        addRtxTextView(fbike_ep, tlevel_max, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        RtxTextView tlevel_def = new RtxTextView(mContext);
        sdata = "def";
        addRtxTextView(fbike_ep, tlevel_def, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        //F17, Level default, min, max
        ix = 0;
        iy += 80;
        RtxTextView tlevel = new RtxTextView(mContext);
        sdata = mContext.getResources().getString(R.string.level)+":";
        addRtxTextView(fbike_ep, tlevel, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        addRtxEditView(fbike_ep, t_level_min_val, InputType.TYPE_NUMBER_FLAG_DECIMAL , ix, iy, iw_temp-10, ih);

        ix += iw_temp;
        addRtxEditView(fbike_ep, t_level_max_val, InputType.TYPE_NUMBER_FLAG_DECIMAL , ix, iy, iw_temp-10, ih);

        ix += iw_temp;
        addRtxEditView(fbike_ep, t_level_val, InputType.TYPE_NUMBER_FLAG_DECIMAL , ix, iy, iw_temp-10, ih);

        ix = 0;
        iy += 80;
        iw_temp = iw / 2;
        RtxTextView tincrement_duty = new RtxTextView(mContext);
        sdata = "Increment_Duty:";
        addRtxTextView(fbike_ep, tincrement_duty, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

        ix += iw_temp;
        addRtxEditView(fbike_ep, t_increment_duty_val, InputType.TYPE_CLASS_NUMBER , ix, iy, iw_temp-10, ih);

        ix = 0;
        iy += 60;
        iw_temp = iw / 3;
        ih = 50;
        RtxTextView tlevel_item = new RtxTextView(mContext);
        sdata = "level";
        addRtxTextView(fbike_ep, tlevel_item, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp-10, ih);

        ix += iw_temp;
        RtxTextView ttorqe = new RtxTextView(mContext);
        sdata = "torque";
        addRtxTextView(fbike_ep, ttorqe, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp-10, ih);

        ix += iw_temp;
        RtxTextView tresistance = new RtxTextView(mContext);
        sdata = "resistance";
        addRtxTextView(fbike_ep, tresistance, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp-10, ih);

        for(iLoop = 0 ; iLoop < t_torqe_val.length; iLoop++)
        {
            ix = 0;
            iy += 60;
            sdata = Rtx_TranslateValue.sInt2String(iLoop+1);
            RtxTextView tlvl = new RtxTextView(mContext);
            addRtxTextView(fbike_ep, tlvl, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw_temp, ih);

            ix += iw_temp;
            addRtxEditView(fbike_ep, t_torqe_val[iLoop], InputType.TYPE_CLASS_NUMBER , ix, iy, iw_temp-30, ih);

            ix += iw_temp;
            addRtxEditView(fbike_ep, t_resistance_val[iLoop], InputType.TYPE_CLASS_NUMBER , ix, iy, iw_temp-30, ih);

        }

    }

    private float check_float_input(RtxEditText v, float[] flimitDef, Object Obj)
    {
        float fval = 0;
        float[] flimit = new float[2];

        fval = Rtx_TranslateValue.fString2Float(v.getText().toString());
        if(v == t_speed_min_val || v == t_speed_max_val || v == t_speed_val)
        {
            flimit[0] = EngSetting.Distance.getVal(flimitDef[0]);
            flimit[1] = EngSetting.Distance.getVal(flimitDef[1]);
        }
        else
        {
            flimit[0] = flimitDef[0];
            flimit[1] = flimitDef[1];
        }
        if(fval == -1)
        {
            fval = (float)Obj;
            v.setText(Rtx_TranslateValue.sFloat2String(fval, 1));
        }
        else {

            if (fval >= flimit[0] && fval <= flimit[1]) {
            } else {

                if (fval < flimit[0])
                {
                    fval = flimit[0];
                }
                else
                {
                    fval = flimit[1];
                }
                v.setText(Rtx_TranslateValue.sFloat2String(fval, 1));
            }
        }

        return fval;
    }

    private float check_int_input(EditText v, float[] flimit, Object Obj)
    {
        float fval = 0;

        fval = Rtx_TranslateValue.fString2Float(v.getText().toString());
        if(fval == -1)
        {
            fval = (float)Obj;
            v.setText(Rtx_TranslateValue.sFloat2String(fval, 0));
        }
        else {
            if (fval >= flimit[0] && fval <= flimit[1]) {

            } else {
                if (fval < flimit[0])
                {
                    fval = flimit[0];
                }
                else
                {
                    fval = flimit[1];
                }
                v.setText(Rtx_TranslateValue.sFloat2String(fval, 0));
            }
        }

        return fval;
    }

    private void set_scroller_y(int istep)
    {
        int iy = 100;

        scroller.setScrollY(iy*istep);
    }

    private void set_scroller_y(RtxEditText mRtxEditText)
    {
       //判斷view是否在fscroll frame中
        if(fscroll.indexOfChild(mRtxEditText) != -1)
        {
            scroller.setScrollY((int)mRtxEditText.getY() - 75);
        }
        else
        {
            scroller.setScrollY((int)ftreadmill.getY() + (int)mRtxEditText.getY() - 75);
        }
    }

    private OnFocusChangeListener edittext_focus = new OnFocusChangeListener(){
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            float fval = 0f;
            float[] flimit;

            if(DEBUG)Log.e(TAG,"=========v=" + hasFocus);
            if(v == e_gym_id)
            {
                if(hasFocus) {
                    set_scroller_y(e_gym_id);
                }
            }
            else if(v == e_dealer_id)
            {
                if(hasFocus) {
                    set_scroller_y(e_dealer_id);
                }
            }
            else if(v == e_machine_id)
            {
                if(hasFocus) {
                    set_scroller_y(e_machine_id);
                }
            }
            else if(v == e_country_val)
            {
                if(hasFocus) {
                    set_scroller_y(e_country_val);
                }
            }
            else if(v == e_dev_city)
            {
                if(hasFocus) {
                    set_scroller_y(e_dev_city);
                }
            }
            else if(v == e_weather_interval)
            {
                if(hasFocus) {
                    set_scroller_y(e_weather_interval);
                }
            }
            else if(v == t_speed_min_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_speed_min_val);
                }
                else
                {
                    flimit = fspeed_min_limit;
                    fval = check_float_input(t_speed_min_val, flimit, EngSetting.f_Get_Min_Speed());
                    EngSetting.v_Set_Min_Speed(EngSetting.Distance.fTranslat2KM(fval));
                }
            }
            else if(v == t_speed_max_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_speed_min_val);
                }
                else
                {
                    flimit = fspeed_max_limit;
                    fval = check_float_input(t_speed_max_val, flimit, EngSetting.f_Get_Max_Speed());
                    EngSetting.v_Set_Max_Speed(EngSetting.Distance.fTranslat2KM(fval));
                }
            }
            else if(v == t_speed_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_speed_min_val);
                }
                else
                {
                    flimit = new float[]{EngSetting.f_Get_Min_Speed(), EngSetting.f_Get_Max_Speed()};
                    fval = check_float_input(t_speed_val, flimit, EngSetting.f_Get_Def_Speed());
                    EngSetting.v_Set_Def_Speed(EngSetting.Distance.fTranslat2KM(fval));
                }
            }
            else if(v == t_incline_min_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_speed_min_val);
                }
                else
                {
                    flimit = fincline_min_limit;
                    fval = check_float_input(t_incline_min_val, flimit, EngSetting.f_Get_Min_Incline());
                    EngSetting.v_Set_Min_Incline(fval);
                }
            }
            else if(v == t_incline_max_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_speed_min_val);
                }
                else
                {
                    flimit = fincline_max_limit;
                    fval = check_float_input(t_incline_max_val, flimit, EngSetting.f_Get_Max_Incline());
                    EngSetting.v_Set_Max_Incline(fval);
                }
            }
            else if(v == t_incline_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_speed_min_val);
                }
                else
                {
                    flimit = new float[]{EngSetting.f_Get_Min_Incline(), EngSetting.f_Get_Max_Incline()};
                    fval = check_float_input(t_incline_val, flimit, EngSetting.f_Get_Def_Incline());
                    EngSetting.v_Set_Def_Incline(fval);
                }
            }
            else if(v == t_idle_detect_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_idle_detect_val);
                }
                else
                {
                    fval = check_int_input(t_idle_detect_val, fidle_detect_limit, EngSetting.f_Get_fmachine_idle_detect());
                    EngSetting.v_Set_fmachine_idle_detect(fval);
                }
            }
            else if(v == t_HZ_parameter_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_HZ_parameter_val);
                }
                else
                {
                    fval = check_int_input(t_HZ_parameter_val, fHZ_parameter_limit, EngSetting.f_Get_fmachine_HZ_parameter());
                    EngSetting.v_Set_fmachine_HZ_parameter(fval);
                }
            }
            else if(v == t_km_parameter_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_km_parameter_val);
                }
                else
                {
                    fval = check_int_input(t_km_parameter_val, fkm_parameter_limit, EngSetting.f_Get_fmachine_1km_parameter());
                    EngSetting.v_Set_fmachine_1km_parameter(fval);
                }
            }
            else if(v == t_parameter1_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_parameter1_val);
                }
                else
                {
                    fval = check_int_input(t_parameter1_val, fkm_parameter1_limit, EngSetting.f_Get_fmachine_parameter1());
                    EngSetting.v_Set_fmachine_parameter1(fval);
                }
            }
            else if(v == t_zero_parameter_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_zero_parameter_val);
                }
                else
                {
                    fval = check_int_input(t_zero_parameter_val, fkm_zero_parameter_limit, EngSetting.f_Get_fmachine_zero_parameter());
                    EngSetting.v_Set_fmachine_zero_parameter(fval);
                }
            }
            else if(v == t_add_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_add_val);
                }
                else
                {
                    fval = check_int_input(t_add_val, fspeed_add_limit, EngSetting.f_Get_fspeed_add_val());
                    EngSetting.v_Set_fspeed_add_val(fval);
                }
            }
            else if(v == t_sub_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_sub_val);
                }
                else
                {
                    fval = check_int_input(t_sub_val, fspeed_sub_limit, EngSetting.f_Get_fspeed_sub_val());
                    EngSetting.v_Set_fspeed_sub_val(fval);
                }
            }
            else if(v == t_level_min_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_level_min_val);
                }
                else
                {
                    flimit = flevel_min_limit;
                    fval = check_float_input(t_level_min_val, flimit, EngSetting.f_Get_Min_level());
                    EngSetting.v_Set_Min_level(fval);
                }
            }
            else if(v == t_level_max_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_level_min_val);
                }
                else
                {
                    flimit = flevel_max_limit;
                    fval = check_float_input(t_level_max_val, flimit, EngSetting.f_Get_Max_level());
                    EngSetting.v_Set_Max_level(fval);
                }
            }
            else if(v == t_level_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_level_min_val);
                }
                else
                {
                    flimit = new float[]{EngSetting.f_Get_Min_level(), EngSetting.f_Get_Max_level()};
                    fval = check_float_input(t_level_val, flimit, EngSetting.f_Get_Def_level());
                    EngSetting.v_Set_Def_level(fval);
                }
            }
            else if(v == t_increment_duty_val)
            {
                if(hasFocus) {
                    set_scroller_y(t_level_min_val);
                }
                else
                {
                    fval = check_int_input(t_increment_duty_val, fincrement_duty_limit, EngSetting.f_Get_fmachine_increment_duty());
                    EngSetting.v_Set_fmachine_increment_duty(fval);
                }
            }
            else
            {
                for(int iLoop = 0 ; iLoop < t_torqe_val.length ; iLoop++)
                {
                    if(v == t_torqe_val[iLoop])
                    {
                        if(hasFocus) {
                            set_scroller_y(t_torqe_val[iLoop]);
                        }
                        else
                        {
                            fval = check_int_input(t_torqe_val[iLoop], ftorqe_limit, EngSetting.f_Get_fmachine_tq(iLoop));
                            EngSetting.v_Set_fmachine_tq(iLoop, fval);
                        }
                    }
                }
                for(int iLoop = 0 ; iLoop < t_resistance_val.length ; iLoop++)
                {
                    if(v == t_resistance_val[iLoop])
                    {
                        if(hasFocus) {
                            set_scroller_y(t_resistance_val[iLoop]);
                        }
                        else
                        {
                            fval = check_int_input(t_resistance_val[iLoop], fresistance_limit, EngSetting.f_Get_fmachine_rn(iLoop));
                            EngSetting.v_Set_fmachine_rn(iLoop, fval);
                        }
                    }
                }
            }
        }
    };

    private void v_update_text_setting()
    {
        int ival, iLoop, iLoop_max;
        float fval;
        float[] flimit;

        if(DEBUG)Log.e(TAG,"=========v_update_text_setting=");

        EngSetting.v_Set_ENG_GYM_ID(e_gym_id.getText().toString());

        EngSetting.v_Set_ENG_OWN_DLR(e_dealer_id.getText().toString());

        EngSetting.v_Set_ENG_DEV_MSN(e_machine_id.getText().toString());

        EngSetting.v_Set_DEFAULT_COUNTRY(e_country_val.getText().toString());

        EngSetting.v_Set_DEFAULT_CITY(e_dev_city.getText().toString());

        ival = (int)check_int_input(e_weather_interval, fweather_interval_limit, EngSetting.i_Get_Weather_Check_interval());
        EngSetting.v_Set_Weather_Check_interval(ival);

        //Treadmill mode跑步機模式
        flimit = fspeed_min_limit;
        fval = check_float_input(t_speed_min_val, flimit, EngSetting.f_Get_Min_Speed());
//        EngSetting.v_Set_Min_Speed(fval);
        EngSetting.v_Set_Min_Speed(EngSetting.Distance.fTranslat2KM(fval));

        flimit = fspeed_max_limit;
        fval = check_float_input(t_speed_max_val, flimit, EngSetting.f_Get_Max_Speed());
//        EngSetting.v_Set_Max_Speed(fval);
        EngSetting.v_Set_Max_Speed(EngSetting.Distance.fTranslat2KM(fval));

        flimit = new float[]{EngSetting.f_Get_Min_Speed(), EngSetting.f_Get_Max_Speed()};
        fval = check_float_input(t_speed_val, flimit, EngSetting.f_Get_Def_Speed());
//        EngSetting.v_Set_Def_Speed(fval);
        EngSetting.v_Set_Def_Speed(EngSetting.Distance.fTranslat2KM(fval));

        flimit = fincline_min_limit;
        fval = check_float_input(t_incline_min_val, flimit, EngSetting.f_Get_Min_Incline());
        EngSetting.v_Set_Min_Incline(fval);

        flimit = fincline_max_limit;
        fval = check_float_input(t_incline_max_val, flimit, EngSetting.f_Get_Max_Incline());
        EngSetting.v_Set_Max_Incline(fval);

        flimit = new float[]{EngSetting.f_Get_Min_Incline(), EngSetting.f_Get_Max_Incline()};
        fval = check_float_input(t_incline_val, flimit, EngSetting.f_Get_Def_Incline());
        EngSetting.v_Set_Def_Incline(fval);

        fval = check_int_input(t_idle_detect_val, fidle_detect_limit, EngSetting.f_Get_fmachine_idle_detect());
        EngSetting.v_Set_fmachine_idle_detect(fval);

        fval = check_int_input(t_HZ_parameter_val, fHZ_parameter_limit, EngSetting.f_Get_fmachine_HZ_parameter());
        EngSetting.v_Set_fmachine_HZ_parameter(fval);

        fval = check_int_input(t_km_parameter_val, fkm_parameter_limit, EngSetting.f_Get_fmachine_1km_parameter());
        EngSetting.v_Set_fmachine_1km_parameter(fval);

        fval = check_int_input(t_parameter1_val, fkm_parameter1_limit, EngSetting.f_Get_fmachine_parameter1());
        EngSetting.v_Set_fmachine_parameter1(fval);

        fval = check_int_input(t_zero_parameter_val, fkm_zero_parameter_limit, EngSetting.f_Get_fmachine_zero_parameter());
        EngSetting.v_Set_fmachine_zero_parameter(fval);

        fval = check_int_input(t_add_val, fspeed_add_limit, EngSetting.f_Get_fspeed_add_val());
        EngSetting.v_Set_fspeed_add_val(fval);

        fval = check_int_input(t_sub_val, fspeed_sub_limit, EngSetting.f_Get_fspeed_sub_val());
        EngSetting.v_Set_fspeed_sub_val(fval);

        //Bike mode 腳踏車模式
        flimit = flevel_min_limit;
        fval = check_float_input(t_level_min_val, flimit, EngSetting.f_Get_Min_level());
        EngSetting.v_Set_Min_level(fval);

        flimit = flevel_max_limit;
        fval = check_float_input(t_level_max_val, flimit, EngSetting.f_Get_Max_level());
        EngSetting.v_Set_Max_level(fval);

        flimit = new float[]{EngSetting.f_Get_Min_level(), EngSetting.f_Get_Max_level()};
        fval = check_float_input(t_level_val, flimit, EngSetting.f_Get_Def_level());
        EngSetting.v_Set_Def_level(fval);

        fval = check_int_input(t_increment_duty_val, fincrement_duty_limit, EngSetting.f_Get_fmachine_increment_duty());
        EngSetting.v_Set_fmachine_increment_duty(fval);

        if(fbike_ep != null && t_increment_duty_val != null)
        {
            iLoop_max = EngSetting.fmachine_tq.length;

            if(EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING6)
            {
                for (iLoop = 0; iLoop < iLoop_max; iLoop++) {
                    fval = check_int_input(t_torqe_val[iLoop], ftorqe_limit, EngSetting.f_Get_fmachine_tq(iLoop));
                    EngSetting.v_Set_fmachine_tq(iLoop, fval);
                    EngSetting.v_Set_fmachine_tq_ubike(iLoop, fval);

                    fval = check_int_input(t_resistance_val[iLoop], fresistance_limit, EngSetting.f_Get_fmachine_rn(iLoop));
                    EngSetting.v_Set_fmachine_rn(iLoop, fval);
                    EngSetting.v_Set_fmachine_rn_ubike(iLoop, fval);
                }
            }

            if(EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING6)
            {
                for (iLoop = 0; iLoop < iLoop_max; iLoop++) {
                    fval = check_int_input(t_torqe_val[iLoop], ftorqe_limit, EngSetting.f_Get_fmachine_tq(iLoop));
                    EngSetting.v_Set_fmachine_tq(iLoop, fval);
                    EngSetting.v_Set_fmachine_tq_rbike(iLoop, fval);

                    fval = check_int_input(t_resistance_val[iLoop], fresistance_limit, EngSetting.f_Get_fmachine_rn(iLoop));
                    EngSetting.v_Set_fmachine_rn(iLoop, fval);
                    EngSetting.v_Set_fmachine_rn_rbike(iLoop, fval);
                }
            }

            if(EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL || EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL6)
            {
                for (iLoop = 0; iLoop < iLoop_max; iLoop++) {
                    fval = check_int_input(t_torqe_val[iLoop], ftorqe_limit, EngSetting.f_Get_fmachine_tq(iLoop));
                    EngSetting.v_Set_fmachine_tq(iLoop, fval);
                    EngSetting.v_Set_fmachine_tq_elliptical(iLoop, fval);

                    fval = check_int_input(t_resistance_val[iLoop], fresistance_limit, EngSetting.f_Get_fmachine_rn(iLoop));
                    EngSetting.v_Set_fmachine_rn(iLoop, fval);
                    EngSetting.v_Set_fmachine_rn_elliptical(iLoop, fval);
                }
            }
        }
    }

    public void vEngSettingFrame_Register()
    {
        CloudCmd_Info.vCloudCmd_Add(cloudglobal.iCHK_AVL_MSN_DLR01, 3);
        CloudDataStruct.CloudData_04.Set_input();
        mEngModeActivity.mCloudCmd.iCloudCmd_RegisterDevice();

        EngSetting.v_Set_DEFAULT_REGISTER_REGISTER("OK");

        if(CloudDataStruct.CloudData_04.sdata_out[Cloud_04_CHK_AVL_MSN_DLR.Output.DATA_1] == null) {
            EngSetting.v_Set_DEFAULT_REGISTER_REGISTER("Fail!");
        }
        else {
            if (CloudDataStruct.CloudData_04.sdata_out[Cloud_04_CHK_AVL_MSN_DLR.Output.DATA_1].equals("Machine S.N. not available")) {
//                Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + CloudDataStruct.CloudData_04.sdata_out[Cloud_04_CHK_AVL_MSN_DLR.Output.DATA_1]);
                EngSetting.v_Set_DEFAULT_REGISTER_REGISTER("Fail!");
            }
        }

        if(CloudDataStruct.CloudData_04.sdata_out[Cloud_04_CHK_AVL_MSN_DLR.Output.DATA_2] == null) {
            EngSetting.v_Set_DEFAULT_REGISTER_REGISTER("Fail!");
        }
        else {
            if (CloudDataStruct.CloudData_04.sdata_out[Cloud_04_CHK_AVL_MSN_DLR.Output.DATA_2].equals("Dealer ID not available")) {
//                Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + CloudDataStruct.CloudData_04.sdata_out[Cloud_04_CHK_AVL_MSN_DLR.Output.DATA_2]);
                EngSetting.v_Set_DEFAULT_REGISTER_REGISTER("Fail!");
            }
        }
    }

    public void vEngSettingFrame_Connect()
    {
        CloudDataStruct.CloudData_27.Set_input();
        mEngModeActivity.mCloudCmd.iCloudCmd_GetDeviceInfo();

        EngSetting.v_Set_DEFAULT_REGISTER_CONNECT("");

        if(CloudDataStruct.CloudData_27.sdata_out[Cloud_27_GET_DEV_BSC.Output.OWN_DLR] == null) {
        }
        else {
            if (CloudDataStruct.CloudData_27.b_is_get_dev_info()) {
                Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + CloudDataStruct.CloudData_27.sdata_out[Cloud_27_GET_DEV_BSC.Output.OWN_DLR]);
                Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + EngSetting.s_Get_ENG_OWN_DLR());
                if (CloudDataStruct.CloudData_27.sdata_out[Cloud_27_GET_DEV_BSC.Output.OWN_DLR].equals(EngSetting.s_Get_ENG_OWN_DLR())) {
                    EngSetting.v_Set_DEFAULT_REGISTER_CONNECT("OK");
                } else {
                    EngSetting.v_Set_DEFAULT_REGISTER_CONNECT("Fail!");
                }
            } else {
            }
        }
    }

    private void vClickBackgroud()
    {
        Rtx_Keyboard.closeSoftKeybord(this,mContext);
    }

    private void vClickRadioBut(int isel)
    {
        //Set Choice
        if(DEBUG) Log.e(TAG, "   vClickRadioBut=" + isel);
        switch (isel)
        {
            case 0:
            case 1:
                EngSetting.setUnit(isel);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                v_update_text_setting();
                EngSetting.v_Set_ExerciseType(isel-2);
                break;
            case 6:
            case 8:
            case 10:
                EngSetting.v_Set_Screen_Rotation(isel-6);
                break;
            case 11:
                v_update_text_setting();
                EngSetting.v_Set_ExerciseType(isel-6);
                break;
            case 12:
                v_update_text_setting();
                EngSetting.v_Set_ExerciseType(isel-6);
                break;
            case 14:
                v_update_text_setting();
                EngSetting.v_Set_ExerciseType(isel-7);
                break;
            case 15:
                v_update_text_setting();
                EngSetting.v_Set_ExerciseType(isel-7);
                break;
            case 16:
                v_update_text_setting();
                EngSetting.v_Set_ExerciseType(isel-7);
                break;
            default:
                break;
        }

        v_RadioBut_Fresh();

    }

    private void vClickTimezoneOffsetBut(int isel)
    {
        int iTimeVal = -1;
        switch (isel)
        {
            case 0:
                if(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(isel).equals("+"))
                {
                    EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(isel, "-");
                }
                else
                {
                    EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(isel, "+");
                }
                break;
            case 1:
                iTimeVal = Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(isel)) * 10 + Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(isel + 1));
                iTimeVal = iTimeVal + 10;
                if(iTimeVal == (23 + 10))
                {
                    iTimeVal = 3;
                }
                if(iTimeVal > 23)
                {
                    iTimeVal = 23;
                }

                EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(isel, Rtx_TranslateValue.sInt2String(iTimeVal / 10));
                EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(isel + 1, Rtx_TranslateValue.sInt2String(iTimeVal % 10));
                break;
            case 2:
                iTimeVal = Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(isel - 1)) * 10 + Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(isel));
                iTimeVal++;
                if(iTimeVal > 23)
                {
                    iTimeVal = 0;
                }

                EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(isel - 1, Rtx_TranslateValue.sInt2String(iTimeVal / 10));
                EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(isel, Rtx_TranslateValue.sInt2String(iTimeVal % 10));
                break;
            case 3:
                iTimeVal = Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(isel)) * 10 + Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(isel + 1));
                iTimeVal = iTimeVal + 10;
                if(iTimeVal > 59)
                {
                    EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(isel, Rtx_TranslateValue.sInt2String(0));
                }
                else
                {
                    EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(isel, Rtx_TranslateValue.sInt2String(iTimeVal / 10));
                }

                EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(isel + 1, Rtx_TranslateValue.sInt2String(iTimeVal % 10));

                break;
            case 4:
                iTimeVal = Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(isel - 1)) * 10 + Rtx_TranslateValue.iString2Int(EngSetting.s_Get_DEFAULT_TIMEZONE_OFFSET(isel));
                iTimeVal++;
                if(iTimeVal > 59)
                {
                    iTimeVal = 0;
                }

                EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(isel - 1, Rtx_TranslateValue.sInt2String(iTimeVal / 10));
                EngSetting.v_Set_DEFAULT_TIMEZONE_OFFSET(isel, Rtx_TranslateValue.sInt2String(iTimeVal % 10));
                break;
            default:
                break;
        }

        v_TimezoneOffsetBut_Fresh();
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
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_ABOUT);
                vClickBackgroud();
            }
            else if (v == i_setting) {
                if (DEBUG) Log.e(TAG, "==========i_setting========");
                //mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_SETTING);
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
            else if (v == i_eng_background) {
                if (DEBUG) Log.e(TAG, "==========i_eng_background========");
                vClickBackgroud();
            }
            else if (v == i_register_register) {
                if (DEBUG) Log.e(TAG, "==========i_register_register========");
                v_update_text_setting();
                vClickBackgroud();
                vEngSettingFrame_Register();
                v_Register_Fresh();
            }
            else if (v == i_register_connect) {
                if (DEBUG) Log.e(TAG, "==========i_register_connect========");
                v_update_text_setting();
                vClickBackgroud();
                vEngSettingFrame_Connect();
                v_Connect_Fresh();
            }
            else if (v == i_defaule_set) {
                if (DEBUG) Log.e(TAG, "==========i_defaule_set========");
                if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 && EngSetting.getUnit() == EngSetting.UNIT_METRIC)
                {
                    t_speed_min_val.setText("0.8");
                    t_speed_max_val.setText("20.0");
                    t_speed_val.setText("0.8");

                    t_incline_min_val.setText("0.0");
                    t_incline_max_val.setText("15.0");
                    t_incline_val.setText("0.0");

                    t_idle_detect_val.setText("0");
                    t_HZ_parameter_val.setText("640");
                    t_km_parameter_val.setText("640");
                    t_zero_parameter_val.setText("100");
                    t_add_val.setText("5.0");
                    t_sub_val.setText("5.0");
                }
                else if (EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7 && EngSetting.getUnit() == EngSetting.UNIT_METRIC)
                {
                    t_speed_min_val.setText("0.8");
                    t_speed_max_val.setText("20.0");
                    t_speed_val.setText("0.8");

                    t_incline_min_val.setText("0.0");
                    t_incline_max_val.setText("16.0");
                    t_incline_val.setText("0.0");

                    t_idle_detect_val.setText("0");
                    t_HZ_parameter_val.setText("530");
                    t_km_parameter_val.setText("530");
                    t_zero_parameter_val.setText("100");
                    t_add_val.setText("5.0");
                    t_sub_val.setText("5.0");
                }
                else if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING && EngSetting.getUnit() == EngSetting.UNIT_METRIC)
                {
                    t_speed_min_val.setText("0.5");
                    t_speed_max_val.setText("25.0");
                    t_speed_val.setText("0.5");

                    t_incline_min_val.setText("0.0");
                    t_incline_max_val.setText("16.0");
                    t_incline_val.setText("0.0");

                    t_idle_detect_val.setText("0");
                    t_HZ_parameter_val.setText("600");
                    t_km_parameter_val.setText("600");
                    t_zero_parameter_val.setText("100");
                    t_add_val.setText("5.0");
                    t_sub_val.setText("5.0");
                }
                else if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING6 && EngSetting.getUnit() == EngSetting.UNIT_IMPERIAL)
                {
                    t_speed_min_val.setText("0.5");
                    t_speed_max_val.setText("12.5");
                    t_speed_val.setText("0.5");

                    t_incline_min_val.setText("0.0");
                    t_incline_max_val.setText("15.0");
                    t_incline_val.setText("0.0");

                    t_idle_detect_val.setText("0");
                    t_HZ_parameter_val.setText("640");
                    t_km_parameter_val.setText("640");
                    t_zero_parameter_val.setText("100");
                    t_add_val.setText("5.0");
                    t_sub_val.setText("5.0");
                }
                else if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING7 && EngSetting.getUnit() == EngSetting.UNIT_IMPERIAL)
                {
                    t_speed_min_val.setText("0.5");
                    t_speed_max_val.setText("12.5");
                    t_speed_val.setText("0.5");

                    t_incline_min_val.setText("0.0");
                    t_incline_max_val.setText("16.0");
                    t_incline_val.setText("0.0");

                    t_idle_detect_val.setText("0");
                    t_HZ_parameter_val.setText("530");
                    t_km_parameter_val.setText("530");
                    t_zero_parameter_val.setText("100");
                    t_add_val.setText("5.0");
                    t_sub_val.setText("5.0");
                }
                else if(EngSetting.i_Get_ExerciseType() == EngSetting.RUNNING && EngSetting.getUnit() == EngSetting.UNIT_IMPERIAL)
                {
                    t_speed_min_val.setText("0.3");
                    t_speed_max_val.setText("15.5");
                    t_speed_val.setText("0.3");

                    t_incline_min_val.setText("0.0");
                    t_incline_max_val.setText("16.0");
                    t_incline_val.setText("0.0");

                    t_idle_detect_val.setText("0");
                    t_HZ_parameter_val.setText("600");
                    t_km_parameter_val.setText("600");
                    t_zero_parameter_val.setText("100");
                    t_add_val.setText("5.0");
                    t_sub_val.setText("5.0");
                }
                else if(EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING || EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING)
                {
                    t_level_min_val.setText("1");
                    t_level_max_val.setText("25");
                    t_level_val.setText("1");

                    t_increment_duty_val.setText("16.0");

                    String[] torqe_B7_Array = {"49","65","81","97","114","130","146","162","179","195","211","230","250","269","289","308","328","347","367","386","438","490","542","594","649"};
                    String[] resistance_B7_Array = {"235","314","370","410","520","555","590","630","680","720","760","790","820","850","885","920","950","985","1020","1055","1130","1210","1290","1365","1445"};

                    for(int i=0 ; i<25 ; i++)
                    {
                        t_torqe_val[i].setText(torqe_B7_Array[i]);
                        t_resistance_val[i].setText(resistance_B7_Array[i]);
                    }
                }
                else if(EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL)
                {
                    t_level_min_val.setText("1");
                    t_level_max_val.setText("25");
                    t_level_val.setText("1");

                    t_increment_duty_val.setText("16.0");

                    String[] torqe_E7_Array = {"65","89","114","138","162","187","211","235","260","284","308","333","357","381","406","430","454","479","503","527","552","576","600","625","649"};
                    String[] resistance_E7_Array = {"100","245","345","415","500","540","600","640","680","720","760","800","840","860","900","940","980","1020","1060","1080","1120","1160","1200","1240","1280"};

                    for(int i=0 ; i<25 ; i++)
                    {
                        t_torqe_val[i].setText(torqe_E7_Array[i]);
                        t_resistance_val[i].setText(resistance_E7_Array[i]);
                    }
                }
                else if(EngSetting.i_Get_ExerciseType() == EngSetting.UBIKING6 || EngSetting.i_Get_ExerciseType() == EngSetting.RBIKING6 || EngSetting.i_Get_ExerciseType() == EngSetting.ELLIPTICAL6)
                {
                    t_level_min_val.setText("1");
                    t_level_max_val.setText("16");
                    t_level_val.setText("1");

                    t_increment_duty_val.setText("16.0");

                    String[] torqe_E7_Array = {"89","105","122","138","154","170","187","203","219","235","252","268","292","325","365","406","406","406","406","406","406","406","406","406","406"};
                    String[] resistance_E7_Array = {"390","465","535","570","610","655","700","735","765","795","825","855","890","950","1020","1110","1110","1110","1110","1110","1110","1110","1110","1110","1110"};

                    for(int i=0 ; i<25 ; i++)
                    {
                        t_torqe_val[i].setText(torqe_E7_Array[i]);
                        t_resistance_val[i].setText(resistance_E7_Array[i]);
                    }
                }
            }
            else
            {
                int iLoop;
                for(iLoop = 0; iLoop < imax_Radio; iLoop++)
                {
                    if(v == mRadio[iLoop])
                    {
                        vClickRadioBut(iLoop);
                    }
                }

                for(iLoop = 0; iLoop < imax_TimezoneOffset; iLoop++)
                {
                    if(v == i_timezone_offset[iLoop])
                    {
                        if (DEBUG) Log.e(TAG, "==========t_timezone_offset[" + iLoop + "]========");
                        vClickTimezoneOffsetBut(iLoop);
                    }
                }
            }
        }
    }
}