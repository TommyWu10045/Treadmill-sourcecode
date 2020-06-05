package com.rtx.treadmill.RtxMainFunction.Performance;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.retonix.circlecloud.Cloud_33_GET_EW_DAY;
import com.retonix.circlecloud.Cloud_34_GET_EW_MON;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayoutE;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
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

public class MonthLayoutE extends Rtx_BaseLayoutE {
    private String TAG = "Jerry";

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity mMainActivity;

    private RtxFillerTextView[] f_but;

    private FrameLayout      f_back;
    public RtxImageView      i_prev;
    private RtxTextView      t_date;
    public RtxImageView      i_next;

    public RtxImageView      i_share;
    public RtxTextView       t_share;

    private RtxDrawView draw_view;
    private RtxImageView draw_image;
    private RtxFillerTextView f_show_total;
    private RtxFillerTextView[] mfiller;

    private FrameLayout f_bot_info;
    private RtxImageView[]            i_data;
    private RtxDoubleStringView[]     t_data;
    private RtxTextView[]             t_info;
    private RtxTextView     t_close;
    private RtxImageView     i_close;

    private int istr_item[] = {
            R.string.session1  ,  R.string.month  ,  R.string.year
    };
    private int i_but = istr_item.length;

    private int istr_item_list[][] = {
            {R.string.distance   , Cloud_33_GET_EW_DAY.Output.TTL_DST      },
            {R.string.duration   , Cloud_33_GET_EW_DAY.Output.TTL_DRT      },
            {R.string.calories   , Cloud_33_GET_EW_DAY.Output.TTL_CAL      },
            {R.string.pace       , Cloud_33_GET_EW_DAY.Output.AVG_PAC      },
            {R.string.heart_rate , Cloud_33_GET_EW_DAY.Output.AVG_HRT_RAT  },
            {R.string.watt       , Cloud_33_GET_EW_DAY.Output.AVG_WAT      },
    };
    private int imax_item = istr_item_list.length;

    private int istr_list[][] = {
            {R.drawable.pf_distance_icon  ,  R.string.distance       , Cloud_34_GET_EW_MON.Output.TTL_DST        },
            {R.drawable.pf_duration_icon  ,  R.string.duration       , Cloud_34_GET_EW_MON.Output.TTL_DRT        },
            {R.drawable.pf_calories_icon  ,  R.string.calories       , Cloud_34_GET_EW_MON.Output.TTL_CAL        },
            {R.drawable.pf_pace_icon      ,  R.string.avg_pace       , Cloud_34_GET_EW_MON.Output.AVG_PAC        },
            {R.drawable.pf_hr_icon        ,  R.string.avg_heart_rate , Cloud_34_GET_EW_MON.Output.AVG_HRT_RAT    },
    };
    private int imax_data = istr_list.length;

    private int i_item_choice = 0;
    private int ichoice = 0;
    private int ichoice_max;
    private int icolor_en = Common.Color.pf_word_white;
    private int icolor_dis = Common.Color.pf_word_gray;
    private TreeMap<String, String[]> monthtree = CloudDataStruct.CloudData_34.clound_cmd34_list;
    private TreeMap<String, String[]> daystree = CloudDataStruct.CloudData_33.clound_cmd33_list;

    private int ichoice_show = 0;
    String[] scurr_data = null;

