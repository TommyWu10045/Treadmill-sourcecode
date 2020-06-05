package com.rtx.treadmill.RtxMainFunction.Performance;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.retonix.circlecloud.Cloud_21_GET_EXC_REC;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_Debug;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;


/**
 * Created by chasechang on 3/22/17.
 */

public class SessionLayout extends PFBaseLayout {
    private String TAG = "Jerry";

    private Context mContext;

    private MainActivity mMainActivity;

    private RtxTextView t_date;
    private RtxTextView t_time;
    private RtxTextView t_SportName;
    private RtxTextView t_SportManualName;
    private RtxImageView i_add;
    private RtxTextView t_add;
    private RtxImageView i_delete;
    private RtxTextView t_delete;
    private RtxImageView i_type_source; //EXC_MDL
    private RtxImageView i_type_dot;
    private RtxImageView i_type_destination;//MCH_TYPE

    private int ichoice = 0;
    private int ichoice_max;
    private ArrayList<String[]> sessionlist = CloudDataStruct.CloudData_21.clound_cmd21_list;
    private int icolor_en = Common.Color.pf_word_white;
    private int icolor_dis = Common.Color.pf_word_gray;

    public SessionLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void init_CustomerView() {
        if (t_date == null) {
            t_date = new RtxTextView(mContext);
        }
        if (t_time == null) {
            t_time = new RtxTextView(mContext);
        }
        if (t_SportName == null){
            t_SportName = new RtxTextView(mContext);
        }
        if (t_SportManualName == null){
            t_SportManualName = new RtxTextView(mContext);
        }
        if (i_add == null) {
            i_add = new RtxImageView(mContext);
        }
        if (t_add == null) {
            t_add = new RtxTextView(mContext);
        }
        if (i_delete == null) {
            i_delete = new RtxImageView(mContext);
        }
        if (t_delete == null) {
            t_delete = new RtxTextView(mContext);
        }
        if (i_type_source == null) {
            i_type_source = new RtxImageView(mContext);
        }
        if (i_type_dot == null) {
            i_type_dot = new RtxImageView(mContext);
        }
        if (i_type_destination == null) {
            i_type_destination = new RtxImageView(mContext);
        }

    }

    @Override
    protected void init_CustomerEvent() {
        i_add.setOnClickListener(mButtonListener);
        t_add.setOnClickListener(mButtonListener);
        i_delete.setOnClickListener(mButtonListener);
        t_delete.setOnClickListener(mButtonListener);
    }

    @Override
    protected void add_CustomerView() {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize, fsize_unit;
        String sdata;
        String sunit;
        int ipadding = 30;
        int ix_shift;

        //menu
        v_set_title(R.string.session1);

        //add
        ix = 475;
        iy = 115;
        iw = 325;
        ih = 35;
        fsize = 20f;
        sdata = "";
        addRtxTextViewToLayout(t_date, sdata.toUpperCase(), fsize, Common.Color.pf_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        sdata = "";
        addRtxTextViewToLayout(t_time, sdata.toUpperCase(), fsize, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);


        addRtxTextViewToLayout(t_SportName, "", 30, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 550, 450, 200, 50);

        addRtxTextViewToLayout(t_SportManualName, "", 30, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, 550, 500, 200, 50);

        //add
        ix = 160;
        iy = 330;
        iw = 35;
        ih = 35;
        addRtxImage(null, i_add, R.xml.pf_add_session, ix, iy, iw, ih, ipadding, null);

        ix = 140 - 38;
        iy = 370;
        iw = 75 + 76;
        ih = 40;
        fsize = 20f;
        sdata = LanguageData.s_get_string(mContext, R.string.add);
        addRtxTextViewToLayout(t_add, sdata.toUpperCase(), fsize, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        //delete
        ix = 1132;
        if(Rtx_Debug.bRtxDebug_GetShareEnable())
        {
            iy = 385;
        }
        else
        {
            iy = 330;
        }
        iw = 35;
        ih = 35;
        addRtxImagePaddingViewToLayout(i_delete, R.xml.pf_delete_icon, ix, iy, iw, ih, ipadding);

        ix = 1020;
        if(Rtx_Debug.bRtxDebug_GetShareEnable())
        {
            iy = 425;
        }
        else
        {
            iy = 370;
        }
        iw = 260;
        ih = 40;
        fsize = 20f;
        sdata = LanguageData.s_get_string(mContext, R.string.delete);
        addRtxTextViewToLayout(t_delete, sdata.toUpperCase(), fsize, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        //source icon
        ix = 380;
        iy = 300;
        iw = 140;
        ih = 140;
        ipadding = 0;
        addRtxImage(null, i_type_source, R.drawable.pf_destination_null, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);

        //dot icon
        ix = 530;
        iy = 300;
        iw = 230;
        ih = 140;
        addRtxImagePaddingViewToLayout(i_type_dot, R.drawable.pf_dot_null, ix, iy, iw, ih, ipadding);

        //destination icon
        ix = 770;
        iy = 300;
        iw = 140;
        ih = 140;
        addRtxImage(null, i_type_destination, R.drawable.pf_source_null, ix, iy, iw, ih, ipadding, ImageView.ScaleType.FIT_XY);

        ichoice = 0;
        Refresh_All_data();

        if(CloudDataStruct.CloudData_20.is_log_in())
        {
            i_add.setEnabled(true);
            t_add.setTextColor(icolor_en);
            t_add.setEnabled(true);
        }
        else
        {
            i_add.setEnabled(false);
            t_add.setTextColor(icolor_dis);
            t_add.setEnabled(false);
        }
    }

    @Override
    protected void v_back() {
        mMainActivity.mMainProcTreadmill.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_PERFORMANCE);
    }

    @Override
    protected void v_prev() {
        vchoice_cal(1);
    }

    @Override
    protected void v_next() {
        vchoice_cal(-1);
    }

    @Override
    protected void v_share() {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_SHARE,mMainActivity,mMainActivity.frameLayout_Base);
    }

    @Override
    protected void v_icon_click(View v) {
        if (v == i_add || v == t_add) {
            mMainActivity.mMainProcTreadmill.performanceProc.vSetNextState(PerformanceState.PROC_ADD_TYPE_SOURCE);
        } else if (v == i_delete || v == t_delete) {
            mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_DELETE, LanguageData.s_get_string(mContext,R.string.delete_item_description),-1);
            mMainActivity.dialogLayout_Delete.fillerTextView_Delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                mMainActivity.mMainProcTreadmill.performanceProc.vSetNextState(PerformanceState.PROC_CLOUD_PF_DEL_SESSION);
                mMainActivity.mMainProcTreadmill.performanceProc.vSet_Del_WorkSeq(ichoice);
                }
            });
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
            i_delete.setEnabled(true);
            t_delete.setEnabled(true);
            t_delete.setTextColor(icolor_en);

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
            t_SportName.setText(stemp);
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



            idot = R.drawable.pf_dot_icon;
        }

        i_type_source.setImageResource(ides);
        i_type_dot.setImageResource(idot);
        i_type_destination.setImageResource(isource);

    }

    private boolean Refresh_session_maxsize()
    {
        boolean bret = false;

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

        if(inum >= 0) {
            if(ichoice == (ichoice_max - 2)) {
                mMainActivity.mMainProcTreadmill.performanceProc.vSetNextState(PerformanceState.PROC_CLOUD_PF_GET_SESSION);
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

}

