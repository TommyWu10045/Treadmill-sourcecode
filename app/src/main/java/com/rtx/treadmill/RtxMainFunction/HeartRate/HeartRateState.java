package com.rtx.treadmill.RtxMainFunction.HeartRate;

/**
 * Created by chasechang on 3/27/17.
 */

public enum HeartRateState {
    /* enum */
    PROC_SHOW_MAIN                  (0x00000001) ,
    PROC_SHOW_WEIGHT                (0x00000002) ,
    PROC_SHOW_AGE                   (0x00000003) ,
    PROC_SHOW_TARGET                (0x00000004) ,
    PROC_SHOW_TIME                  (0x00000005) ,
    PROC_SHOW_START                 (0x00000006) ,

    PROC_SHOW_PAGE_INFO             (0x00000080) ,
    PROC_SHOW_PAGE_HRCINFO          (0x00000081) ,

    PROC_EXERCISE_SHOW              (0x00000030) ,
    PROC_EXERCISE_INIT              (0x00000031) ,
    PROC_EXERCISE_REFRESH           (0x00000032) ,
    PROC_EXERCISE_HR_FINISH         (0x00000033) ,
    PROC_EXERCISE_CHECK_COUNTDOWN   (0x00000034) ,

    PROC_SHOW_DONE                  (0x00000058) ,
    PROC_SHOW_LOGOUT                (0x00000059) ,

    PROC_INIT                       (0x01000000) ,
    PROC_EXIT                       (0x10000000) ,
    PROC_IDLE                       (0x20000000) ,
    PROC_NULL                       (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    HeartRateState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static HeartRateState fromId(int value) {
        for(HeartRateState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
