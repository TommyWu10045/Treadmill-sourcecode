package com.rtx.treadmill.Engmode;

/**
 * Created by chasechang on 3/27/17.
 */

public enum EngModeState {
    /* enum */
    PROC_SHOW_WIFI                (0x00000010) ,
    PROC_SHOW_ABOUT               (0x00000020) ,
    PROC_SHOW_SETTING             (0x00000021) ,
    PROC_SHOW_ERROR               (0x00000030) ,
    PROC_SHOW_UARTTEST            (0x00000040) ,
    PROC_SHOW_UARTTEST_CHECK      (0x00000041) ,
    PROC_SHOW_UARTTEST_REPEAT     (0x00000042) ,
    PROC_SHOW_UPDATE              (0x00000050) ,

    PROC_INIT                       (0x01000000) ,
    PROC_INIT_PAGE                  (0x02000000) ,
    PROC_EXIT                       (0x10000000) ,
    PROC_IDLE                       (0x20000000) ,
    PROC_NULL                       (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    EngModeState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static EngModeState fromId(int value) {
        for(EngModeState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
