package com.rtx.treadmill.RtxMainFunctionBike.BodyManager;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.retonix.circlecloud.Cloud_05_DB_BDY_IDX_REC;
import com.rtx.treadmill.GlobalData.CloudDataStruct;
import com.rtx.treadmill.GlobalData.CloudDataStruct.HistoryData;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.GlobalData.infolist;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.TextJustification;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxDrawView;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxScrollView;
import com.rtx.treadmill.RtxView.RtxTextView;
import com.rtx.treadmill.RtxView.RtxView_RecPercent_Vertical;

import java.util.ArrayList;

/**
 * Created by chasechang on 3/22/17.
 */

public class AllViewLayout extends Rtx_BaseLayout {
    private static String TAG = "Jerry=";
    private final static boolean DEBUG = false;

    private Context mContext;

    private ButtonListener      mButtonListener;
    private MainActivity        mMainActivity;

    private RtxDrawView draw_view;
    private RtxImageView i_none_data;
    private RtxScrollView s_info;
    private RtxTextView t_info_subtitle;
    private RtxTextView t_info;

    private RtxView_RecPercent_Vertical mRecPercent_Vertical;
    private RtxDoubleStringView t_data;

    private RtxImageView i_input;
    private RtxTextView t_input;

    private RtxImageView i_history;
    private RtxTextView t_history;

    private RtxTextView t_healthy_bonemass;

    ArrayList<HistoryData> historypoints = CloudDataStruct.BodyIndexData_14.historypoints_list;

    //private ArrayList<DrawData> mImageData = null ;
    private int imode;
    private boolean blogin = false;

    private int i_gap_null = 40;
    private int i_gap_data = 20;

    public AllViewLayout(Context context, MainActivity mMainActivity) {
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
        init_Title();
        init_BackPrePage();

        if(i_none_data == null)           {i_none_data = new RtxImageView(mContext);}
        if(mRecPercent_Vertical == null)       { mRecPercent_Vertical = new RtxView_RecPercent_Vertical(mContext);   }
        if(t_data == null)           {t_data = new RtxDoubleStringView(mContext);}

        if(s_info == null)           {s_info = new RtxScrollView(mContext);}
        if(t_info_subtitle == null)           {t_info_subtitle = new RtxTextView(mContext);}
        if(t_info == null)           {t_info = new RtxTextView(mContext);}

        if(i_input == null)           {i_input = new RtxImageView(mContext);}
        if(t_input == null)           {t_input = new RtxTextView(mContext);}
        if(i_history == null)           {i_history = new RtxImageView(mContext);}
        if(t_history == null)           {t_history = new RtxTextView(mContext);}

        if(t_healthy_bonemass == null)           {t_healthy_bonemass = new RtxTextView(mContext);}

    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        i_input.setOnClickListener(mButtonListener);
        i_history.setOnClickListener(mButtonListener);
    }

    private void add_View() {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize;
        int ix_temp, iy_temp, iw_temp, ih_temp;
        float fsize_temp;
        String sdata;
        float fsize_unit;

        //        menu
        sdata = BodyManagerFunc.s_get_bodymanage_title(mContext, imode);
        vSetTitleText(sdata.toUpperCase());

        add_draw_view();

        add_vertical_data();

        ix = 100;
        iy = 550;
        iw = 690;
        ih = 230;
        fsize = 27.95f;
        addViewToLayout(s_info, ix, iy, iw, ih);
        s_info.addRtxTextView(t_info_subtitle, fsize, Common.Color.yellow, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.TOP|Gravity.LEFT);
        s_info.addRtxTextView(t_info, fsize, Common.Color.white, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.TOP|Gravity.LEFT);

        t_info_subtitle.setText(BodyManagerFunc.s_get_bodymanage_info_subtitle(mContext, imode));
        t_info.setText(BodyManagerFunc.s_get_bodymanage_info(mContext, imode));
        infolist.v_set_info(t_info, BodyManagerFunc.s_get_bodymanage_infolist(mContext, imode));
        //分散對齊,unmask below
        //v_leftright_alignment(t_info_subtitle, iw);
        //v_leftright_alignment(t_info, iw);

        //v_input_show();

        v_history_show();

        v_check_click_enable();

    }

    private void v_leftright_alignment(RtxTextView tview, int iwidth)
    {
        TextJustification.justify(tview, iwidth);
    }

    public void v_setSelect(boolean bval, int isel)
    {
        blogin = bval;
        imode = isel;
    }

