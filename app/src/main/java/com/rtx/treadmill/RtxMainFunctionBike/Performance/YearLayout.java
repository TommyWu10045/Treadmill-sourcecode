package com.rtx.treadmill.RtxMainFunctionBike.Performance;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.retonix.circlecloud.Cloud_26_GET_EW_YER;
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
import java.util.TreeMap;


/**
 * Created by chasechang on 3/22/17.
 */

public class YearLayout extends PFBaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     MainActivity        mMainActivity;

    private RtxTextView       t_date;
    private RtxDrawView draw_view;
    private RtxImageView draw_image;
    private RtxFillerTextView[] mfiller;

    private int istr_list[] = {
            R.string.distance  ,  R.string.duration  ,  R.string.calories  ,  R.string.pace  ,  R.string.heart_rate  ,  R.string.watt
    };

    private int iitem_list[] = {
            Cloud_34_GET_EW_MON.Output.TTL_DST,
            Cloud_34_GET_EW_MON.Output.TTL_DRT,
            Cloud_34_GET_EW_MON.Output.TTL_CAL,
            Cloud_34_GET_EW_MON.Output.AVG_PAC,
            Cloud_34_GET_EW_MON.Output.AVG_HRT_RAT,
            Cloud_34_GET_EW_MON.Output.AVG_WAT
    };

    private int imax = istr_list.length;

    private int i_item_choice = 0;
    private int ichoice = 0;
    private int ichoice_max;
    private int icolor_en = Common.Color.pf_word_white;
    private int icolor_dis = Common.Color.pf_word_gray;
    private TreeMap<String, String[]> yeartree = CloudDataStruct.CloudData_26.clound_cmd26_list;
    private TreeMap<String, String[]> monthtree = CloudDataStruct.CloudData_34.clound_cmd34_list;

    private int ichoice_show = 0;
    String[] scurr_data = null;

    public YearLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;
    }

    @Override
    protected void init_CustomerView()
    {
        int iLoop ;

        if(t_date == null)    { t_date = new RtxTextView(mContext);     }

        if (draw_image == null) {
            draw_image = new RtxImageView(mContext);
        }

        if(mfiller == null)
        {
            mfiller = new RtxFillerTextView[imax];
            for(iLoop = 0; iLoop < imax; iLoop++)
            {
                mfiller[iLoop] = new RtxFillerTextView(mContext);
            }
        }

    }

    @Override
    protected void init_CustomerEvent()
    {
        int iLoop ;

        for(iLoop = 0; iLoop < imax; iLoop++)
        {
            mfiller[iLoop].setOnClickListener(mButtonListener);
        }

    }

    @Override
    protected void add_CustomerView()
    {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize, fsize_unit;
        String sdata;
        String sunit;
        int ipadding = 30;
        int ix_shift = 195;

        //menu
        v_set_title(R.string.year);

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
        addRtxImageViewToLayout(draw_image, R.drawable.pf_year_none, ix, iy, iw, ih);

        ix = 65;
        iy = 510;
        iw = 160;//By Alan
        ih = 50;
        fsize = 20f;
        for(iLoop = 0; iLoop < imax; iLoop++)
        {
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
                Object skey = yeartree.keySet().toArray(new Object[yeartree.size()])[ichoice];
                scurr_data = yeartree.get(skey.toString());
            }
        }

        v_item_select(0);
    }

    private void v_item_select(int isel)
    {
        int iLoop;
        for(iLoop = 0; iLoop < imax; iLoop++)
        {
            if(iLoop != isel)
            {
                mfiller[iLoop].setMode(5);
                mfiller[iLoop].setTextColor(Common.Color.pf_word_blue);
            }
            else
            {
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
    protected void v_back()
    {
        mMainActivity.mMainProcBike.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_PERFORMANCE);
    }

    @Override
    protected void v_prev()
    {
        vchoice_cal(1);
    }

    @Override
    protected void v_next()
    {
        vchoice_cal(-1);
    }

    @Override
    protected void v_share()
    {
        mMainActivity.showInfoDialog(mMainActivity.DIALOG_TYPE_SHARE,mMainActivity,mMainActivity.frameLayout_Base);
    }

    @Override
    protected void v_icon_click(View v)
    {
        int iLoop;
        for(iLoop = 0; iLoop < imax; iLoop++)
        {
            if(v == mfiller[iLoop])
            {
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

        v_set_buttom_data(2, sdata);

        if (sdata == null) {
            t_date.setText(stemp);
        } else {
            cCal = Rtx_Calendar.cStr2Calendar(sdata[Cloud_26_GET_EW_YER.Output.YM], "yyyy");
            syear = Rtx_Calendar.sCalendar2Str(cCal, "yyyy");

            stemp = syear;
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

        if (monthtree == null) {
            return;
        }

        if (monthtree.size() > 0) {
            String[] sx = new String[iday_max];
            float[] fy = new float[iday_max];
            String skey;
            String[] value;
            for (iLoop = 0; iLoop < iday_max; iLoop++) {
                skey = stitle + "-" + Rtx_TranslateValue.sInt2String(iLoop+1, 2);
                value = monthtree.get(skey);

                sx[iLoop] = Common.MONTH_OF_YEAR[iLoop];
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
        if (sdata == null) {
            draw_view.setVisibility(INVISIBLE);
            draw_image.setVisibility(VISIBLE);
        } else {
            draw_view.setVisibility(VISIBLE);
            draw_image.setVisibility(INVISIBLE);
            if (i_item_choice < imax)
            {
                if(i_item_choice == 1 || i_item_choice == 3)//PACE
                {
                    draw_view.RtxDrawView_PF_Year("", "H:mm");
                }
                else
                {
                    draw_view.RtxDrawView_PF_Year("", 0);
                }

                v_Refresh_draw(sdata[Cloud_26_GET_EW_YER.Output.YM], 12, i_item_choice);
                draw_view.set_Draw_mode(0);
            }
        }
    }

    private boolean Refresh_session_maxsize() {
        boolean bret = false;

        if (yeartree != null) {
            ichoice_max = yeartree.size();
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
            if (yeartree.size() > 0) {
                skey = yeartree.firstKey();
                cdate = Rtx_Calendar.cStr2Calendar(skey,"yyyy");
                cdate.add(Calendar.YEAR, -ichoice_show);
                skey = Rtx_Calendar.sCalendar2Str(cdate, "yyyy");
                scurr_data = yeartree.get(skey);

                Refresh_All_data();

                if(scurr_data == null)
                {
                    t_date.setText(skey);
                }
            }
        }

    }

}

