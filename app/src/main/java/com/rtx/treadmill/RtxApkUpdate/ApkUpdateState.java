package com.rtx.treadmill.RtxApkUpdate;

/**
 * Created by Kevin on 8/16/18.
 */

public enum ApkUpdateState {
    /* enum */
    PROC_APKUPDATE_INIT                 (0x00000001) ,
    PROC_APKUPDATE_UPDATE               (0x00000002) ,
    PROC_APKUPDATE_CHECKUPDATE          (0x00000003) ,
    PROC_APKUPDATE_CHECKURL             (0x00000004) ,
    PROC_APKUPDATE_DOWNLOADFAIL         (0x00000005) ,
    PROC_APKUPDATE_CHECKIDLE            (0x00000006) ,
    PROC_APKUPDATE_SHOWDIALOG           (0x00000007) ,
    PROC_APKUPDATE_DOWNLOADFILE         (0x00000008) ,
    PROC_APKUPDATE_WAITDOWNLOAD         (0x00000009) ,
    PROC_APKUPDATE_CHECKFILE            (0x0000000A) ,
    PROC_APKUPDATE_WAITUNZIP            (0x0000000B) ,
    PROC_APKUPDATE_UNZIP                (0x0000000C) ,
    PROC_APKUPDATE_ZIPENTRY             (0x0000000D) ,
    PROC_APKUPDATE_CHECKUNZIP           (0x0000000E) ,
    PROC_APKUPDATE_CHECKSERVICE         (0x0000000F) ,
    PROC_APKUPDATE_STARTSERVICE         (0x00000010) ,
    PROC_APKUPDATE_INSTALLAPK           (0x00000011) ,


    PROC_INIT                           (0x01000000) ,
    PROC_EXIT                           (0x10000000) ,
    PROC_IDLE                           (0x20000000) ,
    PROC_NULL                           (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    ApkUpdateState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static ApkUpdateState fromId(int value) {
        for(ApkUpdateState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
