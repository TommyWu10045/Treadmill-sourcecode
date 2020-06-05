package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Stop;

/**
 * Created by chasechang on 3/27/17.
 */

public enum StopState {
    /* enum */
    PROC_STOP_INIT              (0x00000001) ,
    PROC_STOP_FINISH            (0x00000002) ,

    PROC_STOP_CLOUD11           (0x00000010) ,
    PROC_STOP_CLOUD11_CHECK     (0x00000011) ,

    PROC_INIT                        (0x01000000) ,
    PROC_EXIT                        (0x10000000) ,
    PROC_IDLE                        (0x20000000) ,
    PROC_NULL                        (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    StopState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static StopState fromId(int value) {
        for(StopState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
