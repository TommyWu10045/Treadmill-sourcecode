package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Pause;

/**
 * Created by chasechang on 3/27/17.
 */

public enum PauseState {
    /* enum */
    PROC_PAUSE_INIT              (0x00000001) ,

    PROC_PAUSE_MODE1             (0x00000010) ,
    PROC_PAUSE_MODE1_FLASH       (0x00000011) ,
    PROC_PAUSE_MODE1_RESUME      (0x00000012) ,

    PROC_PAUSE_MODE2             (0x00000020) ,
    PROC_PAUSE_MODE2_FLASH       (0x00000021) ,
    PROC_PAUSE_MODE2_RESUME      (0x00000022) ,

    PROC_PAUSE_RESET1            (0x00000030) ,
    PROC_PAUSE_RESET1_FLASH      (0x00000031) ,
    PROC_PAUSE_RESET1_RESUME     (0x00000032) ,

    PROC_PAUSE_RESET2            (0x00000040) ,
    PROC_PAUSE_RESET2_FLASH      (0x00000041) ,
    PROC_PAUSE_RESET2_RESUME     (0x00000042) ,

    PROC_PAUSE_STOP              (0x00000100) ,

    PROC_GETSTATUS              (0x0000F001) ,
    PROC_DELAY                  (0x0000F002) ,
    PROC_CACULATION             (0x0000F003) ,

    PROC_INIT                        (0x01000000) ,
    PROC_EXIT                        (0x10000000) ,
    PROC_IDLE                        (0x20000000) ,
    PROC_NULL                        (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    PauseState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static PauseState fromId(int value) {
        for(PauseState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
