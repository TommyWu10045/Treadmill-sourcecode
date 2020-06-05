package com.rtx.treadmill.RtxMainFunction.HeartRate;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.ExerciseRunState;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunction.Exercise.CountDown.CountDownState;
import com.rtx.treadmill.RtxMainFunction.Exercise.ExerciseState;
import com.rtx.treadmill.RtxMainFunction.Exercise.HeartRateControl;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class StartLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     ButtonListener      mButtonListener;
    private     MainActivity        mMainActivity;

    private RtxImageView        i_start;
    private RtxImageView[]      i_hrc;
    private RtxTextView[]       t_date;
    private RtxTextView[]       t_unit;


    public int istr_list[] =  {
            R.drawable.target_input_weight_icon,       R.drawable.hrc_heart,       R.drawable.hrc_time,     R.drawable.hrc_age
    };

    int imax = istr_list.length;

    private float[] fdata = new float[imax];

    public StartLayout(Context context, MainActivity mMainActivity) {
        super(context);

        mContext = context;

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

    public void v_set_data(float fwei, float ftarget, float ftime, float fage)
    {
        fdata[0] = fwei;
        fdata[1] = ftarget;
        fdata[2] = ftime;
        fdata[3] = fage;
    }

    private void init_View()
    {
        int iLoop ;

        init_BackPrePage();
        init_Title();

        if(i_start == null)    { i_start = new RtxImageView(mContext);     }

        if(i_hrc == null) {
            i_hrc = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                i_hrc[iLoop] = new RtxImageView(mContext);
            }
        }

        if(t_date == null) {
            t_date = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_date[iLoop] = new RtxTextView(mContext);
            }
        }

        if(t_unit == null) {
            t_unit = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_unit[iLoop] = new RtxTextView(mContext);
            }
        }

    }

    private void init_event()
    {
        imageView_BackPrePage.setOnClickListener(mButtonListener);
        i_start.setOnClickListener(mButtonListener);
    }

    private void add_View()
    {
        int iLoop;
        int ix, iy, iw, ih;
        float fsize;
        float fsize_unit;
        String sdata;

//        menu
        sdata = LanguageData.s_get_string(mContext, R.string.start);
        vSetTitleText(sdata.toUpperCase());

//        info
        ix = 420;
        iy = 218;
        iw = 456;
        ih = 456;
        addRtxImagePaddingViewToLayout(i_start, R.drawable.hrc_start, ix, iy, iw, ih, 0);

        fsize = 83.35f;
        fsize_unit = 33.05f;

//      age icon
        ix = 183;
        iy = 186;
        iw = 61 ;
        ih = 61;
        iLoop = 3;
        addRtxImage(null, i_hrc[iLoop], istr_list[iLoop], ix, iy, iw, ih, 0, ImageView.ScaleType.CENTER_INSIDE);

        ix = 0;
        iy += 100;
        iw = 416 ;
        ih = 90;
        sdata = Rtx_TranslateValue.sFloat2String(fdata[iLoop], 0);
        addRtxTextViewToLayout(t_date[iLoop], sdata, fsize, Common.Color.hrc_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        ih = 60;
        sdata = HeartRateFunc.get_unit(mContext, R.string.years);
        addRtxTextViewToLayout(t_unit[iLoop], sdata.toLowerCase(), fsize_unit, Common.Color.hrc_word_blue, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

//      weight icon
        ix = 183;
        iy = 515;
        iw = 61 ;
        ih = 61;
        iLoop = 0;
        addRtxImage(null, i_hrc[iLoop], istr_list[iLoop], ix, iy, iw, ih, 0, ImageView.ScaleType.CENTER_INSIDE);

        ix = 0;
        iy += 100;
        iw = 416 ;
        ih = 90;
        if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
        {
            sdata = Rtx_TranslateValue.sFloat2String( EngSetting.Weight.getVal(fdata[iLoop]), 1);
        }
        else
        {
            sdata = Rtx_TranslateValue.sFloat2String( EngSetting.Weight.getVal(fdata[iLoop]), 0);
        }

        addRtxTextViewToLayout(t_date[iLoop], sdata, fsize, Common.Color.hrc_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        ih = 60;
        sdata = HeartRateFunc.get_mass_unit(mContext);
        addRtxTextViewToLayout(t_unit[iLoop], sdata.toLowerCase(), fsize_unit, Common.Color.hrc_word_blue, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

//      time icon
        ix = 1040;
        iy = 183;
        iw = 61 ;
        ih = 61;
        iLoop = 2;
        addRtxImage(null, i_hrc[iLoop], istr_list[iLoop], ix, iy, iw, ih, 0, ImageView.ScaleType.CENTER_INSIDE);

        ix = 863;
        iy += 100;
        iw = 416 ;
        ih = 90;
        sdata = Rtx_TranslateValue.sFloat2String(fdata[iLoop], 0);
        addRtxTextViewToLayout(t_date[iLoop], sdata, fsize, Common.Color.hrc_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        ih = 60;
        sdata = HeartRateFunc.get_unit(mContext, R.string.min);
        addRtxTextViewToLayout(t_unit[iLoop], sdata.toLowerCase(), fsize_unit, Common.Color.hrc_word_blue, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

//      heart icon
        ix = 1040;
        iy = 515;
        iw = 61 ;
        ih = 61;
        iLoop = 1;
        addRtxImage(null, i_hrc[iLoop], istr_list[iLoop], ix, iy, iw, ih, 0, ImageView.ScaleType.CENTER_INSIDE);

        ix = 863;
        iy += 100;
        iw = 416 ;
        ih = 90;
        sdata = Rtx_TranslateValue.sFloat2String(fdata[iLoop], 0);
        addRtxTextViewToLayout(t_date[iLoop], sdata, fsize, Common.Color.hrc_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        ih = 60;
        sdata = HeartRateFunc.get_unit(mContext, R.string.bpm);
        addRtxTextViewToLayout(t_unit[iLoop], sdata.toLowerCase(), fsize_unit, Common.Color.hrc_word_blue, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

    }


    /////////////////////////////////////////////////////////////////////////////
    private void v_Update_Run_Data(float fw, float ftarget, float ftime, float fage)
    {
        int iLoop;
        int imode = mMainActivity.mMainProcTreadmill.heartrateProc.i_Get_mode();
        int[] idata;

        ExerciseData.clear(); //clear all data
        HeartRateControl.clear();

        ExerciseData.v_set_weight(fw);

        int istep_sec = ExerciseData.iStep_time;
        int imax = (int)ftime;

        for(iLoop = 0; iLoop < imax; iLoop++) {
            ExerciseData.DEVICE_COMMAND suart_cmd_orig = new ExerciseData.DEVICE_COMMAND();

            suart_cmd_orig.bcal = true;
            suart_cmd_orig.isec = istep_sec;
            suart_cmd_orig.fspeed = EngSetting.f_Get_Def_Speed();
            suart_cmd_orig.fincline = EngSetting.f_Get_Def_Incline();

            ExerciseData.list_original_setting.add(suart_cmd_orig);
        }
        ExerciseGenfunc.v_Exercise_Keep();

        //心跳的上下限
        idata = HeartRateFunc.v_calculate_bpm(imode, fage);

        ExerciseGenfunc.v_set_target_limit(idata[0], idata[1]);
        ExerciseGenfunc.v_set_target_heart_rate(ftarget);
        HeartRateControl.iHR_Set_status(0x01);

        ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_HEARTRATE);
        mMainActivity.mMainProcTreadmill.heartrateProc.vSetNextState(HeartRateState.PROC_EXERCISE_CHECK_COUNTDOWN);
        mMainActivity.mMainProcTreadmill.exerciseProc.countdownProc.vSetNextState(CountDownState.PROC_COUNTDOWN_INIT);
        mMainActivity.mMainProcTreadmill.exerciseProc.vSetNextState(ExerciseState.PROC_EXERCISE_COUNTDOWN);


    }

    /////////////////////////////////////////////////////////////////////////////
    private void vBackPrePage()
    {
        mMainActivity.mMainProcTreadmill.heartrateProc.vSetNextState(HeartRateState.PROC_SHOW_TIME);
    }

    private void vConfirm()
    {
        v_Update_Run_Data(fdata[0], fdata[1], fdata[2], fdata[3]);
    }

    class ButtonListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if(v == imageView_BackPrePage)                      { vBackPrePage(); }
            else if(v == i_start)                     { vConfirm(); }
        }
    }

}

