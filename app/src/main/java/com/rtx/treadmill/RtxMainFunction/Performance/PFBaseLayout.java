package com.rtx.treadmill.RtxMainFunction.Performance;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.retonix.circlecloud.Cloud_21_GET_EXC_REC;
import com.retonix.circlecloud.Cloud_26_GET_EW_YER;
import com.retonix.circlecloud.Cloud_34_GET_EW_MON;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

abstract public class PFBaseLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;

    public ButtonListener mButtonListener;
    private MainActivity        mMainActivity;

    public RtxImageView      i_prev;
    public RtxImageView      i_next;
    public RtxImageView      i_share;
    public RtxTextView       t_share;

    private RtxImageView[]            i_data;
    private RtxDoubleStringView[]     t_data;
    private RtxTextView[]             t_info;

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

    private int[] ishow_month_list = {
            Cloud_34_GET_EW_MON.Output.TTL_DST,
            Cloud_34_GET_EW_MON.Output.TTL_DRT,
            Cloud_34_GET_EW_MON.Output.TTL_CAL,
            Cloud_34_GET_EW_MON.Output.AVG_PAC,
            Cloud_34_GET_EW_MON.Output.AVG_HRT_RAT,
            Cloud_34_GET_EW_MON.Output.AVG_WAT
    };

    private int[] ishow_year_list = {
            Cloud_26_GET_EW_YER.Output.TTL_DST,
            Cloud_26_GET_EW_YER.Output.TTL_DRT,
            Cloud_26_GET_EW_YER.Output.TTL_CAL,
            Cloud_26_GET_EW_YER.Output.AVG_PAC,
            Cloud_26_GET_EW_YER.Output.AVG_HRT_RAT,
            Cloud_26_GET_EW_YER.Output.AVG_WAT
    };

    private int icolor_en = Common.Color.pf_word_white;
    private int icolor_dis = Common.Color.pf_word_gray;

    public PFBaseLayout(Context context, MainActivity mMainActivity) {
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
        init_CustomerView();    //Let user can override this.
        init_event();
        init_CustomerEvent();   //Let user can override this.
        add_View();
        add_CustomerView();    //Let user can override this.
    }

    public void onDestroy()
    {
        removeAllViews();
        System.gc();
    }

    public void init_View()
    {
        int iLoop ;

        init_BackPrePage();
        init_Title();

        if(i_prev == null)    { i_prev = new RtxImageView(mContext);     }
        if(i_next == null)    { i_next = new RtxImageView(mContext);     }
        if(i_share == null)    { i_share = new RtxImageView(mContext);     }
        if(t_share == null)    { t_share = new RtxTextView(mContext);     }

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


    }

    public void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        i_prev.setOnClickListener(mButtonListener);
        i_next.setOnClickListener(mButtonListener);
        i_share.setOnClickListener(mButtonListener);
        t_share.setOnClickListener(mButtonListener);
    }

    public void add_View() {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize, fsize_unit;
        String sdata;
        String sunit;
        int ipadding = 30;
        int ix_shift ;


//        menu
        sdata = LanguageData.s_get_string(mContext, R.string.my_performance);
        vSetTitleText(sdata.toUpperCase());

//      prev and next icon
        ix = 440;
        iy = 115;
        iw = 35;
        ih = 35;
        addRtxImage(null, i_prev, R.xml.pf_prev_icon, ix, iy, iw, ih, ipadding, null);

        ix += 360;
        addRtxImage(null, i_next, R.xml.pf_next_icon, ix, iy, iw, ih, ipadding, null);

        ix = 1132;
        iy = 280;
        iw = 35;
        ih = 35;
        addRtxImagePaddingViewToLayout(i_share, R.xml.pf_share_icon, ix, iy, iw, ih, ipadding);
        vRtxBaseLayoutE_SetShareView(i_share);

        ix = 1020;
        iy = 320;
        iw = 260;
        ih = 40;
        fsize = 20f;
        sdata = LanguageData.s_get_string(mContext, R.string.share);
        addRtxTextViewToLayout(t_share, sdata.toUpperCase(), fsize, Common.Color.pf_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        vRtxBaseLayoutE_SetShareView(t_share);

//      detail
        ix = 115;
        iy = 625;
        iw = 35;
        ih = 35;
        ix_shift = 205;
        ipadding = 0;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            addRtxImagePaddingViewToLayout(i_data[iLoop], istr_list[iLoop][0], ix, iy, iw, ih, ipadding);
            ix += ix_shift;
        }

        ix = 30;//By Alan
        iy = 670;
        iw = 200;//By Alan
        ih = 50;
        ix_shift = 205;
        fsize = 43.32f;
        fsize_unit = 22f;
        sdata = "";
        sunit = "";
        for(iLoop = 0; iLoop < imax; iLoop++) {
            addRtxDoubleStringView(t_data[iLoop], ix, iy, iw, ih);
            t_data[iLoop].setGap(20);
            t_data[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.pf_word_white, fsize, Common.Font.Relay_BlackItalic, Common.Color.pf_word_blue, fsize_unit);
            t_data[iLoop].setText(sdata.toUpperCase(), sunit.toLowerCase());
            ix += ix_shift;
        }

        ix = 30;//By Alan
        iy = 720;
        iw = 200;//By Alan
        ih = 50;
        ix_shift = 205;
        fsize = 20f;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            sdata = LanguageData.s_get_string(mContext, istr_list[iLoop][1]);
            addRtxTextViewToLayout(t_info[iLoop], sdata.toUpperCase(), fsize, Common.Color.pf_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

            ix += ix_shift;
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void v_set_title(int istr_id)
    {
        String sdata;

        sdata = LanguageData.s_get_string(mContext, R.string.my_performance) + " - " + LanguageData.s_get_string(mContext, istr_id);
        vSetTitleText(sdata.toUpperCase());
    }

    public void v_set_data(String[] sdata, String[] sunit)
    {
        int ilen_data, ilen_unit, iLoop ;

        if(sdata != null && sunit != null)
        {
            ilen_data = sdata.length;
            ilen_unit = sunit.length;
            if(ilen_data == imax && ilen_unit == imax)
            {
                for(iLoop = 0; iLoop < imax; iLoop++)
                {
                    t_data[iLoop].setText(sdata[iLoop], sunit[iLoop]);
                }
            }
        }
    }

    public void v_set_buttom_data(int imode, String[] sdata)
    {
        int[] ishow_list;
        switch (imode)
        {
            case 0:
                ishow_list = ishow_session_list;
                break;
            case 1:
                ishow_list = ishow_month_list;
                break;
            case 2:
                ishow_list = ishow_year_list;
                break;
            default:
                return;
        }

        int ishow_max = ishow_list.length;
        int idata_max;
        int iLoop;
        String stemp = "";
        String[] sval = new String[ishow_max];
        String[] sunit = new String[ishow_max];
        float fval;

        if (sdata == null) {
            i_share.setEnabled(false);
            t_share.setEnabled(false);
            t_share.setTextColor(icolor_dis);
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
            i_share.setEnabled(true);
            t_share.setEnabled(true);
            t_share.setTextColor(icolor_en);

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
        v_set_data(sval, sunit);

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    abstract protected void init_CustomerView();

    abstract protected void init_CustomerEvent();

    abstract protected void add_CustomerView();

    abstract protected void v_back();

    abstract protected void v_prev();

    abstract protected void v_next();

    abstract protected void v_share();

    abstract protected void v_icon_click(View v);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)
            {
                v_back();
            }
            else if(v == i_prev)
            {
                v_prev();
            }
            else if(v == i_next)
            {
                v_next();
            }
            else if(v == i_share || v == t_share)
            {
                v_share();
            }
            else
            {
                v_icon_click(v);
            }
        }
    }
}
