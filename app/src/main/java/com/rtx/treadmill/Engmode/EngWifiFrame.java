package com.rtx.treadmill.Engmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import com.rtx.treadmill.Engmode.wifi.AccessPoint;
import com.rtx.treadmill.Engmode.wifi.WifiAdapter;
import com.rtx.treadmill.Engmode.wifi.WifiController;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;

public class EngWifiFrame extends Rtx_BaseLayout {
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

    RtxTextView t_wifi_info;
    Switch sw_wifi;
    RtxImageView i_eng_line;

    ListView lv_eng_wifi;
    WifiAdapter adapter_wifi;

    WifiManager mwifi;
    WifiController mwificontrol = null ;

    private IntentFilter mFilter;
    private BroadcastReceiver mReceiver;
    public static ArrayList<AccessPoint> accesspoints = new ArrayList<AccessPoint>();
    public static boolean brescan = true ;

    public EngWifiFrame(Context context, EngModeActivity mEngModeActivity) {
        super(context);

        this.mContext = context;
        this.mEngModeActivity = mEngModeActivity;

        mwifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        mwificontrol = WifiController.getInstance(mContext, mwifi, accesspoints);

        mFilter = new IntentFilter();
        mFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                handleEvent(intent);
            }
        };
        mContext.registerReceiver(mReceiver, mFilter);

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
        v_EngWifiFrame_Refresh_data();
    }

    public void onDestroy()
    {
        v_EngWifiFrame_Remove_receiver();
        removeAllViews();
        System.gc();
    }


    private void handleEvent(Intent intent) {
        String action = intent.getAction();
        if(DEBUG) Log.e(TAG, "===handleEvent===" + action);
        if(brescan && mwificontrol != null && adapter_wifi != null)
        {
            mwificontrol.wifiscan_sort();
            adapter_wifi.notifyDataSetChanged();
        }
    }


    public void v_EngWifiFrame_Refresh_data() {
        if (DEBUG) Log.e(TAG, "Refresh_data");
        if(mwifi.isWifiEnabled()) {
            sw_wifi.setChecked(true);
            sw_wifi.setThumbResource(R.drawable.wifi_btn_on);
            sw_wifi.setTrackResource(R.drawable.wifi_track_on);
        }
        else
        {
            sw_wifi.setChecked(false);
            sw_wifi.setThumbResource(R.drawable.wifi_btn_off);
            sw_wifi.setTrackResource(R.drawable.wifi_track_off);
        }
    }

    public void v_EngWifiFrame_Remove_receiver() {
        if (DEBUG) Log.e(TAG, "Remove_receiver");
        if(mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }

    private void add_view(ListView mListView , int iX , int iY , int iWidth , int iHeight) {
        LayoutParams mLayoutParams = new LayoutParams(iWidth,iHeight);
        mLayoutParams.leftMargin = iX;
        mLayoutParams.topMargin = iY;
        mListView.setLayoutParams(mLayoutParams);

        addView(mListView);
    }

    private void add_view(Switch mView ,int gravity, int iX , int iY , int iWidth , int iHeight, int itrack, int ithumb) {
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

        if(t_wifi_info == null)           {t_wifi_info = new RtxTextView(mContext);}
        if(sw_wifi == null)           {sw_wifi = new Switch(mContext);}
        if(i_eng_line == null)           {i_eng_line = new RtxImageView(mContext);}
        if(adapter_wifi == null)           {adapter_wifi = new WifiAdapter((ArrayList<AccessPoint>)accesspoints, mContext);}
        if(lv_eng_wifi == null)           {lv_eng_wifi = new ListView(mContext);}


    }

    private void init_event()
    {
        i_wifi.setOnClickListener(mButtonListener);
        i_about.setOnClickListener(mButtonListener);
        i_setting.setOnClickListener(mButtonListener);
        i_update.setOnClickListener(mButtonListener);
        i_exit.setOnClickListener(mButtonListener);

        sw_wifi.setOnCheckedChangeListener(wifi_but);
        lv_eng_wifi.setOnItemClickListener(wifilisten);

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

        ih = 525/4-5;
        iy += 5;
        addRtxImageViewToLayout(i_wifi, R.drawable.eng_but, ix, iy, iw, ih);
        i_wifi.setScaleType(ImageView.ScaleType.FIT_XY);
        iy -= 5;

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

        ih = 525/4+5;
        addRtxImageViewToLayout(i_update, -1, ix, iy, iw, ih);

        ix = 40;
        iy = 185;
        iw = 245;
        ih = 525/4;
        fsize = 36f;
        sdata = mContext.getResources().getString(R.string.engwifi);
        addRtxTextViewToLayout(t_wifi, sdata.toUpperCase(), fsize, Common.Color.engmode_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 525/4;
        sdata = mContext.getResources().getString(R.string.engabout);
        addRtxTextViewToLayout(t_about, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 525/4;
        sdata = mContext.getResources().getString(R.string.engsetting);
        addRtxTextViewToLayout(t_setting, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += 525/4;
        sdata = mContext.getResources().getString(R.string.engupdate);
        addRtxTextViewToLayout(t_update, sdata.toUpperCase(), fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ix = 285;
        iy = 190;
        iw = 950;
        ih = 80;
        addRtxText(t_wifi_info, R.string.engwifi, fsize, Common.Color.engmode_word_gray, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER_VERTICAL, ix, iy, iw, ih, 0);
        t_wifi_info.setPadding(20, 0, 0, 0);

        add_view(sw_wifi, Gravity.RIGHT|Gravity.CENTER_VERTICAL, ix, iy, iw, ih, R.drawable.wifi_track_on, R.drawable.wifi_btn_on);
        sw_wifi.setPadding(0, 0, 30, 0);

        iy += 80;
        ih = 10;
        addRtxImage(null, i_eng_line, R.drawable.eng_line, ix, iy, iw, ih, 0, ImageView.ScaleType.FIT_XY);

        ix = 285;
        iy = 280;
        iw = 950;
        ih = 420;
        lv_eng_wifi.setAdapter(adapter_wifi);
        add_view(lv_eng_wifi, ix, iy, iw, ih);

    }

    private Switch.OnCheckedChangeListener wifi_but = new Switch.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                toggleWiFi(true);
                sw_wifi.setThumbResource(R.drawable.wifi_btn_on);
                sw_wifi.setTrackResource(R.drawable.wifi_track_on);
                mwifi.startScan();
                lv_eng_wifi.setVisibility(View.VISIBLE);
            } else {
                toggleWiFi(false);
                sw_wifi.setThumbResource(R.drawable.wifi_btn_off);
                sw_wifi.setTrackResource(R.drawable.wifi_track_off);
                lv_eng_wifi.setVisibility(View.INVISIBLE);
            }
        }
    };

    public void toggleWiFi(boolean status) {
        //if (DEBUG) Log.e(TAG, "status=" + status);
        if (status == true && !mwifi.isWifiEnabled()) {
            mwifi.setWifiEnabled(true);
        } else if (status == false && mwifi.isWifiEnabled()) {
            mwifi.setWifiEnabled(false);
        }
    }

    private AdapterView.OnItemClickListener wifilisten = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
        {
            if (DEBUG) Log.e(TAG, "AdapterView=" + arg0 + "===View=" + arg1 + "===position=" + position + "===arg3=" + arg3);
            brescan = false;
            mwificontrol.selectid(accesspoints.get(position));
        }
    };

    class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if (v == i_wifi) {
                if (DEBUG) Log.e(TAG, "==========i_wifi========");
                //mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_WIFI);
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
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_UPDATE);
            }
            else if (v == i_exit) {
                if (DEBUG) Log.e(TAG, "==========i_exit========");
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_EXIT);
            }
            else if (v == i_eng_logo) {
                if (DEBUG) Log.e(TAG, "==========i_eng_logo========");
                mEngModeActivity.mEngModeProc.vSetNextState(EngModeState.PROC_SHOW_UARTTEST);
            }
        }

    }
}
