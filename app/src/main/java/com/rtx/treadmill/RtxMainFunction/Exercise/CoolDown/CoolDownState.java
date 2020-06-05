package com.rtx.treadmill.RtxMainFunction.Exercise.CoolDown;

/**
 * Created by chasechang on 3/27/17.
 */

public enum CoolDownState {
    /* enum */
    PROC_PAUSE_INIT              (0x00000001) ,

    PROC_COOLDOWN_MODE1          (0x00000010) ,
    PROC_COOLDOWN_MODE2          (0x00000020) ,

    PROC_GETSTATUS               (0x0000F001) ,
    PROC_DELAY                   (0x0000F002) ,
    PROC_CACULATION              (0x0000F003) ,

    PROC_INIT                    (0x01000000) ,
    PROC_EXIT                    (0x10000000) ,
    PROC_IDLE                    (0x20000000) ,
    PROC_NULL                    (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    CoolDownState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static CoolDownState fromId(int value) {
        for(CoolDownState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
