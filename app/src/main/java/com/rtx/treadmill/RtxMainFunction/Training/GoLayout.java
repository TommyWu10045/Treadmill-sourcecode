package com.rtx.treadmill.RtxMainFunction.Training;

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

public class GoLayout extends Rtx_BaseLayout {
    private String TAG = "Jerry" ;

    private     Context mContext;

    private     ButtonListener      mButtonListener;
    private     MainActivity        mMainActivity;

    private RtxImageView        i_start;
    private RtxImageView[]      i_tr;
    private RtxTextView[]       t_data;
    private RtxTextView[]       t_unit;

    private int imode = 0;
    private int ilevel = 0;
    private int imax = 2;
    private float[] fdata = new float[imax];

    //hill E/M/H
    float[][] fhill = {
            {1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1},
            {1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 3, 2, 2},
            {2, 3, 3, 4, 4, 5, 5, 5, 6, 6, 7, 7, 7, 8, 9,10, 9, 8, 7, 7, 7, 6, 6, 6, 5, 5, 5, 4, 4, 3}
    };

    //fat burn E/M/H
    float[][] ffat_burn = {
            {0, 1, 1, 1, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
            {1, 2, 3, 3, 4, 4, 4, 4, 4, 5, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 6},
            {1, 2, 3, 4, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 9,10,10,10,10, 8, 8, 7, 7}
    };

    //cardio E/M/H
    float[][] fcardio = {
            {1, 2, 3, 4, 5, 4, 3, 2, 1, 2, 3, 4, 5, 4, 3, 2, 1, 2, 3, 4, 5, 4, 3, 2, 1, 2, 3, 4, 5, 4},
            {1, 3, 4, 5, 7, 5, 4, 3, 1, 3, 4, 5, 7, 5, 4, 3, 1, 3, 4, 5, 7, 5, 4, 3, 1, 3, 4, 5, 7, 5},
            {1, 3, 5, 7, 9, 7, 5, 3, 1, 3, 5, 7, 9, 7, 5, 3, 1, 3, 5, 7, 9, 7, 5, 3, 1, 3, 5, 7, 9, 7}
    };

    //strength E/M/H
    float[][] fstrength = {
            {1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6, 6, 6, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 2, 1},
            {2, 3, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 8, 8, 8, 9, 9, 9, 8, 7, 6, 6, 5, 5, 4, 4, 4, 4, 3},
            {4, 4, 5, 5, 6, 7, 7, 8, 8, 8, 9, 9, 9,10,10,10,11,11,10,10,10, 9, 8, 7, 6, 5, 4, 4, 3, 2}
    };

    //interval E/M/H
    float[][] finterval = {
            {10, 0, 0, 0, 0,10, 0, 0, 0, 0,10, 0, 0, 0, 0,10, 0, 0, 0, 0,10, 0, 0, 0, 0,10, 0, 0, 0, 0},
            {10, 0, 0,10, 0, 0,10, 0, 0,10, 0, 0,10, 0, 0,10, 0, 0,10, 0, 0,10, 0, 0,10, 0, 0,10, 0, 0},
            {10, 0,10, 0,10, 0,10, 0,10, 0,10, 0,10, 0,10, 0,10, 0,10, 0,10, 0,10, 0,10, 0,10, 0,10, 0}
    };

    public GoLayout(Context context, MainActivity mMainActivity) {
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

    public void v_set_data(int imode, int ilevel, float fwei, float ftarget)
    {
        this.imode = imode;
        this.ilevel = ilevel;
        fdata[0] = fwei;
        fdata[1] = ftarget;
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
        int i_target_start  = mMainActivity.mMainProcTreadmill.trainingProc.i_target_start;

//        info
        ix = 420;
        iy = 202;
        iw = 444;
        ih = 444;
        addRtxImagePaddingViewToLayout(i_start, R.drawable.tr_go, ix, iy, iw, ih, 0);

//      weight icon
        ix = 182;
        iy = 316;
        iw = 54 ;
        ih = 60;
        iLoop = 0;
        addRtxImagePaddingViewToLayout(i_tr[iLoop], R.drawable.target_input_weight_icon, ix, iy, iw, ih, 0);

        ix = 0;
        iy += 120;
        iw = 416 ;
        ih = 90;
        fsize = 83.35f;
        fsize_unit = 33.05f;
        if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
        {
            sdata = Rtx_TranslateValue.sFloat2String( EngSetting.Weight.getVal(fdata[iLoop]), 1);
        }
        else
        {
            sdata = Rtx_TranslateValue.sFloat2String( EngSetting.Weight.getVal(fdata[iLoop]), 0);
        }
        addRtxTextViewToLayout(t_data[iLoop], sdata, fsize, Common.Color.hrc_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        ih = 60;
        sdata = EngSetting.Weight.getUnitString(mContext);
        addRtxTextViewToLayout(t_unit[iLoop], sdata.toLowerCase(), fsize_unit, Common.Color.hrc_word_blue, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

//      target icon
        if(imode == i_target_start)
        {
            i_Resid = R.drawable.tr_calories_icon;
            sunit = LanguageData.s_get_string(mContext, R.string.kcal);
        }
        else if(imode == (i_target_start+2))
        {
            i_Resid = R.drawable.tr_distance_icon;
            sunit = EngSetting.Distance.getUnitString(mContext);
        }
        else
        {
            i_Resid = R.drawable.tr_time_icon;
            sunit = LanguageData.s_get_string(mContext, R.string.min);
        }

        ix = 1040;
        iy = 316;
        iw = 62 ;
        ih = 62;
        iLoop = 1;
        addRtxImagePaddingViewToLayout(i_tr[iLoop], i_Resid, ix, iy, iw, ih, 0);

        ix = 863;
        iy += 120;
        iw = 416 ;
        ih = 90;
        if(imode == (i_target_start+2))
        {
            sdata = EngSetting.Distance.getValString(fdata[iLoop]);
        }
        else {
            sdata = Rtx_TranslateValue.sFloat2String(fdata[iLoop], 0);
        }
        addRtxTextViewToLayout(t_data[iLoop], sdata, fsize, Common.Color.hrc_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        ih = 60;
        addRtxTextViewToLayout(t_unit[iLoop], sunit.toLowerCase(), fsize_unit, Common.Color.hrc_word_blue, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void v_Create_profile(float ftime, int ilvl, float[][] farray)
    {
        int iLoop , iIndex;
        int istep_sec = ExerciseData.iStep_time;
        int imax = (int)ftime;

        if(farray == null || farray[0].length <= ilvl)
        {
            return;
        }

        iIndex = 0;
        for(iLoop = 0; iLoop < imax; iLoop++) {
            ExerciseData.DEVICE_COMMAND suart_cmd_orig = new ExerciseData.DEVICE_COMMAND();

            suart_cmd_orig.bcal = true;
            suart_cmd_orig.isec = istep_sec;
            suart_cmd_orig.fspeed = EngSetting.f_Get_Def_Speed();
            suart_cmd_orig.fincline = farray[ilvl][iIndex];

            ExerciseData.list_original_setting.add(suart_cmd_orig);
            iIndex++;
            if(iIndex >= farray[ilvl].length)
            {
                iIndex = 0;
            }
        }
        ExerciseGenfunc.v_Exercise_Keep();

        ExerciseGenfunc.v_set_target_limit(600, 7200);
        ExerciseGenfunc.v_set_target_time(imax * istep_sec);

    }

    private void v_Create_calories(float ftarget)
    {
        int iLoop ;
        int istep_sec = ExerciseData.iStep_time;
        int imax = ExerciseData.ilist_max;

        for(iLoop = 0; iLoop < imax; iLoop++) {
            ExerciseData.DEVICE_COMMAND suart_cmd_orig = new ExerciseData.DEVICE_COMMAND();

            suart_cmd_orig.bcal = true;
            suart_cmd_orig.isec = istep_sec;
            suart_cmd_orig.fspeed = EngSetting.f_Get_Def_Speed();
            suart_cmd_orig.fincline = EngSetting.f_Get_Def_Incline();

            ExerciseData.list_original_setting.add(suart_cmd_orig);
        }
        ExerciseGenfunc.v_Exercise_Keep();

        ExerciseGenfunc.v_set_target_limit(100, 2000);
        ExerciseGenfunc.v_set_target_calorie(ftarget);

    }

    private void v_Create_time(float ftarget)
    {
        int iLoop ;
        int istep_sec = ExerciseData.iStep_time;
        int imax = (int) ftarget;

        for(iLoop = 0; iLoop < imax; iLoop++) {
            ExerciseData.DEVICE_COMMAND suart_cmd_orig = new ExerciseData.DEVICE_COMMAND();

            suart_cmd_orig.bcal = true;
            suart_cmd_orig.isec = istep_sec;
            suart_cmd_orig.fspeed = EngSetting.f_Get_Def_Speed();
            suart_cmd_orig.fincline = EngSetting.f_Get_Def_Incline();

            ExerciseData.list_original_setting.add(suart_cmd_orig);
        }
        ExerciseGenfunc.v_Exercise_Keep();

        ExerciseGenfunc.v_set_target_limit(600, 7200);
        ExerciseGenfunc.v_set_target_time((int)ftarget * istep_sec);

    }

    private void v_Create_distance(float ftarget)
    {
        int iLoop ;
        int istep_sec = ExerciseData.iStep_time;
        int imax = ExerciseData.ilist_max;

        for(iLoop = 0; iLoop < imax; iLoop++) {
            ExerciseData.DEVICE_COMMAND suart_cmd_orig = new ExerciseData.DEVICE_COMMAND();

            suart_cmd_orig.bcal = true;
            suart_cmd_orig.isec = istep_sec;
            suart_cmd_orig.fspeed = EngSetting.f_Get_Def_Speed();
            suart_cmd_orig.fincline = EngSetting.f_Get_Def_Incline();

            ExerciseData.list_original_setting.add(suart_cmd_orig);
        }
        ExerciseGenfunc.v_Exercise_Keep();


        if(EngSetting.getUnit() == EngSetting.UNIT_METRIC)
        {
            //SPEC 1km ~ 50km
            ExerciseGenfunc.v_set_target_limit(1000, 50000);
        }
        else
        {
            //SPEC 1mi ~ 30mi
            ExerciseGenfunc.v_set_target_limit(1000 * EngSetting.mile2km, 30000 * EngSetting.mile2km);
        }
        ExerciseGenfunc.v_set_target_distance(ftarget);

    }

    private void v_Update_Run_Data(int itype, int ilvl, float fw, float ftarget)
    {
        int iLoop;

        ExerciseData.clear(); //clear all data
        HeartRateControl.clear();

        ExerciseData.v_set_weight(fw);

        switch (itype)
        {
            case 0x00: //manual mode
                v_Create_time(ftarget);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_TR_MANUAL);
                break;
            case 0x01: //hill mode
                v_Create_profile(ftarget, ilvl, fhill);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_TR_HILL);
                break;
            case 0x02: //fatburn mode
                v_Create_profile(ftarget, ilvl, ffat_burn);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_TR_FATBURN);
                break;
            case 0x03: //cardio mode
                v_Create_profile(ftarget, ilvl, fcardio);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_TR_CARDIO);
                break;
            case 0x04: //strenght mode
                v_Create_profile(ftarget, ilvl, fstrength);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_TR_STRENGHT);
                break;
            case 0x05: //interval mode
                v_Create_profile(ftarget, ilvl, finterval);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_TR_INTERVAL);
                break;
            case 0x0a: //calories mode
                v_Create_calories(ftarget);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_TR_CALORIES);
                break;
            case 0x0b: //time mode
                v_Create_time(ftarget);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_TR_TIME);
                break;
            case 0x0c: //distance mode
                v_Create_distance(ftarget);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_TR_DISTANCE);
                break;
            default:
                break;
        }

        mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_EXERCISE_CHECK_COUNTDOWN);
        mMainActivity.mMainProcTreadmill.exerciseProc.countdownProc.vSetNextState(CountDownState.PROC_COUNTDOWN_INIT);
        mMainActivity.mMainProcTreadmill.exerciseProc.vSetNextState(ExerciseState.PROC_EXERCISE_COUNTDOWN);

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void vBackPrePage()
    {
        int iback = mMainActivity.mMainProcTreadmill.trainingProc.i_target_start;
        if(imode == iback)
        {
            mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_SHOW_CALORIES);
        }
        else if(imode == (iback + 1) || imode == 0)
        {
            mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_SHOW_TIME);
        }
        else if(imode == (iback + 2))
        {
            mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_SHOW_DISTANCE);
        }
        else
        {
            mMainActivity.mMainProcTreadmill.trainingProc.vSetNextState(TrainingState.PROC_SHOW_LEVEL);
        }

    }

    private void vConfirm()
    {
        v_Update_Run_Data(imode, ilevel, fdata[0], fdata[1]);
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

