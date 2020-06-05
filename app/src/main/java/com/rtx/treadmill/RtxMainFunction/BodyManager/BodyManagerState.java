package com.rtx.treadmill.RtxMainFunction.BodyManager;

/**
 * Created by chasechang on 3/27/17.
 */

public enum BodyManagerState {
    /* enum */
    PROC_SHOW_PAGE01                (0x00000001) ,
    PROC_SHOW_PAGE02                (0x00000002) ,
    PROC_SHOW_ALLVIEW               (0x00000003) ,
    PROC_SHOW_INPUT                 (0x00000004) ,
    PROC_SHOW_HISTORY               (0x00000005) ,
    PROC_SHOW_MUSCLE                (0x00000006) ,
    PROC_SHOW_BODYFAT               (0x00000007) ,
    PROC_SHOW_PAGE08                (0x00000008) ,

    PROC_SHOW_PAGE_INFO             (0x00000010) ,

    PROC_CLOUD_BD_GET               (0x00000100) ,
    PROC_CLOUD_GET_CHECK            (0x00000200) ,
    PROC_CLOUD_HIS_GET              (0x00001100) ,
    PROC_CLOUD_HIS_CHECK            (0x00001200) ,
    PROC_CLOUD_HIS_REFRESH          (0x00002100) ,
    PROC_CLOUD_HIS_REFRESH_CHECK    (0x00002200) ,
    PROC_CLOUD_BD_SET               (0x00003100) ,
    PROC_CLOUD_SET_CHECK            (0x00003200) ,

    PROC_INIT                       (0x01000000) ,
    PROC_INIT_PAGE                  (0x02000000) ,
    PROC_EXIT                       (0x10000000) ,
    PROC_IDLE                       (0x20000000) ,
    PROC_NULL                       (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    BodyManagerState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static BodyManagerState fromId(int value) {
        for(BodyManagerState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
