package com.rtx.treadmill.RtxCheck;


import android.os.Handler;

/**
 * Created by jerry on 2017/6/23.
 */

public class Check_EmergencyCode extends Rtx_Check
{
    protected int CODE_ERROR_Machine    = 0x00000001;
    protected int CODE_ERROR_Emergency  = 0x00000002;

    public Check_EmergencyCode(Handler handler) {
        super(handler);
    }

    @Override
    public void checkTask()
    {
        if(iCode == CODE_NULL)
        {
            return;
        }

        if((iCode & CODE_ERROR_Machine) != CODE_NULL)
        {

        }

        if((iCode & CODE_ERROR_Emergency) != CODE_NULL)
        {

        }

        clear();
    }
}
