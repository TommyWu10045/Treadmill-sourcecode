package com.rtx.treadmill.RtxMainFunction.Physical;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunction.BaseLayout.SummaryBaseLayout;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxFillerTextView;
import com.rtx.treadmill.RtxView.RtxPercentCircleView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class CooperFinishLayout extends SummaryBaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;
    private MainActivity        mMainActivity;

    private RtxTextView       t_complete;
    private RtxFillerTextView t_grade;

    private RtxTextView       t_score;

    private RtxTextView       t_percent;
    private RtxPercentCircleView mPercentCircle;

    private RtxTextView       t_time;
    private RtxTextView       t_time_info;
    private RtxDoubleStringView t_distance;
    private RtxTextView       t_distance_info;


    private int icolor_com = Common.Color.physical_word_green;
    private int icolor_imcom = Common.Color.physical_word_pink;

    public CooperFinishLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;

    }

    @Override
    protected void init_CustomerView()
    {
        if(t_complete == null)    { t_complete = new RtxTextView(mContext);     }
        if(t_grade == null)    { t_grade = new RtxFillerTextView(mContext);     }

        if(t_score == null)    { t_score = new RtxTextView(mContext);     }

        if(mPercentCircle == null)           { mPercentCircle = new RtxPercentCircleView(mContext); }
        if(t_percent == null)    { t_percent = new RtxTextView(mContext);     }

        if(t_time == null)    { t_time = new RtxTextView(mContext);     }
        if(t_time_info == null)    { t_time_info = new RtxTextView(mContext);     }
        if(t_distance == null)    { t_distance = new RtxDoubleStringView(mContext);     }
        if(t_distance_info == null)    { t_distance_info = new RtxTextView(mContext);     }

    }

    @Override
    protected void init_CustomerEvent()
    {

    }

    @Override
    protected void add_CustomerView()
    {
        int ix, iy, iw, ih;
        String sdata;
        float fsize, fsize_unit;;
        int icolor;
        int igrade;
        int ipercent;
        String sunit;
        float fval;

        //        menu
        sdata = LanguageData.s_get_string(mContext, R.string.summary) + " - " +LanguageData.s_get_string(mContext, R.string.cooper_test);
        vSetTitleText(sdata.toUpperCase());

        //      grade
        ix = 0;
        iy = 150;
        iw = 460;
        ih = 70;
        fsize = 46.75f;
        sdata = s_get_complete_string();
        icolor = i_get_complete_color();
        addRtxTextViewToLayout(t_complete, sdata.toUpperCase(), fsize, icolor, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        ix = 72;
        iy = 287;
        iw = 308;
        ih = 94;
        fsize = 33.33f;
        igrade = (int)f_cooper_grade();
        sdata = s_get_grade_string(igrade);
        icolor = i_get_grade_color(igrade);
        addRtxTextViewToLayout(t_grade, "", fsize, Common.Color.physical_word_white, Common.Font.KatahdinRound, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih, icolor);
        t_grade.setText(sdata.toUpperCase());
        t_grade.setMode(0);
        t_grade.setFillet(ih/2);
        if(b_get_complete())
        {
            t_grade.setVisibility(VISIBLE);
        }
        else
        {
            t_grade.setVisibility(INVISIBLE);
        }

        ix = 0;
        iy = 426;
        iw = 460;
        ih = 40;
        fsize = 20f;
        sdata = LanguageData.s_get_string(mContext, R.string.score);
        addRtxTextViewToLayout(t_score, sdata.toUpperCase(), fsize, Common.Color.physical_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

//        circle percent
        ix = 509;
        iy = 163;
        iw = 262;
        ih = 262;
        fsize = 75.65f;
        addViewToLayout(mPercentCircle, ix, iy, iw, ih);

        ipercent = i_get_percent();

        sdata = Rtx_TranslateValue.sInt2String(ipercent) + "%";
        addRtxTextViewToLayout(t_percent, sdata, fsize, Common.Color.physical_word_yellow, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
        mPercentCircle.setPercentValue(ipercent);
        mPercentCircle.setVisibility(INVISIBLE);
        t_percent.setVisibility(INVISIBLE);

//        distance info
        ix = 390;
        iy = 200;
        iw = 500;
        ih = 160;
        fsize = 150f;
        fsize_unit = 40f;
        fval = ExerciseGenfunc.f_get_total_distance_show_km();
        sdata = EngSetting.Distance.getValString(fval);
        sunit = EngSetting.Distance.getUnitString(mContext);
        addRtxDoubleStringView(t_distance, ix, iy, iw, ih);
        t_distance.setGap(20);
        t_distance.setPaint(Common.Font.Relay_Black, Common.Color.physical_word_yellow, fsize, Common.Font.Relay_BlackItalic, Common.Color.physical_word_blue, fsize_unit);
        t_distance.setText(sdata.toUpperCase(), sunit.toLowerCase());

        iy += ih;
        ih = 50;
        fsize = 20f;
        sdata = LanguageData.s_get_string(mContext, R.string.distance);
        addRtxTextViewToLayout(t_distance_info, sdata.toUpperCase(), fsize, Common.Color.physical_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

//        time info
        ix = 900;
        iy = 250;
        iw = 300;
        ih = 100;
        fsize = 100f;
        sdata = Rtx_Calendar.s_trans_integer(3, ExerciseGenfunc.i_get_total_time());
        addRtxTextViewToLayout(t_time, sdata.toUpperCase(), fsize, Common.Color.physical_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);


        iy += ih;
        ih = 50;
        fsize = 20f;
        sdata = LanguageData.s_get_string(mContext, R.string.time);
        addRtxTextViewToLayout(t_time_info, sdata.toUpperCase(), fsize, Common.Color.physical_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);



        runmode_refresh();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void runmode_refresh()
    {
        String stitle;
        String smode;

        smode = LanguageData.s_get_string(mContext, R.string.physical_test).toUpperCase();
        stitle = LanguageData.s_get_string(mContext, R.string.cooper_test);

        v_set_type_name(smode, stitle.toUpperCase());
    }

    private int i_get_percent()
    {
        int igrade;

        igrade = 80;

        return igrade;
    }

    //0:very good; 1:good; 2:average; 3:bad; 4:very bad
    private float f_cooper_grade() {
        float fgrade = 0;
        float iage = ExerciseData.fage;
        int igender = ExerciseData.igender;
        int iLoop;
        int iage_min, iage_max;
        float[] farray_age;
        float[] farray_stage;
        float fdistance;

        float[][] fage = {
                {13, 15, 17, 21, 30, 40, 50, 999},//female : igender = 0
                {13, 15, 17, 21, 30, 40, 50, 999}//male : igender = 1
        };

        //cooper grade
        float[][][] fstage = {
                {
                        {1999, 1899, 1599, 1499, 0},
                        {2099, 1999, 1899, 1599, 0},
                        {2299, 2099, 1799, 1699, 0},
                        {2699, 2199, 1799, 1499, 0},
                        {2499, 1999, 1699, 1399, 0},
                        {2299, 1899, 1499, 1199, 0},
                        {2199, 1699, 1399, 1099, 0}
                },
                {
                        {2699, 2399, 2199, 2099, 0},
                        {2799, 2499, 2299, 2199, 0},
                        {2999, 2699, 2499, 2299, 0},
                        {2799, 2399, 2199, 1599, 0},
                        {2699, 2299, 1899, 1499, 0},
                        {2499, 2099, 1699, 1399, 0},
                        {2399, 1999, 1599, 1299, 0}
                }
        };

        if(igender == 0 || igender == 1)
        {
            iage_min = (int)fage[igender][0];
            iage_max = (int)fage[igender][fage[igender].length-1];
            if(iage < iage_min || iage > iage_max)
            {
                return fgrade;
            }
        }
        else
        {
            return fgrade;
        }

        farray_age = fage[igender];
        for(iLoop = 1; iLoop < farray_age.length; iLoop++)
        {
            if(iage < farray_age[iLoop])
            {
                break;
            }
        }

        farray_stage = fstage[igender][iLoop-1];
        fdistance = ExerciseData.mCaculate_Data.sdistance.ftotal;

        for(iLoop = 0; iLoop < farray_stage.length; iLoop++)
        {
            if(fdistance > farray_stage[iLoop])
            {
                break;
            }
        }

        if(iLoop < farray_stage.length)
        {
            fgrade = iLoop;
        }

        return fgrade;
    }

    private String s_get_grade_string(int igrade)
    {
        String sdata;

        switch (igrade)
        {
            case 0:
                sdata = LanguageData.s_get_string(mContext, R.string.very_good);
                break;
            case 1:
                sdata = LanguageData.s_get_string(mContext, R.string.good);
                break;
            case 2:
                sdata = LanguageData.s_get_string(mContext, R.string.average);
                break;
            case 3:
                sdata = LanguageData.s_get_string(mContext, R.string.bad);
                break;
            case 4:
                sdata = LanguageData.s_get_string(mContext, R.string.very_bad);
                break;
            default:
                sdata = LanguageData.s_get_string(mContext, R.string.very_good);
                break;
        }

        return sdata.toUpperCase();
    }

    private int i_get_grade_color(int igrade)
    {
        int icolor;

        switch (igrade)
        {
            case 0:
                icolor = Common.Color.physical_word_blue;
                break;
            case 1:
                icolor = Common.Color.physical_word_blue;
                break;
            case 2:
                icolor = Common.Color.physical_word_pink;
                break;
            case 3:
                icolor = Common.Color.physical_word_pink;
                break;
            case 4:
                icolor = Common.Color.physical_word_pink;
                break;
            default:
                icolor = Common.Color.physical_word_blue;
                break;
        }

        return icolor;
    }

    private int i_get_complete_color()
    {
        int icolor;

        if(b_get_complete()) {
            icolor = icolor_com;
        }
        else {
            icolor = icolor_imcom;
        }

        return icolor;
    }

    private String s_get_complete_string()
    {
        String sdata;

        if(b_get_complete()) {
            sdata = LanguageData.s_get_string(mContext, R.string.complete);
        }
        else {
            sdata = LanguageData.s_get_string(mContext, R.string.imcomplete);
        }

        return sdata;
    }

    private boolean b_get_complete()
    {
        boolean bret = false;
        if(ExerciseData.i_get_finish() == 0x00) {
            bret = true;
        }

        return  bret;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void v_logout()
    {
        mMainActivity.mMainProcTreadmill.physicalProc.vSetNextState(PhysicalState.PROC_SHOW_LOGOUT);
    }

    @Override
    protected void v_done()
    {
        mMainActivity.mMainProcTreadmill.physicalProc.vSetNextState(PhysicalState.PROC_SHOW_DONE);
    }


}
