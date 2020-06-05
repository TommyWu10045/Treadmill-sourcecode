package com.rtx.treadmill.RtxMainFunctionBike.Physical;

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
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.CountDown.CountDownState;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.ExerciseState;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.HeartRateControl;
import com.rtx.treadmill.RtxMainFunctionBike.Exercise.WattControl;
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

    private int istr_list[][];

    private int imode = 0;
    private float fweight;
    private int igender;
    private float fage;

    private int imax = 2;

    //fvo2
    float[][] fvo2 = {
            { 180  ,   180  ,   180  ,   180 ,  180 ,  180 ,  180 ,  180 ,  180 ,  180   }, //time sec
            {    2 ,     2  ,     2  ,    2  ,   2  ,   2  ,   2  ,   2  ,    2 ,    2   }  //level
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

    public void v_set_data(int imode, float fwei, int igender, float fage)
    {
        this.imode = imode;
        this.fweight = fwei;
        this.igender = igender;
        this.fage = fage;

    }

    private void init_View()
    {
        int iLoop ;

        istr_list = mMainActivity.mMainProcBike.physicalProc.itype_tread_list;

        init_BackPrePage();
        init_Title();

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
        int imax = istr_list.length;


//        menu
        if(imode < imax)
        {
            sdata = LanguageData.s_get_string(mContext, istr_list[imode][1]);
        }
        else
        {
            sdata = "";
        }
        vSetTitleText(sdata);

//        info
        ix = 420;
        iy = 202;
        iw = 444;
        ih = 444;
        addRtxImagePaddingViewToLayout(i_start, R.drawable.hrc_start, ix, iy, iw, ih, 0);

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
            sdata = Rtx_TranslateValue.sFloat2String( EngSetting.Weight.getVal(fweight), 1);
        }
        else
        {
            sdata = Rtx_TranslateValue.sFloat2String( EngSetting.Weight.getVal(fweight), 0);
        }
        addRtxTextViewToLayout(t_data[iLoop], sdata, fsize, Common.Color.physical_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        ih = 60;
        sdata = EngSetting.Weight.getUnitString(mContext);
        addRtxTextViewToLayout(t_unit[iLoop], sdata.toLowerCase(), fsize_unit, Common.Color.physical_word_blue, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

//      target icon
        if(igender == 0)
        {
            i_Resid = R.drawable.phy_female;
        }
        else
        {
            i_Resid = R.drawable.phy_male;
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
        sdata = Rtx_TranslateValue.sFloat2String(fage, 0);
        addRtxTextViewToLayout(t_data[iLoop], sdata, fsize, Common.Color.physical_word_white, Common.Font.Relay_Black, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);

        iy += ih;
        ih = 60;
        sunit = LanguageData.s_get_string(mContext, R.string.years);
        addRtxTextViewToLayout(t_unit[iLoop], sunit.toLowerCase(), fsize_unit, Common.Color.physical_word_blue, Common.Font.Relay_BoldItalic, Typeface.NORMAL, Gravity.CENTER, ix, iy, iw, ih);


    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void v_Create_gerkin(float fage, float[][] farray)
    {
        int iLoop ;
        float fheartrate ;

        if(farray == null || farray[0].length <= 0)
        {
            return;
        }

        fheartrate = ((float)220 - fage) * (float)0.85;

        for(iLoop = 0; iLoop < farray[0].length; iLoop++) {
            ExerciseData.DEVICE_COMMAND suart_cmd_orig = new ExerciseData.DEVICE_COMMAND();

            suart_cmd_orig.bcal = true;
            suart_cmd_orig.isec = (int)farray[0][iLoop];
            suart_cmd_orig.fspeed = EngSetting.f_Get_Def_Speed();
            suart_cmd_orig.fincline = farray[1][iLoop];

            ExerciseData.list_original_setting.add(suart_cmd_orig);
        }

        ExerciseGenfunc.v_Exercise_Keep();

        ExerciseGenfunc.v_set_target_limit(80, 220);
        ExerciseGenfunc.v_set_target_heart_rate(fheartrate);

    }

    private void v_Update_Run_Data_HRControll()
    {

        HeartRateControl.iHR_Set_status(0x01);
    }

    private void v_Update_Run_Data_WattControll()
    {
        float[] farray_watt = {0f, 55f};
        float[] fdiff_val = {10f, 10f};
        float[] fdiff_level = {1f, 1f};

        WattControl.clear();
        WattControl.fvo2_target_watt.add(farray_watt);
        WattControl.v_Set_Watt_target(farray_watt[1]);
        WattControl.v_Set_Watt_target_diff(fdiff_val);
        WattControl.v_Set_Watt_target_level(fdiff_level);

        WattControl.v_Set_Watt_interval1_Timeout(10000);
        WattControl.v_Set_Watt_interval2_Timeout(20000);
        WattControl.v_Set_Watt_interval3_Timeout(2000);
        WattControl.v_Set_Watt_interval3_RPM_time(1000);

    }

    private void v_Update_Run_Data(int itype, float fw, float fval, int igen)
    {

        ExerciseData.clear(); //clear all data
        HeartRateControl.clear();

        v_Update_Run_Data_HRControll();
        v_Update_Run_Data_WattControll();

        ExerciseData.v_set_weight(fw);
        ExerciseData.v_set_age(fval);
        ExerciseData.v_set_gender(igen);

        v_Create_gerkin(fval, fvo2);
        ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_PHY_GERKIN);
        mMainActivity.mMainProcBike.physicalProc.vSetNextState(PhysicalState.PROC_EXERCISE_CHECK_COUNTDOWN);

        mMainActivity.mMainProcBike.exerciseProc.countdownProc.vSetNextState(CountDownState.PROC_COUNTDOWN_INIT);
        mMainActivity.mMainProcBike.exerciseProc.vSetNextState(ExerciseState.PROC_EXERCISE_COUNTDOWN);

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void vBackPrePage()
    {
        mMainActivity.mMainProcBike.physicalProc.vSetNextState(PhysicalState.PROC_SHOW_GENDER);
    }

    private void vConfirm()
    {
        v_Update_Run_Data(imode, fweight, fage, igender);
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

