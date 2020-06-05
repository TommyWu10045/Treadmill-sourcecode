package com.rtx.treadmill.RtxMainFunctionBike.Performance;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.retonix.circlecloud.Cloud_21_GET_EXC_REC;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayoutE;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_Debug;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;


/**
 * Created by chasechang on 3/22/17.
 */

public class SessionLayoutE extends Rtx_BaseLayoutE {
    private String TAG = "Jerry";

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity mMainActivity;

    private RtxFillerTextView[] f_but;

    private FrameLayout f_back;
    public RtxImageView      i_prev;
    private RtxTextView      t_date;
    private RtxTextView      t_time;
    public RtxImageView      i_next;

    private RtxImageView     i_add;
    private RtxTextView      t_add;
    public RtxImageView      i_share;
    public RtxTextView       t_share;
    private RtxImageView     i_delete;
    private RtxTextView      t_delete;
    private RtxTextView      t_SportName;
    private RtxTextView      t_SportManualName;

    private RtxImageView i_type_source; //EXC_MDL
    private RtxImageView i_type_dot;
    private RtxImageView i_type_destination;//MCH_TYPE

    private RtxImageView[]            i_data;
    private RtxDoubleStringView[]     t_data;
    private RtxTextView[]             t_info;

    private int ichoice = 0;
    private int ichoice_max;
    private ArrayList<String[]> sessionlist = CloudDataStruct.CloudData_21.clound_cmd21_list;
    private int icolor_en = Common.Color.pf_word_white;
    private int icolor_dis = Common.Color.pf_word_gray;

    private int istr_item[] = {
            R.string.session1  ,  R.string.month  ,  R.string.year
    };
    private int i_but = istr_item.length;

    private int istr_list[][] = {
            {R.drawable.pf_distance_icon  ,  R.string.distance },
            {R.drawable.pf_duration_icon  ,  R.string.duration },
            {R.drawable.pf_calories_icon  ,  R.string.calories },
            {R.drawable.pf_pace_icon  ,  R.string.avg_pace },
            {R.drawable.pf_hr_icon  ,  R.string.avg_heart_rate },
            {R.drawable.pf_watt_icon  ,  R.string.avg_watt },
    };
    private int imax = istr_list.length;

    private int[] ishow_session_list = {
            Cloud_21_GET_EXC_REC.Output.RUN_DST,
            Cloud_21_GET_EXC_REC.Output.DRT_TIM,
            Cloud_21_GET_EXC_REC.Output.COS_CAL,
            Cloud_21_GET_EXC_REC.Output.AVG_PAC,
            Cloud_21_GET_EXC_REC.Output.AVG_HRT_RAT,
            Cloud_21_GET_EXC_REC.Output.AVG_WAT
    };


    public SessionLayoutE(Context context, MainActivity mMainActivity) {
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

    public void init_View()
    {
        int iLoop;

        init_BackHome();
        init_BackExercise();

        if(f_but == null) {
            f_but = new RtxFillerTextView[i_but];
            for (iLoop = 0; iLoop < i_but; iLoop++) {
                f_but[iLoop] = new RtxFillerTextView(mContext);
            }
        }

        if(f_back == null)    { f_back = new FrameLayout(mContext);     }
        f_back.removeAllViews();
        if(i_prev == null)    { i_prev = new RtxImageView(mContext);     }
        if (t_date == null)   { t_date = new RtxTextView(mContext);     }
        if (t_time == null)   { t_time = new RtxTextView(mContext);     }
        if(i_next == null)    { i_next = new RtxImageView(mContext);     }

        if(i_add == null)    { i_add = new RtxImageView(mContext);     }
        if(t_add == null)    { t_add = new RtxTextView(mContext);     }
        if(i_share == null)    { i_share = new RtxImageView(mContext);     }
        if(t_share == null)    { t_share = new RtxTextView(mContext);     }
        if(i_delete == null)    { i_delete = new RtxImageView(mContext);     }
        if(t_delete == null)    { t_delete = new RtxTextView(mContext);     }

        if(i_type_source == null)    { i_type_source = new RtxImageView(mContext);     }
        if(i_type_dot == null)    { i_type_dot = new RtxImageView(mContext);     }
        if(i_type_destination == null)    { i_type_destination = new RtxImageView(mContext);     }

        if(i_data == null) {
            i_data = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                i_data[iLoop] = new RtxImageView(mContext);
            }
        }

        if(t_data == null) {
            t_data = new RtxDoubleStringView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_data[iLoop] = new RtxDoubleStringView(mContext);
            }
        }

