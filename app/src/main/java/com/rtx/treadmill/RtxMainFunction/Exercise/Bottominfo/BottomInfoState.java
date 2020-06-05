package com.rtx.treadmill.RtxMainFunction.Exercise.Bottominfo;

/**
 * Created by chasechang on 3/27/17.
 */

public enum BottomInfoState {
    /* enum */
    PROC_INFO_INIT                   (0x00000001) ,
    PROC_BOTTOM                      (0x00000010) ,

    PROC_INIT                        (0x01000000) ,
    PROC_EXIT                        (0x10000000) ,
    PROC_IDLE                        (0x20000000) ,
    PROC_NULL                        (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    BottomInfoState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static BottomInfoState fromId(int value) {
        for(BottomInfoState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
