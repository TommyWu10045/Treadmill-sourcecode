package com.rtx.treadmill.RtxTools;

import com.rtx.treadmill.GlobalData.EngSetting;

/**
 * Created by jerry on 2017/6/16.
 */

public class Rtx_FitnessCalculation {


    public static float f_calories_calculate(long lMillionSec, int imode, float fSpeed, float fIncline, float fWeight)
    {
        float fval = 0;
        float fwatt = 0;

        if(imode == EngSetting.RUNNING ||imode == EngSetting.RUNNING6 || imode == EngSetting.RUNNING7) {
            if(fSpeed > 0) {
                if (fSpeed < 6) {
                    fval = (float) ((((3.5 + (10 * fSpeed) / 6) + (30 * fSpeed * fIncline)/100) * (fWeight / 200)) * lMillionSec / 60000);
                } else {
                    fval = (float) ((((3.5 + (20 * fSpeed) / 6) + (15 * fSpeed * fIncline)/100) * (fWeight / 200)) * lMillionSec / 60000);
                }
            }
        }
        else if(imode == EngSetting.RBIKING || imode == EngSetting.UBIKING || imode == EngSetting.ELLIPTICAL || imode == EngSetting.RBIKING6 || imode == EngSetting.UBIKING6 || imode == EngSetting.ELLIPTICAL6) //RB and UB
        {
            fwatt = f_watt_calculate(60, imode, fSpeed, fIncline);
            fval = (float) (((1.8 * fwatt * 6 / fWeight + 7) * fWeight / 1200 ) * lMillionSec / 1000 );
            //fval = (float) ((1.8 * fwatt * 6 / fWeight + 7) * fWeight * 60 * lMillionSec / 200000);
        }

        return fval;
    }

    public static float f_watt_calculate(long lTimeSec, int imode, float fSpeed, float fIncline)
    {
        float frpm = 0;
        float flevel = 0;
        float ftouqe = 0;
        float fwatt = 0;

        frpm = fSpeed;
        flevel = fIncline - 1;
        if(imode == EngSetting.RBIKING || imode == EngSetting.UBIKING || imode == EngSetting.ELLIPTICAL || imode == EngSetting.RBIKING6 || imode == EngSetting.UBIKING6 || imode == EngSetting.ELLIPTICAL6) //RB and UB
        {
            ftouqe = EngSetting.f_Get_fmachine_tq((int)flevel);
            fwatt = ftouqe * frpm * 1.027f / 100f;
        }

        fwatt = fwatt * lTimeSec / 60;

        return fwatt;
    }

    public static float f_distance_calculate(long lTimeSec, int imode, float fSpeed)
    {
        float fval = 0;
        float frpm = 0;

        if(imode == EngSetting.RUNNING || imode == EngSetting.RUNNING6 || imode == EngSetting.RUNNING7) {

        }
        else
        {
            frpm = fSpeed;
            fSpeed = f_rpm_to_speed(imode, frpm);
        }

        if (fSpeed > 0)
        {
            fval =  (float)( fSpeed * lTimeSec ) / 3600f;
        }

        return fval;
    }

    public static float f_rpm_to_speed(int imode, float frpm)
    {
        float fspeed = 0;

        if(imode == EngSetting.RBIKING || imode == EngSetting.UBIKING || imode == EngSetting.RBIKING6 || imode == EngSetting.UBIKING6) //RB and UB, 由RPM轉speed
        {
            fspeed = (78f * 3.14159f * 2.54f * frpm) * 60 / 100000;
        }
        else if(imode == EngSetting.ELLIPTICAL || imode == EngSetting.ELLIPTICAL6) //EP, 由RPM轉speed
        {
            fspeed = 101.6f * frpm * 60 / 100000;
        }

        return fspeed;
    }

    public static float f_avg_speed_calculate(float fTotalKm, long lTotalSec)
    {
        float fAvgSpeed = 0;

        fAvgSpeed = ( fTotalKm * 3600 ) / lTotalSec;

        return fAvgSpeed;
    }

    public static int cal_avg_pace_calculate(float fTotalKm, long lTotalMin)
    {
        float fAvgSpeedMin = 0;

        fAvgSpeedMin = (float) lTotalMin /  fTotalKm ;

        return (int)fAvgSpeedMin;
    }

    public static int cal_avg_pace_calculate_sec(float fTotalKm, long lTotalSec)
    {
        float fAvgSpeedSec = 0;

        fAvgSpeedSec = (float) lTotalSec /  fTotalKm ;

        return (int)fAvgSpeedSec;
    }
}
