package com.rtx.treadmill.Cloud;

/**
 * Created by chasechang on 3/27/17.
 */

public enum CloudInfoState {
    /* enum */
    PROC_INFO_INIT              (0x00000001) ,
    PROC_INFO_CLOUD             (0x00000002) ,
    PROC_INFO_CLOUD_CHECK       (0x00000003) ,

    PROC_INIT                        (0x01000000) ,
    PROC_EXIT                        (0x10000000) ,
    PROC_IDLE                        (0x20000000) ,
    PROC_NULL                        (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    CloudInfoState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static CloudInfoState fromId(int value) {
        for(CloudInfoState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
