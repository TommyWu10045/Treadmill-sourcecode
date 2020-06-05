package com.rtx.treadmill.Dialog;

/**
 * Created by chasechang on 3/27/17.
 */

public enum DialogState {
    /* enum */
    PROC_DIALOG_INIT              (0x00000001) ,
    PROC_DIALOG_BM_INFO           (0x00000002) ,
    PROC_DIALOG_BME_INFO          (0x00000003) ,
    PROC_DIALOG_INFO              (0x00000004) ,
    PROC_DIALOG_INFOE             (0x00000005) ,
    PROC_DIALOG_CLOUD_UPDATE_FAIL (0x00000006) ,
    PROC_DIALOG_CLOUD_UPDATE_FAIL02 (0x00000007) ,
    PROC_DIALOG_CLOUD_WIFI_FAIL01 (0x00000008) ,

    PROC_DIALOG_TEST              (0x000000ff) ,
    PROC_DIALOG_TEST1             (0x000000fe) ,
    PROC_DIALOG_CLOUD_RESEND      (0x000000fd) ,
    PROC_DIALOG_CLOUD_RESEND_CHECK(0x000000fc) ,

    PROC_INIT                        (0x01000000) ,
    PROC_EXIT                        (0x10000000) ,
    PROC_IDLE                        (0x20000000) ,
    PROC_NULL                        (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    DialogState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static DialogState fromId(int value) {
        for(DialogState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
