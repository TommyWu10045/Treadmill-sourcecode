package com.rtx.treadmill.RtxMainFunction.Physical;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxMainFunction.BaseLayout.SummaryBaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxProfile;
import com.rtx.treadmill.RtxView.RtxTextView;

/**
 * Created by chasechang on 3/22/17.
 */

public class GerkinFinishLayout extends SummaryBaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;
    private MainActivity        mMainActivity;

    private RtxTextView       t_complete;
    private RtxTextView       t_grade;

    private RtxTextView       t_score;

    private RtxProfile fprofile;

    private int icolor_com = Common.Color.physical_word_green;
    private int icolor_imcom = Common.Color.physical_word_pink;

    private int iMachine_mode = 0; //0 : treadmill; etc : bike

    public GerkinFinishLayout(Context context, MainActivity mMainActivity) {
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

        if(fprofile == null)    { fprofile = new RtxProfile(mContext);     }
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
        float fsize;
        int icolor;

        //        menu
        sdata = LanguageData.s_get_string(mContext, R.string.summary) + " - " +LanguageData.s_get_string(mContext, R.string.gerkin_protocol);
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

        //Profile Frame
        ix = 450;
        iy = 140;
        iw = 800;
        ih = 400;
        addViewToLayout(fprofile, ix, iy, iw, ih);
        ExerciseData.ilist_virtual_num = 20;
        fprofile.i_profile.vSet_mode(iMachine_mode);
        fprofile.i_profile.vSet_limit(iGet_YMin(), iGet_YMax());

        fprofile.i_yprofile.vSet_limit(iGet_YMin(), iGet_YMax());
        fprofile.init(0.7f);

        fprofile.t_speed.setText(LanguageData.s_get_string(mContext,R.string.speed));
        fprofile.i_speed.setImageResource(R.drawable.speed_icon);

        fprofile.t_speed.setVisibility(INVISIBLE);
        fprofile.i_speed.setVisibility(INVISIBLE);
        fprofile.t_incline.setVisibility(INVISIBLE);
        fprofile.i_incline.setVisibility(INVISIBLE);
        fprofile.t_min.setVisibility(INVISIBLE);
        fprofile.i_profile.vSet_iIncline_mode(0x01);
        fprofile.i_yprofile.vSet_mode(0x02);

        v_Refresh_Time();

        runmode_refresh();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void runmode_refresh()
    {
        String stitle;
        String smode;

        smode = LanguageData.s_get_string(mContext, R.string.physical_test).toUpperCase();
        stitle = LanguageData.s_get_string(mContext, R.string.gerkin_protocol);

        v_set_type_name(smode, stitle.toUpperCase());
    }

    private void v_Refresh_Time() {
        int iLoop;
        int icount_total;
        int icount_list;
        int icount_end;
        int icount_flash;
        int iprofile_total = ExerciseData.ilist_virtual_num;
        int iprofile_center = iprofile_total;
        ExerciseData.DEVICE_COMMAND Uart_command = null;
        int istep_sec = ExerciseData.iStep_time;
        int imode = 0; //0: 0 start; 1: finish end
        int istep_shift = 1; //skip warm up

        if (ExerciseData.list_real_setting != null) {

            icount_total = ExerciseData.list_real_setting.size();
            icount_list = ExerciseData.ilist_count;

            if (icount_total <= 0 || iprofile_total <= 3) {
                return;
            }

            if (icount_total <= iprofile_total) {
                iLoop = 0;
                icount_end = icount_total;
                icount_flash = icount_list;
            } else if (icount_list <= iprofile_center) {
                iLoop = 0;
                icount_end = iprofile_total;
                icount_flash = icount_list;
            } else {
                if (imode == 0) {
                    iLoop = 0;
                    icount_end = iprofile_total;
                    icount_flash = iprofile_center;
                } else {
                    iLoop = icount_list - iprofile_center;
                    icount_end = iLoop + iprofile_total;
                    icount_flash = iprofile_center;
                }

                if (icount_end > icount_total) {
                    iLoop = icount_total - iprofile_total;
                    icount_end = icount_total;
                    icount_flash = icount_list - (icount_total - iprofile_total);
                }
            }

            //XScale Redraw
            fprofile.i_xprofile.vSetShift(iLoop);

            ExerciseData.list_incline.clear();
            ExerciseData.list_speed.clear();
            for (iLoop = istep_shift; iLoop < icount_end; iLoop++) {
                //避開ExerciseRunProc.java v_CheckTime_list同時存取產生的錯誤
                try {
                    Uart_command = ExerciseData.list_real_setting.get(iLoop);
                }
                catch (IndexOutOfBoundsException e)
                {
                    Uart_command = null;
                }

                if (Uart_command != null) {
                    ExerciseData.list_incline.add(Uart_command.fincline);
                    ExerciseData.list_speed.add(Uart_command.fspeed);
                }
            }

            fprofile.i_profile.vSetInclineList(ExerciseData.list_incline);
            fprofile.i_profile.vSetSpeedList(ExerciseData.list_speed);
            icount_flash -= istep_shift;
            fprofile.i_profile.vSetflash(icount_flash);

        }
    }


    private String s_get_grade()
    {
        String sdata;
        int isec ;
        int iLoop;
        float fgrade;

        float[] fstage_array = {
                31.15f	,	32.55f  ,   33.6f   ,   34.65f  ,   35.35f  ,   37.45f   ,  39.55f    ,     41.3f    ,   43.4f      ,   44.1f   ,   45.15f  ,
                46.2f   ,   46.5f   ,   48.6f   ,   50f     ,   51.4f   ,   52.8f    ,  53.9f     ,     54.9f    ,   56f        ,   57f     ,   57.7f   ,
                58.8f   ,   60.2f   ,   61.2f   ,   62.3f   ,   63.3f   ,   64f      ,  65f       ,     66.5f    ,   68.2f      ,   69f     ,   70.7f   ,
                72.1f   ,   73.1f   ,   73.8f   ,   74.9f   ,   76.3f   ,   77.7f    ,  79.1f     ,     80f
        };

        isec = ExerciseData.mCaculate_Data.itotal_time;

        iLoop = (isec - 60) / 15;

        if(iLoop < 0)
        {
            iLoop = 0;
        }

        if(iLoop < fstage_array.length)
        {
            fgrade = fstage_array[iLoop];
        }
        else
        {
            fgrade = fstage_array[fstage_array.length - 1];
        }

        sdata = Rtx_TranslateValue.sFloat2String(fgrade, 0);

        return sdata;
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
        if(ExerciseData.i_get_finish() == 0x00 && ExerciseData.ilist_count > 0) {
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

    /////////////////////////////
    private int iGet_YMin()
    {
        int ival ;
        float fspeed ;
        float fincline ;
        float flevel ;

        fspeed = EngSetting.f_Get_Min_Speed();
        if(EngSetting.getUnit() == EngSetting.UNIT_IMPERIAL)
        {
            fspeed *= EngSetting.km2mile;
        }
        fincline = EngSetting.f_Get_Min_Incline();

        if(fspeed < fincline)
        {
            ival = (int) (fspeed - 0.9);
        }
        else
        {
            ival = (int) (fincline - 0.9);
        }

        return ival;
    }

    private int iGet_YMax()
    {
        int ival ;
        float fspeed ;
        float fincline ;

        fspeed = EngSetting.f_Get_Max_Speed();
        if(EngSetting.getUnit() == EngSetting.UNIT_IMPERIAL)
        {
            fspeed *= EngSetting.km2mile;
        }
        fincline = EngSetting.f_Get_Max_Incline();

        if(fspeed < fincline)
        {
            ival = (int) (fincline + 0.9);
        }
        else
        {
            ival = (int) (fspeed + 0.9);
        }

        return ival;
    }


}
