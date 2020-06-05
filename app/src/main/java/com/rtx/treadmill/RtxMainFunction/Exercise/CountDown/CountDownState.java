package com.rtx.treadmill.RtxMainFunction.Exercise.CountDown;

/**
 * Created by chasechang on 3/27/17.
 */

public enum CountDownState {
    /* enum */
    PROC_COUNTDOWN_INIT              (0x00000001) ,
    PROC_COUNTDOWN_3SEC              (0x00000002) ,
    PROC_COUNTDOWN_2SEC              (0x00000003) ,
    PROC_COUNTDOWN_1SEC              (0x00000004) ,
    PROC_COUNTDOWN_GO                (0x00000005) ,
    PROC_COUNTDOWN_START             (0x00000006) ,
    PROC_COUNTDOEN_DELAY             (0x00000007) ,
    PROC_COUNTDOWN_NULL              (0x00000008) ,

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
    CountDownState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static CountDownState fromId(int value) {
        for(CountDownState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
