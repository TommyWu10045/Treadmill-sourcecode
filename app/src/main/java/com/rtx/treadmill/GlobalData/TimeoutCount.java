package com.rtx.treadmill.GlobalData;

import android.util.Log;

import com.rtx.treadmill.MainProcTreadmill;
import com.rtx.treadmill.MainState;
import com.rtx.treadmill.RtxMainFunction.Home.HomeState;

/**
 * Created by jerry on 2017/6/23.
 */

public class TimeoutCount {
    private static String TAG = "Jerry=";
    private final static boolean DEBUG = false;

    private static int DEF_TIMEOUT_SEC = 120;

    public static int iTimeoutCount = 0;

    /////////////////////////////////////////////////////////////////////////////////////

    //public static void vAddCount(MainState mState , MainProcTreadmill proc)
    public static void vAddCount()
    {
        iTimeoutCount ++;
        //bCheckTimeout(mState,proc);
    }

    public static void vResetCount()
    {
        iTimeoutCount = 0;
    }

    public static int iGetCurrentCountSec()
    {
        int iSec = 0;

        iSec = iTimeoutCount / EngSetting.DEF_SEC_COUNT;

        return iSec;
    }

    public static boolean bCheckTimeout(MainState mState, MainProcTreadmill proc)
    {
        boolean bResult = false;

        if(ExerciseData.b_is_exercising())
        {
            vResetCount();
        }
        else
        if((mState == MainState.PROC_HOME) && (!CloudDataStruct.CloudData_20.is_log_in()))
        {
            vResetCount();
        }
        else
        {
            if(iGetCurrentCountSec() > DEF_TIMEOUT_SEC)
            {
                vResetCount();

                if(CloudDataStruct.CloudData_20.is_log_in())
                {
                    CloudDataStruct.CloudData_20.set_log_in(false);
                }

                proc.v_Goto_Home();
            }

            //Log.e("Jerry","iGetCurrentCountSec() = " + iGetCurrentCountSec());
            //Log.e("Jerry","ExerciseData.i_get_finish() = " + ExerciseData.i_get_finish());
        }

        return bResult;
    }
     /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

}
