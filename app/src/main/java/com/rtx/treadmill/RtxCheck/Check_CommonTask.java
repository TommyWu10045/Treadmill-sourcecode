package com.rtx.treadmill.RtxCheck;


import android.os.Handler;

/**
 * Created by jerry on 2017/6/23.
 */

public class Check_CommonTask extends Rtx_Check {

    protected int CODE_DIALOG_LOGIN     = 0x00000001;
    protected int CODE_DIALOG_NOTE      = 0x00000002;

    public Check_CommonTask(Handler handler) {
        super(handler);
    }

    @Override
    public void checkTask()
    {
        if(iCode == CODE_NULL)
        {
            return;
        }

        if(iCode == CODE_DIALOG_LOGIN)
        {

        }
        else
        if(iCode == CODE_DIALOG_NOTE)
        {

        }

        clear();
    }
}
