package com.rtx.treadmill.RtxMainFunction.Training;

/**
 * Created by chasechang on 3/27/17.
 */

public enum TrainingState {
    /* enum */
    PROC_SHOW_TRAINING              (0x00000010) ,
    PROC_SHOW_WEIGHT                (0x00000020) ,
    PROC_SHOW_TIME                  (0x00000021) ,
    PROC_SHOW_LEVEL                 (0x00000022) ,
    PROC_SHOW_TARGET                (0x00000023) ,
    PROC_SHOW_CALORIES              (0x00000024) ,
    PROC_SHOW_DISTANCE              (0x00000025) ,
    PROC_SHOW_GO                    (0x00000026) ,
    PROC_SHOW_DISTANCE_REFRESH      (0x00000027) ,

    PROC_EXERCISE_SHOW              (0x00000030) ,
    PROC_EXERCISE_INIT              (0x00000031) ,
    PROC_EXERCISE_REFRESH           (0x00000032) ,
    PROC_EXERCISE_TR_FINISH         (0x00000033) ,
    PROC_EXERCISE_CHECK_COUNTDOWN   (0x00000034) ,

    PROC_SHOW_DONE                  (0x00000058) ,
    PROC_SHOW_LOGOUT                (0x00000059) ,

    PROC_INIT                       (0x01000000) ,
    PROC_INIT_PAGE                  (0x02000000) ,
    PROC_EXIT                       (0x10000000) ,
    PROC_IDLE                       (0x20000000) ,
    PROC_NULL                       (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    TrainingState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static TrainingState fromId(int value) {
        for(TrainingState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
