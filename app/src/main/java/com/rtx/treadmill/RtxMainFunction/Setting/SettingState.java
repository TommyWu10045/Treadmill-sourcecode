package com.rtx.treadmill.RtxMainFunction.Setting;

/**
 * Created by chasechang on 3/27/17.
 */

public enum SettingState {
    /* enum */
    PROC_SHOW_SETTING               (0x00000010) ,
    PROC_SHOW_PROFILE               (0x00000020) ,
    PROC_SHOW_PROFILE_NAME          (0x00000021) ,
    PROC_SHOW_PROFILE_HEIGHT        (0x00000022) ,
    PROC_SHOW_PROFILE_BIRTH         (0x00000023) ,
    PROC_SHOW_CHANGEPASSWORD00      (0x00000030) ,
    PROC_SHOW_CHANGEPASSWORD01      (0x00000031) ,
    PROC_SHOW_CHANGEPASSWORD02      (0x00000032) ,
    PROC_SHOW_CHANGEPASSWORD03      (0x00000033) ,
    PROC_SHOW_CHANGEPASSWORD04      (0x00000034) ,
    PROC_SHOW_DATASYNC              (0x00000040) ,

    PROC_SHOW_DATASYNC_NIKE         (0x00000041) ,
    PROC_SHOW_DATASYNC_RUNKEEPER    (0x00000042) ,
    PROC_SHOW_DATASYNC_GOOGLE       (0x00000043) ,
    PROC_SHOW_DATASYNC_MYMAP        (0x00000044) ,
    PROC_SHOW_DATASYNC_FITBIT       (0x00000045) ,
    PROC_SHOW_DATASYNC_JAWBONE      (0x00000046) ,
    PROC_SHOW_DATASYNC_GARMIN       (0x00000047) ,

    PROC_SHOW_DATASYNC_NIKEP        (0x00000141) ,
    PROC_SHOW_DATASYNC_RUNKEEPERP   (0x00000142) ,
    PROC_SHOW_DATASYNC_GOOGLEP      (0x00000143) ,
    PROC_SHOW_DATASYNC_MYMAPP       (0x00000144) ,
    PROC_SHOW_DATASYNC_FITBITP      (0x00000145) ,
    PROC_SHOW_DATASYNC_JAWBONEP     (0x00000146) ,
    PROC_SHOW_DATASYNC_GARMINP      (0x00000147) ,

    PROC_CLOUD_37_SET               (0x00001100) ,
    PROC_CLOUD_37_CHECK             (0x00001200) ,
    PROC_CLOUD_45_SET               (0x00002100) ,
    PROC_CLOUD_45_CHECK             (0x00002200) ,
    PROC_CLOUD_43_SET               (0x00003100) ,
    PROC_CLOUD_43_CHECK             (0x00003200) ,
    PROC_CLOUD_62_SET               (0x00004100) ,
    PROC_CLOUD_62_CHECK             (0x00004200) ,

    PROC_INIT                       (0x01000000) ,
    PROC_INIT_PAGE                  (0x02000000) ,
    PROC_EXIT                       (0x10000000) ,
    PROC_IDLE                       (0x20000000) ,
    PROC_NULL                       (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    SettingState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static SettingState fromId(int value) {
        for(SettingState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