        if(t_info == null) {
            t_info = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_info[iLoop] = new RtxTextView(mContext);
            }
        }

        if (t_SportName == null){
            t_SportName = new RtxTextView(mContext);
        }

        if (t_SportManualName == null){
            t_SportManualName = new RtxTextView(mContext);
        }

    }

    public void init_event()
    {
        int iLoop;
        i_delete.setOnClickListener(mButtonListener);

        for (iLoop = 0; iLoop < i_but; iLoop++) {
            f_but[iLoop].setOnClickListener(mButtonListener);
        }

        imageView_BackHome.setOnClickListener(mButtonListener);
        imageView_ReturnExercisePage.setOnClickListener(mButtonListener);
        i_prev.setOnClickListener(mButtonListener);
        i_next.setOnClickListener(mButtonListener);

    }

    public void add_View() {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize, fsize_unit;
        String sdata;
        String sunit;
        int ipadding = 30;
        int ix_shift;

        //Item select
        ix = 310;
        iy = 30;
        iw = 200;
        ih = 50;//By Alan
        fsize = 18f;
        for(iLoop = 0; iLoop < i_but; iLoop++)
        {
            sdata = LanguageData.s_get_string(mContext, istr_item[iLoop]).toUpperCase();
            addRtxTextViewToLayout(f_but[iLoop], sdata, fsize, Common.Color.pf_word_yellow, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
            f_but[iLoop].setBackgroundColor(Common.Color.pf_word_yellow);
            ix += iw + 30;
            if(iLoop != 0)
            {
                f_but[iLoop].setMode(5);
                f_but[iLoop].setTextColor(Common.Color.pf_word_yellow);
            }
            else
            {
                f_but[iLoop].setMode(4);
                f_but[iLoop].setTextColor(Common.Color.pf_word_marker);
            }
        }

        //        frame
        ix = 0;
        iy = 100;
        iw = 1280;
        ih = 800;
        addRtxViewToLayout(f_back, ix, iy, iw, ih, Common.Color.pf_exercise_back);

        //      prev and next icon
        ix = 460;
        iy = 55;
        iw = 35;
        ih = 35;
        addRtxImage(f_back, i_prev, R.xml.pf_prev_icon, ix, iy, iw, ih, ipadding, null);

        ix += 330;
        addRtxImage(f_back, i_next, R.xml.pf_next_icon, ix, iy, iw, ih, ipadding, null);

        //date
        ix = 495;
        iy = 55;
        iw = 295;
        ih = 35;
        fsize = 20f;
        sdata = "";
        addRtxTextView(f_back, t_date, sdata.toUpperCase(), fsize, Common.Color.pf_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        sdata = "";
        addRtxTextView(f_back, t_time, sdata.toUpperCase(), fsize, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        //add
        ix = 220;
        iy = 210;
        iw = 30;
        ih = 30;
        addRtxImage(f_back, i_add, R.xml.pf_add_session, ix, iy, iw, ih, ipadding, null);

        ix = 190 - 45;
        iy = 250;
        iw = 90 + 90;
        ih = 30;
        fsize = 18f;
        sdata = LanguageData.s_get_string(mContext, R.string.add);
        addRtxTextView(f_back, t_add, sdata.toUpperCase(), fsize, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        //share
        ix = 1120;
        iy = 180;
        iw = 30;
        ih = 30;
        addRtxImage(f_back, i_share, R.xml.pf_share_icon, ix, iy, iw, ih, ipadding, null);
        vRtxBaseLayoutE_SetShareView(i_share);

        ix = 1090;
        iy = 220;
        iw = 90;
        ih = 40;
        sdata = LanguageData.s_get_string(mContext, R.string.share);
        addRtxTextView(f_back, t_share, sdata.toUpperCase(), fsize, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        vRtxBaseLayoutE_SetShareView(t_share);

        //delete
        ix = 1120;
        if(Rtx_Debug.bRtxDebug_GetShareEnable())
        {
            iy = 285;
        }
        else
        {
            iy = 210;
        }
        iw = 30;
        ih = 30;
        addRtxImage(f_back, i_delete, R.xml.pf_delete_icon, ix, iy, iw, ih, ipadding, null);

        ix = 1090 - 45;
        if(Rtx_Debug.bRtxDebug_GetShareEnable())
        {
            iy = 325;
        }
        else
        {
            iy = 250;
        }
        iw = 90 + 90;
        ih = 40;
        sdata = LanguageData.s_get_string(mContext, R.string.delete);
        addRtxTextView(f_back, t_delete, sdata.toUpperCase(), fsize, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        //source icon
        ix = 410;
        iy = 190;
        iw = 120;
        ih = 120;
        ipadding = 0;
        addRtxImage(f_back, i_type_source, R.drawable.pf_destination_null, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);

        //dot icon
        ix = 545;
        iy = 200;
        iw = 210;
        ih = 100;
        addRtxImage(f_back, i_type_dot, R.drawable.pf_dot_null, ix, iy, iw, ih, ipadding, null);

        //destination icon
        ix = 770;
        iy = 190;
        iw = 120;
        ih = 120;
        addRtxImage(f_back, i_type_destination, R.drawable.pf_source_null, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);

        //bottom info
        ix = 200;
        iy = 405;
        iw = 30;
        ih = 30;
        ix_shift = 170;
        ipadding = 0;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            addRtxImage(f_back, i_data[iLoop], istr_list[iLoop][0], ix, iy, iw, ih, ipadding, ImageView.ScaleType.CENTER_INSIDE);
            ix += ix_shift;
        }

        ix = 140;//By Alan
        iy = 445;
        iw = 150;//By Alan
        ih = 40;
        fsize = 33.32f;
        fsize_unit = 18f;
        sdata = "";
        sunit = "";
        for(iLoop = 0; iLoop < imax; iLoop++) {
            addRtxDoubleStringView(f_back, t_data[iLoop], ix, iy, iw, ih);
            t_data[iLoop].setGap(15);
            t_data[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.pf_word_white, fsize, Common.Font.Relay_BlackItalic, Common.Color.pf_word_blue, fsize_unit);
            t_data[iLoop].setText(sdata.toUpperCase(), sunit.toLowerCase());
            ix += ix_shift;
        }

        ix = 140;//By Alan
        iy = 485;
        iw = 150;//By Alan
        ih = 40;
        fsize = 18f;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            sdata = LanguageData.s_get_string(mContext, istr_list[iLoop][1]);
            addRtxTextView(f_back, t_info[iLoop], sdata.toUpperCase(), fsize, Common.Color.pf_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

            ix += ix_shift;
        }

        i_add.setEnabled(false);
        t_add.setEnabled(false);
        t_add.setTextColor(icolor_dis);
        i_share.setEnabled(false);
        t_share.setEnabled(false);
        t_share.setTextColor(icolor_dis);
        i_delete.setEnabled(false);
        t_delete.setEnabled(false);
        t_delete.setTextColor(icolor_dis);

        ichoice = 0;


        addRtxTextViewToLayout(t_SportName, "", 25, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 550, 390, 200, 50);
        addRtxTextViewToLayout(t_SportManualName, "", 25, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 550, 420, 200, 50);

        Refresh_All_data();


    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void v_set_buttom_data(int imode, String[] sdata)
    {
        int[] ishow_list;

        ishow_list = ishow_session_list;

        int ishow_max = ishow_list.length;
        int idata_max;
        int iLoop;
        String stemp = "";
        String[] sval = new String[ishow_max];
        String[] sunit = new String[ishow_max];
        float fval;

        if (sdata == null) {
            for (iLoop = 0; iLoop < ishow_max; iLoop++) {
                if (iLoop == 0) {
                    stemp = "-";
                } else if (iLoop == 1) {
                    stemp = "--:--";
                } else if (iLoop == 2) {
                    stemp = "-";
                } else if (iLoop == 3) {
                    stemp = "--:--";
                } else if (iLoop == 4) {
                    stemp = "-";
                } else if (iLoop == 5) {
                    stemp = "-";
                }

                stemp = "";
                sval[iLoop] = stemp;
                sunit[iLoop] = "";
            }
        } else {
            idata_max = sdata.length;

            for (iLoop = 0; iLoop < ishow_max; iLoop++) {
                if (ishow_list[iLoop] < idata_max) {
                    if (iLoop == 0) {
                        stemp = EngSetting.Distance.getValString(sdata[ishow_list[iLoop]]);
                    } else if (iLoop == 1) {
                        fval = Rtx_Calendar.cStr2Sec(sdata[ishow_list[iLoop]], "HH:mm:ss");
                        stemp = Rtx_Calendar.s_trans_integer(5, (int) fval);
                    } else if (iLoop == 2) {
                        fval = Rtx_TranslateValue.fString2Float(sdata[ishow_list[iLoop]]);
                        stemp = Rtx_TranslateValue.sFloat2String((int) fval, 0);
                    } else if (iLoop == 3) {
                        fval = Rtx_Calendar.cStr2Sec(sdata[ishow_list[iLoop]], "HH:mm:ss");
                        stemp = Rtx_Calendar.s_trans_integer(5, (int) fval);
                    } else if (iLoop == 4) {
                        fval = Rtx_TranslateValue.fString2Float(sdata[ishow_list[iLoop]]);
                        stemp = Rtx_TranslateValue.sFloat2String((int) fval, 0);
                    } else if (iLoop == 5) {
                        fval = Rtx_TranslateValue.fString2Float(sdata[ishow_list[iLoop]]);
                        stemp = Rtx_TranslateValue.sFloat2String((int) fval, 0);
                    }
                    sval[iLoop] = stemp;
                    sunit[iLoop] = "";
                }
            }
        }
        v_set_data(ishow_max, sval, sunit);

    }

    private void v_set_data(int ishow_max, String[] sdata, String[] sunit)
    {
        int ilen_data, ilen_unit, iLoop ;

        if(sdata != null && sunit != null)
        {
            ilen_data = sdata.length;
            ilen_unit = sunit.length;
            if(ilen_data == ishow_max && ilen_unit == ishow_max)
            {
                for(iLoop = 0; iLoop < ishow_max; iLoop++)
                {
                    t_data[iLoop].setText(sdata[iLoop], sunit[iLoop]);
                }
            }
        }
    }

    private void v_trans_show_data(String[] sdata)
    {

        String stemp = "";

        v_set_buttom_data(0, sdata);

        v_icon_show(sdata);

        if(sdata == null)
        {
            t_date.setText(stemp);
            t_time.setText(stemp);
        }
        else
        {
            stemp = Rtx_Calendar.s_trans_DateTime_Str(1, "M/dd/yyyy", "yyyy-MM-dd HH:mm:ss", sdata[Cloud_21_GET_EXC_REC.Output.CRE_DT], 0, 0);
            t_date.setText(stemp);
            stemp = Rtx_Calendar.s_trans_DateTime_Str(1, "h:mm a", "yyyy-MM-dd HH:mm:ss", sdata[Cloud_21_GET_EXC_REC.Output.CRE_DT], 0, 0);
            t_time.setText(stemp);
        }

    }

    private void v_icon_show(String[] sdata)
    {
        int isource = R.drawable.pf_source_null;
        int idot = R.drawable.pf_dot_null;
        int ides = R.drawable.pf_destination_null;
        char ctype;
        char stype;
        String ctemp;
        String stemp;
        int iLoop;

        if(sdata == null)
        {
            i_delete.setEnabled(false);
            t_delete.setEnabled(false);
            t_delete.setTextColor(icolor_dis);
        }
        else
        {
            i_delete.setEnabled(false);
            t_delete.setEnabled(false);
            t_delete.setTextColor(icolor_dis);
//            i_delete.setEnabled(true);
//            t_delete.setTextColor(icolor_en);

            stemp = sdata[Cloud_21_GET_EXC_REC.Output.EXC_MDL];
            String stemp2 = sdata[Cloud_21_GET_EXC_REC.Output.EXC_MDL_NAM];
            t_SportManualName.setText(stemp2);
            if(stemp != null && stemp.length() > 0) {
                ctype = stemp.charAt(0);
                for (iLoop = 0; iLoop < PerformanceFunc.ssession_EXC_MDL_icon_name.length; iLoop++) {
                    stype = PerformanceFunc.ssession_EXC_MDL_icon_name[iLoop].charAt(0);
                    if (ctype == stype) {
                        isource = PerformanceFunc.isession_EXC_MDL_icon_id[iLoop];
                        break;
                    }
                }
            }

            stemp = sdata[Cloud_21_GET_EXC_REC.Output.MCH_TYP];
            if(stemp != null && stemp.length() > 0) {
                for (iLoop = 0; iLoop < PerformanceFunc.ssession_MCH_TYP_icon_name.length; iLoop++) {
                    ctemp = PerformanceFunc.ssession_MCH_TYP_icon_name[iLoop];
                    if (stemp.toLowerCase().compareTo(ctemp.toLowerCase()) == 0) {
                        ides = PerformanceFunc.isession_MCH_TYP_icon_id[iLoop];
                        break;
                    }

                    ides = R.drawable.session_manual;
                }
            }

            t_SportName.setText(stemp);

            idot = R.drawable.pf_dot_icon;
        }

        i_type_source.setImageResource(ides);
        i_type_dot.setImageResource(idot);
        i_type_destination.setImageResource(isource);

    }

    private boolean Refresh_session_maxsize()
    {
        boolean bret = false;
        Log.e(TAG, "====sessionlist.size()=" + sessionlist.size());

        if(sessionlist != null) {
            ichoice_max = sessionlist.size();
            if(ichoice_max > 0)
            {
                bret = true;
            }
        }
        else
        {
            ichoice_max = 0;
            ichoice = 0;
        }

        return bret;
    }

    private void v_next_prev_check() {

        if(ichoice_max <= 1)
        {
            i_next.setEnabled(false);
            i_prev.setEnabled(false);
        }
        else {
            i_next.setEnabled(true);
            i_prev.setEnabled(true);

            if (ichoice == 0) {
                i_next.setEnabled(false);
            }

            if (ichoice == (ichoice_max - 1)) {
                i_prev.setEnabled(false);
            }
        }
    }

    private void Refresh_All_data()
    {
        String[] scurr_data = null;

        if(Refresh_session_maxsize())
        {
            if(ichoice < ichoice_max)
            {
                scurr_data = sessionlist.get(ichoice);
            }
        }
        v_trans_show_data(scurr_data);

        v_next_prev_check();
    }

    private void vupdate_datalist(int inum)
    {
        Log.e(TAG, "vupdate_datalist inum=" + inum + "     ichoice_max=" + ichoice_max );

        if(inum >= 0) {
            if(ichoice == (ichoice_max - 2)) {
                mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_CLOUD_PF_GET_SESSION);
            }
        }
    }

    private void vchoice_cal(int inum)
    {
        ichoice += inum ;

        if(ichoice >= ichoice_max) {
            ichoice = ichoice_max - 1;
        }

        if (ichoice < 0) {
            ichoice = 0;
        }

        vupdate_datalist(inum);

        Refresh_All_data();

    }

    /////////////////////////////////////////////////////////////////////
    private void vClickMain()
    {
        mMainActivity.mMainProcBike.performanceProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vBackToExercisePage()
    {
        mMainActivity.mMainProcBike.v_Goto_ExercisePage();
    }

    private void v_prev() {
        vchoice_cal(1);
    }

    private void v_next() {
        vchoice_cal(-1);
    }

    private void v_top_item(int isel) {
        switch (isel)
        {
            case 0:
                //mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_SESSIONE);
                break;
            case 1:
                mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_MONTHE);
                break;
            case 2:
                mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_YEARE);
                break;
            default:
                break;
        }
    }

    protected void v_delete_click() {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_DELETE, LanguageData.s_get_string(mContext,R.string.delete_item_description),-1);
        mMainActivity.dialogLayout_Delete.fillerTextView_Delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_CLOUD_PF_DEL_SESSION);
                mMainActivity.mMainProcBike.performanceProc.vSet_Del_WorkSeq(ichoice);
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackHome)                 { vClickMain(); }
            else if(v == imageView_ReturnExercisePage)  { vBackToExercisePage(); }
            else if(v == i_prev)                        { v_prev(); }
            else if(v == i_next)                        { v_next(); }
            else if(v == i_delete)                        { v_delete_click(); }
            else
            {
                int iLoop;
                for(iLoop = 0; iLoop < i_but; iLoop++)
                {
                    if(v == f_but[iLoop])
                    {
                        v_top_item(iLoop);
                        break;
                    }
                }
            }
        }
    }



}

