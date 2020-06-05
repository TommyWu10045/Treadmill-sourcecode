package com.rtx.treadmill.Fan;

/**
 * Created by Kevin on 8/30/18.
 */

public enum FanState {
    /* enum */
    PROC_FAN_INIT                       (0x00000001) ,
    PROC_FAN_CHECK                      (0x00000002) ,
    PROC_FAN_CHECKVALUE                 (0x00000003) ,
    PROC_FAN_OFF                        (0x00000004) ,
    PROC_FAN_LOW                        (0x00000005) ,
    PROC_FAN_HIGH                       (0x00000006) ,
    PROC_FAN_UNKNOW                     (0x00000007) ,
    PROC_FAN_WAIT                       (0x00000008) ,
    PROC_FAN_DISMISSDIALOG              (0x00000009) ,


    PROC_INIT                           (0x01000000) ,
    PROC_EXIT                           (0x10000000) ,
    PROC_IDLE                           (0x20000000) ,
    PROC_NULL                           (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    FanState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static FanState fromId(int value) {
        for(FanState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
