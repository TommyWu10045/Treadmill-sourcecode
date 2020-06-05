package com.rtx.treadmill.RtxMainFunctionBike.Performance;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.retonix.circlecloud.Cloud_33_GET_EW_DAY;
import com.retonix.circlecloud.Cloud_34_GET_EW_MON;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDrawView;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.Calendar;
import java.util.Set;
import java.util.TreeMap;


/**
 * Created by chasechang on 3/22/17.
 */

public class MonthLayout extends PFBaseLayout {
    private String TAG = "Jerry";

    private Context mContext;

    private MainActivity mMainActivity;

    private RtxTextView t_date;
    private RtxDrawView draw_view;
    private RtxImageView draw_image;
    private RtxFillerTextView[] mfiller;

    private int istr_list[] = {
            R.string.distance, R.string.duration, R.string.calories, R.string.pace, R.string.heart_rate, R.string.watt
    };

    private int iitem_list[] = {
            Cloud_33_GET_EW_DAY.Output.TTL_DST,
            Cloud_33_GET_EW_DAY.Output.TTL_DRT,
            Cloud_33_GET_EW_DAY.Output.TTL_CAL,
            Cloud_33_GET_EW_DAY.Output.AVG_PAC,
            Cloud_33_GET_EW_DAY.Output.AVG_HRT_RAT,
            Cloud_33_GET_EW_DAY.Output.AVG_WAT
    };

    private int imax = istr_list.length;

    private int i_item_choice = 0;
    private int ichoice = 0;
    private int ichoice_max;
    private int icolor_en = Common.Color.pf_word_white;
    private int icolor_dis = Common.Color.pf_word_gray;
    private TreeMap<String, String[]> monthtree = CloudDataStruct.CloudData_34.clound_cmd34_list;
    private TreeMap<String, String[]> daystree = CloudDataStruct.CloudData_33.clound_cmd33_list;

    private int ichoice_show = 0;
    String[] scurr_data = null;

