package com.rtx.treadmill.RtxMainFunctionBike.BaseLayout;

import android.content.Context;

import com.rtx.treadmill.GlobalData.EngSetting;
import com.rtx.treadmill.GlobalData.ExerciseData;
import com.rtx.treadmill.GlobalData.LanguageData;
import com.rtx.treadmill.MainActivity;
import com.rtx.treadmill.R;
import com.rtx.treadmill.RtxView.RtxProfile;

/**
 * Created by chasechang on 3/22/17.
 */

abstract public class SummaryLayout extends SummaryBaseLayout {
    private String TAG = "Jerry" ;

    private Context mContext;
    private MainActivity        mMainActivity;

    public RtxProfile fprofile;

    private int iMachine_mode = 1; //0 : treadmill; etc : bike

    public SummaryLayout(Context context, MainActivity mMainActivity) {
        super(context, mMainActivity);

        mContext = context;

        this.mMainActivity = mMainActivity;

    }


    @Override
    protected void init_CustomerView()
    {
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

        //Profile Frame
        ix = 250;
        iy = 140;
        iw = 800;
        ih = 350;
        addViewToLayout(fprofile, ix, iy, iw, ih);

        fprofile.i_profile.vSet_mode(iMachine_mode);
        fprofile.i_profile.vSet_limit(iGet_YMin(), iGet_YMax());

        fprofile.i_yprofile.vSet_limit(iGet_YMin(), iGet_YMax());
        fprofile.init(0.7f);

        fprofile.t_speed.setText(LanguageData.s_get_string(mContext,R.string.resistance_level));
        fprofile.i_speed.setImageResource(R.drawable.resistance_level_icon);

        fprofile.t_speed.setVisibility(VISIBLE);
        fprofile.i_speed.setVisibility(VISIBLE);
        fprofile.t_incline.setVisibility(INVISIBLE);
        fprofile.i_incline.setVisibility(INVISIBLE);
        fprofile.t_min.setVisibility(VISIBLE);

        v_Refresh_Time();

        add_CustomerUpdateView();

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
                if(imode == 0)
                {
                    iLoop = 0;
                    icount_end = iprofile_total;
                    icount_flash = iprofile_center;
                }
                else
                {
                    iLoop = icount_list - iprofile_center;
                    icount_end = iLoop + iprofile_total;
                    icount_flash = iprofile_center;
                }

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


    ///////////////////////////////////////////////////////////////////////////////////////////////
    protected void add_CustomerUpdateView(){

    };

    /////////////////////////////
    private int iGet_YMin()
    {
        int ival ;
        float flevel ;

        flevel = EngSetting.f_Get_Min_level();

        ival = (int) (flevel - 0.9);

        return ival;
    }

    private int iGet_YMax()
    {
        int ival ;
        float flevel ;

        flevel = EngSetting.f_Get_Max_level();

        ival = (int) (flevel + 0.9);

        return ival;
    }


}
