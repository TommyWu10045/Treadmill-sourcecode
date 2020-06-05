package com.rtx.treadmill.RtxMainFunctionBike.Training;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.ExerciseRunState;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_BaseLayout;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.HeartRateControl;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.CountDown.CountDownState;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.ExerciseState;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.WattControl;
import com.rtx.treadmill.RtxTools.ExerciseGenfunc;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxImageView;
import com.rtx.treadmill.RtxView.RtxTextView;


/**
 * Created by chasechang on 3/22/17.
 */

public class GoWattLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     ButtonListener      mButtonListener;
    private     MainActivity        mMainActivity;

    private RtxImageView        i_start;
    private RtxImageView[]      i_tr;
    private RtxTextView[]       t_data;
    private RtxTextView[]       t_unit;

    private int imode = 0;
    private int imax = 3;
    private float[] fdata = new float[imax];

    public GoWattLayout(Context context, MainActivity mMainActivity) {
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

    public void v_set_data(int imode, float fwei, float ftime, float fwatt)
    {
        this.imode = imode;
        fdata[0] = fwei;
        fdata[1] = ftime;
        fdata[2] = fwatt;
    }

    private void init_View()
    {
        int iLoop ;

        init_BackPrePage();

        if(i_start == null)    { i_start = new RtxImageView(mContext);     }

        if(i_tr == null) {
            i_tr = new RtxImageView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                i_tr[iLoop] = new RtxImageView(mContext);
            }
        }

        if(t_data == null) {
            t_data = new RtxTextView[imax];
            for (iLoop = 0; iLoop < imax; iLoop++) {
                t_data[iLoop] = new RtxTextView(mContext);
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
        String sdata, sunit;
        int i_Resid;
        int i_target_start  = mMainActivity.mMainProcBike.trainingProc.i_target_start;

//        info
        ix = 420;
        iy = 202;
        iw = 444;
        ih = 444;
        addRtxImagePaddingViewToLayout(i_start, R.drawable.tr_go, ix, iy, iw, ih, 0);

//      weight icon
        iLoop = 0;
        i_Resid = R.drawable.target_input_weight_icon;
        if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
        {
            sdata = Rtx_TranslateValue.sFloat2String( EngSetting.Weight.getVal(fdata[iLoop]), 1);
        }
        else
        {
            sdata = Rtx_TranslateValue.sFloat2String( EngSetting.Weight.getVal(fdata[iLoop]), 0);
        }
        sunit = EngSetting.Weight.getUnitString(mContext);

        ix = 182;
        iy = 156;
        iw = 54 ;
        ih = 60;
        addRtxImagePaddingViewToLayout(i_tr[iLoop], i_Resid, ix, iy, iw, ih, 0);

        ix = 0;
        iy += 120;
        iw = 416 ;
        ih = 90;
        fsize = 83.35f;
        fsize_unit = 33.05f;
        addRtxTextViewToLayout(t_data[iLoop], sdata, fsize, Common.Color.hrc_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        ih = 60;
        addRtxTextViewToLayout(t_unit[iLoop], sunit.toLowerCase(), fsize_unit, Common.Color.hrc_word_blue, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

//      time icon
        iLoop = 1;
        i_Resid = R.drawable.tr_time_icon;
        sdata = Rtx_TranslateValue.sFloat2String(fdata[iLoop], 0);
        sunit = LanguageData.s_get_string(mContext, R.string.min);

        ix = 182;
        iy = 466;
        iw = 54 ;
        ih = 60;
        addRtxImagePaddingViewToLayout(i_tr[iLoop], i_Resid, ix, iy, iw, ih, 0);

        ix = 0;
        iy += 120;
        iw = 436 ;
        ih = 90;
        fsize = 83.35f;
        fsize_unit = 33.05f;
        addRtxTextViewToLayout(t_data[iLoop], sdata, fsize, Common.Color.hrc_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        ih = 60;
        sdata = EngSetting.Weight.getUnitString(mContext);
        addRtxTextViewToLayout(t_unit[iLoop], sunit.toLowerCase(), fsize_unit, Common.Color.hrc_word_blue, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

//      watt icon
        iLoop = 2;
        i_Resid = R.drawable.input_watt_icon;
        sdata = Rtx_TranslateValue.sFloat2String(fdata[iLoop], 0);
        sunit = LanguageData.s_get_string(mContext, R.string.watt);

        ix = 1040;
        iy = 316;
        iw = 62 ;
        ih = 62;
        addRtxImagePaddingViewToLayout(i_tr[iLoop], i_Resid, ix, iy, iw, ih, 0);

        ix = 863;
        iy += 120;
        iw = 416 ;
        ih = 90;
        addRtxTextViewToLayout(t_data[iLoop], sdata, fsize, Common.Color.hrc_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        ih = 60;
        addRtxTextViewToLayout(t_unit[iLoop], sunit.toLowerCase(), fsize_unit, Common.Color.hrc_word_blue, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void v_Create_constantwatt(float fwatt, float ftime)
    {
        int iLoop ;
        int istep_sec = ExerciseData.iStep_time;
        int imax = (int) ftime;
        float[] fdiff_val = {20f, 20f};
        float[] fdiff_level = {1f, 1f};

        WattControl.clear();

        WattControl.v_Set_ConstantWatt_level(EngSetting.f_Get_Def_level());
        WattControl.v_Set_Watt_target_diff(fdiff_val);
        WattControl.v_Set_Watt_target_level(fdiff_level);

        WattControl.v_Set_Watt_interval1_Timeout(10000);
        WattControl.v_Set_Watt_interval2_Timeout(30000);
        WattControl.v_Set_Watt_interval3_Timeout(10000);
        WattControl.v_Set_Watt_interval3_RPM_time(1000);

        WattControl.v_Set_Watt_Status(0x01);

        for(iLoop = 0; iLoop < imax; iLoop++) {
            ExerciseData.DEVICE_COMMAND suart_cmd_orig = new ExerciseData.DEVICE_COMMAND();

            suart_cmd_orig.bcal = true;
            suart_cmd_orig.isec = istep_sec;
            suart_cmd_orig.fspeed = EngSetting.f_Get_Def_Speed();
            suart_cmd_orig.fincline = EngSetting.f_Get_Def_level();

            ExerciseData.list_original_setting.add(suart_cmd_orig);
        }
        ExerciseGenfunc.v_Exercise_Keep();

        ExerciseGenfunc.v_set_target_limit(50, 400);
        ExerciseGenfunc.v_set_target_watt(fwatt);

    }

    private void v_Update_Run_Data(int itype, float fw, float ftime, float fwatt)
    {
        int iLoop;

        ExerciseData.clear(); //clear all data
        HeartRateControl.clear();

        ExerciseData.v_set_weight(fw);

       //conswatt mode
        v_Create_constantwatt(fwatt, ftime);
        ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_TR_CONSWATT);

        mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_EXERCISE_CHECK_COUNTDOWN);
        mMainActivity.mMainProcBike.exerciseProc.countdownProc.vSetNextState(CountDownState.PROC_COUNTDOWN_INIT);
        mMainActivity.mMainProcBike.exerciseProc.vSetNextState(ExerciseState.PROC_EXERCISE_COUNTDOWN);

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void vBackPrePage()
    {
        mMainActivity.mMainProcBike.trainingProc.vSetNextState(TrainingState.PROC_SHOW_WATT);
    }

    private void vConfirm()
    {
        v_Update_Run_Data(imode, fdata[0], fdata[1], fdata[2]);
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

