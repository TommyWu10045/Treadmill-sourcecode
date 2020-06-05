package com.rtx.treadmill.RtxTools;


/**
 * Created by jerry on 2017/6/16.
 */

public class Rtx_Debug {

    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////

    public static final boolean RELEASE_MODE = true;

    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////

    public static final boolean VIRTUAL_UART_ENABLE = true;
    public static final boolean CLOUD_MESSAGE_ENABLE = true;
    public static final boolean LOGCAT_SAVE_ENABLE = true;
    public static final boolean BT_ENABLE = false;

    public static final boolean AUTO_LOGIN_FOR_TEST = true;
    public static final String  AUTO_LOGIN_ID = "466";

    public static final boolean FUNCTION_ENABLE_TRAINING       = true;
    public static final boolean FUNCTION_ENABLE_HEART_RATE     = true;
    public static final boolean FUNCTION_ENABLE_HIIT           = true;
    public static final boolean FUNCTION_ENABLE_PHYSICAL_TEST  = true;
    public static final boolean FUNCTION_ENABLE_TARGET_TRAIN   = true;
    public static final boolean FUNCTION_ENABLE_MY_PERFORMANCE = true;
    public static final boolean FUNCTION_ENABLE_BODY_MANAGER   = true;
    public static final boolean FUNCTION_ENABLE_MY_GYM         = true;
    public static final boolean FUNCTION_ENABLE_MY_WORKOUT     = true;
    public static final boolean FUNCTION_ENABLE_QUICK_START    = true;

    /////////////////////////////////////////////////////////////////////////////////////////////////

    public static final boolean FUNCTION_ENABLE_SHARE = false;

    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int FUNCTION_INDEX_TRAINING       = 0;
    public static final int FUNCTION_INDEX_HEART_RATE     = 1;
    public static final int FUNCTION_INDEX_HIIT           = 2;
    public static final int FUNCTION_INDEX_PHYSICAL_TEST  = 3;
    public static final int FUNCTION_INDEX_TARGET_TRAIN   = 4;
    public static final int FUNCTION_INDEX_MY_PERFORMANCE = 5;
    public static final int FUNCTION_INDEX_BODY_MANAGER   = 6;
    public static final int FUNCTION_INDEX_MY_GYM         = 7;
    public static final int FUNCTION_INDEX_MY_WORKOUT     = 8;

    public final static int FUNCTION_INDEX_QUICK_START    = 9;

    /////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean bAutoLogin()
    {
        if(RELEASE_MODE)
        {
            return false;
        }

        if(AUTO_LOGIN_FOR_TEST)
        {
            return true;
        }

        return false;
    }

    public static String sGetID_ForAutoLogin()
    {
        return AUTO_LOGIN_ID;
    }

    public static boolean bGetFunctionEnable(int iFunctionIndex)
    {
        if(!RELEASE_MODE)
        {
            return true;
        }

        if     (iFunctionIndex == FUNCTION_INDEX_TRAINING)          { return FUNCTION_ENABLE_TRAINING; }
        else if(iFunctionIndex == FUNCTION_INDEX_HEART_RATE)        { return FUNCTION_ENABLE_HEART_RATE; }
        else if(iFunctionIndex == FUNCTION_INDEX_HIIT)              { return FUNCTION_ENABLE_HIIT; }
        else if(iFunctionIndex == FUNCTION_INDEX_PHYSICAL_TEST)     { return FUNCTION_ENABLE_PHYSICAL_TEST; }
        else if(iFunctionIndex == FUNCTION_INDEX_TARGET_TRAIN)      { return FUNCTION_ENABLE_TARGET_TRAIN; }
        else if(iFunctionIndex == FUNCTION_INDEX_MY_PERFORMANCE)    { return FUNCTION_ENABLE_MY_PERFORMANCE; }
        else if(iFunctionIndex == FUNCTION_INDEX_BODY_MANAGER)      { return FUNCTION_ENABLE_BODY_MANAGER; }
        else if(iFunctionIndex == FUNCTION_INDEX_MY_GYM)            { return FUNCTION_ENABLE_MY_GYM; }
        else if(iFunctionIndex == FUNCTION_INDEX_MY_WORKOUT)        { return FUNCTION_ENABLE_MY_WORKOUT; }
        else if(iFunctionIndex == FUNCTION_INDEX_QUICK_START)       { return FUNCTION_ENABLE_QUICK_START; }
        else                                                        { return false;}
    }

    public static boolean bGetVirtualUartEnable()
    {
        if(RELEASE_MODE)
        {
            return false;
        }

        return VIRTUAL_UART_ENABLE;
    }

    public static boolean bGetCloud_MessageEnable()
    {
        if(RELEASE_MODE)
        {
            return false;
        }

        return CLOUD_MESSAGE_ENABLE;
    }

    public static boolean bGetLogcat_SaveEnable()
    {
        if(RELEASE_MODE)
        {
            return false;
        }

        return LOGCAT_SAVE_ENABLE;
    }

    public static boolean bGetBT_Enable()
    {
        if(RELEASE_MODE)
        {
          return true;
        }

        return BT_ENABLE;
    }

    public static boolean bGetRELEASE_MODE()
    {
        return RELEASE_MODE;
    }

    public static boolean bRtxDebug_GetShareEnable()
    {
        return FUNCTION_ENABLE_SHARE;
    }
}
