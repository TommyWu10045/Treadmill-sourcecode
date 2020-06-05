package com.rtx.treadmill.RtxMainFunction.Physical;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxTools.Rtx_Calendar;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxDoubleStringView;
import com.rtx.treadmill.RtxView.RtxPercentCircleView;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class UsmcLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;

    private ButtonListener mButtonListener;
    private MainActivity        mMainActivity;

    private RtxDoubleStringView[]       t_data;
    private RtxTextView[]       t_info;

    private RtxPercentCircleView mPercentCircle;

    private int imax = 4;

    private int icount = 0;
    private float ftarget ;

    public UsmcLayout(Context context, MainActivity mMainActivity) {
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

        icount = 0;

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
        int iLoop ;

        init_Title();

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

        if(mPercentCircle == null)           { mPercentCircle = new RtxPercentCircleView(mContext); }

    }

    public void init_event()
    {

    }

    public void add_View() {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize, fsize_unit;
        String sdata;
        String sdis = "";

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
        vSetTitleText(sdata.toUpperCase());

//        iLoop 0 circle percent
        ix = 161;
        iy = 355;
        iw = 443 ;
        ih = 160;
        fsize = 150f;
        fsize_unit = 22f;
        sdata = "";
        iLoop = 0;
        addRtxDoubleStringView(t_data[iLoop], ix, iy, iw, ih);
        t_data[iLoop].setGap(20);
        t_data[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.physical_word_pink, fsize, Common.Font.Relay_BlackItalic, Common.Color.physical_word_blue, fsize_unit);
        t_data[iLoop].setText(sdata, "");

        addViewToLayout(mPercentCircle, 126, 178, 518, 518);
        mPercentCircle.setValueBarColor(Common.Color.physical_word_pink);

//       iLoop 1 target distance, 2 time, 3 required time,
        ix = 743;
        iy = 176;
        iw = 406 ;
        ih = 90;
        fsize = 83.36f;

        for(iLoop = 1; iLoop < imax; iLoop++) {
            if(iLoop == 1)
            {
                ftarget = ExerciseGenfunc.f_get_target_distance();
                sdata = EngSetting.Distance.getValString(ExerciseGenfunc.f_get_target_distance_km());
                t_data[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.physical_word_white, fsize, Common.Font.Relay_BlackItalic, Common.Color.physical_word_blue, fsize_unit);
                t_data[iLoop].setText(sdata, EngSetting.Distance.getUnitString(mContext));
            }
            else if(iLoop == 2)
            {
                sdata = Rtx_Calendar.s_trans_integer(3, ExerciseGenfunc.i_get_total_time());
                t_data[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.physical_word_yellow, fsize, Common.Font.Relay_BlackItalic, Common.Color.physical_word_blue, fsize_unit);
                t_data[iLoop].setText(sdata, "");
            }
            else if(iLoop == 3)
            {
                sdata = Rtx_Calendar.s_trans_integer(3, ExerciseData.mCaculate_Data.ioptimal_time);
                t_data[iLoop].setPaint(Common.Font.Relay_Black, Common.Color.physical_word_white, fsize, Common.Font.Relay_BlackItalic, Common.Color.physical_word_blue, fsize_unit);
                t_data[iLoop].setText(sdata, "");
            }
            addRtxDoubleStringView(t_data[iLoop], ix, iy, iw, ih);
            t_data[iLoop].setGap(20);

            iy += 208;

        }

        iy = 266;
        ih = 60;
        fsize = 20f;
        for(iLoop = 1; iLoop < imax; iLoop++) {
            if(iLoop == 1)
            {
                sdata = LanguageData.s_get_string(mContext, R.string.target_distance);
            }
            else if(iLoop == 2)
            {
                sdata = LanguageData.s_get_string(mContext, R.string.time);
            }
            else if(iLoop == 3)
            {
                sdata = LanguageData.s_get_string(mContext, R.string.time_required_60);
            }
            addRtxTextViewToLayout(t_info[iLoop], sdata.toUpperCase(), fsize, Common.Color.physical_word_blue, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
            iy += 208;
        }

        init_ViewMask();
        vVisibleMaskView(true);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void v_refresh_time()
    {
        int icurr = ExerciseGenfunc.i_get_total_time();
        float ftotal = ExerciseGenfunc.f_get_total_distance();

        int iPercent;
        String sdata;

        if(ftarget <= 0)
        {
            iPercent = 0;
        }
        else {
            iPercent = (int) (100f * ftotal / ftarget);
            if(iPercent < 0)
            {
                iPercent = 0;
            }
            else if(iPercent > 100)
            {
                iPercent = 100;
            }
        }

        sdata = Rtx_TranslateValue.sInt2String(iPercent) + "%";
        t_data[0].setText(sdata, "");

        sdata = Rtx_Calendar.s_trans_integer(3, icurr);
        t_data[2].setText(sdata, "");

        mPercentCircle.setPercentValue(iPercent);
    }

    public void Refresh()
    {
        if(icount % EngSetting.DEF_EXERCISE_PHYSICAL_REFRESH == 0)
        {
            v_refresh_time();
        }

        if(icount % (EngSetting.DEF_SEC_COUNT * 2) == 0)
        {
            vVisibleMaskView(false);
        }

        icount++;

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {

        }
    }
}
