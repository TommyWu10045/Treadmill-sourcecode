package com.rtx.treadmill.RtxMainFunction.Exercise;

/**
 * Created by chasechang on 3/27/17.
 */

public enum ExerciseState {
    /* enum */
    //MState
    PROC_EXERCISE_ERROR_CODE             (0x00000001) ,
    PROC_EXERCISE_EMERGENCY              (0x00000002) ,
    PROC_EXERCISE_PAUSE                  (0x00000003) ,
    PROC_EXERCISE_COUNTDOWN              (0x00000004) ,
    PROC_EXERCISE_RUN                    (0x00000005) ,
    PROC_EXERCISE_COOLDOWN               (0x00000006) ,
    PROC_EXERCISE_STOP                   (0x00000007) ,

    PROC_GETSTATUS              (0x0000F001) ,
    PROC_DELAY                  (0x0000F002) ,
    PROC_CACULATION             (0x0000F003) ,

    PROC_INIT                       (0x01000000) ,
    PROC_EXIT                       (0x10000000) ,
    PROC_IDLE                       (0x20000000) ,
    PROC_NULL                       (0x00000000) ;

    /**/
    private final int iState ;

    /**/
    ExerciseState(int code) {
        this.iState = code ;
    }

    public int getValue(){
        return (iState) ;
    }

    public static ExerciseState fromId(int value) {
        for(ExerciseState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
