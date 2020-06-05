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
import com.rtx.treadmill.RtxView.RtxPercentCircleView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class UsmcFinishLayout extends SummaryBaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;
    private MainActivity        mMainActivity;

    private RtxTextView       t_complete;
    private RtxTextView       t_grade;

    private RtxTextView       t_score;

    private RtxTextView       t_percent;
    private RtxPercentCircleView mPercentCircle;

    private RtxTextView       t_time;
    private RtxTextView       t_time_info;
    private RtxDoubleStringView t_distance;
    private RtxTextView       t_distance_info;


    private int icolor_com = Common.Color.physical_word_green;
    private int icolor_imcom = Common.Color.physical_word_pink;

    private int icolor_low_risk = Common.Color.physical_word_green;
    private int icolor_moderate_risk = Common.Color.physical_word_green;
    private int icolor_high_risk = Common.Color.physical_word_pink;

    public UsmcFinishLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;

    }

    @Override
    protected void init_CustomerView()
    {
        if(t_complete == null)    { t_complete = new RtxTextView(mContext);     }
        if(t_grade == null)    { t_grade = new RtxTextView(mContext);     }

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
        String sdata, ssummary;
        float fsize, fsize_unit;;
        int icolor;
        int igrade;
        int ipercent;
        String sunit;
        float fval;

        //        menu
        switch (ExerciseData.E_mode)
        {
            case PROC_EXERCISE_PHY_GERKIN  :
                sdata = LanguageData.s_get_string(mContext, R.string.gerkin_test);
                break;
            case PROC_EXERCISE_PHY_COOPER  :
                sdata = LanguageData.s_get_string(mContext, R.string.cooper_test);
                break;
            case PROC_EXERCISE_PHY_USMC    :
                sdata = LanguageData.s_get_string(mContext, R.string.usmc_ptf);
                break;
            case PROC_EXERCISE_PHY_ARMY    :
                sdata = LanguageData.s_get_string(mContext, R.string.army_prt);
                break;
            case PROC_EXERCISE_PHY_NAVY    :
                sdata = LanguageData.s_get_string(mContext, R.string.navy_prt);
                break;
            case PROC_EXERCISE_PHY_USAF    :
                sdata = LanguageData.s_get_string(mContext, R.string.usaf_ptf);
                break;
            case PROC_EXERCISE_PHY_FEDERAL :
                sdata = LanguageData.s_get_string(mContext, R.string.federal_law);
                break;
            default:
                sdata = LanguageData.s_get_string(mContext, R.string.gerkin_test);
                break;
        }
        ssummary = LanguageData.s_get_string(mContext, R.string.summary) + " - " + sdata;
        vSetTitleText(ssummary.toUpperCase());

        //      grade
        ix = 0;
        iy = 150;
        iw = 460;
        ih = 70;
        fsize = 46.75f;
        sdata = s_get_complete_string();
        icolor = i_get_complete_color();
        addRtxTextViewToLayout(t_complete, sdata.toUpperCase(), fsize, icolor, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        ih = 200;
        fsize = 200f;
        sdata = s_get_grade();
        addRtxTextViewToLayout(t_grade, sdata.toUpperCase(), fsize, icolor, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
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
        switch (ExerciseData.E_mode)
        {
            case PROC_EXERCISE_PHY_GERKIN  :
                stitle = LanguageData.s_get_string(mContext, R.string.gerkin_protocol);
                break;
            case PROC_EXERCISE_PHY_COOPER  :
                stitle = LanguageData.s_get_string(mContext, R.string.cooper_test);
                break;
            case PROC_EXERCISE_PHY_USMC    :
                stitle = LanguageData.s_get_string(mContext, R.string.usmc_ptf);
                break;
            case PROC_EXERCISE_PHY_ARMY    :
                stitle = LanguageData.s_get_string(mContext, R.string.army_prt);
                break;
            case PROC_EXERCISE_PHY_NAVY    :
                stitle = LanguageData.s_get_string(mContext, R.string.navy_prt);
                break;
            case PROC_EXERCISE_PHY_USAF    :
                stitle = LanguageData.s_get_string(mContext, R.string.usaf_ptf);
                break;
            case PROC_EXERCISE_PHY_FEDERAL :
                stitle = LanguageData.s_get_string(mContext, R.string.federal_law);
                break;
            default:
                stitle = LanguageData.s_get_string(mContext, R.string.gerkin_protocol);
                break;
        }

        v_set_type_name(smode, stitle.toUpperCase());
    }

    private int i_get_percent()
    {
        int igrade;
        float ftarget = ExerciseData.mCaculate_Data.ftarget_distance;
        float ftotal = ExerciseData.mCaculate_Data.sdistance.ftotal;

        if(ftarget <= 0)
        {
            igrade = 0;
        }
        else {
            igrade = (int) (100 * ftotal / ftarget);
        }

        if(igrade < 0)
        {
            igrade = 0;
        }
        else if(igrade > 100)
        {
            igrade = 100;
        }
        return igrade;
    }

    private float f_usmc_grade() {
        float fgrade = 100;
        int isec_finish;
        int isec_vg;
        int ideff;

        isec_finish = ExerciseData.mCaculate_Data.itotal_time;
        if(ExerciseData.igender == 0)
        {
            isec_vg = 1260;
        }
        else
        {
            isec_vg = 1080;
        }

        ideff = isec_finish - isec_vg;
        if(ideff > 0) {
            fgrade -= (ideff + 9)/10;
        }

        if(fgrade < 0) {
            fgrade = 0;
        }

        return fgrade;
    }

    private float f_army_grade() {
        float fgrade = 0;
        int iage = ExerciseData.i_get_age();
        int igender = ExerciseData.igender;
        int iLoop;
        int iage_min, iage_max;
        int[] iarray_age;
        int isec_start;
        int[] iarray_stage;
        int idiff;

        int[][] iage_array = {
                {17, 22, 27, 32, 37, 42, 47, 52, 57, 62, 999},//female : igender = 0
                {17, 22, 27, 32, 37, 42, 47, 52, 57, 62, 999}//male : igender = 1
        };

        int[][] isec_array = {
                {936	,	936	,	948	,	954	,	1020,	104	,	105	,	114	,	1182,	1200},//female : igender = 0
                {780	,	780	,	798	,	798	,	816	,	846	,	864	,	882	,	918	,	942}//male : igender = 1
        };

        //cooper grade
        int[][][] istage_array = {
                {
                        {100	,	99	,	98	,	96	,	95	,	94	,	93	,	92	,	90	,	89	,	88	,	87	,	85	,	84	,	83	,	82	,	81	,	79	,	78	,	77	,	76	,	75	,	73	,	72	,	71	,	70	,	68	,	67	,	66	,	65	,	64	,	62	,	61	,	60	,	59	,	58	,	56	,	55	,	54	,	53	,	52	,	50	,	49	,	48	,	47	,	45	,	44	,	43	,	42	,	41	,	39	,	38	,	37	,	36	,	35	,	33	,	32	,	31	,	30	,	28	,	27	,	26	,	25	,	24	,	22	,	21	,	20	,	19	,	18	,	16	,	15	,	14	,	13	,	12	,	10	,	9	,	8	,	7	,	5	,	4	,	3	,	2	,	1	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	98	,	97	,	96	,	95	,	94	,	93	,	92	,	91	,	90	,	89	,	88	,	87	,	86	,	85	,	84	,	83	,	82	,	81	,	80	,	79	,	78	,	77	,	76	,	75	,	74	,	73	,	72	,	71	,	70	,	69	,	68	,	67	,	66	,	65	,	64	,	63	,	62	,	61	,	60	,	59	,	58	,	57	,	56	,	55	,	54	,	53	,	52	,	51	,	50	,	49	,	48	,	47	,	46	,	45	,	44	,	43	,	42	,	41	,	40	,	39	,	38	,	37	,	36	,	35	,	34	,	33	,	32	,	31	,	30	,	29	,	28	,	27	,	26	,	25	,	24	,	23	,	22	,	21	,	20	,	19	,	18	,	17	,	16	,	15	,	14	,	13	,	12	,	11	,	10	,	9	,	8	,	7	,	6	,	5	,	4	,	3	,	2	,	1	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	98	,	97	,	97	,	96	,	95	,	94	,	93	,	92	,	91	,	91	,	90	,	89	,	88	,	87	,	86	,	86	,	85	,	84	,	83	,	82	,	81	,	80	,	80	,	79	,	78	,	77	,	76	,	75	,	74	,	74	,	73	,	72	,	71	,	70	,	69	,	69	,	68	,	67	,	66	,	65	,	64	,	63	,	63	,	62	,	61	,	60	,	59	,	58	,	57	,	57	,	56	,	55	,	54	,	53	,	52	,	51	,	51	,	50	,	49	,	48	,	47	,	46	,	46	,	45	,	44	,	43	,	42	,	41	,	40	,	40	,	39	,	38	,	37	,	36	,	35	,	34	,	34	,	33	,	32	,	31	,	30	,	29	,	29	,	28	,	27	,	26	,	25	,	24	,	23	,	23	,	22	,	21	,	20	,	19	,	18	,	17	,	17	,	16	,	15	,	14	,	13	,	12	,	11	,	11	,	10	,	9	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	99	,	98	,	97	,	97	,	96	,	95	,	94	,	94	,	93	,	92	,	92	,	91	,	90	,	90	,	89	,	88	,	88	,	87	,	86	,	86	,	85	,	84	,	83	,	83	,	82	,	81	,	81	,	80	,	79	,	79	,	78	,	77	,	77	,	76	,	75	,	74	,	74	,	73	,	72	,	72	,	71	,	70	,	70	,	69	,	68	,	68	,	67	,	66	,	66	,	65	,	64	,	63	,	63	,	62	,	61	,	61	,	60	,	59	,	59	,	58	,	57	,	57	,	56	,	55	,	54	,	54	,	53	,	52	,	52	,	51	,	50	,	49	,	49	,	48	,	48	,	47	,	46	,	46	,	45	,	44	,	43	,	43	,	42	,	41	,	41	,	40	,	39	,	39	,	38	,	37	,	37	,	36	,	35	,	34	,	34	,	33	,	32	,	32	,	31	,	30	,	30	,	29	,	28	,	28	,	27	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	99	,	98	,	97	,	96	,	96	,	95	,	94	,	94	,	93	,	92	,	92	,	91	,	90	,	89	,	89	,	88	,	87	,	87	,	86	,	85	,	85	,	84	,	83	,	82	,	82	,	81	,	80	,	80	,	79	,	78	,	78	,	77	,	76	,	75	,	75	,	74	,	73	,	73	,	72	,	71	,	71	,	70	,	69	,	68	,	68	,	67	,	66	,	66	,	65	,	64	,	64	,	63	,	62	,	61	,	61	,	60	,	59	,	59	,	58	,	57	,	56	,	56	,	55	,	54	,	54	,	53	,	52	,	52	,	51	,	50	,	49	,	49	,	48	,	47	,	47	,	46	,	45	,	45	,	44	,	43	,	42	,	42	,	41	,	40	,	40	,	39	,	38	,	38	,	37	,	36	,	35	,	35	,	34	,	33	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	99	,	98	,	97	,	97	,	96	,	96	,	95	,	94	,	94	,	93	,	92	,	92	,	91	,	90	,	90	,	89	,	89	,	88	,	87	,	87	,	86	,	85	,	85	,	84	,	83	,	83	,	82	,	82	,	81	,	80	,	80	,	79	,	78	,	78	,	77	,	77	,	76	,	75	,	75	,	74	,	73	,	73	,	72	,	71	,	71	,	70	,	70	,	69	,	68	,	68	,	67	,	66	,	66	,	65	,	64	,	64	,	63	,	63	,	62	,	61	,	61	,	60	,	59	,	59	,	58	,	57	,	57	,	56	,	56	,	55	,	54	,	54	,	53	,	52	,	52	,	51	,	50	,	50	,	49	,	49	,	48	,	47	,	47	,	46	,	45	,	45	,	44	,	43	,	43	,	42	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	99	,	98	,	97	,	97	,	96	,	96	,	95	,	94	,	94	,	93	,	92	,	92	,	91	,	91	,	90	,	89	,	89	,	88	,	87	,	87	,	86	,	86	,	85	,	84	,	84	,	83	,	82	,	82	,	81	,	81	,	80	,	79	,	79	,	78	,	77	,	77	,	76	,	76	,	75	,	74	,	74	,	73	,	72	,	72	,	71	,	71	,	70	,	69	,	69	,	68	,	67	,	67	,	66	,	66	,	65	,	64	,	64	,	63	,	62	,	62	,	61	,	61	,	60	,	59	,	59	,	58	,	57	,	57	,	56	,	56	,	55	,	54	,	54	,	53	,	52	,	52	,	51	,	51	,	50	,	49	,	49	,	48	,	47	,	47	,	46	,	46	,	45	,	44	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	99	,	98	,	97	,	96	,	96	,	95	,	94	,	93	,	93	,	92	,	91	,	90	,	90	,	89	,	88	,	87	,	87	,	86	,	85	,	84	,	84	,	83	,	82	,	81	,	81	,	80	,	79	,	79	,	78	,	77	,	76	,	76	,	75	,	74	,	73	,	73	,	72	,	71	,	70	,	70	,	69	,	68	,	67	,	67	,	66	,	65	,	64	,	64	,	63	,	62	,	61	,	61	,	60	,	59	,	59	,	58	,	57	,	56	,	56	,	55	,	54	,	53	,	53	,	52	,	51	,	50	,	50	,	49	,	48	,	47	,	47	,	46	,	45	,	44	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	98	,	98	,	97	,	96	,	95	,	95	,	94	,	93	,	92	,	91	,	91	,	90	,	89	,	88	,	87	,	87	,	86	,	85	,	84	,	84	,	83	,	82	,	81	,	80	,	80	,	79	,	78	,	77	,	76	,	76	,	75	,	74	,	73	,	73	,	72	,	71	,	70	,	69	,	69	,	68	,	67	,	66	,	65	,	65	,	64	,	63	,	62	,	62	,	61	,	60	,	59	,	58	,	58	,	57	,	56	,	55	,	55	,	54	,	53	,	52	,	51	,	51	,	50	,	49	,	48	,	47	,	47	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	98	,	98	,	97	,	96	,	95	,	94	,	94	,	93	,	92	,	91	,	90	,	90	,	89	,	88	,	87	,	86	,	86	,	85	,	84	,	83	,	82	,	82	,	81	,	80	,	79	,	78	,	78	,	77	,	76	,	75	,	74	,	74	,	73	,	72	,	71	,	70	,	70	,	69	,	68	,	67	,	66	,	66	,	65	,	64	,	63	,	62	,	62	,	61	,	60	,	59	,	58	,	58	,	57	,	56	,	55	,	54	,	54	,	53	,	52	,	51	,	50	,	50	,	49	,	48	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 }
                },
                {
                        {100	,	99	,	97	,	96	,	94	,	93	,	92	,	90	,	89	,	88	,	86	,	85	,	83	,	82	,	81	,	79	,	78	,	77	,	75	,	74	,	72	,	71	,	70	,	68	,	67	,	66	,	64	,	63	,	61	,	60	,	59	,	57	,	56	,	54	,	53	,	52	,	50	,	49	,	48	,	46	,	45	,	43	,	42	,	41	,	39	,	38	,	37	,	35	,	34	,	32	,	31	,	30	,	28	,	27	,	26	,	24	,	23	,	21	,	20	,	19	,	17	,	16	,	14	,	13	,	12	,	10	,	9	,	8	,	6	,	5	,	3	,	2	,	1	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	98	,	97	,	96	,	94	,	96	,	92	,	91	,	90	,	89	,	88	,	87	,	86	,	84	,	83	,	82	,	81	,	80	,	79	,	78	,	77	,	76	,	74	,	73	,	72	,	71	,	70	,	69	,	68	,	67	,	66	,	64	,	63	,	62	,	61	,	60	,	59	,	58	,	57	,	56	,	54	,	53	,	52	,	51	,	50	,	49	,	48	,	47	,	46	,	44	,	43	,	43	,	41	,	40	,	39	,	38	,	37	,	36	,	34	,	33	,	32	,	31	,	30	,	29	,	28	,	27	,	26	,	24	,	23	,	22	,	21	,	20	,	19	,	18	,	17	,	16	,	14	,	13	,	12	,	11	,	10	,	9	,	8	,	7	,	6	,	4	,	3	,	2	,	1	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	98	,	97	,	96	,	95	,	94	,	92	,	91	,	90	,	89	,	88	,	87	,	86	,	85	,	84	,	83	,	82	,	81	,	79	,	78	,	77	,	76	,	75	,	74	,	73	,	72	,	71	,	70	,	69	,	68	,	66	,	65	,	64	,	63	,	62	,	61	,	60	,	59	,	58	,	57	,	56	,	55	,	54	,	52	,	51	,	50	,	49	,	48	,	47	,	46	,	45	,	44	,	43	,	42	,	41	,	39	,	38	,	37	,	36	,	35	,	34	,	33	,	32	,	31	,	30	,	29	,	28	,	26	,	25	,	24	,	23	,	22	,	21	,	20	,	19	,	18	,	17	,	16	,	15	,	14	,	12	,	11	,	10	,	9	,	8	,	7	,	6	,	5	,	4	,	3	,	2	,	1	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	98	,	97	,	96	,	95	,	95	,	94	,	93	,	92	,	91	,	90	,	89	,	88	,	87	,	86	,	85	,	85	,	84	,	83	,	82	,	81	,	80	,	79	,	78	,	77	,	76	,	75	,	75	,	74	,	73	,	72	,	71	,	70	,	69	,	68	,	67	,	66	,	65	,	65	,	64	,	63	,	62	,	61	,	60	,	59	,	58	,	57	,	56	,	55	,	55	,	54	,	53	,	52	,	51	,	50	,	49	,	48	,	47	,	46	,	45	,	45	,	44	,	43	,	42	,	41	,	40	,	39	,	38	,	37	,	36	,	35	,	35	,	34	,	33	,	32	,	31	,	30	,	29	,	28	,	27	,	26	,	25	,	25	,	24	,	23	,	22	,	21	,	20	,	19	,	18	,	17	,	16	,	15	,	15	,	14	,	13	,	12	,	11	,	10	,	9	,	8	,	7	,	6	,	5	,	5	,	4	,	3	,	2	,	1	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	98	,	97	,	97	,	96	,	95	,	94	,	93	,	92	,	91	,	91	,	90	,	89	,	88	,	87	,	86	,	86	,	85	,	84	,	83	,	82	,	81	,	80	,	80	,	79	,	78	,	77	,	76	,	75	,	74	,	74	,	73	,	72	,	71	,	70	,	69	,	69	,	68	,	67	,	66	,	65	,	64	,	63	,	63	,	62	,	61	,	60	,	59	,	58	,	57	,	57	,	56	,	55	,	54	,	53	,	52	,	51	,	51	,	50	,	49	,	48	,	47	,	46	,	46	,	45	,	44	,	43	,	42	,	41	,	40	,	40	,	39	,	38	,	37	,	36	,	35	,	34	,	34	,	33	,	32	,	31	,	30	,	29	,	29	,	28	,	27	,	26	,	25	,	24	,	23	,	23	,	22	,	21	,	20	,	19	,	18	,	17	,	17	,	16	,	15	,	14	,	13	,	12	,	11	,	11	,	10	,	9	,	8	,	7	,	6	,	6	,	5	,	4	,	3	,	2	,	1	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	98	,	97	,	97	,	96	,	95	,	94	,	93	,	92	,	91	,	90	,	90	,	89	,	88	,	87	,	86	,	85	,	84	,	83	,	83	,	82	,	81	,	80	,	79	,	78	,	77	,	77	,	76	,	75	,	74	,	73	,	72	,	71	,	70	,	70	,	69	,	68	,	67	,	66	,	65	,	64	,	63	,	63	,	62	,	61	,	60	,	59	,	58	,	57	,	57	,	56	,	55	,	54	,	53	,	52	,	51	,	50	,	50	,	49	,	48	,	47	,	46	,	45	,	44	,	43	,	43	,	42	,	41	,	40	,	39	,	38	,	37	,	37	,	36	,	35	,	34	,	33	,	32	,	31	,	30	,	30	,	29	,	28	,	27	,	26	,	25	,	24	,	23	,	23	,	22	,	21	,	20	,	19	,	18	,	17	,	17	,	16	,	15	,	14	,	13	,	12	,	11	,	10	,	10	,	9	,	8	,	7	,	6	,	5	,	4	,	3	,	3	,	2	,	1	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	98	,	98	,	97	,	96	,	95	,	95	,	94	,	93	,	92	,	91	,	91	,	90	,	89	,	88	,	87	,	87	,	86	,	85	,	84	,	84	,	83	,	82	,	81	,	80	,	80	,	79	,	78	,	77	,	76	,	76	,	75	,	74	,	73	,	73	,	72	,	71	,	70	,	69	,	69	,	68	,	67	,	66	,	65	,	65	,	64	,	63	,	62	,	62	,	61	,	60	,	59	,	58	,	58	,	57	,	56	,	55	,	55	,	54	,	53	,	52	,	51	,	51	,	50	,	49	,	48	,	47	,	47	,	46	,	45	,	44	,	44	,	43	,	42	,	41	,	40	,	40	,	39	,	38	,	37	,	36	,	36	,	35	,	34	,	33	,	33	,	32	,	31	,	30	,	29	,	29	,	28	,	27	,	26	,	25	,	25	,	24	,	23	,	22	,	22	,	21	,	20	,	19	,	18	,	18	,	17	,	16	,	15	,	15	,	14	,	13	,	12	,	11	,	11	,	10	,	9	,	8	,	7	,	7	,	6	,	5	,	4	,	4	,	3	,	2	,	1	,	0 },
                        {100	,	99	,	98	,	98	,	97	,	96	,	95	,	95	,	94	,	93	,	92	,	91	,	91	,	90	,	89	,	88	,	87	,	87	,	86	,	85	,	84	,	84	,	83	,	82	,	81	,	80	,	80	,	79	,	78	,	77	,	76	,	76	,	75	,	74	,	73	,	73	,	72	,	71	,	70	,	69	,	69	,	68	,	67	,	66	,	65	,	65	,	64	,	63	,	62	,	62	,	61	,	60	,	59	,	58	,	58	,	57	,	56	,	55	,	55	,	54	,	53	,	52	,	51	,	51	,	50	,	49	,	48	,	47	,	47	,	46	,	45	,	44	,	44	,	43	,	42	,	41	,	40	,	40	,	39	,	38	,	37	,	36	,	36	,	35	,	34	,	33	,	33	,	32	,	31	,	30	,	29	,	29	,	28	,	27	,	26	,	25	,	25	,	24	,	23	,	22	,	22	,	21	,	20	,	19	,	18	,	18	,	17	,	16	,	15	,	15	,	14	,	13	,	12	,	11	,	11	,	10	,	9	,	8	,	7	,	7	,	6	,	5	,	4	,	4	,	3	,	2	,	1	,	0 },
                        {100	,	99	,	98	,	97	,	97	,	96	,	95	,	94	,	93	,	92	,	91	,	90	,	90	,	89	,	88	,	87	,	86	,	85	,	84	,	83	,	83	,	82	,	81	,	80	,	79	,	78	,	77	,	77	,	76	,	75	,	74	,	73	,	72	,	71	,	70	,	70	,	69	,	68	,	67	,	66	,	65	,	64	,	63	,	63	,	62	,	61	,	60	,	59	,	58	,	57	,	57	,	56	,	55	,	54	,	53	,	52	,	51	,	50	,	50	,	49	,	48	,	47	,	46	,	45	,	44	,	43	,	43	,	42	,	41	,	40	,	39	,	38	,	37	,	37	,	36	,	35	,	34	,	33	,	32	,	31	,	30	,	30	,	29	,	28	,	27	,	26	,	25	,	24	,	23	,	23	,	22	,	21	,	20	,	19	,	18	,	17	,	17	,	16	,	15	,	14	,	13	,	12	,	11	,	10	,	10	,	9	,	8	,	7	,	6	,	5	,	4	,	3	,	3	,	2	,	1	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 },
                        {100	,	99	,	98	,	97	,	96	,	95	,	94	,	93	,	93	,	92	,	91	,	90	,	89	,	88	,	87	,	86	,	85	,	84	,	83	,	82	,	81	,	80	,	80	,	79	,	78	,	77	,	76	,	75	,	74	,	73	,	72	,	71	,	70	,	69	,	68	,	67	,	67	,	66	,	65	,	64	,	63	,	62	,	61	,	60	,	59	,	58	,	57	,	56	,	55	,	54	,	53	,	53	,	52	,	51	,	50	,	49	,	48	,	47	,	46	,	45	,	44	,	43	,	42	,	41	,	40	,	40	,	39	,	38	,	37	,	36	,	35	,	34	,	33	,	32	,	31	,	30	,	29	,	28	,	27	,	27	,	26	,	25	,	24	,	23	,	22	,	21	,	20	,	19	,	18	,	17	,	16	,	15	,	14	,	13	,	13	,	12	,	11	,	10	,	9	,	8	,	7	,	6	,	5	,	4	,	3	,	2	,	1	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0 }

                }
        };

        //check age and gender is legal
        if(igender >= 0 && igender <= 1)
        {
            iage_min = (int)iage_array[igender][0];
            iage_max = (int)iage_array[igender][iage_array[igender].length-1];
            if(iage < iage_min || iage > iage_max)
            {
                return fgrade;
            }
        }
        else
        {
            return fgrade;
        }

        iarray_age = iage_array[igender];
        for(iLoop = 1; iLoop < iarray_age.length; iLoop++)
        {
            if(iage < iarray_age[iLoop])
            {
                break;
            }
        }

        if(iLoop >= iage_array[igender].length)
        {
            return fgrade;
        }

        iarray_stage = istage_array[igender][iLoop-1];
        isec_start = isec_array[igender][iLoop-1];

        idiff = (ExerciseData.mCaculate_Data.itotal_time - isec_start + 5)/6;

        if(idiff >= iarray_stage.length)
        {
            return fgrade;
        }

        if(idiff < 0)
        {
            idiff = 0;
        }

        fgrade = (float) iarray_stage[idiff];

        return fgrade;
    }

    private float f_navy_grade() {
        float fgrade = 0;
        int iage = ExerciseData.i_get_age();
        int igender = ExerciseData.igender;
        int iLoop;
        int iage_min, iage_max;
        int[] iarray_age;
        int[] iarray_sec;
        int isec;

        int[][] iage_array = {
                {17	,	20	,	25	,	30	,	35	,	40	,	45	,	50	,	55	,	60	,	65	,	999},//female : igender = 0
                {20	,	25	,	30	,	35	,	40	,	45	,	50	,	55	,	60	,	65	,	999}//male : igender = 1
        };

        int[][][] isec_array = {
                {
                        { 569	,	690	,	750	,	810	,	900  },
                        { 587	,	690	,	795	,	855	,	930  },
                        { 617	,	705	,	803	,	893	,	968  },
                        { 646	,	720	,	810	,	930	,	1005 },
                        { 651	,	728	,	825	,	953	,	1020 },
                        { 656	,	735	,	840	,	975	,	1035 },
                        { 658	,	750	,	848	,	990	,	1043 },
                        { 660	,	765	,	855	,	1005,	1050 },
                        { 743	,	837	,	920	,	1068,	1114 },
                        { 814	,	908	,	985	,	1131,	1183 },
                        { 885	,	979	,	1050,	1196,	1252 }
                },
                {
                        {510	,	555	,	630	,	720	,	810  },
                        {525	,	578	,	652	,	773	,	840  },
                        {560	,	600	,	675	,	825	,	870  },
                        {565	,	608	,	683	,	848	,	900  },
                        {570	,	615	,	705	,	870	,	930  },
                        {573	,	630	,	728	,	893	,	968  },
                        {575	,	645	,	750	,	915	,	1005 },
                        {642	,	685	,	792	,	975	,	1029 },
                        {681	,	724	,	833	,	1067,	1132 },
                        {701	,	763	,	874	,	1093,	1235 }
                }
        };

        int[][] istage_array = {
                {100	,	90	,	75	,	60	,	45},//female : igender = 0
                {100	,	90	,	75	,	60	,	45}//male : igender = 1
        };

        //check age and gender is legal
        if(igender >= 0 && igender <= 1)
        {
            iage_min = (int)iage_array[igender][0];
            iage_max = (int)iage_array[igender][iage_array[igender].length-1];
            if(iage < iage_min || iage > iage_max)
            {
                return fgrade;
            }
        }
        else
        {
            return fgrade;
        }

        iarray_age = iage_array[igender];
        for(iLoop = 1; iLoop < iarray_age.length; iLoop++)
        {
            if(iage < iarray_age[iLoop])
            {
                break;
            }
        }

        if(iLoop >= iage_array[igender].length)
        {
            return fgrade;
        }

        iarray_sec = isec_array[igender][iLoop-1];
        isec = ExerciseData.mCaculate_Data.itotal_time;

        for(iLoop = 0; iLoop < iarray_sec.length; iLoop++)
        {
            if(isec <= iarray_sec[iLoop])
            {
                break;
            }
        }

        if(iLoop < istage_array[igender].length)
        {
            fgrade = (float) istage_array[igender][iLoop];
        }

        return fgrade;
    }

    private float f_usaf_grade() {
        float fgrade = 0;
        int iage = ExerciseData.i_get_age();
        int igender = ExerciseData.igender;
        int iLoop;
        int iage_min, iage_max;
        int[] iarray_age;
        int[] iarray_sec;
        float[] farray_stage;
        int isec;

        int[][] iage_array = {
                {30	,	40	,	50	,	60	,	999},//female : igender = 0
                {30	,	40	,	50	,	60	,	999}//male : igender = 1
        };

        int[][][] isec_array = {
                {
                        {623	,	651	,	666	,	682	,	698	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017	,	1054	,	1094	,	1136	,	1183	,	1233  ,   9999},
                        {651	,	682	,	698	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017	,	1054	,	1094	,	1136	,	1183	,	1233       ,   9999           },
                        {682	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017	,	1054	,	1094	,	1136	,	1183	,	1233	,	1288	,	1348   ,   9999       },
                        {773	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017,	1054,	1094,	1136,	1183	,	1233	,	1288	,	1348	,	1414    ,   9999                                  },
                        {840	,	892	,	920	,	950	,	982	,	1017,	1054,	1094,	1136,	1183,	1233,	1288,	1348	,	1414	,	1486	,	1566    ,   9999                                  }
                },
                {
                        {552	,	574	,	585	,	598	,	610	,	623	,	637	,	651	,	666	,	682	,	698	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017      ,   9999               },
                        {598	,	610	,	623	,	637	,	651	,	666	,	682	,	698	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017                               ,   9999              },
                        {585	,	610	,	623	,	637	,	651	,	666	,	682	,	698	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017	,	1054	,	1094       ,   9999              },
                        {637	,	666	,	682	,	698	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017	,	1054	,	1094	,	1136	,	1183	,	1233    ,   9999             },
                        {682	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017,	1054,	1094	,	1136	,	1183	,	1233	,	1288	,	1348	,	1414  ,   9999   }

                }
        };

        float[][][] fstage_array = {
                {
                        {60f	,	59.9f	,	59.5f	,	59.2f	,	58.9f	,	58.6f	,	58.1f	,	57.6f	,	57f  	,	56.2f	,	55.3f	,	54.2f	,	52.8f	,	51.2f	,	49.3f	,	46.9f	,	44.1f	,	40.8f	,	36.7f	,	31.8f	,	25.9f	,	18.8f	,	10.3f	,	0f  },
                        {60f	,	59.5f	,	59f     ,	58.6f	,	58.1f	,	57.6f	,	57f     ,	56.2f	,	55.3f	,	54.2f	,	52.8f	,	51.2f	,	49.3f	,	46.9f	,	44.1f	,	40.8f	,	36.7f	,	31.8f	,	25.9f	,	18.8f	,	10.3f	,	0f                          },
                        {60f	,	59.9f	,	59.8f	,	59.6f	,	59.4f	,	59.1f	,	58.7f	,	58.2f	,	57.7f	,	56.9f	,	56f 	,	54.8f	,	53.3f	,	51.4f	,	49f  	,	45.9f	,	42f  	,	37.1f	,	30.8f	,	22.9f	,	12.8f	,	0f                          },
                        {60f	,	59.8f	,	59.6f	,	59.3f	,	28.9f	,	58.4f	,	57.7f	,	56.8f	,	55.6f	,	54f	    ,	51.9f	,	49.2f	,	45.5f	,	40.7f	,	34.3f	,	25.9f	,	14.7f	,	0f                                                                               },
                        {60f	,	59.8f	,	59.5f	,	59.1f	,	58.6f	,	57.9f	,	57f	    ,	55.8f	,	54.2f	,	52.1f	,	49.3f	,	45.6f	,	40.8f	,	34.4f	,	26f  	,	14.8f	,	0f                                                                                           }

                },
                {
                        {60f	,	59.7f	,	59.3f	,	58.9f	,	58.5f	,	57.9f	,	57.3f	,	56.6f	,	55.7f	,	54.8f	,	53.7f	,	52.4f	,	50.9f	,	49.2f	,	47.2f	,	44.9f	,	42.3f	,	39.3f	,	35.8f	,	31.7f	,	27.1f	,	21.7f	,	15.5f	,	8.3f	,	0f  },
                        {60f	,	59.3f	,	58.6f	,	57.9f	,	57.3f	,	56.6f	,	55.7f	,	54.8f	,	53.7f	,	52.4f	,	50.9f	,	49.2f	,	47.2f	,	44.9f	,	42.3f	,	39.3f	,	35.8f	,	31.7f	,	27.1f	,	21.7f	,	15.5f	,	8.3f 	,	0f                      },
                        {60f	,	59.8f	,	59.5f	,	59.1f	,	58.7f	,	58.3f	,	57.7f	,	57.1f	,	56.3f	,	55.4f	,	54.3f	,	53.1f	,	51.5f	,	49.8f	,	47.7f	,	45.2f	,	42.3f	,	38.8f	,	34.7f	,	29.9f	,	24.2f	,	17.4f 	,	9.4f	,	0f      },
                        {60f	,	59.7f	,	59.4f	,	59f	    ,	58.5f	,	58f 	,	57.3f	,	56.5f	,	55.6f	,	54.5f	,	53.3f	,	51.8f	,	50f 	,	47.9f	,	45.4f	,	42.4f	,	39f  	,	34.9f	,	30f  	,	24.3f	,	17.5f	,	9.5f 	,	0f                      },
                        {60f	,	59.7f	,	59.4f	,	59f	    ,	58.5f	,	58f 	,	57.3f	,	56.5f	,	55.6f	,	54.5f	,	53.3f	,	51.8f	,	50f 	,	47.9f	,	45.4f	,	42.4f	,	39f  	,	34.9f	,	30f  	,	24.3f	,	17.5f	,	9.5f 	,	0f                      }

                }
        };

        iarray_age = iage_array[igender];
        for(iLoop = 1; iLoop < iarray_age.length; iLoop++)
        {
            if(iage < iarray_age[iLoop])
            {
                break;
            }
        }

        if(iLoop >= iage_array[igender].length)
        {
            return fgrade;
        }

        iarray_sec = isec_array[igender][iLoop];
        farray_stage = fstage_array[igender][iLoop];
        isec = ExerciseData.mCaculate_Data.itotal_time;

        for(iLoop = 0; iLoop < iarray_sec.length; iLoop++)
        {
            if(isec <= iarray_sec[iLoop])
            {
                break;
            }
        }

        if(iLoop < farray_stage.length)
        {
            fgrade = farray_stage[iLoop];
        }

        return fgrade;
    }

    private int i_usaf_risklevel() {
        int irisk = 0;
        int iage = ExerciseData.i_get_age();
        int igender = ExerciseData.igender;
        int iLoop;
        int iage_min, iage_max;
        int[] iarray_age;
        int[] iarray_sec;
        int[] iarray_stage;
        int isec;

        int[][] iage_array = {
                {30	,	40	,	50	,	60	,	999},//female : igender = 0
                {30	,	40	,	50	,	60	,	999}//male : igender = 1
        };

        int[][][] isec_array = {
                {
                        {623	,	651	,	666	,	682	,	698	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017	,	1054	,	1094	,	1136	,	1183	,	1233  ,   9999},
                        {651	,	682	,	698	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017	,	1054	,	1094	,	1136	,	1183	,	1233       ,   9999           },
                        {682	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017	,	1054	,	1094	,	1136	,	1183	,	1233	,	1288	,	1348   ,   9999       },
                        {773	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017,	1054,	1094,	1136,	1183	,	1233	,	1288	,	1348	,	1414    ,   9999                                  },
                        {840	,	892	,	920	,	950	,	982	,	1017,	1054,	1094,	1136,	1183,	1233,	1288,	1348	,	1414	,	1486	,	1566    ,   9999                                  }
                },
                {
                        {552	,	574	,	585	,	598	,	610	,	623	,	637	,	651	,	666	,	682	,	698	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017      ,   9999               },
                        {598	,	610	,	623	,	637	,	651	,	666	,	682	,	698	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017                               ,   9999              },
                        {585	,	610	,	623	,	637	,	651	,	666	,	682	,	698	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017	,	1054	,	1094       ,   9999              },
                        {637	,	666	,	682	,	698	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017	,	1054	,	1094	,	1136	,	1183	,	1233    ,   9999             },
                        {682	,	716	,	734	,	753	,	773	,	794	,	816	,	840	,	865	,	892	,	920	,	950	,	982	,	1017,	1054,	1094	,	1136	,	1183	,	1233	,	1288	,	1348	,	1414  ,   9999   }

                }
        };

        //0:Low-Risk; 1:Moderate Risk; 2:High Risk
        int[][][] irisk_array = {
                {
                        {0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	1	,	1	,	1	,	2	,	2	,	2	,	2	,	2	,	2  ,   2},
                        {0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	1	,	1	,	1	,	2	,	2	,	2	,	2	,	2       ,   2           },
                        {0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	1	,	1	,	1	,	2	,	2	,	2	,	2	,	2   ,   2       },
                        {0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0   ,	0   ,	0  ,	1  ,	1	,	2	,	2	,	2	,	2   ,   2                                  },
                        {0	,	0	,	0	,	0	,	0	,	0   ,	0  ,	0   ,	0   ,	0   ,	1  ,	1  ,	1	,	2	,	2	,	2   ,   2                                  }
                },
                {
                        {0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	1	,	1	,	1	,	2	,	2	,	2	,	2	,	2	,	2	,	2      ,   2   },
                        {0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	1	,	1	,	1	,	2	,	2	,	2	,	2	,	2	,	2   ,   2    },
                        {0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	1	,	1	,	1	,	2	,	2	,	2	,	2	,	2	,	2   ,   2     },
                        {0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	1	,	1	,	1	,	2	,	2	,	2	,	2	,	2	,	2   ,   2             },
                        {0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	0	,	1,	1,	1	,	2	,	2	,	2	,	2	,	2	,	2   ,   2   }

                }
        };

        iarray_age = iage_array[igender];
        for(iLoop = 1; iLoop < iarray_age.length; iLoop++)
        {
            if(iage < iarray_age[iLoop])
            {
                break;
            }
        }

        if(iLoop >= iage_array[igender].length)
        {
            return irisk;
        }

        iarray_sec = isec_array[igender][iLoop];
        iarray_stage = irisk_array[igender][iLoop];
        isec = ExerciseData.mCaculate_Data.itotal_time;

        for(iLoop = 0; iLoop < iarray_sec.length; iLoop++)
        {
            if(isec <= iarray_sec[iLoop])
            {
                break;
            }
        }

        if(iLoop < iarray_stage.length)
        {
            irisk = iarray_stage[iLoop];
        }

        return irisk;
    }

    private float f_federal_grade() {
        float fgrade = 0;
        int iage = ExerciseData.i_get_age();
        int igender = ExerciseData.igender;
        int iLoop;
        int iage_min, iage_max;
        int[] iarray_age;
        int[] iarray_sec;
        int isec;
        int idiff;

        int[][] iage_array = {
                {25	,	30	,	35	,	40	,	45	,	50	,	55	,	999},//female : igender = 0
                {25	,	30	,	35	,	40	,	45	,	50	,	55	,	60	,	999}//male : igender = 1
        };

        int[][][] isec_array = {
                {
                        {618 ,643 ,662 ,674 ,684 ,697 ,705 ,714 ,723 ,732 ,740 ,746 ,752 ,759 ,765 ,771 ,775 ,780 ,786 ,791 ,797 ,803 ,807 ,811 ,818 ,821 ,828 ,829 ,833 ,837 ,841 ,846 ,841 ,855 ,842 ,865 ,868 ,872 ,878 ,844 ,884 ,887 ,892 ,897 ,901 ,905 ,909 ,913 ,918 ,922 ,928 ,931 ,935 ,904 ,943 ,947 ,953 ,957 ,1082 ,1087 ,1091 ,976 ,1101 ,1108 ,990 ,995 ,1120 ,1124 ,1130 ,1138 ,1023 ,1028 ,1034 ,1039 ,1044 ,1052 ,1059 ,1064 ,1070 ,1078 ,1088 ,1095 ,1104 ,1111 ,1120 ,1085 ,1142 ,1153 ,1165 ,1178 ,1191 ,1205 ,1222 ,1239 ,1261 ,1303 ,1341 ,1387                                                   },
                        {629 ,658 ,677 ,690 ,702 ,711 ,719 ,725 ,733 ,740 ,747 ,754 ,760 ,768 ,772 ,778 ,783 ,788 ,792 ,797 ,803 ,808 ,813 ,818 ,823 ,828 ,832 ,838 ,841 ,847 ,851 ,855 ,859 ,864 ,868 ,873 ,878 ,883 ,887 ,890 ,898 ,900 ,904 ,908 ,912 ,917 ,922 ,927 ,931 ,936 ,940 ,944 ,948 ,953 ,958 ,1082 ,1085 ,1091 ,1097 ,1102 ,1108 ,990 ,995 ,1001 ,1008 ,1131 ,1135 ,1021 ,1028 ,1035 ,1040 ,1047 ,1053 ,1061 ,1068 ,1073 ,1080 ,1086 ,1095 ,1104 ,1113 ,1121 ,1130 ,1137 ,1147 ,1159 ,1168 ,1144 ,1195 ,1204 ,1220 ,1234 ,1251 ,1271 ,1301 ,1329 ,1362 ,1413 ,1500                                         },
                        {629 ,658 ,677 ,690 ,702 ,711 ,719 ,725 ,733 ,740 ,747 ,754 ,760 ,768 ,772 ,778 ,783 ,788 ,792 ,797 ,803 ,808 ,813 ,818 ,823 ,828 ,832 ,838 ,841 ,847 ,851 ,855 ,859 ,864 ,868 ,873 ,878 ,883 ,887 ,890 ,896 ,900 ,904 ,908 ,912 ,917 ,922 ,927 ,931 ,988 ,1112 ,1117 ,1121 ,1125 ,1131 ,1138 ,1023 ,1028 ,1033 ,1039 ,1044 ,1023 ,1055 ,1060 ,1065 ,1070 ,1077 ,1081 ,1087 ,1093 ,1099 ,1105 ,1112 ,1118 ,1124 ,1132 ,1139 ,1145 ,1151 ,1159 ,1169 ,1178 ,1185 ,1195 ,1204 ,1214 ,1228 ,1240 ,1254 ,1268 ,1285 ,1307 ,1327 ,1348 ,1378 ,1403 ,1442 ,1500 ,1687                                  },
                        {668 ,689 ,708 ,717 ,731 ,743 ,754 ,768 ,774 ,783 ,792 ,801 ,809 ,818 ,822 ,829 ,835 ,840 ,845 ,852 ,857 ,861 ,868 ,871 ,877 ,883 ,888 ,893 ,897 ,902 ,908 ,913 ,919 ,923 ,929 ,932 ,938 ,944 ,949 ,953 ,959 ,1085 ,1089 ,975 ,980 ,984 ,988 ,994 ,999 ,1123 ,1127 ,1013 ,1019 ,1024 ,1029 ,1035 ,1039 ,1045 ,1050 ,1058 ,1064 ,1071 ,1075 ,1082 ,1089 ,1098 ,1103 ,1108 ,1115 ,1120 ,1128 ,1135 ,1140 ,1149 ,1160 ,1169 ,1175 ,1184 ,1192 ,1198 ,1208 ,1214 ,1227 ,1234 ,1243 ,1255 ,1261 ,1273 ,1285 ,1297 ,1310 ,1327 ,1345 ,1367 ,1382 ,1434 ,1452 ,1500 ,1738                               },
                        {666 ,698 ,725 ,738 ,754 ,763 ,772 ,789 ,800 ,809 ,817 ,830 ,840 ,851 ,858 ,864 ,872 ,879 ,881 ,888 ,893 ,898 ,905 ,908 ,912 ,918 ,921 ,929 ,936 ,939 ,945 ,952 ,958 ,1083 ,1088 ,1095 ,1102 ,1108 ,1111 ,998 ,1122 ,1010 ,1014 ,1022 ,1027 ,1031 ,1034 ,1038 ,1044 ,1050 ,1052 ,1056 ,1060 ,1067 ,1074 ,1070 ,1083 ,1088 ,1093 ,1096 ,1100 ,1105 ,1110 ,1119 ,1123 ,1127 ,1134 ,1088 ,1148 ,1155 ,1160 ,1165 ,1172 ,1177 ,1183 ,1190 ,1195 ,1200 ,1207 ,1220 ,1224 ,1230 ,1245 ,1251 ,1263 ,1275 ,1280 ,1299 ,1308 ,1320 ,1344 ,1358 ,1385 ,1410 ,1443 ,1484 ,1529 ,1568 ,1649                  },
                        {669 ,737 ,774 ,791 ,805 ,823 ,840 ,848 ,853 ,862 ,867 ,878 ,881 ,887 ,895 ,905 ,911 ,918 ,928 ,929 ,933 ,943 ,905 ,958 ,1082 ,1093 ,1098 ,1102 ,1105 ,1112 ,1118 ,1119 ,1123 ,1128 ,1134 ,1139 ,1027 ,1021 ,1038 ,1042 ,1048 ,1051 ,1054 ,1059 ,1068 ,1069 ,1071 ,1075 ,1083 ,1088 ,1092 ,1097 ,1108 ,1114 ,1123 ,1126 ,1129 ,1133 ,1138 ,1145 ,1148 ,1150 ,1158 ,1165 ,1167 ,1173 ,1177 ,1179 ,1553 ,1192 ,1194 ,1200 ,1205 ,1214 ,1224 ,1231 ,1241 ,1248 ,1250 ,1258 ,1257 ,1260 ,1273 ,1291 ,1299 ,1317 ,1333 ,1339 ,1348 ,1371 ,1391 ,1382 ,1424 ,1440 ,1472 ,1495 ,1500 ,1559              },
                        {778 ,818 ,845 ,865 ,843 ,878 ,877 ,881 ,888 ,898 ,898 ,909 ,915 ,923 ,903 ,941 ,958 ,959 ,1089 ,1091 ,1101 ,1119 ,1127 ,1020 ,1022 ,1025 ,1027 ,1032 ,1038 ,1045 ,1047 ,1051 ,1054 ,1057 ,1062 ,1079 ,1083 ,1118 ,1119 ,1121 ,1129 ,1148 ,1151 ,1153 ,1154 ,1155 ,1158 ,1142 ,1173 ,1178 ,1144 ,1192 ,1194 ,1198 ,1200 ,1219 ,1222 ,1223 ,1227 ,1238 ,1239 ,1245 ,1249 ,1251 ,1252 ,1254 ,1261 ,1871 ,1279 ,1294 ,1304 ,1265 ,1311 ,1328 ,1332 ,1335 ,1341 ,1362 ,1367 ,1373 ,1379 ,1387 ,1387 ,1391 ,1397 ,1412 ,1418 ,1435 ,1469 ,1503 ,1688 ,1694 ,1729 ,1738 ,1680 ,1684 ,1693 ,1703 ,1858  },
                        {674 ,728 ,813 ,875 ,845 ,899 ,924 ,928 ,934 ,942 ,945 ,957 ,971 ,972 ,974 ,985 ,987 ,989 ,990 ,991 ,992 ,995 ,1138 ,1031 ,1057 ,1063 ,1072 ,1075 ,1104 ,1109 ,1110 ,1120 ,1130 ,1140 ,1144 ,1150 ,1152 ,1162 ,1181 ,1182 ,1185 ,1186 ,1191 ,1192 ,1199 ,1202 ,1204 ,1207 ,1209 ,1223 ,1239 ,1248 ,1205 ,1262 ,1264 ,1265 ,1268 ,1280 ,1288 ,1300 ,1318 ,1332 ,1341 ,1349 ,1354 ,1359 ,1368 ,1374 ,1378 ,1380 ,1384 ,1388 ,1387 ,1390 ,1392 ,1404 ,1420 ,1438 ,1448 ,1458 ,1472 ,1480 ,1482 ,1490 ,1512 ,1519 ,1542 ,1550 ,1572 ,1594 ,1603 ,1617 ,1678 ,1718 ,1733 ,1739 ,1798 ,1934 ,1981      }
                },
                {
                        { 527 ,543 ,554 ,563 ,570 ,577 ,582 ,588 ,594 ,598 ,603 ,608 ,612 ,618 ,602 ,623 ,627 ,631 ,634 ,637 ,640 ,644 ,647 ,650 ,653 ,658 ,659 ,662 ,665 ,667 ,671 ,674 ,677 ,680 ,683 ,688 ,689 ,691 ,694 ,697 ,700 ,703 ,708 ,708 ,711 ,714 ,717 ,720 ,722 ,725 ,728 ,731 ,735 ,738 ,722 ,743 ,747 ,750 ,754 ,757 ,760 ,764 ,767 ,770 ,774 ,778 ,781 ,785 ,790 ,794 ,799 ,804 ,808 ,813 ,817 ,823 ,829 ,834 ,839 ,845 ,852 ,858 ,865 ,872 ,844 ,887 ,895 ,905 ,915 ,928 ,938 ,953 ,1087 ,1108 ,1132 ,1038 ,1069 ,1118 ,1185                                                         },
                        { 532 ,549 ,561 ,572 ,544 ,587 ,594 ,599 ,604 ,609 ,614 ,618 ,622 ,626 ,603 ,634 ,637 ,641 ,645 ,648 ,652 ,655 ,659 ,662 ,665 ,660 ,672 ,675 ,678 ,681 ,684 ,687 ,689 ,692 ,695 ,698 ,701 ,704 ,707 ,709 ,712 ,715 ,718 ,721 ,724 ,727 ,721 ,733 ,736 ,730 ,742 ,745 ,749 ,752 ,758 ,759 ,762 ,766 ,769 ,773 ,778 ,780 ,783 ,787 ,791 ,795 ,782 ,804 ,808 ,813 ,817 ,822 ,827 ,832 ,837 ,843 ,848 ,854 ,842 ,867 ,873 ,844 ,887 ,895 ,903 ,901 ,902 ,903 ,942 ,953 ,1085 ,962 ,1115 ,1013 ,1033 ,1060 ,1097 ,1142 ,1218                                                        },
                        { 548 ,564 ,577 ,586 ,594 ,602 ,609 ,615 ,621 ,628 ,631 ,638 ,640 ,644 ,648 ,652 ,657 ,661 ,664 ,668 ,672 ,675 ,679 ,682 ,688 ,689 ,692 ,695 ,698 ,701 ,705 ,708 ,711 ,714 ,717 ,720 ,723 ,728 ,730 ,733 ,738 ,740 ,743 ,746 ,749 ,752 ,755 ,758 ,761 ,765 ,760 ,772 ,775 ,779 ,783 ,788 ,790 ,793 ,798 ,802 ,805 ,810 ,813 ,817 ,821 ,825 ,830 ,835 ,840 ,844 ,849 ,854 ,859 ,864 ,870 ,878 ,882 ,888 ,895 ,902 ,908 ,918 ,924 ,931 ,940 ,948 ,958 ,1087 ,1098 ,990 ,1128 ,1020 ,1034 ,1051 ,1074 ,1099 ,1131 ,1180                                                           },
                        { 555 ,576 ,589 ,599 ,609 ,617 ,624 ,630 ,635 ,641 ,645 ,650 ,655 ,660 ,664 ,670 ,675 ,679 ,683 ,688 ,691 ,695 ,699 ,703 ,707 ,710 ,714 ,717 ,721 ,724 ,728 ,732 ,735 ,739 ,742 ,748 ,750 ,753 ,755 ,760 ,764 ,767 ,771 ,774 ,777 ,780 ,783 ,787 ,791 ,795 ,798 ,802 ,808 ,809 ,813 ,817 ,820 ,824 ,827 ,831 ,835 ,840 ,844 ,849 ,853 ,858 ,862 ,866 ,872 ,877 ,882 ,888 ,893 ,900 ,905 ,901 ,917 ,922 ,929 ,936 ,943 ,950 ,957 ,965 ,974 ,962 ,995 ,1006 ,1017 ,1028 ,1041 ,1058 ,1072 ,1092 ,1113 ,1140 ,638 ,1225 ,1320                                                     },
                        { 574 ,535 ,611 ,622 ,630 ,639 ,645 ,654 ,660 ,667 ,673 ,679 ,684 ,690 ,697 ,703 ,707 ,712 ,718 ,721 ,728 ,731 ,737 ,741 ,748 ,753 ,758 ,762 ,765 ,769 ,772 ,778 ,781 ,785 ,790 ,794 ,798 ,802 ,808 ,810 ,813 ,817 ,822 ,828 ,830 ,834 ,838 ,842 ,846 ,850 ,854 ,859 ,862 ,866 ,870 ,875 ,878 ,883 ,887 ,891 ,898 ,901 ,906 ,911 ,915 ,920 ,926 ,931 ,935 ,939 ,945 ,950 ,955 ,1080 ,968 ,973 ,979 ,987 ,991 ,997 ,1008 ,1014 ,1020 ,1032 ,1040 ,1051 ,1065 ,1076 ,1090 ,1102 ,1117 ,1133 ,1157 ,1177 ,1204 ,1228 ,1279 ,1342 ,1424                                            },
                        { 597 ,748 ,641 ,654 ,665 ,676 ,687 ,695 ,701 ,705 ,712 ,719 ,728 ,732 ,739 ,744 ,750 ,753 ,758 ,763 ,768 ,773 ,778 ,783 ,787 ,791 ,796 ,782 ,805 ,810 ,814 ,818 ,822 ,828 ,832 ,836 ,840 ,845 ,849 ,852 ,856 ,859 ,864 ,869 ,874 ,879 ,882 ,887 ,892 ,898 ,902 ,907 ,910 ,914 ,918 ,922 ,927 ,931 ,935 ,939 ,944 ,949 ,954 ,959 ,964 ,970 ,975 ,980 ,986 ,993 ,998 ,1123 ,1009 ,1019 ,1024 ,1030 ,1037 ,1044 ,1050 ,1058 ,1065 ,1074 ,1082 ,1090 ,1099 ,1108 ,1119 ,1126 ,1131 ,1138 ,1143 ,640 ,1200 ,1215 ,1242 ,1281 ,1326 ,1385 ,1464                                     },
                        { 607 ,632 ,642 ,654 ,663 ,697 ,712 ,723 ,732 ,722 ,748 ,755 ,763 ,771 ,777 ,780 ,785 ,791 ,798 ,805 ,810 ,815 ,820 ,823 ,829 ,838 ,840 ,847 ,852 ,858 ,861 ,865 ,868 ,871 ,875 ,880 ,885 ,889 ,892 ,895 ,901 ,907 ,910 ,918 ,921 ,928 ,928 ,932 ,937 ,943 ,948 ,953 ,957 ,1080 ,1084 ,1088 ,1092 ,1099 ,1107 ,1110 ,1114 ,1120 ,1128 ,1134 ,1137 ,1020 ,1028 ,1034 ,1037 ,1042 ,1049 ,1053 ,1060 ,1068 ,1025 ,1079 ,1085 ,1099 ,1107 ,1114 ,1121 ,1130 ,1141 ,1149 ,1157 ,1165 ,1174 ,1188 ,1195 ,1207 ,1222 ,1250 ,1262 ,1288 ,1304 ,1338 ,1373 ,1424 ,1538                  },
                        { 629 ,693 ,720 ,741 ,757 ,767 ,775 ,782 ,787 ,790 ,797 ,808 ,810 ,820 ,827 ,835 ,843 ,845 ,849 ,854 ,863 ,871 ,877 ,880 ,888 ,890 ,895 ,902 ,908 ,911 ,917 ,921 ,928 ,930 ,938 ,943 ,945 ,949 ,958 ,1083 ,1086 ,1088 ,1094 ,1090 ,1104 ,1108 ,1111 ,1114 ,1121 ,1127 ,1131 ,1020 ,1021 ,1030 ,1032 ,1038 ,1042 ,1047 ,1040 ,1052 ,1060 ,1068 ,1072 ,1077 ,1082 ,1088 ,1098 ,1101 ,1108 ,1113 ,1119 ,1123 ,1134 ,1145 ,1154 ,1159 ,1170 ,1177 ,1181 ,1195 ,1199 ,1205 ,1213 ,1224 ,1238 ,1248 ,1260 ,1272 ,1284 ,1302 ,1328 ,1342 ,1355 ,1387 ,1408 ,1435 ,1450 ,1483 ,1668    },
                        { 718 ,737 ,745 ,748 ,749 ,753 ,779 ,802 ,817 ,821 ,823 ,829 ,838 ,842 ,853 ,855 ,869 ,875 ,888 ,890 ,894 ,897 ,899 ,905 ,907 ,909 ,917 ,918 ,923 ,937 ,941 ,948 ,950 ,955 ,958 ,1083 ,1084 ,1088 ,1088 ,1094 ,1098 ,1101 ,1105 ,1108 ,1112 ,1118 ,1122 ,1137 ,1020 ,1028 ,1030 ,1032 ,1035 ,1045 ,1054 ,1063 ,1068 ,1078 ,1093 ,1112 ,1122 ,1128 ,1131 ,1133 ,1137 ,1161 ,1165 ,1143 ,1172 ,1179 ,1185 ,1192 ,1195 ,1198 ,1202 ,1208 ,1208 ,1219 ,1228 ,1232 ,1251 ,1259 ,1270 ,1281 ,1295 ,1265 ,1318 ,1328 ,1338 ,1349 ,1362 ,1398 ,1401 ,1434 ,1440 ,1501 ,1548 ,1678 ,1857}
                }
        };


        iarray_age = iage_array[igender];
        for(iLoop = 0; iLoop < iarray_age.length; iLoop++)
        {
            if(iage < iarray_age[iLoop])
            {
                break;
            }
        }

        if(iLoop >= iage_array[igender].length)
        {
            return fgrade;
        }

        iarray_sec = isec_array[igender][iLoop];
        isec = ExerciseData.mCaculate_Data.itotal_time;
        for(iLoop = 0; iLoop < iarray_sec.length; iLoop++)
        {
            if(isec <= iarray_sec[iLoop])
            {
                break;
            }
        }
        if(iLoop <= 99)
        {
            fgrade = 99 - iLoop;
        }

        return fgrade;
    }


    private String s_get_grade()
    {
        String sdata;
        float fval;

        switch (ExerciseData.E_mode)
        {
            case PROC_EXERCISE_PHY_USMC    :
                fval = f_usmc_grade();
                break;
            case PROC_EXERCISE_PHY_ARMY    :
                fval = f_army_grade();
                break;
            case PROC_EXERCISE_PHY_NAVY    :
                fval = f_navy_grade();
                break;
            case PROC_EXERCISE_PHY_USAF    :
                fval = f_usaf_grade();
                break;
            case PROC_EXERCISE_PHY_FEDERAL :
                fval = f_federal_grade();
                break;
            default:
                fval = 0;
                break;
        }
        sdata = Rtx_TranslateValue.sFloat2String(fval, 0);

        return sdata;
    }

    private int i_get_complete_color()
    {
        int icolor;

        switch (ExerciseData.E_mode)
        {
            case PROC_EXERCISE_PHY_USAF    :
                if(b_get_complete()) {
                    switch (i_usaf_risklevel()) {
                        case 0:
                            icolor = icolor_low_risk;
                            break;
                        case 1:
                            icolor = icolor_moderate_risk;
                            break;
                        default:
                            icolor = icolor_high_risk;
                            break;
                    }
                }
                else {
                    icolor = icolor_imcom;
                }
                break;
            default:
                if(b_get_complete()) {
                    icolor = icolor_com;
                }
                else {
                    icolor = icolor_imcom;
                }

                break;
        }

        return icolor;
    }

    private String s_get_complete_string()
    {
        String sdata;

        switch (ExerciseData.E_mode)
        {
            case PROC_EXERCISE_PHY_USAF    :
                if(b_get_complete()) {
                    switch (i_usaf_risklevel()) {
                        case 0:
                            sdata = LanguageData.s_get_string(mContext, R.string.low_risk);
                            break;
                        case 1:
                            sdata = LanguageData.s_get_string(mContext, R.string.moderate_risk);
                            break;
                        default:
                            sdata = LanguageData.s_get_string(mContext, R.string.high_risk);
                            break;
                    }
                }
                else {
                    sdata = LanguageData.s_get_string(mContext, R.string.imcomplete);
                }
                break;
            default:
                if(b_get_complete()) {
                    sdata = LanguageData.s_get_string(mContext, R.string.complete);
                }
                else {
                    sdata = LanguageData.s_get_string(mContext, R.string.imcomplete);
                }
                break;
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
