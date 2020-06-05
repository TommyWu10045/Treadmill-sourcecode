package com.rtx.treadmill.RtxMainFunction.Exercise.Emergency;

/**
 * Created by chasechang on 3/27/17.
 */

public enum EmergencyState {
    /* enum */
    PROC_EMERGENCY_INIT              (0x00000001) ,
    PROC_EMERGENCY_UP                (0x00000002) ,
    PROC_EMERGENCY_DOWN              (0x00000003) ,

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
    EmergencyState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static EmergencyState fromId(int value) {
        for(EmergencyState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