    public MonthLayoutE(Context context, MainActivity mMainActivity) {
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

    public void init_View() {
        int iLoop;

        init_BackHome();
        init_BackExercise();

        if (f_but == null) {
            f_but = new RtxFillerTextView[i_but];
            for (iLoop = 0; iLoop < i_but; iLoop++) {
                f_but[iLoop] = new RtxFillerTextView(mContext);
            }
        }

        if(f_back == null)    { f_back = new FrameLayout(mContext);     }
        f_back.removeAllViews();
        if(i_prev == null)    { i_prev = new RtxImageView(mContext);     }
        if (t_date == null)   { t_date = new RtxTextView(mContext);     }
        if(i_next == null)    { i_next = new RtxImageView(mContext);     }

        if(i_share == null)    { i_share = new RtxImageView(mContext);     }
        if(t_share == null)    { t_share = new RtxTextView(mContext);     }

        if (draw_image == null) {
            draw_image = new RtxImageView(mContext);
        }

        f_show_total = new RtxFillerTextView(mContext);

        if (mfiller == null) {
            mfiller = new RtxFillerTextView[imax_item];
            for (iLoop = 0; iLoop < imax_item; iLoop++) {
                mfiller[iLoop] = new RtxFillerTextView(mContext);
            }
        }

        if(f_bot_info == null)    { f_bot_info = new FrameLayout(mContext);     }
        f_bot_info.removeAllViews();
        if(i_data == null) {
            i_data = new RtxImageView[imax_data];
            for (iLoop = 0; iLoop < imax_data; iLoop++) {
                i_data[iLoop] = new RtxImageView(mContext);
            }
        }

        if(t_data == null) {
            t_data = new RtxDoubleStringView[imax_data];
            for (iLoop = 0; iLoop < imax_data; iLoop++) {
                t_data[iLoop] = new RtxDoubleStringView(mContext);
            }
        }

        if(t_info == null) {
            t_info = new RtxTextView[imax_data];
            for (iLoop = 0; iLoop < imax_data; iLoop++) {
                t_info[iLoop] = new RtxTextView(mContext);
            }
        }

        if(t_close == null) {t_close = new RtxTextView(mContext);}
        if(i_close == null) {i_close = new RtxImageView(mContext);}

    }

    public void init_event()
    {
        int iLoop;

        for (iLoop = 0; iLoop < i_but; iLoop++) {
            f_but[iLoop].setOnClickListener(mButtonListener);
        }

        imageView_BackHome.setOnClickListener(mButtonListener);
        imageView_ReturnExercisePage.setOnClickListener(mButtonListener);
        i_prev.setOnClickListener(mButtonListener);
        i_next.setOnClickListener(mButtonListener);

        f_show_total.setOnClickListener(mButtonListener);
        for (iLoop = 0; iLoop < imax_item; iLoop++) {
            mfiller[iLoop].setOnClickListener(mButtonListener);
        }

        i_close.setOnClickListener(mButtonListener);

    }

    public void add_View() {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize, fsize_unit;
        String sdata;
        String sunit;
        int ipadding = 30;
        int ix_shift = 195;

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
            if(iLoop != 1)
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

//        frame_back
        ix = 0;
        iy = 100;
        iw = 1280;
        ih = 800;
        addRtxViewToLayout(f_back, ix, iy, iw, ih, Common.Color.bd_exercise_back);

//      prev and next icon
        ix = 460;
        iy = 60;
        iw = 35;
        ih = 35;
        addRtxImage(f_back, i_prev, R.xml.pf_prev_icon, ix, iy, iw, ih, ipadding, null);

        ix += 360;
        addRtxImage(f_back, i_next, R.xml.pf_next_icon, ix, iy, iw, ih, ipadding, null);

        ix = 495;
        iy = 60;
        iw = 325;
        ih = 35;
        fsize = 20f;
        sdata = "";
        addRtxTextView(f_back, t_date, sdata.toUpperCase(), fsize, Common.Color.pf_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 1140;
        iy = 200;
        iw = 35;
        ih = 35;
        addRtxImage(f_back, i_share, R.xml.pf_share_icon, ix, iy, iw, ih, ipadding, null);
        vRtxBaseLayoutE_SetShareView(i_share);

        ix = 1020;
        iy = 240;
        iw = 260;
        ih = 40;
        fsize = 20f;
        sdata = LanguageData.s_get_string(mContext, R.string.share);
        addRtxTextView(f_back, t_share, sdata.toUpperCase(), fsize, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        vRtxBaseLayoutE_SetShareView(t_share);

        ix = 10;
        iy = 0;
        iw = 930;
        ih = 330;
        if (draw_view == null) {
            draw_view = new RtxDrawView(mContext, ix, iy, iw, ih);
        }
        addViewToLayout(f_back, draw_view, ix + 130, iy + 20, iw, ih + 60);

        ix = 220;
        iy = 90;
        iw = 820;
        ih = 270;
        addRtxImage(f_back, draw_image, R.drawable.pf_month_none, ix, iy, iw, ih, 0, null);
        //draw_image.setBackgroundColor(Common.Color.yellow);

        ix = 460;
        iy = 400;
        iw = 360;//By Alan
        ih = 50;
        fsize = 20f;
        sdata = LanguageData.s_get_string(mContext, R.string.show_history_data).toUpperCase();
        addRtxTextView(f_back, f_show_total, sdata, fsize, Common.Color.pf_word_yellow, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        f_show_total.setBackgroundColor(Common.Color.pf_word_yellow);
        f_show_total.setMode(5);

        ix = 65;
        iy = 470;
        iw = 165;//By Alan
        ih = 50;
        fsize = 20f;
        for (iLoop = 0; iLoop < imax_item; iLoop++) {
            sdata = LanguageData.s_get_string(mContext, istr_item_list[iLoop][0]).toUpperCase();
            addRtxTextView(f_back, mfiller[iLoop], sdata, fsize, Common.Color.pf_word_white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
            mfiller[iLoop].setBackgroundColor(Common.Color.pf_word_blue);
            ix += ix_shift;
        }

//        frame_bot_info
        ix = 0;
        iy = 480;
        iw = 1280;
        ih = 160;
        addRtxViewToLayout(f_bot_info, ix, iy, iw, ih, Common.Color.background);

//      detail
        ix = 115;
        iy = 15;
        iw = 35;
        ih = 35;
        ix_shift = 205;
        for(iLoop = 0; iLoop < imax_data; iLoop++) {
            addRtxImage(f_bot_info, i_data[iLoop], istr_list[iLoop][0], ix, iy, iw, ih, 0, null);
            ix += ix_shift;
        }

        ix = 0;
        iy = 60;
        iw = 260;
        ih = 50;
        ix_shift = 205;
        fsize = 43.32f;
        fsize_unit = 22f;
        sdata = "";
        sunit = "";
        for(iLoop = 0; iLoop < imax_data; iLoop++) {
            addRtxDoubleStringView(f_bot_info, t_data[iLoop], ix, iy, iw, ih);
            t_data[iLoop].setGap(20);
            t_data[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.pf_word_white, fsize, Common.Font.Relay_BlackItalic, Common.Color.pf_word_blue, fsize_unit);
            t_data[iLoop].setText(sdata.toUpperCase(), sunit.toLowerCase());
            ix += ix_shift;
        }

        ix = 30;//By Alan
        iy = 110;
        iw = 200;//By Alan
        ih = 50;
        ix_shift = 205;
        fsize = 20f;
        for(iLoop = 0; iLoop < imax_data; iLoop++) {
            sdata = LanguageData.s_get_string(mContext, istr_list[iLoop][1]);
            addRtxTextView(f_bot_info, t_info[iLoop], sdata.toUpperCase(), fsize, Common.Color.pf_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

            ix += ix_shift;
        }

        ix = 1100;
        iy = 50;
        iw = 80;
        ih = 40;
        fsize = 12f;
        sdata = LanguageData.s_get_string(mContext, R.string.close);
        addRtxTextView(f_bot_info, t_close, sdata.toUpperCase(), fsize, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix += iw;
        iw = 40;
        ih = 40;
        addRtxImage(f_bot_info, i_close, R.drawable.close_yellow, ix, iy, iw, ih, ipadding, null);

        f_bot_info.setVisibility(INVISIBLE);

//        init all view
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

        i_share.setEnabled(false);
        t_share.setTextColor(icolor_dis);

        v_item_select(0);

    }

    private void v_item_select(int isel) {
        int iLoop;
        for (iLoop = 0; iLoop < imax_item; iLoop++) {
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

    private void v_set_data(String[] sdata, String[] sunit)
    {
        int ilen_data, ilen_unit, iLoop ;

        if(sdata != null && sunit != null)
        {
            ilen_data = sdata.length;
            ilen_unit = sunit.length;
            if(ilen_data == imax_data && ilen_unit == imax_data)
            {
                for(iLoop = 0; iLoop < imax_data; iLoop++)
                {
                    t_data[iLoop].setText(sdata[iLoop], sunit[iLoop]);
                }
            }
        }
    }

    private void v_set_buttom_data(int imode, String[] sdata)
    {
        int[][] ishow_list;

        ishow_list = istr_list;

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
                if (ishow_list[iLoop][2] < idata_max) {
                    if (iLoop == 0) {
                        stemp = EngSetting.Distance.getValString(sdata[ishow_list[iLoop][2]]);
                    } else if (iLoop == 1) {
                        fval = Rtx_Calendar.cStr2Sec(sdata[ishow_list[iLoop][2]], "HH:mm:ss");
                        stemp = Rtx_Calendar.s_trans_integer(5, (int) fval);
                    } else if (iLoop == 2) {
                        fval = Rtx_TranslateValue.fString2Float(sdata[ishow_list[iLoop][2]]);
                        stemp = Rtx_TranslateValue.sFloat2String((int) fval, 0);
                    } else if (iLoop == 3) {
                        fval = Rtx_Calendar.cStr2Sec(sdata[ishow_list[iLoop][2]], "HH:mm:ss");
                        stemp = Rtx_Calendar.s_trans_integer(5, (int) fval);
                    } else if (iLoop == 4) {
                        fval = Rtx_TranslateValue.fString2Float(sdata[ishow_list[iLoop][2]]);
                        stemp = Rtx_TranslateValue.sFloat2String((int) fval, 0);
                    } else if (iLoop == 5) {
                        fval = Rtx_TranslateValue.fString2Float(sdata[ishow_list[iLoop][2]]);
                        stemp = Rtx_TranslateValue.sFloat2String((int) fval, 0);
                    }
                    sval[iLoop] = stemp;
                    sunit[iLoop] = "";
                }
            }
        }
        v_set_data(sval, sunit);

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
                        cCal = Rtx_Calendar.cStr2Calendar(value[istr_item_list[iitem][1]], "HH:mm:ss");
                        if(cCal != null) {
                            ih = cCal.get(Calendar.HOUR);
                            im = cCal.get(Calendar.MINUTE);
                            is = cCal.get(Calendar.SECOND);
                        }
                        fy[iLoop] = ih * 3600 + im * 60 + is;
                    } else {
                        fy[iLoop] = Rtx_TranslateValue.fString2Float(value[istr_item_list[iitem][1]], 0);
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

            if (i_item_choice < imax_item)
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
                        mMainActivity.mMainProcTreadmill.performanceProc.vSet_Get_Exercise_Year(iyear);
                        mMainActivity.mMainProcTreadmill.performanceProc.vSetNextState(PerformanceState.PROC_CLOUD_PF_GET_MONTH);
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

    /////////////////////////////////////////////////////////////////////
    private void vClickMain()
    {
        mMainActivity.mMainProcTreadmill.performanceProc.vMainChangePage(MainState.PROC_HOME);
    }

    private void vBackToExercisePage()
    {
        mMainActivity.mMainProcTreadmill.v_Goto_ExercisePage();
    }

    private void v_prev() {
        vchoice_cal(1);
    }

    private void v_next() {
        vchoice_cal(-1);
    }

    private void v_show_total() {
        f_bot_info.setVisibility(VISIBLE);
    }

    private void v_hide_total() {
        f_bot_info.setVisibility(INVISIBLE);
    }

    private void v_top_item(int isel) {
        switch (isel)
        {
            case 0:
                mMainActivity.mMainProcTreadmill.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_SESSIONE);
                break;
            case 1:
                //mMainActivity.mMainProcTreadmill.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_MONTHE);
                break;
            case 2:
                mMainActivity.mMainProcTreadmill.performanceProc.vSetNextState(PerformanceState.PROC_SHOW_YEARE);
                break;
            default:
                break;
        }
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
            else if(v == f_show_total)                  { v_show_total(); }
            else if(v == i_close)                        { v_hide_total(); }
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

                for(iLoop = 0; iLoop < imax_item; iLoop++)
                {
                    if (v == mfiller[iLoop]) {
                        v_item_select(iLoop);
                        break;
                    }
                }
            }
        }
    }

}

