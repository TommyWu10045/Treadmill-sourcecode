package com.rtx.treadmill.RtxMainFunction.Exercise.Info;

/**
 * Created by chasechang on 3/27/17.
 */

public enum InfoState {
    /* enum */
    PROC_INFO_INIT              (0x00000001) ,
    PROC_INFO_FLASH             (0x00000002) ,

    PROC_INIT                        (0x01000000) ,
    PROC_EXIT                        (0x10000000) ,
    PROC_IDLE                        (0x20000000) ,
    PROC_NULL                        (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    InfoState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static InfoState fromId(int value) {
        for(InfoState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
