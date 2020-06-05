package com.rtx.treadmill.RtxMainFunctionBike.Performance;

/**
 * Created by chasechang on 3/27/17.
 */

public enum PerformanceState {
    /* enum */
    PROC_SHOW_PERFORMANCE           (0x00000010) ,
    PROC_SHOW_SESSION               (0x00000020) ,
    PROC_ADD_TYPE_SOURCE            (0x00000021) ,
    PROC_ADD_NAME                   (0x00000022) ,
    PROC_ADD_DATE                   (0x00000023) ,
    PROC_ADD_TIME                   (0x00000024) ,
    PROC_ADD_DURATION               (0x00000025) ,
    PROC_ADD_DISTANCE               (0x00000026) ,
    PROC_ADD_CALORIES               (0x00000027) ,
    PROC_SHOW_DELETE                (0x00000028) ,
    PROC_SHOW_DISTANCE_REFRESH      (0x00000029) ,

    PROC_SHOW_MONTH                 (0x00000030) ,
    PROC_SHOW_YEAR                  (0x00000040) ,

    PROC_SHOW_SESSIONE              (0x00000060) ,
    PROC_SHOW_MONTHE                (0x00000070) ,
    PROC_SHOW_YEARE                 (0x00000080) ,

    PROC_CLOUD_PF_GET_SESSION       (0x00010000) ,
    PROC_CLOUD_PF_GET_SESSION_CHECK (0x00020000) ,
    PROC_CLOUD_PF_GET_MONTH         (0x00030000) ,
    PROC_CLOUD_PF_GET_MONTH_CHECK   (0x00040000) ,
    PROC_CLOUD_PF_GET_YEAR          (0x00050000) ,
    PROC_CLOUD_PF_GET_YEAR_CHECK    (0x00060000) ,
    PROC_CLOUD_PF_DEL_SESSION       (0x00070000) ,
    PROC_CLOUD_PF_DEL_SESSION_CHECK (0x00080000) ,
    PROC_CLOUD_PF_ADD_SESSION       (0x00090000) ,
    PROC_CLOUD_PF_ADD_SESSION_CHECK (0x000A0000) ,

    PROC_SHOW_DIALOG_DELETE         (0x000B0000) ,

    PROC_INIT                       (0x01000000) ,
    PROC_INIT_PAGE                  (0x02000000) ,
    PROC_INIT_PAGEE                 (0x04000000) ,
    PROC_EXIT                       (0x10000000) ,
    PROC_IDLE                       (0x20000000) ,
    PROC_NULL                       (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    PerformanceState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static PerformanceState fromId(int value) {
        for(PerformanceState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