    private boolean b_draw_view_is_null()
    {
        boolean bret = false;
        int imch_type ;
        int iLoop;
        int istr_list[];

        if(historypoints.size() <= 0 || !blogin)
        {
            bret = true;
        }
        else {
            imch_type = BodyManagerFunc.iget_body_machine_type();

            istr_list = mMainActivity.mMainProcBike.bodymanagerProc.ipage_list[1];

            if (imch_type == 0)//Circle
            {
                for (iLoop = 0; iLoop < istr_list.length; iLoop++) {
                    if(imode == istr_list[iLoop])
                    {
                        bret = true;
                        break;
                    }
                }
            }
            else //etc.
            {
            }
        }

        return bret;
    }

    private void add_draw_view()
    {
        int ix, iy, iw, ih;
        String sdata;

        i_none_data.setVisibility(INVISIBLE);
        if(draw_view != null)
        {
            draw_view.setVisibility(INVISIBLE);
        }

        if(b_draw_view_is_null())
        {
            ix = 60;
            iy = 230;
            iw = 750;
            ih = 260;
            addRtxImageViewToLayout(i_none_data, R.drawable.bd_allview_none, ix, iy, iw, ih);
            i_none_data.setVisibility(VISIBLE);
        }
        else
        {
            ix = 0;
            iy = 80;
            iw = 810;
            ih = 350;
            if(draw_view == null) {
                draw_view = new RtxDrawView(mContext, ix, iy, iw, ih);
            }
            addViewToLayout(draw_view, ix, iy, iw, ih+100);

            sdata = BodyManagerFunc.s_get_bodymanage_unit(mContext, imode);

            draw_view.RtxDrawView_BD(sdata, BodyManagerFunc.i_get_bodymanage_point(mContext, imode, 1));
            int imark_digit = 0x1000 + BodyManagerFunc.i_get_bodymanage_point(mContext, imode, 0);
            draw_view.set_mark_mode(imark_digit);

            v_Refresh_draw(sdata);
            draw_view.set_Draw_mode(1);

            draw_view.setVisibility(VISIBLE);
        }

    }

    private void add_vertical_bar()
    {
        int ix, iy, iw, ih;

        ix = 900;
        iy = 165;
        iw = 400;
        ih = 350;
        mRecPercent_Vertical.setRec_WH(35, ih - 70);
        mRecPercent_Vertical.set_Rec_Shiftx(130);
        mRecPercent_Vertical.set_Rec_Shifty(35);
        addViewToLayout(mRecPercent_Vertical, ix, iy, iw, ih);

        v_set_healthy();

    }

