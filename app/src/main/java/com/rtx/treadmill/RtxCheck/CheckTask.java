package com.rtx.treadmill.RtxCheck;


import android.os.Handler;

/**
 * Created by jerry on 2017/6/23.
 */

public class CheckTask
{
    Check_EmergencyCode     checkEmergencyCode;
    Check_ErrorCode         checkErrorCode;
    Check_ExerciseTask      checkExerciseTask;
    Check_CommonTask        checkCommonTask;

    Handler handler_UI;

    public CheckTask(Handler handler)
    {
        handler_UI = handler;

        checkEmergencyCode = new Check_EmergencyCode(handler_UI);
        checkErrorCode = new Check_ErrorCode(handler_UI);
        checkExerciseTask = new Check_ExerciseTask(handler_UI);
        checkCommonTask = new Check_CommonTask(handler_UI);
    }

    public void checkAll()
    {
        check(checkEmergencyCode);
        check(checkErrorCode);
        check(checkExerciseTask);
        check(checkCommonTask);
    }

    public void clearAll()
    {
        clear(checkEmergencyCode);
        clear(checkErrorCode);
        clear(checkExerciseTask);
        clear(checkCommonTask);
    }

    //////////////////////////////////////////////////////////////////////////////////

    public void check(Rtx_Check rtx_check)
    {
        if(handler_UI == null)
        {
            return;
        }

        if(rtx_check != null)
        {
            rtx_check.checkTask();
        }
    }

    public void clear(Rtx_Check rtx_check)
    {
        if(rtx_check != null)
        {
            rtx_check.clear();
        }
    }
}
