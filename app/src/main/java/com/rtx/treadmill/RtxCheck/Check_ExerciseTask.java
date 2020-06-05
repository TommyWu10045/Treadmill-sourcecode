package com.rtx.treadmill.RtxCheck;


import android.os.Handler;

/**
 * Created by jerry on 2017/6/23.
 */

public class Check_ExerciseTask extends Rtx_Check
{
    protected int CODE_EXERCISE_PAUSE   = 0x00000001;
    protected int CODE_EXERCISE_STOP    = 0x00000002;

    public Check_ExerciseTask(Handler handler) {
        super(handler);
    }

    @Override
    public void checkTask()
    {
        if(iCode == CODE_NULL)
        {
            return;
        }

        if(iCode == CODE_EXERCISE_PAUSE)
        {

        }

        if(iCode == CODE_EXERCISE_STOP)
        {

        }

        clear();
    }
}
