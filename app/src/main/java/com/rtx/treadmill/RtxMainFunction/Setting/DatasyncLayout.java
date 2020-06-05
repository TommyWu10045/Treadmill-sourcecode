package com.rtx.treadmill.RtxMainFunction.Setting;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.retonix.circlecloud.Cloud_62_GET_TRD_TKN;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by chasechang on 3/22/17.
 */

public class DatasyncLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry=" ;
    private boolean DEBUG = true;

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity   mMainActivity;

    ScrollView scrollView;
    FrameLayout fscroll;

    RtxTextView t_fitness;
    RtxTextView t_wearable;

    RtxImageView[] i_vendor;
    RtxImageView[] i_switch;
    RtxTextView[] t_vendor;
    RtxTextView[] t_switch_on;
    RtxTextView[] t_switch_off;

    private int ifitness_list[][] = {
            // name, icon, cloud id, 65 en/dis, 62 TKN_TAG , 62 TRD_OF , 62 LK_NUM
            //fitness
//            {R.string.nike_plus,    R.drawable.session_nikeplus,    1,  0,  0,  0,  0},
            {R.string.google_fit,   R.drawable.session_googlefit,   2,  0,  0,  0,  0},
            {R.string.map_my_run,        R.drawable.session_run,    3,  0,  0,  0,  0},
            {R.string.map_my_walk,        R.drawable.session_walk,    3,  0,  0,  0,  0},
            {R.string.map_my_ride,        R.drawable.session_ride,    3,  0,  0,  0,  0},
            {R.string.runkeeper,    R.drawable.session_runkeeper,   4,  0,  0,  0,  0},
            //wearable
            {R.string.fitbit,       R.drawable.session_fitbit,      5,  0,  0,  0,  0},
//            {R.string.jawbone,      R.drawable.session_jawbone,     6,  0,  0,  0,  0},
            {R.string.garmin,       R.drawable.session_garmin,      7,  0,  0,  0,  0}
    };
    int imax = ifitness_list.length;
    int ifitness_count = 5;

    public DatasyncLayout(Context context, MainActivity mMainActivity) {
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
        int iLoop ;

        init_BackPrePage();

        init_Title();

        if(fscroll == null)     {   fscroll = new FrameLayout(mContext);}
        if(scrollView == null)  {   scrollView = new ScrollView(mContext); }
        else {
            scrollView.removeAllViews();
        }
        if(t_fitness == null)  {   t_fitness = new RtxTextView(mContext); }
        if(t_wearable == null)  {   t_wearable = new RtxTextView(mContext); }

        if(i_vendor == null)
        {
            i_vendor = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++)
            {
                i_vendor[iLoop] = new RtxImageView(mContext);
            }
        }

        if(i_switch == null)
        {
            i_switch = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++)
            {
                i_switch[iLoop] = new RtxImageView(mContext);
            }
        }

        if(t_vendor == null)
        {
            t_vendor = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++)
            {
                t_vendor[iLoop] = new RtxTextView(mContext);
            }
        }

        if(t_switch_on == null)
        {
            t_switch_on = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++)
            {
                t_switch_on[iLoop] = new RtxTextView(mContext);
            }
        }

        if(t_switch_off == null)
        {
            t_switch_off = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++)
            {
                t_switch_off[iLoop] = new RtxTextView(mContext);
            }
        }

    }

    private void init_event(){
        int iLoop;
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        for (iLoop = 0; iLoop < imax; iLoop++){
            t_switch_on[iLoop].setOnClickListener(mButtonListener);
            t_switch_off[iLoop].setOnClickListener(mButtonListener);
        }
    }

    private void add_View(){
        int ix, iy, iw, ih;
        int ix_temp, iy_temp, iw_temp, ih_temp;
        float fsize, fsize_temp;
        String sdata;
        int iLoop ;
        int iCount_enable = 0;
        int iCount_enable_fitness = 0;
        int iCount_enable_wearable = 0;

        sdata = LanguageData.s_get_string(mContext, R.string.data_syncing);
        vSetTitleText(sdata.toUpperCase());

        scrollView.addView(fscroll);
        addViewToLayout(scrollView, 70, 150, 1140, 600);

        ix = 0;
        iy = 0;
        iw = 1140;
        ih = 100;
        fsize = 30f;
        fsize_temp = 13.33f;
        sdata = LanguageData.s_get_string(mContext, R.string.fitness_apps);
        addRtxTextView(fscroll, t_fitness, sdata.toUpperCase(), fsize, Common.Color.setting_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 242;
        iy = 100;
        iw = 86;
        ih = 86;
        ix_temp = ix - 167;
        iy_temp = iy + 127;
        iw_temp = 480;
        ih_temp = 62;
        iCount_enable_fitness = 0;
        for(iLoop = 0; iLoop < ifitness_count; iLoop++)
        {
            ifitness_list[iLoop][3] = 0;
            if(b_check_cloud_enable(ifitness_list[iLoop][2])) {
                iCount_enable_fitness++;
                ifitness_list[iLoop][3] = 1;
                addRtxImage(fscroll, i_vendor[iLoop], ifitness_list[iLoop][1], ix+30, iy, iw, ih, 0, ImageView.ScaleType.CENTER_INSIDE);
                addRtxImage(fscroll, i_switch[iLoop], R.drawable.switch_right2, ix_temp, iy_temp, iw_temp, ih_temp, 0, ImageView.ScaleType.CENTER_INSIDE);
                sdata = LanguageData.s_get_string(mContext, R.string.on);
                addRtxTextView(fscroll, t_switch_on[iLoop], sdata.toUpperCase(), fsize, Common.Color.setting_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp+35, iy_temp, iw_temp / 2, ih_temp);
                sdata = LanguageData.s_get_string(mContext, R.string.off);
                addRtxTextView(fscroll, t_switch_off[iLoop], sdata.toUpperCase(), fsize, Common.Color.setting_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp + (iw_temp-70) / 2, iy_temp, iw_temp / 2, ih_temp);
                sdata = LanguageData.s_get_string(mContext, ifitness_list[iLoop][0]);
                addRtxTextView(fscroll, t_vendor[iLoop], sdata, fsize_temp, Common.Color.setting_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp - 40, iw_temp, 40);

                if (iCount_enable_fitness % 2 == 0) {
                    ix = 242;
                    iy += 250;
                } else {
                    ix += 570;
                }
                ix_temp = ix - 167;
                iy_temp = iy + 127;
            }
        }

        ix = 0;
        iy += 100 + (iCount_enable_fitness % 2) * 250;
        iw = 1140;
        ih = 100;
        sdata = LanguageData.s_get_string(mContext, R.string.wearable_apps);
        addRtxTextView(fscroll, t_wearable, sdata.toUpperCase(), fsize, Common.Color.setting_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 242;
        iy += 100;
        iw = 86;
        ih = 86;
        ix_temp = ix - 167;
        iy_temp = iy + 127;
        iw_temp = 480;
        ih_temp = 62;
        iCount_enable_wearable = 0;
        for(iLoop = ifitness_count; iLoop < imax ; iLoop++)
        {
            ifitness_list[iLoop][3] = 0;
            if(b_check_cloud_enable(ifitness_list[iLoop][2])) {
                iCount_enable_wearable++;
                ifitness_list[iLoop][3] = 1;
                addRtxImage(fscroll, i_vendor[iLoop], ifitness_list[iLoop][1], ix+30, iy, iw, ih, 0, ImageView.ScaleType.CENTER_INSIDE);
                addRtxImage(fscroll, i_switch[iLoop], R.drawable.switch_right2, ix_temp, iy_temp, iw_temp, ih_temp, 0, ImageView.ScaleType.CENTER_INSIDE);
                sdata = LanguageData.s_get_string(mContext, R.string.on);
                addRtxTextView(fscroll, t_switch_on[iLoop], sdata.toUpperCase(), fsize, Common.Color.setting_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp+35, iy_temp, iw_temp / 2, ih_temp);
                sdata = LanguageData.s_get_string(mContext, R.string.off);
                addRtxTextView(fscroll, t_switch_off[iLoop], sdata.toUpperCase(), fsize, Common.Color.setting_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp + (iw_temp-70) / 2, iy_temp, iw_temp / 2, ih_temp);
                sdata = LanguageData.s_get_string(mContext, ifitness_list[iLoop][0]);
                addRtxTextView(fscroll, t_vendor[iLoop], sdata, fsize_temp, Common.Color.setting_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix_temp, iy_temp - 40, iw_temp, 40);

                if (iCount_enable_wearable % 2 == 0) {
                    ix = 242;
                    iy += 250;
                } else {
                    ix += 570;
                }
                ix_temp = ix - 167;
                iy_temp = iy + 127;
            }
        }

        iCount_enable = iCount_enable_fitness + iCount_enable_wearable;

        v_fresh_switch();

    }

    private boolean b_check_cloud_enable(int id)
    {
        boolean bret = false;
        TreeMap<Integer, String[]> mTree = CloudDataStruct.CloudData_65.tree_clound_cmd65_result;

        for (Integer key : mTree.keySet()) {
            if(key == id)
            {
                bret = true;
                break;
            }
        }

        return bret;
    }

    private boolean b_check_enable(int id)
    {
        boolean bret = false;
        TreeMap<Integer, String[]> mTree = CloudDataStruct.CloudData_62.tree_clound_cmd62_result;

        Iterator iterator = mTree.keySet().iterator();
        String[] value;

        if(mTree != null)
        {
            value = mTree.get(id);
            if (value != null) {
                if(value[Cloud_62_GET_TRD_TKN.Output.TRD_OF].compareTo("1") == 0)
                {
                    bret = true;
                }
            }
        }

        return bret;
    }

    private void v_fresh_switch()
    {
        int iLoop;

        for (iLoop = 0; iLoop < imax; iLoop++)
        {
            if(ifitness_list[iLoop][3] == 1)
            {
                if(b_check_enable(ifitness_list[iLoop][2]))
                {
                    v_fresh_switch_on(iLoop);
                }
                else
                {
                    v_fresh_switch_off(iLoop);
                }
            }
        }
    }

    private void v_fresh_switch_on(int iLoop)
    {
        ifitness_list[iLoop][5]   = 1;
        i_switch[iLoop].setImageResource(R.drawable.switch_left);
        t_switch_on[iLoop].setTextColor(Common.Color.setting_word_white);
        t_switch_off[iLoop].setTextColor(Common.Color.setting_word_blue);
    }

    private void v_fresh_switch_off(int iLoop)
    {
        ifitness_list[iLoop][5]   = 0;
        i_switch[iLoop].setImageResource(R.drawable.switch_right);
        t_switch_on[iLoop].setTextColor(Common.Color.setting_word_blue);
        t_switch_off[iLoop].setTextColor(Common.Color.setting_word_white);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void vClickBack()
    {
        mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_SHOW_SETTING);
    }


    private void v_check_app_on(int isel)
    {
        String sdata;

        if(ifitness_list[isel][5] == 0)
        {
            sdata = LanguageData.s_get_string(mContext, ifitness_list[isel][0]);
            if(DEBUG) Log.e(TAG, "v_check_app_on=" + sdata);
            mMainActivity.mMainProcTreadmill.settingProc.vSet_trd_pty_app(ifitness_list[isel][2]);
            mMainActivity.mMainProcTreadmill.settingProc.vSet_trd_of("1");
            mMainActivity.mMainProcTreadmill.settingProc.vSet_trd_id("");
            mMainActivity.mMainProcTreadmill.settingProc.vSet_trd_pw("");

            switch (ifitness_list[isel][2])
            {
                case 1:
                    mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_SHOW_DATASYNC_NIKE);
                    break;

                case 2:
                    mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_SHOW_DATASYNC_GOOGLE);
                    break;

                case 3:
                    mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_SHOW_DATASYNC_MYMAP);
                    break;

                case 4:
                    mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_SHOW_DATASYNC_RUNKEEPER);
                    break;

                case 5:
                    mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_SHOW_DATASYNC_FITBIT);
                    break;

                case 6:
                    mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_SHOW_DATASYNC_JAWBONE);
                    break;

                case 7:
                    mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_SHOW_DATASYNC_GARMIN);
                    break;

                default:
                    break;
            }
        }
    }

    private void v_check_app_off(int isel)
    {
        String sdata;

        if(ifitness_list[isel][5] == 1)
        {
            sdata = LanguageData.s_get_string(mContext, ifitness_list[isel][0]);
            if(DEBUG) Log.e(TAG, "v_check_app_off=" + sdata);
            mMainActivity.mMainProcTreadmill.settingProc.vSet_trd_pty_app(ifitness_list[isel][2]);
            mMainActivity.mMainProcTreadmill.settingProc.vSet_trd_of("0");
            mMainActivity.mMainProcTreadmill.settingProc.vSet_trd_id("");
            mMainActivity.mMainProcTreadmill.settingProc.vSet_trd_pw("");
            mMainActivity.mMainProcTreadmill.settingProc.vSetNextState(SettingState.PROC_CLOUD_43_SET);
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)  { vClickBack(); }
            else
            {
                int iLoop;

                for(iLoop = 0; iLoop < imax; iLoop++)
                {
                    if(t_switch_off[iLoop] == v)
                    {
                        v_check_app_off(iLoop);
                        break;
                    }
                    else if(t_switch_on[iLoop] == v)
                    {
                        v_check_app_on(iLoop);
                        break;
                    }
                }
            }

        }
    }
}
