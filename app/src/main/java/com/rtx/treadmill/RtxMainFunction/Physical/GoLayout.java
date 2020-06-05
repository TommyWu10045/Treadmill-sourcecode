package com.rtx.treadmill.RtxMainFunction.Physical;

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

    private int istr_list[][];

    private int imode = 0;
    private float fweight;
    private int igender;
    private float fage;

    private int imax = 2;

    //Gerkin mode
    float[][] fgerkin = {
            { 180  ,   60  ,   60  ,   60 ,  60 ,  60 ,  60 ,  60 ,  60 ,  60 ,  60 ,  60  }, //time sec
            //{ 10  ,   10  ,   10  ,   10 ,  10 ,  10 ,  10 ,  10 ,  10 ,  10 ,  10 ,  10  }, //time sec
            { 4.8f ,  7.2f ,  7.2f ,   8f ,  8f , 8.8f, 8.8f, 9.6f, 9.6f,10.4f,10.4f,11.2f }, //speed kph
            {    0 ,    0  ,    2  ,   2  ,  4  ,  4  ,  6  ,  6  ,  8  ,  8  , 10  ,  10  }  //incline%
    };

    //cooper mode
    float fcooper = 720f; //time sec
    //usmc mode
    float fusmc = 4.8f; //distance meter
    //army mode
    float farmy = 3.2f; //distance meter
    //navy mode
    float fnavy = 2.4f; //distance meter
    //usaf mode
    float fusaf = 2.4f; //distance meter
    //federal mode
    float ffederal = 2.4f; //distance meter

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

        istr_list = mMainActivity.mMainProcTreadmill.physicalProc.itype_tread_list;

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

            if(iLoop == 0)
            {
                suart_cmd_orig.bcal = false;
            }
            else {
                suart_cmd_orig.bcal = true;
            }

            suart_cmd_orig.isec = (int)farray[0][iLoop];
            suart_cmd_orig.fspeed = farray[1][iLoop];
            suart_cmd_orig.fincline = farray[2][iLoop];

            ExerciseData.list_original_setting.add(suart_cmd_orig);
        }

        ExerciseGenfunc.v_Exercise_Keep();

        ExerciseGenfunc.v_set_target_limit(80, 220);
        ExerciseGenfunc.v_set_target_heart_rate(fheartrate);

        HeartRateControl.iHR_Set_status(0x01);
        HeartRateControl.v_Set_HR_interval1_Timeout(0);
    }

    private void v_Create_cooper()
    {
        int iLoop ;
        int istep_sec = ExerciseData.iStep_time*1440;
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

        ExerciseGenfunc.v_set_target_limit(600, 7200);
        ExerciseGenfunc.v_set_target_time((int)fcooper);

    }

    private void v_Create_Distance(float fdis)
    {
        int iLoop ;
        int istep_sec = ExerciseData.iStep_time*1440;
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

        ExerciseGenfunc.v_set_target_limit(1000, 50000);
        ExerciseGenfunc.v_set_target_distance(fdis);

    }

    private void v_Create_usmc(float fdis)
    {
        v_Create_Distance(fdis);
        int[][] iusmcsec_array = {
                {1660},//female : igender = 0
                {1480}//male : igender = 1
        };
        if(ExerciseData.igender < iusmcsec_array.length) {
            ExerciseData.mCaculate_Data.ioptimal_time = iusmcsec_array[ExerciseData.igender][0];
        }

    }

    private void v_Create_army(float fdis)
    {
        int iLoop;

        v_Create_Distance(fdis);
        int[][] iarmyage_array = {
                {17, 22, 27, 32, 37, 42, 47, 52, 57, 62, 999},//female : igender = 0
                {17, 22, 27, 32, 37, 42, 47, 52, 57, 62, 999}//male : igender = 1
        };

        int[][] iarmysec_array = {
                {1134	,	1176	,	1230	,	1302	,	1362,	1422	,	1440	,	1464	,	1488,	1500},//female : igender = 0
                {954	,	996	,	1020	,	1062	,	1098	,	1122	,	1170	,	1188	,	1196	,	1200}//male : igender = 1
        };
        if(ExerciseData.igender < iarmyage_array.length) {
            for(iLoop = 1 ; iLoop < iarmyage_array[ExerciseData.igender].length; iLoop++)
            {
                if(ExerciseData.fage < iarmyage_array[ExerciseData.igender][iLoop])
                {
                    break;
                }
            }
            if(iLoop <= iarmysec_array[ExerciseData.igender].length)
            {
                ExerciseData.mCaculate_Data.ioptimal_time = iarmysec_array[ExerciseData.igender][iLoop-1];
            }
        }

    }

    private void v_Create_navy(float fdis)
    {
        int iLoop;

        v_Create_Distance(fdis);
        int[][] inavyage_array = {
                {17	,	20	,	25	,	30	,	35	,	40	,	45	,	50	,	55	,	60	,	65	,	999},//female : igender = 0
                {20	,	25	,	30	,	35	,	40	,	45	,	50	,	55	,	60	,	65	,	999}//male : igender = 1
        };
        int[][] inavysec_array = {
                {810	,	855	,	897	,	930	,	953,	975	,	990	,	1005	,	1068,	1131,	1196},//female : igender = 0
                {720	,	773	,	825	,	848	,	870,	893	,	915	,	975	    ,	1067,	1093}//male : igender = 1
        };
        if(ExerciseData.igender < inavyage_array.length) {
            for(iLoop = 1 ; iLoop < inavyage_array[ExerciseData.igender].length; iLoop++)
            {
                if(ExerciseData.fage < inavyage_array[ExerciseData.igender][iLoop])
                {
                    break;
                }
            }
            if(iLoop <= inavysec_array[ExerciseData.igender].length)
            {
                ExerciseData.mCaculate_Data.ioptimal_time = inavysec_array[ExerciseData.igender][iLoop-1];
            }
        }

    }

    private void v_Create_usaf(float fdis)
    {
        int iLoop;

        v_Create_Distance(fdis);
        int[][] iusafage_array = {
                {30	,	40	,	50	,	60	,	999},//female : igender = 0
                {30	,	40	,	50	,	60	,	999}//male : igender = 1
        };
        int[][] iusafsec_array = {
                {623	,	651	,	682	,	773	,	840},//female : igender = 0
                {552	,	574	,	585	,	637	,	682}//male : igender = 1
        };
        if(ExerciseData.igender < iusafage_array.length) {
            for(iLoop = 0 ; iLoop < iusafage_array[ExerciseData.igender].length; iLoop++)
            {
                if(ExerciseData.fage < iusafage_array[ExerciseData.igender][iLoop])
                {
                    break;
                }
            }
            if(iLoop < iusafsec_array[ExerciseData.igender].length)
            {
                ExerciseData.mCaculate_Data.ioptimal_time = iusafsec_array[ExerciseData.igender][iLoop];
            }
        }

    }

    private void v_Create_federal(float fdis)
    {
        int iLoop;

        v_Create_Distance(fdis);
        int[][] ifederalage_array = {
                {25	,	30	,	35	,	40	,	45	,	50	,	55	,	999},//female : igender = 0
                {25	,	30	,	35	,	40	,	45	,	50	,	55	,	60	,	999}//male : igender = 1
        };
        int[][] ifederalsec_array = {
                {844	,	890	,	890	,	953	,	996,	1042	,	1121	,	1182},//female : igender = 0
                {697	,	709	,	733	,	760	,	810,	852	,	895	,	963	,	974}//male : igender = 1
        };
        if(ExerciseData.igender < ifederalage_array.length) {
            for(iLoop = 0 ; iLoop < ifederalage_array[ExerciseData.igender].length; iLoop++)
            {
                if(ExerciseData.fage < ifederalage_array[ExerciseData.igender][iLoop])
                {
                    break;
                }
            }
            if(iLoop < ifederalsec_array[ExerciseData.igender].length)
            {
                ExerciseData.mCaculate_Data.ioptimal_time = ifederalsec_array[ExerciseData.igender][iLoop];
            }
        }

    }

    private void v_Update_Run_Data(int itype, float fw, float fval, int igen)
    {
        ExerciseData.clear(); //clear all data
        HeartRateControl.clear();

        ExerciseData.v_set_weight(fw);
        ExerciseData.v_set_age(fval);
        ExerciseData.v_set_gender(igen);

        switch (itype)
        {
            case 0: //gerkin mode
                v_Create_gerkin(fval, fgerkin);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_PHY_GERKIN);
                break;
            case 1: //cooper mode
                v_Create_cooper();
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_PHY_COOPER);
                break;
            case 2: //usmc mode
                v_Create_usmc(fusmc);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_PHY_USMC);
                break;
            case 3: //army mode
                v_Create_army(farmy);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_PHY_ARMY);
                break;
            case 4: //navy mode
                v_Create_navy(fnavy);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_PHY_NAVY);
                break;
            case 5: //usaf mode
                v_Create_usaf(fusaf);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_PHY_USAF);
                break;
            case 6: //federal mode
                v_Create_federal(ffederal);
                ExerciseData.v_Set_All_E_mode(ExerciseRunState.PROC_EXERCISE_PHY_FEDERAL);
                break;
            default:
                break;
        }

        if(itype < 7)
        {
            mMainActivity.mMainProcTreadmill.physicalProc.vSetNextState(PhysicalState.PROC_EXERCISE_CHECK_COUNTDOWN);
            mMainActivity.mMainProcTreadmill.exerciseProc.countdownProc.vSetNextState(CountDownState.PROC_COUNTDOWN_INIT);
            mMainActivity.mMainProcTreadmill.exerciseProc.vSetNextState(ExerciseState.PROC_EXERCISE_COUNTDOWN);
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void vBackPrePage()
    {
        mMainActivity.mMainProcTreadmill.physicalProc.vSetNextState(PhysicalState.PROC_SHOW_GENDER);
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