    public MonthLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void init_CustomerView() {
        int iLoop;

        if (t_date == null) {
            t_date = new RtxTextView(mContext);
        }

        if (draw_image == null) {
            draw_image = new RtxImageView(mContext);
        }

        if (mfiller == null) {
            mfiller = new RtxFillerTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                mfiller[iLoop] = new RtxFillerTextView(mContext);
            }
        }

    }

    @Override
    protected void init_CustomerEvent() {
        int iLoop;

        for (iLoop = 0; iLoop < imax; iLoop++) {
            mfiller[iLoop].setOnClickListener(mButtonListener);
        }

    }

    @Override
    protected void add_CustomerView() {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize, fsize_unit;
        String sdata;
        String sunit;
        int ipadding = 30;
        int ix_shift = 195;

        //menu
        v_set_title(R.string.month);

        //add
        ix = 475;
        iy = 115;
        iw = 325;
        ih = 35;
        fsize = 20f;
        sdata = "";
        addRtxTextViewToLayout(t_date, sdata.toUpperCase(), fsize, Common.Color.pf_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 10;
        iy = 0;
        iw = 930;
        ih = 330;
        if (draw_view == null) {
            draw_view = new RtxDrawView(mContext, ix, iy, iw, ih);
        }
        addViewToLayout(draw_view, ix + 130, iy + 120, iw, ih + 60);

        ix = 220;
        iy = 190;
        iw = 820;
        ih = 270;
        addRtxImageViewToLayout(draw_image, R.drawable.pf_month_none, ix, iy, iw, ih);
        //draw_image.setBackgroundColor(Common.Color.yellow);

        ix = 65;
        iy = 510;
        iw = 160;//By Alan
        ih = 50;
        fsize = 20f;
        for (iLoop = 0; iLoop < imax; iLoop++) {
            sdata = LanguageData.s_get_string(mContext, istr_list[iLoop]).toUpperCase();
            addRtxTextViewToLayout(mfiller[iLoop], sdata, fsize, Common.Color.pf_word_white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
            mfiller[iLoop].setBackgroundColor(Common.Color.pf_word_blue);
            ix += ix_shift;
        }

        ichoice_show = 0;
        scurr_data = null;
        Refresh_All_data();

        ichoice = 0;
        if(Refresh_session_maxsize())
        {
            if(ichoice < ichoice_max)
            {
                Object skey = monthtree.keySet().toArray(new Object[monthtree.size()])[ichoice];
                scurr_data = monthtree.get(skey.toString());
            }
        }

        v_item_select(0);

    }

    private void v_item_select(int isel) {
        int iLoop;
        for (iLoop = 0; iLoop < imax; iLoop++) {
            if (iLoop != isel) {
                mfiller[iLoop].setMode(5);
                mfiller[iLoop].setTextColor(Common.Color.pf_word_blue);
            } else {
                mfiller[iLoop].setMode(4);
                mfiller[iLoop].setTextColor(Common.Color.pf_word_white);
            }

        }

        i_item_choice = isel;

        if(scurr_data != null) {
            Refresh_All_data();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void v_back() {
        mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_PERFORMANCE);
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
        int iLoop;
        for (iLoop = 0; iLoop < imax; iLoop++) {
            if (v == mfiller[iLoop]) {
                v_item_select(iLoop);
                break;
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void v_trans_show_data(String[] sdata) {

        String stemp = "";
        Calendar cCal;
        String syear = "";
        String smonth = "";
        int imonth;

        v_set_buttom_data(1, sdata);

        if (sdata == null) {
            t_date.setText(stemp);
        } else {
            cCal = Rtx_Calendar.cStr2Calendar(sdata[Cloud_34_GET_EW_MON.Output.YM], "yyyy-MM");
            syear = Rtx_Calendar.sCalendar2Str(cCal, "yyyy");
            imonth = cCal.get(Calendar.MONTH);

            if(imonth < Common.LONG_MONTH_OF_YEAR.length) {
                smonth = LanguageData.s_get_string(mContext, Common.LONG_MONTH_OF_YEAR[imonth]);
            }
            stemp = smonth + " " + syear;
            t_date.setText(stemp);
        }

        v_draw_show(sdata);

    }

    private void v_Refresh_draw(String stitle, int iday_max, int iitem) {
        int iLoop;
        Calendar cCal;
        int ih = 0;
        int im = 0;
        int is = 0;

        float fymax = 0;

        if (daystree == null) {
            return;
        }

        if (daystree.size() > 0) {
            String[] sx = new String[iday_max];
            float[] fy = new float[iday_max];
            String skey;
            String[] value;
            for (iLoop = 0; iLoop < iday_max; iLoop++) {
                skey = stitle + "-" + Rtx_TranslateValue.sInt2String(iLoop+1, 2);
                value = daystree.get(skey);

                if (iLoop == 0) {
                    sx[iLoop] = "1";
                } else if (iLoop == (iday_max - 1)) {
                    sx[iLoop] = Rtx_TranslateValue.sInt2String(iday_max);
                } else {
                    sx[iLoop] = ".";
                }
                if (value != null) {
                    if (iitem == 1 || iitem == 3) {
                        cCal = Rtx_Calendar.cStr2Calendar(value[iitem_list[iitem]], "HH:mm:ss");
                        if(cCal != null) {
                            ih = cCal.get(Calendar.HOUR);
                            im = cCal.get(Calendar.MINUTE);
                            is = cCal.get(Calendar.SECOND);
                        }
                        fy[iLoop] = ih * 3600 + im * 60 + is;
                    } else {
                        fy[iLoop] = Rtx_TranslateValue.fString2Float(value[iitem_list[iitem]], 0);
                    }

                    if(fymax < fy[iLoop])
                    {
                        fymax = fy[iLoop];
                    }
                } else {
                    fy[iLoop] = 0;
                }

                if (iitem == 1 || iitem == 3) {
                    //9為不會產生0:00的最小值
                    if (fymax < 9 * 60 ) {
                        draw_view.vRtxDrawView_setAxisMaxMin(9 * 60, 0);
                    } else {
                        draw_view.vRtxDrawView_setAxisMaxMin(0, 0);
                    }
                }
                else {
                    //5為分成五等分而不會產生四捨五入的顯示錯誤
                    if (fymax < 5) {
                        draw_view.vRtxDrawView_setAxisMaxMin(5, 0);
                    } else {
                        draw_view.vRtxDrawView_setAxisMaxMin(0, 0);
                    }
                }
            }

            draw_view.Create_Draw_Data(stitle, sx.length, sx, fy.length, fy);
        }
    }

    private void v_draw_show(String[] sdata) {
        Calendar cCal;
        int iyear;
        int imonth;
        int iday_max;

        if (sdata == null) {
            draw_view.setVisibility(INVISIBLE);
            draw_image.setVisibility(VISIBLE);
        } else {
            draw_view.setVisibility(VISIBLE);
            draw_image.setVisibility(INVISIBLE);
            cCal = Rtx_Calendar.cStr2Calendar(sdata[Cloud_34_GET_EW_MON.Output.YM], "yyyy-MM");
            iyear = cCal.get(Calendar.YEAR);
            imonth = cCal.get(Calendar.MONTH) + 1;
            iday_max = Rtx_Calendar.getDaysOfMonth(iyear, imonth);

            if (i_item_choice < imax)
            {
                if(i_item_choice == 1 || i_item_choice == 3)//PACE
                {
                    draw_view.RtxDrawView_PF_Month("", "H:mm");
                }
                else
                {
                    draw_view.RtxDrawView_PF_Month("", 0);
                }

                v_Refresh_draw(sdata[Cloud_34_GET_EW_MON.Output.YM], iday_max, i_item_choice);
                draw_view.set_Draw_mode(0);
            }
        }
    }

    private boolean Refresh_session_maxsize() {
        boolean bret = false;

        if (monthtree != null) {
            ichoice_max = monthtree.size();
            if (ichoice_max > 0) {
                bret = true;
            }
            else
            {
                ichoice_max = 0;
                ichoice = 0;
            }
        } else {
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

            if (ichoice_show == 0) {
                i_next.setEnabled(false);
            }

            if (ichoice == (ichoice_max - 1)) {
                i_prev.setEnabled(false);
            }
        }
    }

    private void Refresh_All_data()
    {
        v_trans_show_data(scurr_data);

        v_next_prev_check();
    }

    private void vupdate_datalist(int inum)
    {
        if(inum >= 0) {
            if(ichoice == (ichoice_max - 2)) {
                if(ichoice_max > 0) {
                    String skey = monthtree.lastKey();
                    String[] sarray = monthtree.get(skey);
                    int iyear;
                    Calendar cdate = null;
                    if (sarray != null) {
                        cdate = Rtx_Calendar.cStr2Calendar(sarray[Cloud_34_GET_EW_MON.Output.YM], "yyyy-MM");
                        cdate.add(Calendar.YEAR, -1);
                        iyear = cdate.get(Calendar.YEAR);
                        mMainActivity.mMainProcBike.performanceProc.vSet_Get_Exercise_Year(iyear);
                        mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_CLOUD_PF_GET_MONTH);
                    }
                }
            }
        }

    }

    private int iget_map_index(String skey)
    {
        int iret = ichoice;
        int icount = 0;

        Set<String> keys = monthtree.keySet();
        for(String key: keys){
            if(skey.compareTo(key) == 0) {
                break;
            }
            icount++;
        }

        if(icount != monthtree.size())
        {
            iret = icount;
        }

        return iret;
    }

    private void vchoice_cal(int inum)
    {
        String skey ;
        Calendar cdate ;
        String stemp = "";
        String syear = "";
        String smonth = "";
        int imonth;

        Refresh_session_maxsize();

        ichoice_show += inum;

        if(ichoice_show < 0 )
        {
            ichoice_show = 0;
        }
        else {
            if (monthtree.size() > 0) {
                skey = monthtree.firstKey();
                cdate = Rtx_Calendar.cStr2Calendar(skey,"yyyy-MM");
                cdate.add(Calendar.MONTH, -ichoice_show);
                skey = Rtx_Calendar.sCalendar2Str(cdate, "yyyy-MM");
                scurr_data = monthtree.get(skey);

                ichoice = iget_map_index(skey);

                if(ichoice >= ichoice_max) {
                    ichoice = ichoice_max - 1;
                }

                if (ichoice < 0) {
                    ichoice = 0;
                }

                Refresh_All_data();

                if(scurr_data == null)
                {
                    imonth = cdate.get(Calendar.MONTH);
                    syear = Rtx_Calendar.sCalendar2Str(cdate, "yyyy");

                    if(imonth < Common.LONG_MONTH_OF_YEAR.length) {
                        smonth = LanguageData.s_get_string(mContext, Common.LONG_MONTH_OF_YEAR[imonth]);
                    }
                    stemp = smonth + " " + syear;
                    t_date.setText(stemp);

                }
                else
                {
                    vupdate_datalist(inum);
                }
            }
        }

    }

}