    private void add_vertical_val()
    {
        int ix, iy, iw, ih;
        float fsize;
        float fsize_unit;
        String sdata;
        int igap, idata_color;
        float fval;

        ix = 850;
        iy = 300;
        iw = 400;
        ih = 100;
        fsize = 80f;
        fsize_unit = 33.33f;
        addRtxDoubleStringView(t_data, ix, iy, iw, ih);

        sdata = BodyManagerFunc.s_get_drawdata(mContext, imode);
        if(sdata.compareTo("") == 0)
        {
            igap = i_gap_null;
            idata_color = Common.Color.bd_word_gray;
        }
        else
        {
            igap = i_gap_data;
            idata_color = Common.Color.bd_word_green;
        }

        t_data.setGap(igap);
        t_data.setPaint(Common.Font.Relay_Black, idata_color, fsize, Common.Font.Relay_BlackItalic, idata_color, fsize_unit);
        t_data.setText(sdata, BodyManagerFunc.s_get_bodymanage_unit(mContext, imode));

        if(imode == Cloud_05_DB_BDY_IDX_REC.Input.fBON_MAS)
        {
            fsize = 18.0f;
            iy += ih;
            ih = 30;
            sdata = LanguageData.s_get_string(mContext, R.string.your_healthy_bone_mass) + " " + BodyManagerFunc.s_healthy_bone_mass(mContext, imode);
            addRtxTextViewToLayout(t_healthy_bonemass, sdata, fsize, Common.Color.bd_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        }
        else if(imode == Cloud_05_DB_BDY_IDX_REC.Input.fWeight)
        {
            fsize = 18.0f;
            iy += ih;
            ih = 30;
            fval = BodyManagerFunc.f_ideal_weight(mContext, imode);
            sdata = LanguageData.s_get_string(mContext, R.string.your_ideal_weight) + " "
                    + EngSetting.Weight.getValString(fval/1.1f) + " ~ " + EngSetting.Weight.getValString(fval*1.1f)
                    + EngSetting.Weight.getUnitString(mContext);
            addRtxTextViewToLayout(t_healthy_bonemass, sdata, fsize, Common.Color.bd_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        }
        else if(imode == Cloud_05_DB_BDY_IDX_REC.Input.fWIT_HI_RAO)
        {
            fsize = 18.0f;
            iy += ih;
            ih = 45;
            sdata = LanguageData.s_get_string(mContext, R.string.your_health_waist_hip_ratio);
            addRtxTextViewToLayout(t_healthy_bonemass, sdata, fsize, Common.Color.bd_word_blue, Common.Font.Relay_Bold, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        }


//        mImageData = new ArrayList<DrawData>();
//        mImageData.add(new DrawData(
//                t_data  ,
//                BodyManagerFunc.s_get_bodymanage_data(mContext, imode) ,
//                BodyManagerFunc.s_get_bodymanage_unit(mContext, imode) ,
//                new ArrayList<Object>(Arrays.asList(
//                        ix, iy, iw, ih, igap
//                        ,Common.Font.Relay_Black, Common.Color.bd_word_green, fsize
//                        ,Common.Font.Relay_Black, Common.Color.bd_word_green, fsize_unit
//                        ))
//        )) ;

    }

    private void add_vertical_data()
    {
        int itype;

        if(b_draw_view_is_null())return;

        itype = BodyManagerFunc.i_get_bodymanage_vertical_type(mContext, imode);

        mRecPercent_Vertical.setVisibility(INVISIBLE);
        t_data.setVisibility(INVISIBLE);
        t_healthy_bonemass.setVisibility(INVISIBLE);
        if(itype == 1)
        {
            t_data.setVisibility(VISIBLE);
            t_healthy_bonemass.setVisibility(VISIBLE);
            add_vertical_val();
        }
        else if(itype == 2)
        {
            mRecPercent_Vertical.setVisibility(VISIBLE);
            add_vertical_bar();
        }

    }

    private void v_set_healthy()
    {
        float fdata ;
        String sdata ;
        String sunit;

        float finput ;
        float fdata_min;
        float fdata_max;
        float fdiff;
        float fscale;
        float fval;
        float fbar_range = 100;
        float fw = 0;

        float[] f_array ;
        String[] mIndex_String ;
        int[] mREC_COLOR ;

        int iLoop ;
        finput = BodyManagerFunc.f_get_bodymanage_rawdata(mContext, imode);

        f_array = BodyManagerFunc.f_get_bar_rangers(mContext, imode);

        mIndex_String = BodyManagerFunc.s_get_bar_Strings(mContext, imode);
        mREC_COLOR = BodyManagerFunc.i_get_bar_colors(mContext, imode);

        mRecPercent_Vertical.setVisibility(INVISIBLE);

        if(f_array != null)
        {
            if(f_array.length > 2) {
                fdata_min = f_array[0];
                fdata_max = f_array[f_array.length - 1];
                fdiff = fdata_max - fdata_min;
                fscale = fbar_range / fdiff;

                mRecPercent_Vertical.clear_Percent();
                for (iLoop = 1; iLoop < f_array.length; iLoop++) {
                    fval = (f_array[iLoop] - fdata_min) * fscale;
                    mRecPercent_Vertical.setPercent(iLoop - 1, (int) fval);
                }

                fval = (finput - fdata_min) * fscale;
                mRecPercent_Vertical.set_pointer_posy(fval);

                sunit = BodyManagerFunc.s_get_bodymanage_unit(mContext, imode);
                sdata = BodyManagerFunc.s_get_drawdata(mContext, imode) + " " + sunit;
                fdata = (BodyManagerFunc.f_get_bodymanage_rawdata(mContext, imode) - fdata_min) * fscale;
                mRecPercent_Vertical.set_pointer_textval(fdata, sdata);
                mRecPercent_Vertical.setPointerTextSize(52);

                mRecPercent_Vertical.setTextPercent(mIndex_String);
                mRecPercent_Vertical.setColor(mREC_COLOR);

                mRecPercent_Vertical.v_Redraw();
                mRecPercent_Vertical.setVisibility(VISIBLE);
            }
        }

    }

    private void v_input_show()
    {
        int ix, iy, iw, ih;
        float fsize;
        int ix_temp, iy_temp, iw_temp, ih_temp;
        String sdata;

        if(BodyManagerFunc.is_edit(mContext, imode)) {
            ix = 920;
            iy = 590;
            iw = 50;
            ih = 50;
            fsize = 20f;
            addRtxImageViewToLayout(i_input, R.xml.bd_input, ix, iy, iw, ih);
            ix_temp = ix + iw + 20;
            iy_temp = iy;
            iw_temp = 260;
            ih_temp = ih;
            sdata = LanguageData.s_get_string(mContext, R.string.input);
            addRtxTextViewToLayout(t_input, sdata.toUpperCase(), fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT | Gravity.CENTER_VERTICAL, ix_temp, iy_temp, iw_temp, ih_temp);
        }

    }

    private void v_history_show()
    {
        int ix, iy, iw, ih;
        float fsize;
        int ix_temp, iy_temp, iw_temp, ih_temp;
        String sdata;

        ix = 920;
        iy = 680;
        iw = 50;
        ih = 50;
        fsize = 20f;
        addRtxImageViewToLayout(i_history, R.xml.bd_history, ix, iy, iw, ih);
        ix_temp = ix + iw + 20;
        iy_temp = iy;
        iw_temp = 260;
        ih_temp = ih;
        sdata = LanguageData.s_get_string(mContext, R.string.view_history);
        addRtxTextViewToLayout(t_history, sdata.toUpperCase(), fsize, Common.Color.bd_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.LEFT|Gravity.CENTER_VERTICAL, ix_temp, iy_temp, iw_temp, ih_temp);

    }

    private void v_check_click_enable()
    {
        int icolor_false = Common.Color.bd_word_gray;
        int icolor_true = Common.Color.bd_word_white;

        if(!b_draw_view_is_null())
        {
            i_input.setEnabled(true);
            t_input.setTextColor(icolor_true);
            if(historypoints.size() <= 0)
            {
                i_history.setEnabled(false);
                t_history.setTextColor(icolor_false);
            }
            else
            {
                i_history.setEnabled(true);
                t_history.setTextColor(icolor_true);
            }
        }
        else
        {
            i_input.setEnabled(false);
            t_input.setTextColor(icolor_false);
            i_history.setEnabled(false);
            t_history.setTextColor(icolor_false);
        }
    }

    private void v_Refresh_draw(String stitle)
    {
        int iLoop;
        int idata_max = 8;
        int idata_count = 8;

        if(historypoints.size() > 0) {
            idata_count = historypoints.size();
            if(idata_count > idata_max)
            {
                idata_count = idata_max;
            }

            String[] sx = new String[idata_count];
            float[] fy= new float[idata_count];

            HistoryData value;
            for(iLoop = 0; iLoop < idata_count; iLoop++)
            {
                value = historypoints.get(iLoop);

                if (value != null) {
                    try {
                        int siip = EngSetting.getUnit();
                        sx[iLoop] = Rtx_Calendar.s_trans_DateTime_Str(1, "M/dd", "M/dd/yyyy", value.sDate, 0, 0);
                        fy[iLoop] = BodyManagerFunc.f_get_drawdata_string(mContext, imode, value.sVal, siip);
                    } catch (Exception e) {

                    }
                }
            }


            if(idata_count > 0)
            {
                float[] fydata = new float[idata_count];
                String[] sxdata = new String[idata_count];

                for(iLoop = 0; iLoop < idata_count; iLoop++ )
                {
                    fydata[idata_count - 1 - iLoop] = fy[iLoop];
                    sxdata[idata_count - 1 - iLoop] = sx[iLoop];
                }
                draw_view.Create_Draw_Data(stitle, sxdata.length, sxdata, fydata.length, fydata);
            }

        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void vClickBack()
    {
        int iLoop ;
        int istr_list[];

        istr_list = mMainActivity.mMainProcBike.bodymanagerProc.ipage_list[0];


        for(iLoop = 0; iLoop < istr_list.length; iLoop++)
        {
            if(imode == istr_list[iLoop])
            {
                mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_PAGE01);
                return;
            }
        }

        istr_list = mMainActivity.mMainProcBike.bodymanagerProc.ipage_list[1];
        for(iLoop = 0; iLoop < istr_list.length; iLoop++)
        {
            if(imode == istr_list[iLoop])
            {
                mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_PAGE02);
                return;
            }
        }

        mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_PAGE01);

    }

    private void vClickInput()
    {
        mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_INPUT);
    }

    private void vClickHistory()
    {
        mMainActivity.mMainProcBike.bodymanagerProc.vSetNextState(BodyManagerState.PROC_SHOW_HISTORY);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)      { vClickBack(); }
            else if(v == i_input)      { vClickInput(); }
            else if(v == i_history)      { vClickHistory(); }

        }
    }
}
