package com.rtx.treadmill.RtxMainFunctionBike.MyWorkout;

/**
 * Created by chasechang on 3/27/17.
 */

public enum MyWorkoutState {
    /* enum */
    PROC_INIT(0) ,
    PROC_SHOW_PAGE_MAIN(1) ,
    PROC_SHOW_PAGE_NAME(2) ,
    PROC_SHOW_PAGE_DATALIST(3) ,
    PROC_SHOW_PAGE_SETTING(4) ,
    PROC_SHOW_PAGE_PREVIEW(5) ,
    PROC_SHOW_PAGE_DETAILS(6) ,
    PROC_SHOW_DIALOG_DELETE(7) ,

    PROC_CLOUD_GET_NAME_LIST(30),
    PROC_CLOUD_GET_NAME_LIST_CHECK(31),
    PROC_CLOUD_GET_INTERVAL(32),
    PROC_CLOUD_GET_INTERVAL_CHECK(33),
    PROC_CLOUD_ADD_INTERVAL(34),
    PROC_CLOUD_ADD_INTERVAL_CHECK(35),
    PROC_CLOUD_DELETE_INTERVAL(36),
    PROC_CLOUD_DELETE_INTERVAL_CHECK(37),
    PROC_CLOUD_UPDATE_INTERVAL(38),
    PROC_CLOUD_UPDATE_INTERVAL_CHECK(39),

    PROC_EXERCISE_PREPARE(50),
    PROC_EXERCISE_SHOW(51),
    PROC_EXERCISE_INIT(52),
    PROC_EXERCISE_REFRESH(53),
    PROC_EXERCISE_FINISH(54),
    PROC_EXERCISE_SHOW_DONE(55),
    PROC_EXERCISE_SHOW_LOGOUT(56),
    PROC_EXERCISE_CHECK_COUNTDOWN(57),

    PROC_IDLE(998) ,
    PROC_EXIT(999) ,
    PROC_NULL(1000) ;

    /**/
    private final int iState ;

    /**/
    MyWorkoutState(int code) {
        this.iState = code ;
    }

    public int getInt(){
        return (iState) ;
    }

    public static MyWorkoutState fromId(int value) {
        for(MyWorkoutState mCmd : values()) {
            if (mCmd.iState == value) {
                return mCmd;
            }
        }
        return ( PROC_NULL ) ;
    }
}
