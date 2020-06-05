package com.rtx.treadmill.RtxCheck;


import android.os.Handler;

/**
 * Created by jerry on 2017/6/23.
 */

public class Check_ErrorCode extends Rtx_Check
{
    protected int CODE_ERROR_NO_INTERNET    = 0x00000001;
    protected int CODE_ERROR_NO_WIFI        = 0x00000002;

    public Check_ErrorCode(Handler handler) {
        super(handler);
    }

    @Override
    public void checkTask()
    {
        if(iCode == CODE_NULL)
        {
            return;
        }

        if((iCode & CODE_ERROR_NO_INTERNET) != CODE_NULL)
        {

        }

        if((iCode & CODE_ERROR_NO_WIFI) != CODE_NULL)
        {

        }

        clear();
    }

}
