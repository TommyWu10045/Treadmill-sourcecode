package com.rtx.treadmill.Engmode;

import android.content.Context;
import android.graphics.Typeface;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Switch;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxApkUpdate.ApkUpdateProc;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Log;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

public class EngUpdateFrame extends Rtx_BaseLayout {
    private static String TAG = "Jerry=";
    private final static boolean DEBUG = false;

    Context mContext;
    EngModeActivity mEngModeActivity;

    ButtonListener mButtonListener;

    private RtxImageView i_eng_background;
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

    private RtxTextView t_app_ver;
    private RtxTextView t_app_val;
    private RtxImageView i_app;
    private RtxTextView t_bike_resistance_ver;
    private RtxTextView t_bike_resistance_val;
    private RtxImageView i_bike_resistance;
    private RtxTextView t_bike_resistance_return;

    //limite data
    float[] ftorqe_limit = EngSetting.ftorqe_limit;
    float[] fresistance_limit = EngSetting.fresistance_limit;

    public EngUpdateFrame(Context context, EngModeActivity mEngModeActivity) {
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
        v_EngUpdateFrame_Refresh_data();
    }

    public void v_EngUpdateFrame_Refresh_data() {
        if (DEBUG) Log.e(TAG, "Refresh_data");

    }

    private void v_BikeRrsistance_Fresh()
    {
        if (DEBUG) Log.e(TAG, "v_BikeRrsistance_Fresh");
        if(true)
        {
            t_bike_resistance_return.setText(mContext.getResources().getString(R.string.engbike_resistance_update_finished));
        }
        else
        {
            t_bike_resistance_return.setText(mContext.getResources().getString(R.string.engbike_resistance_update_fail));
        }
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

    private void add_view(ListView mListView , int iX , int iY , int iWidth , int iHeight) {
        LayoutParams mLayoutParams = new LayoutParams(iWidth,iHeight);
        mLayoutParams.leftMargin = iX;
        mLayoutParams.topMargin = iY;
        mListView.setLayoutParams(mLayoutParams);

        addView(mListView);
    }

    private void add_view(Switch mView , int gravity, int iX , int iY , int iWidth , int iHeight, int itrack, int ithumb) {
        mView.setGravity(gravity);
        mView.setTrackDrawable(mContext.getResources().getDrawable(itrack));
        mView.setThumbResource(ithumb);

        LayoutParams mLayoutParams = new LayoutParams(iWidth,iHeight);
        mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;
        mLayoutParams.leftMargin = iX;
        mLayoutParams.topMargin = iY;
        mView.setLayoutParams(mLayoutParams);
        addView(mView);
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

        if(t_app_ver == null)           {t_app_ver = new RtxTextView(mContext);}
        if(t_app_val == null)           {t_app_val = new RtxTextView(mContext);}
        if(i_app == null)           {i_app = new RtxImageView(mContext);}
        if(t_bike_resistance_ver == null)           {t_bike_resistance_ver = new RtxTextView(mContext);}
        if(t_bike_resistance_val == null)           {t_bike_resistance_val = new RtxTextView(mContext);}
        if(i_bike_resistance == null)           {i_bike_resistance = new RtxImageView(mContext);}
        if(t_bike_resistance_return == null)        {t_bike_resistance_return = new RtxTextView(mContext);}

    }

    private void init_event()
    {
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

        if(mEngModeActivity.bUartTest) {
            i_eng_logo.setOnClickListener(mButtonListener);
        }

        i_app.setOnClickListener(mButtonListener);
        t_app_val.setEnabled(false);
        t_app_val.setClickable(false);
        i_bike_resistance.setOnClickListener(mButtonListener);
        t_bike_resistance_val.setEnabled(false);
        t_bike_resistance_val.setClickable(false);
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

        ih = 525/4+5;
        addRtxImageViewToLayout(i_about, -1, ix, iy, iw, ih);

        iy += 525/4;
        ih = 10;
        addRtxImage(null, i_line02, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        ih = 525/4+5;
        addRtxImageViewToLayout(i_setting, -1, ix, iy, iw, ih);

        iy += 525/4;
        ih = 10;
        addRtxImage(null, i_line03, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        iy += 5;
        ih = 525/4+5;
        addRtxImageViewToLayout(i_update, R.drawable.eng_but, ix, iy, iw, ih);
        i_update.setScaleType(ImageView.ScaleType.FIT_XY);
        iy -= 5;

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
        addRtxTextViewToLayout(t_setting, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 525/4;
        sdata = mContext.getResources().getString(R.string.engupdate);
        addRtxTextViewToLayout(t_update, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        ix = 285;
        iy = 190;
        iw = 945;//995;
        ih = 500;
        add_scrollview(scroller, ix, iy, iw, ih);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //app update
        ix = 50;
        iy = 50;//80;
        iw = 350;//450;
        ih = 50;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.engapp);
        addRtxTextView(fscroll, t_app_ver, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;//460;
        iw = 150;
        ih = 50;
        fsize = 28f;
        sdata = mContext.getResources().getString(R.string.engapp_update);
        addRtxImage(fscroll, i_app, R.drawable.eng_time, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);
        addRtxTextView(fscroll, t_app_val, sdata, fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 50;
        ih = 10;

        //bike resistance
        ix = 50;
        iy += 50;//80;
        iw = 350;//450;
        ih = 50;
        sdata = mContext.getResources().getString(R.string.engbike_resistance);
        addRtxTextView(fscroll, t_bike_resistance_ver, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih);

        ix += 360;//460;
        iw = 150;
        ih = 50;
        sdata = mContext.getResources().getString(R.string.engbike_resistance_update);
        addRtxImage(fscroll, i_bike_resistance, R.drawable.eng_time, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);
        addRtxTextView(fscroll, t_bike_resistance_val, sdata, fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += 160;
        iw = 300;
        addRtxTextView(fscroll, t_bike_resistance_return, "", fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 50;
        ih = 10;

    }

    private float check_int_input(String sValue, float[] flimit, Object Obj)
    {
        float fval = 0;

        fval = Float.valueOf(sValue);
        if(fval == -1)
        {
            fval = (float)Obj;
        }
        else
        {
            if (fval >= flimit[0] && fval <= flimit[1])
            {
            }
            else
            {
                if (fval < flimit[0])
                {
                    fval = flimit[0];
                }
                else
                {
                    fval = flimit[1];
                }
            }
        }

        return fval;
    }

    private void vEngUpdateFrame_AppUpdate()
    {
        final StorageManager sm = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);

        String[] volumePaths = new String[0];
        try
        {
            final Method method = sm.getClass().getMethod("getVolumePaths");
            if(method != null)
            {
                method.setAccessible(true);
                volumePaths = (String[]) method.invoke(sm);
            }

        }catch (Exception e)
        {

        }

        if((volumePaths != null) && (volumePaths.length > 0))
        {
            for(String sdcardPath : volumePaths)
            {
                File fFile = new File(sdcardPath + "/Treadmill_Sign.apk");
                if(fFile.isDirectory())
                {
                }
                else if (fFile.isFile())
                {
                    if (fFile.exists())
                    {
                        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + fFile.getPath());
                        ApkUpdateProc mApkUpdate = new ApkUpdateProc(mEngModeActivity);

                        if(mApkUpdate.bApkUpdateProc_CheckService())
                        {
                            mApkUpdate.vApkUpdateProc_StartService();
                        }
                        mApkUpdate.vApkUpdateProc_InstallApk(mContext, fFile.getPath());
                    }
                    else
                    {
                    }
                }
            }
        }
    }

    private void vEngUpdateFrame_BikeUpdate()
    {
        final StorageManager sm = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);

        String[] volumePaths = new String[0];
        try
        {
            final Method method = sm.getClass().getMethod("getVolumePaths");
            if(method != null)
            {
                method.setAccessible(true);
                volumePaths = (String[]) method.invoke(sm);
            }

        }catch (Exception e)
        {

        }

        if((volumePaths != null) && (volumePaths.length > 0))
        {
            for(String sdcardPath : volumePaths)
            {
                File fFile = new File(sdcardPath + "/bike.json");
                if(fFile.isDirectory())
                {
                }
                else if (fFile.isFile())
                {
                    if (fFile.exists())
                    {
                        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + fFile.getPath());
                        vEngUpdateFrame_BikeJson(fFile.getPath());
                    }
                    else
                    {
                    }
                }
            }
        }
    }

    private  void vEngUpdateFrame_BikeJson(String sPathname)
    {
        Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + sPathname);

        String json;
        try {
            File f = new File(sPathname);
            InputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            float fval = 0f;

            json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);

            for(int iJsonArrayIndex = 0 ; iJsonArrayIndex < jsonArray.length(); iJsonArrayIndex++) {
                JSONObject jsonObjectUbike = jsonArray.getJSONObject(iJsonArrayIndex);
//                Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : "+jsonObjectUbike.getString("machinetype"));
                if (jsonObjectUbike.getString("machinetype").equals("ubike")) {
//                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + jsonObjectUbike.getString("current"));
//                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + jsonObjectUbike.getString("torque"));

                    JSONArray jsonArrayCurrent = jsonObjectUbike.getJSONArray("current");
                    JSONArray jsonArrayTorque = jsonObjectUbike.getJSONArray("torque");

                    for (int i = 0; i < jsonArrayCurrent.length(); i++) {
                        fval = check_int_input(jsonArrayCurrent.getString(i), fresistance_limit, EngSetting.f_Get_fmachine_rn_ubike(i));
                        EngSetting.v_Set_fmachine_rn_ubike(i, fval);
                        fval = check_int_input(jsonArrayTorque.getString(i), ftorqe_limit, EngSetting.f_Get_fmachine_tq_ubike(i));
                        EngSetting.v_Set_fmachine_tq_ubike(i, fval);
                    }
                }

                if (jsonObjectUbike.getString("machinetype").equals("rbike")) {
//                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + jsonObjectUbike.getString("current"));
//                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + jsonObjectUbike.getString("torque"));

                    JSONArray jsonArrayCurrent = jsonObjectUbike.getJSONArray("current");
                    JSONArray jsonArrayTorque = jsonObjectUbike.getJSONArray("torque");

                    for (int i = 0; i < jsonArrayCurrent.length(); i++) {
                        fval = check_int_input(jsonArrayCurrent.getString(i), fresistance_limit, EngSetting.f_Get_fmachine_rn_rbike(i));
                        EngSetting.v_Set_fmachine_rn_rbike(i, fval);
                        fval = check_int_input(jsonArrayTorque.getString(i), ftorqe_limit, EngSetting.f_Get_fmachine_tq_rbike(i));
                        EngSetting.v_Set_fmachine_tq_rbike(i, fval);
                    }
                }

                if (jsonObjectUbike.getString("machinetype").equals("elliptical")) {
//                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + jsonObjectUbike.getString("current"));
//                    Log.e("Jerry", Rtx_Log.__FLIE__() + "(" + Rtx_Log.__LINE__() + ") : " + jsonObjectUbike.getString("torque"));

                    JSONArray jsonArrayCurrent = jsonObjectUbike.getJSONArray("current");
                    JSONArray jsonArrayTorque = jsonObjectUbike.getJSONArray("torque");

                    for (int i = 0; i < jsonArrayCurrent.length(); i++) {
                        fval = check_int_input(jsonArrayCurrent.getString(i), fresistance_limit, EngSetting.f_Get_fmachine_rn_elliptical(i));
                        EngSetting.v_Set_fmachine_rn_elliptical(i, fval);
                        fval = check_int_input(jsonArrayTorque.getString(i), ftorqe_limit, EngSetting.f_Get_fmachine_tq_elliptical(i));
                        EngSetting.v_Set_fmachine_tq_elliptical(i, fval);
                    }
                }
            }

            v_BikeRrsistance_Fresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if (v == i_wifi) {
                if (DEBUG) Log.e(TAG, "==========i_wifi========");
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_WIFI);
            }
            else if (v == i_about) {
                if (DEBUG) Log.e(TAG, "==========i_about========");
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_ABOUT);
            }
            else if (v == i_setting) {
                if (DEBUG) Log.e(TAG, "==========i_setting========");
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_SETTING);
            }
            else if (v == i_update) {
                if (DEBUG) Log.e(TAG, "==========i_update========");
//                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_UPDATE);
            }
            else if (v == i_exit) {
                if (DEBUG) Log.e(TAG, "==========i_exit========");
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_EXIT);
            }
            else if (v == i_eng_logo) {
                if (DEBUG) Log.e(TAG, "==========i_eng_logo========");
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_UARTTEST);
            }
            else if (v == i_app) {
                if (DEBUG) Log.e(TAG, "==========i_app========");
                vEngUpdateFrame_AppUpdate();
            }
            else if (v == i_bike_resistance) {
                if (DEBUG) Log.e(TAG, "==========i_bike_resistance========");
                vEngUpdateFrame_BikeUpdate();
            }
        }

    }
}
