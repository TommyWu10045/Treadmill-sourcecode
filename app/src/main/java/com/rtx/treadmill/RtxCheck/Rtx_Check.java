package com.rtx.treadmill.RtxCheck;


import android.os.Handler;

/**
 * Created by jerry on 2017/6/23.
 */

public class Rtx_Check
{
    Handler handlerUI;

    public Rtx_Check(Handler handler)
    {
        handlerUI = handler;
    }

    protected int CODE_NULL = 0x00000000;

    protected int iCode = CODE_NULL;

    public void checkTask()
    {

    }

    public void clear()
    {
        iCode = CODE_NULL;
    }

    public void setCode(int code)
    {
        iCode = code;
    }

    public int getCode()
    {
        return iCode;
    }
}
