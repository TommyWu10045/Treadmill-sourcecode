package com.rtx.treadmill.RtxMainFunctionBike.Exercise.Error;

/**
 * Created by chasechang on 3/27/17.
 */

public enum ErrorState {
    /* enum */
    PROC_ERROR_INIT              (0x00000001) ,
    PROC_ERROR_SHOW              (0x00000002) ,
    PROC_ERROR_NULL              (0x00000003) ,

    PROC_ERROR_CLOUD02           (0x00000010) ,
    PROC_ERROR_CLOUD02_CHECK     (0x00000011) ,

    PROC_INIT                        (0x01000000) ,
    PROC_EXIT                        (0x10000000) ,
    PROC_IDLE                        (0x20000000) ,
    PROC_NULL                        (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    ErrorState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static ErrorState fromId(int value) {
        for(ErrorState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
