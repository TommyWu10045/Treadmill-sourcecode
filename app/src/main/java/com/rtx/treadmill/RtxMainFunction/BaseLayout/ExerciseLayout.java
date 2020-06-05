package com.rtx.treadmill.RtxMainFunction.BaseLayout;

import android.content.Context;
import android.view.View;

import com.rtx.treadmill.GlobalData.Bottom_UI_Info;
import com.rtx.treadmill.GlobalData.Common;
import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.ExerciseRunState;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxBaseLayout.Rtx_ExerciseBaseLayout;
import com.rtx.treadmill.RtxTools.Rtx_TranslateValue;
import com.rtx.treadmill.RtxView.RtxLap;
import com.rtx.treadmill.RtxView.RtxProfile;
import com.rtx.treadmill.RtxView.RtxSimple;

/**
 * Created by chasechang on 3/22/17.
 */

public class ExerciseLayout extends Rtx_ExerciseBaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;

    private MainActivity        mMainActivity;

    private int iprofile_mode = 0;//0 shift 1 step ; 1 shift 5 step

    public RtxProfile fprofile;
    private RtxSimple fsimple;
    private RtxLap flap;

    private int icount = 0;

    int iLapPercent = ExerciseData.iLapMeter;

    private int iMachine_mode = 0; //0 : treadmill; etc : bike

    public ExerciseLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;

    }


    @Override
    protected void init_CustomerView()
    {
        //profile Frame
        if(fprofile == null)     {   fprofile = new RtxProfile(mContext);}
        //Lap Frame
        if(fsimple == null)     {   fsimple = new RtxSimple(mContext, mMainActivity);}
        //Lap Frame
        if(flap == null)     {   flap = new RtxLap(mContext);}

        Bottom_UI_Info.vSet_PageState(ExerciseData.iexercise_def_page);
    }

    @Override
    protected void init_CustomerEvent()
    {
        //Let user can override this.
    }

    @Override
    protected void add_CustomerView()
    {
        //Profile Frame
        addViewToLayout(fprofile, 90, 140, 1100, 505);
        ExerciseData.ilist_virtual_num = ExerciseData.iprofile_num;

        fprofile.i_profile.vSet_mode(iMachine_mode);
        fprofile.i_profile.vSet_limit(iGet_YMin(), iGet_YMax());

        fprofile.i_yprofile.vSet_limit(iGet_YMin(), iGet_YMax());
        fprofile.init(1f);

        fprofile.t_speed.setText(LanguageData.s_get_string(mContext,R.string.speed_upper));
        fprofile.i_speed.setImageResource(R.drawable.speed_icon);

        fprofile.t_speed.setVisibility(VISIBLE);
        fprofile.i_speed.setVisibility(VISIBLE);
        fprofile.t_incline.setVisibility(VISIBLE);
        fprofile.i_incline.setVisibility(VISIBLE);
        fprofile.t_min.setVisibility(VISIBLE);
        fprofile.i_profile.vSet_iIncline_mode(0x02);

        //Lap Frame
        addViewToLayout(fsimple, 0, 100, 1280, 700);
        fsimple.init(1f);

        //Lap Frame
        addViewToLayout(flap, 0, 100, 1280, 700);
        flap.init(1f);

        vChoice_Page(Bottom_UI_Info.iGet_PageState());
        vSet_CustomerView();

        init_ViewMask();
        vVisibleMaskView(true);
    }

    public void vSet_profilemode(int imode)
    {
        this.iprofile_mode = imode;
    }

    protected void vSet_CustomerView()
    {
        //Let user can override this.
    }

    protected void vClickHomeCallBack()
    {

    }

    protected void vChoicePage_Set_Bottom(int isel)
    {

    }

    @Override
    public void vClickHome()
    {
        vClickHomeCallBack();
    }

    @Override
    public void v_clickpage(int isel)
    {
        Bottom_UI_Info.vSet_PageState(isel);
        vChoice_Page(isel);
        //ExerciseData.iexercise_def_page = ipage;//if want to keep last page unmask it.

    }

    /////////////////////////////////////////////////////
    private void v_Refresh_Time() {
        int iLoop;
        int icount_total;
        int icount_list;
        int icount_end;
        int icount_flash;
        int iprofile_total = ExerciseData.ilist_virtual_num;
        int iprofile_center = iprofile_total - ExerciseData.iprofile_center;
        ExerciseData.DEVICE_COMMAND Uart_command = null;

        if (ExerciseData.list_real_setting != null) {

            icount_total = ExerciseData.list_real_setting.size();
            icount_list = ExerciseData.ilist_count;

            if(icount_total <= 0 || iprofile_total <= 3)
            {
                return;
            }

            if (icount_total <= iprofile_total) {
                iLoop = 0;
                icount_end = icount_total;
                icount_flash = icount_list;
            }
            else if (icount_list <= iprofile_center) {
                iLoop = 0;
                icount_end = iprofile_total;
                icount_flash = icount_list;
            }
            else {
                iLoop = icount_list - iprofile_center;
                icount_end = iLoop + iprofile_total;
                icount_flash = iprofile_center;

                if (icount_end > icount_total) {
                    iLoop = icount_total - iprofile_total;
                    icount_end = icount_total;
                    icount_flash = icount_list - (icount_total - iprofile_total) ;
                }
            }

            //XScale Redraw
            fprofile.i_xprofile.vSetShift(iLoop);

            ExerciseData.list_incline.clear();
            ExerciseData.list_speed.clear();
            for (; iLoop < icount_end; iLoop++) {
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
            fprofile.i_profile.vSetflash(icount_flash);

        }
    }

    private void v_Refresh_Time01() {
        int iLoop;
        int icount_total;
        int icount_list;
        int icount_end;
        int icount_flash;
        int iprofile_total = ExerciseData.ilist_virtual_num;
        int iprofile_shift = ExerciseData.iprofile_shift_num;
        ExerciseData.DEVICE_COMMAND Uart_command = null;
        int ishift_time ;

        if (ExerciseData.list_real_setting != null) {

            icount_total = ExerciseData.list_real_setting.size();
            icount_list = ExerciseData.ilist_count;

            if(icount_total <= 0 || iprofile_total <= 3)
            {
                return;
            }

            if (icount_total <= iprofile_total) {
                iLoop = 0;
                icount_end = icount_total;
                icount_flash = icount_list;
            }
            else if (icount_list < iprofile_total) {
                iLoop = 0;
                icount_end = iprofile_total;
                icount_flash = icount_list;
            }
            else {
                ishift_time = (icount_list - iprofile_total) / iprofile_shift;
                iLoop = (1 + ishift_time) * iprofile_shift;
                icount_end = iLoop + iprofile_total;
                icount_flash = icount_list - iLoop;
            }

            //XScale Redraw
            fprofile.i_xprofile.vSetShift(iLoop);

            ExerciseData.list_incline.clear();
            ExerciseData.list_speed.clear();
            for (; iLoop < icount_end; iLoop++) {
                if (iLoop < ExerciseData.list_real_setting.size())
                {
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
            }

            fprofile.i_profile.vSetInclineList(ExerciseData.list_incline);
            fprofile.i_profile.vSetSpeedList(ExerciseData.list_speed);
            fprofile.i_profile.vSetflash(icount_flash);

        }
    }
    ////////// v_Refresh_Profile ///////////
    private void v_Refresh_Profile() {
        ExerciseRunState imode = ExerciseData.E_mode.fromId(ExerciseData.E_imode & ExerciseData.M_mask_type);

        switch (imode)
        {
            case PROC_EXERCISE_TR:
            case PROC_EXERCISE_HEARTRATE:
            case PROC_EXERCISE_QUICKSTART:
            case PROC_EXERCISE_WORKOUT:
            case PROC_EXERCISE_HIIT:
                if(iprofile_mode == 0) {
                    v_Refresh_Time();
                }
                else {
                    v_Refresh_Time01();
                }
                break;
            default:
                break;
        }

    }

    ////////// v_Refresh_Simple ///////////
    private void v_Refresh_Simple() {

        fsimple.vUpdate_Data();
    }

    ////////// v_Refresh_Lap ///////////
    private void v_Refresh_Lap() {
        int ival;
        String sdata = "";

        ival = (int)(ExerciseData.mCaculate_Data.sdistance.ftotal % iLapPercent);
        flap.i_lap.setPercent(ival);
        ival = 1 + (int)(ExerciseData.mCaculate_Data.sdistance.ftotal/iLapPercent);
        sdata = Rtx_TranslateValue.sInt2String(ival);
        flap.t_lap_num.setText(sdata);
    }

    private void v_refresh_all(int isel)
    {
        switch (isel)
        {
            case 0: //profile
                v_Refresh_Profile();
                break;
            case 1: //simple
                v_Refresh_Simple();
                break;
            case 2: //lap
                v_Refresh_Lap();
                break;
            default:
                break;
        }
    }

    public void Refresh()
    {
        if(icount % EngSetting.DEF_EXERCISE_REFRESH == 0)
        {
            v_refresh_all(Bottom_UI_Info.iGet_PageState());
        }

        if(icount % (EngSetting.DEF_SEC_COUNT * 2) == 0)
        {
            vVisibleMaskView(false);
        }

        icount++;

    }

    private void vChoice_Page(int isel)
    {
        switch (isel)
        {
            case 0: //profile
                fprofile.setVisibility(VISIBLE);
                fsimple.setVisibility(INVISIBLE);
                flap.setVisibility(INVISIBLE);
                break;
            case 1: //simple
                fprofile.setVisibility(INVISIBLE);
                fsimple.setVisibility(VISIBLE);
                flap.setVisibility(INVISIBLE);
                break;
            case 2: //lap
                fprofile.setVisibility(INVISIBLE);
                fsimple.setVisibility(INVISIBLE);
                flap.setVisibility(VISIBLE);
                break;
            default:
                break;
        }

        vChoicePage_Set_Bottom(isel);
        v_refresh_all(isel);

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
