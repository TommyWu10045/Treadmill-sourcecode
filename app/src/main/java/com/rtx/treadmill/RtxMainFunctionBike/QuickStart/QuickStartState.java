package com.rtx.treadmill.RtxMainFunctionBike.QuickStart;

/**
 * Created by chasechang on 3/27/17.
 */

public enum QuickStartState {
    /* enum */
    PROC_EXERCISE_SHOW              (0x00000030) ,
    PROC_EXERCISE_INIT              (0x00000031) ,
    PROC_EXERCISE_REFRESH           (0x00000032) ,
    PROC_EXERCISE_QS_FINISH         (0x00000033) ,
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
    QuickStartState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static QuickStartState fromId(int value) {
        for(QuickStartState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
